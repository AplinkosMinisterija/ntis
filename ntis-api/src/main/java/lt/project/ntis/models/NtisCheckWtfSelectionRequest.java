package lt.project.ntis.models;

import eu.itreegroup.spark.annotations.TypeScriptOptional;

public class NtisCheckWtfSelectionRequest {

    @TypeScriptOptional
    private Integer wtfId;

    @TypeScriptOptional
    private Integer wtfDistance;

    @TypeScriptOptional
    private String wtfType;

    @TypeScriptOptional
    private Integer adId;

    private Double lksX;

    private Double lksY;

    @TypeScriptOptional
    private String address;

    public NtisCheckWtfSelectionRequest() {
        super();
    }

    public NtisCheckWtfSelectionRequest(Integer wtfId, Integer wtfDistance, String wtfType, Integer adId, Double lksX, Double lksY, String address) {
        super();
        this.wtfId = wtfId;
        this.wtfDistance = wtfDistance;
        this.wtfType = wtfType;
        this.adId = adId;
        this.lksX = lksX;
        this.lksY = lksY;
        this.address = address;
    }

    public Integer getWtfId() {
        return wtfId;
    }

    public void setWtfId(Integer wtfId) {
        this.wtfId = wtfId;
    }

    public Integer getWtfDistance() {
        return wtfDistance;
    }

    public void setWtfDistance(Integer wtfDistance) {
        this.wtfDistance = wtfDistance;
    }

    public String getWtfType() {
        return wtfType;
    }

    public void setWtfType(String wtfType) {
        this.wtfType = wtfType;
    }

    public Integer getAdId() {
        return adId;
    }

    public void setAdId(Integer adId) {
        this.adId = adId;
    }

    public Double getLksX() {
        return lksX;
    }

    public void setLksX(Double lksX) {
        this.lksX = lksX;
    }

    public Double getLksY() {
        return lksY;
    }

    public void setLksY(Double lksY) {
        this.lksY = lksY;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
