package lt.project.ntis.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import eu.itreegroup.spark.annotations.TypeScriptOptional;

@JsonInclude(Include.NON_NULL)
public class NtisMapFacilityDetails {

    @TypeScriptOptional
    private Integer wtfId;

    @TypeScriptOptional
    private Double x;

    @TypeScriptOptional
    private Double y;

    @TypeScriptOptional
    private String state;

    @TypeScriptOptional
    private String stateCode;

    @TypeScriptOptional
    private String address;

    @TypeScriptOptional
    private String type;

    @TypeScriptOptional
    private String typeCode;

    @TypeScriptOptional
    private List<String> servedObjectAddresses;

    @TypeScriptOptional
    private String distance;

    @TypeScriptOptional
    private String installationDate;

    @TypeScriptOptional
    private String checkoutDate;

    @TypeScriptOptional
    private String capacity;

    @TypeScriptOptional
    private String technicalPassport;

    @TypeScriptOptional
    private String manufacturer;

    @TypeScriptOptional
    private String model;

    @TypeScriptOptional
    private String manufacturerDescription;

    @TypeScriptOptional
    private String dischargeType;

    @TypeScriptOptional
    private String dischargeCoordinatesText;

    private List<Double> facilityCoordinates;

    @TypeScriptOptional
    private List<Double> dischargeCoordinates;

    @TypeScriptOptional
    private List<NtisMapPoint> servedObjects;

    public Integer getWtfId() {
        return wtfId;
    }

    public void setWtfId(Integer wtfId) {
        this.wtfId = wtfId;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public List<String> getServedObjectAddresses() {
        return servedObjectAddresses;
    }

    public void setServedObjectAddresses(List<String> servedObjectAddresses) {
        this.servedObjectAddresses = servedObjectAddresses;
    }

    @JsonIgnore
    public void setServedObjectAddressesFromJsonString(String jsonString) {
        if (jsonString == null) {
            this.servedObjectAddresses = null;
        }
        JSONArray jsonArray = new JSONArray(jsonString);
        this.servedObjectAddresses = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            this.servedObjectAddresses.add(jsonArray.getString(i));
        }

    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(String installationDate) {
        this.installationDate = installationDate;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getTechnicalPassport() {
        return technicalPassport;
    }

    public void setTechnicalPassport(String technicalPassport) {
        this.technicalPassport = technicalPassport;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturerDescription() {
        return manufacturerDescription;
    }

    public void setManufacturerDescription(String manufacturerDescription) {
        this.manufacturerDescription = manufacturerDescription;
    }

    public String getDischargeType() {
        return dischargeType;
    }

    public void setDischargeType(String dischargeType) {
        this.dischargeType = dischargeType;
    }

    public String getDischargeCoordinatesText() {
        return dischargeCoordinatesText;
    }

    public void setDischargeCoordinatesText(String dischargeCoordinatesText) {
        this.dischargeCoordinatesText = dischargeCoordinatesText;
    }

    public List<Double> getFacilityCoordinates() {
        return facilityCoordinates;
    }

    public void setFacilityCoordinates(List<Double> facilityCoordinates) {
        this.facilityCoordinates = facilityCoordinates;
    }

    @JsonIgnore
    public void setFacilityCoordinatesFromJsonString(String jsonString) {
        if (jsonString != null) {
            JSONArray json = new JSONArray(jsonString);
            ArrayList<Double> coordinatesList = new ArrayList<>();
            for (int i = 0; i < json.length(); i++) {
                coordinatesList.add(json.getDouble(i));
            }
            this.setFacilityCoordinates(coordinatesList);
        } else {
            this.setFacilityCoordinates(null);
        }
    }

    public List<Double> getDischargeCoordinates() {
        return dischargeCoordinates;
    }

    public void setDischargeCoordinates(List<Double> dischargeCoordinates) {
        this.dischargeCoordinates = dischargeCoordinates;
    }

    @JsonIgnore
    public void setDischargeCoordinatesFromJsonString(String jsonString) {
        if (jsonString != null) {
            JSONArray json = new JSONArray(jsonString);
            ArrayList<Double> coordinatesList = new ArrayList<>();
            for (int i = 0; i < json.length(); i++) {
                coordinatesList.add(json.getDouble(i));
            }
            this.setDischargeCoordinates(coordinatesList);
        } else {
            this.setDischargeCoordinates(null);
        }
    }

    public List<NtisMapPoint> getServedObjects() {
        return servedObjects;
    }

    public void setServedObjects(List<NtisMapPoint> servedObjects) {
        this.servedObjects = servedObjects;
    }

    @JsonIgnore
    public void setServedObjectsFromJsonString(String jsonString) {
        if (jsonString != null) {
            JSONArray json = new JSONArray(jsonString);
            ArrayList<NtisMapPoint> pointsList = new ArrayList<>();
            json.forEach(obj -> {
                JSONObject objectJsonObj = (JSONObject) obj;
                int id = objectJsonObj.getInt("id");
                JSONArray coordsJsonArray = objectJsonObj.getJSONArray("coords");
                pointsList.add(new NtisMapPoint(id, coordsJsonArray.getDouble(0), coordsJsonArray.getDouble(1)));
            });
            this.setServedObjects(pointsList);
        } else {
            this.setServedObjects(null);
        }
    }

}
