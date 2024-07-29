package lt.project.ntis.logic.forms.model;

import java.util.ArrayList;
import java.util.Date;

import eu.itreegroup.spark.annotations.TypeScriptOptional;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import lt.project.ntis.dao.NtisServiceReqItemsDAO;

/**
 * Klasė skirta prašymo modeliui apibrėžti
 *
 */
public class NtisServiceReqDetails {

    private Double sr_id;

    private String sr_type;

    private Date sr_registration_date;

    private String sr_resp_person_description;

    private String sr_status;

    private String sr_status_meaning;

    private String registered_services;

    private String org_address;

    private String org_code;

    private String sr_email;

    private String sr_email_verified;

    private String org_house_number;

    private String org_name;

    private String sr_phone;

    private String org_region;

    private String org_type;

    private String sr_homepage;

    private Date sr_status_date;

    private String sr_data_is_correct;

    private String sr_rules_accepted;

    private String sr_removal_reason;

    @TypeScriptOptional
    private ArrayList<SprFile> attachments;

    @TypeScriptOptional
    private Double sr_usr_id;

    @TypeScriptOptional
    private Double sr_org_id;

    @TypeScriptOptional
    private ArrayList<NtisServiceReqItemsDAO> reqItemsDAO;

    public Double getSr_usr_id() {
        return sr_usr_id;
    }

    public void setSr_usr_id(Double sr_usr_id) {
        this.sr_usr_id = sr_usr_id;
    }

    public Double getSr_org_id() {
        return sr_org_id;
    }

    public void setSr_org_id(Double sr_org_id) {
        this.sr_org_id = sr_org_id;
    }

    public ArrayList<SprFile> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<SprFile> attachments) {
        this.attachments = attachments;
    }

    public Double getSr_id() {
        return sr_id;
    }

    public void setSr_id(Double sr_id) {
        this.sr_id = sr_id;
    }

    public Date getSr_registration_date() {
        return sr_registration_date;
    }

    public void setSr_registration_date(Date sr_registration_date) {
        this.sr_registration_date = sr_registration_date;
    }

    public String getSr_resp_person_description() {
        return sr_resp_person_description;
    }

    public void setSr_resp_person_description(String sr_resp_person_description) {
        this.sr_resp_person_description = sr_resp_person_description;
    }

    public String getSr_status() {
        return sr_status;
    }

    public void setSr_status(String sr_status) {
        this.sr_status = sr_status;
    }

    public String getSr_status_meaning() {
        return sr_status_meaning;
    }

    public void setSr_status_meaning(String sr_status_meaning) {
        this.sr_status_meaning = sr_status_meaning;
    }

    public String getRegistered_services() {
        return registered_services;
    }

    public void setRegistered_services(String registered_services) {
        this.registered_services = registered_services;
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

    public String getSr_email() {
        return sr_email;
    }

    public void setSr_email(String sr_email) {
        this.sr_email = sr_email;
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

    public String getSr_phone() {
        return sr_phone;
    }

    public void setSr_phone(String sr_phone) {
        this.sr_phone = sr_phone;
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

    public String getSr_homepage() {
        return sr_homepage;
    }

    public void setSr_homepage(String sr_homepage) {
        this.sr_homepage = sr_homepage;
    }

    public Date getSr_status_date() {
        return sr_status_date;
    }

    public void setSr_status_date(Date sr_status_date) {
        this.sr_status_date = sr_status_date;
    }

    public String getSr_type() {
        return sr_type;
    }

    public void setSr_type(String sr_type) {
        this.sr_type = sr_type;
    }

    public String getSr_data_is_correct() {
        return sr_data_is_correct;
    }

    public void setSr_data_is_correct(String sr_data_is_correct) {
        this.sr_data_is_correct = sr_data_is_correct;
    }

    public String getSr_rules_accepted() {
        return sr_rules_accepted;
    }

    public void setSr_rules_accepted(String sr_rules_accepted) {
        this.sr_rules_accepted = sr_rules_accepted;
    }

    public String getSr_email_verified() {
        return sr_email_verified;
    }

    public void setSr_email_verified(String sr_email_verified) {
        this.sr_email_verified = sr_email_verified;
    }

    public String getSr_removal_reason() {
        return sr_removal_reason;
    }

    public void setSr_removal_reason(String sr_removal_reason) {
        this.sr_removal_reason = sr_removal_reason;
    }

    public ArrayList<NtisServiceReqItemsDAO> getReqItemsDAO() {
        return reqItemsDAO;
    }

    public void setReqItemsDAO(ArrayList<NtisServiceReqItemsDAO> reqItemsDAO) {
        this.reqItemsDAO = reqItemsDAO;
    }

}
