package eu.itreegroup.spark.modules.admin.logic.forms.model;

public class RoleRolesRecord {

    private String roa_assigned_rol_id;

    private String roa_rol_id;

    public String getRoa_assigned_rol_id() {
        return roa_assigned_rol_id;
    }

    public void setRoa_assigned_rol_id(String roa_assigned_rol_id) {
        this.roa_assigned_rol_id = roa_assigned_rol_id;
    }

    public String getRoa_rol_id() {
        return roa_rol_id;
    }

    public void setRoa_rol_id(String roa_rol_id) {
        this.roa_rol_id = roa_rol_id;
    }

    public String toString() {
        return " roa_assigned_rol_id: " + roa_assigned_rol_id + " roa_rol_id: " + roa_rol_id;
    }

}
