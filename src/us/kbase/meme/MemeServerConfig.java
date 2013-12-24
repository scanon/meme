package us.kbase.meme;

public class MemeServerConfig {
	
	//Set DEPLOY_CLUSTER to false before deployment if you do not wish to use cluster service  
	protected static final boolean DEPLOY_CLUSTER = false;

	//Service URLs
	protected static final String JOB_SERVICE = "http://140.221.84.180:7083";
	protected static final String CLUSTER_SERVICE = "https://198.128.58.82/services/cs_test/jobs";
	protected static final String ID_SERVICE_URL = "http://kbase.us/services/idserver";
	protected static final String WS_SERVICE_URL = "http://140.221.84.209:7058";

	//File and directory paths
	//Where temporary files would be created? 
	protected static final String WORK_DIRECTORY = "/var/tmp/meme"; 

	//Other options
	//NEVER EVER BYPASS SSL CERTIFICATE CHECK IN PRODUCTION (unless you have to deal with expired ANL certificate)
	protected static final boolean BYPASS_HTTPS = true;


}
