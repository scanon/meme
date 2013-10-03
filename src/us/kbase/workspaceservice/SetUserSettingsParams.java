
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
 * Input parameters for the "set_user_settings" function.
 * 
 *         string setting - the setting to be set (an essential argument)
 *         string value - new value to be set (an essential argument)
 *         string auth - the authentication token of the KBase account changing workspace permissions; must have 'admin' privelages to workspace (an optional argument; user is "public" if auth is not provided)
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "setting",
    "value",
    "auth"
})
public class SetUserSettingsParams {

    @JsonProperty("setting")
    private String setting;
    @JsonProperty("value")
    private String value;
    @JsonProperty("auth")
    private String auth;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("setting")
    public String getSetting() {
        return setting;
    }

    @JsonProperty("setting")
    public void setSetting(String setting) {
        this.setting = setting;
    }

    public SetUserSettingsParams withSetting(String setting) {
        this.setting = setting;
        return this;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    public SetUserSettingsParams withValue(String value) {
        this.value = value;
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

    public SetUserSettingsParams withAuth(String auth) {
        this.auth = auth;
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
