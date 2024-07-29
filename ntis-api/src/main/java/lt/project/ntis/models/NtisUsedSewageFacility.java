package lt.project.ntis.models;

/**
 * Klasė skirta formų "Sukurti nuotekų tvarkymo pristatymą" ir "Redaguoti nuotekų tvarkymo pristatymą (pasl. teikėjas)"  biznio logikai apibrėžti 
 * ir perduoti įrenginio, kuriame dumblas panaudotas, duomenis ntis-used-sewage-facility komponentui
 */

public class NtisUsedSewageFacility {

    private String wtf_id;

    private String wtf_address;

    private String wtf_type;

    private String wtf_technical_passport_id;

    private String wtf_manufacturer;

    private String wtf_model;

    private String wtf_installation_date;

    private String wtf_distance;

    private String us_id;

    private String us_wd_id;

    private String typeClsf;

    private String capacity;

    public NtisUsedSewageFacility() {
        super();
    }

    public NtisUsedSewageFacility(String wtf_id, String wtf_address, String wtf_type, String wtf_technical_passport_id, String wtf_manufacturer,
            String wtf_model, String wtf_installation_date, String wtf_distance, String us_id, String us_wd_id) {
        super();
        this.wtf_id = wtf_id;
        this.wtf_address = wtf_address;
        this.us_id = us_id;
        this.wtf_type = wtf_type;
        this.wtf_technical_passport_id = wtf_technical_passport_id;
        this.wtf_manufacturer = wtf_manufacturer;
        this.wtf_model = wtf_model;
        this.wtf_installation_date = wtf_installation_date;
        this.wtf_distance = wtf_distance;
        this.us_wd_id = us_wd_id;
    }

    public String getWtf_id() {
        return wtf_id;
    }

    public void setWtf_id(String wtf_id) {
        this.wtf_id = wtf_id;
    }

    public String getWtf_address() {
        return wtf_address;
    }

    public void setWtf_address(String wtf_address) {
        this.wtf_address = wtf_address;
    }

    public String getWtf_type() {
        return wtf_type;
    }

    public void setWtf_type(String wtf_type) {
        this.wtf_type = wtf_type;
    }

    public String getWtf_technical_passport_id() {
        return wtf_technical_passport_id;
    }

    public void setWtf_technical_passport_id(String wtf_technical_passport_id) {
        this.wtf_technical_passport_id = wtf_technical_passport_id;
    }

    public String getWtf_manufacturer() {
        return wtf_manufacturer;
    }

    public void setWtf_manufacturer(String wtf_manufacturer) {
        this.wtf_manufacturer = wtf_manufacturer;
    }

    public String getWtf_model() {
        return wtf_model;
    }

    public void setWtf_model(String wtf_model) {
        this.wtf_model = wtf_model;
    }

    public String getWtf_installation_date() {
        return wtf_installation_date;
    }

    public void setWtf_installation_date(String wtf_installation_date) {
        this.wtf_installation_date = wtf_installation_date;
    }

    public String getWtf_distance() {
        return wtf_distance;
    }

    public void setWtf_distance(String wtf_distance) {
        this.wtf_distance = wtf_distance;
    }

    public String getUs_id() {
        return us_id;
    }

    public void setUs_id(String us_id) {
        this.us_id = us_id;
    }

    public String getUs_wd_id() {
        return us_wd_id;
    }

    public void setUs_wd_id(String us_wd_id) {
        this.us_wd_id = us_wd_id;
    }

    public String getTypeClsf() {
        return typeClsf;
    }

    public void setTypeClsf(String typeClsf) {
        this.typeClsf = typeClsf;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }
}
