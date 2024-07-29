package lt.project.ntis.models;

public class AddressSearch {

    private Double municipality;

    private Double city;

    private Double street;

    private String houseNr;

    private String flatNr;

    private Double longitude;

    private Double latitude;

    private String immovablePropertyNo;

    public AddressSearch() {
        super();
    }

    public Double getMunicipality() {
        return municipality;
    }

    public void setMunicipality(Double municipality) {
        this.municipality = municipality;
    }

    public Double getCity() {
        return city;
    }

    public void setCity(Double city) {
        this.city = city;
    }

    public Double getStreet() {
        return street;
    }

    public void setStreet(Double street) {
        this.street = street;
    }

    public String getHouseNr() {
        return houseNr;
    }

    public void setHouseNr(String houseNr) {
        this.houseNr = houseNr;
    }

    public String getFlatNr() {
        return flatNr;
    }

    public void setFlatNr(String flatNr) {
        this.flatNr = flatNr;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getImmovablePropertyNo() {
        return immovablePropertyNo;
    }

    public void setImmovablePropertyNo(String immovablePropertyNo) {
        this.immovablePropertyNo = immovablePropertyNo;
    }

}
