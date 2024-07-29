package eu.itreegroup.spark.modules.admin.logic.forms.model;

import java.util.Map;

public class Request4RefCode {

    private String lang;

    private String code;

    private Map<String, String> parameters;

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String toString() {
        return "Code: " + getCode() + " language: " + getLang();
    }
}
