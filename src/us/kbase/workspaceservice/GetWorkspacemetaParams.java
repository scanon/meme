
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
 * Input parameters for the "get_workspacemeta" function.
 * 
 *         workspace_id workspace - ID of the workspace for which metadata should be returned (an essential argument)
 *         string auth - the authentication token of the KBase account accessing workspace metadata (an optional argument; user is "public" if auth is not provided)
 *         bool2 asHash - a bool2ean indicating if metadata should be returned as a hash
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "workspace",
    "auth",
    "asHash"
})
public class GetWorkspacemetaParams {

    @JsonProperty("workspace")
    private String workspace;
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

    public GetWorkspacemetaParams withWorkspace(String workspace) {
        this.workspace = workspace;
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

    public GetWorkspacemetaParams withAuth(String auth) {
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

    public GetWorkspacemetaParams withAsHash(Integer asHash) {
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
