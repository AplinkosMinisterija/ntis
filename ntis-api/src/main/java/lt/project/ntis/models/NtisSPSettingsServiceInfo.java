package lt.project.ntis.models;

public class NtisSPSettingsServiceInfo {

    private String service_type;

    private String is_active;

    public NtisSPSettingsServiceInfo(String service_type, String is_active) {
        super();
        this.service_type = service_type;
        this.is_active = is_active;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

}
