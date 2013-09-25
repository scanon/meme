package us.kbase.workspaceservice;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.type.TypeReference;
import us.kbase.JsonClientCaller;
import us.kbase.Tuple11;
import us.kbase.Tuple6;

public class WorkspaceserviceClient {
    private JsonClientCaller caller;

    public WorkspaceserviceClient(String url) throws MalformedURLException {
        caller = new JsonClientCaller(url);
    }

    public Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> saveObject(SaveObjectParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>>> retType = new TypeReference<List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>>>() {};
        List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>> res = caller.jsonrpcCall("workspaceService.save_object", args, retType, true, false);
        return res.get(0);
    }

    public Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> deleteObject(DeleteObjectParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>>> retType = new TypeReference<List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>>>() {};
        List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>> res = caller.jsonrpcCall("workspaceService.delete_object", args, retType, true, false);
        return res.get(0);
    }

    public Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> deleteObjectPermanently(DeleteObjectPermanentlyParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>>> retType = new TypeReference<List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>>>() {};
        List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>> res = caller.jsonrpcCall("workspaceService.delete_object_permanently", args, retType, true, false);
        return res.get(0);
    }

    public GetObjectOutput getObject(GetObjectParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<GetObjectOutput>> retType = new TypeReference<List<GetObjectOutput>>() {};
        List<GetObjectOutput> res = caller.jsonrpcCall("workspaceService.get_object", args, retType, true, false);
        return res.get(0);
    }

    public GetObjectOutput getObjectByRef(GetObjectByRefParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<GetObjectOutput>> retType = new TypeReference<List<GetObjectOutput>>() {};
        List<GetObjectOutput> res = caller.jsonrpcCall("workspaceService.get_object_by_ref", args, retType, true, false);
        return res.get(0);
    }

    public Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> saveObjectByRef(SaveObjectByRefParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>>> retType = new TypeReference<List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>>>() {};
        List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>> res = caller.jsonrpcCall("workspaceService.save_object_by_ref", args, retType, true, false);
        return res.get(0);
    }

    public Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> getObjectmeta(GetObjectmetaParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>>> retType = new TypeReference<List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>>>() {};
        List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>> res = caller.jsonrpcCall("workspaceService.get_objectmeta", args, retType, true, false);
        return res.get(0);
    }

    public Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> getObjectmetaByRef(GetObjectmetaByRefParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>>> retType = new TypeReference<List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>>>() {};
        List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>> res = caller.jsonrpcCall("workspaceService.get_objectmeta_by_ref", args, retType, true, false);
        return res.get(0);
    }

    public Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> revertObject(RevertObjectParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>>> retType = new TypeReference<List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>>>() {};
        List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>> res = caller.jsonrpcCall("workspaceService.revert_object", args, retType, true, false);
        return res.get(0);
    }

    public Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> copyObject(CopyObjectParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>>> retType = new TypeReference<List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>>>() {};
        List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>> res = caller.jsonrpcCall("workspaceService.copy_object", args, retType, true, false);
        return res.get(0);
    }

    public Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> moveObject(MoveObjectParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>>> retType = new TypeReference<List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>>>() {};
        List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>> res = caller.jsonrpcCall("workspaceService.move_object", args, retType, true, false);
        return res.get(0);
    }

    public Integer hasObject(HasObjectParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<Integer>> retType = new TypeReference<List<Integer>>() {};
        List<Integer> res = caller.jsonrpcCall("workspaceService.has_object", args, retType, true, false);
        return res.get(0);
    }

    public List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>> objectHistory(ObjectHistoryParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>>>> retType = new TypeReference<List<List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>>>>() {};
        List<List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>>> res = caller.jsonrpcCall("workspaceService.object_history", args, retType, true, false);
        return res.get(0);
    }

    public Tuple6<String, String, String, Integer, String, String> createWorkspace(CreateWorkspaceParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<Tuple6<String, String, String, Integer, String, String>>> retType = new TypeReference<List<Tuple6<String, String, String, Integer, String, String>>>() {};
        List<Tuple6<String, String, String, Integer, String, String>> res = caller.jsonrpcCall("workspaceService.create_workspace", args, retType, true, false);
        return res.get(0);
    }

    public Tuple6<String, String, String, Integer, String, String> getWorkspacemeta(GetWorkspacemetaParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<Tuple6<String, String, String, Integer, String, String>>> retType = new TypeReference<List<Tuple6<String, String, String, Integer, String, String>>>() {};
        List<Tuple6<String, String, String, Integer, String, String>> res = caller.jsonrpcCall("workspaceService.get_workspacemeta", args, retType, true, false);
        return res.get(0);
    }

    public Map<String,String> getWorkspacepermissions(GetWorkspacepermissionsParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<Map<String,String>>> retType = new TypeReference<List<Map<String,String>>>() {};
        List<Map<String,String>> res = caller.jsonrpcCall("workspaceService.get_workspacepermissions", args, retType, true, false);
        return res.get(0);
    }

    public Tuple6<String, String, String, Integer, String, String> deleteWorkspace(DeleteWorkspaceParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<Tuple6<String, String, String, Integer, String, String>>> retType = new TypeReference<List<Tuple6<String, String, String, Integer, String, String>>>() {};
        List<Tuple6<String, String, String, Integer, String, String>> res = caller.jsonrpcCall("workspaceService.delete_workspace", args, retType, true, false);
        return res.get(0);
    }

    public Tuple6<String, String, String, Integer, String, String> cloneWorkspace(CloneWorkspaceParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<Tuple6<String, String, String, Integer, String, String>>> retType = new TypeReference<List<Tuple6<String, String, String, Integer, String, String>>>() {};
        List<Tuple6<String, String, String, Integer, String, String>> res = caller.jsonrpcCall("workspaceService.clone_workspace", args, retType, true, false);
        return res.get(0);
    }

    public List<Tuple6<String, String, String, Integer, String, String>> listWorkspaces(ListWorkspacesParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<List<Tuple6<String, String, String, Integer, String, String>>>> retType = new TypeReference<List<List<Tuple6<String, String, String, Integer, String, String>>>>() {};
        List<List<Tuple6<String, String, String, Integer, String, String>>> res = caller.jsonrpcCall("workspaceService.list_workspaces", args, retType, true, false);
        return res.get(0);
    }

    public List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>> listWorkspaceObjects(ListWorkspaceObjectsParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>>>> retType = new TypeReference<List<List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>>>>() {};
        List<List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>>> res = caller.jsonrpcCall("workspaceService.list_workspace_objects", args, retType, true, false);
        return res.get(0);
    }

    public Tuple6<String, String, String, Integer, String, String> setGlobalWorkspacePermissions(SetGlobalWorkspacePermissionsParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<Tuple6<String, String, String, Integer, String, String>>> retType = new TypeReference<List<Tuple6<String, String, String, Integer, String, String>>>() {};
        List<Tuple6<String, String, String, Integer, String, String>> res = caller.jsonrpcCall("workspaceService.set_global_workspace_permissions", args, retType, true, false);
        return res.get(0);
    }

    public Integer setWorkspacePermissions(SetWorkspacePermissionsParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<Integer>> retType = new TypeReference<List<Integer>>() {};
        List<Integer> res = caller.jsonrpcCall("workspaceService.set_workspace_permissions", args, retType, true, false);
        return res.get(0);
    }

    public UserSettings getUserSettings(GetUserSettingsParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<UserSettings>> retType = new TypeReference<List<UserSettings>>() {};
        List<UserSettings> res = caller.jsonrpcCall("workspaceService.get_user_settings", args, retType, true, false);
        return res.get(0);
    }

    public UserSettings setUserSettings(SetUserSettingsParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<UserSettings>> retType = new TypeReference<List<UserSettings>>() {};
        List<UserSettings> res = caller.jsonrpcCall("workspaceService.set_user_settings", args, retType, true, false);
        return res.get(0);
    }

    public Integer queueJob(QueueJobParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<Integer>> retType = new TypeReference<List<Integer>>() {};
        List<Integer> res = caller.jsonrpcCall("workspaceService.queue_job", args, retType, true, false);
        return res.get(0);
    }

    public Integer setJobStatus(SetJobStatusParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<Integer>> retType = new TypeReference<List<Integer>>() {};
        List<Integer> res = caller.jsonrpcCall("workspaceService.set_job_status", args, retType, true, false);
        return res.get(0);
    }

    public List<Object> getJobs(GetJobsParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<List<Object>>> retType = new TypeReference<List<List<Object>>>() {};
        List<List<Object>> res = caller.jsonrpcCall("workspaceService.get_jobs", args, retType, true, false);
        return res.get(0);
    }

    public List<String> getTypes() throws Exception {
        List<Object> args = new ArrayList<Object>();
        TypeReference<List<List<String>>> retType = new TypeReference<List<List<String>>>() {};
        List<List<String>> res = caller.jsonrpcCall("workspaceService.get_types", args, retType, true, false);
        return res.get(0);
    }

    public Integer addType(AddTypeParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<Integer>> retType = new TypeReference<List<Integer>>() {};
        List<Integer> res = caller.jsonrpcCall("workspaceService.add_type", args, retType, true, false);
        return res.get(0);
    }

    public Integer removeType(RemoveTypeParams params) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<Integer>> retType = new TypeReference<List<Integer>>() {};
        List<Integer> res = caller.jsonrpcCall("workspaceService.remove_type", args, retType, true, false);
        return res.get(0);
    }
}
