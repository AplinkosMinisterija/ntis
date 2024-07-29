package lt.project.ntis.models;

/**
 * Klasė skirta Valyklų sąrašo redagavimo formos biznio logikai apibrėžti ir duomenų perdavimui
 */

public class NtisInstitutionEditModel {

    private Double org_id;

    private String org_name;

    private String org_code;

    private Double ou_id;

    private Double usr_id;

    private Double per_id;

    private String usr_username;

    private String per_name;

    private String per_surname;

    private String per_email;

    private String per_phone_number;

    private String sendInvitation;

    private String org_type;

    private Double municipality;

    public NtisInstitutionEditModel() {
        super();
    }

    public NtisInstitutionEditModel(Double org_id, String org_name, String org_code, Double usr_id, Double ou_id, Double per_id, String usr_username,
            String per_name, String per_surname, String per_email, String per_phone_number, String sendInvitation, String org_type, Double municipality) {
        super();
        this.org_id = org_id;
        this.org_name = org_name;
        this.org_code = org_code;
        this.usr_id = usr_id;
        this.ou_id = ou_id;
        this.per_id = per_id;
        this.usr_username = usr_username;
        this.per_name = per_name;
        this.per_surname = per_surname;
        this.per_email = per_email;
        this.per_phone_number = per_phone_number;
        this.sendInvitation = sendInvitation;
        this.org_type = org_type;
        this.municipality = municipality;
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

    public Double getPer_id() {
        return per_id;
    }

    public void setPer_id(Double per_id) {
        this.per_id = per_id;
    }

    public String getPer_name() {
        return per_name;
    }

    public void setPer_name(String per_name) {
        this.per_name = per_name;
    }

    public String getPer_email() {
        return per_email;
    }

    public void setPer_email(String per_email) {
        this.per_email = per_email;
    }

    public String getPer_phone_number() {
        return per_phone_number;
    }

    public void setPer_phone_number(String per_phone_number) {
        this.per_phone_number = per_phone_number;
    }

    public Double getOu_id() {
        return ou_id;
    }

    public void setOu_id(Double ou_id) {
        this.ou_id = ou_id;
    }

    public String getOrg_code() {
        return org_code;
    }

    public void setOrg_code(String org_code) {
        this.org_code = org_code;
    }

    public String getPer_surname() {
        return per_surname;
    }

    public void setPer_surname(String per_surname) {
        this.per_surname = per_surname;
    }

    public Double getUsr_id() {
        return usr_id;
    }

    public void setUsr_id(Double usr_id) {
        this.usr_id = usr_id;
    }

    public String getSendInvitation() {
        return sendInvitation;
    }

    public void setSendInvitation(String sendInvitation) {
        this.sendInvitation = sendInvitation;
    }

    public String getOrg_type() {
        return org_type;
    }

    public void setOrg_type(String org_type) {
        this.org_type = org_type;
    }

    public Double getMunicipality() {
        return municipality;
    }

    public void setMunicipality(Double municipality) {
        this.municipality = municipality;
    }

    public String getUsr_username() {
        return usr_username;
    }

    public void setUsr_username(String usr_username) {
        this.usr_username = usr_username;
    }

}
