package lt.project.ntis.models;

import java.util.Date;

import eu.itreegroup.spark.annotations.TypeScriptOptional;

/**
 * Klasė skirta formos "Peržiūrėti gautą paslaugos užsakymą (išvežimas)" biznio logikai apibrėžti ir užsakymo duomenų perdavimui
 */

public class NtisOrderDetails {

    private Double ord_id;

    private String ord_name;

    private String ord_email;

    private String ord_phone_number;

    private Date ord_completion_request_date;

    private String ord_additional_description;

    private Double ord_usr_id;

    private Double ord_per_id;

    private Double ord_wtf_id;

    private Double ord_org_id;

    private Double ord_srv_id;

    private String ord_state;

    private String ord_state_clsf;

    private String ord_rejection_reason;

    private Date ord_rejection_date;

    private String ord_planned_works_description;

    private Date ord_completion_estimate_date;

    private Date ord_created;

    private String ocw_completed_works_description;

    private Date ocw_completed_date;

    private Double ocw_discharged_sludge_amount;

    private Double ocw_id;

    private Double ocw_cr_id;

    private String car_name;

    private Date ord_prefered_date_from;

    private Date ord_prefered_date_to;

    @TypeScriptOptional
    private Double ord_cs_id;

    public NtisOrderDetails() {
        super();
    }

    public NtisOrderDetails(Double ord_id, String ord_name, String ord_email, String ord_phone_number, Date ord_completion_request_date,
            String ord_additional_description, Double ord_usr_id, Double ord_wtf_id, Double ord_org_id, Double ord_per_id, Double ord_srv_id, String ord_state,
            String ord_state_clsf, String ord_rejection_reason, Date ord_rejection_date, String ord_planned_works_description,
            Date ord_completion_estimate_date, Date ord_created, String ocw_completed_works_description, Date ocw_completed_date,
            Double ocw_discharged_sludge_amount, Double ocw_id, Double ocw_cr_id, String car_name) {
        super();
        this.ord_id = ord_id;
        this.ord_name = ord_name;
        this.ord_email = ord_email;
        this.ord_phone_number = ord_phone_number;
        this.ord_completion_request_date = ord_completion_request_date;
        this.ord_additional_description = ord_additional_description;
        this.ord_usr_id = ord_usr_id;
        this.ord_per_id = ord_per_id;
        this.ord_wtf_id = ord_wtf_id;
        this.ord_srv_id = ord_srv_id;
        this.ord_state = ord_state;
        this.ord_state_clsf = ord_state_clsf;
        this.ord_rejection_reason = ord_rejection_reason;
        this.ord_rejection_date = ord_rejection_date;
        this.ord_planned_works_description = ord_planned_works_description;
        this.ord_completion_estimate_date = ord_completion_estimate_date;
        this.ocw_completed_date = ocw_completed_date;
        this.ord_created = ord_created;
        this.ocw_completed_works_description = ocw_completed_works_description;
        this.ocw_discharged_sludge_amount = ocw_discharged_sludge_amount;
        this.ocw_id = ocw_id;
        this.ocw_cr_id = ocw_cr_id;
        this.car_name = car_name;
        this.ord_org_id = ord_org_id;

    }

    public String getOrd_name() {
        return ord_name;
    }

    public void setOrd_name(String ord_name) {
        this.ord_name = ord_name;
    }

    public String getOrd_email() {
        return ord_email;
    }

    public void setOrd_email(String ord_email) {
        this.ord_email = ord_email;
    }

    public String getOrd_phone_number() {
        return ord_phone_number;
    }

    public void setOrd_phone_number(String ord_phone_number) {
        this.ord_phone_number = ord_phone_number;
    }

    public Date getOrd_completion_request_date() {
        return ord_completion_request_date;
    }

    public void setOrd_completion_request_date(Date ord_completion_request_date) {
        this.ord_completion_request_date = ord_completion_request_date;
    }

    public String getOrd_additional_description() {
        return ord_additional_description;
    }

    public void setOrd_additional_description(String ord_additional_description) {
        this.ord_additional_description = ord_additional_description;
    }

    public Double getOrd_id() {
        return ord_id;
    }

    public void setOrd_id(Double ord_id) {
        this.ord_id = ord_id;
    }

    public Double getOrd_usr_id() {
        return ord_usr_id;
    }

    public void setOrd_usr_id(Double ord_usr_id) {
        this.ord_usr_id = ord_usr_id;
    }

    public Double getOrd_wtf_id() {
        return ord_wtf_id;
    }

    public void setOrd_wtf_id(Double ord_wtf_id) {
        this.ord_wtf_id = ord_wtf_id;
    }

    public Double getOrd_srv_id() {
        return ord_srv_id;
    }

    public void setOrd_srv_id(Double ord_srv_id) {
        this.ord_srv_id = ord_srv_id;
    }

    public String getOrd_state() {
        return ord_state;
    }

    public void setOrd_state(String ord_state) {
        this.ord_state = ord_state;
    }

    public String getOrd_state_clsf() {
        return ord_state_clsf;
    }

    public void setOrd_state_clsf(String ord_state_clsf) {
        this.ord_state_clsf = ord_state_clsf;
    }

    public String getOrd_rejection_reason() {
        return ord_rejection_reason;
    }

    public void setOrd_rejection_reason(String ord_rejection_reason) {
        this.ord_rejection_reason = ord_rejection_reason;
    }

    public String getOrd_planned_works_description() {
        return ord_planned_works_description;
    }

    public void setOrd_planned_works_description(String ord_planned_works_description) {
        this.ord_planned_works_description = ord_planned_works_description;
    }

    public Date getOrd_completion_estimate_date() {
        return ord_completion_estimate_date;
    }

    public void setOrd_completion_estimate_date(Date ord_completion_estimate_date) {
        this.ord_completion_estimate_date = ord_completion_estimate_date;
    }

    public Date getOrd_created() {
        return ord_created;
    }

    public void setOrd_created(Date ord_created) {
        this.ord_created = ord_created;
    }

    public String getOcw_completed_works_description() {
        return ocw_completed_works_description;
    }

    public void setOcw_completed_works_description(String ocw_completed_works_description) {
        this.ocw_completed_works_description = ocw_completed_works_description;
    }

    public Double getOcw_discharged_sludge_amount() {
        return ocw_discharged_sludge_amount;
    }

    public void setOcw_discharged_sludge_amount(Double ocw_discharged_sludge_amount) {
        this.ocw_discharged_sludge_amount = ocw_discharged_sludge_amount;
    }

    public Date getOcw_completed_date() {
        return ocw_completed_date;
    }

    public void setOcw_completed_date(Date ocw_completed_date) {
        this.ocw_completed_date = ocw_completed_date;
    }

    public Date getOrd_rejection_date() {
        return ord_rejection_date;
    }

    public void setOrd_rejection_date(Date ord_rejection_date) {
        this.ord_rejection_date = ord_rejection_date;
    }

    public Double getOcw_id() {
        return ocw_id;
    }

    public void setOcw_id(Double ocw_id) {
        this.ocw_id = ocw_id;
    }

    public Double getOcw_cr_id() {
        return ocw_cr_id;
    }

    public void setOcw_cr_id(Double ocw_cr_id) {
        this.ocw_cr_id = ocw_cr_id;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public Double getOrd_org_id() {
        return ord_org_id;
    }

    public void setOrd_org_id(Double ord_org_id) {
        this.ord_org_id = ord_org_id;
    }

    public Date getOrd_prefered_date_from() {
        return ord_prefered_date_from;
    }

    public void setOrd_prefered_date_from(Date ord_prefered_date_from) {
        this.ord_prefered_date_from = ord_prefered_date_from;
    }

    public Date getOrd_prefered_date_to() {
        return ord_prefered_date_to;
    }

    public void setOrd_prefered_date_to(Date ord_prefered_date_to) {
        this.ord_prefered_date_to = ord_prefered_date_to;
    }

    public Double getOrd_per_id() {
        return ord_per_id;
    }

    public void setOrd_per_id(Double ord_per_id) {
        this.ord_per_id = ord_per_id;
    }

    public Double getOrd_cs_id() {
        return ord_cs_id;
    }

    public void setOrd_cs_id(Double ord_cs_id) {
        this.ord_cs_id = ord_cs_id;
    }

}
