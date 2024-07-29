package lt.project.ntis.models;

import java.util.Date;

/**
 * Klasė skirta nuotekų tvarkymo įrenginio valdytojo pridėjimui ir informacijos atnaujinimui skirtų duomenų perdavimui.
 */

public class NtisFacilityManagerEditModel {

    private Double fo_id;

    private Double fo_wtf_id;

    private String per_code;

    private String per_name;

    private String per_surname;

    private Date fo_date_from;

    private Date fo_date_to;

    public Double getFo_id() {
        return fo_id;
    }

    public void setFo_id(Double fo_id) {
        this.fo_id = fo_id;
    }

    public String getPer_code() {
        return per_code;
    }

    public void setPer_code(String per_code) {
        this.per_code = per_code;
    }

    public String getPer_name() {
        return per_name;
    }

    public void setPer_name(String per_name) {
        this.per_name = per_name;
    }

    public String getPer_surname() {
        return per_surname;
    }

    public void setPer_surname(String per_surname) {
        this.per_surname = per_surname;
    }

    public Date getFo_date_from() {
        return fo_date_from;
    }

    public void setFo_date_from(Date fo_date_from) {
        this.fo_date_from = fo_date_from;
    }

    public Date getFo_date_to() {
        return fo_date_to;
    }

    public void setFo_date_to(Date fo_date_to) {
        this.fo_date_to = fo_date_to;
    }

    public Double getFo_wtf_id() {
        return fo_wtf_id;
    }

    public void setFo_wtf_id(Double fo_wtf_id) {
        this.fo_wtf_id = fo_wtf_id;
    }
}
