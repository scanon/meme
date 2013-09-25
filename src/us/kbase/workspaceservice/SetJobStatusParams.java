
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
 * Input parameters for the "set_job_status" function.
 * 
 *         string jobid - ID of the job to be have status changed (an essential argument)
 *         string status - Status to which job should be changed; accepted values are 'queued', 'running', and 'done' (an essential argument)
 *         string auth - the authentication token of the KBase account requesting job status; only status for owned jobs can be retrieved (an optional argument; user is "public" if auth is not provided)
 *         string currentStatus - Indicates the current statues of the selected job (an optional argument; default is "undef")
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "jobid",
    "status",
    "auth",
    "currentStatus"
})
public class SetJobStatusParams {

    @JsonProperty("jobid")
    private String jobid;
    @JsonProperty("status")
    private String status;
    @JsonProperty("auth")
    private String auth;
    @JsonProperty("currentStatus")
    private String currentStatus;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("jobid")
    public String getJobid() {
        return jobid;
    }

    @JsonProperty("jobid")
    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    public SetJobStatusParams withJobid(String jobid) {
        this.jobid = jobid;
        return this;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public SetJobStatusParams withStatus(String status) {
        this.status = status;
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

    public SetJobStatusParams withAuth(String auth) {
        this.auth = auth;
        return this;
    }

    @JsonProperty("currentStatus")
    public String getCurrentStatus() {
        return currentStatus;
    }

    @JsonProperty("currentStatus")
    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public SetJobStatusParams withCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
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
