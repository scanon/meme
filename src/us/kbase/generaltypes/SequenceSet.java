
package us.kbase.generaltypes;

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
 * <p>Original spec-file type: SequenceSet</p>
 * <pre>
 * Represents set of sequences
 * string sequence_set_id - identifier of sequence set
 * list<Sequence> sequences - sequences
 * </pre>
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "sequence_set_id",
    "sequences"
})
public class SequenceSet {

    @JsonProperty("sequence_set_id")
    private String sequenceSetId;
    @JsonProperty("sequences")
    private List<Sequence> sequences = new ArrayList<Sequence>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("sequence_set_id")
    public String getSequenceSetId() {
        return sequenceSetId;
    }

    @JsonProperty("sequence_set_id")
    public void setSequenceSetId(String sequenceSetId) {
        this.sequenceSetId = sequenceSetId;
    }

    public SequenceSet withSequenceSetId(String sequenceSetId) {
        this.sequenceSetId = sequenceSetId;
        return this;
    }

    @JsonProperty("sequences")
    public List<Sequence> getSequences() {
        return sequences;
    }

    @JsonProperty("sequences")
    public void setSequences(List<Sequence> sequences) {
        this.sequences = sequences;
    }

    public SequenceSet withSequences(List<Sequence> sequences) {
        this.sequences = sequences;
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
