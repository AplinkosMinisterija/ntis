package lt.project.ntis.logic.forms.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class NtisNewContractRequestInfo {

    private Double orgId;

    private String orgName;

    private String orgEmail;

    private String orgPhone;

    private ArrayList<NtisContractRequestService> availableServices;

    private NtisWtfInfo wtfInfo;

    private String applicantEmail;

    private String applicantPhone;

    public NtisNewContractRequestInfo() {
        super();
    }

    public Double getOrgId() {
        return orgId;
    }

    public void setOrgId(Double orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgEmail() {
        return orgEmail;
    }

    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }

    public String getOrgPhone() {
        return orgPhone;
    }

    public void setOrgPhone(String orgPhone) {
        this.orgPhone = orgPhone;
    }

    public ArrayList<NtisContractRequestService> getAvailableServices() {
        return availableServices;
    }

    public void setAvailableServices(ArrayList<NtisContractRequestService> availableServices) {
        this.availableServices = availableServices;
    }

    @JsonIgnore
    public void setAvailableServicesJson(String jsonString) {
        if (jsonString != null) {
            JSONArray servicesJSON = new JSONArray(jsonString);
            ArrayList<NtisContractRequestService> availableServices = new ArrayList<NtisContractRequestService>();
            for (int i = 0; i < servicesJSON.length(); i++) {
                JSONObject serviceJSON = servicesJSON.getJSONObject(i);
                NtisContractRequestService availableService = new NtisContractRequestService(serviceJSON.getString("type"), serviceJSON.getString("name"),
                        serviceJSON.isNull("description") ? null : serviceJSON.getString("description"));
                availableServices.add(availableService);
            }
            this.setAvailableServices(availableServices);
        } else {
            this.setAvailableServices(null);
        }
    }

    public NtisWtfInfo getWtfInfo() {
        return wtfInfo;
    }

    public void setWtfInfo(NtisWtfInfo wtfInfo) {
        this.wtfInfo = wtfInfo;
    }

    public String getApplicantEmail() {
        return applicantEmail;
    }

    public void setApplicantEmail(String applicantEmail) {
        this.applicantEmail = applicantEmail;
    }

    public String getApplicantPhone() {
        return applicantPhone;
    }

    public void setApplicantPhone(String applicantPhone) {
        this.applicantPhone = applicantPhone;
    }

}
