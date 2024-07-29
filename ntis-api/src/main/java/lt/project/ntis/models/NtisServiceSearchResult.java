package lt.project.ntis.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.itreegroup.spark.constants.DbConstants;

public class NtisServiceSearchResult {

    private Double orgId;

    private String orgName;
    
    private Double score;

    private ArrayList<NtisServiceSearchResultService> services;

    private String hasCar;

    public NtisServiceSearchResult() {
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

    public ArrayList<NtisServiceSearchResultService> getServices() {
        return services;
    }

    public void setServices(ArrayList<NtisServiceSearchResultService> services) {
        this.services = services;
    }

    public String getHasCar() {
        return hasCar;
    }

    public void setHasCar(String hasCar) {
        this.hasCar = hasCar;
    }

    @JsonIgnore
    public void setServicesJson(String jsonString) {
        ArrayList<NtisServiceSearchResultService> resultServices = new ArrayList<NtisServiceSearchResultService>();
        JSONArray servicesJSON = new JSONArray(jsonString);
        for (int i = 0; i < servicesJSON.length(); i++) {
            JSONObject serviceJSON = servicesJSON.getJSONObject(i);
            NtisServiceSearchResultService service = new NtisServiceSearchResultService(serviceJSON.getInt("id"), serviceJSON.getString("name"),
                    serviceJSON.getString("type"), serviceJSON.getString("description"),serviceJSON.getString("phone"),serviceJSON.getString("email"), serviceJSON.getInt("priceFrom"), serviceJSON.getInt("priceTo"),
                    serviceJSON.getInt("completionInDays"), DbConstants.BOOLEAN_TRUE.equals(serviceJSON.getString("contractAvailable")),
                    serviceJSON.getInt("completionInDaysTo"));
            resultServices.add(service);
        }
        this.setServices(resultServices);
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

}
