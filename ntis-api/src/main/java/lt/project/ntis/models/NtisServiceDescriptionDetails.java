package lt.project.ntis.models;

/**
 * Klasė skirta formos "Peržiūrėti gautą paslaugos užsakymą (išvežimas)" biznio logikai apibrėžti ir paslaugos aprašymo duomenų perdavimui
 */

public class NtisServiceDescriptionDetails {

    private Double srv_id;

    private String srv_org_name;

    private String srv_type;

    private String srv_name;

    private String srv_email;

    private String srv_phone_no;

    private Double srv_price_from;

    private Double srv_price_to;

    private Double srv_completion_in_days_from;

    private String srv_description;

    private Double srv_org_id;

    private String srv_address;

    private Double cs_id;

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

    public Double getSrv_completion_in_days_from() {
        return srv_completion_in_days_from;
    }

    public void setSrv_completion_in_days_from(Double srv_completion_in_days_from) {
        this.srv_completion_in_days_from = srv_completion_in_days_from;
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

    public String getSrv_address() {
        return srv_address;
    }

    public void setSrv_address(String srv_address) {
        this.srv_address = srv_address;
    }

    public Double getCs_id() {
        return cs_id;
    }

    public void setCs_id(Double cs_id) {
        this.cs_id = cs_id;
    }

}
