package eu.itreegroup.spark.modules.admin.logic.forms.model;

import java.util.Date;
import java.util.List;

import eu.itreegroup.spark.app.model.SprListIdKeyValue;

public class ApiKey {

    private Double api_id;

    private Double api_usr_id;

    private Double org_id;

    private Double api_ou_id;

    private Double api_org_id;

    private String api_type_code;

    private String usr_username;

    private String per_name;

    private String per_surname;

    private String org_name;

    private String api_key;

    private Date api_date_from;

    private Date api_date_to;

    private List<SprListIdKeyValue> orgInfo;

    private List<SprListIdKeyValue> userInfo;

    public Double getApi_id() {
        return api_id;
    }

    public void setApi_id(Double api_id) {
        this.api_id = api_id;
    }

    public Double getApi_usr_id() {
        return api_usr_id;
    }

    public void setApi_usr_id(Double api_usr_id) {
        this.api_usr_id = api_usr_id;
    }

    public Double getApi_ou_id() {
        return api_ou_id;
    }

    public void setApi_ou_id(Double api_ou_id) {
        this.api_ou_id = api_ou_id;
    }

    public String getApi_type_code() {
        return api_type_code;
    }

    public void setApi_type_code(String api_type_code) {
        this.api_type_code = api_type_code;
    }

    public String getUsr_username() {
        return usr_username;
    }

    public void setUsr_username(String usr_username) {
        this.usr_username = usr_username;
    }

    public String getPer_name() {
        return per_name;
    }

    public void setPer_name(String per_name) {
        this.per_name = per_name;
    }

    public String getPer_surname() {
        return per_surname;
    }

    public void setPer_surname(String per_surname) {
        this.per_surname = per_surname;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public Date getApi_date_from() {
        return api_date_from;
    }

    public void setApi_date_from(Date api_date_from) {
        this.api_date_from = api_date_from;
    }

    public Date getApi_date_to() {
        return api_date_to;
    }

    public void setApi_date_to(Date api_date_to) {
        this.api_date_to = api_date_to;
    }

    @Override
    public String toString() {
        return "ApiKeys [api_id=" + api_id + ", api_usr_id=" + api_usr_id + ", api_ou_id=" + api_ou_id + ", api_belongs=" + api_type_code + ", usr_username="
                + usr_username + ", per_name=" + per_name + ", per_surname=" + per_surname + ", org_name=" + org_name + ", api_key=" + api_key
                + ", api_date_from=" + api_date_from + ", api_date_to=" + api_date_to + "]";
    }

    public Double getOrg_id() {
        return org_id;
    }

    public void setOrg_id(Double org_id) {
        this.org_id = org_id;
    }

    public Double getApi_org_id() {
        return api_org_id;
    }

    public void setApi_org_id(Double api_org_id) {
        this.api_org_id = api_org_id;
    }

    public List<SprListIdKeyValue> getOrgInfo() {
        return orgInfo;
    }

    public void setOrgInfo(List<SprListIdKeyValue> orgInfo) {
        this.orgInfo = orgInfo;
    }

    public List<SprListIdKeyValue> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(List<SprListIdKeyValue> userInfo) {
        this.userInfo = userInfo;
    }

}
