package lt.project.ntis.models;

import java.util.Date;

/**
 * Klasė skirta formos "Centralizuoto nuotekų tvarkymo duomenų peržiūra" biznio logikai apibrėžti ir duomenų apie įkeltą failą paėmimui
 */
public class NtisWastewaterDataImportFile {

    private Double cwf_id;

    private String org_name;

    private Date cwf_import_date;

    private String cwf_status;

    private Date cwf_status_date;

    private Double total_errors;

    private Double total_records;

    private String fil_content_type;

    private String fil_key;

    private String fil_name;

    private Double fil_size;

    private String fil_status;

    private Date fil_status_date;

    public NtisWastewaterDataImportFile() {
    };

    public NtisWastewaterDataImportFile(Double cwf_id, String org_name, Date cwf_import_date, String cwf_status, Date cwf_status_date, Double total_errors,
            Double total_records, String fil_content_type, String fil_key, String fil_name, Double fil_size, String fil_status, Date fil_status_date) {
        super();
        this.cwf_id = cwf_id;
        this.org_name = org_name;
        this.cwf_import_date = cwf_import_date;
        this.cwf_status = cwf_status;
        this.cwf_status_date = cwf_status_date;
        this.total_errors = total_errors;
        this.total_records = total_records;
        this.fil_content_type = fil_content_type;
        this.fil_key = fil_key;
        this.fil_name = fil_name;
        this.fil_size = fil_size;
        this.fil_status = fil_status;
        this.fil_status_date = fil_status_date;
    }

    public Double getCwf_id() {
        return cwf_id;
    }

    public void setCwf_id(Double cwf_id) {
        this.cwf_id = cwf_id;
    }

    public Double getTotal_errors() {
        return total_errors;
    }

    public void setTotal_errors(Double total_errors) {
        this.total_errors = total_errors;
    }

    public Double getTotal_records() {
        return total_records;
    }

    public void setTotal_records(Double total_records) {
        this.total_records = total_records;
    }

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

    public Date getCwf_import_date() {
        return cwf_import_date;
    }

    public void setCwf_import_date(Date cwf_import_date) {
        this.cwf_import_date = cwf_import_date;
    }

    public Date getCwf_status_date() {
        return cwf_status_date;
    }

    public void setCwf_status_date(Date cwf_status_date) {
        this.cwf_status_date = cwf_status_date;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getCwf_status() {
        return cwf_status;
    }

    public void setCwf_status(String cwf_status) {
        this.cwf_status = cwf_status;
    }
}
