package lt.project.ntis.logic.forms.brokerws;

import java.io.StringReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.dao.common.Utils;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.Unmarshaller;
import lt.project.ntis.brokerws.ntr40.ASMENSREGISTRAI;
import lt.project.ntis.brokerws.ntr40.REGISTRAI;
import lt.project.ntis.brokerws.ntr40.REGISTRAS;
import lt.project.ntis.brokerws.ntr40.TEISE;
import lt.project.ntis.brokerws.ntr95.ASMENYS;
import lt.project.ntis.brokerws.ntr95.ASMUO;
import lt.project.ntis.brokerws.ntr95.OBJEKTAI;
import lt.project.ntis.brokerws.ntr95.OBJEKTAS;
import lt.project.ntis.models.NtrOwnerModel;

/**
 * Pagalbinė integracijos su Registrų Centro BrokerWS sistema klasė, naudojama darbui su JAXB tipais.
 */
@Component
public class RcBrokerJaxb {

    private static final BigInteger TEIT_TIPAS_NUOSAVYBE = BigInteger.ONE;

    @Autowired
    JAXBContext jaxbContext40;

    @Autowired
    JAXBContext jaxbContext4020;

    @Autowired
    JAXBContext jaxbContext46;

    @Autowired
    JAXBContext jaxbContext47;

    @Autowired
    JAXBContext jaxbContext48;

    @Autowired
    JAXBContext jaxbContext605;

    @Autowired
    JAXBContext jaxbContext95;

    Gr605Data collectResponse605(String xml) throws Exception {
        lt.project.ntis.brokerws.gr605.PersonDetailInformation personInfo = (lt.project.ntis.brokerws.gr605.PersonDetailInformation) jaxbContext605
                .createUnmarshaller().unmarshal(new StringReader(xml));
        return new Gr605Data(personInfo.getAtsAsmKodas(), personInfo.getAtsAsmVardas(), personInfo.getAtsAsmPav());

    }

    Collection<Integer> collectObjkodasResponse46(String xml) throws Exception {
        Set<Integer> result = new HashSet<>();
        lt.project.ntis.brokerws.jar46.PAIESKA paieska = (lt.project.ntis.brokerws.jar46.PAIESKA) jaxbContext46.createUnmarshaller()
                .unmarshal(new StringReader(xml));
        for (lt.project.ntis.brokerws.jar46.JURASMUO jurAsmuo : paieska.getJASARASAS().getJURASMUO()) {
            result.add(jurAsmuo.getOBJKODAS());
        }
        return result;
    }

    Collection<Integer> collectObjkodasResponse47(String xml) throws Exception {
        Set<Integer> result = new HashSet<>();
        lt.project.ntis.brokerws.jar47.PAIESKA paieska = (lt.project.ntis.brokerws.jar47.PAIESKA) jaxbContext47.createUnmarshaller()
                .unmarshal(new StringReader(xml));
        for (lt.project.ntis.brokerws.jar47.OBJEKTAI objektas : paieska.getOBJEKTAI()) {
            result.add(objektas.getOBJKODAS());
        }
        return result;
    }

    Collection<Integer> collectObjkodasResponse48(String xml) throws Exception {
        Set<Integer> result = new HashSet<>();
        lt.project.ntis.brokerws.jar48.PAIESKA paieska = (lt.project.ntis.brokerws.jar48.PAIESKA) jaxbContext48.createUnmarshaller()
                .unmarshal(new StringReader(xml));
        for (lt.project.ntis.brokerws.jar48.JURASMUO jurAsmuo : paieska.getJASARASAS().getJURASMUO()) {
            result.add(jurAsmuo.getOBJKODAS());
        }
        return result;
    }

    private static final int TATR_KODAS_PHONE = 10;

    private static final int TATR_KODAS_MOBILE_PHONE = 30;

    private static final int TATR_KODAS_EMAIL = 40;

    private static final int TATR_KODAS_WEBSITE = 50;

    Jar4020Data collectResponse4020(String xml) throws Exception {
        lt.project.ntis.brokerws.jar4020.OBJEKTAI objektai = (lt.project.ntis.brokerws.jar4020.OBJEKTAI) jaxbContext4020.createUnmarshaller()
                .unmarshal(new StringReader(xml));
        Jar4020Data result = new Jar4020Data(objektai.getOBJKODAS(), objektai.getOBJPAV(), objektai.getFORMKODAS(), objektai.getJADTEKSTAS(),
                Utils.getDateFromString(objektai.getOBJREGDATA(), "yyyy-MM-dd"));

        for (lt.project.ntis.brokerws.jar4020.TEKSTINIAIDUOMENYS td : objektai.getTEKSTINIAIDUOMENYS()) {
            int tatrKodas = td.getTATRKODAS().intValue();
            String tatrTekstas = td.getTXDTEKSTAS();
            switch (tatrKodas) {
                case TATR_KODAS_PHONE:
                    result.setText10(tatrTekstas);
                    break;
                case TATR_KODAS_MOBILE_PHONE:
                    result.setText30(tatrTekstas);
                    break;
                case TATR_KODAS_EMAIL:
                    result.setText40(tatrTekstas);
                    break;
                case TATR_KODAS_WEBSITE:
                    result.setText50(tatrTekstas);
                    break;
            }
        }
        return result;
    }

    ASMENSREGISTRAI toAsmensRegistrai(String xml) throws Exception {
        return (ASMENSREGISTRAI) jaxbContext40.createUnmarshaller().unmarshal(new StringReader(xml));
    }

    lt.project.ntis.brokerws.ntr95.REGISTRAS toRegistras(String xml) throws Exception {
        Unmarshaller unmarshaller = jaxbContext95.createUnmarshaller();
        return (lt.project.ntis.brokerws.ntr95.REGISTRAS) unmarshaller.unmarshal(new StringReader(xml));
    }

    List<lt.project.ntis.brokerws.ntr95.REGISTRAS> toRegistrasList(List<String> xmlList) throws Exception {
        List<lt.project.ntis.brokerws.ntr95.REGISTRAS> result = new ArrayList<>();
        if (xmlList != null && !xmlList.isEmpty()) {
            Unmarshaller unmarshaller = jaxbContext95.createUnmarshaller();
            for (String xml : xmlList) {
                result.add((lt.project.ntis.brokerws.ntr95.REGISTRAS) unmarshaller.unmarshal(new StringReader(xml)));
            }
        }
        return result;
    }

    static List<NtrOwnerModel> toNtrOwnerData(lt.project.ntis.brokerws.ntr95.REGISTRAS registras) throws Exception {
        lt.project.ntis.brokerws.ntr95.ASMENYS asmenys = streamChildrenOfType(registras, GET_REGISTRAS_95_CHILDREN,
                lt.project.ntis.brokerws.ntr95.ASMENYS.class).findFirst().orElse(null);
        List<lt.project.ntis.brokerws.ntr95.ASMUO> asmuoList = streamChildrenOfType(asmenys, GET_ASMENYS_CHILDREN, lt.project.ntis.brokerws.ntr95.ASMUO.class)
                .toList();

        List<NtrOwnerModel> result = new ArrayList<>();
        for (lt.project.ntis.brokerws.ntr95.ASMUO asmuo : asmuoList) {
            if (asmuo.getTipas() == 1) {
                NtrOwnerModel owner = new NtrOwnerModel();
                owner.setOwner_name(getStringChildByQName(asmuo, GET_ASMUO_CHILDREN, "VARDAS"));
                owner.setOwner_lastname(getStringChildByQName(asmuo, GET_ASMUO_CHILDREN, "PAVARDE"));
                result.add(owner);
            } else if (asmuo.getTipas() == 2) {
                NtrOwnerModel owner = new NtrOwnerModel();
                Long kodas = asmuo.getKodas();
                if (kodas != null) {
                    owner.setOwner_code(Long.toString(kodas));
                }
                owner.setOwner_organization_name(getStringChildByQName(asmuo, GET_ASMUO_CHILDREN, "PAVADINIMAS"));
                result.add(owner);
            }
        }
        return result;
    }

    static List<Ntr40Data> toNtr40Data(ASMENSREGISTRAI asmensregistrai) throws Exception {
        Function<REGISTRAS, Ntr40Data> toNtr40Data = registras -> {
            return new Ntr40Data(registras.getRegTarnNr(), registras.getRegNr());
        };

        REGISTRAI registrai = streamChildrenOfType(asmensregistrai, GET_ASMENSREGISTRAI_CHILDREN, REGISTRAI.class).findFirst().orElseThrow();
        return streamChildrenOfType(registrai, GET_REGISTRAI_CHILDREN, REGISTRAS.class).filter(RcBrokerJaxb::hasNuosavybesTeise).map(toNtr40Data).toList();
    }

    static Set<Long> toObjInvCodes(lt.project.ntis.brokerws.ntr95.REGISTRAS registras) throws Exception {
        return streamChildrenOfType(registras, GET_REGISTRAS_95_CHILDREN, OBJEKTAI.class).map(OBJEKTAI::getOBJEKTAS).flatMap(List::stream)
                .mapToLong(OBJEKTAS::getObjKodas).boxed().collect(Collectors.toSet());
    }

    private static boolean hasNuosavybesTeise(REGISTRAS registras) {
        TEISE teise = streamChildrenOfType(registras, GET_REGISTRAS_40_CHILDREN, TEISE.class).findFirst().orElseThrow();
        return TEIT_TIPAS_NUOSAVYBE.equals(teise.getTeitTipas());
    }

    private static <T> Stream<T> streamChildrenOfType(Object dataObject, Function<Object, List<Object>> contentListProducer, Class<T> ofType) {
        Stream<T> result = null;
        if (dataObject == null) {
            result = Stream.empty();
        } else {
            List<Object> contentList = contentListProducer.apply(dataObject);
            if (contentList == null || contentList.isEmpty()) {
                result = Stream.empty();
            } else {
                result = contentList.stream().filter(listItem -> ofType.isAssignableFrom(listItem.getClass())).map(ofType::cast);
            }
        }
        return result;
    }

    private static String getStringChildByQName(Object dataObject, Function<Object, List<Object>> contentListProducer, String childQName) {
        List<Object> contentList = contentListProducer.apply(dataObject);
        return (contentList == null || contentList.isEmpty()) ? ""
                : (String) contentList.stream().filter(listItem -> JAXBElement.class.isAssignableFrom(listItem.getClass())).map(JAXBElement.class::cast)
                        .filter(el -> childQName.equals(el.getName().getLocalPart())).map(JAXBElement::getValue).findFirst().orElse("");
    }

    private static final Function<Object, List<Object>> GET_REGISTRAI_CHILDREN = obj -> ((REGISTRAI) obj).getContent();

    private static final Function<Object, List<Object>> GET_ASMENSREGISTRAI_CHILDREN = obj -> ((ASMENSREGISTRAI) obj).getContent();

    private static final Function<Object, List<Object>> GET_ASMENYS_CHILDREN = obj -> ((ASMENYS) obj).getContent();

    private static final Function<Object, List<Object>> GET_ASMUO_CHILDREN = obj -> ((ASMUO) obj).getContent();

    private static final Function<Object, List<Object>> GET_REGISTRAS_40_CHILDREN = obj -> ((REGISTRAS) obj).getContent();

    private static final Function<Object, List<Object>> GET_REGISTRAS_95_CHILDREN = obj -> ((lt.project.ntis.brokerws.ntr95.REGISTRAS) obj).getContent();

}
