package lt.project.ntis.models;

import eu.itreegroup.spark.modules.admin.dao.SprRefCodesDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import lt.project.ntis.dao.NtisFacilityModelDAO;

public class NtisFacilityModelEditModel {

    SprRefCodesDAO facilityModel;

    NtisFacilityModelDAO facilityModelAdditionalDetails;

    SprFile passport;

    public NtisFacilityModelEditModel() {
        super();
    }

    public NtisFacilityModelEditModel(SprRefCodesDAO facilityModel, NtisFacilityModelDAO facilityModelAdditionalDetails, SprFile passport) {
        super();
        this.facilityModel = facilityModel;
        this.facilityModelAdditionalDetails = facilityModelAdditionalDetails;
        this.passport = passport;
    }

    public SprRefCodesDAO getFacilityModel() {
        return facilityModel;
    }

    public void setFacilityModel(SprRefCodesDAO facilityModel) {
        this.facilityModel = facilityModel;
    }

    public NtisFacilityModelDAO getFacilityModelAdditionalDetails() {
        return facilityModelAdditionalDetails;
    }

    public void setFacilityModelAdditionalDetails(NtisFacilityModelDAO facilityModelAdditionalDetails) {
        this.facilityModelAdditionalDetails = facilityModelAdditionalDetails;
    }

    public SprFile getPassport() {
        return passport;
    }

    public void setPassport(SprFile passport) {
        this.passport = passport;
    }

}
