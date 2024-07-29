package lt.project.ntis.logic.forms.model;

import java.util.List;

import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import lt.project.ntis.dao.NtisContractsDAO;

/**
 * Klasė skirta prašymo/sutarties informacijos modeliui apibrėžti
 *
 */
public class NtisContractEditModel extends NtisContractsDAO {

    private String cot_state_meaning;

    private SprFile attachment;

    private List<NtisContractRequestComment> cot_comments;

    private List<NtisContractRequestService> cot_services;

    private NtisWtfInfo wtf_info;

    private SprOrganizationsDAO sp_info;

    private Double org_id;

    private String org_name;

    private String per_name;

    public String getCot_state_meaning() {
        return cot_state_meaning;
    }

    public void setCot_state_meaning(String cot_state_meaning) {
        this.cot_state_meaning = cot_state_meaning;
    }

    public List<NtisContractRequestComment> getCot_comments() {
        return cot_comments;
    }

    public void setCot_comments(List<NtisContractRequestComment> cot_comments) {
        this.cot_comments = cot_comments;
    }

    public List<NtisContractRequestService> getCot_services() {
        return cot_services;
    }

    public void setCot_services(List<NtisContractRequestService> cot_services) {
        this.cot_services = cot_services;
    }

    public NtisWtfInfo getWtf_info() {
        return wtf_info;
    }

    public void setWtf_info(NtisWtfInfo wtf_info) {
        this.wtf_info = wtf_info;
    }

    public Double getOrg_id() {
        return org_id;
    }

    public void setOrg_id(Double org_id) {
        this.org_id = org_id;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getPer_name() {
        return per_name;
    }

    public void setPer_name(String per_name) {
        this.per_name = per_name;
    }

    public SprFile getAttachment() {
        return attachment;
    }

    public void setAttachment(SprFile attachment) {
        this.attachment = attachment;
    }

    public SprOrganizationsDAO getSp_info() {
        return sp_info;
    }

    public void setSp_info(SprOrganizationsDAO sp_info) {
        this.sp_info = sp_info;
    }

}
