package eu.itreegroup.spark.modules.admin.logic.forms.model;

import java.util.Date;

import eu.itreegroup.spark.app.storage.FileStorageService;

public class SprFile {

    public SprFile() {
    };

    public SprFile(String fil_content_type, String fil_key, String fil_name, Double fil_size, String fil_status, Date fil_status_date) {
        super();
        this.fil_content_type = fil_content_type;
        this.fil_key = fil_key;
        this.fil_name = fil_name;
        this.fil_size = fil_size;
        this.fil_status = fil_status;
        this.fil_status_date = fil_status_date;
    }

    private String fil_content_type;

    private String fil_key;

    private String fil_name;

    private Double fil_size;

    private String fil_status;

    private Date fil_status_date;

    public String getFil_content_type() {
        return fil_content_type;
    }

    public void setFil_content_type(String fil_content_type) {
        this.fil_content_type = fil_content_type;
    }

    public String getFil_key() {
        return fil_key;
    }

    public void setFil_key(String fil_key) {
        this.fil_key = fil_key;
    }

    public void setFilKey4Encript(FileStorageService fileStorageService, String fil_key) {
        if (fil_key != null) {
            this.fil_key = fileStorageService.encryptFileKey(fil_key);
        }
    }

    public String getFil_name() {
        return fil_name;
    }

    public void setFil_name(String fil_name) {
        this.fil_name = fil_name;
    }

    public Double getFil_size() {
        return fil_size;
    }

    public void setFil_size(Double fil_size) {
        this.fil_size = fil_size;
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
