package lt.project.ntis.models;

import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;

public class FacilityModelAdditionalDetails {

    private Integer rfc_id;

    private SprFile passport;

    private Double fam_pop_equivalent;

    private String fam_tech_pass;

    private Double fam_chds;

    private Integer fil_id;

    private Double fam_bds;

    private Double fam_float_material;

    private Double fam_phosphor;

    private Double fam_nitrogen;
    
    public FacilityModelAdditionalDetails() {
        super();
    }

    public Integer getRfc_id() {
        return rfc_id;
    }

    public void setRfc_id(Integer rfc_id) {
        this.rfc_id = rfc_id;
    }

    public SprFile getPassport() {
        return passport;
    }

    public void setPassport(SprFile passport) {
        this.passport = passport;
    }

    public Double getFam_pop_equivalent() {
        return fam_pop_equivalent;
    }

    public void setFam_pop_equivalent(Double fam_pop_equivalent) {
        this.fam_pop_equivalent = fam_pop_equivalent;
    }

    public String getFam_tech_pass() {
        return fam_tech_pass;
    }

    public void setFam_tech_pass(String fam_tech_pass) {
        this.fam_tech_pass = fam_tech_pass;
    }

    public Double getFam_chds() {
        return fam_chds;
    }

    public void setFam_chds(Double fam_chds) {
        this.fam_chds = fam_chds;
    }

    public Integer getFil_id() {
        return fil_id;
    }

    public void setFil_id(Integer fil_id) {
        this.fil_id = fil_id;
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
}
