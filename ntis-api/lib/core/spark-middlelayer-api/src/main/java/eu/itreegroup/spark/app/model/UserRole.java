package eu.itreegroup.spark.app.model;

import java.io.Serializable;

public class UserRole implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6399753752966762108L;

    private String rol_id;

    private String rol_code;

    private String rol_name;

    public String getRol_id() {
        return rol_id;
    }

    public void setRol_id(String rol_id) {
        this.rol_id = rol_id;
    }

    public String getRol_code() {
        return rol_code;
    }

    public void setRol_code(String rol_code) {
        this.rol_code = rol_code;
    }

    public String getRol_name() {
        return rol_name;
    }

    public void setRol_name(String rol_name) {
        this.rol_name = rol_name;
    }
}