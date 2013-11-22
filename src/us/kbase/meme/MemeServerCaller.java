package us.kbase.meme;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import us.kbase.auth.AuthToken;
import us.kbase.auth.TokenFormatException;
import us.kbase.common.service.JacksonTupleModule;
import us.kbase.common.service.JsonClientException;
import us.kbase.common.service.ServerException;
import us.kbase.common.service.UnauthorizedException;
import us.kbase.generaltypes.SequenceSet;
import us.kbase.userandjobstate.UserAndJobStateClient;

public class MemeServerCaller {

	private static final String JOB_SERVICE = "http://140.221.84.180:7083";
	//private static final String JOB_ACCOUNT = "memejobs";
	//private static final String JOB_PASSWORD = "1475_rokegi";
	private static UserAndJobStateClient _jobClient = null;
	
	private static final String CLUSTER_SERVICE = "http://kbase.us/services/cs_test/jobs/";
	private static Integer connectionReadTimeOut = 30 * 60 * 1000;
	private static boolean isAuthAllowedForHttp = false;
	private static ObjectMapper mapper = new ObjectMapper().registerModule(new JacksonTupleModule());
	private static boolean deployCluster = true;

	
	protected static void setAuthAllowedForHttp(boolean AllowedForHttp) {
		isAuthAllowedForHttp = AllowedForHttp;
	}
	
	protected static UserAndJobStateClient jobClient(AuthToken token) throws TokenFormatException, UnauthorizedException, IOException {
		if(_jobClient == null)
		{
			URL jobServiceUrl = new URL (JOB_SERVICE);
			_jobClient = new UserAndJobStateClient (jobServiceUrl, token);
			_jobClient.setAuthAllowedForHttp(true);
		}
		return _jobClient;
	} 

	private static HttpURLConnection setupCall(AuthToken accessToken) throws IOException, JsonClientException {
		URL clusterServiceUrl = new URL(CLUSTER_SERVICE);
		HttpURLConnection conn = (HttpURLConnection) clusterServiceUrl.openConnection();
		conn.setConnectTimeout(10000);
		if (connectionReadTimeOut != null) {
			conn.setReadTimeout(connectionReadTimeOut);
		}
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		if (accessToken != null) {
			if (!(conn instanceof HttpsURLConnection || isAuthAllowedForHttp)) {
				throw new UnauthorizedException("RPC method required authentication shouldn't " +
						"be called through unsecured http, use https instead or call " +
						"setAuthAllowedForHttp(true) for your client");
			}
			if (accessToken == null || accessToken.isExpired()) {
				throw new UnauthorizedException("This method requires authentication but token was not set");
			}
			conn.setRequestProperty("Authorization", accessToken.toString());
		}
		return conn;
	}

	protected static String jsonCall(Map<String, String> arg, AuthToken token) throws IOException, JsonClientException {
		HttpURLConnection conn = setupCall(token);
		OutputStream os = conn.getOutputStream();
		JsonGenerator g = mapper.getFactory().createGenerator(os, JsonEncoding.UTF8);

		g.writeStartObject();
		for (Map.Entry<String, String> entry : arg.entrySet())
		{
			g.writeStringField(entry.getKey(), entry.getValue());
		}
		g.writeEndObject();
		g.close();

		JsonGenerator g2 = mapper.getFactory().createGenerator(System.out, JsonEncoding.UTF8);
		g2.writeStartObject();
		for (Map.Entry<String, String> entry : arg.entrySet())
		{
			g2.writeStringField(entry.getKey(), entry.getValue());
		}
		g2.writeEndObject();
		g2.close();
		

		
		int code = conn.getResponseCode();
		conn.getResponseMessage();

		InputStream istream;
		if (code == 500) {
			istream = conn.getErrorStream();
		} else {
			istream = conn.getInputStream();
		}

		JsonNode node = mapper.readTree(new UnclosableInputStream(istream));
		System.out.println(node.toString());
		
		if (node.has("error")) {
			Map<String, String> ret_error = mapper.readValue(mapper.treeAsTokens(node.get("error")), 
					new TypeReference<Map<String, String>>(){});
			
			String data = ret_error.get("data") == null ? ret_error.get("error") : ret_error.get("data");
			throw new ServerException(ret_error.get("message"),
					new Integer(ret_error.get("code")), ret_error.get("name"),
					data);
		}
		String res = null;
		if (node.has("result"))
			res = mapper.readValue(mapper.treeAsTokens(node.get("result")), String.class);
		if (res == null)
			throw new ServerException("An unknown server error occured", 0, "Unknown", null);
		return res;
	}

	
	public static MemeRunResult findMotifsWithMeme (SequenceSet sequenceSet, MemeRunParameters params) throws Exception {
		MemeRunResult returnVal = null;
        returnVal = MemeServerImpl.findMotifsWithMeme(sequenceSet, params, null, null);
        return returnVal;
	}
	
	public static String findMotifsWithMemeFromWs(String wsId, String sequenceSetId, MemeRunParameters params, AuthToken authPart) throws Exception {
        String returnVal = null;
        returnVal = MemeServerImpl.findMotifsWithMemeFromWs(wsId, sequenceSetId, params, authPart.toString());
        return returnVal;		
	}
	
    public static String findMotifsWithMemeJobFromWs(String wsId, String sequenceSetId, MemeRunParameters params, AuthToken authPart) throws Exception {

    	String returnVal = null;

        //Ask for job id
        returnVal = jobClient(authPart).createJob();
        
        if (deployCluster == false) { 
        	String result = MemeServerImpl.findMotifsWithMemeJobFromWs(wsId, sequenceSetId, params, returnVal, authPart.toString());
        	System.out.println(result);            
        } else {
        	//Call tug
        	Map<String, String> jsonArgs = new HashMap<String, String>();
        	jsonArgs.put("target", "cloud");
        	jsonArgs.put("application", "meme");
        	jsonArgs.put("method", "find_motifs_with_meme_job_from_ws");
        	jsonArgs.put("job_id", returnVal);
        	jsonArgs.put("workspace", wsId);
        	jsonArgs.put("seq_id", sequenceSetId);
        	jsonArgs.put("mod", params.getMod());
        	jsonArgs.put("nmotifs", params.getNmotifs().toString());
        	jsonArgs.put("minw", params.getMinw().toString());
        	jsonArgs.put("maxw", params.getMaxw().toString());
        	jsonArgs.put("pal", params.getPal().toString());
        	jsonArgs.put("revcomp", params.getRevcomp().toString());
        	jsonArgs.put("nsites", params.getNsites().toString());
        	jsonArgs.put("minsites", params.getMinsites().toString());
        	jsonArgs.put("maxsites", params.getMaxsites().toString());
        	jsonArgs.put("token", authPart.toString());
                
        	String result = jsonCall(jsonArgs, authPart);
        	System.out.println(result);
        }

        return returnVal;
    }

    public static TomtomRunResult compareMotifsWithTomtom(MemePSPM query, MemePSPMCollection target, TomtomRunParameters params) throws Exception {
        TomtomRunResult returnVal = null;
        returnVal = MemeServerImpl.compareMotifsWithTomtom(query,  target, params);
        return returnVal;
    }

    public static String compareMotifsWithTomtomFromWs(String wsId, String queryId, String targetId, TomtomRunParameters params, AuthToken authPart) throws Exception {
        String returnVal = null;
        returnVal = MemeServerImpl.compareMotifsWithTomtomFromWs(wsId, queryId, targetId, params, authPart.toString());
        return returnVal;
    }

    public static String compareMotifsWithTomtomJobFromWs(String wsId, String queryId, String targetId, TomtomRunParameters params, AuthToken authPart) throws Exception {
        String returnVal = null;
        //BEGIN compare_motifs_with_tomtom_job_from_ws
        
        //Ask for job id
        returnVal = jobClient(authPart).createJob();

        if (deployCluster == false) { 
        	String result = MemeServerImpl.compareMotifsWithTomtomJobFromWs(wsId, queryId, targetId, params, returnVal, authPart.toString());
        	System.out.println(result);
        } else {
        	//Call tug
        	Map<String, String> jsonArgs = new HashMap<String, String>();
        	jsonArgs.put("target", "cloud");
        	jsonArgs.put("application", "meme");
        	jsonArgs.put("method", "compare_motifs_with_tomtom_job_from_ws");
        	jsonArgs.put("job_id", returnVal);
        	jsonArgs.put("workspace", wsId);
        	jsonArgs.put("queryId", queryId);
        	jsonArgs.put("targetId", targetId);
        	jsonArgs.put("thresh", params.getThresh().toString());
        	jsonArgs.put("evalue", params.getEvalue().toString());
        	jsonArgs.put("dist", params.getDist());
        	jsonArgs.put("min_overlap", params.getMinOverlap().toString());
        	jsonArgs.put("internal", params.getInternal().toString());
        	jsonArgs.put("token", authPart.toString());
                
        	String result = jsonCall(jsonArgs, authPart);
        	System.out.println(result);
		}
		
        //END compare_motifs_with_tomtom_job_from_ws
        return returnVal;
    }

    public static TomtomRunResult compareMotifsWithTomtomByCollection(MemePSPMCollection query, MemePSPMCollection target, String pspmId, TomtomRunParameters params) throws Exception {
        TomtomRunResult returnVal = null;
        returnVal = MemeServerImpl.compareMotifsWithTomtomByCollection(query, target, pspmId, params, null, null);
        return returnVal;
    }

    public static String compareMotifsWithTomtomByCollectionFromWs(String wsId, String queryId, String targetId, String pspmId, TomtomRunParameters params, AuthToken authPart) throws Exception {
        String returnVal = null;
        returnVal = MemeServerImpl.compareMotifsWithTomtomByCollectionFromWs(wsId, queryId, targetId, pspmId, params, authPart.toString());
        return returnVal;
    }

    public static String compareMotifsWithTomtomJobByCollectionFromWs(String wsId, String queryId, String targetId, String pspmId, TomtomRunParameters params, AuthToken authPart) throws Exception {
        String returnVal = null;

        //Ask for job id
        returnVal = jobClient(authPart).createJob();

        if (deployCluster == false) { 
        	String result = MemeServerImpl.compareMotifsWithTomtomJobByCollectionFromWs(wsId, queryId, targetId, pspmId, params, returnVal, authPart.toString());
            System.out.println(result);
        } else {

        	//Call tug
        	Map<String, String> jsonArgs = new HashMap<String, String>();
        	jsonArgs.put("target", "cloud");
        	jsonArgs.put("application", "meme");
        	jsonArgs.put("method", "compare_motifs_with_tomtom_job_from_ws");
        	jsonArgs.put("job_id", returnVal);
        	jsonArgs.put("workspace", wsId);
        	jsonArgs.put("queryId", queryId);
        	jsonArgs.put("targetId", targetId);
        	jsonArgs.put("thresh", params.getThresh().toString());
        	jsonArgs.put("evalue", params.getEvalue().toString());
        	jsonArgs.put("dist", params.getDist());
        	jsonArgs.put("min_overlap", params.getMinOverlap().toString());
        	jsonArgs.put("internal", params.getInternal().toString());
        	jsonArgs.put("pspm", pspmId);
        	jsonArgs.put("token", authPart.toString());
        
        String result = jsonCall(jsonArgs, authPart);
        System.out.println(result);
        }
        return returnVal;
    }

    public static MastRunResult findSitesWithMast(MemePSPM query, SequenceSet target, Double mt) throws Exception {
        MastRunResult returnVal = null;
        returnVal = MemeServerImpl.findSitesWithMast(query, target, mt);
        return returnVal;
    }

    public static String findSitesWithMastFromWs(String wsId, String queryId, String targetId, Double mt, AuthToken authPart) throws Exception {
        String returnVal = null;
        returnVal = MemeServerImpl.findSitesWithMastFromWs(wsId, queryId, targetId, mt, authPart.toString());
        return returnVal;
    }

    public static String findSitesWithMastJobFromWs(String wsId, String queryId, String targetId, Double mt, AuthToken authPart) throws Exception {
        String returnVal = null;
        //BEGIN find_sites_with_mast_job_from_ws
        //Ask for job id
        returnVal = jobClient(authPart).createJob();
        
        if (deployCluster == false) { 
        	String result = MemeServerImpl.findSitesWithMastJobFromWs(wsId, queryId, targetId, mt, returnVal, authPart.toString());
        	System.out.println(result);
        } else {
        	Map<String, String> jsonArgs = new HashMap<String, String>();
        	jsonArgs.put("target", "cloud");
        	jsonArgs.put("application", "meme");
        	jsonArgs.put("method", "find_sites_with_mast_job_from_ws");
        	jsonArgs.put("job_id", returnVal);
        	jsonArgs.put("workspace", wsId);
        	jsonArgs.put("queryId", queryId);
        	jsonArgs.put("targetId", targetId);
        	jsonArgs.put("thresh", mt.toString());
        	jsonArgs.put("token", authPart.toString());
        
        	String result = jsonCall(jsonArgs, authPart);
        	System.out.println(result);
        }

        //END find_sites_with_mast_job_from_ws
        return returnVal;
    }

    public static MastRunResult findSitesWithMastByCollection(MemePSPMCollection query, SequenceSet target, String pspmId, Double mt) throws Exception {
        MastRunResult returnVal = null;
        returnVal = MemeServerImpl.findSitesWithMastByCollection(query, target, pspmId, mt, null, null);
        return returnVal;
    }

    public static String findSitesWithMastByCollectionFromWs(String wsId, String queryId, String targetId, String pspmId, Double mt, AuthToken authPart) throws Exception {
        String returnVal = null;
        returnVal = MemeServerImpl.findSitesWithMastByCollectionFromWs(wsId, queryId, targetId, pspmId, mt, authPart.toString());
        return returnVal;
    }

    public static String findSitesWithMastJobByCollectionFromWs(String wsId, String queryId, String targetId, String pspmId, Double mt, AuthToken authPart) throws Exception {
        String returnVal = null;
        //BEGIN find_sites_with_mast_job_by_collection_from_ws

        returnVal = jobClient(authPart).createJob();

        if (deployCluster == false) { 
        	String result = MemeServerImpl.findSitesWithMastJobByCollectionFromWs(wsId, queryId, targetId, pspmId, mt, returnVal, authPart.toString());
        	System.out.println(result);
        } else {

        	Map<String, String> jsonArgs = new HashMap<String, String>();
        	jsonArgs.put("target", "cloud");
        	jsonArgs.put("application", "meme");
        	jsonArgs.put("method", "find_sites_with_mast_job_from_ws");
        	jsonArgs.put("job_id", returnVal);
        	jsonArgs.put("workspace", wsId);
        	jsonArgs.put("queryId", queryId);
        	jsonArgs.put("targetId", targetId);
        	jsonArgs.put("thresh", mt.toString());
        	jsonArgs.put("pspm", pspmId);
        	jsonArgs.put("token", authPart.toString());
        
        	String result = jsonCall(jsonArgs, authPart);
        	System.out.println(result);
        }
        
        //END find_sites_with_mast_job_by_collection_from_ws
        return returnVal;
    }

    public static MemePSPMCollection getPspmCollectionFromMemeResult(MemeRunResult memeRunResult) throws Exception {
        MemePSPMCollection returnVal = null;
        returnVal = MemeServerImpl.getPspmCollectionFromMemeResult(memeRunResult);
        return returnVal;
    }

    public static String getPspmCollectionFromMemeResultFromWs(String wsId, String memeRunResultId, AuthToken authPart) throws Exception {
        String returnVal = null;
        returnVal = MemeServerImpl.getPspmCollectionFromMemeResultFromWs(wsId, memeRunResultId, authPart.toString());
        return returnVal;
    }

    public static String getPspmCollectionFromMemeResultJobFromWs(String wsId, String memeRunResultId, AuthToken authPart) throws Exception {
        String returnVal = null;
        //BEGIN get_pspm_collection_from_meme_result_job_from_ws
        returnVal = jobClient(authPart).createJob();

        if (deployCluster == false) { 
        	String result = MemeServerImpl.getPspmCollectionFromMemeJobResultFromWs(wsId, memeRunResultId, returnVal, authPart.toString());
        	System.out.println(result);
        } else {

        	Map<String, String> jsonArgs = new HashMap<String, String>();
        	jsonArgs.put("target", "cloud");
        	jsonArgs.put("application", "meme");
        	jsonArgs.put("method", "get_pspm_collection_from_meme_result_from_ws");
        	jsonArgs.put("job_id", returnVal);
        	jsonArgs.put("workspace", wsId);
        	jsonArgs.put("queryId", memeRunResultId);
        	jsonArgs.put("token", authPart.toString());
        
        	String result = jsonCall(jsonArgs, authPart);
        	System.out.println(result);
        }
        
        //END get_pspm_collection_from_meme_result_job_from_ws
        return returnVal;
    }    

	private static class UnclosableInputStream extends InputStream {
		private InputStream inner;
		private boolean isClosed = false;
		
		public UnclosableInputStream(InputStream inner) {
			this.inner = inner;
		}
		
		@Override
		public int read() throws IOException {
			if (isClosed)
				return -1;
			return inner.read();
		}
		
		@Override
		public int available() throws IOException {
			if (isClosed)
				return 0;
			return inner.available();
		}
		
		@Override
		public void close() throws IOException {
			isClosed = true;
		}
		
		@Override
		public synchronized void mark(int readlimit) {
			inner.mark(readlimit);
		}
		
		@Override
		public boolean markSupported() {
			return inner.markSupported();
		}
		
		@Override
		public int read(byte[] b) throws IOException {
			if (isClosed)
				return 0;
			return inner.read(b);
		}
		
		@Override
		public int read(byte[] b, int off, int len) throws IOException {
			if (isClosed)
				return 0;
			return inner.read(b, off, len);
		}
		
		@Override
		public synchronized void reset() throws IOException {
			if (isClosed)
				return;
			inner.reset();
		}
		
		@Override
		public long skip(long n) throws IOException {
			if (isClosed)
				return 0;
			return inner.skip(n);
		}
	}

}
