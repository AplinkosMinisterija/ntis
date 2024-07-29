package eu.itreegroup.spark.modules.admin.logic.forms.model;

public class Request4FormActions {

    private String rol_id;

    private Form form;

    private String availableActionsCnt;

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public String getRol_id() {
        return rol_id;
    }

    public void setRol_id(String rol_id) {
        this.rol_id = rol_id;
    }

    public String getavailableActionsCnt() {
        return availableActionsCnt;
    }

    public void setavailableActionsCnt(String availableActionsCnt) {
        this.availableActionsCnt = availableActionsCnt;
    }

    public String toString() {
        String all_forms = "rol_id: " + rol_id;

        all_forms = all_forms + form.toString();

        return all_forms;
    }

}
