package lt.project.ntis.rest.controller;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import eu.itreegroup.s2.server.rest.S2RestApiService;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lt.project.ntis.logic.forms.NtisApi;
import lt.project.ntis.models.api.ApiDeclareCentralizedWastewaterResponse;
import lt.project.ntis.models.api.ApiDeclareTechOrderResponse;
import lt.project.ntis.models.api.ApiDeclareWastewaterRemovalOrderResponse;
import lt.project.ntis.models.api.ApiLinks;
import lt.project.ntis.models.api.ApiList;
import lt.project.ntis.models.api.ApiTechnicalSupportOrderResponse;
import lt.project.ntis.models.api.ApiWastewaterRemovalOrderResponse;
import lt.project.ntis.models.api.ApiWastewaterTreatmentFacilityResponse;
import lt.project.ntis.rest.controller.models.ApiCentralizedWastewaterPostParameters;
import lt.project.ntis.rest.controller.models.ApiErrorMessage;
import lt.project.ntis.rest.controller.models.ApiPagingParameters;
import lt.project.ntis.rest.controller.models.ApiTechnicalSupportOrderParameters;
import lt.project.ntis.rest.controller.models.ApiTechnicalSupportOrdersListParameters;
import lt.project.ntis.rest.controller.models.ApiWastewaterRemovalOrderParameters;
import lt.project.ntis.rest.controller.models.ApiWastewaterRevomalOrdersListParameters;
import lt.project.ntis.rest.controller.models.ApiWastewaterTreatmentFacilityListParameters;

@RestController
@RequestMapping("/api/")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Validated
public class NtisApiController extends S2RestApiService<SprBackendUserSession> {

    @Value("${app.host}")
    private String hostUrl;

    @Autowired
    NtisApi ntisApi;

    @ApiResponse(responseCode = "200", description = "Successful response")
    @ApiResponse(responseCode = "400", description = "Bad request, validation failed", content = @Content(mediaType = "", examples = {}, schema = @Schema(implementation = ApiErrorMessage.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "", examples = {}))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "", examples = {}))
    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(mediaType = "", examples = {}))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "", examples = {}))
    @Operation(summary = "Gauti nuotekų tvarkymo įrenginių sąrašą.", description = """
            Nuotekų tvarkymo įrenginių galima ieškoti šiais būdais (prioriteto tvarka):
            nurodant nuotekų tvarkymo įrenginio aptarnaujamo objekto Nekilnojamojo turto registro (NTR) numerį. Jeigu randamas NTR objektas - grąžinamas visų jį aptarnaujančių nuotekų tvarkymo įrenginių sąrašas,
            nurodant nuotekų tvarkymo įrenginio aptarnaujamo objekto patalpos adreso identifikavimo kodą (PAT_KODAS). Jeigu randamas NTR objektas - grąžinamas visų jį aptarnaujančių nuotekų tvarkymo įrenginių sąrašas,
            nurodant detalų adresą pagal Adresų registro kodus ar pavadinimus ir pagal adreso eilutę (ar jos fragmentą). Galima atlikti paiešką gyvenvietės lygiu ar smulkiau (t.y., visada privaloma nurodyti mažiausiai savivaldybę ir gyvenvietę).
            Visi adresų kodai atitinka Registrų Centro duomenis: https://www.registrucentras.lt/p/1187
            """)

    @GetMapping(value = "/wastewater-treatment-facilities-list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiList<ApiWastewaterTreatmentFacilityResponse>> wastewaterTreatmentFacilitiesList(
            @Valid @ParameterObject ApiWastewaterTreatmentFacilityListParameters filterParameters, HttpServletRequest request) throws Exception {

        var data = ntisApi.getApiWtfList(this.getDBConnection(), this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_per_id(), filterParameters.getPageSize(),
                filterParameters.getAddressFragment(), filterParameters.getMunicipalityCode(), filterParameters.getResidenceCode(),
                filterParameters.getStreetCode(), filterParameters.getBuildingNumber(), filterParameters.getFlatNumber(),
                filterParameters.getMunicipalityName(), filterParameters.getResidenceName(), filterParameters.getStreetName(),
                filterParameters.getRealEstateId(), filterParameters.getAobCode(), filterParameters.getPatCode(), filterParameters.getPageBefore(),
                filterParameters.getPageAfter(), request);

        Integer maxId = !data.isEmpty() ? data.get(data.size() - 1).getId().intValue() : null;
        Integer minId = !data.isEmpty() ? data.get(0).getId().intValue() : null;
        var links = getNavigationLinks(request, data.size(), maxId, minId, filterParameters);

        return okResponse(new ApiList<ApiWastewaterTreatmentFacilityResponse>(data, links));

    }

    @Operation(summary = "Gauti nuotekų (dumblo) išvežimo paslaugos užsakymų sąrašą.", description = "Atliekant paiešką privaloma nurodyti laikotarpį, už kurį pageidaujama gauti užsakymų sąrašą. Pasirinktinai galima nurodyti užsakymo būseną, gauti užsakymus konkrečiam nurodytam įrenginiui arba gauti konkretaus nurodyto užsakymo informaciją. Yra grąžinami tik tie paslaugos užsakymai, kuriuos naudotojas pagal turimas teises gali matyti NTIS portale.")
    @ApiResponse(responseCode = "200", description = "Successful response")
    @ApiResponse(responseCode = "400", description = "Bad request, validation failed", content = @Content(mediaType = "", examples = {}, schema = @Schema(implementation = ApiErrorMessage.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "", examples = {}))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "", examples = {}))
    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(mediaType = "", examples = {}))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "", examples = {}))
    @GetMapping(value = "/wastewater-removal-orders-list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiList<ApiWastewaterRemovalOrderResponse>> wastewaterRemovalOrdersList(//
            @Valid @ParameterObject ApiWastewaterRevomalOrdersListParameters filterParameters, HttpServletRequest request) throws Exception {

        var data = ntisApi.getNtisDisposalOrders(this.getDBConnection(), this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_org_id(), filterParameters, request);

        Integer maxId = !data.isEmpty() ? data.get(data.size() - 1).getOrderId() : null;
        Integer minId = !data.isEmpty() ? data.get(0).getOrderId() : null;
        var links = getNavigationLinks(request, data.size(), maxId, minId, filterParameters);

        return okResponse(new ApiList<ApiWastewaterRemovalOrderResponse>(data, links));
    }

    @Operation(summary = "Gauti techninės priežiūros paslaugos užsakymų sąrašą.", description = "Atliekant paiešką privaloma nurodyti laikotarpį, už kurį pageidaujama gauti užsakymų sąrašą. Pasirinktinai galima nurodyti užsakymo būseną, gauti užsakymus konkrečiam nurodytam įrenginiui arba gauti konkretaus nurodyto užsakymo informaciją. Yra grąžinami tik tie paslaugos užsakymai, kuriuos naudotojas pagal turimas teises gali matyti NTIS portale.")
    @ApiResponse(responseCode = "200", description = "Successful response")
    @ApiResponse(responseCode = "400", description = "Bad request, validation failed", content = @Content(mediaType = "", examples = {}, schema = @Schema(implementation = ApiErrorMessage.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "", examples = {}))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "", examples = {}))
    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(mediaType = "", examples = {}))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "", examples = {}))
    @GetMapping(value = "/technical-support-orders-list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiList<ApiTechnicalSupportOrderResponse>> technicalSupportOrdersList(//
            @Valid @ParameterObject ApiTechnicalSupportOrdersListParameters filterParameters, HttpServletRequest request, UriComponentsBuilder uriBuilder)
            throws Exception {
        var data = ntisApi.getTechnicalSupportOrder(this.getDBConnection(), this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_org_id(), filterParameters, request);
        Integer maxId = !data.isEmpty() ? data.get(data.size() - 1).getOrderId() : null;
        Integer minId = !data.isEmpty() ? data.get(0).getOrderId() : null;
        var links = getNavigationLinks(request, data.size(), maxId, minId, filterParameters);

        return okResponse(new ApiList<ApiTechnicalSupportOrderResponse>(data, links));
    }

    @Operation(summary = "Užregistruoti įvykdytą techninės priežiūros paslaugos užsakymą.", description = "Užregistruoto techninės priežiūros paslaugos užsakymo būsena yra “Įvykdytas”. Deklaruojant užsakymą privaloma nurodyti nuotekų tvarkymo įrenginį, kuriam buvo užsakyta paslauga.")
    @ApiResponse(responseCode = "200", description = "Successful response")
    @ApiResponse(responseCode = "400", description = "Bad request, validation failed", content = @Content(mediaType = "", examples = {}, schema = @Schema(implementation = ApiErrorMessage.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "", examples = {}))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "", examples = {}))
    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(mediaType = "", examples = {}))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "", examples = {}))
    @PostMapping(value = "/declare-technical-support-order", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiDeclareTechOrderResponse> declareTechnicalSupportOrder(//
            @Valid @ParameterObject ApiTechnicalSupportOrderParameters parameters, HttpServletRequest request) throws Exception {
        ApiDeclareTechOrderResponse orderResult = ntisApi.declareTechnicalSupportOrder(this.getDBConnection(),
                this.requestContext.getUserSession().getSes_usr_id(), this.requestContext.getUserSession().getSes_org_id(), parameters, request);
        return okResponse(orderResult);
    }

    @Operation(summary = "Užregistruoti įvykdytą nuotekų (dumblo) išvežimo paslaugos užsakymą.", description = "Užregistruoto nuotekų (dumblo) išvežimo paslaugos užsakymo būsena yra “Įvykdytas”. Deklaruojant užsakymą privaloma nurodyti nuotekų tvarkymo įrenginį, kuriam buvo užsakyta paslauga. Nuotekas išvežusi transporto priemonė privalo būti užregistruota NTIS portale.")
    @ApiResponse(responseCode = "200", description = "Successful response")
    @ApiResponse(responseCode = "400", description = "Bad request, validation failed", content = @Content(mediaType = "", examples = {}, schema = @Schema(implementation = ApiErrorMessage.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "", examples = {}))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "", examples = {}))
    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(mediaType = "", examples = {}))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "", examples = {}))
    @PostMapping(value = "/declare-wastewater-removal-order", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiDeclareWastewaterRemovalOrderResponse> declareWastewaterRemovalOrder(//
            @Valid @ParameterObject ApiWastewaterRemovalOrderParameters parameters, HttpServletRequest request) throws Exception {
        ApiDeclareWastewaterRemovalOrderResponse orderResult = ntisApi.declareWastewaterRemovalOrder(this.getDBConnection(),
                this.requestContext.getUserSession().getSes_usr_id(), this.requestContext.getUserSession().getSes_org_id(), parameters, request);
        return okResponse(orderResult);
    }

    @Operation(summary = "Pateikti informaciją nuo kada nekilnojamojo turto objekto nuotekos tvarkomos centralizuotai.", description = "Vandentvarkos įmonės, naudodamos šį išteklių gali pateikti informaciją nuo kada nekilnojamojo turto objekto nuotekos tvarkos centralizuotai. Panaudoti šį išteklių gali tik API naudotojai, turintys teisę pateikti centralizuotai tvarkomų nekilnojamojo turto objektų duomenis įprastiniu būdu (per vartotojo sąsają).")
    @ApiResponse(responseCode = "200", description = "Successful response")
    @ApiResponse(responseCode = "400", description = "Bad request, validation failed", content = @Content(mediaType = "", examples = {}, schema = @Schema(implementation = ApiErrorMessage.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "", examples = {}))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "", examples = {}))
    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(mediaType = "", examples = {}))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "", examples = {}))
    @PostMapping(value = "/declare-centralized-wastewater")
    public ResponseEntity<ApiDeclareCentralizedWastewaterResponse> declareCentralizedWastewater(//
            @Valid @ParameterObject ApiCentralizedWastewaterPostParameters filterParameters, HttpServletRequest request, UriComponentsBuilder uriBuilder)
            throws Exception {
        var data = ntisApi.declareCentralizedWastewater(this.getDBConnection(), this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_org_id(), filterParameters, request);
        return okResponse(data);
    }

    private ApiLinks getNavigationLinks(HttpServletRequest request, Integer size, Integer maxId, Integer minId, ApiPagingParameters filterParameters) {
        var links = new ApiLinks();
        UriComponentsBuilder uriComponentss = UriComponentsBuilder.fromUriString(hostUrl + "/" + request.getRequestURI() + "?" + request.getQueryString());
        links.setFirst(uriComponentss.replaceQueryParam("pageAfter").replaceQueryParam("pageBefore").build().toUriString());
        if (size != 0) {
            if (filterParameters.getPageSize() <= size || filterParameters.getPageBefore() != null) {
                var nextId = (filterParameters.getPageBefore() == null && filterParameters.getPageAfter() == null) || filterParameters.getPageAfter() != null
                        ? maxId
                        : minId;
                links.setNext(uriComponentss.replaceQueryParam("pageBefore").replaceQueryParam("pageAfter", nextId).build().toUriString());
            }
            if ((filterParameters.getPageBefore() != null || filterParameters.getPageAfter() != null)) {
                var prevId = filterParameters.getPageAfter() == null ? maxId : minId;
                links.setPrev(uriComponentss.replaceQueryParam("pageAfter").replaceQueryParam("pageBefore", prevId).build().toUriString());
            }
        } else {
            if (filterParameters.getPageAfter() != null)
                links.setPrev(
                        uriComponentss.replaceQueryParam("pageAfter").replaceQueryParam("pageBefore", filterParameters.getPageAfter()).build().toUriString());
            else if (filterParameters.getPageBefore() != null) {
                links.setNext(
                        uriComponentss.replaceQueryParam("pageBefore").replaceQueryParam("pageAfter", filterParameters.getPageAfter()).build().toUriString());
            }
        }
        return links;

    }

}
