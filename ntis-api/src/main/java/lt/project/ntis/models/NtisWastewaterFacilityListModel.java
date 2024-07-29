package lt.project.ntis.models;

public class NtisWastewaterFacilityListModel {

    private Integer wtf_id;

    private String wtf_fl_latitude;

    private String wtf_fl_longitude;

    private String wtf_fl_address;

    private String wtf_state;

    private String wtf_type;

    private String wtf_served_objects;

    public NtisWastewaterFacilityListModel(Integer wtf_id, String wtf_fl_latitude, String wtf_fl_longitude, String wtf_fl_address, String wtf_state,
            String wtf_type, String wtf_served_objects) {
        super();
        this.wtf_id = wtf_id;
        this.wtf_fl_latitude = wtf_fl_latitude;
        this.wtf_fl_longitude = wtf_fl_longitude;
        this.wtf_fl_address = wtf_fl_address;
        this.wtf_state = wtf_state;
        this.wtf_type = wtf_type;
        this.wtf_served_objects = wtf_served_objects;
    }

    public Integer getWtf_id() {
        return wtf_id;
    }

    public void setWtf_id(Integer wtf_id) {
        this.wtf_id = wtf_id;
    }

    public String getWtf_fl_latitude() {
        return wtf_fl_latitude;
    }

    public void setWtf_fl_latitude(String wtf_fl_latitude) {
        this.wtf_fl_latitude = wtf_fl_latitude;
    }

    public String getWtf_fl_longitude() {
        return wtf_fl_longitude;
    }

    public void setWtf_fl_longitude(String wtf_fl_longitude) {
        this.wtf_fl_longitude = wtf_fl_longitude;
    }

    public String getWtf_fl_address() {
        return wtf_fl_address;
    }

    public void setWtf_fl_address(String wtf_fl_address) {
        this.wtf_fl_address = wtf_fl_address;
    }

    public String getWtf_state() {
        return wtf_state;
    }

    public void setWtf_state(String wtf_state) {
        this.wtf_state = wtf_state;
    }

    public String getWtf_type() {
        return wtf_type;
    }

    public void setWtf_type(String wtf_type) {
        this.wtf_type = wtf_type;
    }

    public String getWtf_served_objects() {
        return wtf_served_objects;
    }

    public void setWtf_served_objects(String wtf_served_objects) {
        this.wtf_served_objects = wtf_served_objects;
    }

}
