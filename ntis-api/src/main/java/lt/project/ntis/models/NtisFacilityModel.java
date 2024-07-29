package lt.project.ntis.models;

public class NtisFacilityModel {

    private String rfc_id;

    private String rfc_code;

    private String rfc_meaning;

    public NtisFacilityModel() {
        super();
    }

    public NtisFacilityModel(String rfc_id, String rfc_code, String rfc_meaning) {
        super();
        this.rfc_id = rfc_id;
        this.rfc_code = rfc_code;
        this.rfc_meaning = rfc_meaning;
    }

    public String getRfc_id() {
        return rfc_id;
    }

    public void setRfc_id(String rfc_id) {
        this.rfc_id = rfc_id;
    }

    public String getRfc_code() {
        return rfc_code;
    }

    public void setRfc_code(String rfc_code) {
        this.rfc_code = rfc_code;
    }

    public String getRfc_meaning() {
        return rfc_meaning;
    }

    public void setRfc_meaning(String rfc_meaning) {
        this.rfc_meaning = rfc_meaning;
    }

}
