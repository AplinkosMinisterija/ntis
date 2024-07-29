package lt.project.ntis.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import lt.project.ntis.dao.NtisServedObjectsDAO;

public class NtisWastewaterTreatmentFaci {

    private AddressSearchResponse dischargeWastewaterAddr;

    private AddressSearchResponse facilityLocationAddr;

    private ArrayList<NtisServedObjectsDAO> servedObjects;

    private ArrayList<SprFile> attachments;

    private Date wtf_checkout_date;

    private Date wtf_installation_date;

    private Double wtf_discharge_latitude;

    private Double wtf_discharge_longitude;

    private Double wtf_distance;

    private Double wtf_facility_latitude;

    private Double wtf_facility_longitude;

    private Double wtf_ad_id;

    private Integer wtf_id;

    private String wtf_capacity;

    private String wtf_discharge_type;

    private String wtf_manufacturer;

    private String wtf_manufacturer_description;

    private String wtf_model;

    private String wtf_state;

    private String wtf_technical_passport_id;

    private String wtf_type;
    
    private Integer fam_rfc_id;
    
    private Double fam_pop_equivalent;

    private Double fam_chds;
    
    private Double fam_bds;
    
    private Double fam_float_material;
    
    private Double fam_phosphor;
    
    private Double fam_nitrogen;
    
    private String wtf_identification_number;
    
    private Double wtf_fam_id;
    
    private String fam_description;
    
    private String fam_model;
    
    private String fam_manufacturer;
    
    private String wtf_data_source;

    private List<AddressSearchResponse> servedObjectsAddr;

    public NtisWastewaterTreatmentFaci() {
        super();
    }

    public AddressSearchResponse getDischargeWastewaterAddr() {
        return dischargeWastewaterAddr;
    }

    public void setDischargeWastewaterAddr(AddressSearchResponse dischargeWastewaterAddr) {
        this.dischargeWastewaterAddr = dischargeWastewaterAddr;
    }

    public AddressSearchResponse getFacilityLocationAddr() {
        return facilityLocationAddr;
    }

    public void setFacilityLocationAddr(AddressSearchResponse facilityLocationAddr) {
        this.facilityLocationAddr = facilityLocationAddr;
    }

    public ArrayList<NtisServedObjectsDAO> getServedObjects() {
        return servedObjects;
    }

    public void setServedObjects(ArrayList<NtisServedObjectsDAO> servedObjects) {
        this.servedObjects = servedObjects;
    }

    public ArrayList<SprFile> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<SprFile> attachments) {
        this.attachments = attachments;
    }

    public Date getWtf_checkout_date() {
        return wtf_checkout_date;
    }

    public void setWtf_checkout_date(Date wtf_checkout_date) {
        this.wtf_checkout_date = wtf_checkout_date;
    }

    public Date getWtf_installation_date() {
        return wtf_installation_date;
    }

    public void setWtf_installation_date(Date wtf_installation_date) {
        this.wtf_installation_date = wtf_installation_date;
    }

    public Double getWtf_discharge_latitude() {
        return wtf_discharge_latitude;
    }

    public void setWtf_discharge_latitude(Double wtf_discharge_latitude) {
        this.wtf_discharge_latitude = wtf_discharge_latitude;
    }

    public Double getWtf_discharge_longitude() {
        return wtf_discharge_longitude;
    }

    public void setWtf_discharge_longitude(Double wtf_discharge_longitude) {
        this.wtf_discharge_longitude = wtf_discharge_longitude;
    }

    public Double getWtf_distance() {
        return wtf_distance;
    }

    public void setWtf_distance(Double wtf_distance) {
        this.wtf_distance = wtf_distance;
    }

    public Double getWtf_facility_latitude() {
        return wtf_facility_latitude;
    }

    public void setWtf_facility_latitude(Double wtf_facility_latitude) {
        this.wtf_facility_latitude = wtf_facility_latitude;
    }

    public Double getWtf_facility_longitude() {
        return wtf_facility_longitude;
    }

    public void setWtf_facility_longitude(Double wtf_facility_longitude) {
        this.wtf_facility_longitude = wtf_facility_longitude;
    }

    public Double getWtf_ad_id() {
        return wtf_ad_id;
    }

    public void setWtf_ad_id(Double wtf_ad_id) {
        this.wtf_ad_id = wtf_ad_id;
    }

    public Integer getWtf_id() {
        return wtf_id;
    }

    public void setWtf_id(Integer wtf_id) {
        this.wtf_id = wtf_id;
    }

    public String getWtf_capacity() {
        return wtf_capacity;
    }

    public void setWtf_capacity(String wtf_capacity) {
        this.wtf_capacity = wtf_capacity;
    }

    public String getWtf_discharge_type() {
        return wtf_discharge_type;
    }

    public void setWtf_discharge_type(String wtf_discharge_type) {
        this.wtf_discharge_type = wtf_discharge_type;
    }

    public String getWtf_manufacturer() {
        return wtf_manufacturer;
    }

    public void setWtf_manufacturer(String wtf_manufacturer) {
        this.wtf_manufacturer = wtf_manufacturer;
    }

    public String getWtf_manufacturer_description() {
        return wtf_manufacturer_description;
    }

    public void setWtf_manufacturer_description(String wtf_manufacturer_description) {
        this.wtf_manufacturer_description = wtf_manufacturer_description;
    }

    public String getWtf_model() {
        return wtf_model;
    }

    public void setWtf_model(String wtf_model) {
        this.wtf_model = wtf_model;
    }

    public String getWtf_state() {
        return wtf_state;
    }

    public void setWtf_state(String wtf_state) {
        this.wtf_state = wtf_state;
    }

    public String getWtf_technical_passport_id() {
        return wtf_technical_passport_id;
    }

    public void setWtf_technical_passport_id(String wtf_technical_passport_id) {
        this.wtf_technical_passport_id = wtf_technical_passport_id;
    }

    public String getWtf_type() {
        return wtf_type;
    }

    public void setWtf_type(String wtf_type) {
        this.wtf_type = wtf_type;
    }

    public List<AddressSearchResponse> getServedObjectsAddr() {
        return servedObjectsAddr;
    }

    public void setServedObjectsAddr(List<AddressSearchResponse> servedObjectsAddr) {
        this.servedObjectsAddr = servedObjectsAddr;
    }

    public Integer getFam_rfc_id() {
        return fam_rfc_id;
    }

    public void setFam_rfc_id(Integer fam_rfc_id) {
        this.fam_rfc_id = fam_rfc_id;
    }
    
    public Double getFam_pop_equivalent() {
        return fam_pop_equivalent;
    }

    
    public void setFam_pop_equivalent(Double fam_pop_equivalent) {
        this.fam_pop_equivalent = fam_pop_equivalent;
    }

    
    public Double getFam_chds() {
        return fam_chds;
    }

    
    public void setFam_chds(Double fam_chds) {
        this.fam_chds = fam_chds;
    }

    
    public Double getFam_bds() {
        return fam_bds;
    }

    
    public void setFam_bds(Double fam_bds) {
        this.fam_bds = fam_bds;
    }

    
    public Double getFam_float_material() {
        return fam_float_material;
    }

    
    public void setFam_float_material(Double fam_float_material) {
        this.fam_float_material = fam_float_material;
    }

    
    public Double getFam_phosphor() {
        return fam_phosphor;
    }

    
    public void setFam_phosphor(Double fam_phosphor) {
        this.fam_phosphor = fam_phosphor;
    }

    
    public Double getFam_nitrogen() {
        return fam_nitrogen;
    }

    
    public void setFam_nitrogen(Double fam_nitrogen) {
        this.fam_nitrogen = fam_nitrogen;
    }

    public String getWtf_identification_number() {
        return wtf_identification_number;
    }

    public void setWtf_identification_number(String wtf_identification_number) {
        this.wtf_identification_number = wtf_identification_number;
    }

    public Double getWtf_fam_id() {
        return wtf_fam_id;
    }

    public void setWtf_fam_id(Double wtf_fam_id) {
        this.wtf_fam_id = wtf_fam_id;
    }

    public String getFam_description() {
        return fam_description;
    }

    public void setFam_description(String fam_description) {
        this.fam_description = fam_description;
    }

    public String getFam_model() {
        return fam_model;
    }

    public void setFam_model(String fam_model) {
        this.fam_model = fam_model;
    }

    public String getFam_manufacturer() {
        return fam_manufacturer;
    }

    public void setFam_manufacturer(String fam_manufacturer) {
        this.fam_manufacturer = fam_manufacturer;
    }

    public String getWtf_data_source() {
        return wtf_data_source;
    }

    public void setWtf_data_source(String wtf_data_source) {
        this.wtf_data_source = wtf_data_source;
    }

}
