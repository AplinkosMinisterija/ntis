package eu.itreegroup.s2.auth.isense;

/**
 * Interfeisas apibrėžiantis darbo su iSense e-identification posisteme metodus.
 * 
 */
public interface IsenseAuthClient {

    /**
     * Pradeda naudotojo identifikacijos sesiją iSense sistemoje.
     * @return konkretaus naudotojo identifikacijos sesijos raktas.
     * @throws Exception
     */
    String startIdentificationSession() throws Exception;

    /**
     * Gauna naudotojo identifikacijos duomenis iš iSense sistemos.
     * @param identificationToken konkretaus naudotojo identifikacijos sesijos raktas.
     * @return naudotojo identifikacijos duomenys.
     * @throws Exception
     */
    IsenseAuthData getIdentificationSessionInfo(String identificationToken) throws Exception;

    /**
     * Uždaro naudotojo identifikacijos sesiją iSense sistemoje.
     * @param identificationToken konkretaus naudotojo identifikacijos sesijos raktas.
     * @throws Exception
     */
    void deleteIdentificationSession(String identificationToken) throws Exception;
}
