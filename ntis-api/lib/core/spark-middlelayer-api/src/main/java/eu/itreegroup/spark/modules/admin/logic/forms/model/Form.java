package eu.itreegroup.spark.modules.admin.logic.forms.model;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Form {

    private String roa_id;

    private String frm_id;

    private Date roa_date_to;

    private Date roa_date_from;

    private String enabled_actions[];

    private String disabled_actions[];

    public void setRoa_date_from(Date roa_date_from) {
        this.roa_date_from = roa_date_from;
    }

    public Date getRoa_date_from() {
        return roa_date_from;
    }

    public void setRoa_date_to(Date roa_date_to) {
        this.roa_date_to = roa_date_to;
    }

    public Date getRoa_date_to() {
        return roa_date_to;
    }

    public String[] getEnabled_actions() {
        return enabled_actions;
    }

    public void setEnabled_actions(String[] enabled_actions) {
        this.enabled_actions = enabled_actions;
    }

    public String[] getDisabled_actions() {
        return disabled_actions;
    }

    public void setDisabled_actions(String[] disabled_actions) {
        this.disabled_actions = disabled_actions;
    }

    public String getRoa_id() {
        return roa_id;
    }

    public void setRoa_id(String roa_id) {
        this.roa_id = roa_id;
    }

    public String getFrm_id() {
        return frm_id;
    }

    public void setFrm_id(String frm_id) {
        this.frm_id = frm_id;
    }

    public String getArray() {
        StringBuilder ids = new StringBuilder();
        Arrays.asList(disabled_actions).forEach(id -> ids.append(id).append(","));
        ids.setLength(ids.length() - 1);
        String strIds = ids.toString();
        return strIds;
    }

    public Set<String> getSet() {
        Set<String> disabled_actions = new HashSet<String>();

        for (String fra_id : this.disabled_actions) {
            disabled_actions.add(fra_id);
        }

        return disabled_actions;
    }

    public String toString() {
        String actions = "roa_id: " + roa_id + "form_id" + frm_id + " disabled_actions: ";

        for (String a : disabled_actions) {
            actions = actions + a;
        }

        return actions;
    }

}
