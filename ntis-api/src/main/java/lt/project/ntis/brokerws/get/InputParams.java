
package lt.project.ntis.brokerws.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for InputParams complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InputParams">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="ActionType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CallerCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EndUserInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Parameters" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Time" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Signature" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CallerSignature" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InputParams", propOrder = {

})
public class InputParams {

    @XmlElement(name = "ActionType", required = true)
    protected String actionType;

    @XmlElement(name = "CallerCode", required = true)
    protected String callerCode;

    @XmlElement(name = "EndUserInfo")
    protected String endUserInfo;

    @XmlElement(name = "Parameters", required = true)
    protected String parameters;

    @XmlElement(name = "Time", required = true)
    protected String time;

    @XmlElement(name = "Signature", required = true)
    protected String signature;

    @XmlElement(name = "CallerSignature")
    protected String callerSignature;

    /**
     * Gets the value of the actionType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActionType() {
        return actionType;
    }

    /**
     * Sets the value of the actionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActionType(String value) {
        this.actionType = value;
    }

    /**
     * Gets the value of the callerCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCallerCode() {
        return callerCode;
    }

    /**
     * Sets the value of the callerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCallerCode(String value) {
        this.callerCode = value;
    }

    /**
     * Gets the value of the endUserInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndUserInfo() {
        return endUserInfo;
    }

    /**
     * Sets the value of the endUserInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndUserInfo(String value) {
        this.endUserInfo = value;
    }

    /**
     * Gets the value of the parameters property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParameters() {
        return parameters;
    }

    /**
     * Sets the value of the parameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParameters(String value) {
        this.parameters = value;
    }

    /**
     * Gets the value of the time property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTime(String value) {
        this.time = value;
    }

    /**
     * Gets the value of the signature property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignature() {
        return signature;
    }

    /**
     * Sets the value of the signature property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignature(String value) {
        this.signature = value;
    }

    public void ChangeParametersToPDF() {
        this.parameters = this.parameters.replace("xml", "pdf");
    }

    /**
     * Gets the value of the callerSignature property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCallerSignature() {
        return callerSignature;
    }

    /**
     * Sets the value of the callerSignature property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCallerSignature(String value) {
        this.callerSignature = value;
    }

}
