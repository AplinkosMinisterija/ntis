package lt.project.ntis.models.api;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Duomenų apie centralizuotą nuotekų tvarkymą pateikimo atsakymas")
public class ApiDeclareCentralizedWastewaterResponse {

    @Schema(description = "Duomenų atnaujinimo statusas.")
    private String updateStatus;

    @Schema(description = "Klaidos pateiktuose duomenyse.")
    private List<ApiCentralizedWastewaterError> errors;

    public String getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(String updateStatus) {
        this.updateStatus = updateStatus;
    }

    public List<ApiCentralizedWastewaterError> getErrors() {
        return errors;
    }

    public void setErrors(List<ApiCentralizedWastewaterError> errors) {
        this.errors = errors;
    }
}
