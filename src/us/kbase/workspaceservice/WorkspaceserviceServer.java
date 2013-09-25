package us.kbase.workspaceservice;

import java.util.List;
import java.util.Map;
import us.kbase.JsonServerMethod;
import us.kbase.JsonServerServlet;
import us.kbase.Tuple11;
import us.kbase.Tuple6;

//BEGIN_HEADER
//END_HEADER

public class WorkspaceserviceServer extends JsonServerServlet {
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: <program> <server_port>");
            return;
        }
        new WorkspaceserviceServer().startupServer(Integer.parseInt(args[0]));
    }

    //BEGIN_CLASS_HEADER
    //END_CLASS_HEADER

    public WorkspaceserviceServer() throws Exception {
        //BEGIN_CONSTRUCTOR
        //END_CONSTRUCTOR
    }

    @JsonServerMethod(rpc = "workspaceService.save_object")
    public Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> saveObject(SaveObjectParams params) throws Exception {
        Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> ret = null;
        //BEGIN save_object
        //END save_object
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.delete_object")
    public Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> deleteObject(DeleteObjectParams params) throws Exception {
        Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> ret = null;
        //BEGIN delete_object
        //END delete_object
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.delete_object_permanently")
    public Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> deleteObjectPermanently(DeleteObjectPermanentlyParams params) throws Exception {
        Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> ret = null;
        //BEGIN delete_object_permanently
        //END delete_object_permanently
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.get_object")
    public GetObjectOutput getObject(GetObjectParams params) throws Exception {
        GetObjectOutput ret = null;
        //BEGIN get_object
        //END get_object
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.get_object_by_ref")
    public GetObjectOutput getObjectByRef(GetObjectByRefParams params) throws Exception {
        GetObjectOutput ret = null;
        //BEGIN get_object_by_ref
        //END get_object_by_ref
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.save_object_by_ref")
    public Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> saveObjectByRef(SaveObjectByRefParams params) throws Exception {
        Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> ret = null;
        //BEGIN save_object_by_ref
        //END save_object_by_ref
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.get_objectmeta")
    public Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> getObjectmeta(GetObjectmetaParams params) throws Exception {
        Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> ret = null;
        //BEGIN get_objectmeta
        //END get_objectmeta
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.get_objectmeta_by_ref")
    public Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> getObjectmetaByRef(GetObjectmetaByRefParams params) throws Exception {
        Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> ret = null;
        //BEGIN get_objectmeta_by_ref
        //END get_objectmeta_by_ref
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.revert_object")
    public Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> revertObject(RevertObjectParams params) throws Exception {
        Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> ret = null;
        //BEGIN revert_object
        //END revert_object
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.copy_object")
    public Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> copyObject(CopyObjectParams params) throws Exception {
        Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> ret = null;
        //BEGIN copy_object
        //END copy_object
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.move_object")
    public Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> moveObject(MoveObjectParams params) throws Exception {
        Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>> ret = null;
        //BEGIN move_object
        //END move_object
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.has_object")
    public Integer hasObject(HasObjectParams params) throws Exception {
        Integer ret = null;
        //BEGIN has_object
        //END has_object
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.object_history")
    public List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>> objectHistory(ObjectHistoryParams params) throws Exception {
        List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>> ret = null;
        //BEGIN object_history
        //END object_history
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.create_workspace")
    public Tuple6<String, String, String, Integer, String, String> createWorkspace(CreateWorkspaceParams params) throws Exception {
        Tuple6<String, String, String, Integer, String, String> ret = null;
        //BEGIN create_workspace
        //END create_workspace
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.get_workspacemeta")
    public Tuple6<String, String, String, Integer, String, String> getWorkspacemeta(GetWorkspacemetaParams params) throws Exception {
        Tuple6<String, String, String, Integer, String, String> ret = null;
        //BEGIN get_workspacemeta
        //END get_workspacemeta
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.get_workspacepermissions")
    public Map<String,String> getWorkspacepermissions(GetWorkspacepermissionsParams params) throws Exception {
        Map<String,String> ret = null;
        //BEGIN get_workspacepermissions
        //END get_workspacepermissions
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.delete_workspace")
    public Tuple6<String, String, String, Integer, String, String> deleteWorkspace(DeleteWorkspaceParams params) throws Exception {
        Tuple6<String, String, String, Integer, String, String> ret = null;
        //BEGIN delete_workspace
        //END delete_workspace
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.clone_workspace")
    public Tuple6<String, String, String, Integer, String, String> cloneWorkspace(CloneWorkspaceParams params) throws Exception {
        Tuple6<String, String, String, Integer, String, String> ret = null;
        //BEGIN clone_workspace
        //END clone_workspace
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.list_workspaces")
    public List<Tuple6<String, String, String, Integer, String, String>> listWorkspaces(ListWorkspacesParams params) throws Exception {
        List<Tuple6<String, String, String, Integer, String, String>> ret = null;
        //BEGIN list_workspaces
        //END list_workspaces
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.list_workspace_objects")
    public List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>> listWorkspaceObjects(ListWorkspaceObjectsParams params) throws Exception {
        List<Tuple11<String, String, String, Integer, String, String, String, String, String, String, Map<String,String>>> ret = null;
        //BEGIN list_workspace_objects
        //END list_workspace_objects
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.set_global_workspace_permissions")
    public Tuple6<String, String, String, Integer, String, String> setGlobalWorkspacePermissions(SetGlobalWorkspacePermissionsParams params) throws Exception {
        Tuple6<String, String, String, Integer, String, String> ret = null;
        //BEGIN set_global_workspace_permissions
        //END set_global_workspace_permissions
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.set_workspace_permissions")
    public Integer setWorkspacePermissions(SetWorkspacePermissionsParams params) throws Exception {
        Integer ret = null;
        //BEGIN set_workspace_permissions
        //END set_workspace_permissions
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.get_user_settings")
    public UserSettings getUserSettings(GetUserSettingsParams params) throws Exception {
        UserSettings ret = null;
        //BEGIN get_user_settings
        //END get_user_settings
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.set_user_settings")
    public UserSettings setUserSettings(SetUserSettingsParams params) throws Exception {
        UserSettings ret = null;
        //BEGIN set_user_settings
        //END set_user_settings
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.queue_job")
    public Integer queueJob(QueueJobParams params) throws Exception {
        Integer ret = null;
        //BEGIN queue_job
        //END queue_job
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.set_job_status")
    public Integer setJobStatus(SetJobStatusParams params) throws Exception {
        Integer ret = null;
        //BEGIN set_job_status
        //END set_job_status
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.get_jobs")
    public List<Object> getJobs(GetJobsParams params) throws Exception {
        List<Object> ret = null;
        //BEGIN get_jobs
        //END get_jobs
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.get_types")
    public List<String> getTypes() throws Exception {
        List<String> ret = null;
        //BEGIN get_types
        //END get_types
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.add_type")
    public Integer addType(AddTypeParams params) throws Exception {
        Integer ret = null;
        //BEGIN add_type
        //END add_type
        return ret;
    }

    @JsonServerMethod(rpc = "workspaceService.remove_type")
    public Integer removeType(RemoveTypeParams params) throws Exception {
        Integer ret = null;
        //BEGIN remove_type
        //END remove_type
        return ret;
    }
}
