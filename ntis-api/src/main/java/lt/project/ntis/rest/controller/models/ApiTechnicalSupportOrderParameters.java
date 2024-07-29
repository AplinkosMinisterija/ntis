package lt.project.ntis.rest.controller.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lt.project.ntis.models.validators.ExtendedEmailValidator;
import lt.project.ntis.models.validators.PhoneValidator;

public class ApiTechnicalSupportOrderParameters {

    @Parameter(description = "Nuotekų tvarkymo įrenginio unikalus identifikatorius (id) NTIS portale. Reikia nurodyti vertę, grąžintą panaudojant API išteklių /wastewater-treatment-facilities-list arba paimti reikšmę iš NTIS portalo.", example = "1") //
    @NotNull(message = "facilityId negali būti tuščias") //
    private Integer facilityId;

    @Parameter(description = "Užsakymo pateikimo data (kada pateikė užsakovas).", example = "yyyy-MM-dd") //
    @NotNull(message = "orderDate negali būti tuščias") //
    @PastOrPresent(message = "orderDate Negali būti ateities data") //
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) //
    private Date orderDate;

    @Parameter(description = "Užsakymo įvykdymo data (kada buvo atlikti techniniai darbai).\r\n" + "\r\n"
            + "Nurodyta vertė negali būti ankstesnė negu lauko orderDate vertė.", example = "yyyy-MM-dd") //
    @NotNull(message = "completionDate negali būti tuščias") //
    @PastOrPresent(message = "completionDate negali būti ateities data") //
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) //
    private Date completionDate;

    @Parameter(description = "Paslaugų teikėjo informacija apie atliktus darbus arba komentaras apie užsakymą.") //
    private String completionComment;

    @Parameter(description = "Užsakovo elektroninio pašto adresas.") //
    @ExtendedEmailValidator
    private String customerEmail;

    @Parameter(description = "Užsakovo telefono numeris.") //
    @PhoneValidator
    private String customerPhoneNumber;

    @Schema(description = "Užsakovo komentaras apie užsakymą arba paslaugų teikėjo komentaras apie užsakovą.") //
    private String customerComment;

    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public String getCompletionComment() {
        return completionComment;
    }

    public void setCompletionComment(String completionComment) {
        this.completionComment = completionComment;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getCustomerComment() {
        return customerComment;
    }

    public void setCustomerComment(String customerComment) {
        this.customerComment = customerComment;
    }

}
