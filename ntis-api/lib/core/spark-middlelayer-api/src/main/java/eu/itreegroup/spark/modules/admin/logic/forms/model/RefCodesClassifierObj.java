package eu.itreegroup.spark.modules.admin.logic.forms.model;

import java.util.ArrayList;

public class RefCodesClassifierObj {

    private Double rfc_id;

    private String rfc_domain;

    private String rfc_code;

    private String rfc_meaning;

    private String rfc_description;

    private String rfc_parent_domain;

    private String rfc_parent_domain_code;

    private Double rfc_order;

    private ArrayList<RefTranslationsObj> rft = new ArrayList<RefTranslationsObj>();

    public Double getRfc_id() {
        return rfc_id;
    }

    public void setRfc_id(Double rfc_id) {
        this.rfc_id = rfc_id;
    }

    public String getRfc_domain() {
        return rfc_domain;
    }

    public void setRfc_domain(String rfc_domain) {
        this.rfc_domain = rfc_domain;
    }

    public String getRfc_code() {
        return rfc_code;
    }

    public void setRfc_code(String rfc_code) {
        this.rfc_code = rfc_code;
    }

    public String getRfc_meaning() {
        return rfc_meaning;
    }

    public void setRfc_meaning(String rfc_meaning) {
        this.rfc_meaning = rfc_meaning;
    }

    public String getRfc_description() {
        return rfc_description;
    }

    public void setRfc_description(String rfc_description) {
        this.rfc_description = rfc_description;
    }

    public String getRfc_parent_domain() {
        return rfc_parent_domain;
    }

    public void setRfc_parent_domain(String rfc_parent_domain) {
        this.rfc_parent_domain = rfc_parent_domain;
    }

    public String getRfc_parent_domain_code() {
        return rfc_parent_domain_code;
    }

    public void setRfc_parent_domain_code(String rfc_parent_domain_code) {
        this.rfc_parent_domain_code = rfc_parent_domain_code;
    }

    public Double getRfc_order() {
        return rfc_order;
    }

    public void setRfc_order(Double rfc_order) {
        this.rfc_order = rfc_order;
    }

    public ArrayList<RefTranslationsObj> getRft() {
        return rft;
    }

    public void setRft(ArrayList<RefTranslationsObj> rft) {
        this.rft = rft;
    }

    public void appendRft(RefTranslationsObj rft) {
        this.rft.add(rft);
    }
}
