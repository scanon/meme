package us.kbase.util;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import us.kbase.JsonClientCaller;
import us.kbase.Tuple11;
import us.kbase.auth.AuthToken;
import us.kbase.workspaceservice.AddTypeParams;
import us.kbase.workspaceservice.SaveObjectParams;
import us.kbase.workspaceservice.WorkspaceserviceClient;

public class WSUtil {
	
	public static final String workspaceClientUrl = "http://kbase.us/services/workspace/";
	public static final String userName = "aktest";
	public static final String password = "1475rokegi";
	public static final String workspaceName = "AKtest";
	
	private static WorkspaceserviceClient _wsClient = null;
	private static AuthToken _token = null;
	
	
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
		typeParams.setType(dataType);
		typeParams.setAuth( authToken().toString() );
		
		return wsClient().addType(typeParams);
	}

	public static Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> saveObject(String id, Object data, boolean registerType) throws Exception {
		String dataType = data.getClass().getSimpleName();
		
		if(registerType){
			System.out.print("Registering data type " + dataType + ": ");
			try{
				Integer ret = registerDataType(dataType);
				System.out.println(ret);
			}
			catch(Exception e){
				System.out.println(" false...");
			}
		}
		
		SaveObjectParams params = new SaveObjectParams();
		params.setAuth(authToken().toString());
		params.setCompressed(0);
		params.setData(data);
		params.setId(id); 
		params.setJson(0); 
		params.setType(dataType);
		
		Map<String, String> metadata = new HashMap<String, String>();
		params.setMetadata(metadata);
		
		params.setWorkspace(workspaceName);
		Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> ret = wsClient().saveObject(params);
		
		System.out.println("Saving object:");
		System.out.println(ret.getE1());
		System.out.println(ret.getE2());
		System.out.println(ret.getE3());
		System.out.println(ret.getE4());
		System.out.println(ret.getE5());
		System.out.println(ret.getE6());
		System.out.println(ret.getE7());
		System.out.println(ret.getE8());
		System.out.println(ret.getE9());
		System.out.println(ret.getE10());
		System.out.println(ret.getE11());
				
		
		return ret;		
	}
}
