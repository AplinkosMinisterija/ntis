package lt.project.ntis.rest.controller.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lt.project.ntis.models.validators.ExtendedEmailValidator;
import lt.project.ntis.models.validators.PhoneValidator;

public class ApiWastewaterRemovalOrderParameters {

    @Parameter(description = "Nuotekų tvarkymo įrenginio unikalus identifikatorius (id) NTIS portale. Reikia nurodyti vertę, grąžintą panaudojant API išteklių /wastewater-treatment-facilities-list arba paimti reikšmę iš NTIS portalo.")
    @NotNull(message = "facilityId negali būti tuščias") //
    private Integer facilityId;

    @Parameter(description = "Užsakymo pateikimo data (kada pateikė užsakovas).", example = "yyyy-MM-dd") //
    @NotNull(message = "orderDate negali būti tuščias") //
    @PastOrPresent(message = "orderDate Negali būti ateities data") //
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) //
    private Date orderDate;

    @Parameter(description = "Užsakymo įvykdymo data (kada buvo išvežtos nuotekos (dumblas)).\r\n"
            + "\r\nNurodyta vertė negali būti ankstesnė negu lauko orderDate vertė.).", example = "yyyy-MM-dd") //
    @NotNull(message = "removalDate negali būti tuščias") //
    @PastOrPresent(message = "removalDate negali būti ateities data") //
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) //
    private Date removalDate;

    @Parameter(description = "Išvežtas nuotekų (dumblo) kiekis kubiniais metrais.", example = "0.01")
    @NotNull(message = "removedQuantity negali būti tuščias") //
    private Double removedQuantity;

    @Parameter(description = "Valstybinis transporto priemonės numeris.")
    @NotNull(message = "numberPlate negali būti tuščias") //
    private String carNumberPlate;

    @Parameter(description = "Paslaugų teikėjo informacija apie išvežtas nuotekas (dumblą) arba komentaras apie užsakymą.") //
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

    public Date getRemovalDate() {
        return removalDate;
    }

    public void setRemovalDate(Date removalDate) {
        this.removalDate = removalDate;
    }

    public Double getRemovedQuantity() {
        return removedQuantity;
    }

    public void setRemovedQuantity(Double removedQuantity) {
        this.removedQuantity = removedQuantity;
    }

    public String getCarNumberPlate() {
        return carNumberPlate;
    }

    public void setCarNumberPlate(String carNumberPlate) {
        this.carNumberPlate = carNumberPlate;
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
