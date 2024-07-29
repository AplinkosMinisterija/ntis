package eu.itreegroup.spark.modules.admin.logic.forms.model;

import java.util.Date;

public class OrganizationRoleRequest {

    private String org_id;

    private String rol_id;

    private String oar_id;

    private Date oar_date_from;

    private Date oar_date_to;

    private boolean isSelected;

    public String getOrg_id() {
        return org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }

    public String getRol_id() {
        return rol_id;
    }

    public void setRol_id(String rol_id) {
        this.rol_id = rol_id;
    }

    public String getOar_id() {
        return oar_id;
    }

    public void setOar_id(String oar_id) {
        this.oar_id = oar_id;
    }

    public Date getOar_date_from() {
        return oar_date_from;
    }

    public void setOar_date_from(Date oar_date_from) {
        this.oar_date_from = oar_date_from;
    }

    public Date getOar_date_to() {
        return oar_date_to;
    }

    public void setOar_date_to(Date oar_date_to) {
        this.oar_date_to = oar_date_to;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    @Override
    public String toString() {
        return "org_id: " + org_id + " rol_id: " + rol_id + " oar_id: " + oar_id + " isSelected: " + isSelected;
    }
}
