package lt.project.ntis.logic.forms.brokerws;

import java.math.BigInteger;

/**
 * Pagalbinė integracijos su Registrų Centro BrokerWS sistema klasė, naudojama NTR paslaugos nr.40 duomenų surinkimui ir perdavimui.
 */
final class Ntr40Data {

    final BigInteger regTarnNr;

    final BigInteger regNr;

    Ntr40Data(BigInteger regTarnNr, BigInteger regNr) {
        this.regTarnNr = regTarnNr;
        this.regNr = regNr;
    }

    BigInteger getRegTarnNr() {
        return regTarnNr;
    }

    BigInteger getRegNr() {
        return regNr;
    }
}