package lt.project.ntis.logic.forms.brokerws;

import static lt.project.ntis.logic.forms.brokerws.RcBroker.MDC_ORG_CODE;
import static lt.project.ntis.logic.forms.brokerws.RcBroker.MDC_ORG_ID;
import static lt.project.ntis.logic.forms.brokerws.RcBroker.MDC_PER_CODE;
import static lt.project.ntis.logic.forms.brokerws.RcBroker.MDC_PER_ID;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.brokerws.get.Get;
import lt.project.ntis.brokerws.get.GetPortType;
import lt.project.ntis.brokerws.get.GetResponseType;
import lt.project.ntis.brokerws.get.InputParams;

/**
 * Pagalbinė integracijos su Registrų Centro BrokerWS sistema klasė, naudojama komunikacijai su BrokerWS sistema.
 */
@Component
public class RcBrokerWs {

    private static final Logger log = LoggerFactory.getLogger(RcBrokerWs.class);

    @Value("${brokerws.url}")
    private String brokerwsUrl;

    @Value("${brokerws.callerCode}")
    private String brokerwsCallerCode;

    @Value("${brokerws.keystorePath}")
    private File brokerwsKeystorePath;

    @Value("${brokerws.keystorePass}")
    private String brokerwsKeystorePass;

    @Value("${brokerws.keyAlias}")
    private String brokerwsKeyAlias;

    @Value("${brokerws.keyPass}")
    private String brokerwsKeyPass;

    @Value("${brokerws.ntr40.teit}")
    private String brokerwsNtr40teit;

    @Value("${brokerws.ntr40.regt}")
    private String brokerwsNtr40regt;

    private static final class ResponseData {

        private final int responseCode;

        private final String responseData;

        ResponseData(int responseCode, String responseData) {
            this.responseCode = responseCode;
            this.responseData = responseData;
        }

        public int getResponseCode() {
            return responseCode;
        }

        public String getResponseData() {
            return responseData;
        }

    }

    private ResponseData doCall(String actionType, String parameters) throws Exception {
        final String TIME = getTime();

        InputParams inputParams = new InputParams();
        inputParams.setActionType(actionType);
        inputParams.setParameters(parameters);
        inputParams.setEndUserInfo("");
        inputParams.setCallerCode(brokerwsCallerCode);
        inputParams.setTime(TIME);
        inputParams.setSignature(buildSignature(actionType, brokerwsCallerCode, parameters, TIME));

        Get request = new Get(new URL(brokerwsUrl));
        GetPortType port = request.getGetPort();

        long t1 = System.currentTimeMillis();
        GetResponseType response = port.getData(inputParams);
        int responseCode = response.getResponseCode();
        String responseData = response.getResponseData();
        long t2 = System.currentTimeMillis();

        log.debug("BrokerWS call took {}ms, responseCode was {}, received data {}", (t2 - t1), responseCode, responseData);
        return new ResponseData(responseCode, responseData);
    }

    private String callBrokerWs(String actionType, String parameters) throws Exception {
        ResponseData responseData = doCall(actionType, parameters);
        if (responseData.getResponseCode() != 1) {
            throw new Exception(
                    String.format("BrokerWS call returned with code %d and message %s.", responseData.getResponseCode(), responseData.getResponseData()));
        }

        return decodeResponse(responseData);
    }

    private String callBrokerWsNoDataIsAcceptable(String actionType, String parameters) throws Exception {
        String result = null;
        ResponseData responseData = doCall(actionType, parameters);
        int responseCode = responseData.getResponseCode();
        if (responseCode > 0) {
            result = decodeResponse(responseData);
        } else if (responseCode < 0) {
            throw new Exception(String.format("BrokerWS call returned with code %d and message %s.", responseCode, responseData.getResponseData()));
        }
        return result;
    }

    public String startNtrTransaction761(LocalDate date) throws Exception {
        String dateString = getDate(date);
        log.info("Starting service 761 (NTR batch changes for a single day) transaction for date:{};   {}", dateString);

        String parameters = String.format(
                "<?xml version='1.0' encoding='UTF-8'?><args xmlns='https://www.registrucentras.lt/bdbrokerargs'><pslg_kodas>1500</pslg_kodas><parameters><parameter name='data_parametras'>%s</parameter></parameters></args>",
                dateString);
        ResponseData data = doCall("761", parameters);
        // responseCode=0 is returned for asynchronous service
        return data.getResponseCode() > -1 ? data.getResponseData() : null;
    }

    public String checkNtrTransaction762(String transactionId) throws Exception {
        log.info("Checking NTR service 762 (NTR batch changes for a single day) transaction:{}.", transactionId);

        String parameters = String.format("<?xml version='1.0' encoding='UTF-8'?><args><transaction_id>%s</transaction_id></args>", transactionId);
        ResponseData data = doCall("762", parameters);
        // responseCode=0 is returned if transaction is still being processed
        return data.getResponseCode() > -1 ? data.getResponseData() : null;
    }

    String retrieveData605(String personCode) throws Exception {
        String parameters = String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><args><asm_kodas>%s</asm_kodas><fmt>xml</fmt></args>", personCode);
        ResponseData data = doCall("605", parameters);
        return data.getResponseCode() > 0 ? decodeResponse(data) : null;
        // no logging of personal data set here
    }

    String retrieveData46(LocalDate date) throws Exception {
        String dateString = getDate(date);
        String parameters = String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><args><data>%s</data><fmt>xml</fmt></args>", dateString);
        String result = callBrokerWsNoDataIsAcceptable("46", parameters);

        log.info("Retrieved JAR service 46 data for date:{};   {}", dateString, result);

        return result;
    }

    String retrieveData47(LocalDate date) throws Exception {
        String dateString = getDate(date);
        String parameters = String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><args><data>%s</data><fmt>xml</fmt></args>", dateString);
        String result = callBrokerWsNoDataIsAcceptable("47", parameters);

        log.info("Retrieved JAR service 47 data for date:{};   {}", dateString, result);

        return result;
    }

    String retrieveData48(LocalDate date) throws Exception {
        String dateString = getDate(date);
        String parameters = String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><args><data>%s</data><fmt>xml</fmt></args>", dateString);
        String result = callBrokerWsNoDataIsAcceptable("48", parameters);

        log.info("Retrieved JAR service 48 data for date:{};   {}", dateString, result);

        return result;
    }

    String retrieveData4020(Integer objKodas) throws Exception {
        String parameters = String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><args><obj_kodas>%d</obj_kodas><fmt>xml</fmt></args>", objKodas);
        String result = callBrokerWs("4020", parameters);

        log.info("Retrieved JAR service 4020 data for obj_kodas:{};   {}", objKodas, result);

        return result;
    }

    String retrieveData40(boolean asmTypeLegal, String asmk, String asmp) throws Exception {
        // asmk - Fizinio/juridinio asmens, kurio turto ieškoma, kodas
        // asmt - Asmens tipas (1 - fizinis asmuo; 2 - juridinis asmuo)
        // asmp - Fizinio asmens, kurio turto ieškoma, pavardė
        //
        // teit - Teisės tipas(-ai). Reikšmės vien nuo kitos t.b. atskirtos kableliais.
        // (iš klasifikatoriaus "NTR teisių ir suvaržymų tipai" https://www.registrucentras.lt/p/766)
        //
        // regt - Registro tipas(-ai). Reikšmės vien nuo kitos t.b. atskirtos kableliais.
        // (iš klasifikatoriaus "NTR registrų tipai" https://www.registrucentras.lt/p/172)
        //
        // ist - Ar pateikti ir istorinius įrašus? (0 - ne; 1 - taip)
        // p - rezultatų puslapio numeris. Nutylint=1
        // fmt - (xml; pdf)
        String parameters = null;
        if (asmTypeLegal) {
            parameters = String.format(
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><args><asmk>%s</asmk><asmt>2</asmt><teit>%s</teit><regt>%s</regt><ist>0</ist><fmt>xml</fmt></args>",
                    asmk, brokerwsNtr40teit, brokerwsNtr40regt);
        } else {
            parameters = String.format(
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><args><asmk>%s</asmk><asmt>1</asmt><asmp>%s</asmp><teit>%s</teit><regt>%s</regt><ist>0</ist><fmt>xml</fmt></args>",
                    asmk, asmp, brokerwsNtr40teit, brokerwsNtr40regt);
        }
        String result = callBrokerWs("40", parameters);

        log.info("Retrieved NTR service 40 data for perId:{}; perCode:{}; orgId:{}; orgCode:{}; asmTypeLegal:{}; asmk:{};   {}", MDC.get(MDC_PER_ID),
                MDC.get(MDC_PER_CODE), MDC.get(MDC_ORG_ID), MDC.get(MDC_ORG_CODE), asmTypeLegal, asmk, result);

        return result;
    }

    String retrieveData95(BigInteger regTarnNr, BigInteger regNr) throws Exception {
        // tnr - registro tarnybos nr
        // rnr - registro numeris tarnyboje
        // fmt - (xml; pdf)
        // opt - išrašo formavimo opcijos: L0.[ikaxw]
        // i - požymis kad reikia išrašo su istorija
        // k - požymis kad reikia pateikti fizinių asmenų kodus
        // a - požymis kad nereikia teikti fiz. asmenų gimimo datų
        // x - požymis kad nereikia teikti jokių fiz. asmenų duomenų
        // w - požymis kad išrašas bus teikiamas elektronine forma (veik_tipas=4060)
        String parameters = String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><args><opt>L0.x</opt><tnr>%d</tnr><rnr>%d</rnr><fmt>xml</fmt></args>",
                regTarnNr.longValue(), regNr.longValue());
        String result = callBrokerWs("95", parameters);

        log.info("Retrieved NTR service 95 data for perId:{}; perCode:{}; orgId:{}; orgCode:{}; regTarnNr:{}; regNr:{};   {}", MDC.get(MDC_PER_ID),
                MDC.get(MDC_PER_CODE), MDC.get(MDC_ORG_ID), MDC.get(MDC_ORG_CODE), regTarnNr, regNr, result);

        return result;
    }

    String retrieveData95Optional(BigInteger regTarnNr, BigInteger regNr) throws Exception {
        String parameters = String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><args><opt>L0.x</opt><tnr>%d</tnr><rnr>%d</rnr><fmt>xml</fmt></args>",
                regTarnNr.longValue(), regNr.longValue());
        ResponseData responseData = doCall("95", parameters);
        if (responseData.getResponseCode() == 0) {
            throw new SparkBusinessException(new S2Message("components.brokerWS.no95dataByRegNr", SparkMessageType.ERROR, "Contract does not have document."));
        }

        String result = decodeResponse(responseData);
        log.info("Retrieved NTR service 95 data for regTarnNr:{}; regNr:{}; {}", regTarnNr, regNr, result);
        return result;
    }

    private static String decodeResponse(ResponseData responseData) {
        return new String(Base64.getDecoder().decode(responseData.getResponseData().getBytes()));
    }

    private String buildSignature(String... params) throws Exception {
        String data = Arrays.stream(params).collect(Collectors.joining());
        // RSASSA-PKCS1-v1_5 su SHA-1 HASH funkcija
        Signature s = Signature.getInstance("SHA1WithRSA");
        s.initSign(getPrivateKey());
        s.update(data.getBytes(StandardCharsets.UTF_8));
        byte[] signedData = s.sign();
        return Base64.getEncoder().encodeToString(signedData);
    }

    private PrivateKey getPrivateKey() throws Exception {
        KeyStore k = KeyStore.getInstance(KeyStore.getDefaultType());
        k.load(new FileInputStream(brokerwsKeystorePath), brokerwsKeystorePass.toCharArray());
        return (PrivateKey) k.getKey(brokerwsKeyAlias, brokerwsKeyPass.toCharArray());
    }

    static String getDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private static String getTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
