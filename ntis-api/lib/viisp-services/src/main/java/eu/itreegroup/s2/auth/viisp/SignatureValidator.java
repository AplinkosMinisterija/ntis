package eu.itreegroup.s2.auth.viisp;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Iterator;

import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SignatureValidator {

    private static Logger log = LoggerFactory.getLogger(SignatureValidator.class);

    private PublicKey publicKey = null;

    private Boolean secureValidationEnabled = Boolean.FALSE;

    public SignatureValidator(File certificate, Boolean secureValidationEnabled) throws Exception {

        try (InputStream is = new FileInputStream(certificate)) {

            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate userCert = (X509Certificate) cf.generateCertificate(is);
            log.info("Loaded certificate " + certificate.getAbsolutePath() + " for signature validation");
            log.info("Certificate issuer: " + userCert.getIssuerX500Principal().getName());
            log.info("Certificate subject: " + userCert.getIssuerX500Principal().getName());
            publicKey = userCert.getPublicKey();
            if (publicKey == null) {
                throw new IllegalArgumentException("VIISP public key not found.");
            }

        }

        this.secureValidationEnabled = secureValidationEnabled;
    }

    public void validateSignature(Document doc) throws Exception {
        // Find Signature element
        NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
        if (nl.getLength() == 0) {
            throw new Exception("Cannot find Signature element");
        }

        // Create a DOM XMLSignatureFactory that will be used to unmarshal the
        // document containing the XMLSignature
        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

        log.debug("Validate signature: " + doc);
        DOMValidateContext valContext = new DOMValidateContext(publicKey, nl.item(0));

        // To work with Java17. Viisp returns signature (SHA1) which is banned when secure validation is ON.
        valContext.setProperty("org.jcp.xml.dsig.secureValidation", secureValidationEnabled);

        // unmarshal the XMLSignature
        XMLSignature signature = fac.unmarshalXMLSignature(valContext);

        // Validate the XMLSignature (generated above)
        boolean coreValidity = signature.validate(valContext);

        // Check core validation status
        if (coreValidity == false) {
            log.error("Signature failed core validation");
            boolean sv = signature.getSignatureValue().validate(valContext);
            log.info("signature validation status: " + sv);
            // check the validation status of each Reference
            Iterator<?> i = signature.getSignedInfo().getReferences().iterator();
            for (int j = 0; i.hasNext(); j++) {
                Reference ref = ((Reference) i.next());
                boolean refValid = ref.validate(valContext);
                log.info("ref[" + j + ", uri = " + ref.getURI() + "] validity status: " + refValid);
            }

            throw new Exception("Siganure is not valid");
        } else {
            log.debug("Signature passed core validation");
        }
    }

    /**
     * Sets ID attribute (named "id") for given node.
     * <p>
     * Necessary for XML signing/validation when running on JDK 7
     * 
     * @param node node to set ID attribute for
     * @see http://stackoverflow.com/questions/17331187/xml-dig-sig-error-after-upgrade-to-java7u25
     */
    public static void setIdAttribute(Node node) {
        log.debug("setIdAttribute on {}", node.getLocalName());
        Node idAttribute = node.getAttributes().getNamedItem("id");
        if (idAttribute != null) {
            ((Element) node).setIdAttribute("id", true);
            log.debug("idAtribute set on {}", node.getLocalName());
        }
    }

}
