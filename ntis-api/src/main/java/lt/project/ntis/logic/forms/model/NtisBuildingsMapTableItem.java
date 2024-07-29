package lt.project.ntis.logic.forms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Klasė skirta atiduoti statinių sluoksnio objekto informaciją į front-end
 */

public class NtisBuildingsMapTableItem {

    private String address;

    private String ntrNumber;

    private Boolean isCentralized;

    private String status;

    private String purpose;

    private String info;

    private String belongsToNtrNumber;

    private Double minX;

    private Double minY;

    private Double maxX;

    private Double maxY;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNtrNumber() {
        return ntrNumber;
    }

    public void setNtrNumber(String ntrNumber) {
        this.ntrNumber = ntrNumber;
    }

    public Boolean getIsCentralized() {
        return isCentralized;
    }

    public void setIsCentralized(Boolean isCentralized) {
        this.isCentralized = isCentralized;
    }

    @JsonIgnore
    public void setIsCentralizedFromBaId(Double baId) {
        this.isCentralized = baId != null;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getBelongsToNtrNumber() {
        return belongsToNtrNumber;
    }

    public void setBelongsToNtrNumber(String belongsToNtrNumber) {
        this.belongsToNtrNumber = belongsToNtrNumber;
    }

    public Double getMinX() {
        return minX;
    }

    public void setMinX(Double minX) {
        this.minX = minX;
    }

    public Double getMinY() {
        return minY;
    }

    public void setMinY(Double minY) {
        this.minY = minY;
    }

    public Double getMaxX() {
        return maxX;
    }

    public void setMaxX(Double maxX) {
        this.maxX = maxX;
    }

    public Double getMaxY() {
        return maxY;
    }

    public void setMaxY(Double maxY) {
        this.maxY = maxY;
    }

}
