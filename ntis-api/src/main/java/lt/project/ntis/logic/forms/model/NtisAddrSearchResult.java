package lt.project.ntis.logic.forms.model;

import eu.itreegroup.spark.annotations.TypeScriptOptional;

/**
 * Klasė skirta adreso paieškos duomenims perduoti
 *
 */
public class NtisAddrSearchResult {

    @TypeScriptOptional
    private Double wtf_id;

    private Double ad_id;

    private String municipality;

    private String city;

    private String street;

    private String housing;

    private Double latitude;

    private Double longitude;

    private String address;

    public Double getWtf_id() {
        return wtf_id;
    }

    public void setWtf_id(Double wtf_id) {
        this.wtf_id = wtf_id;
    }

    public Double getAd_id() {
        return ad_id;
    }

    public void setAd_id(Double ad_id) {
        this.ad_id = ad_id;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getHousing() {
        return housing;
    }

    public void setHousing(String housing) {
        this.housing = housing;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
