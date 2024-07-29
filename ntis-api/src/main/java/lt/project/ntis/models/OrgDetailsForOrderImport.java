package lt.project.ntis.models;

public class OrgDetailsForOrderImport {

    private Integer orgId;

    private String orgName;

    private String srvTypeClsf;

    private Integer srvId;
    
    private Integer car_exists;

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

    public String getSrvTypeClsf() {
        return srvTypeClsf;
    }

    public void setSrvTypeClsf(String srvTypeClsf) {
        this.srvTypeClsf = srvTypeClsf;
    }

    public Integer getSrvId() {
        return srvId;
    }

    public void setSrvId(Integer srvId) {
        this.srvId = srvId;
    }

    public Integer getCar_exists() {
        return car_exists;
    }

    public void setCar_exists(Integer car_exists) {
        this.car_exists = car_exists;
    }

}
