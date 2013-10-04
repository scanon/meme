
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
 * <p>Original spec-file type: MemeRunParameters</p>
 * <pre>
 * Contains parameters of a MEME run
 * string mod - distribution of motifs, acceptable values are "oops", "zoops", "anr"
 * int nmotifs - maximum number of motifs to find
 * int minw - minumum motif width
 * int maxw - maximum motif width
 * int nsites - number of sites for each motif
 * int minsites - minimum number of sites for each motif
 * int maxsites - maximum number of sites for each motif
 * int pal - force palindromes, acceptable values are 0 and 1
 * int revcomp - allow sites on + or - DNA strands, acceptable values are 0 and 1
 * </pre>
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "mod",
    "nmotifs",
    "minw",
    "maxw",
    "nsites",
    "minsites",
    "maxsites",
    "pal",
    "revcomp"
})
public class MemeRunParameters {

    @JsonProperty("mod")
    private String mod;
    @JsonProperty("nmotifs")
    private Integer nmotifs;
    @JsonProperty("minw")
    private Integer minw;
    @JsonProperty("maxw")
    private Integer maxw;
    @JsonProperty("nsites")
    private Integer nsites;
    @JsonProperty("minsites")
    private Integer minsites;
    @JsonProperty("maxsites")
    private Integer maxsites;
    @JsonProperty("pal")
    private Integer pal;
    @JsonProperty("revcomp")
    private Integer revcomp;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("mod")
    public String getMod() {
        return mod;
    }

    @JsonProperty("mod")
    public void setMod(String mod) {
        this.mod = mod;
    }

    public MemeRunParameters withMod(String mod) {
        this.mod = mod;
        return this;
    }

    @JsonProperty("nmotifs")
    public Integer getNmotifs() {
        return nmotifs;
    }

    @JsonProperty("nmotifs")
    public void setNmotifs(Integer nmotifs) {
        this.nmotifs = nmotifs;
    }

    public MemeRunParameters withNmotifs(Integer nmotifs) {
        this.nmotifs = nmotifs;
        return this;
    }

    @JsonProperty("minw")
    public Integer getMinw() {
        return minw;
    }

    @JsonProperty("minw")
    public void setMinw(Integer minw) {
        this.minw = minw;
    }

    public MemeRunParameters withMinw(Integer minw) {
        this.minw = minw;
        return this;
    }

    @JsonProperty("maxw")
    public Integer getMaxw() {
        return maxw;
    }

    @JsonProperty("maxw")
    public void setMaxw(Integer maxw) {
        this.maxw = maxw;
    }

    public MemeRunParameters withMaxw(Integer maxw) {
        this.maxw = maxw;
        return this;
    }

    @JsonProperty("nsites")
    public Integer getNsites() {
        return nsites;
    }

    @JsonProperty("nsites")
    public void setNsites(Integer nsites) {
        this.nsites = nsites;
    }

    public MemeRunParameters withNsites(Integer nsites) {
        this.nsites = nsites;
        return this;
    }

    @JsonProperty("minsites")
    public Integer getMinsites() {
        return minsites;
    }

    @JsonProperty("minsites")
    public void setMinsites(Integer minsites) {
        this.minsites = minsites;
    }

    public MemeRunParameters withMinsites(Integer minsites) {
        this.minsites = minsites;
        return this;
    }

    @JsonProperty("maxsites")
    public Integer getMaxsites() {
        return maxsites;
    }

    @JsonProperty("maxsites")
    public void setMaxsites(Integer maxsites) {
        this.maxsites = maxsites;
    }

    public MemeRunParameters withMaxsites(Integer maxsites) {
        this.maxsites = maxsites;
        return this;
    }

    @JsonProperty("pal")
    public Integer getPal() {
        return pal;
    }

    @JsonProperty("pal")
    public void setPal(Integer pal) {
        this.pal = pal;
    }

    public MemeRunParameters withPal(Integer pal) {
        this.pal = pal;
        return this;
    }

    @JsonProperty("revcomp")
    public Integer getRevcomp() {
        return revcomp;
    }

    @JsonProperty("revcomp")
    public void setRevcomp(Integer revcomp) {
        this.revcomp = revcomp;
    }

    public MemeRunParameters withRevcomp(Integer revcomp) {
        this.revcomp = revcomp;
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
