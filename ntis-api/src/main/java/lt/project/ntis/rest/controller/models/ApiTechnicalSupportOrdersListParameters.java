package lt.project.ntis.rest.controller.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lt.project.ntis.enums.NtisOrderStatus;

public class ApiTechnicalSupportOrdersListParameters extends ApiPagingParameters {

    public @Parameter(description = "Laikotarpio, už kurį pageidaujama gauti užsakymų sąrašą, pradžios data.\r\n" + "\r\n"
            + "Rezultatai atrenkami pagal užsakymo atributą “Užsakymo data” (kada buvo pateiktas užsakymas)." + "\r\n"
            + "Lauko dateFrom reikšmė negali būti ateities data.", example = "yyyy-MM-dd") //
    @NotNull(message = "dateFrom negali būti tuščias") //
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) //
    @PastOrPresent(message = "Lauko dateFrom reikšmė negali būti ateities data.") //
    Date dateFrom;

    public @Parameter(description = "Laikotarpio, už kurį pageidaujama gauti užsakymų sąrašą, pabaigos data (imtinai).\r\n" + "\r\n"
            + "Rezultatai atrenkami pagal užsakymo atributą “Užsakymo data”  (kada buvo pateiktas užsakymas)." + "\r\n"
            + "Lauko dateTo reikšmė turi būti didesnė negu arba lygi lauko dateFrom reikšmei.", example = "yyyy-MM-dd") //
    @NotNull(message = "dateTo negali būti tuščias") //
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) //
    Date dateTo;

    public @Parameter(description = "Galima nenurodyti jokios reikšmės arba privaloma nurodyti nuotekų tvarkymo įrenginio unikalų identifikatorių (id) NTIS portale (vertę, grąžintą panaudojant API išteklių /wastewater-treatment-facilities-list ar /create-wastewater-treatment-facility, arba paimtą iš NTIS portalo).\r\n"
            + "\r\n" + "Nenurodžius jokios reikšmės bus gražinami užsakymai visiems įrenginiams.", example = "1") //
    Integer facilityId;

    public @Parameter(description = "Užsakymo būsenos kodas. Galima nenurodyti jokios reikšmės arba privaloma nurodyti vieną iš reikšmių:\r\n" + "\r\n"
            + "“ORD_STS_SUBMITTED” - kai užsakymo būsena “Pateiktas”.\r\n" + "\r\n" + "“ORD_STS_CANCELLED” - kai užsakymo būsena “Atšauktas”,\r\n" + "\r\n"
            + "“ORD_STS_REJECTED” - kai užsakymo būsena “Atmestas”,\r\n" + "\r\n" + "“ORD_STS_CONFIRMED” - kai užsakymo būsena “Patvirtintas”,\r\n" + "\r\n"
            + "“ORD_STS_FINISHED” - kai užsakymo būsena “Įvykdytas”,\r\n" + "\r\n"
            + "“ORD_STS_CANCELLED_SYSTEM” - kai užsakymo būsena “Atšauktas sistemos”.\r\n" + "\r\n"
            + "Nenurodžius jokios reikšmės bus grąžinami visų būsenų užsakymai.") //
    NtisOrderStatus orderStatusCode;

    public @Parameter(description = "Užsakymo numeris NTIS portale. Galima nenurodyti jokios reikšmės, arba privaloma nurodyti konkretaus užsakymo, esančio NTIS portale, numerį.", example = "1") //
    Integer orderId;

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public NtisOrderStatus getOrderStatusCode() {
        return orderStatusCode;
    }

    public void setOrderStatusCode(NtisOrderStatus orderStatusCode) {
        this.orderStatusCode = orderStatusCode;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

}
