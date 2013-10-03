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
 * Original spec-file type: Sequence
 * </p>
 * 
 * <pre>
 * Represents a particular sequence from sequence set
 * string sequenceId - sequence identifier,  must be unique in SequenceSet
 * string sequence - nucleotide sequence
 * </pre>
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({ "sequenceId", "sequence" })
public class Sequence {

	@JsonProperty("sequenceId")
	private String sequenceId;
	@JsonProperty("sequence")
	private String sequence;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("sequenceId")
	public String getSequenceId() {
		return sequenceId;
	}

	@JsonProperty("sequenceId")
	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}

	public Sequence withSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
		return this;
	}

	@JsonProperty("sequence")
	public String getSequence() {
		return sequence;
	}

	@JsonProperty("sequence")
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public Sequence withSequence(String sequence) {
		this.sequence = sequence;
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
