package eu.itreegroup.spark.modules.common.rest.model;

public class NotificationRecipient {

    private String name;

    private Double usrId;

    private Double orgId;

    private Double rolId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getUsrId() {
        return usrId;
    }

    public void setUsrId(Double usrId) {
        this.usrId = usrId;
    }

    public Double getOrgId() {
        return orgId;
    }

    public void setOrgId(Double orgId) {
        this.orgId = orgId;
    }

    public Double getRolId() {
        return rolId;
    }

    public void setRolId(Double rolId) {
        this.rolId = rolId;
    }

}
