package lt.project.ntis.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import eu.itreegroup.spark.annotations.TypeScriptOptional;

@JsonInclude(Include.NON_NULL)
public class NtisMapCentDetails {

    @TypeScriptOptional
    private String ntrNumber;

    @TypeScriptOptional
    private String municipality;

    @TypeScriptOptional
    private String placeName;

    @TypeScriptOptional
    private String street;

    @TypeScriptOptional
    private String address;

    @TypeScriptOptional
    private Double x;

    @TypeScriptOptional
    private Double y;

    public String getNtrNumber() {
        return ntrNumber;
    }

    public void setNtrNumber(String ntrNumber) {
        this.ntrNumber = ntrNumber;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

}
