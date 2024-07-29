package eu.itreegroup.s2.auth.isense;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Pagalbinė integracijos su iSense klasė, naudojama darbui su JSON dokumentais.
 * 
 */
public class IsenseJson {

    private IsenseJson() {
    }

    static String buildMinimalIdentificationBody(AuthServiceConfig config) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("locale", config.getLocaleName());
        rootNode.put("callbackAsyncEnabled", config.isCallbackAsyncEnabled());
        rootNode.put("defaultCountry", config.getDefaultCountryName());
        rootNode.put("dataToBeSigned", (String) null);
        rootNode.put("messageToPhone", config.getMessage());
        String returnUrl = config.getReturnUrl();
        if (returnUrl != null && !returnUrl.isBlank()) {
            rootNode.put("returnUrl", returnUrl);
        }
        String callbackUrl = config.getCallbackUrl();
        if (callbackUrl != null && !callbackUrl.isBlank()) {
            rootNode.put("callbackUrl", callbackUrl);
        }
        return mapper.writer().writeValueAsString(rootNode);
    }

}
