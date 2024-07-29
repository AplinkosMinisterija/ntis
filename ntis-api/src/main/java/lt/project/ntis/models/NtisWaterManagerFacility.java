package lt.project.ntis.models;

/**
 * Klasė skirta formos "Valyklų sąrašas"  biznio logikai apibrėžti ir redaguojamos valyklos duomenų perdavimui
 *  
 */

public class NtisWaterManagerFacility {

    private Double wto_id;

    private String wto_name;

    private String wto_address;

    private Double wto_productivity;

    private String wto_domestic_sewage;

    private String wto_industrial_sewage;

    private String wto_sludge;

    private String wto_is_it_used;

    private Double address_id;

    private String wto_auto_accept;

    private String wto_no_notifications;

    public NtisWaterManagerFacility() {
        super();
    }

    public Double getWto_id() {
        return wto_id;
    }

    public void setWto_id(Double wto_id) {
        this.wto_id = wto_id;
    }

    public String getWto_name() {
        return wto_name;
    }

    public void setWto_name(String wto_name) {
        this.wto_name = wto_name;
    }

    public String getWto_domestic_sewage() {
        return wto_domestic_sewage;
    }

    public void setWto_domestic_sewage(String wto_domestic_sewage) {
        this.wto_domestic_sewage = wto_domestic_sewage;
    }

    public Double getWto_productivity() {
        return wto_productivity;
    }

    public void setWto_productivity(Double wto_productivity) {
        this.wto_productivity = wto_productivity;
    }

    public String getWto_industrial_sewage() {
        return wto_industrial_sewage;
    }

    public void setWto_industrial_sewage(String wto_industrial_sewage) {
        this.wto_industrial_sewage = wto_industrial_sewage;
    }

    public String getWto_sludge() {
        return wto_sludge;
    }

    public void setWto_sludge(String wto_sludge) {
        this.wto_sludge = wto_sludge;
    }

    public String getWto_is_it_used() {
        return wto_is_it_used;
    }

    public void setWto_is_it_used(String wto_is_it_used) {
        this.wto_is_it_used = wto_is_it_used;
    }

    public Double getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Double address_id) {
        this.address_id = address_id;
    }

    public String getWto_address() {
        return wto_address;
    }

    public void setWto_address(String wto_address) {
        this.wto_address = wto_address;
    }

    public String getWto_auto_accept() {
        return wto_auto_accept;
    }

    public void setWto_auto_accept(String wto_auto_accept) {
        this.wto_auto_accept = wto_auto_accept;
    }

    public String getWto_no_notifications() {
        return wto_no_notifications;
    }

    public void setWto_no_notifications(String wto_no_notifications) {
        this.wto_no_notifications = wto_no_notifications;
    }

}
