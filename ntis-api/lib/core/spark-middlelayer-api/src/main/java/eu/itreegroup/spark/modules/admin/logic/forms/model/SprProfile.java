package eu.itreegroup.spark.modules.admin.logic.forms.model;

import eu.itreegroup.spark.annotations.TypeScriptOptional;

public class SprProfile {

    private Boolean emailNotifications;

    private String per_code;

    private String per_name;

    private String per_surname;

    private String per_phone_number;

    private String per_email;

    private Boolean per_email_confirmed;

    private String per_address_country_code;

    private String per_address_street;

    private String per_address_house_number;

    private String per_address_flat_number;

    private String per_address_city;

    private String per_address_post_code;

    private String api_key;

    private Double api_id;

    @TypeScriptOptional
    private Boolean linkAccountsAvailable;

    public Boolean getEmailNotifications() {
        return emailNotifications;
    }

    public void setEmailNotifications(Boolean emailNotifications) {
        this.emailNotifications = emailNotifications;
    }

    public String getPer_phone_number() {
        return this.per_phone_number;
    }

    public String getPer_email() {
        return this.per_email;
    }

    public String getPer_code() {
        return per_code;
    }

    public String getPer_name() {
        return per_name;
    }

    public String getPer_surname() {
        return per_surname;
    }

    public String getPer_address_country_code() {
        return per_address_country_code;
    }

    public String getPer_address_street() {
        return per_address_street;
    }

    public String getPer_address_house_number() {
        return per_address_house_number;
    }

    public String getPer_address_flat_number() {
        return per_address_flat_number;
    }

    public String getPer_address_city() {
        return per_address_city;
    }

    public String getPer_address_post_code() {
        return per_address_post_code;
    }

    public void setPer_code(String per_code) {
        this.per_code = per_code;
    }

    public void setPer_name(String per_name) {
        this.per_name = per_name;
    }

    public void setPer_surname(String per_surname) {
        this.per_surname = per_surname;
    }

    public void setPer_phone_number(String per_phone_number) {
        this.per_phone_number = per_phone_number;
    }

    public void setPer_email(String per_email) {
        this.per_email = per_email;
    }

    public void setPer_email_confirmed(Boolean per_email_confirmed) {
        this.per_email_confirmed = per_email_confirmed;
    }

    public boolean getPer_email_confirmed() {
        return this.per_email_confirmed;
    }

    public void setPer_address_country_code(String per_address_country_code) {
        this.per_address_country_code = per_address_country_code;
    }

    public void setPer_address_street(String per_address_street) {
        this.per_address_street = per_address_street;
    }

    public void setPer_address_house_number(String per_address_house_number) {
        this.per_address_house_number = per_address_house_number;
    }

    public void setPer_address_flat_number(String per_address_flat_number) {
        this.per_address_flat_number = per_address_flat_number;
    }

    public void setPer_address_city(String per_address_city) {
        this.per_address_city = per_address_city;
    }

    public void setPer_address_post_code(String per_address_post_code) {
        this.per_address_post_code = per_address_post_code;
    }

    public Boolean getLinkAccountsAvailable() {
        return linkAccountsAvailable;
    }

    public void setLinkAccountsAvailable(Boolean linkAccountsAvailable) {
        this.linkAccountsAvailable = linkAccountsAvailable;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public Double getApi_id() {
        return api_id;
    }

    public void setApi_id(Double api_id) {
        this.api_id = api_id;
    }

}
