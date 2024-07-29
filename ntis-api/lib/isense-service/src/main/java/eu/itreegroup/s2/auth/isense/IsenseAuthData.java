package eu.itreegroup.s2.auth.isense;

/**
 * Naudotojo identifikacijos duomenys gauti iš iSense sistemos.
 * 
 */
public record IsenseAuthData(String personCode, String personName, String personSurname) {
}
