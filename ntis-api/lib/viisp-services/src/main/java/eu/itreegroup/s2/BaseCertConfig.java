package eu.itreegroup.s2;

import java.io.File;

public class BaseCertConfig implements CertConfig {

    private File keyStoreFile;

    private String keyStorePassword;

    private File viispSignCertificateFile;

    private Boolean secureValidatioEnabled = false;

    @Override
    public File getKeyStoreFile() {
        return keyStoreFile;
    }

    @Override
    public void setKeyStoreFile(File keyStoreFile) {
        this.keyStoreFile = keyStoreFile;
    }

    @Override
    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    @Override
    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    @Override
    public File getViispSignCertificateFile() {
        return viispSignCertificateFile;
    }

    @Override
    public void setViispSignCertificateFile(File viispSignCertificateFile) {
        this.viispSignCertificateFile = viispSignCertificateFile;
    }

    @Override
    public boolean getSecureValidationEnabled() {
        return secureValidatioEnabled;
    }

    @Override
    public void setSecureValidationEnabled(Boolean secureValidatioEnabled) {
        this.secureValidatioEnabled = secureValidatioEnabled;
    }

    public Boolean getSecureValidatioEnabled() {
        return secureValidatioEnabled;
    }

    public void setSecureValidatioEnabled(Boolean secureValidatioEnabled) {
        this.secureValidatioEnabled = secureValidatioEnabled;
    }

}
