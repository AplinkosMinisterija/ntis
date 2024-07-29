package lt.project.ntis.logic.forms.model;

import java.util.Date;

/**
 * Klasė skirta naujo prašymo informacijos modeliui apibrėžti
 *
 */
public class NtisNewServiceRequest {

    private Double usr_id;

    private String per_name;

    private String per_surname;

    private String per_date_of_birth;

    private String org_address;

    private String org_code;

    private String org_email;

    private String org_house_number;

    private String org_name;

    private String org_phone;

    private String org_region;

    private String org_type;

    private String org_website;
    
    private Date org_installation_from;

    public Double getUsr_id() {
        return usr_id;
    }

    public void setUsr_id(Double usr_id) {
        this.usr_id = usr_id;
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

    public String getPer_date_of_birth() {
        return per_date_of_birth;
    }

    public void setPer_date_of_birth(String per_date_of_birth) {
        this.per_date_of_birth = per_date_of_birth;
    }

    public String getOrg_address() {
        return org_address;
    }

    public void setOrg_address(String org_address) {
        this.org_address = org_address;
    }

    public String getOrg_code() {
        return org_code;
    }

    public void setOrg_code(String org_code) {
        this.org_code = org_code;
    }

    public String getOrg_email() {
        return org_email;
    }

    public void setOrg_email(String org_email) {
        this.org_email = org_email;
    }

    public String getOrg_house_number() {
        return org_house_number;
    }

    public void setOrg_house_number(String org_house_number) {
        this.org_house_number = org_house_number;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getOrg_phone() {
        return org_phone;
    }

    public void setOrg_phone(String org_phone) {
        this.org_phone = org_phone;
    }

    public String getOrg_region() {
        return org_region;
    }

    public void setOrg_region(String org_region) {
        this.org_region = org_region;
    }

    public String getOrg_type() {
        return org_type;
    }

    public void setOrg_type(String org_type) {
        this.org_type = org_type;
    }

    public String getOrg_website() {
        return org_website;
    }

    public void setOrg_website(String org_website) {
        this.org_website = org_website;
    }

    public Date getOrg_installation_from() {
        return org_installation_from;
    }

    public void setOrg_installation_from(Date org_installation_from) {
        this.org_installation_from = org_installation_from;
    }
}
