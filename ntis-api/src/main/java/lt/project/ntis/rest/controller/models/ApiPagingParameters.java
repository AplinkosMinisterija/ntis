package lt.project.ntis.rest.controller.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class ApiPagingParameters {

    private static final int DEFAULT_PAGE_SIZE = 100;

    private @Max(value = 10000, message = "pageSize reikšmė turi būti tarp 1 ir 10000") //
    @Min(value = 1, message = "pageSize reikšmė turi būti tarp 1 ir 10000") //
    @Schema(description = "Didžiausias įrašų skaičius užklausos atsako puslapyje. Maksimalus puslapio dydis 10000", example = "100") //
    Integer pageSize;

    private //
    @Min(value = 1, message = "pageAfter reikšmė turi didesnė už 1") //
    @Schema(description = "Puslapiavimo parametras, nurodomas kai norima gauti įrašus kurių ID didesnis už nurodytą") //
    Integer pageAfter;

    private @Min(value = 1, message = "pageBefore reikšmė turi būti didesnė už 1 ") //
    @Schema(description = "Puslapiavimo parametras, nurodomas kai norima gauti įrašus kurių ID mažesnis už nurodytą") //
    Integer pageBefore;

    public ApiPagingParameters() {
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageAfter() {
        return pageAfter;
    }

    public void setPageAfter(Integer pageAfter) {
        this.pageAfter = pageAfter;
    }

    public Integer getPageBefore() {
        return pageBefore;
    }

    public void setPageBefore(Integer pageBefore) {
        this.pageBefore = pageBefore;
    }

}