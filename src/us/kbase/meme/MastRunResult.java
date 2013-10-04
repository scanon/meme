
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
 * <p>Original spec-file type: MastRunResult</p>
 * <pre>
 * Represents result of a single MAST run
 * string id - KBase ID of MastRunResult
 * string timestamp - timestamp for creation time of MastRunResult
 * float mt - mt parameter of MAST run (p-value threshold)
 * list<MastHit> hits - A list of all hits found by MAST
 * </pre>
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "id",
    "timestamp",
    "mt",
    "hits"
})
public class MastRunResult {

    @JsonProperty("id")
    private String id;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("mt")
    private Double mt;
    @JsonProperty("hits")
    private List<MastHit> hits = new ArrayList<MastHit>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public MastRunResult withId(String id) {
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

    public MastRunResult withTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @JsonProperty("mt")
    public Double getMt() {
        return mt;
    }

    @JsonProperty("mt")
    public void setMt(Double mt) {
        this.mt = mt;
    }

    public MastRunResult withMt(Double mt) {
        this.mt = mt;
        return this;
    }

    @JsonProperty("hits")
    public List<MastHit> getHits() {
        return hits;
    }

    @JsonProperty("hits")
    public void setHits(List<MastHit> hits) {
        this.hits = hits;
    }

    public MastRunResult withHits(List<MastHit> hits) {
        this.hits = hits;
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
