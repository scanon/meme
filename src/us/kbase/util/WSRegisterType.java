package us.kbase.util;

import java.net.MalformedURLException;

import us.kbase.JsonClientCaller;
import us.kbase.auth.AuthToken;
import us.kbase.workspaceservice.AddTypeParams;
import us.kbase.workspaceservice.WorkspaceserviceClient;

public class WSRegisterType {
	
/*	Examples:
    WSRegisterType registerMotifMeme = new WSRegisterType("MotifMeme");
	WSRegisterType registerSiteMeme = new WSRegisterType("SiteMeme");
	WSRegisterType registerMotifCollectionMeme = new WSRegisterType("MotifCollectionMeme");
	WSRegisterType registerSequence = new WSRegisterType("Sequence");
	WSRegisterType registerSequenceSet = new WSRegisterType("SequenceSet");
	WSRegisterType registerHitMast = new WSRegisterType("HitMast");
	WSRegisterType registerHitTomtom = new WSRegisterType("HitTomtom");
    WSRegisterType registerExpressionDataCollection = new WSRegisterType("ExpressionDataCollection");
    WSRegisterType registerExpressionDataPoint = new WSRegisterType("ExpressionDataPoint");
    WSRegisterType registerExpressionDataSet = new WSRegisterType("ExpressionDataSet");
    WSRegisterType registerCmonkeyRun = new WSRegisterType("CmonkeyRun");
    WSRegisterType registerCmonkeyNetwork = new WSRegisterType("CmonkeyNetwork");
    WSRegisterType registerCmonkeyCluster = new WSRegisterType("CmonkeyCluster");
    WSRegisterType registerMotifCmonkey = new WSRegisterType("MotifCmonkey");

*/		

	public static final String workspaceClientUrl = "http://kbase.us/services/workspace/";
	public static final String userName = "aktest";
	public static final String password = "1475rokegi";
	public static final String workspaceName = "AKtest";
	
	private static WorkspaceserviceClient _wsClient = null;
	private static AuthToken _token = null;
	
	public WSRegisterType (String dataType) throws Exception{
		Integer i = registerDataType(dataType);
		System.out.println(String.valueOf(i));
	}
	
	public static WorkspaceserviceClient wsClient() throws MalformedURLException{
		if(_wsClient == null)
		{
			_wsClient = new WorkspaceserviceClient(workspaceClientUrl);
		}
		return _wsClient;
	}
	
	public static AuthToken authToken() throws Exception{
		if(_token == null){
			_token = JsonClientCaller.requestTokenFromKBase(userName, password.toCharArray()); 
		}
		return _token; 	  
	}
	
	
	public static Integer registerDataType(String dataType) throws Exception{
		AddTypeParams typeParams = new AddTypeParams();
		System.out.println("Registering data type: "+dataType);
		typeParams.setType(dataType);
		typeParams.setAuth( authToken().toString() );
		
		return wsClient().addType(typeParams);
	}

}
