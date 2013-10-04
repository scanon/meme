
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
 * <p>Original spec-file type: MemeRunResult</p>
 * <pre>
 * Represents results of a single MEME run
 * string id - KBase ID of MemeRunResult
 * string timestamp - timestamp for creation time of collection
 * string version - version of MEME like "MEME version 4.9.0 (Release date: Wed Oct  3 11:07:26 EST 2012)"
 * string input_file_name - name of input file, DATAFILE field of MEME output
 * string alphabet - ALPHABET field of MEME output (like "ACGT")
 * string training_set - strings from TRAINING SET section, except DATAFILE and ALPHABET fields
 * string command_line - command line of MEME run 
 * string mod - value of mod parameter of MEME run
 * int nmotifs - value of nmotifs parameter of MEME run
 * string evt - value of evt parameter of MEME run
 * string object_function - value of object function parameter of MEME run
 * int minw - value of minw parameter of MEME run
 * int maxw - value of maxw parameter of MEME run
 * float minic - value of minic parameter of MEME run
 * int wg - value of wg parameter of MEME run
 * int ws - value of wc parameter of MEME run
 * string endgaps - value of endgaps parameter of MEME run
 * int minsites - value of minsites parameter of MEME run
 * int maxsites - value of maxsites parameter of MEME run
 * float wnsites - value of wnsites parameter of MEME run
 * int prob - value of prob parameter of MEME run
 * string spmap - value of spmsp parameter of MEME run
 * float spfuzz - value of spfuzz parameter of MEME run
 * string substring - value of substring parameter of MEME run
 * string branching - value of branching parameter of MEME run
 * string wbranch - value of wbranch parameter of MEME run
 * string prior - value of prior parameter of MEME run
 * float b - value of b parameter of MEME run
 * int maxiter - value of maxiter parameter of MEME run
 * float distance - value of distance parameter of MEME run
 * int n - value of n parameter of MEME run
 * int n_cap - value of N parameter of MEME run
 * string strands - value of strands parameter of MEME run
 * int seed - value of seed parameter of MEME run
 * int seqfrac - value of seqfrac parameter of MEME run
 * string letter_freq - letter frequencies in dataset
 * string bg_freq - background letter frequencies
 * list<MemeMotif> motifs - A list of all motifs in a collection
 * string raw_output - section of MEME output text file (all before motif data)
 * </pre>
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "id",
    "timestamp",
    "version",
    "input_file_name",
    "alphabet",
    "training_set",
    "command_line",
    "mod",
    "nmotifs",
    "evt",
    "object_function",
    "minw",
    "maxw",
    "minic",
    "wg",
    "ws",
    "endgaps",
    "minsites",
    "maxsites",
    "wnsites",
    "prob",
    "spmap",
    "spfuzz",
    "substring",
    "branching",
    "wbranch",
    "prior",
    "b",
    "maxiter",
    "distance",
    "n",
    "n_cap",
    "strands",
    "seed",
    "seqfrac",
    "letter_freq",
    "bg_freq",
    "motifs",
    "raw_output"
})
public class MemeRunResult {

    @JsonProperty("id")
    private String id;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("version")
    private String version;
    @JsonProperty("input_file_name")
    private String inputFileName;
    @JsonProperty("alphabet")
    private String alphabet;
    @JsonProperty("training_set")
    private List<String> trainingSet = new ArrayList<String>();
    @JsonProperty("command_line")
    private String commandLine;
    @JsonProperty("mod")
    private String mod;
    @JsonProperty("nmotifs")
    private Integer nmotifs;
    @JsonProperty("evt")
    private String evt;
    @JsonProperty("object_function")
    private String objectFunction;
    @JsonProperty("minw")
    private Integer minw;
    @JsonProperty("maxw")
    private Integer maxw;
    @JsonProperty("minic")
    private Double minic;
    @JsonProperty("wg")
    private Integer wg;
    @JsonProperty("ws")
    private Integer ws;
    @JsonProperty("endgaps")
    private String endgaps;
    @JsonProperty("minsites")
    private Integer minsites;
    @JsonProperty("maxsites")
    private Integer maxsites;
    @JsonProperty("wnsites")
    private Double wnsites;
    @JsonProperty("prob")
    private Integer prob;
    @JsonProperty("spmap")
    private String spmap;
    @JsonProperty("spfuzz")
    private String spfuzz;
    @JsonProperty("substring")
    private String substring;
    @JsonProperty("branching")
    private String branching;
    @JsonProperty("wbranch")
    private String wbranch;
    @JsonProperty("prior")
    private String prior;
    @JsonProperty("b")
    private Double b;
    @JsonProperty("maxiter")
    private Integer maxiter;
    @JsonProperty("distance")
    private Double distance;
    @JsonProperty("n")
    private Integer n;
    @JsonProperty("n_cap")
    private Integer nCap;
    @JsonProperty("strands")
    private String strands;
    @JsonProperty("seed")
    private Integer seed;
    @JsonProperty("seqfrac")
    private Integer seqfrac;
    @JsonProperty("letter_freq")
    private String letterFreq;
    @JsonProperty("bg_freq")
    private String bgFreq;
    @JsonProperty("motifs")
    private List<MemeMotif> motifs = new ArrayList<MemeMotif>();
    @JsonProperty("raw_output")
    private String rawOutput;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public MemeRunResult withId(String id) {
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

    public MemeRunResult withTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(String version) {
        this.version = version;
    }

    public MemeRunResult withVersion(String version) {
        this.version = version;
        return this;
    }

    @JsonProperty("input_file_name")
    public String getInputFileName() {
        return inputFileName;
    }

    @JsonProperty("input_file_name")
    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public MemeRunResult withInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
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

    public MemeRunResult withAlphabet(String alphabet) {
        this.alphabet = alphabet;
        return this;
    }

    @JsonProperty("training_set")
    public List<String> getTrainingSet() {
        return trainingSet;
    }

    @JsonProperty("training_set")
    public void setTrainingSet(List<String> trainingSet) {
        this.trainingSet = trainingSet;
    }

    public MemeRunResult withTrainingSet(List<String> trainingSet) {
        this.trainingSet = trainingSet;
        return this;
    }

    @JsonProperty("command_line")
    public String getCommandLine() {
        return commandLine;
    }

    @JsonProperty("command_line")
    public void setCommandLine(String commandLine) {
        this.commandLine = commandLine;
    }

    public MemeRunResult withCommandLine(String commandLine) {
        this.commandLine = commandLine;
        return this;
    }

    @JsonProperty("mod")
    public String getMod() {
        return mod;
    }

    @JsonProperty("mod")
    public void setMod(String mod) {
        this.mod = mod;
    }

    public MemeRunResult withMod(String mod) {
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

    public MemeRunResult withNmotifs(Integer nmotifs) {
        this.nmotifs = nmotifs;
        return this;
    }

    @JsonProperty("evt")
    public String getEvt() {
        return evt;
    }

    @JsonProperty("evt")
    public void setEvt(String evt) {
        this.evt = evt;
    }

    public MemeRunResult withEvt(String evt) {
        this.evt = evt;
        return this;
    }

    @JsonProperty("object_function")
    public String getObjectFunction() {
        return objectFunction;
    }

    @JsonProperty("object_function")
    public void setObjectFunction(String objectFunction) {
        this.objectFunction = objectFunction;
    }

    public MemeRunResult withObjectFunction(String objectFunction) {
        this.objectFunction = objectFunction;
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

    public MemeRunResult withMinw(Integer minw) {
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

    public MemeRunResult withMaxw(Integer maxw) {
        this.maxw = maxw;
        return this;
    }

    @JsonProperty("minic")
    public Double getMinic() {
        return minic;
    }

    @JsonProperty("minic")
    public void setMinic(Double minic) {
        this.minic = minic;
    }

    public MemeRunResult withMinic(Double minic) {
        this.minic = minic;
        return this;
    }

    @JsonProperty("wg")
    public Integer getWg() {
        return wg;
    }

    @JsonProperty("wg")
    public void setWg(Integer wg) {
        this.wg = wg;
    }

    public MemeRunResult withWg(Integer wg) {
        this.wg = wg;
        return this;
    }

    @JsonProperty("ws")
    public Integer getWs() {
        return ws;
    }

    @JsonProperty("ws")
    public void setWs(Integer ws) {
        this.ws = ws;
    }

    public MemeRunResult withWs(Integer ws) {
        this.ws = ws;
        return this;
    }

    @JsonProperty("endgaps")
    public String getEndgaps() {
        return endgaps;
    }

    @JsonProperty("endgaps")
    public void setEndgaps(String endgaps) {
        this.endgaps = endgaps;
    }

    public MemeRunResult withEndgaps(String endgaps) {
        this.endgaps = endgaps;
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

    public MemeRunResult withMinsites(Integer minsites) {
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

    public MemeRunResult withMaxsites(Integer maxsites) {
        this.maxsites = maxsites;
        return this;
    }

    @JsonProperty("wnsites")
    public Double getWnsites() {
        return wnsites;
    }

    @JsonProperty("wnsites")
    public void setWnsites(Double wnsites) {
        this.wnsites = wnsites;
    }

    public MemeRunResult withWnsites(Double wnsites) {
        this.wnsites = wnsites;
        return this;
    }

    @JsonProperty("prob")
    public Integer getProb() {
        return prob;
    }

    @JsonProperty("prob")
    public void setProb(Integer prob) {
        this.prob = prob;
    }

    public MemeRunResult withProb(Integer prob) {
        this.prob = prob;
        return this;
    }

    @JsonProperty("spmap")
    public String getSpmap() {
        return spmap;
    }

    @JsonProperty("spmap")
    public void setSpmap(String spmap) {
        this.spmap = spmap;
    }

    public MemeRunResult withSpmap(String spmap) {
        this.spmap = spmap;
        return this;
    }

    @JsonProperty("spfuzz")
    public String getSpfuzz() {
        return spfuzz;
    }

    @JsonProperty("spfuzz")
    public void setSpfuzz(String spfuzz) {
        this.spfuzz = spfuzz;
    }

    public MemeRunResult withSpfuzz(String spfuzz) {
        this.spfuzz = spfuzz;
        return this;
    }

    @JsonProperty("substring")
    public String getSubstring() {
        return substring;
    }

    @JsonProperty("substring")
    public void setSubstring(String substring) {
        this.substring = substring;
    }

    public MemeRunResult withSubstring(String substring) {
        this.substring = substring;
        return this;
    }

    @JsonProperty("branching")
    public String getBranching() {
        return branching;
    }

    @JsonProperty("branching")
    public void setBranching(String branching) {
        this.branching = branching;
    }

    public MemeRunResult withBranching(String branching) {
        this.branching = branching;
        return this;
    }

    @JsonProperty("wbranch")
    public String getWbranch() {
        return wbranch;
    }

    @JsonProperty("wbranch")
    public void setWbranch(String wbranch) {
        this.wbranch = wbranch;
    }

    public MemeRunResult withWbranch(String wbranch) {
        this.wbranch = wbranch;
        return this;
    }

    @JsonProperty("prior")
    public String getPrior() {
        return prior;
    }

    @JsonProperty("prior")
    public void setPrior(String prior) {
        this.prior = prior;
    }

    public MemeRunResult withPrior(String prior) {
        this.prior = prior;
        return this;
    }

    @JsonProperty("b")
    public Double getB() {
        return b;
    }

    @JsonProperty("b")
    public void setB(Double b) {
        this.b = b;
    }

    public MemeRunResult withB(Double b) {
        this.b = b;
        return this;
    }

    @JsonProperty("maxiter")
    public Integer getMaxiter() {
        return maxiter;
    }

    @JsonProperty("maxiter")
    public void setMaxiter(Integer maxiter) {
        this.maxiter = maxiter;
    }

    public MemeRunResult withMaxiter(Integer maxiter) {
        this.maxiter = maxiter;
        return this;
    }

    @JsonProperty("distance")
    public Double getDistance() {
        return distance;
    }

    @JsonProperty("distance")
    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public MemeRunResult withDistance(Double distance) {
        this.distance = distance;
        return this;
    }

    @JsonProperty("n")
    public Integer getN() {
        return n;
    }

    @JsonProperty("n")
    public void setN(Integer n) {
        this.n = n;
    }

    public MemeRunResult withN(Integer n) {
        this.n = n;
        return this;
    }

    @JsonProperty("n_cap")
    public Integer getNCap() {
        return nCap;
    }

    @JsonProperty("n_cap")
    public void setNCap(Integer nCap) {
        this.nCap = nCap;
    }

    public MemeRunResult withNCap(Integer nCap) {
        this.nCap = nCap;
        return this;
    }

    @JsonProperty("strands")
    public String getStrands() {
        return strands;
    }

    @JsonProperty("strands")
    public void setStrands(String strands) {
        this.strands = strands;
    }

    public MemeRunResult withStrands(String strands) {
        this.strands = strands;
        return this;
    }

    @JsonProperty("seed")
    public Integer getSeed() {
        return seed;
    }

    @JsonProperty("seed")
    public void setSeed(Integer seed) {
        this.seed = seed;
    }

    public MemeRunResult withSeed(Integer seed) {
        this.seed = seed;
        return this;
    }

    @JsonProperty("seqfrac")
    public Integer getSeqfrac() {
        return seqfrac;
    }

    @JsonProperty("seqfrac")
    public void setSeqfrac(Integer seqfrac) {
        this.seqfrac = seqfrac;
    }

    public MemeRunResult withSeqfrac(Integer seqfrac) {
        this.seqfrac = seqfrac;
        return this;
    }

    @JsonProperty("letter_freq")
    public String getLetterFreq() {
        return letterFreq;
    }

    @JsonProperty("letter_freq")
    public void setLetterFreq(String letterFreq) {
        this.letterFreq = letterFreq;
    }

    public MemeRunResult withLetterFreq(String letterFreq) {
        this.letterFreq = letterFreq;
        return this;
    }

    @JsonProperty("bg_freq")
    public String getBgFreq() {
        return bgFreq;
    }

    @JsonProperty("bg_freq")
    public void setBgFreq(String bgFreq) {
        this.bgFreq = bgFreq;
    }

    public MemeRunResult withBgFreq(String bgFreq) {
        this.bgFreq = bgFreq;
        return this;
    }

    @JsonProperty("motifs")
    public List<MemeMotif> getMotifs() {
        return motifs;
    }

    @JsonProperty("motifs")
    public void setMotifs(List<MemeMotif> motifs) {
        this.motifs = motifs;
    }

    public MemeRunResult withMotifs(List<MemeMotif> motifs) {
        this.motifs = motifs;
        return this;
    }

    @JsonProperty("raw_output")
    public String getRawOutput() {
        return rawOutput;
    }

    @JsonProperty("raw_output")
    public void setRawOutput(String rawOutput) {
        this.rawOutput = rawOutput;
    }

    public MemeRunResult withRawOutput(String rawOutput) {
        this.rawOutput = rawOutput;
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
