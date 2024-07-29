package lt.project.ntis.models;

import java.util.List;

import lt.project.ntis.dao.NtisWastewaterDeliveriesDAO;
import lt.project.ntis.dao.NtisWastewaterTreatmentOrgDAO;

/**
 * Klasė skirta formų "Sukurti nuotekų tvarkymo pristatymą", "Redaguoti nuotekų tvarkymo pristatymą (pasl. teikėjas)" 
 * ir "Peržiūrėti nuotekų tvarkymo pristatymą (pasl. teikėjas)" biznio logikai apibrėžti, ir perduoti įrenginio, kuriama dumblas panaudotas, duomenis
 *  
 */

public class NtisWastewaterDeliveryEditModel {

    private NtisWastewaterDeliveriesDAO deliveryInfo;

    private List<NtisUsedSewageFacility> usedSewageFacilities;

    private List<String> deletedUsedFacilities;

    private List<NtisSewageOriginFacility> originFacilities;

    private List<NtisSewageOriginFacility> deletedOriginFacilities;

    private NtisWastewaterTreatmentOrgDAO wastewaterTreatmentOrg;

    public NtisWastewaterDeliveryEditModel() {
        super();
    }

    public NtisWastewaterDeliveryEditModel(NtisWastewaterDeliveriesDAO deliveryInfo, List<NtisUsedSewageFacility> usedSewageFacilities,
            List<String> deletedUsedFacilities, List<NtisSewageOriginFacility> originFacilities, List<NtisSewageOriginFacility> deletedOriginFacilities,
            NtisWastewaterTreatmentOrgDAO wastewaterTreatmentOrg) {
        super();
        this.deliveryInfo = deliveryInfo;
        this.usedSewageFacilities = usedSewageFacilities;
        this.deletedUsedFacilities = deletedUsedFacilities;
        this.originFacilities = originFacilities;
        this.deletedOriginFacilities = deletedOriginFacilities;
        this.wastewaterTreatmentOrg = wastewaterTreatmentOrg;
    }

    public NtisWastewaterDeliveriesDAO getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(NtisWastewaterDeliveriesDAO deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public List<NtisUsedSewageFacility> getUsedSewageFacilities() {
        return usedSewageFacilities;
    }

    public void setUsedSewageFacilities(List<NtisUsedSewageFacility> usedSewageFacilities) {
        this.usedSewageFacilities = usedSewageFacilities;
    }

    public List<String> getDeletedUsedFacilities() {
        return deletedUsedFacilities;
    }

    public void setDeletedUsedFacilities(List<String> deletedUsedFacilities) {
        this.deletedUsedFacilities = deletedUsedFacilities;
    }

    public List<NtisSewageOriginFacility> getOriginFacilities() {
        return originFacilities;
    }

    public void setOriginFacilities(List<NtisSewageOriginFacility> originFacilities) {
        this.originFacilities = originFacilities;
    }

    public List<NtisSewageOriginFacility> getDeletedOriginFacilities() {
        return deletedOriginFacilities;
    }

    public void setDeletedOriginFacilities(List<NtisSewageOriginFacility> deletedOriginFacilities) {
        this.deletedOriginFacilities = deletedOriginFacilities;
    }

    public NtisWastewaterTreatmentOrgDAO getWastewaterTreatmentOrg() {
        return wastewaterTreatmentOrg;
    }

    public void setWastewaterTreatmentOrg(NtisWastewaterTreatmentOrgDAO wastewaterTreatmentOrg) {
        this.wastewaterTreatmentOrg = wastewaterTreatmentOrg;
    }

}
