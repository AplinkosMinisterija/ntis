package lt.project.ntis.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.itreegroup.spark.constants.DbConstants;

public class NtisServiceManagementItem {

    private String serviceType;

    private String serviceName;

    private Integer srId;

    private Integer sriId;

    private Integer srvId;

    private String status;

    private String statusName;

    private Boolean availableInNtisPortal;

    private Boolean contractAvailable;

    private Boolean lithuanianLevel;

    private Boolean isConfirmed;

    public NtisServiceManagementItem() {
        super();
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getSrId() {
        return srId;
    }

    public void setSrId(Integer srId) {
        this.srId = srId;
    }

    public Integer getSriId() {
        return sriId;
    }

    public void setSriId(Integer sriId) {
        this.sriId = sriId;
    }

    public Integer getSrvId() {
        return srvId;
    }

    public void setSrvId(Integer srvId) {
        this.srvId = srvId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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

    public Boolean getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(Boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    @JsonIgnore
    public void setIsConfirmedString(String isConfirmed) {
        this.isConfirmed = isConfirmed != null ? DbConstants.BOOLEAN_TRUE.equals(isConfirmed) : null;
    }

}
