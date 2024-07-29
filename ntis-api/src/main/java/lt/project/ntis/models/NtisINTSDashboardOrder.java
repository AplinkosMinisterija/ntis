
package lt.project.ntis.models;

import java.util.Date;

/**
 * Klasė skirta formos "INTS savininko pagrindinio puslapio"  biznio logikai apibrėžti ir bloko
 * "Nuotekų įrenginio savininko pagrindinio puslapio nuotekų (dumblo) išvežimo ir techninės priežiūros blokai" duomenų perdavimui
 */

public class NtisINTSDashboardOrder {

    private Double srv_id;

    private Double ord_id;

    private Double wtf_id;

    private Date ord_date;

    private String srv_provider;

    private String ord_state;
    
    private Double rev_id;
    
    private String rev_comment;
    
    private Double rev_score;

    public Double getSrv_id() {
        return srv_id;
    }

    public void setSrv_id(Double srv_id) {
        this.srv_id = srv_id;
    }

    public Date getOrd_date() {
        return ord_date;
    }

    public void setOrd_date(Date ord_date) {
        this.ord_date = ord_date;
    }

    public String getSrv_provider() {
        return srv_provider;
    }

    public void setSrv_provider(String srv_provider) {
        this.srv_provider = srv_provider;
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

    public void setWtf_id(Double wtf_id) {
        this.wtf_id = wtf_id;
    }

    public Double getOrd_id() {
        return ord_id;
    }

    public void setOrd_id(Double ord_id) {
        this.ord_id = ord_id;
    }

    public Double getRev_id() {
        return rev_id;
    }

    public void setRev_id(Double rev_id) {
        this.rev_id = rev_id;
    }

    public String getRev_comment() {
        return rev_comment;
    }

    public void setRev_comment(String rev_comment) {
        this.rev_comment = rev_comment;
    }

    public Double getRev_score() {
        return rev_score;
    }

    public void setRev_score(Double rev_score) {
        this.rev_score = rev_score;
    }
}