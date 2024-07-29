package lt.project.ntis.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class NtisMapBuildPointDetails {

    private Integer poId;

    private List<Double> coordinates;

    private List<NtisMapFacilityDetails> facilities;

    private List<String> addresses;

    public NtisMapBuildPointDetails() {
        super();
    }

    public NtisMapBuildPointDetails(Integer poId, List<Double> coordinates, List<NtisMapFacilityDetails> facilities, List<String> addresses) {
        super();
        this.poId = poId;
        this.coordinates = coordinates;
        this.facilities = facilities;
        this.addresses = addresses;
    }

    public NtisMapBuildPointDetails(Integer poId, Double x, Double y, List<NtisMapFacilityDetails> facilities, List<String> addresses) {
        super();
        this.poId = poId;
        this.coordinates = new ArrayList<>();
        this.coordinates.add(x);
        this.coordinates.add(y);
        this.facilities = facilities;
        this.addresses = addresses;
    }

    public Integer getPoId() {
        return poId;
    }

    public void setPoId(Integer poId) {
        this.poId = poId;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    @JsonIgnore
    public void setCoordinatesFromJsonString(String jsonString) {
        if (jsonString == null) {
            this.coordinates = null;
        }
        JSONArray jsonArray = new JSONArray(jsonString);
        this.coordinates = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            this.coordinates.add(jsonArray.getDouble(i));
        }

    }

    public List<NtisMapFacilityDetails> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<NtisMapFacilityDetails> facilities) {
        this.facilities = facilities;
    }

    @JsonIgnore
    public void setFacilitiesFromJsonString(String jsonString) {
        if (jsonString != null) {
            JSONArray json = new JSONArray(jsonString);
            ArrayList<NtisMapFacilityDetails> facilitiesList = new ArrayList<>();
            for (int i = 0; i < json.length(); i++) {
                JSONObject facilityJson = json.getJSONObject(i);
                NtisMapFacilityDetails facility = new NtisMapFacilityDetails();

                facility.setWtfId(facilityJson.getInt("wtfId"));

                JSONArray facilityCoordinatesJson = facilityJson.getJSONArray("facilityCoordinates");
                ArrayList<Double> facilityCoordinates = new ArrayList<>();
                for (int j = 0; j < facilityCoordinatesJson.length(); j++) {
                    facilityCoordinates.add(facilityCoordinatesJson.getDouble(j));
                }
                facility.setFacilityCoordinates(facilityCoordinates);

                if (!facilityJson.isNull("dischargeCoordinates")) {
                    JSONArray dischargeCoordinatesJson = facilityJson.getJSONArray("dischargeCoordinates");
                    ArrayList<Double> dischargeCoordinates = new ArrayList<>();
                    facility.setDischargeCoordinates(dischargeCoordinates);
                    for (int j = 0; j < dischargeCoordinatesJson.length(); j++) {
                        dischargeCoordinates.add(dischargeCoordinatesJson.getDouble(j));
                    }
                }

                facility.setTypeCode(facilityJson.getString("typeCode"));
                facility.setStateCode(facilityJson.getString("stateCode"));

                facilitiesList.add(facility);
            }
            this.setFacilities(facilitiesList);
        } else {
            this.setFacilities(null);
        }
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    @JsonIgnore
    public void setAddressesFromJsonString(String jsonString) {
        if (jsonString == null) {
            this.addresses = null;
        }
        JSONArray jsonArray = new JSONArray(jsonString);
        this.addresses = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            this.addresses.add(jsonArray.getString(i));
        }

    }

}
