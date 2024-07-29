package lt.project.ntis.logic.forms.rcadrws;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import lt.project.ntis.rcadrws.adm.ADMINISTRACINIAI;
import lt.project.ntis.rcadrws.adrxy.ADRESAI;
import lt.project.ntis.rcadrws.gat.GATVES;
import lt.project.ntis.rcadrws.gyv.GYVENVIETES;
import lt.project.ntis.rcadrws.pat.PATALPOS;

/**
 * Pagalbinė integracijos su Registrų Centro AdrWS sistema klasė, naudojama darbui su JAXB tipais.
 */
@Component
public class RcAdrJaxb {

    @Autowired
    JAXBContext jaxbContextAdm;

    @Autowired
    JAXBContext jaxbContextGyv;

    @Autowired
    JAXBContext jaxbContextGat;

    @Autowired
    JAXBContext jaxbContextAdrxy;

    @Autowired
    JAXBContext jaxbContextPat;

    ADMINISTRACINIAI collectResponseAdm(String xml) throws JAXBException {
        return (ADMINISTRACINIAI) jaxbContextAdm.createUnmarshaller().unmarshal(new StringReader(xml));
    }

    GYVENVIETES collectResponseGyv(String xml) throws JAXBException {
        return (GYVENVIETES) jaxbContextGyv.createUnmarshaller().unmarshal(new StringReader(xml));
    }

    GATVES collectResponseGat(String xml) throws JAXBException {
        return (GATVES) jaxbContextGat.createUnmarshaller().unmarshal(new StringReader(xml));
    }

    ADRESAI collectResponseAdrxy(String xml) throws JAXBException {
        return (lt.project.ntis.rcadrws.adrxy.ADRESAI) jaxbContextAdrxy.createUnmarshaller().unmarshal(new StringReader(xml));
    }

    PATALPOS collectResponsePat(String xml) throws JAXBException {
        return (PATALPOS) jaxbContextPat.createUnmarshaller().unmarshal(new StringReader(xml));
    }

}
