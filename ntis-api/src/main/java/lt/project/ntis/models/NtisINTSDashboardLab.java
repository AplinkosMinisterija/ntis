
package lt.project.ntis.models;

import java.util.Date;

/**
 * Klasė skirta formos "INTS savininko pagrindinio puslapio"  biznio logikai apibrėžti ir bloko "Forma Kliento sritis" duomenų perdavimui
 */

public class NtisINTSDashboardLab {

    private Double ord_id;

    private String lab_name;

    private Date ord_created;

    private Double wtf_id;

    private String compliance_norms;

    private String ord_state;
    
    private String rev_comment;
    
    private Double rev_id;
    
    private Double rev_score;

    public Double getOrd_id() {
        return ord_id;
    }

    public void setOrd_id(Double ord_id) {
        this.ord_id = ord_id;
    }

    public String getLab_name() {
        return lab_name;
    }

    public void setLab_name(String lab_name) {
        this.lab_name = lab_name;
    }

    public Date getOrd_created() {
        return ord_created;
    }

    public void setOrd_created(Date ord_created) {
        this.ord_created = ord_created;
    }

    public String getCompliance_norms() {
        return compliance_norms;
    }

    public void setCompliance_norms(String compliance_norms) {
        this.compliance_norms = compliance_norms;
    }

    public String getOrd_state() {
        return ord_state;
    }

    public void setOrd_state(String ord_state) {
        this.ord_state = ord_state;
    }

    public Double getWtf_id() {
        return wtf_id;
    }

    public void setOrd_wtf_id(Double wtf_id) {
        this.wtf_id = wtf_id;
    }

    public String getRev_comment() {
        return rev_comment;
    }

    public void setRev_comment(String rev_comment) {
        this.rev_comment = rev_comment;
    }

    public Double getRev_id() {
        return rev_id;
    }

    public void setRev_id(Double rev_id) {
        this.rev_id = rev_id;
    }

    public Double getRev_score() {
        return rev_score;
    }

    public void setRev_score(Double rev_score) {
        this.rev_score = rev_score;
    }
}