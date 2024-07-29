package eu.itreegroup.s2.auth.viisp.model;

import java.util.HashMap;
import java.util.Map;

public class ViispAuthRequest {
   
    private Map<String, Object> parameters = new HashMap<String, Object>();
    
    public ViispAuthRequest() {
        super();
    }

    public ViispAuthRequest(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

}
