package eu.itreegroup.s2.pay.viisp;

import java.net.URI;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.itreegroup.s2.pay.viisp.model.ViispPaymentInitRequest;
import lt.atea.vaiisis.payment.model.xml.CallbackResponseXml;
import lt.atea.vaiisis.payment.model.xml.PaymentCanceledXml;
import lt.atea.vaiisis.payment.model.xml.PaymentSuccessXml;

@RestController
@RequestMapping("/viisp-payment")
public class ViispPaymentCallbackService  {

    @Autowired(required=false)
    private ViispPaymentClient viispPaymentClient;
    
    private ViispPaymentCallbackProcessor callbackProcessor;

    @Value("${app.host}")
    private String appBaseUrl;

    private static final Logger log = LoggerFactory.getLogger(ViispPaymentCallbackService.class);

    @RequestMapping(value = "/accept-callback-post", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> acceptPaymentCallbackPost(@RequestBody MultiValueMap<String, String> formData)
            throws Exception {
        log.info("Payment callback received from POST");
        ViispPaymentInitRequest pir = processCallback(formData);
        HttpHeaders headers = new HttpHeaders();
        //apsisaugome, kad returnUrl visada prasidėtų "/", nes kitaip redirect neveiks:
        String returnUrl = pir.getReturnUrl().startsWith("/") ? pir.getReturnUrl() : "/" + pir.getReturnUrl();
        headers.setLocation(URI.create(appBaseUrl + returnUrl));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    private ViispPaymentInitRequest processCallback(MultiValueMap<String, String> formData) throws Exception {
        String data = formData.getFirst("data");
        log.debug("Base64 encoded: {}", data);
        String response = new String(Base64.getDecoder().decode(data), "UTF-8");
        log.info("Callback decoded: {}", response);
        return processResponse(response);
    }

    private ViispPaymentInitRequest processResponse(String response) throws Exception {
        Object responseObj = viispPaymentClient.processPaymentResponse(response);
        ViispPaymentInitRequest pir = null;
        if (responseObj instanceof PaymentSuccessXml) {
            PaymentSuccessXml result = (PaymentSuccessXml) responseObj;
            pir = ViispPaymentInitRequest.fromString(result.getCorrelation());
            log.info("Correlation: {}", result.getCorrelation());
            callbackProcessor.onPaymentSuccess(pir, result);
            log.info("Payment {} processed", result.getCorrelation());
        } else if (responseObj instanceof PaymentCanceledXml) {
            PaymentCanceledXml result = (PaymentCanceledXml) responseObj;
            pir = ViispPaymentInitRequest.fromString(result.getCorrelation());
            callbackProcessor.onPaymentCancelled(pir, result);
            log.info("Payment {} cancelled", result.getCorrelation());
        }
        return pir;
    }
    
    

    @RequestMapping(value = "/accept-callback-server", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> acceptServerPaymentCallback(@RequestBody String responseObj) throws Exception {
        log.info("Payment callback received from SERVER");
        log.debug("Response obj: {}", responseObj);
        CallbackResponseXml response = null;
        try {
            processResponse(responseObj);
            response = CallbackResponseXml.SUCCESS;
        } catch (Exception e) {
            log.error("Payment processing error:\n", e);
            response = CallbackResponseXml.FAILURE;
        }
        return new ResponseEntity<String>(viispPaymentClient.createResponse(response), HttpStatus.OK);
    }

    
    public void setCallbackProcessor(ViispPaymentCallbackProcessor callbackProcessor) {
        this.callbackProcessor = callbackProcessor;
    }

}
