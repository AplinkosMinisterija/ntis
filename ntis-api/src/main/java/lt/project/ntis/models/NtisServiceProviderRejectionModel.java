package lt.project.ntis.models;

/**
 * Klasė skirta formos "Paslaugų teikėjų sąrašas"  biznio logikai apibrėžti ir paslaugų teikėjo prieigos apribojimo priežasčiai perduoti
 */

public class NtisServiceProviderRejectionModel {

    private Double org_id;

    private String org_rejection_reason;

    public NtisServiceProviderRejectionModel() {
        super();
    }

    public NtisServiceProviderRejectionModel(Double org_id, String org_rejection_reason) {
        super();
        this.org_id = org_id;
        this.org_rejection_reason = org_rejection_reason;
    }

    public String getOrg_rejection_reason() {
        return org_rejection_reason;
    }

    public void setOrg_rejection_reason(String org_rejection_reason) {
        this.org_rejection_reason = org_rejection_reason;
    }

    public Double getOrg_id() {
        return org_id;
    }

    public void setOrg_id(Double org_id) {
        this.org_id = org_id;
    }

}
