package eu.itreegroup.s2.auth.viisp;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import lt.atea.vaiisis.authentication.model.xml.AuthenticationDataRequestXml;
import lt.atea.vaiisis.authentication.model.xml.AuthenticationDataResponseXml;

public class ViispAuthDataRequestGenerator extends ViispBaseAuthRequestGenerator {


    private static Logger log = LoggerFactory.getLogger(ViispAuthDataRequestGenerator.class);

    public String generateRequest(String ticket) throws Exception {
        AuthenticationDataRequestXml dataRequest = new AuthenticationDataRequestXml();
        dataRequest.setId(SIGNED_NODE_ID);
        dataRequest.setPid(getConfig().getContractId());
        dataRequest.setIncludeSourceData(true);
        dataRequest.setTicket(ticket);
        return toSoapXmlSigned(dataRequest, dataRequest.getId());
    }

    public AuthenticationDataResponseXml getResponse(String soapData) throws Exception {
        String soapString = soapData;
        if (soapString == null) {
            throw new Exception("SoapData string is null");
        }
        if (log.isDebugEnabled()) {
            log.debug("Response: " + soapString);
        }
        // bugfix for invalid digest when doc has certificate with return characters (\r)
        // Document doc = soapData.getAsDocument();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);
        Document doc = dbFactory.newDocumentBuilder().parse(new InputSource(new StringReader(soapString.replaceAll("\r", "&#13;"))));
        Node responseNode = doc.getElementsByTagNameNS("http://www.epaslaugos.lt/services/authentication", "authenticationDataResponse").item(0);
        setIdAttribute(responseNode);
        try {
        	if (!isIgnoreResponseSignatureValidation()) {
        		validateSignature(doc);
        	} else {
            	log.warn("!!! Response signature validation disabled !!! Use only for testing !!!");
            }
        } catch (Exception e) {
            log.warn("Response failed: " + soapString.replaceAll("\r", "&#13;"));
            throw e;
        }
        return (AuthenticationDataResponseXml) unmarshal(responseNode, AuthenticationDataResponseXml.class);
    }
}
