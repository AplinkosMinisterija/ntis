package lt.project.ntis.models;

import java.util.Date;

/**
 * Klasė skirta formos "Importuoto užsakymų failo peržiūros formos" biznio logikai apibrėžti ir duomenų apie įkeltą failą paėmimui
 */
public class NtisOrdImportFile {

    private Double orf_id;

    private String org_name;
    
    private String srv_type;

    private Date orf_import_date;

    private String orf_status;

    private Date orf_status_date;

    private Double total_errors;

    private Double total_records;

    private String fil_content_type;

    private String fil_key;

    private String fil_name;

    private Double fil_size;

    private String fil_status;

    private Date fil_status_date;

    public Double getOrf_id() {
        return orf_id;
    }

    public void setOrf_id(Double orf_id) {
        this.orf_id = orf_id;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public Date getOrf_import_date() {
        return orf_import_date;
    }

    public void setOrf_import_date(Date orf_import_date) {
        this.orf_import_date = orf_import_date;
    }

    public String getOrf_status() {
        return orf_status;
    }

    public void setOrf_status(String orf_status) {
        this.orf_status = orf_status;
    }

    public Date getOrf_status_date() {
        return orf_status_date;
    }

    public void setOrf_status_date(Date orf_status_date) {
        this.orf_status_date = orf_status_date;
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

	public String getSrv_type() {
		return srv_type;
	}

	public void setSrv_type(String srv_type) {
		this.srv_type = srv_type;
	}

}
