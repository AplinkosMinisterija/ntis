//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.12.19 at 03:51:32 PM EET 
//


package lt.atea.vaiisis.payment.model.xml;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for callbackResponse.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="callbackResponse">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SUCCESS"/>
 *     &lt;enumeration value="FAILURE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "callbackResponse", namespace = "http://www.epaslaugos.lt/services/payment")
@XmlEnum
@XmlRootElement(name="callbackResponse")
public enum CallbackResponseXml {

    SUCCESS,
    FAILURE;

    public String value() {
        return name();
    }

    public static CallbackResponseXml fromValue(String v) {
        return valueOf(v);
    }

}