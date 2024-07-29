/**
 * Model is intended for detailed address search
 */

package lt.project.ntis.models;

public class AddressSearchResponse {

    private Double ntr_building_id;

    private Double address_id;

    private String full_address_text;

    private String purpose_of_building;

    private Double latitude;

    private Double longitude;

    private String inv_code;

    public AddressSearchResponse() {
        super();
    }

    public Double getNtr_building_id() {
        return ntr_building_id;
    }

    public void setNtr_building_id(Double ntr_building_id) {
        this.ntr_building_id = ntr_building_id;
    }

    public Double getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Double address_id) {
        this.address_id = address_id;
    }

    public String getFull_address_text() {
        return full_address_text;
    }

    public void setFull_address_text(String full_address_text) {
        this.full_address_text = full_address_text;
    }

    public String getPurpose_of_building() {
        return purpose_of_building;
    }

    public void setPurpose_of_building(String purpose_of_building) {
        this.purpose_of_building = purpose_of_building;
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

    public String getInv_code() {
        return inv_code;
    }

    public void setInv_code(String inv_code) {
        this.inv_code = inv_code;
    }

}
