package us.kbase.meme;

public class MemeServerConfig {
	
	//Set DEPLOY_CLUSTER to false before deployment if you do not wish to use cluster service  
	protected static final boolean DEPLOY_AWE = true;

	//Service URLs
	public static final String JOB_SERVICE = "https://kbase.us/services/userandjobstate";//dev:"http://140.221.84.180:7083";
	public static final String CLUSTER_SERVICE = "http://140.221.85.171:7080/job";//"https://198.128.58.82/services/cs_test/jobs";
	public static final String ID_SERVICE_URL = "http://kbase.us/services/idserver";
	public static final String WS_SERVICE_URL = "http://140.221.84.209:7058";
	public static final String SHOCK_URL = "http://140.221.84.236:8000";


	//File and directory paths
	//Where temporary files would be created? 
	protected static final String WORK_DIRECTORY = "/var/tmp/meme"; 

	//Other options
	//NEVER EVER BYPASS SSL CERTIFICATE CHECK IN PRODUCTION (unless you have to deal with expired ANL certificate)
	protected static final boolean BYPASS_HTTPS = true;

	//Writes all JSON calls to AWE client and all AWE responses to /var/tmp/meme/meme-awe.log
	//This is a serious security threat because log will contain all auth tokens
	//SET IT TO FALSE ON PRODUCTION  
	public static final boolean LOG_AWE_CALLS = true;

}
