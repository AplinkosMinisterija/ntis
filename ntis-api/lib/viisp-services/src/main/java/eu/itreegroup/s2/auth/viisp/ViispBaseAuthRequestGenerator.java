package eu.itreegroup.s2.auth.viisp;

import javax.annotation.PostConstruct;

import eu.itreegroup.s2.ViispBaseRequestGenerator;

public class ViispBaseAuthRequestGenerator extends ViispBaseRequestGenerator {

    protected static final String SIGNED_NODE_ID = "uniqueNodeId";

    protected AuthServiceConfig config;

    protected boolean ignoreResponseSignatureValidation = false;

    @PostConstruct
    public void init() {
        setCertConfig(config);
        initKeys();
    }

    public AuthServiceConfig getConfig() {
        return config;
    }

    public void setConfig(AuthServiceConfig config) {
        this.config = config;
    }

    public boolean isIgnoreResponseSignatureValidation() {
        return ignoreResponseSignatureValidation;
    }

    public void setIgnoreResponseSignatureValidation(boolean ignoreResponseSignatureValidation) {
        this.ignoreResponseSignatureValidation = ignoreResponseSignatureValidation;
    }

}
