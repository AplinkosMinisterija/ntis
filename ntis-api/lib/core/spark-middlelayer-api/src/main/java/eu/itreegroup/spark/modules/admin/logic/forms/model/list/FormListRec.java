package eu.itreegroup.spark.modules.admin.logic.forms.model.list;

import eu.itreegroup.spark.dto.RecordBase;

public class FormListRec extends RecordBase {

    Double frm_id;

    String frm_code;

    String frm_name;

    String frm_description;

    String rec_create_timestamp;

    public Double getFrm_id() {
        return frm_id;
    }

    public void setFrm_id(Double frm_id) {
        this.frm_id = frm_id;
    }

    public String getFrm_code() {
        return frm_code;
    }

    public void setFrm_code(String frm_code) {
        this.frm_code = frm_code;
    }

    public String getFrm_name() {
        return frm_name;
    }

    public void setFrm_name(String frm_name) {
        this.frm_name = frm_name;
    }

    public String getFrm_description() {
        return frm_description;
    }

    public void setFrm_description(String frm_description) {
        this.frm_description = frm_description;
    }

    public String getRec_create_timestamp() {
        return rec_create_timestamp;
    }

    public void setRec_create_timestamp(String rec_create_timestamp) {
        this.rec_create_timestamp = rec_create_timestamp;
    }
}
