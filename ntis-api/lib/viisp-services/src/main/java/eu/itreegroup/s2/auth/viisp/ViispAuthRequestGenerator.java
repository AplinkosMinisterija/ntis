package eu.itreegroup.s2.auth.viisp;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import lt.atea.vaiisis.authentication.model.xml.AuthenticationAttribute;
import lt.atea.vaiisis.authentication.model.xml.AuthenticationProviderXml;
import lt.atea.vaiisis.authentication.model.xml.AuthenticationRequestXml;
import lt.atea.vaiisis.authentication.model.xml.AuthenticationResponseXml;
import lt.atea.vaiisis.authentication.model.xml.UserInformation;

public class ViispAuthRequestGenerator extends ViispBaseAuthRequestGenerator {
	
	protected ArrayList<AuthenticationAttribute> authenticationAttributes;

	protected ArrayList<AuthenticationProviderXml> authenticationProviders;

    private static Logger log = LoggerFactory.getLogger(ViispAuthRequestGenerator.class);
       
    public String generateRequest(String correlationData) throws Exception {
        AuthenticationRequestXml request = new AuthenticationRequestXml();
        request.setId(SIGNED_NODE_ID);
        request.setPid(getConfig().getContractId());
        request.setCustomData(correlationData);
        request.setPostbackUrl(getConfig().getPostbackUrl());
        request.getAuthenticationAttribute().addAll(getAuthenticationAttributes());
        request.getAuthenticationProvider().addAll(getAuthenticationProviders());
        request.getUserInformation().add(UserInformation.FIRST_NAME);
        request.getUserInformation().add(UserInformation.LAST_NAME);
        request.getUserInformation().add(UserInformation.COMPANY_NAME);
        return toSoapXmlSigned(request, request.getId());
    }

    public AuthenticationResponseXml getResponse(String soapData) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Response: " + (soapData != null ? soapData : null));
        }
        AuthenticationResponseXml result = null;
        Document doc = convertStringToXMLDocument(soapData);
        Node responseNode = doc.getElementsByTagNameNS("http://www.epaslaugos.lt/services/authentication", "authenticationResponse").item(0);
        try {
            setIdAttribute(responseNode);
            if (!isIgnoreResponseSignatureValidation()) {
            	validateSignature(doc);
            } else {
            	log.warn("!!! Response signature validation disabled !!! Use only for testing !!!");
            }
        } catch (Exception e) {
            log.error("Failed to process response: " + (soapData != null ? soapData : null), e);
            throw e;
        }
        result = (AuthenticationResponseXml) unmarshal(responseNode, AuthenticationResponseXml.class);
        return result;
    }

	public ArrayList<AuthenticationAttribute> getAuthenticationAttributes() {
		return authenticationAttributes;
	}

	public void setAuthenticationAttributes(ArrayList<AuthenticationAttribute> authenticationAttributes) {
		this.authenticationAttributes = authenticationAttributes;
	}

	public ArrayList<AuthenticationProviderXml> getAuthenticationProviders() {
		return authenticationProviders;
	}

	public void setAuthenticationProviders(ArrayList<AuthenticationProviderXml> authenticationProviders) {
		this.authenticationProviders = authenticationProviders;
	}
}
