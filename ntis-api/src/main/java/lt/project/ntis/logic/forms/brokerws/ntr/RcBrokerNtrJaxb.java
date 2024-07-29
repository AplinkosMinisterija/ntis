package lt.project.ntis.logic.forms.brokerws.ntr;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.xml.bind.JAXBContext;
import lt.project.ntis.brokerws.ntr761ws.GetReportResponse;
import lt.project.ntis.brokerws.ntr762.Dataset;
import lt.project.ntis.brokerws.ntr762ws.GetResultResponse;

/**
 * Pagalbinė integracijos su Registrų Centro BrokerWS sistema klasė skirta masiniam NTR pasikeitimų atsisiuntimui ir pritaikymui, 
 * naudojama darbui su JAXB tipais.
 */
@Component
public class RcBrokerNtrJaxb {

    @Autowired
    JAXBContext jaxbContext761ws;

    @Autowired
    JAXBContext jaxbContext762ws;

    @Autowired
    JAXBContext jaxbContext762;

    GetReportResponse parseGetReportResponse(String xml) throws Exception {
        return (GetReportResponse) jaxbContext761ws.createUnmarshaller().unmarshal(new StringReader(xml));
    }

    GetResultResponse parseGetResultResponse(String xml) throws Exception {
        return (GetResultResponse) jaxbContext762ws.createUnmarshaller().unmarshal(new StringReader(xml));
    }

    Dataset parseDataset(byte[] dataZipped) throws Exception {
        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(dataZipped))) {
            zis.getNextEntry();
            return parseDataset(zis);
        }
    }

    private Dataset parseDataset(InputStream xml) throws Exception {
        return (Dataset) jaxbContext762.createUnmarshaller().unmarshal(xml);
    }

}
