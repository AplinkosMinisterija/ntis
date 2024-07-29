package eu.itreegroup.spark.modules.admin.logic.forms.model;

import java.util.Date;

public class UserRoleDate {

    private String uro_id;

    private Date uro_date_to;

    private Date uro_date_from;

    public String getUro_id() {
        return uro_id;
    }

    public void setUro_id(String uro_id) {
        this.uro_id = uro_id;
    }

    public Date getUro_date_to() {
        return uro_date_to;
    }

    public void setUro_date_to(Date uro_date_to) {
        this.uro_date_to = uro_date_to;
    }

    public Date getUro_date_from() {
        return uro_date_from;
    }

    public void setUro_date_from(Date uro_date_from) {
        this.uro_date_from = uro_date_from;
    }

    public String toString() {
        return "uro_id: " + uro_id + " uro_date_to: " + uro_date_to + " uro_date_from: " + uro_date_from;
    }

}
