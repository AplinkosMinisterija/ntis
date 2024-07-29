
package lt.project.ntis.brokerws.get;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
  */
// @WebServiceClient(name = "Get", targetNamespace = "http://www.registrucentras.lt", wsdlLocation = "https://wstest.registrucentras.lt/broker/index.php?wsdl")
@WebServiceClient(name = "Get", targetNamespace = "http://www.registrucentras.lt", wsdlLocation = "http://localhost:8090/axis/services/GetPort?wsdl")
public class Get extends Service {

    private final static URL GET_WSDL_LOCATION;

    private final static WebServiceException GET_EXCEPTION;

    private final static QName GET_QNAME = new QName("http://www.registrucentras.lt", "Get");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8090/axis/services");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        GET_WSDL_LOCATION = url;
        GET_EXCEPTION = e;
    }

    public Get() {
        super(__getWsdlLocation(), GET_QNAME);
    }

    public Get(WebServiceFeature... features) {
        super(__getWsdlLocation(), GET_QNAME, features);
    }

    public Get(URL wsdlLocation) {
        super(wsdlLocation, GET_QNAME);
    }

    public Get(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, GET_QNAME, features);
    }

    public Get(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Get(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns GetPortType
      */
    @WebEndpoint(name = "GetPort")
    public GetPortType getGetPort() {
        return super.getPort(new QName("http://www.registrucentras.lt", "GetPort"), GetPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns GetPortType
      */
    @WebEndpoint(name = "GetPort")
    public GetPortType getGetPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.registrucentras.lt", "GetPort"), GetPortType.class, features);
    }

    private static URL __getWsdlLocation() {
        if (GET_EXCEPTION != null) {
            throw GET_EXCEPTION;
        }
        return GET_WSDL_LOCATION;
    }

    @XmlElementDecl(namespace = "http://www.registrucentras.lt", name = "Get")
    public JAXBElement<Get> GetData(Get value) {
        return new JAXBElement<Get>(GET_QNAME, Get.class, null, value);
    }
}
