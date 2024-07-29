package eu.itreegroup.s2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import eu.itreegroup.s2.auth.viisp.JAXBContextCache;
import eu.itreegroup.s2.auth.viisp.SignatureValidator;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

public class ViispBaseRequestGenerator {

    private static Logger log = LoggerFactory.getLogger(ViispBaseRequestGenerator.class);

    protected static final String ENVELOPE_START_SIGN = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:aut=\"http://www.epaslaugos.lt/services/authentication\" xmlns:xd=\"http://www.w3.org/2000/09/xmldsig#\"><soapenv:Header/><soapenv:Body>";

    protected static final String ENVELOPE_START_NO_SIGN = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Header/><soapenv:Body>";

    protected static final String ENVELOPE_END = "</soapenv:Body></soapenv:Envelope>";

    private static XMLSignatureFactory XML_SIGNATURE_FACTORY = null;

    protected PrivateKey privateKey = null;

    protected PublicKey publicKey = null;

    protected SignatureValidator signatureValidator;

    protected CertConfig certConfig;

    protected void initKeys() {
        XML_SIGNATURE_FACTORY = XMLSignatureFactory.getInstance("DOM");

        File keyStoreFile = certConfig.getKeyStoreFile();
        if (keyStoreFile == null) {
            throw new IllegalArgumentException("Cannot load keystore, keystore path is empty");
        }
        log.debug("Using keystore for signing: " + keyStoreFile.getAbsolutePath());
        File certificateFile = certConfig.getViispSignCertificateFile();
        if (certificateFile == null) {
            throw new IllegalArgumentException("Cannot load certificate, file is null");
        }
        log.debug("Using certificate for signature validation: " + certificateFile);
        String password = certConfig.getKeyStorePassword();
        try (InputStream is = new FileInputStream(keyStoreFile)) {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(is, password.toCharArray());
            for (Enumeration<String> e = keyStore.aliases(); e.hasMoreElements();) {
                String alias = e.nextElement();
                if (keyStore.isKeyEntry(alias)) {
                    privateKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());
                    X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);
                    publicKey = cert.getPublicKey();
                    break;
                }
            }
            if (privateKey == null) {
                throw new Exception("Private key not found");
            }
            if (publicKey == null) {
                throw new Exception("Public key not found");
            }
            signatureValidator = new SignatureValidator(certConfig.getViispSignCertificateFile(), certConfig.getSecureValidationEnabled());
        } catch (Exception e) {
            throw new IllegalStateException("Could not prepare VIISP keystore: " + e, e);
        }
    }

    public static Node marshal(Object data) throws JAXBException {
        String packageName = data.getClass().getPackage().getName();
        JAXBContext jc = JAXBContextCache.getJAXBContext(packageName);
        DOMResult result = new DOMResult();
        Marshaller marshaller = jc.createMarshaller();
        marshaller.marshal(data, result);
        return result.getNode();
    }

    public ByteArrayOutputStream getSignedXml(Node node, String referenceUri) throws Exception {
        signNode(node, referenceUri);
        return getNodeAsOutputStream(node);
    }

    protected ByteArrayOutputStream getNodeAsOutputStream(Node node) throws Exception {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer trans = tf.newTransformer();
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        trans.transform(new DOMSource(node), new StreamResult(output));
        return output;
    }

    public void signNode(Node node, String uri) throws Exception {
        DOMSignContext dsc = new DOMSignContext(privateKey, node);
        XMLSignatureFactory fac = XML_SIGNATURE_FACTORY;
        List<String> prefixList = new ArrayList<String>();
        prefixList.add(node.getPrefix());
        C14NMethodParameterSpec spec = new ExcC14NParameterSpec(prefixList);
        List<Transform> transforms = new ArrayList<Transform>();
        transforms.add(fac.newTransform(CanonicalizationMethod.ENVELOPED, (TransformParameterSpec) null));
        transforms.add(fac.newTransform(CanonicalizationMethod.EXCLUSIVE, spec));
        Reference ref = fac.newReference(uri, fac.newDigestMethod(DigestMethod.SHA1, null), transforms, null, null);
        SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(CanonicalizationMethod.EXCLUSIVE, spec),
                fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null), Collections.singletonList(ref));
        KeyInfoFactory kif = fac.getKeyInfoFactory();
        KeyValue kv = kif.newKeyValue(publicKey);
        KeyInfo ki = kif.newKeyInfo(Collections.singletonList(kv));
        XMLSignature signature = fac.newXMLSignature(si, ki);
        signature.sign(dsc);
    }

    public Object unmarshal(Node responseNode, Class<?> dataClass) throws Exception {
        String packageName = dataClass.getPackage().getName();
        JAXBContext jc = JAXBContextCache.getJAXBContext(packageName);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        return unmarshaller.unmarshal(responseNode);
    }

    public void validateSignature(Document doc) throws Exception {
        signatureValidator.validateSignature(doc);
    }

    /**
     * Sets ID attribute (named "id") for given node.
     * <p>
     * Necessary for XML signing/validation when running on JDK 7
     *
     * @param node node to set ID attribute for
     * @see http://stackoverflow.com/questions/17331187/xml-dig-sig-error-after-upgrade-to-java7u25
     */
    protected static void setIdAttribute(Node node) {
        SignatureValidator.setIdAttribute(node);
    }

    public Document convertStringToXMLDocument(String xmlString) throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);
        return dbFactory.newDocumentBuilder().parse(new InputSource(new StringReader(xmlString.replaceAll("\r", "&#13;"))));
    }

    public CertConfig getCertConfig() {
        return certConfig;
    }

    public void setCertConfig(CertConfig config) {
        this.certConfig = config;
    }

    public String toSoapXmlSigned(Object request, String id) throws Exception {
        Document doc = (Document) marshal(request);
        setIdAttribute(doc.getChildNodes().item(0));
        String xml = ENVELOPE_START_SIGN + StringUtils.substringAfter(getSignedXml(doc.getFirstChild(), "#" + id).toString("UTF-8"), "?>") + ENVELOPE_END;
        log.debug("Request: " + xml);
        return xml;
    }

    public String toSoapXmlUnsigned(Object request) throws Exception {
        Document doc = (Document) marshal(request);
        String xml = ENVELOPE_START_NO_SIGN + StringUtils.substringAfter(getNodeAsOutputStream(doc.getFirstChild()).toString("UTF-8"), "?>") + ENVELOPE_END;
        log.debug("Request: " + xml);
        return xml;
    }

}
