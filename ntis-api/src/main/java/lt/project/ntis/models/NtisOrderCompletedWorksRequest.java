package lt.project.ntis.models;

import java.util.Date;

/**
 * Klasė skirta įvykdyto užsakymo modeliui apibrėžti
 *
 */
public class NtisOrderCompletedWorksRequest {

    private Double ocw_ord_id;

    private String ocw_completed_works_description;

    private Date ocw_completed_date;

    public Double getOcw_ord_id() {
        return ocw_ord_id;
    }

    public void setOcw_ord_id(Double ocw_ord_id) {
        this.ocw_ord_id = ocw_ord_id;
    }

    public String getOcw_completed_works_description() {
        return ocw_completed_works_description;
    }

    public void setOcw_completed_works_description(String ocw_completed_works_description) {
        this.ocw_completed_works_description = ocw_completed_works_description;
    }

    public Date getOcw_completed_date() {
        return ocw_completed_date;
    }

    public void setOcw_completed_date(Date ocw_completed_date) {
        this.ocw_completed_date = ocw_completed_date;
    }

    @Override
    public String toString() {
        return "ocw_ord_id: " + ocw_ord_id + " ocw_completed_works_description: " + ocw_completed_works_description + " ocw_completed_date: "
                + ocw_completed_date;
    }
}