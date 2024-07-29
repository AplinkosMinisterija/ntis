package eu.itreegroup.spark.modules.admin.logic.forms.model;

import java.util.Date;

public class OrgUserRoleRequest {

    public OrgUserRoleRequest(Double user_id, Double rol_id, Double ou_id, Double our_id, boolean isSelected, boolean update, Date our_date_from,
            Date our_date_to) {
        super();
        this.user_id = user_id;
        this.rol_id = rol_id;
        this.ou_id = ou_id;
        this.our_id = our_id;
        this.isSelected = isSelected;
        this.update = update;
        this.our_date_from = our_date_from;
        this.our_date_to = our_date_to;
    }

    public OrgUserRoleRequest() {
        super();
    }

    private Double user_id;

    private Double rol_id;

    private Double ou_id;

    private Double our_id;

    private boolean isSelected;

    private boolean update;

    private Date our_date_from;

    private Date our_date_to;

    public Double getUser_id() {
        return user_id;
    }

    public void setUser_id(Double user_id) {
        this.user_id = user_id;
    }

    public Double getRol_id() {
        return rol_id;
    }

    public void setRol_id(Double rol_id) {
        this.rol_id = rol_id;
    }

    public Double getOu_id() {
        return ou_id;
    }

    public void setOu_id(Double ou_id) {
        this.ou_id = ou_id;
    }

    public Double getOur_id() {
        return our_id;
    }

    public void setOur_id(Double our_id) {
        this.our_id = our_id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean getUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public Date getOur_date_from() {
        return our_date_from;
    }

    public void setOur_date_from(Date our_date_from) {
        this.our_date_from = our_date_from;
    }

    public Date getOur_date_to() {
        return our_date_to;
    }

    public void setOur_date_to(Date our_date_to) {
        this.our_date_to = our_date_to;
    }

}
