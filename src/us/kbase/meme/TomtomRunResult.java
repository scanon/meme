
package us.kbase.meme;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * <p>Original spec-file type: TomtomRunResult</p>
 * <pre>
 * Represents result of a single TOMTOM run
 * string id - KBase ID of TomtomRunResult
 * string timestamp - timestamp for creation time of TomtomRunResult
 * float thresh - thresh parameter of TOMTOM run
 * int evalue - evalue parameter of TOMTOM run
 * string dist - dist parameter of TOMTOM run (accepable values are "allr", "ed", "kullback", "pearson", "sandelin")
 * int internal - internal parameter of TOMTOM run ("0" or "1")
 * int min_overlap - min-overlap parameter of TOMTOM run
 * list<TomtomHit> hits - A list of all hits found by TOMTOM
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "id",
    "timestamp",
    "thresh",
    "evalue",
    "dist",
    "internal",
    "min_overlap",
    "hits"
})
public class TomtomRunResult {

    @JsonProperty("id")
    private String id;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("thresh")
    private Double thresh;
    @JsonProperty("evalue")
    private Long evalue;
    @JsonProperty("dist")
    private String dist;
    @JsonProperty("internal")
    private Long internal;
    @JsonProperty("min_overlap")
    private Long minOverlap;
    @JsonProperty("hits")
    private List<TomtomHit> hits;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public TomtomRunResult withId(String id) {
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

    public TomtomRunResult withTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @JsonProperty("thresh")
    public Double getThresh() {
        return thresh;
    }

    @JsonProperty("thresh")
    public void setThresh(Double thresh) {
        this.thresh = thresh;
    }

    public TomtomRunResult withThresh(Double thresh) {
        this.thresh = thresh;
        return this;
    }

    @JsonProperty("evalue")
    public Long getEvalue() {
        return evalue;
    }

    @JsonProperty("evalue")
    public void setEvalue(Long evalue) {
        this.evalue = evalue;
    }

    public TomtomRunResult withEvalue(Long evalue) {
        this.evalue = evalue;
        return this;
    }

    @JsonProperty("dist")
    public String getDist() {
        return dist;
    }

    @JsonProperty("dist")
    public void setDist(String dist) {
        this.dist = dist;
    }

    public TomtomRunResult withDist(String dist) {
        this.dist = dist;
        return this;
    }

    @JsonProperty("internal")
    public Long getInternal() {
        return internal;
    }

    @JsonProperty("internal")
    public void setInternal(Long internal) {
        this.internal = internal;
    }

    public TomtomRunResult withInternal(Long internal) {
        this.internal = internal;
        return this;
    }

    @JsonProperty("min_overlap")
    public Long getMinOverlap() {
        return minOverlap;
    }

    @JsonProperty("min_overlap")
    public void setMinOverlap(Long minOverlap) {
        this.minOverlap = minOverlap;
    }

    public TomtomRunResult withMinOverlap(Long minOverlap) {
        this.minOverlap = minOverlap;
        return this;
    }

    @JsonProperty("hits")
    public List<TomtomHit> getHits() {
        return hits;
    }

    @JsonProperty("hits")
    public void setHits(List<TomtomHit> hits) {
        this.hits = hits;
    }

    public TomtomRunResult withHits(List<TomtomHit> hits) {
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

    @Override
    public String toString() {
        return ((((((((((((((((((("TomtomRunResult"+" [id=")+ id)+", timestamp=")+ timestamp)+", thresh=")+ thresh)+", evalue=")+ evalue)+", dist=")+ dist)+", internal=")+ internal)+", minOverlap=")+ minOverlap)+", hits=")+ hits)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
