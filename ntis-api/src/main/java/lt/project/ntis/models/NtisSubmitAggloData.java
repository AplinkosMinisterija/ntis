package lt.project.ntis.models;

import java.util.Date;

import eu.itreegroup.spark.annotations.TypeScriptOptional;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;

public class NtisSubmitAggloData {

    @TypeScriptOptional
    private Double aggId;

    private Date approvalDate;

    private String approvalDocNo;

    private SprFile dataDocument;

    public NtisSubmitAggloData() {
        super();
    }

    public Double getAggId() {
        return aggId;
    }

    public void setAggId(Double aggId) {
        this.aggId = aggId;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getApprovalDocNo() {
        return approvalDocNo;
    }

    public void setApprovalDocNo(String approvalDocNo) {
        this.approvalDocNo = approvalDocNo;
    }

    public SprFile getDataDocument() {
        return dataDocument;
    }

    public void setDataDocument(SprFile dataDocument) {
        this.dataDocument = dataDocument;
    }

}
