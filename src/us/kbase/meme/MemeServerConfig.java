package us.kbase.meme;

public class MemeServerConfig {
	
	//Set DEPLOY_CLUSTER to false before deployment if you do not wish to use cluster service  
	protected static final boolean DEPLOY_AWE = true;

	//Service URLs
	public static final String JOB_SERVICE = "https://kbase.us/services/userandjobstate";//dev:"http://140.221.84.180:7083";
	public static final String CLUSTER_SERVICE = "http://140.221.85.171:7080/job";//"https://198.128.58.82/services/cs_test/jobs";
	public static final String ID_SERVICE_URL = "http://kbase.us/services/idserver";
	public static final String WS_SERVICE_URL = "https://kbase.us/services/ws";//dev: "http://140.221.84.209:7058";


	//File and directory paths
	//Where temporary files would be created? 
	protected static final String WORK_DIRECTORY = "/var/tmp/meme"; 
	protected static final String AWF_CONFIG_FILE = "/kb/deployment/services/meme/meme.awf";

	//Other options

	//Writes all JSON calls to AWE client and all AWE responses to /var/tmp/meme/meme-awe.log
	//This is a serious security threat because log will contain all auth tokens
	//SET IT TO FALSE ON PRODUCTION  
	public static final boolean LOG_AWE_CALLS = false;

	protected static final String MAST_RUN_RESULT_TYPE = "MEME.MastRunResult";
	protected static final String MEME_PSPM_COLLECTION_TYPE = "MEME.MemePSPMCollection";
	protected static final String TOMTOM_RUN_RESULT_TYPE = "MEME.TomtomRunResult";
	protected static final String MEME_RUN_RESULT_TYPE = "MEME.MemeRunResult";

}
