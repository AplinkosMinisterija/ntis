package eu.itreegroup.spark.modules.admin.logic.forms.model;

import java.util.Date;

public class UserRoleRequest {

    private String user_id;

    private String rol_id;

    private String uro_id;

    private Date uro_date_from;

    private Date uro_date_to;

    private boolean update;

    public double getUser_id() {
        return Double.parseDouble(user_id);
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRol_id() {
        return rol_id;
    }

    public double parseRol_idToDouble() {
        return Double.parseDouble(rol_id);
    }

    public void setRol_id(String rol_id) {
        this.rol_id = rol_id;
    }

    public double parseUro_idToDouble() {
        return Double.parseDouble(uro_id);
    }

    public void setUro_id(String uro_id) {
        this.uro_id = uro_id;
    }

    public String getUro_id() {
        return uro_id;
    }

    public void setUro_date_from(Date uro_date_from) {
        this.uro_date_from = uro_date_from;
    }

    public Date getUro_date_from() {
        return uro_date_from;
    }

    public void setUro_date_to(Date uro_date_to) {
        this.uro_date_to = uro_date_to;
    }

    public Date getUro_date_to() {
        return uro_date_to;
    }

    public boolean getUpdate() {
        return update;
    }

    public String toString() {
        return "user_id: " + user_id + " rol_id: " + rol_id + " uro_id: " + uro_id + " update: " + update;
    }
}
