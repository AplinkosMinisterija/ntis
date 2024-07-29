package eu.itreegroup.spark.modules.common.rest.model;

public class ChangePasswordByEmailRequest {

    private String email;

    private String lang;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLang() {
        return this.lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

}
