package lt.project.ntis.logic.forms.model;

import java.util.List;

import lt.project.ntis.models.NtisSewageOriginFacility;
import lt.project.ntis.models.NtisUsedSewageFacility;

/**
 * Klasė skirta formos "Peržiūrėti nuotekų tvarkymo pristatymą (pasl. teikėjas)"  biznio logikai apibrėžti 
 * ir ntis-sewage-delivery-info komponentui reikalingiems duomenims perduoti
 */

public class NtisSludgeDeliveryDetails {

    private Double wd_id;

    private String wd_state;

    private String wd_state_clsf;

    private String wd_delivery_date;

    private String rec_create_timestamp;

    private String wd_sewage_type;

    private Double wd_delivered_quantity;

    private Double wd_accepted_sewage_quantity;

    private Double wd_used_sludge_quantity;

    private String cr_reg_no;

    private String wto_name;

    private String wto_address;

    private String org_name;

    private String org_code;

    private String org_email;

    private String org_phone;

    private String wd_description;

    private String wd_rejection_reason;

    private String wd_delivered_wastewater_description;

    private List<NtisSewageOriginFacility> originFacilities;

    private List<NtisUsedSewageFacility> usedFacilities;

    public Double getWd_id() {
        return wd_id;
    }

    public void setWd_id(Double wd_id) {
        this.wd_id = wd_id;
    }

    public String getWd_state() {
        return wd_state;
    }

    public void setWd_state(String wd_state) {
        this.wd_state = wd_state;
    }

    public String getWd_state_clsf() {
        return wd_state_clsf;
    }

    public void setWd_state_clsf(String wd_state_clsf) {
        this.wd_state_clsf = wd_state_clsf;
    }

    public String getWd_delivery_date() {
        return wd_delivery_date;
    }

    public void setWd_delivery_date(String wd_delivery_date) {
        this.wd_delivery_date = wd_delivery_date;
    }

    public String getRec_create_timestamp() {
        return rec_create_timestamp;
    }

    public void setRec_create_timestamp(String rec_create_timestamp) {
        this.rec_create_timestamp = rec_create_timestamp;
    }

    public String getWd_sewage_type() {
        return wd_sewage_type;
    }

    public void setWd_sewage_type(String wd_sewage_type) {
        this.wd_sewage_type = wd_sewage_type;
    }

    public Double getWd_delivered_quantity() {
        return wd_delivered_quantity;
    }

    public void setWd_delivered_quantity(Double wd_delivered_quantity) {
        this.wd_delivered_quantity = wd_delivered_quantity;
    }

    public Double getWd_accepted_sewage_quantity() {
        return wd_accepted_sewage_quantity;
    }

    public void setWd_accepted_sewage_quantity(Double wd_accepted_sewage_quantity) {
        this.wd_accepted_sewage_quantity = wd_accepted_sewage_quantity;
    }

    public Double getWd_used_sludge_quantity() {
        return wd_used_sludge_quantity;
    }

    public void setWd_used_sludge_quantity(Double wd_used_sludge_quantity) {
        this.wd_used_sludge_quantity = wd_used_sludge_quantity;
    }

    public String getCr_reg_no() {
        return cr_reg_no;
    }

    public void setCr_reg_no(String cr_reg_no) {
        this.cr_reg_no = cr_reg_no;
    }

    public String getWto_name() {
        return wto_name;
    }

    public void setWto_name(String wto_name) {
        this.wto_name = wto_name;
    }

    public String getWto_address() {
        return wto_address;
    }

    public void setWto_address(String wto_address) {
        this.wto_address = wto_address;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
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

    public String getOrg_phone() {
        return org_phone;
    }

    public void setOrg_phone(String org_phone) {
        this.org_phone = org_phone;
    }

    public String getWd_description() {
        return wd_description;
    }

    public void setWd_description(String wd_description) {
        this.wd_description = wd_description;
    }

    public String getWd_rejection_reason() {
        return wd_rejection_reason;
    }

    public void setWd_rejection_reason(String wd_rejection_reason) {
        this.wd_rejection_reason = wd_rejection_reason;
    }

    public String getWd_delivered_wastewater_description() {
        return wd_delivered_wastewater_description;
    }

    public void setWd_delivered_wastewater_description(String wd_delivered_wastewater_description) {
        this.wd_delivered_wastewater_description = wd_delivered_wastewater_description;
    }

    public List<NtisSewageOriginFacility> getOriginFacilities() {
        return originFacilities;
    }

    public void setOriginFacilities(List<NtisSewageOriginFacility> originFacilities) {
        this.originFacilities = originFacilities;
    }

    public List<NtisUsedSewageFacility> getUsedFacilities() {
        return usedFacilities;
    }

    public void setUsedFacilities(List<NtisUsedSewageFacility> usedFacilities) {
        this.usedFacilities = usedFacilities;
    }

}