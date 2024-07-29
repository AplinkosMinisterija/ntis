package lt.project.ntis.models;

import java.util.Date;

/**
 * Klasė skirta  paslaugos užsakymo peržiūros formoje esančio nuotekų (dumblo) išvežimo pristatymo bloko duomenims perduoti
 */
public class NtisDeliveryDetailsForOrder {

    private Double wd_id;

    private String wd_rejection_reason;

    private String wd_state_meaning;

    private String wd_state;

    private String org_name;

    private String wto_name;

    private Date wd_delivery_date;

    public NtisDeliveryDetailsForOrder() {
        super();
    };

    public NtisDeliveryDetailsForOrder(Double wd_id, String wd_rejection_reason, String org_name, String wto_name, Date wd_delivery_date, String wd_state,
            String wd_state_meaning) {
        super();
        this.wd_id = wd_id;
        this.wd_rejection_reason = wd_rejection_reason;
        this.org_name = org_name;
        this.wto_name = wto_name;
        this.wd_delivery_date = wd_delivery_date;
        this.wd_state = wd_state;
        this.wd_state_meaning = wd_state_meaning;
    }

    public Double getWd_id() {
        return wd_id;
    }

    public void setWd_id(Double wd_id) {
        this.wd_id = wd_id;
    }

    public String getWd_rejection_reason() {
        return wd_rejection_reason;
    }

    public void setWd_rejection_reason(String wd_rejection_reason) {
        this.wd_rejection_reason = wd_rejection_reason;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getWto_name() {
        return wto_name;
    }

    public void setWto_name(String wto_name) {
        this.wto_name = wto_name;
    }

    public Date getWd_delivery_date() {
        return wd_delivery_date;
    }

    public void setWd_delivery_date(Date wd_delivery_date) {
        this.wd_delivery_date = wd_delivery_date;
    }

    public String getWd_state_meaning() {
        return wd_state_meaning;
    }

    public void setWd_state_meaning(String wd_state_meaning) {
        this.wd_state_meaning = wd_state_meaning;
    }

    public String getWd_state() {
        return wd_state;
    }

    public void setWd_state(String wd_state) {
        this.wd_state = wd_state;
    }

}
