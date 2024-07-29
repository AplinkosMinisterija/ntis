package eu.itreegroup.s2.auth.isense.data;

/**
 * Naudotojo identifikavimo iSense sistemoje duomenys.
 * 
 */
public class Identity {

    /**
     * Identifikavimo metodas.
     * Galimos reikšmės paimtos iš iSense sistemos dokumentacijos.
     */
    public static enum IdentificationMethod {
        MSIG, SMARTID, HWCRYPTO, LTID
    }

    private String certificate;

    private String commonName;

    private String givenName;

    private String surname;

    private String personCode;

    private String country;

    private IdentificationMethod identificationMethod;

    private String signature;

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPersonCode() {
        return personCode;
    }

    public void setPersonCode(String personCode) {
        this.personCode = personCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public IdentificationMethod getIdentificationMethod() {
        return identificationMethod;
    }

    public void setIdentificationMethod(IdentificationMethod identificationMethod) {
        this.identificationMethod = identificationMethod;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
