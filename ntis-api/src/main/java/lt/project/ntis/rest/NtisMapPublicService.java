package lt.project.ntis.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.itreegroup.s2.server.rest.S2RestAuthService;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendWebSessionInfo;
import lt.project.ntis.logic.forms.NtisMap;
import lt.project.ntis.logic.forms.model.NtisAggloMapTableItem;
import lt.project.ntis.logic.forms.model.NtisBuildingsMapTableItem;
import lt.project.ntis.logic.forms.model.NtisCountyMunicipality;
import lt.project.ntis.logic.forms.model.NtisMapTableResult;
import lt.project.ntis.models.NtisMapBuildPointDetails;
import lt.project.ntis.models.NtisMapCentDetails;
import lt.project.ntis.models.NtisMapClickedPoint;
import lt.project.ntis.models.NtisMapDisposalDetails;
import lt.project.ntis.models.NtisMapFacilityDetails;
import lt.project.ntis.models.NtisMapResearchDetails;

/**
 * Klasė skirta gauti/siųsti duomenis reikalingus žemėlapiui (neprisijungusiam vartotojui)
 * 
 */

@RestController
@RequestMapping("/ntis-map-pub")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class NtisMapPublicService extends S2RestAuthService<SprBackendWebSessionInfo, SprBackendUserSession> {

    @Autowired
    NtisMap ntisMap;

    @Override
    protected SprBackendUserSession createNewSession() {
        return new SprBackendUserSession();
    }

    @GetMapping(value = "/get-counties-municipalities")
    public ResponseEntity<List<NtisCountyMunicipality>> getCountiesWithMunicipalities() throws Exception {
        return okResponse(ntisMap.getCountiesWithMunicipalities(this.getDBConnection()));
    }

    @PostMapping(value = "/get-agglo-obj-info", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisAggloMapTableItem> getAggloObjectInfo(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(ntisMap.getAggloObjectInfo(this.getDBConnection(), recordIdentifier));
    }

    @PostMapping(value = "/get-agglo-table", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisMapTableResult<NtisAggloMapTableItem>> getAggloTable(
            @RequestBody(required = false) ArrayList<AdvancedSearchParameterStatement> filters) throws Exception {
        return okResponse(ntisMap.getAggloTable(this.getDBConnection(), filters));
    }

    @PostMapping(value = "/get-buildings-table", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisMapTableResult<NtisBuildingsMapTableItem>> getBuildingsTable(
            @RequestBody(required = false) ArrayList<AdvancedSearchParameterStatement> filters) throws Exception {
        return okResponse(ntisMap.getBuildingsTable(this.getDBConnection(), filters));
    }

    @GetMapping(value = "/get-building-details/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisMapBuildPointDetails> getBuildingDetails(@PathVariable String id) throws Exception {
        return okResponse(this.ntisMap.getBuildingDetails(this.getDBConnection(), Utils.getDouble(id).intValue()));
    }

    @PostMapping(value = "/get-facilities-table", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisMapTableResult<NtisMapFacilityDetails>> getFacilitiesTable(
            @RequestBody(required = false) ArrayList<AdvancedSearchParameterStatement> filters) throws Exception {
        return okResponse(ntisMap.getFacilitiesTable(this.getDBConnection(), filters));
    }

    @GetMapping(value = "/get-facility-details/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisMapFacilityDetails> getFacilityDetails(@PathVariable String id) throws Exception {
        return okResponse(this.ntisMap.getFacilityDetails(this.getDBConnection(), Utils.getDouble(id).intValue()));
    }

    @PostMapping(value = "/get-discharges-table", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisMapTableResult<NtisMapFacilityDetails>> getDischargesTable(
            @RequestBody(required = false) ArrayList<AdvancedSearchParameterStatement> filters) throws Exception {
        return okResponse(ntisMap.getDischargesTable(this.getDBConnection(), filters));
    }

    @GetMapping(value = "/get-discharge-details/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisMapFacilityDetails> getDischargeDetails(@PathVariable String id) throws Exception {
        return okResponse(this.ntisMap.getDischargeDetails(this.getDBConnection(), Utils.getDouble(id).intValue()));
    }

    @PostMapping(value = "/get-cent-table", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisMapTableResult<NtisMapCentDetails>> getCentTable(
            @RequestBody(required = false) ArrayList<AdvancedSearchParameterStatement> filters) throws Exception {
        return okResponse(ntisMap.getCentTable(this.getDBConnection(), filters));
    }

    @GetMapping(value = "/get-cent-details/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisMapCentDetails> getCentDetails(@PathVariable String id) throws Exception {
        return okResponse(this.ntisMap.getCentDetails(this.getDBConnection(), Utils.getDouble(id).intValue()));
    }

    @PostMapping(value = "/get-research-table", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisMapTableResult<NtisMapResearchDetails>> getResearchTable(
            @RequestBody(required = false) ArrayList<AdvancedSearchParameterStatement> filters) throws Exception {
        return okResponse(ntisMap.getResearchTable(this.getDBConnection(), filters, this.requestContext.getUserSession().getSes_language()));
    }

    @GetMapping(value = "/get-research-details/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisMapResearchDetails> getResearchDetails(@PathVariable String id) throws Exception {
        return okResponse(this.ntisMap.getResearchDetails(this.getDBConnection(), Utils.getDouble(id).intValue(),
                this.requestContext.getUserSession().getSes_language()));
    }

    @PostMapping(value = "/get-disposal-table", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisMapTableResult<NtisMapDisposalDetails>> getDisposalTable(
            @RequestBody(required = false) ArrayList<AdvancedSearchParameterStatement> filters) throws Exception {
        return okResponse(ntisMap.getDisposalTable(this.getDBConnection(), filters, this.requestContext.getUserSession().getSes_language()));
    }

    @GetMapping(value = "/get-disposal-details/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisMapDisposalDetails> getDisposalDetails(@PathVariable String id) throws Exception {
        return okResponse(this.ntisMap.getDisposalDetails(this.getDBConnection(), Utils.getDouble(id).intValue(),
                this.requestContext.getUserSession().getSes_language()));
    }

    @PostMapping(value = "/get-clicked-points-names", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NtisMapClickedPoint>> getNamesOfClickedPoints(@RequestBody List<NtisMapClickedPoint> items) throws Exception {
        return okResponse(this.ntisMap.getNamesOfClickedPoints(this.getDBConnection(), items));
    }

}
