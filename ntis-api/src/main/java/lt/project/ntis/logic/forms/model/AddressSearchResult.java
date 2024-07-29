package lt.project.ntis.logic.forms.model;


/**
 * @model Adresų greitos paieškos objektas
 */
public class AddressSearchResult {

	private Integer ad_id;
	 private Double ads_coordinate_latitude;
	 private Double ads_coordinate_longitude;
	 private String ads_nr;
	 private String apl_pat_nr;
	 private String re_name_k;
	 private String re_type_abbreviation;
	 private String rfc_meaning;
	 private String str_name;
	 private String str_type_abbreviation;
	
	
	public AddressSearchResult(Integer ad_id, Double ads_coordinate_latitude, Double ads_coordinate_longitude,
			String ads_nr, String apl_pat_nr, String re_name_k, String re_type_abbreviation, String rfc_meaning,
			String str_name, String str_type_abbreviation) {
		super();
		this.ad_id = ad_id;
		this.ads_coordinate_latitude = ads_coordinate_latitude;
		this.ads_coordinate_longitude = ads_coordinate_longitude;
		this.ads_nr = ads_nr;
		this.apl_pat_nr = apl_pat_nr;
		this.re_name_k = re_name_k;
		this.re_type_abbreviation = re_type_abbreviation;
		this.rfc_meaning = rfc_meaning;
		this.str_name = str_name;
		this.str_type_abbreviation = str_type_abbreviation;
	}
	 public AddressSearchResult() {
	        super();
	  
	 }
	
	
 public Integer getAd_id() {
		return ad_id;
	}
	public void setAd_id(Integer ad_id) {
		this.ad_id = ad_id;
	}
	public Double getAds_coordinate_latitude() {
		return ads_coordinate_latitude;
	}
	public void setAds_coordinate_latitude(Double ads_coordinate_latitude) {
		this.ads_coordinate_latitude = ads_coordinate_latitude;
	}
	public Double getAds_coordinate_longitude() {
		return ads_coordinate_longitude;
	}
	public void setAds_coordinate_longitude(Double ads_coordinate_longitude) {
		this.ads_coordinate_longitude = ads_coordinate_longitude;
	}
	public String getAds_nr() {
		return ads_nr;
	}
	public void setAds_nr(String ads_nr) {
		this.ads_nr = ads_nr;
	}
	public String getApl_pat_nr() {
		return apl_pat_nr;
	}
	public void setApl_pat_nr(String apl_pat_nr) {
		this.apl_pat_nr = apl_pat_nr;
	}
	public String getRe_name_k() {
		return re_name_k;
	}
	public void setRe_name_k(String re_name_k) {
		this.re_name_k = re_name_k;
	}
	public String getRe_type_abbreviation() {
		return re_type_abbreviation;
	}
	public void setRe_type_abbreviation(String re_type_abbreviation) {
		this.re_type_abbreviation = re_type_abbreviation;
	}
	public String getRfc_meaning() {
		return rfc_meaning;
	}
	public void setRfc_meaning(String rfc_meaning) {
		this.rfc_meaning = rfc_meaning;
	}
	public String getStr_name() {
		return str_name;
	}
	public void setStr_name(String str_name) {
		this.str_name = str_name;
	}
	public String getStr_type_abbreviation() {
		return str_type_abbreviation;
	}
	public void setStr_type_abbreviation(String str_type_abbreviation) {
		this.str_type_abbreviation = str_type_abbreviation;
	}

}

