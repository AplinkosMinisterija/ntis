package lt.project.ntis.models.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Nuotekų tvarkymo įrenginio techninė informacija.")
public class ApiWtfTechDetails {

    @Schema(description = "Nuotekų tvarkymo įrenginio tipo kodas.")
    private String typeCode;

    @Schema(description = "Nuotekų tvarkymo įrenginio tipo kodo (grąžinamo lauke typeCode) pavadinimas.")
    private String typeTitle;

    @Schema(description = "Nuotekų tvarkymo įrenginio X koordinatė pagal LKS-94 koordinačių standartą.")
    private Double latitude;

    @Schema(description = "Nuotekų tvarkymo įrenginio Y koordinatė pagal LKS-94 koordinačių standartą.")
    private Double longitude;

    @Schema(description = "Nuotekų įrenginio gamintojo pavadinimas.")
    private String manufacturer;

    @Schema(description = "Nuotekų tvarkymo įrenginio modelis.")
    private String model;

    @Schema(description = "Nuotekų tvarkymo įrenginio talpa kubiniais metrais.")
    private String capacity;

    @Schema(description = "Nuotekų tvarkymo įrenginio atstumas iki privažiavimo vietos metrais.")
    private Double distance;

    @Schema(description = "Nuotekų tvarkymo įrenginio įrengimo data.")
    private String installationDate;

    @Schema(description = "Nuotekų tvarkymo įrenginio išregistravimo data.")
    private String checkoutDate;

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeTitle() {
        return typeTitle;
    }

    public void setTypeTitle(String typeTitle) {
        this.typeTitle = typeTitle;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(String installationDate) {
        this.installationDate = installationDate;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public ApiWtfTechDetails(String typeCode, String typeTitle, Double latitude, Double longitude, String manufacturer, String model, String capacity,
            Double distance, String installationDate, String checkoutDate) {
        super();
        this.typeCode = typeCode;
        this.typeTitle = typeTitle;
        this.latitude = latitude;
        this.longitude = longitude;
        this.manufacturer = manufacturer;
        this.model = model;
        this.capacity = capacity;
        this.distance = distance;
        this.installationDate = installationDate;
        this.checkoutDate = checkoutDate;
    }
}