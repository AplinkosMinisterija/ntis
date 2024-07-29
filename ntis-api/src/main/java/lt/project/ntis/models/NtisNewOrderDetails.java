package lt.project.ntis.models;

import java.util.Date;

/**
 * Klasė skirta formų "Užsakyti paslaugą (išvežimas)", "Užsakyti paslaugą (tech. priežiūra) biznio logikai apibrėžti ir naujo užsakymo duomenų perdavimui
 */

public class NtisNewOrderDetails {

    private String ord_name;

    private String ord_email;

    private String ord_phone_number;

    private Date ord_completion_request_date;

    private String ord_additional_description;

    public NtisNewOrderDetails() {
        super();
    }

    public NtisNewOrderDetails(String ord_name, String ord_email, String ord_phone_number, Date ord_completion_request_date,
            String ord_additional_description) {
        super();
        this.ord_name = ord_name;
        this.ord_email = ord_email;
        this.ord_phone_number = ord_phone_number;
        this.ord_completion_request_date = ord_completion_request_date;
        this.ord_additional_description = ord_additional_description;
    }

    public String getOrd_name() {
        return ord_name;
    }

    public void setOrd_name(String ord_name) {
        this.ord_name = ord_name;
    }

    public String getOrd_email() {
        return ord_email;
    }

    public void setOrd_email(String ord_email) {
        this.ord_email = ord_email;
    }

    public String getOrd_phone_number() {
        return ord_phone_number;
    }

    public void setOrd_phone_number(String ord_phone_number) {
        this.ord_phone_number = ord_phone_number;
    }

    public Date getOrd_completion_request_date() {
        return ord_completion_request_date;
    }

    public void setOrd_completion_request_date(Date ord_completion_request_date) {
        this.ord_completion_request_date = ord_completion_request_date;
    }

    public String getOrd_additional_description() {
        return ord_additional_description;
    }

    public void setOrd_additional_description(String ord_additional_description) {
        this.ord_additional_description = ord_additional_description;
    }

}
