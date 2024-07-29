package eu.itreegroup.spark.app.job.executor.model;

import java.util.Date;

public class SprFile4Deletion {

    private String fil_key;

    private String fil_status;

    private Date fil_status_date;

    public String getFil_key() {
        return fil_key;
    }

    public void setFil_key(String fil_key) {
        this.fil_key = fil_key;
    }

    public String getFil_status() {
        return fil_status;
    }

    public void setFil_status(String fil_status) {
        this.fil_status = fil_status;
    }

    public Date getFil_status_date() {
        return fil_status_date;
    }

    public void setFil_status_date(Date fil_status_date) {
        this.fil_status_date = fil_status_date;
    }

}
