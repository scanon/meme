package us.kbase.kbasememe;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * <p>
 * Original spec-file type: hitTomtom
 * </p>
 * 
 * <pre>
 * Represents a particluar TOMTOM hit
 * string queryMotifName - name of query motif
 * string targetMotifName - name of target motif
 * int optimalOffset - Optimal offset: the offset between the query and the target motif
 * float pValue - p-value
 * float eValue - E-value
 * float qValue - q-value
 * int overlap - Overlap: the number of positions of overlap between the two motifs.
 * string queryConsensus - Query consensus sequence.
 * string targetConsensus - Target consensus sequence.
 * string strand - Orientation: Orientation of target motif with respect to query motif.
 * </pre>
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({ "queryMotifName", "targetMotifName", "optimalOffset",
		"pValue", "eValue", "qValue", "overlap", "queryConsensus",
		"targetConsensus", "strand" })
public class HitTomtom {

	@JsonProperty("queryMotifName")
	private String queryMotifName;
	@JsonProperty("targetMotifName")
	private String targetMotifName;
	@JsonProperty("optimalOffset")
	private Integer optimalOffset;
	@JsonProperty("pValue")
	private Double pValue;
	@JsonProperty("eValue")
	private Double eValue;
	@JsonProperty("qValue")
	private Double qValue;
	@JsonProperty("overlap")
	private Integer overlap;
	@JsonProperty("queryConsensus")
	private String queryConsensus;
	@JsonProperty("targetConsensus")
	private String targetConsensus;
	@JsonProperty("strand")
	private String strand;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("queryMotifName")
	public String getQueryMotifName() {
		return queryMotifName;
	}

	@JsonProperty("queryMotifName")
	public void setQueryMotifName(String queryMotifName) {
		this.queryMotifName = queryMotifName;
	}

	public HitTomtom withQueryMotifName(String queryMotifName) {
		this.queryMotifName = queryMotifName;
		return this;
	}

	@JsonProperty("targetMotifName")
	public String getTargetMotifName() {
		return targetMotifName;
	}

	@JsonProperty("targetMotifName")
	public void setTargetMotifName(String targetMotifName) {
		this.targetMotifName = targetMotifName;
	}

	public HitTomtom withTargetMotifName(String targetMotifName) {
		this.targetMotifName = targetMotifName;
		return this;
	}

	@JsonProperty("optimalOffset")
	public Integer getOptimalOffset() {
		return optimalOffset;
	}

	@JsonProperty("optimalOffset")
	public void setOptimalOffset(Integer optimalOffset) {
		this.optimalOffset = optimalOffset;
	}

	public HitTomtom withOptimalOffset(Integer optimalOffset) {
		this.optimalOffset = optimalOffset;
		return this;
	}

	@JsonProperty("pValue")
	public Double getPValue() {
		return pValue;
	}

	@JsonProperty("pValue")
	public void setPValue(Double pValue) {
		this.pValue = pValue;
	}

	public HitTomtom withPValue(Double pValue) {
		this.pValue = pValue;
		return this;
	}

	@JsonProperty("eValue")
	public Double getEValue() {
		return eValue;
	}

	@JsonProperty("eValue")
	public void setEValue(Double eValue) {
		this.eValue = eValue;
	}

	public HitTomtom withEValue(Double eValue) {
		this.eValue = eValue;
		return this;
	}

	@JsonProperty("qValue")
	public Double getQValue() {
		return qValue;
	}

	@JsonProperty("qValue")
	public void setQValue(Double qValue) {
		this.qValue = qValue;
	}

	public HitTomtom withQValue(Double qValue) {
		this.qValue = qValue;
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

	public HitTomtom withOverlap(Integer overlap) {
		this.overlap = overlap;
		return this;
	}

	@JsonProperty("queryConsensus")
	public String getQueryConsensus() {
		return queryConsensus;
	}

	@JsonProperty("queryConsensus")
	public void setQueryConsensus(String queryConsensus) {
		this.queryConsensus = queryConsensus;
	}

	public HitTomtom withQueryConsensus(String queryConsensus) {
		this.queryConsensus = queryConsensus;
		return this;
	}

	@JsonProperty("targetConsensus")
	public String getTargetConsensus() {
		return targetConsensus;
	}

	@JsonProperty("targetConsensus")
	public void setTargetConsensus(String targetConsensus) {
		this.targetConsensus = targetConsensus;
	}

	public HitTomtom withTargetConsensus(String targetConsensus) {
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

	public HitTomtom withStrand(String strand) {
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
