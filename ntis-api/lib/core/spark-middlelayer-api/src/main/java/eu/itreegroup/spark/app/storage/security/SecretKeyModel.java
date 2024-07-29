package eu.itreegroup.spark.app.storage.security;

import javax.crypto.SecretKey;

import eu.itreegroup.spark.enums.SecretKeyType;

/**
 * Class represents instructions for string encryption/decription
 * 
 *
 */
public class SecretKeyModel {

    /**
     * Key that will be used for encryption/decription algorithms
     */
    SecretKey key;

    /**
     * Key Type that shows what was used for key generations (was used session information or other)
     */
    SecretKeyType keyType;

    /**
     * key suffix, that will be added to the encrypted string
     */
    String keySuffix;

    public SecretKeyModel(SecretKey key, SecretKeyType keyType, String keySuffix) {
        this.key = key;
        this.keyType = keyType;
        this.keySuffix = keySuffix;
    }

    public SecretKey getKey() {
        return key;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }

    public SecretKeyType getKeyType() {
        return keyType;
    }

    public void setKeyType(SecretKeyType keyType) {
        this.keyType = keyType;
    }

    public String getKeySuffix() {
        return keySuffix;
    }

    public void setKeySuffix(String keySuffix) {
        this.keySuffix = keySuffix;
    }

}
