package lt.project.ntis.models;

import java.util.Date;

import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;

public class NtisServedBuildingUpdateModel {

    private String ba_state;

    private Date ba_network_connection_date;

    private Double ba_so_id;

    private Double ba_org_id;

    private Double fua_id;

    private String ba_so_address;

    private String ba_update_by;

    private SprFile attachments;

    public NtisServedBuildingUpdateModel() {
        super();
    }

    public Double getBa_so_id() {
        return ba_so_id;
    }

    public void setBa_so_id(Double ba_so_id) {
        this.ba_so_id = ba_so_id;
    }

    public Double getBa_org_id() {
        return ba_org_id;
    }

    public void setBa_org_id(Double ba_org_id) {
        this.ba_org_id = ba_org_id;
    }

    public String getBa_state() {
        return ba_state;
    }

    public void setBa_state(String ba_state) {
        this.ba_state = ba_state;
    }

    public Date getBa_network_connection_date() {
        return ba_network_connection_date;
    }

    public void setBa_network_connection_date(Date ba_network_connection_date) {
        this.ba_network_connection_date = ba_network_connection_date;
    }

    public String getBa_so_address() {
        return ba_so_address;
    }

    public void setBa_so_address(String ba_so_address) {
        this.ba_so_address = ba_so_address;
    }

    public String getBa_update_by() {
        return ba_update_by;
    }

    public void setBa_update_by(String ba_update_by) {
        this.ba_update_by = ba_update_by;
    }

    public SprFile getAttachments() {
        return attachments;
    }

    public void setAttachments(SprFile attachments) {
        this.attachments = attachments;
    }

    public Double getFua_id() {
        return fua_id;
    }

    public void setFua_id(Double fua_id) {
        this.fua_id = fua_id;
    }

}
