package lt.project.ntis.models.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Pateiktų duomenų apie centralizuotą nuotekų tvarkymą klaida.")
public class ApiCentralizedWastewaterError {

    @Schema(description = "Pateikta reikšmė.")
    private String providedValue;

    @Schema(description = "Klaidos aprašymas.")
    private String errorDescription;

    public String getProvidedValue() {
        return providedValue;
    }

    public void setProvidedValue(String providedValue) {
        this.providedValue = providedValue;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

}
