
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
 * Input parameters for the "save_objects function.
 * 
 *         object_type type - type of the object to be saved (an essential argument)
 *         workspace_id workspace - ID of the workspace where the object is to be saved (an essential argument)
 *         object_id id - ID behind which the object will be saved in the workspace (an essential argument)
 *         ObjectData data - string or reference to complex datastructure to be saved in the workspace (an essential argument)
 *         string command - the name of the KBase command that is calling the "save_object" function (an optional argument with default "unknown")
 *         mapping<string,string> metadata - a hash of metadata to be associated with the object (an optional argument with default "{}")
 *         string auth - the authentication token of the KBase account to associate this save command (an optional argument, user is "public" if auth is not provided)
 *         bool2 retrieveFromURL - a flag indicating that the "data" argument contains a URL from which the actual data should be downloaded (an optional argument with default "0")
 *         bool2 json - a flag indicating if the input data is encoded as a JSON string (an optional argument with default "0")
 *         bool2 compressed - a flag indicating if the input data in zipped (an optional argument with default "0")
 *         bool2 asHash - a bool2ean indicating if metadata should be returned as a hash
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "id",
    "type",
    "data",
    "workspace",
    "command",
    "metadata",
    "auth",
    "json",
    "compressed",
    "retrieveFromURL",
    "asHash"
})
public class SaveObjectParams {

    @JsonProperty("id")
    private java.lang.String id;
    @JsonProperty("type")
    private java.lang.String type;
    @JsonProperty("data")
    private Object data;
    @JsonProperty("workspace")
    private java.lang.String workspace;
    @JsonProperty("command")
    private java.lang.String command;
    @JsonProperty("metadata")
    private Map<String, String> metadata;
    @JsonProperty("auth")
    private java.lang.String auth;
    @JsonProperty("json")
    private Integer json;
    @JsonProperty("compressed")
    private Integer compressed;
    @JsonProperty("retrieveFromURL")
    private Integer retrieveFromURL;
    @JsonProperty("asHash")
    private Integer asHash;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("id")
    public java.lang.String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(java.lang.String id) {
        this.id = id;
    }

    public SaveObjectParams withId(java.lang.String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("type")
    public java.lang.String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(java.lang.String type) {
        this.type = type;
    }

    public SaveObjectParams withType(java.lang.String type) {
        this.type = type;
        return this;
    }

    @JsonProperty("data")
    public Object getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(Object data) {
        this.data = data;
    }

    public SaveObjectParams withData(Object data) {
        this.data = data;
        return this;
    }

    @JsonProperty("workspace")
    public java.lang.String getWorkspace() {
        return workspace;
    }

    @JsonProperty("workspace")
    public void setWorkspace(java.lang.String workspace) {
        this.workspace = workspace;
    }

    public SaveObjectParams withWorkspace(java.lang.String workspace) {
        this.workspace = workspace;
        return this;
    }

    @JsonProperty("command")
    public java.lang.String getCommand() {
        return command;
    }

    @JsonProperty("command")
    public void setCommand(java.lang.String command) {
        this.command = command;
    }

    public SaveObjectParams withCommand(java.lang.String command) {
        this.command = command;
        return this;
    }

    @JsonProperty("metadata")
    public Map<String, String> getMetadata() {
        return metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public SaveObjectParams withMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }

    @JsonProperty("auth")
    public java.lang.String getAuth() {
        return auth;
    }

    @JsonProperty("auth")
    public void setAuth(java.lang.String auth) {
        this.auth = auth;
    }

    public SaveObjectParams withAuth(java.lang.String auth) {
        this.auth = auth;
        return this;
    }

    @JsonProperty("json")
    public Integer getJson() {
        return json;
    }

    @JsonProperty("json")
    public void setJson(Integer json) {
        this.json = json;
    }

    public SaveObjectParams withJson(Integer json) {
        this.json = json;
        return this;
    }

    @JsonProperty("compressed")
    public Integer getCompressed() {
        return compressed;
    }

    @JsonProperty("compressed")
    public void setCompressed(Integer compressed) {
        this.compressed = compressed;
    }

    public SaveObjectParams withCompressed(Integer compressed) {
        this.compressed = compressed;
        return this;
    }

    @JsonProperty("retrieveFromURL")
    public Integer getRetrieveFromURL() {
        return retrieveFromURL;
    }

    @JsonProperty("retrieveFromURL")
    public void setRetrieveFromURL(Integer retrieveFromURL) {
        this.retrieveFromURL = retrieveFromURL;
    }

    public SaveObjectParams withRetrieveFromURL(Integer retrieveFromURL) {
        this.retrieveFromURL = retrieveFromURL;
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

    public SaveObjectParams withAsHash(Integer asHash) {
        this.asHash = asHash;
        return this;
    }

    @JsonAnyGetter
    public Map<java.lang.String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(java.lang.String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
