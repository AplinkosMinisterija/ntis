package lt.project.ntis.models;

public class NtisCheckWtfSelectionResponseWtf {

    private Integer wtfId;

    private String wtfTypeCode;

    private String wtfType;

    private String ownerTypeCode;

    private String ownerType;

    private Double adId;

    private Double lksX;

    private Double lksY;

    private String address;

    public NtisCheckWtfSelectionResponseWtf() {
        super();
    }

    public NtisCheckWtfSelectionResponseWtf(Integer wtfId, String wtfTypeCode, String wtfType, String ownerTypeCode, String ownerType, Double adId, Double lksX,
            Double lksY, String address) {
        super();
        this.wtfId = wtfId;
        this.wtfTypeCode = wtfTypeCode;
        this.wtfType = wtfType;
        this.ownerTypeCode = ownerTypeCode;
        this.ownerType = ownerType;
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

    public String getWtfTypeCode() {
        return wtfTypeCode;
    }

    public void setWtfTypeCode(String wtfTypeCode) {
        this.wtfTypeCode = wtfTypeCode;
    }

    public String getWtfType() {
        return wtfType;
    }

    public void setWtfType(String wtfType) {
        this.wtfType = wtfType;
    }

    public String getOwnerTypeCode() {
        return ownerTypeCode;
    }

    public void setOwnerTypeCode(String ownerTypeCode) {
        this.ownerTypeCode = ownerTypeCode;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public Double getAdId() {
        return adId;
    }

    public void setAdId(Double adId) {
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
