
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
 * <p>Original spec-file type: TomtomHit</p>
 * <pre>
 * Represents a particluar TOMTOM hit
 * string query_pspm_id - id of query MemePSPM
 * string target_pspm_id - id of target MemePSPM
 * int optimal_offset - Optimal offset: the offset between the query and the target motif
 * float pvalue - p-value
 * float evalue - E-value
 * float qvalue - q-value
 * int overlap - Overlap: the number of positions of overlap between the two motifs.
 * string query_consensus - Query consensus sequence.
 * string target_consensus - Target consensus sequence.
 * string strand - Orientation: Orientation of target motif with respect to query motif.
 * </pre>
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "query_pspm_id",
    "target_pspm_id",
    "optimal_offset",
    "pvalue",
    "evalue",
    "qvalue",
    "overlap",
    "query_consensus",
    "target_consensus",
    "strand"
})
public class TomtomHit {

    @JsonProperty("query_pspm_id")
    private String queryPspmId;
    @JsonProperty("target_pspm_id")
    private String targetPspmId;
    @JsonProperty("optimal_offset")
    private Integer optimalOffset;
    @JsonProperty("pvalue")
    private Double pvalue;
    @JsonProperty("evalue")
    private Double evalue;
    @JsonProperty("qvalue")
    private Double qvalue;
    @JsonProperty("overlap")
    private Integer overlap;
    @JsonProperty("query_consensus")
    private String queryConsensus;
    @JsonProperty("target_consensus")
    private String targetConsensus;
    @JsonProperty("strand")
    private String strand;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("query_pspm_id")
    public String getQueryPspmId() {
        return queryPspmId;
    }

    @JsonProperty("query_pspm_id")
    public void setQueryPspmId(String queryPspmId) {
        this.queryPspmId = queryPspmId;
    }

    public TomtomHit withQueryPspmId(String queryPspmId) {
        this.queryPspmId = queryPspmId;
        return this;
    }

    @JsonProperty("target_pspm_id")
    public String getTargetPspmId() {
        return targetPspmId;
    }

    @JsonProperty("target_pspm_id")
    public void setTargetPspmId(String targetPspmId) {
        this.targetPspmId = targetPspmId;
    }

    public TomtomHit withTargetPspmId(String targetPspmId) {
        this.targetPspmId = targetPspmId;
        return this;
    }

    @JsonProperty("optimal_offset")
    public Integer getOptimalOffset() {
        return optimalOffset;
    }

    @JsonProperty("optimal_offset")
    public void setOptimalOffset(Integer optimalOffset) {
        this.optimalOffset = optimalOffset;
    }

    public TomtomHit withOptimalOffset(Integer optimalOffset) {
        this.optimalOffset = optimalOffset;
        return this;
    }

    @JsonProperty("pvalue")
    public Double getPvalue() {
        return pvalue;
    }

    @JsonProperty("pvalue")
    public void setPvalue(Double pvalue) {
        this.pvalue = pvalue;
    }

    public TomtomHit withPvalue(Double pvalue) {
        this.pvalue = pvalue;
        return this;
    }

    @JsonProperty("evalue")
    public Double getEvalue() {
        return evalue;
    }

    @JsonProperty("evalue")
    public void setEvalue(Double evalue) {
        this.evalue = evalue;
    }

    public TomtomHit withEvalue(Double evalue) {
        this.evalue = evalue;
        return this;
    }

    @JsonProperty("qvalue")
    public Double getQvalue() {
        return qvalue;
    }

    @JsonProperty("qvalue")
    public void setQvalue(Double qvalue) {
        this.qvalue = qvalue;
    }

    public TomtomHit withQvalue(Double qvalue) {
        this.qvalue = qvalue;
        return this;
    }

    @JsonProperty("overlap")
    public Integer getOverlap() {
        return overlap;
    }

    @JsonProperty("overlap")
    public void setOverlap(Integer overlap) {
        this.overlap = overlap;
    }

    public TomtomHit withOverlap(Integer overlap) {
        this.overlap = overlap;
        return this;
    }

    @JsonProperty("query_consensus")
    public String getQueryConsensus() {
        return queryConsensus;
    }

    @JsonProperty("query_consensus")
    public void setQueryConsensus(String queryConsensus) {
        this.queryConsensus = queryConsensus;
    }

    public TomtomHit withQueryConsensus(String queryConsensus) {
        this.queryConsensus = queryConsensus;
        return this;
    }

    @JsonProperty("target_consensus")
    public String getTargetConsensus() {
        return targetConsensus;
    }

    @JsonProperty("target_consensus")
    public void setTargetConsensus(String targetConsensus) {
        this.targetConsensus = targetConsensus;
    }

    public TomtomHit withTargetConsensus(String targetConsensus) {
        this.targetConsensus = targetConsensus;
        return this;
    }

    @JsonProperty("strand")
    public String getStrand() {
        return strand;
    }

    @JsonProperty("strand")
    public void setStrand(String strand) {
        this.strand = strand;
    }

    public TomtomHit withStrand(String strand) {
        this.strand = strand;
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
