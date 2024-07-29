package lt.project.ntis.models;

/**
 * Klasė skirta ne NTIS portale pasirašytos sutarties įkėlimo modeliui apibrėžti
 *
 */
public class NtisContractUploadRequest {

    private Double contractId;

    private Double spOrgId;

    private Double wtfId;

    private String filKey;

    public Double getContractId() {
        return contractId;
    }

    public void setContractId(Double contractId) {
        this.contractId = contractId;
    }

    public Double getSpOrgId() {
        return spOrgId;
    }

    public void setSpOrgId(Double spOrgId) {
        this.spOrgId = spOrgId;
    }

    public Double getWtfId() {
        return wtfId;
    }

    public void setWtfId(Double wtfId) {
        this.wtfId = wtfId;
    }

    public String getFilKey() {
        return filKey;
    }

    public void setFilKey(String filKey) {
        this.filKey = filKey;
    }

}
