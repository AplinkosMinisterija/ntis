package lt.project.ntis.models;

import java.util.Date;

/**
 * Klasė skirta formų "Sukurti nuotekų tvarkymo pristatymą" ir "Redaguoti nuotekų tvarkymo pristatymą (pasl. teikėjas)"  biznio logikai apibrėžti 
 * ir perduoti duomenis formose naudojamam spr-select komponentui
 *  
 */

public class NtisWaterManagerSelectionModel {

    private Double id;
    
    private Double wtf_id;
    
    private Double ord_id;

    private String name;

    private Date date;

    public NtisWaterManagerSelectionModel() {
        super();
    }

    public NtisWaterManagerSelectionModel(Double id, String name) {
        super();
        this.name = name;
        this.id = id;
    }

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getWtf_id() {
        return wtf_id;
    }

    public void setWtf_id(Double wtf_id) {
        this.wtf_id = wtf_id;
    }

    public Double getOrd_id() {
        return ord_id;
    }

    public void setOrd_id(Double ord_id) {
        this.ord_id = ord_id;
    }

}
