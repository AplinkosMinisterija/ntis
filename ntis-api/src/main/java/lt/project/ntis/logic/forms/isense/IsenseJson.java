package lt.project.ntis.logic.forms.isense;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;

import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;

/**
 * Pagalbinė integracijos su iSense klasė, naudojama darbui su JSON dokumentais.
 */
@Component
public class IsenseJson {

    private static final String SESSION_STATUS_FINISHED = "FINISHED";

    private static final String FILE_STATUS_SIGNED = "SIGNED";

    private static final String JSON_NODE_EDOCS = "edocs";

    private static final String JSON_NODE_EDOC_ID = "edocId";

    private static final String JSON_VALUE_NULL_ADDRESS = "Nenurodyta";

    @Value("${isense.signingCallbackUrl}")
    private String signingCallbackUrl;

    @Value("${isense.signingReturnUrl}")
    private String signingReturnUrl;

    @Value("${isense.previewReturnUrl}")
    private String previewReturnUrl;

    boolean isFileSigned(String callbackBody) throws Exception {
        JsonNode callbackNode = new ObjectMapper().readTree(callbackBody);
        return SESSION_STATUS_FINISHED.equals(getJsonNodeFieldValue(callbackNode, "sessionStatus", callbackBody))
                && FILE_STATUS_SIGNED.equals(getJsonNodeFieldValue(callbackNode.get(JSON_NODE_EDOCS).required(0), "status", callbackBody));
    }

    static String getJsonFieldValue(String json, String fieldName) throws Exception {
        return getJsonNodeFieldValue(new ObjectMapper().readTree(json), fieldName, null);
    }

    private static String getJsonNodeFieldValue(JsonNode jsonNode, String fieldName, String fullJson) throws Exception {
        JsonNode valueNode = jsonNode.get(fieldName);
        if (valueNode == null) {
            throw new Exception(
                    String.format("Could not find field '%s' in received json %s", fieldName, fullJson != null ? fullJson : jsonNode.toPrettyString()));
        }
        if (!valueNode.isValueNode()) {
            throw new Exception(String.format("Found field '%s' of non-value json type %s in received json %s", fieldName, valueNode.getNodeType(),
                    fullJson != null ? fullJson : jsonNode.toPrettyString()));
        }
        if (!valueNode.isTextual()) {
            throw new Exception(String.format("Found field '%s' of %s type (expected %s) in received json %s", fieldName, valueNode.getNodeType(),
                    JsonNodeType.STRING, fullJson != null ? fullJson : jsonNode.toPrettyString()));
        }
        return valueNode.textValue();
    }

    String retrieveEdocIdFromSessionJson(String json) throws Exception {
        JsonNode edocsNode = new ObjectMapper().readTree(json).get(JSON_NODE_EDOCS);
        if (edocsNode == null) {
            throw new Exception(String.format("Could not find array field 'edocs' in received json %s", json));
        }
        if (!edocsNode.isArray()) {
            throw new Exception(String.format("Found field 'edocs' of non-array json type %s in received json %s", edocsNode.getNodeType(), json));
        }
        ArrayNode arrayNode = (ArrayNode) edocsNode;
        if (arrayNode.size() == 0) {
            throw new Exception(String.format("Edocs array is empty in received json %s", json));
        }
        return getJsonNodeFieldValue(arrayNode.required(0), JSON_NODE_EDOC_ID, json);
    }

    String buildMinimalCreateEdocRequestBody(String dataFileId, SprOrganizationsDAO supplierOrganization, SprOrganizationsDAO clientOrganization,
            SprPersonsDAO clientPerson, SprFilesDAO file) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        ArrayNode authors = mapper.createArrayNode();
        authors.add(buildAuthorNode(mapper, getOrganizationAddress(supplierOrganization), supplierOrganization.getOrg_code(), false,
                supplierOrganization.getOrg_name()));

        if (clientOrganization != null) {
            authors.add(buildAuthorNode(mapper, getOrganizationAddress(clientOrganization), clientOrganization.getOrg_code(), false,
                    clientOrganization.getOrg_name()));
        } else if (clientPerson != null) {
            authors.add(buildAuthorNode(mapper, getPersonAddress(clientPerson), clientPerson.getPer_code(), true, clientPerson.getPer_name()));
        }

        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("edocType", "ADOC_BEDOC");
        rootNode.set("documentDefinition", mapper.createObjectNode().put("sort", "Sutartis").put("title", file.getFil_name()));
        rootNode.put("creationDate", Instant.now().truncatedTo(ChronoUnit.MILLIS).toString());
        rootNode.set("authors", authors);
        rootNode.set("mainDocument", mapper.createObjectNode().put("id", dataFileId));
        return mapper.writer().writeValueAsString(rootNode);
    }

    private static ObjectNode buildAuthorNode(ObjectMapper mapper, String address, String code, boolean individual, String name) {
        return mapper.createObjectNode().put("address", address).put("code", code).put("individual", individual).put("name", name);
    }

    private static String getOrganizationAddress(SprOrganizationsDAO organization) {
        String result = organization.getOrg_address();
        return result == null || result.isBlank() ? JSON_VALUE_NULL_ADDRESS : result;
    }

    private static String getPersonAddress(SprPersonsDAO person) {
        String result = JSON_VALUE_NULL_ADDRESS;
        String city = person.getPer_address_city();
        if (city != null && !city.isBlank()) {
            StringBuilder buf = new StringBuilder().append(city);
            String street = person.getPer_address_street();
            if (street != null && !street.isBlank()) {
                buf.append(", ").append(street);
                String house = person.getPer_address_house_number();
                if (house != null && !house.isBlank()) {
                    buf.append(" ").append(house);
                    String flat = person.getPer_address_flat_number();
                    if (flat != null && !flat.isBlank()) {
                        buf.append("-").append(flat);
                    }
                }
            }
            result = buf.toString();
        }
        return result;
    }

    String buildMinimalSigningRequestBody(String edocId, SprPersonsDAO person) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode signatureOptions = mapper.createObjectNode();
        signatureOptions.put("signingPurpose", "signature");
        signatureOptions.set("signer", mapper.createObjectNode().put("individualName", person.getPer_name()));
        signatureOptions.put("signatureLevel", "T");

        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("locale", "LT");
        rootNode.put("callbackAsyncEnabled", false);
        rootNode.set(JSON_NODE_EDOCS, mapper.createArrayNode().add(mapper.createObjectNode().put(JSON_NODE_EDOC_ID, edocId)));
        rootNode.set("signatureOptions", signatureOptions);
        rootNode.put("canSelectReferences", false);
        rootNode.put("confirmRecurrentSigner", true);
        rootNode.put("personCode", person.getPer_code());
        rootNode.put("messageToPhone", "LR AM NTIS");
        rootNode.put("callbackUrl", signingCallbackUrl);
        rootNode.put("returnUrl", signingReturnUrl);
        rootNode.set("enabledWizardSteps", mapper.createArrayNode());
        return mapper.writer().writeValueAsString(rootNode);
    }

    String buildMinimalPreviewRequestBody(String edocId) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("locale", "LT");
        rootNode.put(JSON_NODE_EDOC_ID, edocId);
        rootNode.put("returnUrl", previewReturnUrl);
        rootNode.put("receptionRequired", false);
        rootNode.put("registrationRequired", false);
        return mapper.writer().writeValueAsString(rootNode);
    }

}
