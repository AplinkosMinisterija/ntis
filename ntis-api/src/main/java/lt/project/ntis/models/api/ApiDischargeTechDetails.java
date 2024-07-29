package lt.project.ntis.models.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Nuotekų tvarkymo įrenginio išleistuvo techninė informacija.")
public class ApiDischargeTechDetails {

    @Schema(description = "Nuotekų tvarkymo įrenginio išleistuvo tipas.")
    private String dischargeType;

    @Schema(description = "Nuotekų tvarkymo įrenginio išleistuvo X koordinatė pagal LKS-94 koordinačių standartą.")
    private Double dischargeLatitude;

    @Schema(description = "Nuotekų tvarkymo įrenginio išleistuvo Y koordinatė pagal LKS-94 koordinačių standartą.")
    private Double dischargeLongitude;

    public String getDischargeType() {
        return dischargeType;
    }

    public void setDischargeType(String dischargeType) {
        this.dischargeType = dischargeType;
    }

    public Double getDischargeLatitude() {
        return dischargeLatitude;
    }

    public void setDischargeLatitude(Double dischargeLatitude) {
        this.dischargeLatitude = dischargeLatitude;
    }

    public Double getDischargeLongitude() {
        return dischargeLongitude;
    }

    public void setDischargeLongitude(Double dischargeLongitude) {
        this.dischargeLongitude = dischargeLongitude;
    }

    public ApiDischargeTechDetails(String dischargeType, Double dischargeLatitude, Double dischargeLongitude) {
        super();
        this.dischargeType = dischargeType;
        this.dischargeLatitude = dischargeLatitude;
        this.dischargeLongitude = dischargeLongitude;
    }

}