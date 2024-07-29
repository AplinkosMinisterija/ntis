package lt.project.ntis.logic.forms.model;

import java.util.Date;

/**
 * Klasė skirta formos "Paslaugos aprašymas"  biznio logikai apibrėžti ir duomenų perdavimui
 */

public class NtisMunicipalitiesRequest {

    private String rfc_code;

    private String rfc_meaning;

    private Double mn_id;

    private String mn_municipality;

    private Date mn_date_from;

    private Date mn_date_to;

    private Double mn_srv_id;

    private boolean isSelected;

    public Double getMn_id() {
        return mn_id;
    }

    public void setMn_id(Double mn_id) {
        this.mn_id = mn_id;
    }

    public String getMn_municipality() {
        return mn_municipality;
    }

    public void setMn_municipality(String mn_municipality) {
        this.mn_municipality = mn_municipality;
    }

    public Date getMn_date_from() {
        return mn_date_from;
    }

    public void setMn_date_from(Date mn_date_from) {
        this.mn_date_from = mn_date_from;
    }

    public Date getMn_date_to() {
        return mn_date_to;
    }

    public void setMn_date_to(Date mn_date_to) {
        this.mn_date_to = mn_date_to;
    }

    public Double getMn_srv_id() {
        return mn_srv_id;
    }

    public void setMn_srv_id(Double mn_srv_id) {
        this.mn_srv_id = mn_srv_id;
    }

    public String getRfc_code() {
        return rfc_code;
    }

    public void setRfc_code(String rfc_code) {
        this.rfc_code = rfc_code;
    }

    public String getRfc_meaning() {
        return rfc_meaning;
    }

    public void setRfc_meaning(String rfc_meaning) {
        this.rfc_meaning = rfc_meaning;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

}
