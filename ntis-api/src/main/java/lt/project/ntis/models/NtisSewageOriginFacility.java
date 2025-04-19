package lt.project.ntis.models;

import java.util.Date;

/**
 * Klasė skirta formų "Sukurti nuotekų tvarkymo pristatymą" ir "Redaguoti nuotekų tvarkymo pristatymą (pasl. teikėjas)"  biznio logikai apibrėžti 
 * ir perduoti įrenginio, iš kurio nuotekos paimtos, duomenis ntis-sewage-origin-facility komponentui
 */

public class NtisSewageOriginFacility {

    private String ord_id;

    private String wtf_type;

    private String wtf_type_code;

    private Double wtf_id;

    private String wtf_address;

    private String name;

    private String ocw_discharged_sludge_amount;

    private Double ocw_cr_id;

    private String df_id;

    private Date ord_removed_sewage_date;

    private String type;

    public NtisSewageOriginFacility() {
        super();
    }

    public String getOrd_id() {
        return ord_id;
    }

    public void setOrd_id(String ord_id) {
        this.ord_id = ord_id;
    }

    public String getWtf_type() {
        return wtf_type;
    }

    public void setWtf_type(String wtf_type) {
        this.wtf_type = wtf_type;
    }

    public String getOcw_discharged_sludge_amount() {
        return ocw_discharged_sludge_amount;
    }

    public void setOcw_discharged_sludge_amount(String ocw_discharged_sludge_amount) {
        this.ocw_discharged_sludge_amount = ocw_discharged_sludge_amount;
    }

    public String getDf_id() {
        return df_id;
    }

    public void setDf_id(String df_id) {
        this.df_id = df_id;
    }

    public Double getWtf_id() {
        return wtf_id;
    }

    public void setWtf_id(Double wtf_id) {
        this.wtf_id = wtf_id;
    }

    public Double getOcw_cr_id() {
        return ocw_cr_id;
    }

    public void setOcw_cr_id(Double ocw_cr_id) {
        this.ocw_cr_id = ocw_cr_id;
    }

    public Date getOrd_removed_sewage_date() {
        return ord_removed_sewage_date;
    }

    public void setOrd_removed_sewage_date(Date ord_removed_sewage_date) {
        this.ord_removed_sewage_date = ord_removed_sewage_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWtf_address() {
        return wtf_address;
    }

    public void setWtf_address(String wtf_address) {
        this.wtf_address = wtf_address;
    }

    public String getWtf_type_code() {
        return wtf_type_code;
    }

    public void setWtf_type_code(String wtf_type_code) {
        this.wtf_type_code = wtf_type_code;
    }

}
