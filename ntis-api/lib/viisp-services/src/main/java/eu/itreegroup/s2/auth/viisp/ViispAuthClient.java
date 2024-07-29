package eu.itreegroup.s2.auth.viisp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import eu.itreegroup.s2.auth.viisp.model.ViispAuthData;
import eu.itreegroup.s2.auth.viisp.model.ViispAuthRequest;
import eu.itreegroup.s2.auth.viisp.model.ViispAuthResult;

public interface ViispAuthClient {

    ViispAuthResult initAuth(ViispAuthRequest authRequest) throws Exception;

    ViispAuthData getAuthDataByTicket(String ticket) throws Exception;

    AuthServiceConfig getConfig();
    
    public static Map<String, Object> getAuthRequestParametersFromString(String str) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (str.length() > 2) {
            result = Arrays.stream(str.substring(1, str.length() - 1).split(",")).map(entry -> entry.split("=")).collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));
        }
        return result;
    }
    
    public static String authRequestParametersToString(Map<String, Object> map) {
        return map.keySet().stream().map(key -> key + "=" + map.get(key)).collect(Collectors.joining(",", "{", "}"));
    }
    
}
