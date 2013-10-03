
package us.kbase.workspaceservice;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;


/**
 * Input parameters for the "list_workspace_objects" function.
 * 
 *         workspace_id workspace - ID of the workspace for which objects should be listed (an essential argument)
 *         string type - type of the objects to be listed (an optional argument; all object types will be listed if left unspecified)
 *         bool2 showDeletedObject - a flag that, if set to '1', causes any deleted objects to be included in the output (an optional argument; default is '0')
 *         string auth - the authentication token of the KBase account listing workspace objects; must have at least 'read' privelages (an optional argument; user is "public" if auth is not provided)
 *         bool2 asHash - a bool2ean indicating if metadata should be returned as a hash
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "workspace",
    "type",
    "showDeletedObject",
    "auth",
    "asHash"
})
public class ListWorkspaceObjectsParams {

    @JsonProperty("workspace")
    private String workspace;
    @JsonProperty("type")
    private String type;
    @JsonProperty("showDeletedObject")
    private Integer showDeletedObject;
    @JsonProperty("auth")
    private String auth;
    @JsonProperty("asHash")
    private Integer asHash;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("workspace")
    public String getWorkspace() {
        return workspace;
    }

    @JsonProperty("workspace")
    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

    public ListWorkspaceObjectsParams withWorkspace(String workspace) {
        this.workspace = workspace;
        return this;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public ListWorkspaceObjectsParams withType(String type) {
        this.type = type;
        return this;
    }

    @JsonProperty("showDeletedObject")
    public Integer getShowDeletedObject() {
        return showDeletedObject;
    }

    @JsonProperty("showDeletedObject")
    public void setShowDeletedObject(Integer showDeletedObject) {
        this.showDeletedObject = showDeletedObject;
    }

    public ListWorkspaceObjectsParams withShowDeletedObject(Integer showDeletedObject) {
        this.showDeletedObject = showDeletedObject;
        return this;
    }

    @JsonProperty("auth")
    public String getAuth() {
        return auth;
    }

    @JsonProperty("auth")
    public void setAuth(String auth) {
        this.auth = auth;
    }

    public ListWorkspaceObjectsParams withAuth(String auth) {
        this.auth = auth;
        return this;
    }

    @JsonProperty("asHash")
    public Integer getAsHash() {
        return asHash;
    }

    @JsonProperty("asHash")
    public void setAsHash(Integer asHash) {
        this.asHash = asHash;
    }

    public ListWorkspaceObjectsParams withAsHash(Integer asHash) {
        this.asHash = asHash;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
