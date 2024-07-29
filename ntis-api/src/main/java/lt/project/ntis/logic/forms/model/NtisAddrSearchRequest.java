package lt.project.ntis.logic.forms.model;

import eu.itreegroup.spark.annotations.TypeScriptOptional;

/**
 * Klasė skirta adreso paieškos duomenims perduoti
 *
 */
public class NtisAddrSearchRequest {

    private Double municipalityCode;

    private Double residenceCode;

    private Double streetCode;

    private String houseNo;

    private String flatNo;

    private Double latitude;

    private Double longitude;

    @TypeScriptOptional
    private String uniqueNo;

    public Double getMunicipalityCode() {
        return municipalityCode;
    }

    public void setMunicipalityCode(Double municipalityCode) {
        this.municipalityCode = municipalityCode;
    }

    public Double getResidenceCode() {
        return residenceCode;
    }

    public void setResidenceCode(Double residenceCode) {
        this.residenceCode = residenceCode;
    }

    public Double getStreetCode() {
        return streetCode;
    }

    public void setStreetCode(Double streetCode) {
        this.streetCode = streetCode;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
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

    public String getUniqueNo() {
        return uniqueNo;
    }

    public void setUniqueNo(String uniqueNo) {
        this.uniqueNo = uniqueNo;
    }

}
