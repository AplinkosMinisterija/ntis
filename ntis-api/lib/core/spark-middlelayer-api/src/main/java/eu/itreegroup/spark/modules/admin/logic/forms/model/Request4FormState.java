package eu.itreegroup.spark.modules.admin.logic.forms.model;

public class Request4FormState {

    private String rol_id;

    private Form[] enabled_forms;

    private Form4RoleRequest[] disabled_forms;

    public Form4RoleRequest[] getDisabled_forms() {
        return disabled_forms;
    }

    public void setDisabled_forms(Form4RoleRequest[] disabled_forms) {
        this.disabled_forms = disabled_forms;
    }

    public Form[] getEnabled_forms() {
        return enabled_forms;
    }

    public void setEnabled_forms(Form[] enabled_forms) {
        this.enabled_forms = enabled_forms;
    }

    public String getRol_id() {
        return rol_id;
    }

    public void setRol_id(String rol_id) {
        this.rol_id = rol_id;
    }

    public String toString() {
        String all_forms = "rol_id: " + rol_id;

        for (Form f : enabled_forms) {
            all_forms = all_forms + f.toString();
        }

        return all_forms;
    }

}
