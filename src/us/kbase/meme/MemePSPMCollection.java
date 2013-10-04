
package us.kbase.meme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;


/**
 * <p>Original spec-file type: MemePSPMCollection</p>
 * <pre>
 * Represents collection of MemePSPMs
 * string id - KBase ID of MotifPSPMMeme
 * string timestamp - timestamp for creation time of collection
 * string description - user's description of the collection
 * string alphabet - ALPHABET field of MEME output ("ACGT" for nucleotide motifs)
 * list<MemeMotif> pspms - A list of all MemePSPMs in a collection
 * </pre>
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "id",
    "timestamp",
    "description",
    "alphabet",
    "pspms"
})
public class MemePSPMCollection {

    @JsonProperty("id")
    private String id;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("description")
    private String description;
    @JsonProperty("alphabet")
    private String alphabet;
    @JsonProperty("pspms")
    private List<MemePSPM> pspms = new ArrayList<MemePSPM>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public MemePSPMCollection withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public MemePSPMCollection withTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public MemePSPMCollection withDescription(String description) {
        this.description = description;
        return this;
    }

    @JsonProperty("alphabet")
    public String getAlphabet() {
        return alphabet;
    }

    @JsonProperty("alphabet")
    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    public MemePSPMCollection withAlphabet(String alphabet) {
        this.alphabet = alphabet;
        return this;
    }

    @JsonProperty("pspms")
    public List<MemePSPM> getPspms() {
        return pspms;
    }

    @JsonProperty("pspms")
    public void setPspms(List<MemePSPM> pspms) {
        this.pspms = pspms;
    }

    public MemePSPMCollection withPspms(List<MemePSPM> pspms) {
        this.pspms = pspms;
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
