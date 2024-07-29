package lt.project.ntis.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.itreegroup.spark.constants.DbConstants;

public class NtisServiceProviderSettingsInfo {

    private String name;

    private String code;

    private String address;

    private String manager;

    private NtisServiceProviderContacts contacts;

    private String registeredFrom;

    private String orgType;

    private String deregisteredFrom;
    
    private String facilityInstallerFrom;

    private String deregisteredReason;

    private ArrayList<NtisSPSettingsServiceInfo> services;

    private Integer employeesCount;

    private Integer vehiclesCount;

    private Integer wtoCount;

    private ArrayList<String> favWaterManagers;

    public NtisServiceProviderSettingsInfo() {
        super();
    }

    public NtisServiceProviderSettingsInfo(String name, String code, String address, String manager, NtisServiceProviderContacts contacts,
            String registeredFrom, String orgType, String deregisteredFrom, String facilityInstallerFrom, String deregisteredReason, ArrayList<NtisSPSettingsServiceInfo> services,
            Integer employeesCount, Integer vehiclesCount, Integer wtoCount, ArrayList<String> favWaterManagers) {
        super();
        this.name = name;
        this.code = code;
        this.address = address;
        this.manager = manager;
        this.contacts = contacts;
        this.orgType = orgType;
        this.registeredFrom = registeredFrom;
        this.deregisteredFrom = deregisteredFrom;
        this.facilityInstallerFrom = facilityInstallerFrom;
        this.deregisteredReason = deregisteredReason;
        this.services = services;
        this.employeesCount = employeesCount;
        this.vehiclesCount = vehiclesCount;
        this.wtoCount = wtoCount;
        this.favWaterManagers = favWaterManagers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public NtisServiceProviderContacts getContacts() {
        return contacts;
    }

    public void setContacts(NtisServiceProviderContacts contacts) {
        this.contacts = contacts;
    }

    public void createContacts(Integer orgId, String email, String emailNotifications, String phoneNumber, String webSite) {
        setContacts(new NtisServiceProviderContacts(orgId, email, //
                DbConstants.BOOLEAN_TRUE.equals(emailNotifications), //
                phoneNumber, //
                webSite //
        ));
    }

    public String getRegisteredFrom() {
        return registeredFrom;
    }

    public void setRegisteredFrom(String registeredFrom) {
        this.registeredFrom = registeredFrom;
    }

    public String getDeregisteredFrom() {
        return deregisteredFrom;
    }

    public void setDeregisteredFrom(String deregisteredFrom) {
        this.deregisteredFrom = deregisteredFrom;
    }

    public String getDeregisteredReason() {
        return deregisteredReason;
    }

    public void setDeregisteredReason(String deregisteredReason) {
        this.deregisteredReason = deregisteredReason;
    }

    public ArrayList<NtisSPSettingsServiceInfo> getServices() {
        return services;
    }

    public void setServices(ArrayList<NtisSPSettingsServiceInfo> services) {
        this.services = services;
    }

    @JsonIgnore
    public void setServicesJson(String str) {
        if (str != null) {
            JSONArray servicesJSON = new JSONArray(str);
            ArrayList<NtisSPSettingsServiceInfo> services = new ArrayList<>();
            servicesJSON.forEach(obj -> {
                JSONObject objectJsonObj = (JSONObject) obj;
                String service_type = objectJsonObj.getString("service_type");
                String is_active = objectJsonObj.getString("is_active");
                services.add(new NtisSPSettingsServiceInfo(service_type, is_active));
            });
            this.setServices(services);
        } else {
            this.setServices(null);
        }
    }

    public Integer getEmployeesCount() {
        return employeesCount;
    }

    public void setEmployeesCount(Integer employeesCount) {
        this.employeesCount = employeesCount;
    }

    public Integer getVehiclesCount() {
        return vehiclesCount;
    }

    public void setVehiclesCount(Integer vehiclesCount) {
        this.vehiclesCount = vehiclesCount;
    }

    public Integer getwtoCount() {
        return wtoCount;
    }

    public void setwtoCount(Integer wtoCount) {
        this.wtoCount = wtoCount;
    }

    public ArrayList<String> getFavWaterManagers() {
        return favWaterManagers;
    }

    @JsonIgnore
    public void setFavWaterManagersJson(String str) {
        if (str != null) {
            JSONArray favWaterManagersJSON = new JSONArray(str);
            ArrayList<String> favWaterManagers = new ArrayList<String>();
            for (int i = 0; i < favWaterManagersJSON.length(); i++) {
                favWaterManagers.add(favWaterManagersJSON.getString(i));
            }
            this.setFavWaterManagers(favWaterManagers);
        }
    }

    public void setFavWaterManagers(ArrayList<String> favWaterManagers) {
        this.favWaterManagers = favWaterManagers;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getFacilityInstallerFrom() {
        return facilityInstallerFrom;
    }

    public void setFacilityInstallerFrom(String facilityInstallerFrom) {
        this.facilityInstallerFrom = facilityInstallerFrom;
    }

}
