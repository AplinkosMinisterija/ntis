package lt.project.ntis.logic.forms.brokerws;

import java.util.Date;

/**
 * Pagalbinė integracijos su Registrų Centro BrokerWS sistema klasė, naudojama JAR paslaugos nr.4020 duomenų surinkimui ir perdavimui.
 */
public class Jar4020Data {

    private final int objKodas;

    private final String objPav;

    private final Integer formKodas;

    private final String jadTekstas;

    private final Date regDate;

    private String text10;

    private String text30;

    private String text40;

    private String text50;

    Jar4020Data(int objKodas, String objPav, Integer formKodas, String jadTekstas, Date regDate) {
        this.objKodas = objKodas;
        this.objPav = objPav;
        this.formKodas = formKodas;
        this.jadTekstas = jadTekstas;
        this.regDate = regDate;
    }

    int getObjKodas() {
        return objKodas;
    }

    String getObjPav() {
        return objPav;
    }

    Integer getFormKodas() {
        return formKodas;
    }

    String getJadTekstas() {
        return jadTekstas;
    }

    Date getRegDate() {
        return regDate;
    }

    String getPhone() {
        return text10 != null ? text10 : text30;
    }

    void setText10(String text10) {
        if (this.text10 == null) {
            this.text10 = text10;
        }
    }

    void setText30(String text30) {
        if (this.text30 == null) {
            this.text30 = text30;
        }
    }

    String getEmail() {
        return text40;
    }

    void setText40(String text40) {
        if (this.text40 == null) {
            this.text40 = text40;
        }
    }

    String getWebsite() {
        return text50;
    }

    void setText50(String text50) {
        if (this.text50 == null) {
            this.text50 = text50;
        }
    }

}
