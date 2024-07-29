package eu.itreegroup.spark.modules.admin.logic.forms.model;

public class Form4RoleRequest {

    private String roa_id;

    private String frm_id;

    public String getRoa_id() {
        return roa_id;
    }

    public void setRoa_id(String roa_id) {
        this.roa_id = roa_id;
    }

    public String getFrm_id() {
        return frm_id;
    }

    public void setFrm_id(String frm_id) {
        this.frm_id = frm_id;
    }

    public String toString() {
        return "roa_id: " + roa_id + " frm_id: " + frm_id;
    }

}
