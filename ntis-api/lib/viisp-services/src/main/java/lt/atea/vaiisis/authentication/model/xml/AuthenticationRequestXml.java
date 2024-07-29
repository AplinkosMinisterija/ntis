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
 *         &lt;element name="pid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="serviceTarget" type="{http://www.epaslaugos.lt/services/authentication}serviceTarget" minOccurs="0"/>
 *         &lt;element name="authenticationProvider" type="{http://www.epaslaugos.lt/services/authentication}authenticationProvider" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="authenticationAttribute" type="{http://www.epaslaugos.lt/services/authentication}authenticationAttribute" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="userInformation" type="{http://www.epaslaugos.lt/services/authentication}userInformation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="postbackUrl" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="customData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "pid",
    "serviceTarget",
    "authenticationProvider",
    "authenticationAttribute",
    "userInformation",
    "postbackUrl",
    "customData",
    "signature"
})
@XmlRootElement(name = "authenticationRequest", namespace = "http://www.epaslaugos.lt/services/authentication")
public class AuthenticationRequestXml {

    @XmlElement(namespace = "http://www.epaslaugos.lt/services/authentication", required = true)
    protected String pid;
    @XmlElement(namespace = "http://www.epaslaugos.lt/services/authentication")
    protected ServiceTargetXml serviceTarget;
    @XmlElement(namespace = "http://www.epaslaugos.lt/services/authentication")
    protected List<AuthenticationProviderXml> authenticationProvider;
    @XmlElement(namespace = "http://www.epaslaugos.lt/services/authentication")
    protected List<AuthenticationAttribute> authenticationAttribute;
    @XmlElement(namespace = "http://www.epaslaugos.lt/services/authentication")
    protected List<UserInformation> userInformation;
    @XmlElement(namespace = "http://www.epaslaugos.lt/services/authentication")
    @XmlSchemaType(name = "anyURI")
    protected String postbackUrl;
    @XmlElement(namespace = "http://www.epaslaugos.lt/services/authentication")
    protected String customData;
    @XmlElement(name = "Signature", required = true)
    protected SignatureType signature;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the pid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPid() {
        return pid;
    }

    /**
     * Sets the value of the pid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPid(String value) {
        this.pid = value;
    }

    /**
     * Gets the value of the serviceTarget property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceTargetXml }
     *     
     */
    public ServiceTargetXml getServiceTarget() {
        return serviceTarget;
    }

    /**
     * Sets the value of the serviceTarget property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceTargetXml }
     *     
     */
    public void setServiceTarget(ServiceTargetXml value) {
        this.serviceTarget = value;
    }

    /**
     * Gets the value of the authenticationProvider property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the authenticationProvider property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAuthenticationProvider().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AuthenticationProviderXml }
     * 
     * 
     */
    public List<AuthenticationProviderXml> getAuthenticationProvider() {
        if (authenticationProvider == null) {
            authenticationProvider = new ArrayList<AuthenticationProviderXml>();
        }
        return this.authenticationProvider;
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
     * {@link AuthenticationAttribute }
     * 
     * 
     */
    public List<AuthenticationAttribute> getAuthenticationAttribute() {
        if (authenticationAttribute == null) {
            authenticationAttribute = new ArrayList<AuthenticationAttribute>();
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
     * {@link UserInformation }
     * 
     * 
     */
    public List<UserInformation> getUserInformation() {
        if (userInformation == null) {
            userInformation = new ArrayList<UserInformation>();
        }
        return this.userInformation;
    }

    /**
     * Gets the value of the postbackUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostbackUrl() {
        return postbackUrl;
    }

    /**
     * Sets the value of the postbackUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostbackUrl(String value) {
        this.postbackUrl = value;
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
