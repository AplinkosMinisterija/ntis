package lt.project.ntis.models;

import java.util.ArrayList;

import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import lt.project.ntis.logic.forms.model.NtisWtfInfo;

/**
 * Klasė skirta formos "Peržiūrėti gautą paslaugos užsakymą (išvežimas)" biznio logikai apibrėžti ir duomenų perdavimui
 */

public class NtisServiceOrderRequest {

    private NtisOrderDetails orderDetails;

    private NtisWtfInfo wastewaterFacility;

    private NtisServiceDescriptionDetails serviceDescription;

    private ArrayList<String> selectedResearchTypes;

    private ArrayList<NtisDeliveryDetailsForOrder> deliveryDetails;

    private SprFile researchFile;

    private ArrayList<NtisOrderCarSelection> carSelection;

    private Double revId;

    private Double revScore;

    private String revComment;

    public NtisServiceOrderRequest() {
        super();
    }

    public NtisWtfInfo getWastewaterFacility() {
        return wastewaterFacility;
    }

    public void setWastewaterFacility(NtisWtfInfo wastewaterFacility) {
        this.wastewaterFacility = wastewaterFacility;
    }

    public NtisOrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(NtisOrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public NtisServiceDescriptionDetails getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(NtisServiceDescriptionDetails serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public ArrayList<NtisOrderCarSelection> getCarSelection() {
        return carSelection;
    }

    public void setCarSelection(ArrayList<NtisOrderCarSelection> carSelection) {
        this.carSelection = carSelection;
    }

    public ArrayList<NtisDeliveryDetailsForOrder> getDeliveryDetails() {
        return deliveryDetails;
    }

    public void setDeliveryDetails(ArrayList<NtisDeliveryDetailsForOrder> deliveryDetails) {
        this.deliveryDetails = deliveryDetails;
    }

    public ArrayList<String> getSelectedResearchTypes() {
        return selectedResearchTypes;
    }

    public void setSelectedResearchTypes(ArrayList<String> selectedResearchTypes) {
        this.selectedResearchTypes = selectedResearchTypes;
    }

    public SprFile getResearchFile() {
        return researchFile;
    }

    public void setResearchFile(SprFile researchFile) {
        this.researchFile = researchFile;
    }

    public Double getRevId() {
        return revId;
    }

    public void setRevId(Double revId) {
        this.revId = revId;
    }

    public Double getRevScore() {
        return revScore;
    }

    public void setRevScore(Double revScore) {
        this.revScore = revScore;
    }

    public String getRevComment() {
        return revComment;
    }

    public void setRevComment(String revComment) {
        this.revComment = revComment;
    }

}
