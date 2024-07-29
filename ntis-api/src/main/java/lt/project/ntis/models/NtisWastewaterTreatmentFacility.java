package lt.project.ntis.models;

import java.util.Date;

/**
 * Klasė skirta formos "Peržiūrėti gautą paslaugos užsakymą (išvežimas)" biznio logikai apibrėžti ir INTS įrenginio duomenų perdavimui
 *  
 */

public class NtisWastewaterTreatmentFacility {

    private Double wtf_id;

    private String wtf_address;

    private String wtf_type;

    private String wtf_technical_passport_id;

    private String wtf_manufacturer;

    private String wtf_model;

    private Double wtf_org_id;

    private Date wtf_installation_date;

    private String wtf_distance;

    private Double wtf_usr_id;

    public NtisWastewaterTreatmentFacility() {
        super();
    }

    public NtisWastewaterTreatmentFacility(String wtf_address, String wtf_type, String wtf_technical_passport_id, String wtf_manufacturer, String wtf_model,
            Date wtf_installation_date, String wtf_distance, Double wtf_usr_id) {
        super();
        this.wtf_address = wtf_address;
        this.wtf_type = wtf_type;
        this.wtf_technical_passport_id = wtf_technical_passport_id;
        this.wtf_manufacturer = wtf_manufacturer;
        this.wtf_model = wtf_model;
        this.wtf_installation_date = wtf_installation_date;
        this.setWtf_distance(wtf_distance);
        this.setWtf_usr_id(wtf_usr_id);
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

    public String getWtf_technical_passport_id() {
        return wtf_technical_passport_id;
    }

    public void setWtf_technical_passport_id(String wtf_technical_passport_id) {
        this.wtf_technical_passport_id = wtf_technical_passport_id;
    }

    public String getWtf_manufacturer() {
        return wtf_manufacturer;
    }

    public void setWtf_manufacturer(String wtf_manufacturer) {
        this.wtf_manufacturer = wtf_manufacturer;
    }

    public String getWtf_model() {
        return wtf_model;
    }

    public void setWtf_model(String wtf_model) {
        this.wtf_model = wtf_model;
    }

    public Date getWtf_installation_date() {
        return wtf_installation_date;
    }

    public void setWtf_installation_date(Date wtf_installation_date) {
        this.wtf_installation_date = wtf_installation_date;
    }

    public Double getWtf_id() {
        return wtf_id;
    }

    public void setWtf_id(Double wtf_id) {
        this.wtf_id = wtf_id;
    }

    public Double getWtf_org_id() {
        return wtf_org_id;
    }

    public void setWtf_org_id(Double wtf_org_id) {
        this.wtf_org_id = wtf_org_id;
    }

    public String getWtf_distance() {
        return wtf_distance;
    }

    public void setWtf_distance(String wtf_distance) {
        this.wtf_distance = wtf_distance;
    }

    public Double getWtf_usr_id() {
        return wtf_usr_id;
    }

    public void setWtf_usr_id(Double wtf_usr_id) {
        this.wtf_usr_id = wtf_usr_id;
    }
}
