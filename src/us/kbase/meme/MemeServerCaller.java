package us.kbase.meme;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import us.kbase.auth.AuthToken;
import us.kbase.sequences.SequenceSet;
import us.kbase.userandjobstate.UserAndJobStateClient;

public class MemeServerCaller {

	private static final String JOB_SERVICE = MemeServerConfig.JOB_SERVICE;
	private static final String AWE_SERVICE = MemeServerConfig.CLUSTER_SERVICE;
	private static final String SHOCK_URL = MemeServerConfig.SHOCK_URL;
	private static boolean deployAwe = MemeServerConfig.DEPLOY_AWE;
	
	private static Integer connectionReadTimeOut = 30 * 60 * 1000;
	
	protected static String executePost(String jsonRequest) throws IOException {
		URL url;
		HttpURLConnection connection = null;
		PrintWriter writer = null;
		url = new URL(AWE_SERVICE);
		String boundary = Long.toHexString(System.currentTimeMillis());
		connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(10000);
		if (connectionReadTimeOut != null) {
			connection.setReadTimeout(connectionReadTimeOut);
		}
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type",
				"multipart/form-data; boundary=" + boundary);
		connection.setDoOutput(true);
		// connection.setDoInput(true);
		OutputStream output = connection.getOutputStream();
		writer = new PrintWriter(new OutputStreamWriter(output), true); //set true for autoFlush!
		String CRLF = "\r\n";
		writer.append("--" + boundary).append(CRLF);
		writer.append(
				"Content-Disposition: form-data; name=\"upload\"; filename=\"inferelator.awe\"")
				.append(CRLF);
		writer.append("Content-Type: application/octet-stream").append(CRLF);
		writer.append(CRLF).flush();
		writer.append(jsonRequest).append(CRLF);
		writer.flush();
		writer.append("--" + boundary + "--").append(CRLF);
		writer.append(CRLF).flush();

		// Get Response
		InputStream is = connection.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		String line;
		StringBuffer response = new StringBuffer();
		while ((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\r');
		}
		rd.close();

		if (writer != null)
			writer.close();

		if (connection != null) {
			connection.disconnect();
		}
		return response.toString();
	}

	
	public static MemeRunResult findMotifsWithMeme (SequenceSet sequenceSet, MemeRunParameters params) throws Exception {
		MemeRunResult returnVal = null;
        returnVal = MemeServerImpl.findMotifsWithMeme(sequenceSet, params, null, null);
        return returnVal;
	}
	
	public static String findMotifsWithMemeFromWs(String wsName, MemeRunParameters params, AuthToken authPart) throws Exception {
        String returnVal = null;
        returnVal = MemeServerImpl.findMotifsWithMemeFromWs(wsName, params, authPart.toString());
        return returnVal;		
	}
	
    public static String findMotifsWithMemeJobFromWs(String wsName, MemeRunParameters params, AuthToken authPart) throws Exception {

    	String returnVal = null;
        //Obtain job id
    	URL jobServiceUrl = new URL(JOB_SERVICE);
    	UserAndJobStateClient jobClient = new UserAndJobStateClient(jobServiceUrl, authPart);
    	jobClient.setAuthAllowedForHttp(true);
        returnVal = jobClient.createJob();
        System.out.println(returnVal);
        
        if (deployAwe == false) { 
        	String result = MemeServerImpl.findMotifsWithMemeJobFromWs(wsName, params, returnVal, authPart.toString());
        	System.out.println(result);            
        } else {
        	//Call invoker
    		String jsonArgs = "{\"info\": {\"pipeline\": \"meme-runner-pipeline\",\"name\": \"meme\",\"project\": \"default\""
    				+ ",\"user\": \"default\",\"clientgroups\":\"\",\"sessionId\":\""
    				+ returnVal + "\"},\"tasks\": [{\"cmd\": {\"args\": \""	
    				+ " --job '" + returnVal
    				+ "' --method find_motifs_with_meme_job_from_ws"
    				+ " --ws '" + wsName
    				+ "' --query '" + params.getSourceRef()
    				+ "' --mod '" + params.getMod()
    				+ "' --nmotifs '" + params.getNmotifs().toString()
    				+ "' --minw '" + params.getMinw().toString()
    				+ "' --maxw '" + params.getMaxw().toString()
    				+ "' --nsites '" + params.getNsites().toString()
    				+ "' --minsites '" + params.getMinsites().toString()
    				+ "' --maxsites '" + params.getMaxsites().toString()
    				+ "' --pal '" + params.getPal().toString()
    				+ "' --revcomp '" + params.getRevcomp().toString()
    				+ "' --token '" + authPart.toString() + "'"
    				+ "\", \"description\": \"running MEME.find_motifs_with_meme_job_from_ws\", \"name\": \"run_meme\"}, \"dependsOn\": [], \"outputs\": {\""
    				+ returnVal + ".tgz\": {\"host\": \"" 
    				+ SHOCK_URL + "\"}},\"taskid\": \"0\",\"skip\": 0,\"totalwork\": 1}]}";


			if (MemeServerConfig.LOG_AWE_CALLS) {
				System.out.println(jsonArgs);
				PrintWriter out = new PrintWriter(new FileWriter(
						"/var/tmp/meme/meme-awe.log", true));
				out.write("Job " + returnVal + " : call to AWE\n" + jsonArgs
						+ "\n***\n");
				out.write("***");
				if (out != null) {
					out.close();
				}
			}
			String result = executePost(jsonArgs);
			if (MemeServerConfig.LOG_AWE_CALLS) {
				System.out.println(result);
				PrintWriter out = new PrintWriter(new FileWriter(
						"/var/tmp/meme/meme-awe.log", true));
				out.write("Job " + returnVal + " : AWE response\n" + result
						+ "\n***\n");
				if (out != null) {
					out.close();
				}
			}
        }
        return returnVal;
    }

    public static TomtomRunResult compareMotifsWithTomtom(MemePSPM query, MemePSPMCollection target, TomtomRunParameters params) throws Exception {
        TomtomRunResult returnVal = null;
        returnVal = MemeServerImpl.compareMotifsWithTomtom(query, target, params);
        return returnVal;
    }

/*    public static String compareMotifsWithTomtomFromWs(String wsId, String queryId, String targetId, TomtomRunParameters params, AuthToken authPart) throws Exception {
        String returnVal = null;
        returnVal = MemeServerImpl.compareMotifsWithTomtomFromWs(wsId, queryId, targetId, params, authPart.toString());
        return returnVal;
    }*/

/*    public static String compareMotifsWithTomtomJobFromWs(String wsId, String queryId, String targetId, TomtomRunParameters params, AuthToken authPart) throws Exception {
        String returnVal = null;
        //Ask for job id
        returnVal = jobClient(authPart).createJob();

        if (deployCluster == false) { 
        	String result = MemeServerImpl.compareMotifsWithTomtomJobFromWs(wsId, queryId, targetId, params, returnVal, authPart.toString());
        	System.out.println(result);
        } else {
        	//Call invoker
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
        return returnVal;
    }*/

    public static TomtomRunResult compareMotifsWithTomtomByCollection(MemePSPMCollection query, MemePSPMCollection target, TomtomRunParameters params) throws Exception {
        TomtomRunResult returnVal = null;
        returnVal = MemeServerImpl.compareMotifsWithTomtomByCollection(query, target, params, null, null);
        return returnVal;
    }

    public static String compareMotifsWithTomtomByCollectionFromWs(String wsName, TomtomRunParameters params, AuthToken authPart) throws Exception {
        String returnVal = null;
        returnVal = MemeServerImpl.compareMotifsWithTomtomByCollectionFromWs(wsName, params, authPart.toString());
        return returnVal;
    }

    public static String compareMotifsWithTomtomJobByCollectionFromWs(String wsName, TomtomRunParameters params, AuthToken authPart) throws Exception {
        String returnVal = null;
        //Ask for job id
    	URL jobServiceUrl = new URL(JOB_SERVICE);
    	UserAndJobStateClient jobClient = new UserAndJobStateClient(jobServiceUrl, authPart);
    	jobClient.setAuthAllowedForHttp(true);
        returnVal = jobClient.createJob();

        if (deployAwe == false) { 
        	String result = MemeServerImpl.compareMotifsWithTomtomJobByCollectionFromWs(wsName, params, returnVal, authPart.toString());
            System.out.println(result);
        } else {
        	//Call AWE
        
       		String jsonArgs = "{\"info\": {\"pipeline\": \"meme-runner-pipeline\",\"name\": \"meme\",\"project\": \"default\""
    				+ ",\"user\": \"default\",\"clientgroups\":\"\",\"sessionId\":\""
    				+ returnVal + "\"},\"tasks\": [{\"cmd\": {\"args\": \""	
    				+ " --job '" + returnVal
    				+ "' --method compare_motifs_with_tomtom_job_by_collection_from_ws"
    				+ " --ws '" + wsName
    				+ "' --query '" + params.getQueryRef()
    				+ "' --target '" + params.getTargetRef()
    				+ "' --pspm '" + params.getPspmId()
    				+ "' --thresh '" + params.getThresh().toString()
    				+ "' --dist '" + params.getDist()
    				+ "' --min_overlap '" + params.getMinOverlap().toString()
    				+ "' --evalue '" + params.getEvalue().toString()
    				+ "' --internal '" + params.getInternal().toString()
    				+ "' --token '" + authPart.toString() + "'"
    				+ "\", \"description\": \"running MEME.compare_motifs_with_tomtom_job_by_collection_from_ws\", \"name\": \"run_meme\"}, \"dependsOn\": [], \"outputs\": {\""
    				+ returnVal + ".tgz\": {\"host\": \"" 
    				+ SHOCK_URL + "\"}},\"taskid\": \"0\",\"skip\": 0,\"totalwork\": 1}]}";


			if (MemeServerConfig.LOG_AWE_CALLS) {
				System.out.println(jsonArgs);
				PrintWriter out = new PrintWriter(new FileWriter(
						"/var/tmp/meme/meme-awe.log", true));
				out.write("Job " + returnVal + " : call to AWE\n" + jsonArgs
						+ "\n***\n");
				out.write("***");
				if (out != null) {
					out.close();
				}
			}
			String result = executePost(jsonArgs);
			if (MemeServerConfig.LOG_AWE_CALLS) {
				System.out.println(result);
				PrintWriter out = new PrintWriter(new FileWriter(
						"/var/tmp/meme/meme-awe.log", true));
				out.write("Job " + returnVal + " : AWE response\n" + result
						+ "\n***\n");
				if (out != null) {
					out.close();
				}
			}
        }
        return returnVal;
    }

    public static MastRunResult findSitesWithMast(MemePSPM query, SequenceSet target, MastRunParameters params) throws Exception {
        MastRunResult returnVal = null;
        returnVal = MemeServerImpl.findSitesWithMast(query, target, params);
        return returnVal;
    }

/*    public static String findSitesWithMastFromWs(String wsId, String queryId, String targetId, Double mt, AuthToken authPart) throws Exception {
        String returnVal = null;
        returnVal = MemeServerImpl.findSitesWithMastFromWs(wsId, queryId, targetId, mt, authPart.toString());
        return returnVal;
    }*/

/*    public static String findSitesWithMastJobFromWs(String wsId, String queryId, String targetId, Double mt, AuthToken authPart) throws Exception {
        String returnVal = null;
        //Ask for job id
        returnVal = jobClient(authPart).createJob();
        
        if (deployCluster == false) { 
        	String result = MemeServerImpl.findSitesWithMastJobFromWs(wsId, queryId, targetId, mt, returnVal, authPart.toString());
        	System.out.println(result);
        } else {
        	//Call invoker
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
        return returnVal;
    }*/

    public static MastRunResult findSitesWithMastByCollection(MemePSPMCollection query, SequenceSet target, MastRunParameters params) throws Exception {
        MastRunResult returnVal = null;
        returnVal = MemeServerImpl.findSitesWithMastByCollection(query, target, params, null, null);
        return returnVal;
    }

    public static String findSitesWithMastByCollectionFromWs(String wsName, MastRunParameters params, AuthToken authPart) throws Exception {
        String returnVal = null;
        returnVal = MemeServerImpl.findSitesWithMastByCollectionFromWs(wsName, params, authPart.toString());
        return returnVal;
    }

    public static String findSitesWithMastJobByCollectionFromWs(String wsName, MastRunParameters params, AuthToken authPart) throws Exception {
        String returnVal = null;
    	URL jobServiceUrl = new URL(JOB_SERVICE);
    	UserAndJobStateClient jobClient = new UserAndJobStateClient(jobServiceUrl, authPart);
    	jobClient.setAuthAllowedForHttp(true);
        returnVal = jobClient.createJob();

        if (deployAwe == false) { 
        	String result = MemeServerImpl.findSitesWithMastJobByCollectionFromWs(wsName, params, returnVal, authPart.toString());
        	System.out.println(result);
        } else {
        	//Call AWE
        
       		String jsonArgs = "{\"info\": {\"pipeline\": \"meme-runner-pipeline\",\"name\": \"meme\",\"project\": \"default\""
    				+ ",\"user\": \"default\",\"clientgroups\":\"\",\"sessionId\":\""
    				+ returnVal + "\"},\"tasks\": [{\"cmd\": {\"args\": \""	
    				+ " --job '" + returnVal
    				+ "' --method find_sites_with_mast_job_by_collection_from_ws"
    				+ " --ws '" + wsName
    				+ "' --query '" + params.getQueryRef()
    				+ "' --target '" + params.getTargetRef()
    				+ "' --pspm '" + params.getPspmId()
    				+ "' --thresh '" + params.getMt().toString()
    				+ "' --token '" + authPart.toString() + "'"
    				+ "\", \"description\": \"running MEME.find_sites_with_mast_job_by_collection_from_ws\", \"name\": \"run_meme\"}, \"dependsOn\": [], \"outputs\": {\""
    				+ returnVal + ".tgz\": {\"host\": \"" 
    				+ SHOCK_URL + "\"}},\"taskid\": \"0\",\"skip\": 0,\"totalwork\": 1}]}";


			if (MemeServerConfig.LOG_AWE_CALLS) {
				System.out.println(jsonArgs);
				PrintWriter out = new PrintWriter(new FileWriter(
						"/var/tmp/meme/meme-awe.log", true));
				out.write("Job " + returnVal + " : call to AWE\n" + jsonArgs
						+ "\n***\n");
				out.write("***");
				if (out != null) {
					out.close();
				}
			}
			String result = executePost(jsonArgs);
			if (MemeServerConfig.LOG_AWE_CALLS) {
				System.out.println(result);
				PrintWriter out = new PrintWriter(new FileWriter(
						"/var/tmp/meme/meme-awe.log", true));
				out.write("Job " + returnVal + " : AWE response\n" + result
						+ "\n***\n");
				if (out != null) {
					out.close();
				}
			}
        }
        return returnVal;
    }

    public static MemePSPMCollection getPspmCollectionFromMemeResult(MemeRunResult memeRunResult) throws Exception {
        MemePSPMCollection returnVal = null;
        returnVal = MemeServerImpl.getPspmCollectionFromMemeResult(memeRunResult);
        return returnVal;
    }

    public static String getPspmCollectionFromMemeResultFromWs(String wsId, String memeRunResultRef, AuthToken authPart) throws Exception {
        String returnVal = null;
        returnVal = MemeServerImpl.getPspmCollectionFromMemeResultFromWs(wsId, memeRunResultRef, authPart.toString());
        return returnVal;
    }

    public static String getPspmCollectionFromMemeResultJobFromWs(String wsId, String memeRunResultRef, AuthToken authPart) throws Exception {
        String returnVal = null;
    	URL jobServiceUrl = new URL(JOB_SERVICE);
    	UserAndJobStateClient jobClient = new UserAndJobStateClient(jobServiceUrl, authPart);
    	jobClient.setAuthAllowedForHttp(true);
        returnVal = jobClient.createJob();

       	String result = MemeServerImpl.getPspmCollectionFromMemeJobResultFromWs(wsId, memeRunResultRef, returnVal, authPart.toString());
       	System.out.println(result);
        return returnVal;
    }    

}
