
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
 * Input parameters for the "queue_job" function.
 * 
 *         string jobid - ID of the job to be queued (an essential argument)
 *         string auth - the authentication token of the KBase account queuing the job; must have access to the job being queued (an optional argument; user is "public" if auth is not provided)
 *         string state - the initial state to assign to the job being queued (an optional argument; default is "queued")
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "jobid",
    "auth",
    "state"
})
public class QueueJobParams {

    @JsonProperty("jobid")
    private String jobid;
    @JsonProperty("auth")
    private String auth;
    @JsonProperty("state")
    private String state;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("jobid")
    public String getJobid() {
        return jobid;
    }

    @JsonProperty("jobid")
    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    public QueueJobParams withJobid(String jobid) {
        this.jobid = jobid;
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

    public QueueJobParams withAuth(String auth) {
        this.auth = auth;
        return this;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    public QueueJobParams withState(String state) {
        this.state = state;
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
