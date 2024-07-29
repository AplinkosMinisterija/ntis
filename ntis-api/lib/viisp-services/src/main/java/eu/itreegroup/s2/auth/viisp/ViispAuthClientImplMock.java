package eu.itreegroup.s2.auth.viisp;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.itreegroup.s2.auth.viisp.model.ViispAuthData;
import eu.itreegroup.s2.auth.viisp.model.ViispAuthRequest;
import eu.itreegroup.s2.auth.viisp.model.ViispAuthResult;
import eu.itreegroup.s2.auth.viisp.model.ViispMockAuthRequest;

public class ViispAuthClientImplMock implements ViispAuthClient {

    private static final Logger log = LoggerFactory.getLogger(ViispAuthClientImplMock.class);

    private AuthServiceConfig config;

    private static Map<String, ViispAuthData> tickets;
    {
        Map<String, ViispAuthData> tmp = new HashMap<String, ViispAuthData>();
        tickets = Collections.synchronizedMap(tmp);
    }

    @Override
    public ViispAuthResult initAuth(ViispAuthRequest authRequest) throws Exception {
        String uuid = UUID.randomUUID().toString();
        log.debug("initAuth={}", authRequest);
        if (authRequest instanceof ViispMockAuthRequest) {
            ViispMockAuthRequest viispMockAuthRequest = (ViispMockAuthRequest) authRequest;
            ViispAuthData viispAuthData = viispMockAuthRequest.getMockAuthData();
            viispAuthData.setTicket(uuid);
            viispAuthData.setCustomData(viispMockAuthRequest.getParameters());
            tickets.put(uuid, viispAuthData);
        }
        ViispAuthResult result = new ViispAuthResult();
        result.setViispUrlWithTicket(config.getPostbackUrl() + "?ticket=" + uuid);
        return result;
    }

    @Override
    public ViispAuthData getAuthDataByTicket(String ticket) throws Exception {
        return tickets.get(ticket);
    }

    @Override
    public AuthServiceConfig getConfig() {
        return config;
    }

    public void setConfig(AuthServiceConfig config) {
        this.config = config;
    }

}
