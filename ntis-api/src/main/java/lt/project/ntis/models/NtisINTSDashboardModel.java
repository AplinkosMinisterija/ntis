package lt.project.ntis.models;

import java.util.ArrayList;

/**
 * Klasė skirta formos "INTS savininko pagrindinio puslapio" biznio logikai apibrėžti ir jai reikalingų duomenų perdavimui
 */

public class NtisINTSDashboardModel {

    private ArrayList<NtisINTSDashboardOrder> techOrders;

    private ArrayList<NtisINTSDashboardOrder> disposalOrders;

    private ArrayList<NtisINTSDashboardWastewater> facilities;

    private ArrayList<NtisINTSDashboardContract> contracts;

    private ArrayList<NtisINTSDashboardLab> labs;

    private String[] availableActions;

    public ArrayList<NtisINTSDashboardOrder> getTechOrders() {
        return techOrders;
    }

    public void setTechOrders(ArrayList<NtisINTSDashboardOrder> techOrders) {
        this.techOrders = techOrders;
    }

    public ArrayList<NtisINTSDashboardOrder> getDisposalOrders() {
        return disposalOrders;
    }

    public void setDisposalOrders(ArrayList<NtisINTSDashboardOrder> disposalOrders) {
        this.disposalOrders = disposalOrders;
    }

    public ArrayList<NtisINTSDashboardWastewater> getFacilities() {
        return facilities;
    }

    public void setFacilities(ArrayList<NtisINTSDashboardWastewater> facilities) {
        this.facilities = facilities;
    }

    public ArrayList<NtisINTSDashboardContract> getContracts() {
        return contracts;
    }

    public void setContracts(ArrayList<NtisINTSDashboardContract> contracts) {
        this.contracts = contracts;
    }

    public ArrayList<NtisINTSDashboardLab> getLabs() {
        return labs;
    }

    public void setLabs(ArrayList<NtisINTSDashboardLab> labs) {
        this.labs = labs;
    }

    public String[] getAvailableActions() {
        return availableActions;
    }

    public void setAvailableActions(String[] availableActions) {
        this.availableActions = availableActions;
    }
}