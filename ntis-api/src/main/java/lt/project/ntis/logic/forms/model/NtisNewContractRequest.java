package lt.project.ntis.logic.forms.model;

import eu.itreegroup.spark.annotations.TypeScriptOptional;

public class NtisNewContractRequest {

    private Double srvProvId;

    @TypeScriptOptional
    private Double wtfId;

    public Double getSrvProvId() {
        return srvProvId;
    }

    public void setSrvProvId(Double srvProvId) {
        this.srvProvId = srvProvId;
    }

    public Double getWtfId() {
        return wtfId;
    }

    public void setWtfId(Double wtfId) {
        this.wtfId = wtfId;
    }

}
