package eu.itreegroup.s2.pay.viisp;

import java.math.BigDecimal;

import eu.itreegroup.s2.pay.viisp.model.ViispPaymentInitResult;
import lt.atea.vaiisis.payment.model.xml.Currency;
import lt.atea.vaiisis.payment.model.xml.Language;

public interface ViispPaymentClient {

	ViispPaymentInitResult initPayment(BigDecimal amount, Currency currency, String paymentCode, Language language, String message,
			String correlationData) throws Exception;

	Object processPaymentResponse(String response) throws Exception;
	
	ViispPaymentServiceConfig getConfig();

    String createResponse(Object response) throws Exception;
}
