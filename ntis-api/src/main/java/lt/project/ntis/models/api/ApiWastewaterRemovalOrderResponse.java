package lt.project.ntis.models.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Gauto nuotekų (dumblo) išvežimo paslaugos užsakymo informacija.")
public class ApiWastewaterRemovalOrderResponse {

    @Schema(description = "Užsakymo numeris NTIS portale.")
    private Integer orderId;

    @Schema(description = "Užsakymo pateikimo data (kada pateikė užsakovas).")
    private String orderDate;

    @Schema(description = "Būdas, kuriuo buvo pateiktas užsakymas. ")
    private String orderCreatedInNTIS;

    @Schema(description = "Užsakymo būsena.")
    private String orderStatusCode;

    @Schema(description = "Užsakymo būsenos pavadinimas.")
    private String orderStatusTitle;

    @Schema(description = "Užsakymo įvykdymo data (kada buvo išvežtos nuotekos (dumblas)).")
    private String removalDate;

    @Schema(description = "Išvežtų nuotekų kiekis kubiniais metrais.")
    private Double removedQuantity;

    @Schema(description = "Transporto priemonės, kuria buvo išvežtos nuotekos, valstybinis registracijos numeris.")
    private String carNumberPlate;

    @Schema(description = "Transporto priemonės, kuria buvo išvežtos nuotekos, modelis.")
    private String carModel;

    @Schema(description = "Nuotekų tvarkymo įrenginio, kuriam užsakyta paslauga, unikalus identifikatorius (id) NTIS portale.")
    private Integer facilityId;

    private ApiCustomer customerInfo;

    private ApiAddress address;

    public void setCustomerInfo(String name, String email, String phoneNumber, String comment) {
        this.customerInfo = new ApiCustomer(name, email, phoneNumber, comment);
    }

    public void setAddress(String addressLine, String addressCode, String municipalityCode, String municipalityName, String residenceCode, String residenceName,
            String streetCode, String streetName, String buildingNumber, String buildingUnit, String flatNumber) {
        this.address = new ApiAddress(addressLine, addressCode != null ? Integer.valueOf(addressCode) : null,
                municipalityCode != null ? Integer.valueOf(municipalityCode) : null, municipalityName,
                residenceCode != null ? Integer.valueOf(residenceCode) : null, residenceName, streetCode != null ? Integer.valueOf(streetCode) : null,
                streetName, buildingNumber, buildingUnit, flatNumber);
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderCreatedInNTIS() {
        return orderCreatedInNTIS;
    }

    public void setOrderCreatedInNTIS(String orderCreatedInNTIS) {
        this.orderCreatedInNTIS = orderCreatedInNTIS;
    }

    public String getOrderStatusCode() {
        return orderStatusCode;
    }

    public void setOrderStatusCode(String orderStatusCode) {
        this.orderStatusCode = orderStatusCode;
    }

    public String getOrderStatusTitle() {
        return orderStatusTitle;
    }

    public void setOrderStatusTitle(String orderStatusTitle) {
        this.orderStatusTitle = orderStatusTitle;
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

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public ApiCustomer getCustomerInfo() {
        return customerInfo;
    }

    public ApiAddress getAddress() {
        return address;
    }

    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getRemovalDate() {
        return removalDate;
    }

    public void setRemovalDate(String removalDate) {
        this.removalDate = removalDate;
    }

}
