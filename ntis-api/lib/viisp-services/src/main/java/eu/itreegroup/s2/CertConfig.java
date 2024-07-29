package eu.itreegroup.s2;

import java.io.File;

public interface CertConfig {

    File getKeyStoreFile();

    String getKeyStorePassword();

    File getViispSignCertificateFile();

    boolean getSecureValidationEnabled();

    void setKeyStoreFile(File keyStoreFile);

    void setKeyStorePassword(String keyStorePassword);

    void setViispSignCertificateFile(File viispSignCertificateFile);

    void setSecureValidationEnabled(Boolean enabled);
}
