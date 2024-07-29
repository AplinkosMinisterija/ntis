package eu.itreegroup.s2.auth.viisp.model;

import java.util.Map;

public class ViispMockAuthRequest extends ViispAuthRequest {

    protected ViispAuthData mockAuthData;
    
    public ViispMockAuthRequest() {
        super();
    }

    public ViispMockAuthRequest(Map<String, Object> parameters, ViispAuthData mockAuthData) {
        super(parameters);
        this.mockAuthData = mockAuthData;
    }

    public ViispAuthData getMockAuthData() {
        return mockAuthData;
    }

    public void setMockAuthData(ViispAuthData mockAuthData) {
        this.mockAuthData = mockAuthData;
    }

    @Override
    public String toString() {
        return "ViispMockAuthRequest [mockAuthData=" + mockAuthData + "]";
    }



}
