package lt.project.ntis.models.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Adreso informacija")
public class ApiAddress {

    @Schema(description = "Nuotekų tvarkymo įrenginio adreso taško eilutė.")
    private String addressLine;

    @Schema(description = "Nuotekų tvarkymo įrenginio adreso taško kodas (AOB_kodas, žr. Gyvenamosioms ir negyvenamosioms patalpoms suteikti adresai | VĮ Registrų centras).")
    private Integer aobCode;

    @Schema(description = "Savivaldybės, kuriai yra priskirtas nuotekų tvarkymo įrenginys, kodas pagal Adresų registrą.")
    private Integer municipalityCode;

    private String municipalityName;

    @Schema(description = "Gyvenvietės, kuriai yra priskirtas nuotekų tvarkymo įrenginys, kodas pagal Adresų registrą.")
    private Integer residenceCode;

    private String residenceName;

    @Schema(description = "Gatvės, kuriai yra priskirtas nuotekų tvarkymo įrenginys, kodas pagal Adresų registrą.")
    private Integer streetCode;

    private String streetName;

    @Schema(description = "Pastato, kuriam yra priskirtas nuotekų tvarkymo įrenginys, numeris pagal Adresų registrą.")
    private String buildingNumber;

    @Schema(description = "Pastato, kuriam yra priskirtas nuotekų tvarkymo įrenginys, korpusas pagal Adresų registrą.")
    private String buildingUnit;

    @Schema(description = "Buto, kuriam yra priskirtas nuotekų tvarkymo įrenginys, numeris pagal Adresų registrą.")
    private String flatNumber;

    // Constructors, getters, and setters...

    // Constructors
    public ApiAddress() {
    }

    public ApiAddress(String addressLine, Integer aobCode, Integer municipalityCode, String municipalityName, Integer residenceCode, String residenceName,
            Integer streetCode, String streetName, String buildingNumber, String buildingUnit, String flatNumber) {
        super();
        this.addressLine = addressLine;
        this.aobCode = aobCode;
        this.municipalityCode = municipalityCode;
        this.municipalityName = municipalityName;
        this.residenceCode = residenceCode;
        this.residenceName = residenceName;
        this.streetCode = streetCode;
        this.streetName = streetName;
        this.buildingNumber = buildingNumber;
        this.buildingUnit = buildingUnit;
        this.flatNumber = flatNumber;
    }

    @Override
    public String toString() {
        return "ApiAddress [addressLine=" + addressLine + ", aobCode=" + aobCode + ", municipalityCode=" + municipalityCode + ", municipalityName="
                + municipalityName + ", residenceCode=" + residenceCode + ", residenceName=" + residenceName + ", streetCode=" + streetCode + ", streetName="
                + streetName + ", buildingNumber=" + buildingNumber + ", buildingUnit=" + buildingUnit + ", flatNumber=" + flatNumber + "]";
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public Integer getAobCode() {
        return aobCode;
    }

    public void setAobCode(Integer aobCode) {
        this.aobCode = aobCode;
    }

    public Integer getMunicipalityCode() {
        return municipalityCode;
    }

    public void setMunicipalityCode(Integer municipalityCode) {
        this.municipalityCode = municipalityCode;
    }

    public String getMunicipalityName() {
        return municipalityName;
    }

    public void setMunicipalityName(String municipalityName) {
        this.municipalityName = municipalityName;
    }

    public Integer getResidenceCode() {
        return residenceCode;
    }

    public void setResidenceCode(Integer residenceCode) {
        this.residenceCode = residenceCode;
    }

    public String getResidenceName() {
        return residenceName;
    }

    public void setResidenceName(String residenceName) {
        this.residenceName = residenceName;
    }

    public Integer getStreetCode() {
        return streetCode;
    }

    public void setStreetCode(Integer streetCode) {
        this.streetCode = streetCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getBuildingUnit() {
        return buildingUnit;
    }

    public void setBuildingUnit(String buildingUnit) {
        this.buildingUnit = buildingUnit;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }
}