package eu.itreegroup.s2.pay.viisp;

import eu.itreegroup.s2.pay.viisp.model.ViispPaymentInitRequest;
import lt.atea.vaiisis.payment.model.xml.PaymentCanceledXml;
import lt.atea.vaiisis.payment.model.xml.PaymentSuccessXml;

public interface ViispPaymentCallbackProcessor {
    
    void onPaymentSuccess(ViispPaymentInitRequest pir, PaymentSuccessXml paymentSuccessXml) throws Exception;
    
    void onPaymentCancelled(ViispPaymentInitRequest pir, PaymentCanceledXml paymentCanceledXml) throws Exception;
}
