package lt.project.ntis.logic.forms.brokerws;

/**
 * Pagalbinė integracijos su Registrų Centro BrokerWS sistema klasė, naudojama GR paslaugos nr.605 duomenų surinkimui ir perdavimui.
 */

final class Gr605Data {

    final long asmKodas;

    final String asmVardas;

    final String asmPav;

    Gr605Data(long asmKodas, String asmVardas, String asmPav) {
        this.asmKodas = asmKodas;
        this.asmVardas = asmVardas;
        this.asmPav = asmPav;
    }

    long getAsmKodas() {
        return asmKodas;
    }

    String getAsmVardas() {
        return asmVardas;
    }

    String getAsmPav() {
        return asmPav;
    }

}
