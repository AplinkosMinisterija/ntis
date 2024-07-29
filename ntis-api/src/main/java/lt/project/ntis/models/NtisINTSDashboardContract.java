package lt.project.ntis.models;

import java.util.Date;

/**
 * Klasė skirta formos "INTS savininko pagrindinio puslapio"  biznio logikai apibrėžti ir bloko
 * "Forma Kliento sritis" duomenų perdavimui
 */

public class NtisINTSDashboardContract {

    private Double cot_id;

    private Date cot_created;

    private String cot_srv_provider;

    private String cot_state;

    private Double cot_wtf_id;

    public Double getCot_id() {
        return cot_id;
    }

    public void setCot_id(Double cot_id) {
        this.cot_id = cot_id;
    }

    public Date getCot_created() {
        return cot_created;
    }

    public void setCot_created(Date cot_created) {
        this.cot_created = cot_created;
    }

    public String getCot_srv_provider() {
        return cot_srv_provider;
    }

    public void setCot_srv_provider(String cot_srv_provider) {
        this.cot_srv_provider = cot_srv_provider;
    }

    public String getCot_state() {
        return cot_state;
    }

    public void setCot_state(String cot_state) {
        this.cot_state = cot_state;
    }

    public Double getCot_wtf_id() {
        return cot_wtf_id;
    }

    public void setCot_wtf_id(Double cot_wtf_id) {
        this.cot_wtf_id = cot_wtf_id;
    }
}