package us.kbase.kbasememe;

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
 * <p>
 * Original spec-file type: MotifCollectionMeme
 * </p>
 * 
 * <pre>
 * Represents collection of motifs
 * string kbaseMotifCollectionMemeId - KBase identifier of MotifCollectionMeme
 * string timestamp - timestamp for creation time of collection
 * string versionMeme - version of MEME like "MEME version 4.9.0 (Release date: Wed Oct  3 11:07:26 EST 2012)"
 * string inputDatafile - name of input file, DATAFILE field of MEME output
 * string alphabetMeme - ALPHABET field of MEME output (like "ACGT")
 * string trainingSetMeme - strings from TRAINING SET section, except DATAFILE and ALPHABET fields
 * string commandLineMeme - command line of MEME run 
 * string modMeme - value of mod parameter of MEME run
 * int nmotifsMeme - value of nmotifs parameter of MEME run
 * string evtMeme - value of evt parameter of MEME run
 * string objectFunctionMeme - value of object function parameter of MEME run
 * int minwMeme - value of minw parameter of MEME run
 * int maxwMeme - value of maxw parameter of MEME run
 * float minicMeme - value of minic parameter of MEME run
 * int wgMeme - value of wg parameter of MEME run
 * int wsMeme - value of wc parameter of MEME run
 * string endgapsMeme - value of endgaps parameter of MEME run
 * int minsitesMeme - value of minsites parameter of MEME run
 * int maxsitesMeme - value of maxsites parameter of MEME run
 * float wnsitesMeme - value of wnsites parameter of MEME run
 * int probMeme - value of prob parameter of MEME run
 * string spmapMeme - value of spmsp parameter of MEME run
 * float spfuzzMeme - value of spfuzz parameter of MEME run
 * string substringMeme - value of substring parameter of MEME run
 * string branchingMeme - value of branching parameter of MEME run
 * string wbranchMeme - value of wbranch parameter of MEME run
 * string priorMeme - value of prior parameter of MEME run
 * float bMeme - value of b parameter of MEME run
 * int maxiterMeme - value of maxiter parameter of MEME run
 * float distanceMeme - value of distance parameter of MEME run
 * int nMeme - value of n parameter of MEME run
 * int nCapMeme - value of N parameter of MEME run
 * string strandsMeme - value of strands parameter of MEME run
 * int seedMeme - value of seed parameter of MEME run
 * int seqfracMeme - value of seqfrac parameter of MEME run
 * string letterFreqMeme - letter frequencies in dataset
 * string bgFreqMeme - background letter frequencies
 * list<Motif> motifs - A list of all motifs in a collection
 * string collectionMemeOutput - section of MEME output text file (all before motif data)
 * </pre>
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({ "kbaseMotifCollectionMemeId", "timestamp", "versionMeme",
		"inputDatafile", "alphabetMeme", "trainingSetMeme", "commandLineMeme",
		"modMeme", "nmotifsMeme", "evtMeme", "objectFunctionMeme", "minwMeme",
		"maxwMeme", "minicMeme", "wgMeme", "wsMeme", "endgapsMeme",
		"minsitesMeme", "maxsitesMeme", "wnsitesMeme", "probMeme", "spmapMeme",
		"spfuzzMeme", "substringMeme", "branchingMeme", "wbranchMeme",
		"priorMeme", "bMeme", "maxiterMeme", "distanceMeme", "nMeme",
		"nCapMeme", "strandsMeme", "seedMeme", "seqfracMeme", "letterFreqMeme",
		"bgFreqMeme", "motifs", "collectionMemeOutput" })
public class MotifCollectionMeme {

	@JsonProperty("kbaseMotifCollectionMemeId")
	private String kbaseMotifCollectionMemeId;
	@JsonProperty("timestamp")
	private String timestamp;
	@JsonProperty("versionMeme")
	private String versionMeme;
	@JsonProperty("inputDatafile")
	private String inputDatafile;
	@JsonProperty("alphabetMeme")
	private String alphabetMeme;
	@JsonProperty("trainingSetMeme")
	private List<String> trainingSetMeme = new ArrayList<String>();
	@JsonProperty("commandLineMeme")
	private String commandLineMeme;
	@JsonProperty("modMeme")
	private String modMeme;
	@JsonProperty("nmotifsMeme")
	private Integer nmotifsMeme;
	@JsonProperty("evtMeme")
	private String evtMeme;
	@JsonProperty("objectFunctionMeme")
	private String objectFunctionMeme;
	@JsonProperty("minwMeme")
	private Integer minwMeme;
	@JsonProperty("maxwMeme")
	private Integer maxwMeme;
	@JsonProperty("minicMeme")
	private Double minicMeme;
	@JsonProperty("wgMeme")
	private Integer wgMeme;
	@JsonProperty("wsMeme")
	private Integer wsMeme;
	@JsonProperty("endgapsMeme")
	private String endgapsMeme;
	@JsonProperty("minsitesMeme")
	private Integer minsitesMeme;
	@JsonProperty("maxsitesMeme")
	private Integer maxsitesMeme;
	@JsonProperty("wnsitesMeme")
	private Double wnsitesMeme;
	@JsonProperty("probMeme")
	private Integer probMeme;
	@JsonProperty("spmapMeme")
	private String spmapMeme;
	@JsonProperty("spfuzzMeme")
	private String spfuzzMeme;
	@JsonProperty("substringMeme")
	private String substringMeme;
	@JsonProperty("branchingMeme")
	private String branchingMeme;
	@JsonProperty("wbranchMeme")
	private String wbranchMeme;
	@JsonProperty("priorMeme")
	private String priorMeme;
	@JsonProperty("bMeme")
	private Double bMeme;
	@JsonProperty("maxiterMeme")
	private Integer maxiterMeme;
	@JsonProperty("distanceMeme")
	private Double distanceMeme;
	@JsonProperty("nMeme")
	private Integer nMeme;
	@JsonProperty("nCapMeme")
	private Integer nCapMeme;
	@JsonProperty("strandsMeme")
	private String strandsMeme;
	@JsonProperty("seedMeme")
	private Integer seedMeme;
	@JsonProperty("seqfracMeme")
	private Integer seqfracMeme;
	@JsonProperty("letterFreqMeme")
	private String letterFreqMeme;
	@JsonProperty("bgFreqMeme")
	private String bgFreqMeme;
	@JsonProperty("motifs")
	private List<MotifMeme> motifs = new ArrayList<MotifMeme>();
	@JsonProperty("collectionMemeOutput")
	private String collectionMemeOutput;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("kbaseMotifCollectionMemeId")
	public String getKbaseMotifCollectionMemeId() {
		return kbaseMotifCollectionMemeId;
	}

	@JsonProperty("kbaseMotifCollectionMemeId")
	public void setKbaseMotifCollectionMemeId(String kbaseMotifCollectionMemeId) {
		this.kbaseMotifCollectionMemeId = kbaseMotifCollectionMemeId;
	}

	public MotifCollectionMeme withKbaseMotifCollectionMemeId(
			String kbaseMotifCollectionMemeId) {
		this.kbaseMotifCollectionMemeId = kbaseMotifCollectionMemeId;
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

	public MotifCollectionMeme withTimestamp(String timestamp) {
		this.timestamp = timestamp;
		return this;
	}

	@JsonProperty("versionMeme")
	public String getVersionMeme() {
		return versionMeme;
	}

	@JsonProperty("versionMeme")
	public void setVersionMeme(String versionMeme) {
		this.versionMeme = versionMeme;
	}

	public MotifCollectionMeme withVersionMeme(String versionMeme) {
		this.versionMeme = versionMeme;
		return this;
	}

	@JsonProperty("inputDatafile")
	public String getInputDatafile() {
		return inputDatafile;
	}

	@JsonProperty("inputDatafile")
	public void setInputDatafile(String inputDatafile) {
		this.inputDatafile = inputDatafile;
	}

	public MotifCollectionMeme withInputDatafile(String inputDatafile) {
		this.inputDatafile = inputDatafile;
		return this;
	}

	@JsonProperty("alphabetMeme")
	public String getAlphabetMeme() {
		return alphabetMeme;
	}

	@JsonProperty("alphabetMeme")
	public void setAlphabetMeme(String alphabetMeme) {
		this.alphabetMeme = alphabetMeme;
	}

	public MotifCollectionMeme withAlphabetMeme(String alphabetMeme) {
		this.alphabetMeme = alphabetMeme;
		return this;
	}

	@JsonProperty("trainingSetMeme")
	public List<String> getTrainingSetMeme() {
		return trainingSetMeme;
	}

	@JsonProperty("trainingSetMeme")
	public void setTrainingSetMeme(List<String> trainingSetMeme) {
		this.trainingSetMeme = trainingSetMeme;
	}

	public MotifCollectionMeme withTrainingSetMeme(List<String> trainingSetMeme) {
		this.trainingSetMeme = trainingSetMeme;
		return this;
	}

	@JsonProperty("commandLineMeme")
	public String getCommandLineMeme() {
		return commandLineMeme;
	}

	@JsonProperty("commandLineMeme")
	public void setCommandLineMeme(String commandLineMeme) {
		this.commandLineMeme = commandLineMeme;
	}

	public MotifCollectionMeme withCommandLineMeme(String commandLineMeme) {
		this.commandLineMeme = commandLineMeme;
		return this;
	}

	@JsonProperty("modMeme")
	public String getModMeme() {
		return modMeme;
	}

	@JsonProperty("modMeme")
	public void setModMeme(String modMeme) {
		this.modMeme = modMeme;
	}

	public MotifCollectionMeme withModMeme(String modMeme) {
		this.modMeme = modMeme;
		return this;
	}

	@JsonProperty("nmotifsMeme")
	public Integer getNmotifsMeme() {
		return nmotifsMeme;
	}

	@JsonProperty("nmotifsMeme")
	public void setNmotifsMeme(Integer nmotifsMeme) {
		this.nmotifsMeme = nmotifsMeme;
	}

	public MotifCollectionMeme withNmotifsMeme(Integer nmotifsMeme) {
		this.nmotifsMeme = nmotifsMeme;
		return this;
	}

	@JsonProperty("evtMeme")
	public String getEvtMeme() {
		return evtMeme;
	}

	@JsonProperty("evtMeme")
	public void setEvtMeme(String evtMeme) {
		this.evtMeme = evtMeme;
	}

	public MotifCollectionMeme withEvtMeme(String evtMeme) {
		this.evtMeme = evtMeme;
		return this;
	}

	@JsonProperty("objectFunctionMeme")
	public String getObjectFunctionMeme() {
		return objectFunctionMeme;
	}

	@JsonProperty("objectFunctionMeme")
	public void setObjectFunctionMeme(String objectFunctionMeme) {
		this.objectFunctionMeme = objectFunctionMeme;
	}

	public MotifCollectionMeme withObjectFunctionMeme(String objectFunctionMeme) {
		this.objectFunctionMeme = objectFunctionMeme;
		return this;
	}

	@JsonProperty("minwMeme")
	public Integer getMinwMeme() {
		return minwMeme;
	}

	@JsonProperty("minwMeme")
	public void setMinwMeme(Integer minwMeme) {
		this.minwMeme = minwMeme;
	}

	public MotifCollectionMeme withMinwMeme(Integer minwMeme) {
		this.minwMeme = minwMeme;
		return this;
	}

	@JsonProperty("maxwMeme")
	public Integer getMaxwMeme() {
		return maxwMeme;
	}

	@JsonProperty("maxwMeme")
	public void setMaxwMeme(Integer maxwMeme) {
		this.maxwMeme = maxwMeme;
	}

	public MotifCollectionMeme withMaxwMeme(Integer maxwMeme) {
		this.maxwMeme = maxwMeme;
		return this;
	}

	@JsonProperty("minicMeme")
	public Double getMinicMeme() {
		return minicMeme;
	}

	@JsonProperty("minicMeme")
	public void setMinicMeme(Double minicMeme) {
		this.minicMeme = minicMeme;
	}

	public MotifCollectionMeme withMinicMeme(Double minicMeme) {
		this.minicMeme = minicMeme;
		return this;
	}

	@JsonProperty("wgMeme")
	public Integer getWgMeme() {
		return wgMeme;
	}

	@JsonProperty("wgMeme")
	public void setWgMeme(Integer wgMeme) {
		this.wgMeme = wgMeme;
	}

	public MotifCollectionMeme withWgMeme(Integer wgMeme) {
		this.wgMeme = wgMeme;
		return this;
	}

	@JsonProperty("wsMeme")
	public Integer getWsMeme() {
		return wsMeme;
	}

	@JsonProperty("wsMeme")
	public void setWsMeme(Integer wsMeme) {
		this.wsMeme = wsMeme;
	}

	public MotifCollectionMeme withWsMeme(Integer wsMeme) {
		this.wsMeme = wsMeme;
		return this;
	}

	@JsonProperty("endgapsMeme")
	public String getEndgapsMeme() {
		return endgapsMeme;
	}

	@JsonProperty("endgapsMeme")
	public void setEndgapsMeme(String endgapsMeme) {
		this.endgapsMeme = endgapsMeme;
	}

	public MotifCollectionMeme withEndgapsMeme(String endgapsMeme) {
		this.endgapsMeme = endgapsMeme;
		return this;
	}

	@JsonProperty("minsitesMeme")
	public Integer getMinsitesMeme() {
		return minsitesMeme;
	}

	@JsonProperty("minsitesMeme")
	public void setMinsitesMeme(Integer minsitesMeme) {
		this.minsitesMeme = minsitesMeme;
	}

	public MotifCollectionMeme withMinsitesMeme(Integer minsitesMeme) {
		this.minsitesMeme = minsitesMeme;
		return this;
	}

	@JsonProperty("maxsitesMeme")
	public Integer getMaxsitesMeme() {
		return maxsitesMeme;
	}

	@JsonProperty("maxsitesMeme")
	public void setMaxsitesMeme(Integer maxsitesMeme) {
		this.maxsitesMeme = maxsitesMeme;
	}

	public MotifCollectionMeme withMaxsitesMeme(Integer maxsitesMeme) {
		this.maxsitesMeme = maxsitesMeme;
		return this;
	}

	@JsonProperty("wnsitesMeme")
	public Double getWnsitesMeme() {
		return wnsitesMeme;
	}

	@JsonProperty("wnsitesMeme")
	public void setWnsitesMeme(Double wnsitesMeme) {
		this.wnsitesMeme = wnsitesMeme;
	}

	public MotifCollectionMeme withWnsitesMeme(Double wnsitesMeme) {
		this.wnsitesMeme = wnsitesMeme;
		return this;
	}

	@JsonProperty("probMeme")
	public Integer getProbMeme() {
		return probMeme;
	}

	@JsonProperty("probMeme")
	public void setProbMeme(Integer probMeme) {
		this.probMeme = probMeme;
	}

	public MotifCollectionMeme withProbMeme(Integer probMeme) {
		this.probMeme = probMeme;
		return this;
	}

	@JsonProperty("spmapMeme")
	public String getSpmapMeme() {
		return spmapMeme;
	}

	@JsonProperty("spmapMeme")
	public void setSpmapMeme(String spmapMeme) {
		this.spmapMeme = spmapMeme;
	}

	public MotifCollectionMeme withSpmapMeme(String spmapMeme) {
		this.spmapMeme = spmapMeme;
		return this;
	}

	@JsonProperty("spfuzzMeme")
	public String getSpfuzzMeme() {
		return spfuzzMeme;
	}

	@JsonProperty("spfuzzMeme")
	public void setSpfuzzMeme(String spfuzzMeme) {
		this.spfuzzMeme = spfuzzMeme;
	}

	public MotifCollectionMeme withSpfuzzMeme(String spfuzzMeme) {
		this.spfuzzMeme = spfuzzMeme;
		return this;
	}

	@JsonProperty("substringMeme")
	public String getSubstringMeme() {
		return substringMeme;
	}

	@JsonProperty("substringMeme")
	public void setSubstringMeme(String substringMeme) {
		this.substringMeme = substringMeme;
	}

	public MotifCollectionMeme withSubstringMeme(String substringMeme) {
		this.substringMeme = substringMeme;
		return this;
	}

	@JsonProperty("branchingMeme")
	public String getBranchingMeme() {
		return branchingMeme;
	}

	@JsonProperty("branchingMeme")
	public void setBranchingMeme(String branchingMeme) {
		this.branchingMeme = branchingMeme;
	}

	public MotifCollectionMeme withBranchingMeme(String branchingMeme) {
		this.branchingMeme = branchingMeme;
		return this;
	}

	@JsonProperty("wbranchMeme")
	public String getWbranchMeme() {
		return wbranchMeme;
	}

	@JsonProperty("wbranchMeme")
	public void setWbranchMeme(String wbranchMeme) {
		this.wbranchMeme = wbranchMeme;
	}

	public MotifCollectionMeme withWbranchMeme(String wbranchMeme) {
		this.wbranchMeme = wbranchMeme;
		return this;
	}

	@JsonProperty("priorMeme")
	public String getPriorMeme() {
		return priorMeme;
	}

	@JsonProperty("priorMeme")
	public void setPriorMeme(String priorMeme) {
		this.priorMeme = priorMeme;
	}

	public MotifCollectionMeme withPriorMeme(String priorMeme) {
		this.priorMeme = priorMeme;
		return this;
	}

	@JsonProperty("bMeme")
	public Double getBMeme() {
		return bMeme;
	}

	@JsonProperty("bMeme")
	public void setBMeme(Double bMeme) {
		this.bMeme = bMeme;
	}

	public MotifCollectionMeme withBMeme(Double bMeme) {
		this.bMeme = bMeme;
		return this;
	}

	@JsonProperty("maxiterMeme")
	public Integer getMaxiterMeme() {
		return maxiterMeme;
	}

	@JsonProperty("maxiterMeme")
	public void setMaxiterMeme(Integer maxiterMeme) {
		this.maxiterMeme = maxiterMeme;
	}

	public MotifCollectionMeme withMaxiterMeme(Integer maxiterMeme) {
		this.maxiterMeme = maxiterMeme;
		return this;
	}

	@JsonProperty("distanceMeme")
	public Double getDistanceMeme() {
		return distanceMeme;
	}

	@JsonProperty("distanceMeme")
	public void setDistanceMeme(Double distanceMeme) {
		this.distanceMeme = distanceMeme;
	}

	public MotifCollectionMeme withDistanceMeme(Double distanceMeme) {
		this.distanceMeme = distanceMeme;
		return this;
	}

	@JsonProperty("nMeme")
	public Integer getNMeme() {
		return nMeme;
	}

	@JsonProperty("nMeme")
	public void setNMeme(Integer nMeme) {
		this.nMeme = nMeme;
	}

	public MotifCollectionMeme withNMeme(Integer nMeme) {
		this.nMeme = nMeme;
		return this;
	}

	@JsonProperty("nCapMeme")
	public Integer getNCapMeme() {
		return nCapMeme;
	}

	@JsonProperty("nCapMeme")
	public void setNCapMeme(Integer nCapMeme) {
		this.nCapMeme = nCapMeme;
	}

	public MotifCollectionMeme withNCapMeme(Integer nCapMeme) {
		this.nCapMeme = nCapMeme;
		return this;
	}

	@JsonProperty("strandsMeme")
	public String getStrandsMeme() {
		return strandsMeme;
	}

	@JsonProperty("strandsMeme")
	public void setStrandsMeme(String strandsMeme) {
		this.strandsMeme = strandsMeme;
	}

	public MotifCollectionMeme withStrandsMeme(String strandsMeme) {
		this.strandsMeme = strandsMeme;
		return this;
	}

	@JsonProperty("seedMeme")
	public Integer getSeedMeme() {
		return seedMeme;
	}

	@JsonProperty("seedMeme")
	public void setSeedMeme(Integer seedMeme) {
		this.seedMeme = seedMeme;
	}

	public MotifCollectionMeme withSeedMeme(Integer seedMeme) {
		this.seedMeme = seedMeme;
		return this;
	}

	@JsonProperty("seqfracMeme")
	public Integer getSeqfracMeme() {
		return seqfracMeme;
	}

	@JsonProperty("seqfracMeme")
	public void setSeqfracMeme(Integer seqfracMeme) {
		this.seqfracMeme = seqfracMeme;
	}

	public MotifCollectionMeme withSeqfracMeme(Integer seqfracMeme) {
		this.seqfracMeme = seqfracMeme;
		return this;
	}

	@JsonProperty("letterFreqMeme")
	public String getLetterFreqMeme() {
		return letterFreqMeme;
	}

	@JsonProperty("letterFreqMeme")
	public void setLetterFreqMeme(String letterFreqMeme) {
		this.letterFreqMeme = letterFreqMeme;
	}

	public MotifCollectionMeme withLetterFreqMeme(String letterFreqMeme) {
		this.letterFreqMeme = letterFreqMeme;
		return this;
	}

	@JsonProperty("bgFreqMeme")
	public String getBgFreqMeme() {
		return bgFreqMeme;
	}

	@JsonProperty("bgFreqMeme")
	public void setBgFreqMeme(String bgFreqMeme) {
		this.bgFreqMeme = bgFreqMeme;
	}

	public MotifCollectionMeme withBgFreqMeme(String bgFreqMeme) {
		this.bgFreqMeme = bgFreqMeme;
		return this;
	}

	@JsonProperty("motifs")
	public List<MotifMeme> getMotifs() {
		return motifs;
	}

	@JsonProperty("motifs")
	public void setMotifs(List<MotifMeme> motifs) {
		this.motifs = motifs;
	}

	public MotifCollectionMeme withMotifs(List<MotifMeme> motifs) {
		this.motifs = motifs;
		return this;
	}

	@JsonProperty("collectionMemeOutput")
	public String getCollectionMemeOutput() {
		return collectionMemeOutput;
	}

	@JsonProperty("collectionMemeOutput")
	public void setCollectionMemeOutput(String collectionMemeOutput) {
		this.collectionMemeOutput = collectionMemeOutput;
	}

	public MotifCollectionMeme withCollectionMemeOutput(
			String collectionMemeOutput) {
		this.collectionMemeOutput = collectionMemeOutput;
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
