package us.kbase.meme;

public class MemeServerConfig {
	
	//Set DEPLOY_CLUSTER to false before deployment if you do not wish to use cluster service  
	protected static final boolean DEPLOY_AWE = true;
	
	//Service credentials
	protected static final String SERVICE_LOGIN = "meme";
	protected static final String SERVICE_PASSWORD = "CheburashkaiKrokodilGena1969";

	//Service URLs
	public static String JOB_SERVICE_URL = "https://kbase.us/services/userandjobstate";//dev:"http://140.221.84.180:7083";
	public static String AWE_SERVICE_URL = "http://140.221.85.171:7080/job";
	public static String ID_SERVICE_URL = "http://kbase.us/services/idserver";
	public static String WS_SERVICE_URL = "https://kbase.us/services/ws";//dev: "http://140.221.84.209:7058";


	//File and directory paths
	//Where temporary files would be created? 
	protected static String WORK_DIRECTORY;// = "/var/tmp/meme"; 
	protected static String AWF_CONFIG_FILE = "/kb/deployment/services/meme/meme.awf";

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
