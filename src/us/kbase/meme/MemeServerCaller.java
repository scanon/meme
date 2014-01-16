package us.kbase.meme;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import us.kbase.auth.AuthToken;
import us.kbase.auth.TokenFormatException;
import us.kbase.common.service.JacksonTupleModule;
import us.kbase.common.service.JsonClientException;
import us.kbase.common.service.ServerException;
import us.kbase.kbasesequences.SequenceSet;
import us.kbase.userandjobstate.InitProgress;
import us.kbase.userandjobstate.UserAndJobStateClient;

public class MemeServerCaller {

	private static final String JOB_SERVICE = MemeServerConfig.JOB_SERVICE;
	private static final String AWE_SERVICE = MemeServerConfig.CLUSTER_SERVICE;
	private static boolean deployAwe = MemeServerConfig.DEPLOY_AWE;
	private static final String AWF_CONFIG_FILE = MemeServerConfig.AWF_CONFIG_FILE;

	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssZ");

	public static MemeRunResult findMotifsWithMeme(SequenceSet sequenceSet,
			MemeRunParameters params) throws Exception {
		MemeRunResult returnVal = null;
		returnVal = MemeServerImpl.findMotifsWithMeme(sequenceSet, params,
				null, null);
		return returnVal;
	}

	public static String findMotifsWithMemeFromWs(String wsName,
			MemeRunParameters params, AuthToken authPart) throws Exception {
		String returnVal = null;
		returnVal = MemeServerImpl.findMotifsWithMemeFromWs(wsName, params,
				authPart.toString());
		return returnVal;
	}

	public static String findMotifsWithMemeJobFromWs(String wsName,
			MemeRunParameters params, AuthToken authPart) throws Exception {

		String returnVal = null;
		Date date = new Date();
		date.setTime(date.getTime() + 10000L);
		// Obtain job id

		URL jobServiceUrl = new URL(JOB_SERVICE);
		UserAndJobStateClient jobClient = new UserAndJobStateClient(
				jobServiceUrl, authPart);
		// jobClient.setAuthAllowedForHttp(true);
		returnVal = jobClient.createJob();
		jobClient.startJob(returnVal, authPart.toString(),
				"Starting MEME service job...", "MEME service job " + returnVal
						+ ". Method: findMotifsWithMemeJobFromWs. Input: "
						+ params.getSourceRef() + ". Workspace: " + wsName
						+ ".",
				new InitProgress().withPtype("task").withMax(5L),
				dateFormat.format(date));
		jobClient = null;
		System.out.println(returnVal);

		if (deployAwe == false) {
			String result = MemeServerImpl.findMotifsWithMemeJobFromWs(wsName,
					params, returnVal, authPart.toString());
			System.out.println(result);
		} else {
			// Call invoker
			String jsonArgs = formatAWEConfig(returnVal, wsName, params, authPart.toString());

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

			String result = submitJob(jsonArgs);
			reportAweStatus(authPart, returnVal, result);
			
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

	public static TomtomRunResult compareMotifsWithTomtom(MemePSPM query,
			MemePSPMCollection target, TomtomRunParameters params)
			throws Exception {
		TomtomRunResult returnVal = null;
		returnVal = MemeServerImpl.compareMotifsWithTomtom(query, target,
				params);
		return returnVal;
	}

	/*
	 * public static String compareMotifsWithTomtomFromWs(String wsId, String
	 * queryId, String targetId, TomtomRunParameters params, AuthToken authPart)
	 * throws Exception { String returnVal = null; returnVal =
	 * MemeServerImpl.compareMotifsWithTomtomFromWs(wsId, queryId, targetId,
	 * params, authPart.toString()); return returnVal; }
	 */

	/*
	 * public static String compareMotifsWithTomtomJobFromWs(String wsId, String
	 * queryId, String targetId, TomtomRunParameters params, AuthToken authPart)
	 * throws Exception { String returnVal = null; //Ask for job id returnVal =
	 * jobClient(authPart).createJob();
	 * 
	 * if (deployCluster == false) { String result =
	 * MemeServerImpl.compareMotifsWithTomtomJobFromWs(wsId, queryId, targetId,
	 * params, returnVal, authPart.toString()); System.out.println(result); }
	 * else { //Call invoker Map<String, String> jsonArgs = new HashMap<String,
	 * String>(); jsonArgs.put("target", "cloud"); jsonArgs.put("application",
	 * "meme"); jsonArgs.put("method",
	 * "compare_motifs_with_tomtom_job_from_ws"); jsonArgs.put("job_id",
	 * returnVal); jsonArgs.put("workspace", wsId); jsonArgs.put("queryId",
	 * queryId); jsonArgs.put("targetId", targetId); jsonArgs.put("thresh",
	 * params.getThresh().toString()); jsonArgs.put("evalue",
	 * params.getEvalue().toString()); jsonArgs.put("dist", params.getDist());
	 * jsonArgs.put("min_overlap", params.getMinOverlap().toString());
	 * jsonArgs.put("internal", params.getInternal().toString());
	 * jsonArgs.put("token", authPart.toString());
	 * 
	 * String result = jsonCall(jsonArgs, authPart); System.out.println(result);
	 * } return returnVal; }
	 */

	public static TomtomRunResult compareMotifsWithTomtomByCollection(
			MemePSPMCollection query, MemePSPMCollection target,
			TomtomRunParameters params) throws Exception {
		TomtomRunResult returnVal = null;
		returnVal = MemeServerImpl.compareMotifsWithTomtomByCollection(query,
				target, params, null, null);
		return returnVal;
	}

	public static String compareMotifsWithTomtomByCollectionFromWs(
			String wsName, TomtomRunParameters params, AuthToken authPart)
			throws Exception {
		String returnVal = null;
		returnVal = MemeServerImpl.compareMotifsWithTomtomByCollectionFromWs(
				wsName, params, authPart.toString());
		return returnVal;
	}

	public static String compareMotifsWithTomtomJobByCollectionFromWs(
			String wsName, TomtomRunParameters params, AuthToken authPart)
			throws Exception {
		String returnVal = null;
		Date date = new Date();
		date.setTime(date.getTime() + 10000L);

		// Ask for job id
		URL jobServiceUrl = new URL(JOB_SERVICE);
		UserAndJobStateClient jobClient = new UserAndJobStateClient(
				jobServiceUrl, authPart);
		// jobClient.setAuthAllowedForHttp(true);
		returnVal = jobClient.createJob();
		jobClient.startJob(returnVal, authPart.toString(),
				"Starting MEME service job...", "MEME service job " + returnVal
				+ ". Method: compareMotifsWithTomtomJobByCollectionFromWs. Input: "
				+ params.getQueryRef() + ", " + params.getTargetRef()
				+ ". Workspace: " + wsName + ".",
				new InitProgress().withPtype("task").withMax(5L),
				dateFormat.format(date));
		jobClient = null;

		if (deployAwe == false) {
			String result = MemeServerImpl
					.compareMotifsWithTomtomJobByCollectionFromWs(wsName,
							params, returnVal, authPart.toString());
			System.out.println(result);
		} else {
			// Call AWE
			String jsonArgs = formatAWEConfig(returnVal, wsName, params, authPart.toString());

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
			String result = submitJob(jsonArgs);
			reportAweStatus(authPart, returnVal, result);

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

	public static MastRunResult findSitesWithMast(MemePSPM query,
			SequenceSet target, MastRunParameters params) throws Exception {
		MastRunResult returnVal = null;
		returnVal = MemeServerImpl.findSitesWithMast(query, target, params);
		return returnVal;
	}

	/*
	 * public static String findSitesWithMastFromWs(String wsId, String queryId,
	 * String targetId, Double mt, AuthToken authPart) throws Exception { String
	 * returnVal = null; returnVal =
	 * MemeServerImpl.findSitesWithMastFromWs(wsId, queryId, targetId, mt,
	 * authPart.toString()); return returnVal; }
	 */

	/*
	 * public static String findSitesWithMastJobFromWs(String wsId, String
	 * queryId, String targetId, Double mt, AuthToken authPart) throws Exception
	 * { String returnVal = null; //Ask for job id returnVal =
	 * jobClient(authPart).createJob();
	 * 
	 * if (deployCluster == false) { String result =
	 * MemeServerImpl.findSitesWithMastJobFromWs(wsId, queryId, targetId, mt,
	 * returnVal, authPart.toString()); System.out.println(result); } else {
	 * //Call invoker Map<String, String> jsonArgs = new HashMap<String,
	 * String>(); jsonArgs.put("target", "cloud"); jsonArgs.put("application",
	 * "meme"); jsonArgs.put("method", "find_sites_with_mast_job_from_ws");
	 * jsonArgs.put("job_id", returnVal); jsonArgs.put("workspace", wsId);
	 * jsonArgs.put("queryId", queryId); jsonArgs.put("targetId", targetId);
	 * jsonArgs.put("thresh", mt.toString()); jsonArgs.put("token",
	 * authPart.toString());
	 * 
	 * String result = jsonCall(jsonArgs, authPart); System.out.println(result);
	 * } return returnVal; }
	 */

	public static MastRunResult findSitesWithMastByCollection(
			MemePSPMCollection query, SequenceSet target,
			MastRunParameters params) throws Exception {
		MastRunResult returnVal = null;
		returnVal = MemeServerImpl.findSitesWithMastByCollection(query, target,
				params, null, null);
		return returnVal;
	}

	public static String findSitesWithMastByCollectionFromWs(String wsName,
			MastRunParameters params, AuthToken authPart) throws Exception {
		String returnVal = null;
		returnVal = MemeServerImpl.findSitesWithMastByCollectionFromWs(wsName,
				params, authPart.toString());
		return returnVal;
	}

	public static String findSitesWithMastJobByCollectionFromWs(String wsName,
			MastRunParameters params, AuthToken authPart) throws Exception {
		String returnVal = null;
		Date date = new Date();
		date.setTime(date.getTime() + 10000L);

		URL jobServiceUrl = new URL(JOB_SERVICE);
		UserAndJobStateClient jobClient = new UserAndJobStateClient(
				jobServiceUrl, authPart);
		// jobClient.setAuthAllowedForHttp(true);
		returnVal = jobClient.createJob();
		jobClient.startJob(returnVal, authPart.toString(),
				"Starting MEME service job...", "MEME service job " + returnVal
				+ ". Method: findSitesWithMastJobByCollectionFromWs. Input: "
				+ params.getQueryRef() + ", " + params.getTargetRef()
				+ ". Workspace: " + wsName + ".",
				new InitProgress().withPtype("task").withMax(5L),
				dateFormat.format(date));
		jobClient = null;

		if (deployAwe == false) {
			String result = MemeServerImpl
					.findSitesWithMastJobByCollectionFromWs(wsName, params,
							returnVal, authPart.toString());
			System.out.println(result);
		} else {
			// Call AWE
			String jsonArgs = formatAWEConfig(returnVal, wsName, params, authPart.toString());

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

			String result = submitJob(jsonArgs);
			reportAweStatus(authPart, returnVal, result);

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

	protected static String submitJob(String aweConfig) throws Exception {

		String postResponse = null;
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(
					AWE_SERVICE);
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			InputStream stream = new ByteArrayInputStream(
					aweConfig.getBytes("UTF-8"));
			builder.addBinaryBody("upload", stream,
					ContentType.APPLICATION_OCTET_STREAM, "bambi.awf");
			httpPost.setEntity(builder.build());
			HttpResponse response = httpClient.execute(httpPost);
			postResponse = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			throw new Exception("Can not submit AWE post request: "
					+ e.getMessage());
		}
		return postResponse;
	}

	protected static String formatAWEConfig(String jobId, String wsName,
			MemeRunParameters params, String token
			) throws Exception {

		String formattedConfig;
		try {
			String config = FileUtils.readFileToString(new File(
					AWF_CONFIG_FILE));
			String args = " --job "
					+ jobId + " --method find_motifs_with_meme_job_from_ws"
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
					+ "' --token '" + token	+ "'";

			formattedConfig = String.format(config, jobId, args, jobId);
		} catch (IOException e) {
			throw new Exception("Can not load AWE config file: "
					+ AWF_CONFIG_FILE);
		}
		return formattedConfig;
	}

	protected static String formatAWEConfig(String jobId, String wsName,
			TomtomRunParameters params, String token
			) throws Exception {

		String formattedConfig;
		try {
			String config = FileUtils.readFileToString(new File(
					AWF_CONFIG_FILE));
			String args = " --job " + jobId
					+ " --method compare_motifs_with_tomtom_job_by_collection_from_ws"
					+ " --ws '" + wsName
					+ "' --query '" + params.getQueryRef()
					+ "' --target '" + params.getTargetRef()
					+ "' --pspm '" + params.getPspmId()
					+ "' --thresh '" + params.getThresh().toString()
					+ "' --dist '" + params.getDist()
					+ "' --min_overlap '" + params.getMinOverlap().toString()
					+ "' --evalue '" + params.getEvalue().toString()
					+ "' --internal '" + params.getInternal().toString()
					+ "' --token '" + token + "'";

			formattedConfig = String.format(config, jobId, args, jobId);
		} catch (IOException e) {
			throw new Exception("Can not load AWE config file: "
					+ AWF_CONFIG_FILE);
		}
		return formattedConfig;
	}

	protected static String formatAWEConfig(String jobId, String wsName,
			MastRunParameters params, String token
			) throws Exception {

		String formattedConfig;
		try {
			String config = FileUtils.readFileToString(new File(
					AWF_CONFIG_FILE));
			String args = " --job " + jobId
					+ " --method find_sites_with_mast_job_by_collection_from_ws"
					+ " --ws '" + wsName
					+ "' --query '" + params.getQueryRef()
					+ "' --target '" + params.getTargetRef()
					+ "' --pspm '" + params.getPspmId()
					+ "' --thresh '" + params.getMt().toString()
					+ "' --token '" + token + "'";

			formattedConfig = String.format(config, jobId, args, jobId);
		} catch (IOException e) {
			throw new Exception("Can not load AWE config file: "
					+ AWF_CONFIG_FILE);
		}
		return formattedConfig;
	}

	protected static void reportAweStatus(AuthToken authPart, String returnVal,
			String result) throws IOException, JsonProcessingException,
			TokenFormatException, MalformedURLException, JsonClientException,
			JsonParseException, JsonMappingException, ServerException {
		JsonNode rootNode = new ObjectMapper().registerModule(new JacksonTupleModule()).readTree(result);
		String aweId = "";
		if (rootNode.has("data")){
			JsonNode dataNode = rootNode.get("data");
			if (dataNode.has("id")){
				aweId = AWE_SERVICE + "/" + dataNode.get("id").textValue();
				System.out.println(aweId);
				updateJobProgress(returnVal, "AWE job submitted: " + aweId, 1L, authPart.toString());
			}
		}
		if (rootNode.has("error")){
			if (rootNode.get("error").textValue()!=null){
				System.out.println(rootNode.get("error").textValue());
				updateJobProgress(returnVal, "AWE reported error on job " + aweId, 1L, authPart.toString());
				throw new ServerException(rootNode.get("error").textValue(), 0, "Unknown", null);
			}
		}
	}

	public static MemePSPMCollection getPspmCollectionFromMemeResult(
			MemeRunResult memeRunResult) throws Exception {
		MemePSPMCollection returnVal = null;
		returnVal = MemeServerImpl
				.getPspmCollectionFromMemeResult(memeRunResult);
		return returnVal;
	}

	public static String getPspmCollectionFromMemeResultFromWs(String wsId,
			String memeRunResultRef, AuthToken authPart) throws Exception {
		String returnVal = null;
		returnVal = MemeServerImpl.getPspmCollectionFromMemeResultFromWs(wsId,
				memeRunResultRef, authPart.toString());
		return returnVal;
	}

	public static String getPspmCollectionFromMemeResultJobFromWs(String wsId,
			String memeRunResultRef, AuthToken authPart) throws Exception {
		String returnVal = null;
		URL jobServiceUrl = new URL(JOB_SERVICE);
		UserAndJobStateClient jobClient = new UserAndJobStateClient(
				jobServiceUrl, authPart);
		jobClient.setAuthAllowedForHttp(true);
		returnVal = jobClient.createJob();

		String result = MemeServerImpl
				.getPspmCollectionFromMemeJobResultFromWs(wsId,
						memeRunResultRef, returnVal, authPart.toString());
		System.out.println(result);
		return returnVal;
	}

	protected static void updateJobProgress(String jobId, String status,
			Long tasks, String token) throws TokenFormatException,
			MalformedURLException, IOException, JsonClientException {
		Date date = new Date();
		date.setTime(date.getTime() + 10000L);
		UserAndJobStateClient jobClient = new UserAndJobStateClient(new URL(
				JOB_SERVICE), new AuthToken(token));
		// jobClient.setAuthAllowedForHttp(true);
		jobClient.updateJobProgress(jobId, token, status, tasks,
				dateFormat.format(date));
		jobClient = null;
	}

}
