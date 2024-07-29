package lt.project.ntis.models.api;

import eu.itreegroup.spark.dao.common.Utils;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Nuotekų tvarkymo įrenginių užklausos atsakymas")
public class ApiWastewaterTreatmentFacilityResponse {

    @Schema(description = "Nuotekų tvarkymo įrenginio unikalus identifikatorius (id) NTIS portale.")
    private Integer id;

    @Schema(description = "Nuotekų tvarkymo įrenginio būsena.")
    private String status;

    private ApiDischargeTechDetails dischargeTechDetails;

    private ApiWtfTechDetails wtfTechDetails;

    private ApiAddress address;

    public void setAddress(String addressLine, String addressCode, String municipalityCode, String municipalityName, String residenceCode, String residenceName,
            String streetCode, String streetName, String buildingNumber, String buildingUnit, String flatNumber) {

        this.address = new ApiAddress(addressLine, addressCode != null ? Integer.valueOf(addressCode) : null,
                municipalityCode != null ? Integer.valueOf(municipalityCode) : null, municipalityName,
                residenceCode != null ? Integer.valueOf(residenceCode) : null, residenceName, streetCode != null ? Integer.valueOf(streetCode) : null,
                streetName, buildingNumber, buildingUnit, flatNumber);
    }

    public void setWtfTechDetails(String typeCode, String typeTitle, String latitude, String longitude, String manufacturer, String model, String capacity,
            String distance, String installationDate, String checkoutDate) {
        this.wtfTechDetails = new ApiWtfTechDetails(typeCode, typeTitle, Utils.getDouble(latitude), Utils.getDouble(longitude), manufacturer, model, capacity,
                Utils.getDouble(distance), installationDate, checkoutDate);
    }

    public void setDischargeTechDetails(String dischargeType, String dischargeLatitude, String dischargeLongitude) {
        this.dischargeTechDetails = new ApiDischargeTechDetails(dischargeType, Utils.getDouble(dischargeLatitude), Utils.getDouble(dischargeLongitude));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ApiAddress getAddress() {
        return address;
    }

    public void setAddress(ApiAddress address) {
        this.address = address;
    }

    public ApiDischargeTechDetails getDischargeTechDetails() {
        return dischargeTechDetails;
    }

    public void setDischargeTechDetails(ApiDischargeTechDetails dischargeTechDetails) {
        this.dischargeTechDetails = dischargeTechDetails;
    }

    public ApiWtfTechDetails getWtfTechDetails() {
        return wtfTechDetails;
    }

    public void setWtfTechDetails(ApiWtfTechDetails wtfTechDetails) {
        this.wtfTechDetails = wtfTechDetails;
    }
}
