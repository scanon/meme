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
 * Original spec-file type: MotifMeme
 * </p>
 * 
 * <pre>
 * Represents a particular motif 
 * string kbaseMotifMemeId - KBase identifier of MotifMeme
 * int motifName - name of motif (often number of motif in collection)
 * int motifWidthMeme - width of motif
 * int motifSitesMeme - number of sites
 * float motifLlrMeme - log likelihood ratio of motif
 * float motifEvalueMeme - E-value of motif
 * string motifMemeOutput - part of MEME output file with data about this motif
 * list<SiteMeme> sites - list of sites
 * </pre>
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({ "kbaseMotifMemeId", "motifName", "motifWidthMeme",
		"motifSitesMeme", "motifLlrMeme", "motifEvalueMeme", "motifMemeOutput",
		"sites" })
public class MotifMeme {

	@JsonProperty("kbaseMotifMemeId")
	private String kbaseMotifMemeId;
	@JsonProperty("motifName")
	private Integer motifName;
	@JsonProperty("motifWidthMeme")
	private Integer motifWidthMeme;
	@JsonProperty("motifSitesMeme")
	private Integer motifSitesMeme;
	@JsonProperty("motifLlrMeme")
	private Double motifLlrMeme;
	@JsonProperty("motifEvalueMeme")
	private Double motifEvalueMeme;
	@JsonProperty("motifMemeOutput")
	private String motifMemeOutput;
	@JsonProperty("sites")
	private List<SiteMeme> sites = new ArrayList<SiteMeme>();
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("kbaseMotifMemeId")
	public String getKbaseMotifMemeId() {
		return kbaseMotifMemeId;
	}

	@JsonProperty("kbaseMotifMemeId")
	public void setKbaseMotifMemeId(String kbaseMotifMemeId) {
		this.kbaseMotifMemeId = kbaseMotifMemeId;
	}

	public MotifMeme withKbaseMotifMemeId(String kbaseMotifMemeId) {
		this.kbaseMotifMemeId = kbaseMotifMemeId;
		return this;
	}

	@JsonProperty("motifName")
	public Integer getMotifName() {
		return motifName;
	}

	@JsonProperty("motifName")
	public void setMotifName(Integer motifName) {
		this.motifName = motifName;
	}

	public MotifMeme withMotifName(Integer motifName) {
		this.motifName = motifName;
		return this;
	}

	@JsonProperty("motifWidthMeme")
	public Integer getMotifWidthMeme() {
		return motifWidthMeme;
	}

	@JsonProperty("motifWidthMeme")
	public void setMotifWidthMeme(Integer motifWidthMeme) {
		this.motifWidthMeme = motifWidthMeme;
	}

	public MotifMeme withMotifWidthMeme(Integer motifWidthMeme) {
		this.motifWidthMeme = motifWidthMeme;
		return this;
	}

	@JsonProperty("motifSitesMeme")
	public Integer getMotifSitesMeme() {
		return motifSitesMeme;
	}

	@JsonProperty("motifSitesMeme")
	public void setMotifSitesMeme(Integer motifSitesMeme) {
		this.motifSitesMeme = motifSitesMeme;
	}

	public MotifMeme withMotifSitesMeme(Integer motifSitesMeme) {
		this.motifSitesMeme = motifSitesMeme;
		return this;
	}

	@JsonProperty("motifLlrMeme")
	public Double getMotifLlrMeme() {
		return motifLlrMeme;
	}

	@JsonProperty("motifLlrMeme")
	public void setMotifLlrMeme(Double motifLlrMeme) {
		this.motifLlrMeme = motifLlrMeme;
	}

	public MotifMeme withMotifLlrMeme(Double motifLlrMeme) {
		this.motifLlrMeme = motifLlrMeme;
		return this;
	}

	@JsonProperty("motifEvalueMeme")
	public Double getMotifEvalueMeme() {
		return motifEvalueMeme;
	}

	@JsonProperty("motifEvalueMeme")
	public void setMotifEvalueMeme(Double motifEvalueMeme) {
		this.motifEvalueMeme = motifEvalueMeme;
	}

	public MotifMeme withMotifEvalueMeme(Double motifEvalueMeme) {
		this.motifEvalueMeme = motifEvalueMeme;
		return this;
	}

	@JsonProperty("motifMemeOutput")
	public String getMotifMemeOutput() {
		return motifMemeOutput;
	}

	@JsonProperty("motifMemeOutput")
	public void setMotifMemeOutput(String motifMemeOutput) {
		this.motifMemeOutput = motifMemeOutput;
	}

	public MotifMeme withMotifMemeOutput(String motifMemeOutput) {
		this.motifMemeOutput = motifMemeOutput;
		return this;
	}

	@JsonProperty("sites")
	public List<SiteMeme> getSites() {
		return sites;
	}

	@JsonProperty("sites")
	public void setSites(List<SiteMeme> sites) {
		this.sites = sites;
	}

	public MotifMeme withSites(List<SiteMeme> sites) {
		this.sites = sites;
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
