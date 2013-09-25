
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
 * Input parameters for the "get_object_by_ref" function.
 * 
 *         workspace_ref reference - reference to a specific instance of a specific object in a workspace (an essential argument)
 *         string auth - the authentication token of the KBase account to associate with this object retrieval command (an optional argument; user is "public" if auth is not provided)
 *         bool2 asHash - a bool2ean indicating if metadata should be returned as a hash
 *         bool2 asJSON - indicates that data should be returned in JSON format (an optional argument; default is '0')
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "reference",
    "auth",
    "asHash",
    "asJSON"
})
public class GetObjectByRefParams {

    @JsonProperty("reference")
    private String reference;
    @JsonProperty("auth")
    private String auth;
    @JsonProperty("asHash")
    private Integer asHash;
    @JsonProperty("asJSON")
    private Integer asJSON;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("reference")
    public String getReference() {
        return reference;
    }

    @JsonProperty("reference")
    public void setReference(String reference) {
        this.reference = reference;
    }

    public GetObjectByRefParams withReference(String reference) {
        this.reference = reference;
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

    public GetObjectByRefParams withAuth(String auth) {
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

    public GetObjectByRefParams withAsHash(Integer asHash) {
        this.asHash = asHash;
        return this;
    }

    @JsonProperty("asJSON")
    public Integer getAsJSON() {
        return asJSON;
    }

    @JsonProperty("asJSON")
    public void setAsJSON(Integer asJSON) {
        this.asJSON = asJSON;
    }

    public GetObjectByRefParams withAsJSON(Integer asJSON) {
        this.asJSON = asJSON;
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
