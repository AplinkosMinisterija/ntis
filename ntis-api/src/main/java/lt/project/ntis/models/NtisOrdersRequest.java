package lt.project.ntis.models;

import java.util.Date;

import eu.itreegroup.spark.annotations.TypeScriptOptional;

/**
 * Klasė skirta užsakymo modeliui apibrėžti
 *
 */
public class NtisOrdersRequest {

    private Double ord_id;

    @TypeScriptOptional
    private Double ord_wtf_id;

    @TypeScriptOptional
    private Double ord_srv_id;

    @TypeScriptOptional
    private Double ord_org_id;

    @TypeScriptOptional
    private String ord_additional_description;

    @TypeScriptOptional
    private String ord_state;

    @TypeScriptOptional
    private String ord_rejection_reason;

    @TypeScriptOptional
    private Date ord_preferred_order_date;

    @TypeScriptOptional
    private Date ord_planned_date;

    @TypeScriptOptional
    private String actionType;

    @TypeScriptOptional
    private Double ord_cs_id;

    public Double getOrd_id() {
        return ord_id;
    }

    public void setOrd_id(Double ord_id) {
        this.ord_id = ord_id;
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

    public Double getOrd_org_id() {
        return ord_org_id;
    }

    public void setOrd_org_id(Double ord_org_id) {
        this.ord_org_id = ord_org_id;
    }

    public String getOrd_additional_description() {
        return ord_additional_description;
    }

    public void setOrd_additional_description(String ord_additional_description) {
        this.ord_additional_description = ord_additional_description;
    }

    public String getOrd_state() {
        return ord_state;
    }

    public void setOrd_state(String ord_state) {
        this.ord_state = ord_state;
    }

    public String getOrd_rejection_reason() {
        return ord_rejection_reason;
    }

    public void setOrd_rejection_reason(String ord_rejection_reason) {
        this.ord_rejection_reason = ord_rejection_reason;
    }

    public Date getOrd_preferred_order_date() {
        return ord_preferred_order_date;
    }

    public void setOrd_preferred_order_date(Date ord_preferred_order_date) {
        this.ord_preferred_order_date = ord_preferred_order_date;
    }

    public Date getOrd_planned_date() {
        return ord_planned_date;
    }

    public void setOrd_planned_date(Date ord_planned_date) {
        this.ord_planned_date = ord_planned_date;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    @Override
    public String toString() {
        return "ord_id: " + ord_id + " ord_wtf_id: " + ord_wtf_id + " ord_srv_id: " + ord_srv_id + " ord_org_id: " + ord_org_id
                + " ord_additional_description: " + ord_additional_description + " ord_state: " + ord_state + " ord_rejection_reason: " + ord_rejection_reason
                + " ord_preferred_order_date: " + ord_preferred_order_date + " ord_planned_date: " + ord_planned_date;
    }

    public Double getOrd_cs_id() {
        return ord_cs_id;
    }

    public void setOrd_cs_id(Double ord_cs_id) {
        this.ord_cs_id = ord_cs_id;
    }
}