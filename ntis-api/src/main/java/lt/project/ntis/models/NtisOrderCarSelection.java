package lt.project.ntis.models;

/**
 * Klasė skirta formos "Peržiūrėti gautą paslaugos užsakymą (išvežimas)" biznio logikai apibrėžti ir duomenų, naudojamų spr-select komponente perdavimui
 */

public class NtisOrderCarSelection {

    private Double cr_id;

    private String cr_name;

    public NtisOrderCarSelection() {
        super();
    }

    public NtisOrderCarSelection(Double cr_id, String cr_name) {
        super();
        this.cr_id = cr_id;
        this.cr_name = cr_name;
    }

    public Double getCr_id() {
        return cr_id;
    }

    public void setCr_id(Double cr_id) {
        this.cr_id = cr_id;
    }

    public String getCr_name() {
        return cr_name;
    }

    public void setCr_name(String cr_name) {
        this.cr_name = cr_name;
    }

}
