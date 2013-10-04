
package us.kbase.meme;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;


/**
 * <p>Original spec-file type: TomtomRunParameters</p>
 * <pre>
 * Contains parameters of a TOMTOM run
 * float thresh - thresh parameter of TOMTOM run, must be smaller than or equal to 1.0 unless evalueTomtom == 1
 * int evalue - evalue parameter of TOMTOM run (accepable values are "0" and "1")
 * string dist - value of dist parameter of TOMTOM run (accepable values are "allr", "ed", "kullback", "pearson", "sandelin")
 * int internal - internal parameter of TOMTOM run (accepable values are "0" and "1")
 * int min_overlap - value of min-overlap parameter of TOMTOM run. In case a query motif is smaller than minOverlapTomtom specified, then the motif's width is used as the minimum overlap for that query.
 * </pre>
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "thresh",
    "evalue",
    "dist",
    "internal",
    "min_overlap"
})
public class TomtomRunParameters {

    @JsonProperty("thresh")
    private Double thresh;
    @JsonProperty("evalue")
    private Integer evalue;
    @JsonProperty("dist")
    private String dist;
    @JsonProperty("internal")
    private Integer internal;
    @JsonProperty("min_overlap")
    private Integer minOverlap;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("thresh")
    public Double getThresh() {
        return thresh;
    }

    @JsonProperty("thresh")
    public void setThresh(Double thresh) {
        this.thresh = thresh;
    }

    public TomtomRunParameters withThresh(Double thresh) {
        this.thresh = thresh;
        return this;
    }

    @JsonProperty("evalue")
    public Integer getEvalue() {
        return evalue;
    }

    @JsonProperty("evalue")
    public void setEvalue(Integer evalue) {
        this.evalue = evalue;
    }

    public TomtomRunParameters withEvalue(Integer evalue) {
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

    public TomtomRunParameters withDist(String dist) {
        this.dist = dist;
        return this;
    }

    @JsonProperty("internal")
    public Integer getInternal() {
        return internal;
    }

    @JsonProperty("internal")
    public void setInternal(Integer internal) {
        this.internal = internal;
    }

    public TomtomRunParameters withInternal(Integer internal) {
        this.internal = internal;
        return this;
    }

    @JsonProperty("min_overlap")
    public Integer getMinOverlap() {
        return minOverlap;
    }

    @JsonProperty("min_overlap")
    public void setMinOverlap(Integer minOverlap) {
        this.minOverlap = minOverlap;
    }

    public TomtomRunParameters withMinOverlap(Integer minOverlap) {
        this.minOverlap = minOverlap;
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
