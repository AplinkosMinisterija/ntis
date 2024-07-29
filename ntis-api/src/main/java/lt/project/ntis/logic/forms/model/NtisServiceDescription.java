package lt.project.ntis.logic.forms.model;

import java.util.Date;

/**
 * Klasė skirta formos "Paslaugų aprašymas" biznio logikai apibrėžti ir duomenų perdavimui
 */

public class NtisServiceDescription {

    private Double srv_id;

    private String srv_org_name;

    private String srv_type;

    private String srv_name;

    private String srv_contract_available;

    private String srv_available_in_ntis_portal;

    private String srv_lithuanian_level;

    private String srv_email;

    private String srv_phone_no;

    private Double srv_price_from;

    private Double srv_price_to;

    private Double srv_completion_in_days_from;

    private Double srv_completion_in_days_to;

    private String srv_description;

    private Double srv_org_id;

    private Double srv_fil_id;

    private Double srv_lab_instr_fil_id;

    private Date srv_date_from;

    private Date srv_date_to;

    private Double sri_id;

    public Double getSrv_id() {
        return srv_id;
    }

    public void setSrv_id(Double srv_id) {
        this.srv_id = srv_id;
    }

    public String getSrv_type() {
        return srv_type;
    }

    public void setSrv_type(String srv_type) {
        this.srv_type = srv_type;
    }

    public String getSrv_contract_available() {
        return srv_contract_available;
    }

    public void setSrv_contract_available(String srv_contract_available) {
        this.srv_contract_available = srv_contract_available;
    }

    public String getSrv_available_in_ntis_portal() {
        return srv_available_in_ntis_portal;
    }

    public void setSrv_available_in_ntis_portal(String srv_available_in_ntis_portal) {
        this.srv_available_in_ntis_portal = srv_available_in_ntis_portal;
    }

    public String getSrv_lithuanian_level() {
        return srv_lithuanian_level;
    }

    public void setSrv_lithuanian_level(String srv_lithuanian_level) {
        this.srv_lithuanian_level = srv_lithuanian_level;
    }

    public String getSrv_email() {
        return srv_email;
    }

    public void setSrv_email(String srv_email) {
        this.srv_email = srv_email;
    }

    public String getSrv_phone_no() {
        return srv_phone_no;
    }

    public void setSrv_phone_no(String srv_phone_no) {
        this.srv_phone_no = srv_phone_no;
    }

    public Double getSrv_price_from() {
        return srv_price_from;
    }

    public void setSrv_price_from(Double srv_price_from) {
        this.srv_price_from = srv_price_from;
    }

    public Double getSrv_price_to() {
        return srv_price_to;
    }

    public void setSrv_price_to(Double srv_price_to) {
        this.srv_price_to = srv_price_to;
    }

    public String getSrv_description() {
        return srv_description;
    }

    public void setSrv_description(String srv_description) {
        this.srv_description = srv_description;
    }

    public Double getSrv_org_id() {
        return srv_org_id;
    }

    public void setSrv_org_id(Double srv_org_id) {
        this.srv_org_id = srv_org_id;
    }

    public String getSrv_org_name() {
        return srv_org_name;
    }

    public void setSrv_org_name(String srv_org_name) {
        this.srv_org_name = srv_org_name;
    }

    public String getSrv_name() {
        return srv_name;
    }

    public void setSrv_name(String srv_name) {
        this.srv_name = srv_name;
    }

    public Double getSrv_fil_id() {
        return srv_fil_id;
    }

    public void setSrv_fil_id(Double srv_fil_id) {
        this.srv_fil_id = srv_fil_id;
    }

    public Date getSrv_date_from() {
        return srv_date_from;
    }

    public void setSrv_date_from(Date srv_date_from) {
        this.srv_date_from = srv_date_from;
    }

    public Date getSrv_date_to() {
        return srv_date_to;
    }

    public void setSrv_date_to(Date srv_date_to) {
        this.srv_date_to = srv_date_to;
    }

    public Double getSri_id() {
        return sri_id;
    }

    public void setSri_id(Double sri_id) {
        this.sri_id = sri_id;
    }

    public Double getSrv_completion_in_days_to() {
        return srv_completion_in_days_to;
    }

    public void setSrv_completion_in_days_to(Double srv_completion_in_days_to) {
        this.srv_completion_in_days_to = srv_completion_in_days_to;
    }

    public Double getSrv_completion_in_days_from() {
        return srv_completion_in_days_from;
    }

    public void setSrv_completion_in_days_from(Double srv_completion_in_days_from) {
        this.srv_completion_in_days_from = srv_completion_in_days_from;
    }

    public Double getSrv_lab_instr_fil_id() {
        return srv_lab_instr_fil_id;
    }

    public void setSrv_lab_instr_fil_id(Double srv_lab_instr_fil_id) {
        this.srv_lab_instr_fil_id = srv_lab_instr_fil_id;
    }
}
