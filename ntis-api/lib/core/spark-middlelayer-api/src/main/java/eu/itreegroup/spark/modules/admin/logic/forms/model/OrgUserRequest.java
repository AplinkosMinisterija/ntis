package eu.itreegroup.spark.modules.admin.logic.forms.model;

import java.util.Date;

public class OrgUserRequest {

    private String ou_org_id;

    private String ou_usr_id;

    private String usr_id;

    private String org_id;

    private String ou_id;

    private String ou_position;

    private Date ou_date_from;

    private Date ou_date_to;

    private boolean isSelected;

    public String getOu_org_id() {
        return ou_org_id;
    }

    public void setOu_org_id(String ou_org_id) {
        this.ou_org_id = ou_org_id;
    }

    public String getOu_usr_id() {
        return ou_usr_id;
    }

    public void setOu_usr_id(String ou_usr_id) {
        this.ou_usr_id = ou_usr_id;
    }

    public String getUsr_id() {
        return usr_id;
    }

    public void setUsr_id(String usr_id) {
        this.usr_id = usr_id;
    }

    public String getOrg_id() {
        return org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }

    public String getOu_id() {
        return ou_id;
    }

    public void setOu_id(String ou_id) {
        this.ou_id = ou_id;
    }

    public String getOu_position() {
        return ou_position;
    }

    public void setOu_position(String ou_position) {
        this.ou_position = ou_position;
    }

    public Date getOu_date_from() {
        return ou_date_from;
    }

    public void setOu_date_from(Date ou_date_from) {
        this.ou_date_from = ou_date_from;
    }

    public Date getOu_date_to() {
        return ou_date_to;
    }

    public void setOu_date_to(Date ou_date_to) {
        this.ou_date_to = ou_date_to;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    @Override
    public String toString() {
        return "ou_org_id: " + ou_org_id + "ou_usr_id: " + ou_usr_id + " usr_id: " + usr_id + " org_id: " + org_id + " ou_id: " + ou_id + " ou_position: "
                + ou_position + " isSelected: " + isSelected;
    }
}
