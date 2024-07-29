package lt.project.ntis.models;

/**
 * Klasė skirta formos "INTS savininko pagrindinio puslapio"  biznio logikai apibrėžti ir bloko
 * "NUOTEKŲ TVARKYMO ĮRENGINIAI (INTS savininko darbo aplinka)" duomenų perdavimui
 */

public class NtisINTSDashboardWastewater {

    private Double wtf_id;

    private String wtf_address;

    private String wtf_fua_state;

    private String fua_ID;

    private String wtf_type;

    private String fo_owner_type;

    private String wtf_state;

    private String wtf_served_objects;

    private String fo_selected;

    private Double longitude;

    private Double latitude;

    private Double ful_id;

    public Double getWtf_id() {
        return wtf_id;
    }

    public void setWtf_id(Double wtf_id) {
        this.wtf_id = wtf_id;
    }

    public String getWtf_address() {
        return wtf_address;
    }

    public void setWtf_address(String wtf_address) {
        this.wtf_address = wtf_address;
    }

    public String getWtf_type() {
        return wtf_type;
    }

    public void setWtf_type(String wtf_type) {
        this.wtf_type = wtf_type;
    }

    public String getWtf_state() {
        return wtf_state;
    }

    public void setWtf_state(String wtf_state) {
        this.wtf_state = wtf_state;
    }

    public String getWtf_fua_state() {
        return wtf_fua_state;
    }

    public void setWtf_fua_state(String wtf_fua_state) {
        this.wtf_fua_state = wtf_fua_state;
    }

    public String getWtf_served_objects() {
        return wtf_served_objects;
    }

    public void setWtf_served_objects(String wtf_served_objects) {
        this.wtf_served_objects = wtf_served_objects;
    }

    public String getFo_selected() {
        return fo_selected;
    }

    public void setFo_selected(String fo_selected) {
        this.fo_selected = fo_selected;
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

    public String getFo_owner_type() {
        return fo_owner_type;
    }

    public void setFo_owner_type(String fo_owner_type) {
        this.fo_owner_type = fo_owner_type;
    }

    public String getFua_id() {
        return fua_ID;
    }

    public void setFua_id(String fua_id) {
        this.fua_ID = fua_id;
    }

    public String getFua_ID() {
        return fua_ID;
    }

    public void setFua_ID(String fua_ID) {
        this.fua_ID = fua_ID;
    }

    public Double getFul_id() {
        return ful_id;
    }

    public void setFul_id(Double ful_id) {
        this.ful_id = ful_id;
    }
}