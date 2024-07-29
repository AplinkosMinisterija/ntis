package eu.itreegroup.s2.pay.viisp;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import eu.itreegroup.s2.auth.viisp.SignatureValidator;
import eu.itreegroup.s2.pay.viisp.model.ViispPaymentInitResult;
import lt.atea.vaiisis.payment.model.xml.Currency;
import lt.atea.vaiisis.payment.model.xml.Language;
import lt.atea.vaiisis.payment.model.xml.PaymentSuccessXml;

public class ViispPaymentClientImpl implements ViispPaymentClient {
    
    private static final Logger log = LoggerFactory.getLogger(ViispPaymentClientImpl.class);

	private ViispPaymentRequestGenerator viispPaymentRequestGenerator;

	@Override
	public ViispPaymentInitResult initPayment(BigDecimal amount, Currency currency, String paymentCode, Language language,
			String message, String correlationData) throws Exception {
		ViispPaymentInitResult result = new ViispPaymentInitResult();
		result.setPostData(this.viispPaymentRequestGenerator.generatePaymentRequest(amount, currency, paymentCode, language,
				message, correlationData));
		result.setServiceUrl(viispPaymentRequestGenerator.getConfig().getPostUrl());
		return result;
	}
	
	@Override
	public Object processPaymentResponse(String response) throws Exception {
		Document xml = this.viispPaymentRequestGenerator.convertStringToXMLDocument(response);
		Node responseNode = getResultNode(xml);
		SignatureValidator.setIdAttribute(responseNode);
		this.viispPaymentRequestGenerator.validateSignature(xml);		
		Object responseObj = this.viispPaymentRequestGenerator.unmarshal(responseNode, PaymentSuccessXml.class);
		return responseObj;
	}
	
	@Override
	public String createResponse(Object response) throws Exception {
	    return this.viispPaymentRequestGenerator.toSoapXmlUnsigned(response);
	}
	
	private Node getResultNode(Document xml) {
	    Node responseNode = xml.getElementsByTagNameNS("http://www.epaslaugos.lt/services/payment", "paymentCanceled").item(0);
	    if (responseNode == null) {
	        responseNode = xml.getElementsByTagNameNS("http://www.epaslaugos.lt/services/payment", "paymentSuccess").item(0);
	    }
	    log.debug("Response is {}", responseNode.getLocalName());
	    return responseNode;
	}

	public ViispPaymentRequestGenerator getViispPaymentRequestGenerator() {
		return viispPaymentRequestGenerator;
	}

	public void setViispPaymentRequestGenerator(ViispPaymentRequestGenerator viispPaymentRequestGenerator) {
		this.viispPaymentRequestGenerator = viispPaymentRequestGenerator;
	}

	@Override
	public ViispPaymentServiceConfig getConfig() {
		return viispPaymentRequestGenerator.getConfig();
	}
	

}
