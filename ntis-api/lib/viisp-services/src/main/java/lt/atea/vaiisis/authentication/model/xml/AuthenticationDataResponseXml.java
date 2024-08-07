package lt.atea.vaiisis.authentication.model.xml;
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.15 at 01:18:30 PM EET 
//




import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authenticationProvider" type="{http://www.epaslaugos.lt/services/authentication}authenticationProvider"/>
 *         &lt;element name="authenticationAttribute" type="{http://www.epaslaugos.lt/services/authentication}authenticationAttributePair" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="userInformation" type="{http://www.epaslaugos.lt/services/authentication}userInformationPair" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="customData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sourceData" type="{http://www.epaslaugos.lt/services/authentication}authenticationSourceData" minOccurs="0"/>
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Signature"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "authenticationProvider",
    "authenticationAttribute",
    "userInformation",
    "customData",
    "sourceData",
    "signature"
})
@XmlRootElement(name = "authenticationDataResponse", namespace = "http://www.epaslaugos.lt/services/authentication")
public class AuthenticationDataResponseXml {

    @XmlElement(namespace = "http://www.epaslaugos.lt/services/authentication", required = true)
    protected AuthenticationProviderXml authenticationProvider;
    @XmlElement(namespace = "http://www.epaslaugos.lt/services/authentication")
    protected List<AuthenticationAttributePair> authenticationAttribute;
    @XmlElement(namespace = "http://www.epaslaugos.lt/services/authentication")
    protected List<UserInformationPair> userInformation;
    @XmlElement(namespace = "http://www.epaslaugos.lt/services/authentication")
    protected String customData;
    @XmlElement(namespace = "http://www.epaslaugos.lt/services/authentication")
    protected AuthenticationSourceData sourceData;
    @XmlElement(name = "Signature", required = true)
    protected SignatureType signature;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the authenticationProvider property.
     * 
     * @return
     *     possible object is
     *     {@link AuthenticationProviderXml }
     *     
     */
    public AuthenticationProviderXml getAuthenticationProvider() {
        return authenticationProvider;
    }

    /**
     * Sets the value of the authenticationProvider property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthenticationProviderXml }
     *     
     */
    public void setAuthenticationProvider(AuthenticationProviderXml value) {
        this.authenticationProvider = value;
    }

    /**
     * Gets the value of the authenticationAttribute property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the authenticationAttribute property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAuthenticationAttribute().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AuthenticationAttributePair }
     * 
     * 
     */
    public List<AuthenticationAttributePair> getAuthenticationAttribute() {
        if (authenticationAttribute == null) {
            authenticationAttribute = new ArrayList<AuthenticationAttributePair>();
        }
        return this.authenticationAttribute;
    }

    /**
     * Gets the value of the userInformation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the userInformation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUserInformation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UserInformationPair }
     * 
     * 
     */
    public List<UserInformationPair> getUserInformation() {
        if (userInformation == null) {
            userInformation = new ArrayList<UserInformationPair>();
        }
        return this.userInformation;
    }

    /**
     * Gets the value of the customData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomData() {
        return customData;
    }

    /**
     * Sets the value of the customData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomData(String value) {
        this.customData = value;
    }

    /**
     * Gets the value of the sourceData property.
     * 
     * @return
     *     possible object is
     *     {@link AuthenticationSourceData }
     *     
     */
    public AuthenticationSourceData getSourceData() {
        return sourceData;
    }

    /**
     * Sets the value of the sourceData property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthenticationSourceData }
     *     
     */
    public void setSourceData(AuthenticationSourceData value) {
        this.sourceData = value;
    }

    /**
     * Gets the value of the signature property.
     * 
     * @return
     *     possible object is
     *     {@link SignatureType }
     *     
     */
    public SignatureType getSignature() {
        return signature;
    }

    /**
     * Sets the value of the signature property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureType }
     *     
     */
    public void setSignature(SignatureType value) {
        this.signature = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
