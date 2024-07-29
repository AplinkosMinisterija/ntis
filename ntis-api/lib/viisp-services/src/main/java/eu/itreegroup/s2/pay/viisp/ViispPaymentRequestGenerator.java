package eu.itreegroup.s2.pay.viisp;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.itreegroup.s2.ViispBaseRequestGenerator;
import lt.atea.vaiisis.payment.model.xml.Currency;
import lt.atea.vaiisis.payment.model.xml.Language;
import lt.atea.vaiisis.payment.model.xml.PaymentRequestXml;

public class ViispPaymentRequestGenerator extends ViispBaseRequestGenerator {
	
	private static final Logger log = LoggerFactory.getLogger(ViispPaymentRequestGenerator.class);
	
	protected ViispPaymentServiceConfig config;
	
	public String generatePaymentRequest(BigDecimal amount, Currency currency, String paymentCode, Language language, String message,
			String correlationData) throws Exception {
		PaymentRequestXml request = new PaymentRequestXml();
		request.setPid(config.getContractId());
		request.setSenderId(config.getSenderId());
		request.setAmount(amount);
		request.setCurrency(currency);
		request.setCorrelation(correlationData);
		request.setMessage(message);
		request.setPaymentCode(paymentCode);
		request.setReturnUrl(config.getReturnUrlPost());
		request.setLanguage(language);
		request.setWebServiceUrl(config.getReturnUrlServer());

		Calendar calendar = Calendar.getInstance();
		XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE),
				calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), calendar.get(Calendar.MILLISECOND), 0);
		request.setDate(xmlGregorianCalendar);

		// Parameters to pass to third party (e.g. Lithuanian post) (refer to protocol specification)
//		request.setThirdParty(new ThirdParty());
//		request.getThirdParty().setPayerCode("39901011234");
//		request.getThirdParty().setPersonFirstName("Vardenis");
//		request.getThirdParty().setPersonLastName("Pavardenis");
	
		ByteArrayOutputStream signedXml = getSignedXml(marshal(request).getFirstChild(), "");
		
		log.debug(signedXml.toString("UTF-8"));
		
		return Base64.getEncoder().encodeToString(signedXml.toByteArray());
	}

	@PostConstruct
	public void init() {
		setCertConfig(config);
		initKeys();
	}

	public ViispPaymentServiceConfig getConfig() {
		return config;
	}

	public void setConfig(ViispPaymentServiceConfig config) {
		this.config = config;
	}

}
