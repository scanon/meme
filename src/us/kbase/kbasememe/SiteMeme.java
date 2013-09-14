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
 * Original spec-file type: SiteMeme
 * </p>
 * 
 * <pre>
 * Represents a particular site from motif description 
 * string kbaseSiteMemeId - KBase identifier of MotifMeme
 * string siteSequenceName - identifier of sequence 
 * int siteStart - position of site start 
 * float sitePvalue - site P-value
 * string siteLeftFlank - sequence of left flank
 * string siteSequence - sequence of site
 * string siteRightFlank - sequence of right flank
 * </pre>
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({ "kbaseMotifMemeId", "siteSequenceName", "siteStart",
		"sitePvalue", "siteLeftFlank", "siteSequence", "siteRightFlank" })
public class SiteMeme {

	@JsonProperty("kbaseMotifMemeId")
	private String kbaseMotifMemeId;
	@JsonProperty("siteSequenceName")
	private String siteSequenceName;
	@JsonProperty("siteStart")
	private Integer siteStart;
	@JsonProperty("sitePvalue")
	private Double sitePvalue;
	@JsonProperty("siteLeftFlank")
	private String siteLeftFlank;
	@JsonProperty("siteSequence")
	private String siteSequence;
	@JsonProperty("siteRightFlank")
	private String siteRightFlank;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("kbaseMotifMemeId")
	public String getKbaseMotifMemeId() {
		return kbaseMotifMemeId;
	}

	@JsonProperty("kbaseMotifMemeId")
	public void setKbaseMotifMemeId(String kbaseMotifMemeId) {
		this.kbaseMotifMemeId = kbaseMotifMemeId;
	}

	public SiteMeme withKbaseMotifMemeId(String kbaseMotifMemeId) {
		this.kbaseMotifMemeId = kbaseMotifMemeId;
		return this;
	}

	@JsonProperty("siteSequenceName")
	public String getSiteSequenceName() {
		return siteSequenceName;
	}

	@JsonProperty("siteSequenceName")
	public void setSiteSequenceName(String siteSequenceName) {
		this.siteSequenceName = siteSequenceName;
	}

	public SiteMeme withSiteSequenceName(String siteSequenceName) {
		this.siteSequenceName = siteSequenceName;
		return this;
	}

	@JsonProperty("siteStart")
	public Integer getSiteStart() {
		return siteStart;
	}

	@JsonProperty("siteStart")
	public void setSiteStart(Integer siteStart) {
		this.siteStart = siteStart;
	}

	public SiteMeme withSiteStart(Integer siteStart) {
		this.siteStart = siteStart;
		return this;
	}

	@JsonProperty("sitePvalue")
	public Double getSitePvalue() {
		return sitePvalue;
	}

	@JsonProperty("sitePvalue")
	public void setSitePvalue(Double sitePvalue) {
		this.sitePvalue = sitePvalue;
	}

	public SiteMeme withSitePvalue(Double sitePvalue) {
		this.sitePvalue = sitePvalue;
		return this;
	}

	@JsonProperty("siteLeftFlank")
	public String getSiteLeftFlank() {
		return siteLeftFlank;
	}

	@JsonProperty("siteLeftFlank")
	public void setSiteLeftFlank(String siteLeftFlank) {
		this.siteLeftFlank = siteLeftFlank;
	}

	public SiteMeme withSiteLeftFlank(String siteLeftFlank) {
		this.siteLeftFlank = siteLeftFlank;
		return this;
	}

	@JsonProperty("siteSequence")
	public String getSiteSequence() {
		return siteSequence;
	}

	@JsonProperty("siteSequence")
	public void setSiteSequence(String siteSequence) {
		this.siteSequence = siteSequence;
	}

	public SiteMeme withSiteSequence(String siteSequence) {
		this.siteSequence = siteSequence;
		return this;
	}

	@JsonProperty("siteRightFlank")
	public String getSiteRightFlank() {
		return siteRightFlank;
	}

	@JsonProperty("siteRightFlank")
	public void setSiteRightFlank(String siteRightFlank) {
		this.siteRightFlank = siteRightFlank;
	}

	public SiteMeme withSiteRightFlank(String siteRightFlank) {
		this.siteRightFlank = siteRightFlank;
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
