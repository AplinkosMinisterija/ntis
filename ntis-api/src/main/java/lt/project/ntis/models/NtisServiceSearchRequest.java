package lt.project.ntis.models;

import java.util.ArrayList;

import eu.itreegroup.spark.annotations.TypeScriptOptional;

public class NtisServiceSearchRequest {

    @TypeScriptOptional
    private Integer wtfId;

    private Double lksX;

    private Double lksY;

    private String address;

    private Integer distanceToObject;

    private String equipmentType;

    private Integer carCapacity;

    private ArrayList<String> services;

    private String lang;
    
    @TypeScriptOptional
    private String priceClause;
    
    @TypeScriptOptional
    private String ratingClause;

    public NtisServiceSearchRequest() {
        super();
    }

    public Integer getWtfId() {
        return wtfId;
    }

    public void setWtfId(Integer wtfId) {
        this.wtfId = wtfId;
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

    public Integer getDistanceToObject() {
        return distanceToObject;
    }

    public void setDistanceToObject(Integer distanceToObject) {
        this.distanceToObject = distanceToObject;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public Integer getCarCapacity() {
        return carCapacity;
    }

    public void setCarCapacity(Integer carCapacity) {
        this.carCapacity = carCapacity;
    }

    public ArrayList<String> getServices() {
        return services;
    }

    public void setServices(ArrayList<String> services) {
        this.services = services;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getPriceClause() {
        return priceClause;
    }

    public void setPriceClause(String priceClause) {
        this.priceClause = priceClause;
    }

    public String getRatingClause() {
        return ratingClause;
    }

    public void setRatingClause(String ratingClause) {
        this.ratingClause = ratingClause;
    }

}
