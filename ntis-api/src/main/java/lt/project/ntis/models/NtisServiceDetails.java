package lt.project.ntis.models;

import java.util.ArrayList;

import org.json.JSONArray;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.itreegroup.spark.annotations.TypeScriptOptional;
import eu.itreegroup.spark.constants.DbConstants;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;

public class NtisServiceDetails {

    private Integer srvId;

    private Boolean contractAvailable;

    private Boolean availableInNtisPortal;

    private Boolean lithuanianLevel;

    private String srv_type;

    private ArrayList<String> municipalities;

    private String serviceName;

    private Integer orgId;

    private String orgName;

    private String email;

    private String phone;

    private String priceFrom;

    private String priceTo;

    private String completionInDays;

    private String description;

    private Integer filId;

    private SprFile file;

    private Integer instrFilId;

    private SprFile labInstructions;

    @TypeScriptOptional
    private String completionInDaysTo;

    public NtisServiceDetails() {
        super();
    }

    public Integer getSrvId() {
        return srvId;
    }

    public void setSrvId(Integer srvId) {
        this.srvId = srvId;
    }

    public Boolean getContractAvailable() {
        return contractAvailable;
    }

    public void setContractAvailable(Boolean contractAvailable) {
        this.contractAvailable = contractAvailable;
    }

    @JsonIgnore
    public void setContractAvailableString(String contractAvailable) {
        this.contractAvailable = contractAvailable != null ? DbConstants.BOOLEAN_TRUE.equals(contractAvailable) : null;
    }

    public Boolean getAvailableInNtisPortal() {
        return availableInNtisPortal;
    }

    public void setAvailableInNtisPortal(Boolean availableInNtisPortal) {
        this.availableInNtisPortal = availableInNtisPortal;
    }

    @JsonIgnore
    public void setAvailableInNtisPortalString(String availableInNtisPortal) {
        this.availableInNtisPortal = availableInNtisPortal != null ? DbConstants.BOOLEAN_TRUE.equals(availableInNtisPortal) : null;
    }

    public Boolean getLithuanianLevel() {
        return lithuanianLevel;
    }

    public void setLithuanianLevel(Boolean lithuanianLevel) {
        this.lithuanianLevel = lithuanianLevel;
    }

    @JsonIgnore
    public void setLithuanianLevelString(String lithuanianLevel) {
        this.lithuanianLevel = lithuanianLevel != null ? DbConstants.BOOLEAN_TRUE.equals(lithuanianLevel) : null;
    }

    public ArrayList<String> getMunicipalities() {
        return municipalities;
    }

    public void setMunicipalities(ArrayList<String> municipalities) {
        this.municipalities = municipalities;
    }

    @JsonIgnore
    public void setMunicipalitiesJson(String jsonString) {
        if (jsonString != null) {
            JSONArray municipalitiesJSON = new JSONArray(jsonString);
            ArrayList<String> municipalities = new ArrayList<String>();
            for (int i = 0; i < municipalitiesJSON.length(); i++) {
                municipalities.add(municipalitiesJSON.getString(i));
            }
            this.setMunicipalities(municipalities);
        } else {
            this.setMunicipalities(null);
        }
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(String priceFrom) {
        this.priceFrom = priceFrom;
    }

    public String getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(String priceTo) {
        this.priceTo = priceTo;
    }

    public String getCompletionInDays() {
        return completionInDays;
    }

    public void setCompletionInDays(String completionInDays) {
        this.completionInDays = completionInDays;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getFilId() {
        return filId;
    }

    public void setFilId(Integer filId) {
        this.filId = filId;
    }

    public SprFile getFile() {
        return file;
    }

    public void setFile(SprFile file) {
        this.file = file;
    }

    public String getCompletionInDaysTo() {
        return completionInDaysTo;
    }

    public void setCompletionInDaysTo(String completionInDaysTo) {
        this.completionInDaysTo = completionInDaysTo;
    }

    public SprFile getLabInstructions() {
        return labInstructions;
    }

    public void setLabInstructions(SprFile labInstructions) {
        this.labInstructions = labInstructions;
    }

    public Integer getInstrFilId() {
        return instrFilId;
    }

    public void setInstrFilId(Integer instrFilId) {
        this.instrFilId = instrFilId;
    }

    public String getSrv_type() {
        return srv_type;
    }

    public void setSrv_type(String srv_type) {
        this.srv_type = srv_type;
    }

}
