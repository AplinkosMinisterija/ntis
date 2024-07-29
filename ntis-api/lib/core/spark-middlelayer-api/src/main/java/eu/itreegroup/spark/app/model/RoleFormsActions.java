package eu.itreegroup.spark.app.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

public class RoleFormsActions implements Serializable {

    private static final long serialVersionUID = -7226728317763739743L;

    private String roleName;

    private String roleId;

    HashMap<String, FormActions> roleForms;

    String defaultFormUrl;

    public RoleFormsActions() {
        roleForms = new HashMap<String, FormActions>();
    }

    public RoleFormsActions(String roleName) {
        roleForms = new HashMap<String, FormActions>();
        setRoleName(roleName);

    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public String getRoleId() {
        return this.roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public HashMap<String, FormActions> getRoleForms() {
        return this.roleForms;
    }

    public void addForm(String formName, Collection<String> allActions, Collection<String> availableActions) {
        FormActions form = new FormActions(formName, allActions, availableActions);
        addForm(formName, form);
    }

    public void addForm(String formName, Collection<String> availableActions) {
        FormActions form = new FormActions(formName, availableActions);
        addForm(formName, form);
    }

    public void addForm(String formName, FormActions formActions) {
        roleForms.put(formName, formActions);
    }

    public FormActions getFormActions(String formName) {
        return roleForms.get(formName);
    }

    public boolean isActionAvailable(String form, String action) {
        boolean answer = false;
        FormActions formAction = roleForms.get(form);
        if (formAction != null) {
            answer = formAction.isActionAvailable(action);
        }
        return answer;
    }

    public String getDefaultFormUrl() {
        return defaultFormUrl;
    }

    public void setDefaultFormUrl(String defaultFormUrl) {
        this.defaultFormUrl = defaultFormUrl;
    }

    @Override
    public String toString() {
        return "Role name: " + getRoleName() + " forms:" + roleForms;
    }
}
