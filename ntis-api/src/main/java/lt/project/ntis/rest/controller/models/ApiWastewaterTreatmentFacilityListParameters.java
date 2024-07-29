package lt.project.ntis.rest.controller.models;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ApiWastewaterTreatmentFacilityListParameters extends ApiPagingParameters {

    @Parameter(description = "Aptarnaujamo objekto unikalus identifikatorius pagal Nekilnojamojo turto registro (NTR) numerį.\r\n"
            + "\r\n*Pildomas, tik jei ieškoma aptarnaujamo objekto adreso nurodant nekilnojamojo turto registro numerį.", example = "5190000050290001") //
    private String realEstateId;

    @Parameter(description = "Aptarnaujamo objekto adreso identifikavimo kodas (AOB_KODAS).\r\n"
            + "Jeigu užpildyti abu laukai aobCode ir patCode, paieška atliekama pagal kiekvieną reikšmę atskirai, iki kol randamas rezultatas (jeigu randamas).\r\n"
            + "\r\n**Pildomas, tik jei ieškoma aptarnaujamo objekto adreso nurodant aptarnaujamo objekto adreso identifikavimo kodą.") //
    private Integer aobCode;

    @Parameter(description = "Aptarnaujamo objekto patalpos identifikavimo kodas (PAT_KODAS).\r\n"
            + "Jeigu užpildyti abu laukai aobCode ir patCode, paieška atliekama pagal kiekvieną reikšmę atskirai, iki kol randamas rezultatas (jeigu randamas).\r\n"
            + "\r\n**Pildomas, tik jei ieškoma aptarnaujamo objekto adreso nurodant aptarnaujamo objekto patalpos identifikavimo kodą.") //
    private Integer patCode;

    @Parameter(description = "Pilnas adresas arba jo fragmentas, pagal kurį bus ieškoma įrenginio(-ių). Parametras 'addressFragment' yra privalomas, kai paieška atliekama pagal adresą ir jis privalo būti tarp 5 ir 200 simbolių ilgio.\r\n"
            + "\r\n ***Pildomas, tik jei ieškoma nuotekų tvarkymo įrenginio pagal Adresų registrą.", example = "kalvarij") //
    @Size(max = 200, message = "Parametras 'addressFragment' privalo būti tarp 5 ir 200 simbolių ilgio") //
    private String addressFragment;

    @Positive(message = "Savivaldybės kodas turi būti teigiamas skaičius") //
    @Parameter(description = "Savivaldybės kodas pagal Adresų registrą. Turi būti užpildytas arba laukas municipalityCode arba municipalityName.\r\n"
            + "\r\n ***Pildomas, tik jei ieškoma nuotekų tvarkymo įrenginio pagal Adresų registrą.", example = "48") //
    @Max(value = 999999999999L, message = "Parametras 'municipalityCode' negali viršinti 12 simbolių ilgio") //
    private Integer municipalityCode;

    @Parameter(description = "Savivaldybės pavadinimas pagal Adresų registrą.  Turi būti užpildytas arba laukas municipalityName arba municipalityCode.\r\n"
            + "\r\n ***Pildomas, tik jei ieškoma nuotekų tvarkymo įrenginio pagal Adresų registrą.", example = " ") //
    private String municipalityName;

    @Parameter(description = "Gyvenvietės kodas pagal Adresų registrą. Turi būti užpildytas arba laukas residenceCode arba residenceName.\r\n"
            + "\r\n ***Pildomas, tik jei ieškoma nuotekų tvarkymo įrenginio pagal Adresų registrą.", example = "17018") //
    @Positive(message = "Parametras 'residenceCode'  kodas turi būti teigiamas skaičius") //
    @Max(value = 999999999999L, message = "Parametras 'residenceCode' kodas negali viršinti 12 simbolių ilgio") //
    private Integer residenceCode;

    @Parameter(description = "Gyvenvietės pavadinimas pagal Adresų registrą. Turi būti užpildytas arba laukas residenceName arba residenceCode.\r\n"
            + "\r\n ***Pildomas, tik jei ieškoma nuotekų tvarkymo įrenginio pagal Adresų registrą.", example = " ") //
    private String residenceName;

    @Parameter(description = "Gatvės kodas pagal Adresų registrą. Turi būti užpildytas arba laukas streetCode arba streetName.\r\n"
            + "\r\n ***Pildomas, tik jei ieškoma nuotekų tvarkymo įrenginio pagal Adresų registrą.", example = "1199785") //
    @Positive(message = "Parametras 'streetCode' turi būti teigiamas skaičius") //
    @Max(value = 999999999999L, message = "Parametras 'streetCode' negali viršinti 12 simbolių ilgio") //
    private Integer streetCode;

    @Parameter(description = "Gatvės pavadinimas pagal Adresų registrą. Turi būti užpildytas arba laukas streetName arba streetCode.\r\n "
            + "\r\n ***Pildomas, tik jei ieškoma nuotekų tvarkymo įrenginio pagal Adresų registrą.", example = " ") //
    private String streetName;

    @Size(max = 50, message = "Parametras 'buildingNumber' viršina leistiną maksimalų 50 simbolių skaičių") //
    @Parameter(description = "Pastato numeris pagal Adresų registrą. \r\n"
            + "\r\n ***Pildomas, tik jei ieškoma nuotekų tvarkymo įrenginio pagal Adresų registrą.", example = "32") //
    private String buildingNumber;

    @Size(max = 50, message = "Parametras 'buildingUnit' viršina leistiną maksimalų 50 simbolių skaičių") //
    @Parameter(description = "Pastato korpuso numeris pagal Adresų registrą. \r\n"
            + "\r\n ***Pildomas, tik jei ieškoma nuotekų tvarkymo įrenginio pagal Adresų registrą.") //
    private String buildingUnit;

    @Size(max = 50, message = "Parametras 'flatNo' viršina leistiną maksimalų 50 simbolių skaičių") //
    @Parameter(description = "Buto numeris pagal Adresų registrą. \r\n" + "\r\n ***Pildomas, tik jei ieškoma nuotekų tvarkymo įrenginio pagal Adresų registrą.") //
    private String flatNumber;

    public String getRealEstateId() {
        return realEstateId;
    }

    public void setRealEstateId(String realEstateId) {
        this.realEstateId = realEstateId;
    }

    public Integer getAobCode() {
        return aobCode;
    }

    public void setAobCode(Integer aobCode) {
        this.aobCode = aobCode;
    }

    public Integer getPatCode() {
        return patCode;
    }

    public void setPatCode(Integer patCode) {
        this.patCode = patCode;
    }

    public String getAddressFragment() {
        return addressFragment;
    }

    public void setAddressFragment(String addressFragment) {
        this.addressFragment = addressFragment;
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