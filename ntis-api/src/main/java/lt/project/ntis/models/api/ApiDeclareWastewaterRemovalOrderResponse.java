package lt.project.ntis.models.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Užregistruoto įvykdyto nuotekų (dumblo) išvežimo paslaugos užsakymo, atsakymas")
public class ApiDeclareWastewaterRemovalOrderResponse {

    @Schema(description = "Užsakymo numeris NTIS portale.")
    private Integer orderId;

    @Schema(description = "Būdas, kuriuo buvo pateiktas užsakymas (užsakymas buvo sukurtas panaudojant API).")
    private String orderCreatedInNTIS;

    @Schema(description = "Užsakymo būsenos kodas.")
    private String orderStatusCode;

    @Schema(description = "Užsakymo būsenos pavadinimas.")
    private String orderStatusTitle;

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

}
