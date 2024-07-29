package lt.project.ntis.logic.forms;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.Data2ObjectProcessor;
import eu.itreegroup.spark.dao.query.MethodCaller;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementDataGetter;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementDoubleGetter;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementStringGetter;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.enums.NtisTypeWastewaterTreatment;
import lt.project.ntis.logic.forms.model.NtisAggloMapTableGeom;
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
import lt.project.ntis.service.gen.NtisAgglomerationsDBServiceGen;

/**
 * Klasė skirta formų "N4130" ir "G5090" (Žemėlapio) biznio logikai apibrėžti
 */
@Component
public class NtisMap extends FormBase {

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    NtisAgglomerationsDBServiceGen ntisAglomeracijosDBService;

    static final String ACTION_READ_FACI_FULL_DETAILS = "READ_FACI_FULL_DETAILS";

    static final String ACTION_READ_BUILD_FULL_DETAILS = "READ_BUILD_FULL_DETAILS";

    static final String ACTION_READ_BUILD_OWNER_INFO = "READ_BUILD_OWNER_INFO";

    static final String ACTION_READ_FACI_TABLE = "READ_FACI_TABLE";

    static final String ACTION_READ_BUILD_TABLE = "READ_BUILD_TABLE";

    static final String ACTION_READ_CENT_TABLE = "READ_CENT_TABLE";

    static final String ACTION_READ_DISC_TABLE = "READ_DISC_TABLE";

    static final String ACTION_READ_DISPOSAL_LAYER = "READ_DISPOSAL_LAYER";

    static final String ACTION_READ_RESEARCH_LAYER = "READ_RESEARCH_LAYER";

    static final String ACTION_READ_AGGLO_TABLE = "READ_AGGLO_TABLE";

    @Value("${app.tiles.server.url}")
    private String tilesServerUrl;

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public String getFormName() {
        return "NTIS_MAP";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "NTIS žemėlapis", "NTIS žemėlapis");
        addFormActions(NtisMap.ACTION_READ);
    }

    public List<NtisCountyMunicipality> getCountiesWithMunicipalities(Connection conn) throws Exception {
        StatementAndParams statementAndParams = new StatementAndParams("" //
                + "SELECT counties.rfc_code AS code, " //
                + "       counties.rfc_meaning AS name, " //
                + "       counties.rfc_description AS description, " //
                + "       Array_to_json(ARRAY_AGG(json_build_object('code', municipalities.rfc_code, " //
                + "                                                 'name', municipalities.rfc_meaning, " //
                + "                                                 'description', municipalities.rfc_description) order by municipalities.rfc_order)) AS children_json  " //
                + "  FROM spark.spr_ref_codes counties,  " //
                + "       spark.spr_ref_codes municipalities " //
                + "  WHERE counties.rfc_domain = 'NTIS_COUNTIES' " //
                + "    AND current_date between counties.rfc_date_from and COALESCE(counties.rfc_date_to, current_date) " //
                + "    AND municipalities.rfc_domain = 'NTIS_MUNICIPALITIES' " //
                + "    AND current_date between municipalities.rfc_date_from and COALESCE(municipalities.rfc_date_to, current_date) " //
                + "    AND counties.rfc_code = municipalities.rfc_ref_code_1 " //
                + "  GROUP BY counties.rfc_code, " //
                + "           counties.rfc_meaning, " //
                + "           counties.rfc_description, " //
                + "           counties.rfc_order " //
                + "  ORDER BY counties.rfc_order");
        ArrayList<MethodCaller> methods = new ArrayList<MethodCaller>();
        methods.add(new MethodCaller("setChildrenJson", new StatementDataGetter[] { new StatementStringGetter("children_json") }));
        Data2ObjectProcessor<NtisCountyMunicipality> dataProcessor = new Data2ObjectProcessor<NtisCountyMunicipality>(NtisCountyMunicipality.class, methods);
        return baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams, dataProcessor);
    }

    public NtisAggloMapTableItem getAggloObjectInfo(Connection conn, RecordIdentifier recordIdentifier) throws Exception {
        StatementAndParams statementAndParams = new StatementAndParams("""
                SELECT a_ter_id AS id,
                       pav AS name,
                       sav_kodas AS municipalityCode,
                       ge AS populationEquivalent,
                       gyv_tankis AS populationDensity,
                       a_plotas AS area,
                       a_dok_pav AS docName,
                       a_dok_nr AS docNo,
                       TO_CHAR(a_dok_data, ?) AS docDate
                  FROM tiles.agglomerations
                 WHERE gid = ?::int """);
        statementAndParams.addSelectParam(dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB));
        statementAndParams.addSelectParam(recordIdentifier.getIdAsDouble());
        return baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams, NtisAggloMapTableItem.class).get(0);
    }

    public NtisMapTableResult<NtisAggloMapTableItem> getAggloTable(Connection conn, ArrayList<AdvancedSearchParameterStatement> filters) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisMap.ACTION_READ_AGGLO_TABLE);
        NtisMapTableResult<NtisAggloMapTableItem> result = new NtisMapTableResult<NtisAggloMapTableItem>();
        StatementAndParams statementAndParams = new StatementAndParams("""
                SELECT a_ter_id AS id,
                       pav AS name,
                       sav_kodas AS municipalityCode,
                       ge AS populationEquivalent,
                       gyv_tankis AS populationDensity,
                       a_plotas AS area,
                       a_dok_pav AS docName,
                       a_dok_nr AS docNo,
                       TO_CHAR(a_dok_data, ?) AS docDate,
                       ST_XMin(geom) AS minX,
                       ST_YMin(geom) AS minY,
                       ST_Xmax(geom) AS maxX,
                       ST_Ymax(geom) AS maxY
                  FROM tiles.agglomerations """);
        statementAndParams.addSelectParam(dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB));
        HashMap<String, Integer> fieldTypes = new HashMap<String, Integer>();
        fieldTypes.put("a_ter_id", StatementAndParams.PARAM_DOUBLE);
        fieldTypes.put("pav", StatementAndParams.PARAM_STRING);
        fieldTypes.put("sav_kodas", StatementAndParams.PARAM_DOUBLE);
        fieldTypes.put("ge", StatementAndParams.PARAM_DOUBLE);

        if (filters != null) {
            for (AdvancedSearchParameterStatement filter : filters) {
                Integer fieldType = fieldTypes.get(filter.getParamName());
                if (fieldType != null) {
                    statementAndParams.addParam4WherePart(filter.getParamName(), fieldType, filter);
                }
            }
        }

        statementAndParams.setStatementOrderPart("ORDER BY a_ter_id ASC LIMIT 1000");
        Data2ObjectProcessor<NtisAggloMapTableItem> dataProcessor = new Data2ObjectProcessor<NtisAggloMapTableItem>(NtisAggloMapTableItem.class);
        result.setItems(new ArrayList<NtisAggloMapTableItem>(baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams, dataProcessor)));
        result.setFilteredItems(result.getItems().size());

        StatementAndParams totalCountStatement = new StatementAndParams("SELECT COUNT(1) AS total_count FROM tiles.agglomerations");
        List<HashMap<String, String>> totalCountResult = baseControllerJDBC.selectQueryAsDataArrayList(conn, totalCountStatement);
        result.setTotalItems(Utils.getDouble(totalCountResult.get(0).get("total_count")).intValue());
        return result;
    }

    public List<NtisAggloMapTableGeom> getAggloTableGeoms(Connection conn, ArrayList<AdvancedSearchParameterStatement> filters) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisMap.ACTION_READ_AGGLO_TABLE);
        StatementAndParams statementAndParams = new StatementAndParams("""
                SELECT a_ter_id AS id,
                       ST_AsGeoJSON(geom) AS geom
                  FROM tiles.agglomerations """);
        statementAndParams.addSelectParam(dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB));
        HashMap<String, Integer> fieldTypes = new HashMap<String, Integer>();
        fieldTypes.put("a_ter_id", StatementAndParams.PARAM_DOUBLE);
        fieldTypes.put("pav", StatementAndParams.PARAM_STRING);
        fieldTypes.put("sav_kodas", StatementAndParams.PARAM_DOUBLE);
        fieldTypes.put("ge", StatementAndParams.PARAM_DOUBLE);
        if (filters != null) {
            for (AdvancedSearchParameterStatement filter : filters) {
                Integer fieldType = fieldTypes.get(filter.getParamName());
                if (fieldType != null) {
                    statementAndParams.addParam4WherePart(filter.getParamName(), fieldType, filter);
                }
            }
        }
        statementAndParams.setStatementOrderPart("ORDER BY a_ter_id ASC LIMIT 1000");
        return this.baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams, NtisAggloMapTableGeom.class);
    }

    public NtisMapTableResult<NtisBuildingsMapTableItem> getBuildingsTable(Connection conn, ArrayList<AdvancedSearchParameterStatement> filters)
            throws Exception {
        this.checkIsFormActionAssigned(conn, NtisMap.ACTION_READ_BUILD_TABLE);
        NtisMapTableResult<NtisBuildingsMapTableItem> result = new NtisMapTableResult<NtisBuildingsMapTableItem>();
        String statement = """
                SELECT ad.ad_address AS address,
                       ba.ba_id,
                       ST_XMin(ST_Transform(so_geom, 3857)) AS minX,
                       ST_YMin(ST_Transform(so_geom, 3857)) AS minY,
                       ST_Xmax(ST_Transform(so_geom, 3857)) AS maxX,
                       ST_Ymax(ST_Transform(so_geom, 3857)) AS maxY """;
        if (this.isFormActionAssigned(conn, NtisMap.ACTION_READ_BUILD_FULL_DETAILS)) {
            statement = statement + """
                    ,
                    bn.bn_obj_inv_code AS ntrNumber,
                    bn.bn_status_desc AS status,
                    bn.bn_pask_name AS purpose,
                    'TODO: Statinio informacija apie nuotekų tvarkymą (apibendrinta NTISe)' AS info """;
        }
        if (this.isFormActionAssigned(conn, NtisMap.ACTION_READ_BUILD_OWNER_INFO)) {
            statement = statement + """
                    ,
                    bn.bn_obj_inv_parent_code AS belongsToNtrNumber """;
        }
        statement = statement + " " + """
                FROM ntis.ntis_served_objects so
                LEFT JOIN ntis.ntis_adr_addresses ad
                       ON ad.ad_id = so.so_ad_id
                LEFT JOIN ntis.ntis_building_ntrs bn
                       ON bn.bn_id = so.so_bn_id
                LEFT JOIN ntis.ntis_wastewater_treatment_faci wtf
                       ON wtf.wtf_id = so.so_wtf_id
                LEFT JOIN ntis.ntis_building_agreements ba
                       ON ba.ba_bn_id = bn.bn_id """;

        StatementAndParams statementAndParams = new StatementAndParams(statement);
        HashMap<String, Integer> fieldTypes = new HashMap<>();
        fieldTypes.put("ad.ad_address", StatementAndParams.PARAM_STRING);
        fieldTypes.put("bn.bn_obj_inv_code", StatementAndParams.PARAM_STRING);

        if (filters != null) {
            for (AdvancedSearchParameterStatement filter : filters) {
                Integer fieldType = fieldTypes.get(filter.getParamName());
                if (fieldType != null) {
                    statementAndParams.addParam4WherePart(filter.getParamName(), fieldType, filter);
                } else if ("treatment".equals(filter.getParamName())) {
                    statementAndParams.addCondition4WherePart(
                            String.format("ba.ba_id IS %s NULL",
                                    NtisTypeWastewaterTreatment.CENTRALIZED.getCode().equals(filter.getParamValue().getValue()) ? "NOT" : ""),
                            StatementAndParams.AND_OPERAND);
                }
            }
        }
        statementAndParams.setStatementOrderPart("ORDER BY so.so_id ASC LIMIT 1000");
        ArrayList<MethodCaller> methods = new ArrayList<>();
        methods.add(new MethodCaller("setIsCentralizedFromBaId", new StatementDataGetter[] { new StatementDoubleGetter("ba_id") }));
        Data2ObjectProcessor<NtisBuildingsMapTableItem> dataProcessor = new Data2ObjectProcessor<>(NtisBuildingsMapTableItem.class, methods);
        result.setItems(new ArrayList<>(baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams, dataProcessor)));
        result.setFilteredItems(result.getItems().size());

        StatementAndParams totalCountStatement = new StatementAndParams("SELECT COUNT(1) AS total_count FROM ntis.ntis_served_objects");
        List<HashMap<String, String>> totalCountResult = baseControllerJDBC.selectQueryAsDataArrayList(conn, totalCountStatement);
        result.setTotalItems(Utils.getDouble(totalCountResult.get(0).get("total_count")).intValue());
        return result;
    }

    public NtisMapBuildPointDetails getBuildingDetails(Connection conn, Integer poId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        String statement = """
                SELECT po.po_id AS poId,
                       ARRAY_TO_JSON(ARRAY[
                           ST_X(ST_Transform(po.geom, 3857)),
                           ST_Y(ST_Transform(po.geom, 3857))])::text AS coordinates_json,
                       JSON_AGG(DISTINCT JSONB_BUILD_OBJECT(
                        'wtfId', wtf.wtf_id,
                        'facilityCoordinates',
                        ARRAY[
                            ST_X(ST_Transform(wtf.wtf_facility_geom, 3857)),
                            ST_Y(ST_Transform(wtf.wtf_facility_geom, 3857))
                         ],
                         'dischargeCoordinates',
                        (CASE
                           WHEN
                             wtf.wtf_discharge_geom IS NOT NULL
                           THEN
                             ARRAY[
                               ST_X(ST_Transform(wtf.wtf_discharge_geom, 3857)),
                               ST_Y(ST_Transform(wtf.wtf_discharge_geom, 3857))
                             ]
                           ELSE
                             NULL
                          END),
                         'typeCode', wtf.wtf_type,
                         'stateCode', wtf.wtf_state
                       )) AS facilities_json,
                       ARRAY_TO_JSON(ARRAY_REMOVE(ARRAY_AGG(ad.ad_address
                                                            ORDER BY bn.bn_street,
                                                            REGEXP_REPLACE(bn.bn_house_nr, '\\D','','g')::int,
                                                            REGEXP_REPLACE(bn.bn_flat_nr, '\\D','','g')::int,
                                                            ad.ad_address
                                                           ), null)) AS addresses_json
                  FROM tiles.point_buildings po
                  LEFT JOIN ntis.ntis_building_ntrs bn
                         ON bn.bn_id = po.bn_id
                  LEFT JOIN ntis.ntis_served_objects so
                         ON so.so_bn_id = bn.bn_id
                  LEFT JOIN ntis.ntis_wastewater_treatment_faci wtf
                         ON wtf.wtf_id = so.so_wtf_id
                  LEFT JOIN ntis.ntis_adr_addresses ad
                         ON ad.ad_id = bn.bn_ad_id
                  LEFT JOIN ntis.ntis_building_agreements ba
                         ON ba.ba_bn_id = bn.bn_id
                WHERE po_id = ?::int
                GROUP BY po.po_id, po.geom
                """;
        StatementAndParams statementAndParams = new StatementAndParams(statement);
        statementAndParams.addSelectParam(poId.doubleValue());
        ArrayList<MethodCaller> methods = new ArrayList<>();
        methods.add(new MethodCaller("setCoordinatesFromJsonString", new StatementDataGetter[] { new StatementStringGetter("coordinates_json") }));
        methods.add(new MethodCaller("setFacilitiesFromJsonString", new StatementDataGetter[] { new StatementStringGetter("facilities_json") }));
        methods.add(new MethodCaller("setAddressesFromJsonString", new StatementDataGetter[] { new StatementStringGetter("addresses_json") }));
        Data2ObjectProcessor<NtisMapBuildPointDetails> dataProcessor = new Data2ObjectProcessor<>(NtisMapBuildPointDetails.class, methods);
        return this.baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams, dataProcessor).get(0);
    }

    public NtisMapFacilityDetails getFacilityDetails(Connection conn, Integer wtfId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        boolean canReadFullDetails = this.isFormActionAssigned(conn, NtisMap.ACTION_READ_FACI_FULL_DETAILS);
        String statement = """
                WITH wtf_objects AS (
                SELECT wtf.wtf_id,
                       ARRAY_TO_JSON(
                           ARRAY_AGG(DISTINCT
                               JSONB_BUILD_OBJECT(
                                   'id', po.po_id,
                                   'coords', ARRAY[ST_X(ST_Transform(po.geom, 3857)), ST_Y(ST_Transform(po.geom, 3857))]
                               )
                           )) AS served_objects
                  FROM ntis.ntis_wastewater_treatment_faci wtf
                  LEFT JOIN ntis.ntis_served_objects so
                         ON so.so_wtf_id = wtf.wtf_id
                  LEFT JOIN tiles.point_buildings po
                         ON po.bn_id = so.so_bn_id
                 WHERE wtf_id = ?::int
                 GROUP BY wtf.wtf_id
                )
                SELECT wtf.wtf_id AS wtfId,
                       COALESCE(state_rfc.rfc_meaning, wtf.wtf_state) AS state,
                       wtf.wtf_state AS stateCode,
                       ad.ad_address AS address,
                       COALESCE(type_rfc.rfc_meaning, wtf.wtf_type) AS type,
                       wtf.wtf_type AS typeCode,
                """;
        if (canReadFullDetails) {
            statement = statement + " " + """
                       objlist.addresses_json AS served_addresses_json,
                       wtf.wtf_distance::text AS distance,
                       TO_CHAR(wtf.wtf_installation_date, ?) AS installationDate,
                       TO_CHAR(wtf.wtf_checkout_date, ?) AS checkoutDate,
                       wtf.wtf_capacity AS capacity,
                       wtf.wtf_technical_passport_id AS technicalPassport,
                       COALESCE(manuf_rfc.rfc_meaning, wtf.wtf_manufacturer) AS manufacturer,
                       COALESCE(model_rfc.rfc_meaning, wtf.wtf_model) AS model,
                       COALESCE(wtf.wtf_manufacturer_description, '-') AS manufacturerDescription,
                       COALESCE(dis_type_rfc.rfc_meaning, wtf.wtf_discharge_type) AS dischargeType,
                       ST_X(ST_Transform(wtf.wtf_discharge_geom, 3346))
                         || ', ' || ST_Y(ST_Transform(wtf.wtf_discharge_geom, 3346)) AS dischargeCoordinatesText,
                    """;
        }
        statement = statement + """
                       ARRAY_TO_JSON(
                           ARRAY[
                               ST_X(ST_Transform(wtf.wtf_facility_geom, 3857)),
                               ST_Y(ST_Transform(wtf.wtf_facility_geom, 3857))])::text AS facility_coordinates,
                       ARRAY_TO_JSON(
                           ARRAY[
                               ST_X(ST_Transform(wtf.wtf_discharge_geom, 3857)),
                               ST_Y(ST_Transform(wtf.wtf_discharge_geom, 3857))])::text AS discharge_coordinates,
                       (CASE
                            WHEN objs.served_objects->0->>'id' IS NULL
                            THEN '[]' ELSE objs.served_objects
                        END) AS served_objects
                  FROM ntis.ntis_wastewater_treatment_faci wtf
                 INNER JOIN wtf_objects objs
                         ON objs.wtf_id = wtf.wtf_id
                  LEFT JOIN spark.spr_ref_codes state_rfc
                         ON state_rfc.rfc_code = wtf.wtf_state
                        AND state_rfc.rfc_domain = 'NTIS_INTS_STATUS'
                  LEFT JOIN spark.spr_ref_codes type_rfc
                         ON type_rfc.rfc_code = wtf.wtf_type
                        AND type_rfc.rfc_domain = 'NTIS_WTF_TYPE'
                  LEFT JOIN spark.spr_ref_codes dis_type_rfc
                         ON dis_type_rfc.rfc_code = wtf.wtf_discharge_type
                        AND dis_type_rfc.rfc_domain = 'DISCHARGE_WASTEWATER_TYPE'
                  LEFT JOIN spark.spr_ref_codes manuf_rfc
                         ON manuf_rfc.rfc_code = wtf.wtf_manufacturer
                        AND manuf_rfc.rfc_domain = 'NTIS_FACIL_MANUFA'
                  LEFT JOIN spark.spr_ref_codes model_rfc
                         ON model_rfc.rfc_code = wtf.wtf_model
                        AND model_rfc.rfc_domain = 'NTIS_FACIL_MODEL'
                  LEFT JOIN ntis.ntis_adr_addresses ad
                         ON ad.ad_id = wtf.wtf_ad_id
                """;
        if (canReadFullDetails) {
            statement = statement + " " + """
                    LEFT JOIN (SELECT so.so_wtf_id AS wtf_id,
                                      ARRAY_TO_JSON(
                                          ARRAY_REMOVE(
                                              ARRAY_AGG(ad.ad_address
                                                        ORDER BY bn.bn_street,
                                                                 REGEXP_REPLACE(bn.bn_house_nr, '\\D','','g')::int,
                                                                 REGEXP_REPLACE(bn.bn_flat_nr, '\\D','','g')::int,
                                                                 ad.ad_address
                                                       ), null)) AS addresses_json
                                 FROM ntis.ntis_served_objects so
                                 LEFT JOIN ntis.ntis_building_ntrs bn
                                        ON bn.bn_id = so.so_bn_id
                                 LEFT JOIN ntis.ntis_adr_addresses ad
                                        ON ad.ad_id = bn_ad_id
                                WHERE so.so_wtf_id = ?::int
                                GROUP BY so.so_wtf_id) objlist
                           ON objlist.wtf_id = wtf.wtf_id
                      """;
        }

        StatementAndParams statementAndParams = new StatementAndParams(statement);
        statementAndParams.addSelectParam(wtfId.doubleValue());
        if (canReadFullDetails) {
            statementAndParams.addSelectParam(this.dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB));
            statementAndParams.addSelectParam(this.dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB));
            statementAndParams.addSelectParam(wtfId.doubleValue());
        }

        ArrayList<MethodCaller> methods = new ArrayList<>();
        methods.add(new MethodCaller("setFacilityCoordinatesFromJsonString", new StatementDataGetter[] { new StatementStringGetter("facility_coordinates") }));
        methods.add(
                new MethodCaller("setDischargeCoordinatesFromJsonString", new StatementDataGetter[] { new StatementStringGetter("discharge_coordinates") }));
        methods.add(new MethodCaller("setServedObjectsFromJsonString", new StatementDataGetter[] { new StatementStringGetter("served_objects") }));
        if (canReadFullDetails) {
            methods.add(new MethodCaller("setServedObjectAddressesFromJsonString",
                    new StatementDataGetter[] { new StatementStringGetter("served_addresses_json") }));
        }
        Data2ObjectProcessor<NtisMapFacilityDetails> dataProcessor = new Data2ObjectProcessor<>(NtisMapFacilityDetails.class, methods);
        return this.baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams, dataProcessor).get(0);
    }

    public NtisMapTableResult<NtisMapFacilityDetails> getFacilitiesTable(Connection conn, ArrayList<AdvancedSearchParameterStatement> filters)
            throws Exception {
        this.checkIsFormActionAssigned(conn, NtisMap.ACTION_READ_FACI_TABLE);
        NtisMapTableResult<NtisMapFacilityDetails> result = new NtisMapTableResult<>();
        boolean canReadFullDetails = this.isFormActionAssigned(conn, NtisMap.ACTION_READ_FACI_FULL_DETAILS);
        String statement = """
                SELECT wtf.wtf_id AS wtfId,
                       COALESCE(state_rfc.rfc_meaning, wtf.wtf_state) AS state,
                       wtf.wtf_state AS stateCode,
                       ad.ad_address AS address,
                       COALESCE(type_rfc.rfc_meaning, wtf.wtf_type) AS type,
                       wtf.wtf_type AS typeCode,
                       ST_X(ST_Transform(wtf.wtf_facility_geom, 3857)) AS x,
                       ST_Y(ST_Transform(wtf.wtf_facility_geom, 3857)) AS y """;
        if (canReadFullDetails) {
            statement = statement + """
                    ,
                    'Aptarnaujamo objekto adresas???' AS servedObjectAddress,
                    wtf.wtf_distance::text AS distance,
                    TO_CHAR(wtf.wtf_installation_date, ?) AS installationDate,
                    TO_CHAR(wtf.wtf_checkout_date, ?) AS checkoutDate,
                    wtf.wtf_capacity AS capacity,
                    wtf.wtf_technical_passport_id AS technicalPassport,
                    COALESCE(manuf_rfc.rfc_meaning, wtf.wtf_manufacturer) AS manufacturer,
                    COALESCE(model_rfc.rfc_meaning, wtf.wtf_model) AS model,
                    wtf.wtf_manufacturer_description AS manufacturerDescription,
                    COALESCE(dis_type_rfc.rfc_meaning, wtf.wtf_discharge_type) AS dischargeType,
                    ST_X(ST_Transform(wtf.wtf_discharge_geom, 3346))
                      || ', ' || ST_Y(ST_Transform(wtf.wtf_discharge_geom, 3346)) AS dischargeCoordinatesText """;
        }
        statement = statement + " " + """
                FROM ntis.ntis_wastewater_treatment_faci wtf
                LEFT JOIN spark.spr_ref_codes state_rfc
                       ON state_rfc.rfc_code = wtf.wtf_state
                      AND state_rfc.rfc_domain = 'NTIS_INTS_STATUS'
                LEFT JOIN spark.spr_ref_codes type_rfc
                       ON type_rfc.rfc_code = wtf.wtf_type
                      AND type_rfc.rfc_domain = 'NTIS_WTF_TYPE'
                LEFT JOIN spark.spr_ref_codes dis_type_rfc
                       ON dis_type_rfc.rfc_code = wtf.wtf_discharge_type
                      AND dis_type_rfc.rfc_domain = 'DISCHARGE_WASTEWATER_TYPE'
                LEFT JOIN spark.spr_ref_codes manuf_rfc
                       ON manuf_rfc.rfc_code = wtf.wtf_manufacturer
                      AND manuf_rfc.rfc_domain = 'NTIS_FACIL_MANUFA'
                LEFT JOIN spark.spr_ref_codes model_rfc
                       ON model_rfc.rfc_code = wtf.wtf_model
                      AND model_rfc.rfc_domain = 'NTIS_FACIL_MODEL'
                LEFT JOIN ntis.ntis_adr_addresses ad
                       ON ad.ad_id = wtf.wtf_ad_id """;

        StatementAndParams statementAndParams = new StatementAndParams(statement);
        if (canReadFullDetails) {
            statementAndParams.addSelectParam(this.dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB));
            statementAndParams.addSelectParam(this.dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB));
        }

        HashMap<String, String> fieldNames = new HashMap<>();
        fieldNames.put("wtf_type", "wtf.wtf_type");
        fieldNames.put("wtf_state", "wtf.wtf_state");

        HashMap<String, Integer> fieldTypes = new HashMap<>();
        fieldTypes.put("ad.ad_address", StatementAndParams.PARAM_STRING);
        fieldTypes.put("wtf_type", StatementAndParams.PARAM_STRING);
        fieldTypes.put("wtf_state", StatementAndParams.PARAM_STRING);

        if (filters != null) {
            for (AdvancedSearchParameterStatement filter : filters) {
                String fieldName = fieldNames.get(filter.getParamName());
                Integer fieldType = fieldTypes.get(filter.getParamName());
                if (fieldType != null) {
                    statementAndParams.addParam4WherePart(fieldName != null ? fieldName : filter.getParamName(), fieldType, filter);
                }
            }
        }

        statementAndParams.setStatementOrderPart("ORDER BY wtf.wtf_id ASC LIMIT 1000");
        result.setItems(new ArrayList<>(this.baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams, NtisMapFacilityDetails.class)));
        result.setFilteredItems(result.getItems().size());

        StatementAndParams totalCountStatement = new StatementAndParams("SELECT COUNT(1) AS total_count FROM ntis.ntis_wastewater_treatment_faci");
        List<HashMap<String, String>> totalCountResult = baseControllerJDBC.selectQueryAsDataArrayList(conn, totalCountStatement);
        result.setTotalItems(Utils.getDouble(totalCountResult.get(0).get("total_count")).intValue());
        return result;
    }

    public NtisMapTableResult<NtisMapFacilityDetails> getDischargesTable(Connection conn, ArrayList<AdvancedSearchParameterStatement> filters)
            throws Exception {
        this.checkIsFormActionAssigned(conn, NtisMap.ACTION_READ_DISC_TABLE);
        NtisMapTableResult<NtisMapFacilityDetails> result = new NtisMapTableResult<>();

        StatementAndParams statementAndParams = new StatementAndParams("""
                 SELECT wtf.wtf_id AS wtfId,
                       ad.ad_address AS address,
                       COALESCE(dis_type_rfc.rfc_meaning, wtf.wtf_discharge_type) AS dischargeType,
                       ST_X(ST_Transform(wtf.wtf_discharge_geom, 3857)) AS x,
                       ST_Y(ST_Transform(wtf.wtf_discharge_geom, 3857)) AS y
                  FROM ntis.ntis_wastewater_treatment_faci wtf
                  LEFT JOIN spark.spr_ref_codes dis_type_rfc
                         ON dis_type_rfc.rfc_code = wtf.wtf_discharge_type
                        AND dis_type_rfc.rfc_domain = 'DISCHARGE_WASTEWATER_TYPE'
                  LEFT JOIN ntis.ntis_adr_addresses ad
                         ON ad.ad_id = wtf.wtf_ad_id
                 WHERE wtf.wtf_discharge_geom IS NOT NULL
                """);
        statementAndParams.setWhereExists(true);

        HashMap<String, Integer> fieldTypes = new HashMap<>();
        fieldTypes.put("wtf_discharge_type", StatementAndParams.PARAM_STRING);

        if (filters != null) {
            for (AdvancedSearchParameterStatement filter : filters) {
                Integer fieldType = fieldTypes.get(filter.getParamName());
                if (fieldType != null) {
                    statementAndParams.addParam4WherePart(filter.getParamName(), fieldType, filter);
                }
            }
        }

        statementAndParams.setStatementOrderPart("ORDER BY wtf.wtf_id ASC LIMIT 1000");
        result.setItems(new ArrayList<>(this.baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams, NtisMapFacilityDetails.class)));
        result.setFilteredItems(result.getItems().size());

        StatementAndParams totalCountStatement = new StatementAndParams(
                "SELECT COUNT(1) AS total_count FROM ntis.ntis_wastewater_treatment_faci WHERE wtf_discharge_geom IS NOT NULL");
        List<HashMap<String, String>> totalCountResult = baseControllerJDBC.selectQueryAsDataArrayList(conn, totalCountStatement);
        result.setTotalItems(Utils.getDouble(totalCountResult.get(0).get("total_count")).intValue());
        return result;
    }

    public NtisMapFacilityDetails getDischargeDetails(Connection conn, Integer wtfId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        StatementAndParams statementAndParams = new StatementAndParams("""
                WITH wtf_objects AS (
                SELECT wtf.wtf_id,
                       ARRAY_TO_JSON(
                           ARRAY_AGG(DISTINCT
                               JSONB_BUILD_OBJECT(
                                   'id', po.po_id,
                                   'coords', ARRAY[ST_X(ST_Transform(po.geom, 3857)), ST_Y(ST_Transform(po.geom, 3857))]
                               )
                           )) AS served_objects
                  FROM ntis.ntis_wastewater_treatment_faci wtf
                  LEFT JOIN ntis.ntis_served_objects so
                         ON so.so_wtf_id = wtf.wtf_id
                  LEFT JOIN tiles.point_buildings po
                         ON po.bn_id = so.so_bn_id
                 WHERE wtf_id = ?::int
                   AND wtf_discharge_geom IS NOT NULL
                 GROUP BY wtf.wtf_id, po.po_id
                )
                SELECT wtf.wtf_id AS wtfId,
                       wtf.wtf_state AS stateCode,
                       ad.ad_address AS address,
                       wtf.wtf_type AS typeCode,
                       COALESCE(dis_type_rfc.rfc_meaning, wtf.wtf_discharge_type) AS dischargeType,
                       ARRAY_TO_JSON(
                           ARRAY[
                               ST_X(ST_Transform(wtf.wtf_facility_geom, 3857)),
                               ST_Y(ST_Transform(wtf.wtf_facility_geom, 3857))])::text AS facility_coordinates,
                       ARRAY_TO_JSON(
                           ARRAY[
                               ST_X(ST_Transform(wtf.wtf_discharge_geom, 3857)),
                               ST_Y(ST_Transform(wtf.wtf_discharge_geom, 3857))])::text AS discharge_coordinates,
                       (CASE
                            WHEN objs.served_objects->0->>'id' IS NULL
                            THEN '[]' ELSE objs.served_objects
                        END) AS served_objects
                  FROM ntis.ntis_wastewater_treatment_faci wtf
                 INNER JOIN wtf_objects objs
                         ON objs.wtf_id = wtf.wtf_id
                  LEFT JOIN spark.spr_ref_codes dis_type_rfc
                         ON dis_type_rfc.rfc_code = wtf.wtf_discharge_type
                        AND dis_type_rfc.rfc_domain = 'DISCHARGE_WASTEWATER_TYPE'
                  LEFT JOIN ntis.ntis_adr_addresses ad
                         ON ad.ad_id = wtf.wtf_ad_id
                """);
        statementAndParams.addSelectParam(wtfId.doubleValue());

        ArrayList<MethodCaller> methods = new ArrayList<>();
        methods.add(new MethodCaller("setFacilityCoordinatesFromJsonString", new StatementDataGetter[] { new StatementStringGetter("facility_coordinates") }));
        methods.add(
                new MethodCaller("setDischargeCoordinatesFromJsonString", new StatementDataGetter[] { new StatementStringGetter("discharge_coordinates") }));
        methods.add(new MethodCaller("setServedObjectsFromJsonString", new StatementDataGetter[] { new StatementStringGetter("served_objects") }));
        Data2ObjectProcessor<NtisMapFacilityDetails> dataProcessor = new Data2ObjectProcessor<>(NtisMapFacilityDetails.class, methods);
        return this.baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams, dataProcessor).get(0);
    }

    public NtisMapTableResult<NtisMapCentDetails> getCentTable(Connection conn, ArrayList<AdvancedSearchParameterStatement> filters) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisMap.ACTION_READ_CENT_TABLE);
        NtisMapTableResult<NtisMapCentDetails> result = new NtisMapTableResult<>();

        StatementAndParams statementAndParams = new StatementAndParams("""
                SELECT bn_obj_inv_code AS ntrNumber,
                       bn_municipality AS municipality,
                       bn_place_name AS placeName,
                       bn_street AS street,
                       adresas AS address,
                       ST_X(ST_Transform(wtf.wtf_facility_geom, 3857)) AS x,
                       ST_Y(ST_Transform(wtf.wtf_facility_geom, 3857)) AS y
                  FROM tiles.cent_management_table cent
                 INNER JOIN ntis.ntis_wastewater_treatment_faci wtf
                         ON wtf.wtf_id = cent.wtf_id """);
        statementAndParams.setWhereExists(false);

        HashMap<String, Integer> fieldTypes = new HashMap<>();
        fieldTypes.put("bn_obj_inv_code", StatementAndParams.PARAM_STRING);
        fieldTypes.put("bn_municipality", StatementAndParams.PARAM_STRING);
        fieldTypes.put("bn_place_name", StatementAndParams.PARAM_STRING);
        fieldTypes.put("bn_street", StatementAndParams.PARAM_STRING);

        if (filters != null) {
            for (AdvancedSearchParameterStatement filter : filters) {
                Integer fieldType = fieldTypes.get(filter.getParamName());
                if (fieldType != null) {
                    statementAndParams.addParam4WherePart(filter.getParamName(), fieldType, filter);
                }
            }
        }

        statementAndParams.setStatementOrderPart("ORDER BY id ASC LIMIT 1000");
        result.setItems(new ArrayList<>(this.baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams, NtisMapCentDetails.class)));
        result.setFilteredItems(result.getItems().size());

        StatementAndParams totalCountStatement = new StatementAndParams("SELECT COUNT(1) AS total_count FROM tiles.cent_management_table");
        List<HashMap<String, String>> totalCountResult = baseControllerJDBC.selectQueryAsDataArrayList(conn, totalCountStatement);
        result.setTotalItems(Utils.getDouble(totalCountResult.get(0).get("total_count")).intValue());
        return result;
    }

    public NtisMapCentDetails getCentDetails(Connection conn, Integer id) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        StatementAndParams statementAndParams = new StatementAndParams("""
                SELECT bn_obj_inv_code AS ntrNumber,
                       bn_municipality AS municipality,
                       bn_place_name AS placeName,
                       bn_street AS street,
                       adresas AS address

                  FROM tiles.cent_management
                 WHERE id = ?::int""");
        statementAndParams.addSelectParam(id.doubleValue());
        return this.baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams, NtisMapCentDetails.class).get(0);
    }

    public NtisMapTableResult<NtisMapResearchDetails> getResearchTable(Connection conn, ArrayList<AdvancedSearchParameterStatement> filters, String lang)
            throws Exception {
        this.checkIsFormActionAssigned(conn, NtisMap.ACTION_READ_RESEARCH_LAYER);
        NtisMapTableResult<NtisMapResearchDetails> result = new NtisMapTableResult<>();
        String dateFormatDayDb = dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB);
        String dateFormatDayJava = dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA);
        StatementAndParams statementAndParams = new StatementAndParams("""
                SELECT res.wtf_id AS wtfId,
                       ad_address AS address,
                       TO_CHAR(res.a_research_date, '%s') AS aDate,
                       a_rfc.rft_display_code AS aComplianceName,
                       res.a_research_value AS aValue,
                       res.a_research_norm AS aNorm,
                       TO_CHAR(res.b_research_date, '%s') AS bDate,
                       b_rfc.rft_display_code AS bComplianceName,
                       res.b_research_value AS bValue,
                       res.b_research_norm AS bNorm,
                       TO_CHAR(res.c_research_date, '%s') AS cDate,
                       c_rfc.rft_display_code AS cComplianceName,
                       res.c_research_value AS cValue,
                       res.c_research_norm AS cNorm,
                       TO_CHAR(res.d_research_date, '%s') AS dDate,
                       d_rfc.rft_display_code AS dComplianceName,
                       res.d_research_value AS dValue,
                       res.d_research_norm AS dNorm,
                       ST_X(ST_Transform(res.geom, 3857)) AS x,
                       ST_Y(ST_Transform(res.geom, 3857)) AS y
                  FROM tiles.research res
                  LEFT JOIN spark.spr_ref_codes_vw a_rfc
                         ON a_rfc.rfc_code = a_research_norm_compliance
                        AND a_rfc.rfc_domain = 'NTIS_ORD_RESEARCH_NORMS'
                        AND a_rfc.rft_lang = ?
                  LEFT JOIN spark.spr_ref_codes_vw b_rfc
                         ON b_rfc.rfc_code = b_research_norm_compliance
                        AND b_rfc.rfc_domain = 'NTIS_ORD_RESEARCH_NORMS'
                        AND b_rfc.rft_lang = ?
                  LEFT JOIN spark.spr_ref_codes_vw c_rfc
                         ON c_rfc.rfc_code = c_research_norm_compliance
                        AND c_rfc.rfc_domain = 'NTIS_ORD_RESEARCH_NORMS'
                        AND c_rfc.rft_lang = ?
                  LEFT JOIN spark.spr_ref_codes_vw d_rfc
                         ON d_rfc.rfc_code = d_research_norm_compliance
                        AND d_rfc.rfc_domain = 'NTIS_ORD_RESEARCH_NORMS'
                        AND d_rfc.rft_lang = ?
                  LEFT JOIN ntis.ntis_wastewater_treatment_faci wtf
                         ON wtf.wtf_id = res.wtf_id
                  LEFT JOIN ntis.ntis_adr_addresses ad
                         ON ad.ad_id = wtf.wtf_ad_id """.formatted(dateFormatDayDb, dateFormatDayDb, dateFormatDayDb, dateFormatDayDb));
        statementAndParams.addSelectParam(lang);
        statementAndParams.addSelectParam(lang);
        statementAndParams.addSelectParam(lang);
        statementAndParams.addSelectParam(lang);
        statementAndParams.setWhereExists(false);

        if (filters != null) {
            for (AdvancedSearchParameterStatement filter : filters) {
                statementAndParams.addParam4WherePart("res.a_research_date", StatementAndParams.PARAM_DATE, filter, dateFormatDayJava);
                statementAndParams.addParam4WherePart("res.b_research_date", StatementAndParams.PARAM_DATE, filter, dateFormatDayJava);
                statementAndParams.addParam4WherePart("res.c_research_date", StatementAndParams.PARAM_DATE, filter, dateFormatDayJava);
                statementAndParams.addParam4WherePart("res.d_research_date", StatementAndParams.PARAM_DATE, filter, dateFormatDayJava);
            }
        }

        statementAndParams.setStatementOrderPart("ORDER BY id ASC LIMIT 1000");
        result.setItems(new ArrayList<>(this.baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams, NtisMapResearchDetails.class)));
        result.setFilteredItems(result.getItems().size());

        StatementAndParams totalCountStatement = new StatementAndParams("SELECT COUNT(1) AS total_count FROM tiles.research");
        List<HashMap<String, String>> totalCountResult = baseControllerJDBC.selectQueryAsDataArrayList(conn, totalCountStatement);
        result.setTotalItems(Utils.getDouble(totalCountResult.get(0).get("total_count")).intValue());
        return result;
    }

    public NtisMapResearchDetails getResearchDetails(Connection conn, Integer id, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisMap.ACTION_READ_RESEARCH_LAYER);
        String dateFormatDayDb = dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB);
        StatementAndParams statementAndParams = new StatementAndParams("""
                SELECT res.id AS id,
                       res.wtf_id AS wtfId,
                       ad_address AS address,
                       TO_CHAR(res.a_research_date, '%s') AS aDate,
                       a_rfc.rft_display_code AS aComplianceName,
                       res.a_research_value AS aValue,
                       res.a_research_norm AS aNorm,
                       TO_CHAR(res.b_research_date, '%s') AS bDate,
                       b_rfc.rft_display_code AS bComplianceName,
                       res.b_research_value AS bValue,
                       res.b_research_norm AS bNorm,
                       TO_CHAR(res.c_research_date, '%s') AS cDate,
                       c_rfc.rft_display_code AS cComplianceName,
                       res.c_research_value AS cValue,
                       res.c_research_norm AS cNorm,
                       TO_CHAR(res.d_research_date, '%s') AS dDate,
                       d_rfc.rft_display_code AS dComplianceName,
                       res.d_research_value AS dValue,
                       res.d_research_norm AS dNorm
                  FROM tiles.research res
                  LEFT JOIN spark.spr_ref_codes_vw a_rfc
                         ON a_rfc.rfc_code = a_research_norm_compliance
                        AND a_rfc.rfc_domain = 'NTIS_ORD_RESEARCH_NORMS'
                        AND a_rfc.rft_lang = ?
                  LEFT JOIN spark.spr_ref_codes_vw b_rfc
                         ON b_rfc.rfc_code = b_research_norm_compliance
                        AND b_rfc.rfc_domain = 'NTIS_ORD_RESEARCH_NORMS'
                        AND b_rfc.rft_lang = ?
                  LEFT JOIN spark.spr_ref_codes_vw c_rfc
                         ON c_rfc.rfc_code = c_research_norm_compliance
                        AND c_rfc.rfc_domain = 'NTIS_ORD_RESEARCH_NORMS'
                        AND c_rfc.rft_lang = ?
                  LEFT JOIN spark.spr_ref_codes_vw d_rfc
                         ON d_rfc.rfc_code = d_research_norm_compliance
                        AND d_rfc.rfc_domain = 'NTIS_ORD_RESEARCH_NORMS'
                        AND d_rfc.rft_lang = ?
                  LEFT JOIN ntis.ntis_wastewater_treatment_faci wtf
                         ON wtf.wtf_id = res.wtf_id
                  LEFT JOIN ntis.ntis_adr_addresses ad
                         ON ad.ad_id = wtf.wtf_ad_id
                 WHERE id = ?::int """.formatted(dateFormatDayDb, dateFormatDayDb, dateFormatDayDb, dateFormatDayDb));
        statementAndParams.addSelectParam(lang);
        statementAndParams.addSelectParam(lang);
        statementAndParams.addSelectParam(lang);
        statementAndParams.addSelectParam(lang);
        statementAndParams.addSelectParam(id.doubleValue());
        return this.baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams, NtisMapResearchDetails.class).get(0);
    }

    public NtisMapTableResult<NtisMapDisposalDetails> getDisposalTable(Connection conn, ArrayList<AdvancedSearchParameterStatement> filters, String lang)
            throws Exception {
        this.checkIsFormActionAssigned(conn, NtisMap.ACTION_READ_DISPOSAL_LAYER);
        NtisMapTableResult<NtisMapDisposalDetails> result = new NtisMapTableResult<>();
        String dateFormatDayDb = dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB);
        String dateFormatDayJava = dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA);
        StatementAndParams statementAndParams = new StatementAndParams("""
                SELECT dis.wtf_id AS wtfId,
                       ad_address AS address,
                       TO_CHAR(dis.ord_removed_sewage_date, '%s') AS disposalDate,
                       rfc.rft_display_code AS stateName,
                       ST_X(ST_Transform(dis.geom, 3857)) AS x,
                       ST_Y(ST_Transform(dis.geom, 3857)) AS y
                  FROM tiles.disposal dis
                  LEFT JOIN spark.spr_ref_codes_vw rfc
                         ON rfc.rfc_code = dis.ord_state
                        AND rfc.rfc_domain = 'NTIS_ORDER_STATUS'
                        AND rfc.rft_lang = ?
                  LEFT JOIN ntis.ntis_wastewater_treatment_faci wtf
                         ON wtf.wtf_id = dis.wtf_id
                  LEFT JOIN ntis.ntis_adr_addresses ad
                         ON ad.ad_id = wtf.wtf_ad_id """.formatted(dateFormatDayDb));
        statementAndParams.addSelectParam(lang);
        statementAndParams.setWhereExists(false);

        if (filters != null) {
            for (AdvancedSearchParameterStatement filter : filters) {
                statementAndParams.addParam4WherePart("dis.ord_removed_sewage_date", StatementAndParams.PARAM_DATE, filter, dateFormatDayJava);
            }
        }

        statementAndParams.setStatementOrderPart("ORDER BY id ASC LIMIT 1000");
        result.setItems(new ArrayList<>(this.baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams, NtisMapDisposalDetails.class)));
        result.setFilteredItems(result.getItems().size());

        StatementAndParams totalCountStatement = new StatementAndParams("SELECT COUNT(1) AS total_count FROM tiles.disposal");
        List<HashMap<String, String>> totalCountResult = baseControllerJDBC.selectQueryAsDataArrayList(conn, totalCountStatement);
        result.setTotalItems(Utils.getDouble(totalCountResult.get(0).get("total_count")).intValue());
        return result;
    }

    public NtisMapDisposalDetails getDisposalDetails(Connection conn, Integer id, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisMap.ACTION_READ_DISPOSAL_LAYER);
        String dateFormatDayDb = dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB);
        StatementAndParams statementAndParams = new StatementAndParams("""
                SELECT dis.id AS id,
                       dis.wtf_id AS wtfId,
                       ad_address AS address,
                       TO_CHAR(dis.ord_removed_sewage_date, '%s') AS disposalDate,
                       rfc.rft_display_code AS stateName,
                       ST_X(ST_Transform(dis.geom, 3857)) AS x,
                       ST_Y(ST_Transform(dis.geom, 3857)) AS y
                  FROM tiles.disposal dis
                  LEFT JOIN spark.spr_ref_codes_vw rfc
                         ON rfc.rfc_code = dis.ord_state
                        AND rfc.rfc_domain = 'NTIS_ORDER_STATUS'
                        AND rfc.rft_lang = ?
                  LEFT JOIN ntis.ntis_wastewater_treatment_faci wtf
                         ON wtf.wtf_id = dis.wtf_id
                  LEFT JOIN ntis.ntis_adr_addresses ad
                         ON ad.ad_id = wtf.wtf_ad_id
                 WHERE id = ?::int """.formatted(dateFormatDayDb));
        statementAndParams.addSelectParam(lang);
        statementAndParams.addSelectParam(id.doubleValue());
        return this.baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams, NtisMapDisposalDetails.class).get(0);
    }

    public List<NtisMapClickedPoint> getNamesOfClickedPoints(Connection conn, List<NtisMapClickedPoint> items) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        List<String> statements = new ArrayList<String>();
        List<Integer> statementParams = new ArrayList<Integer>();
        List<NtisMapClickedPoint> faciItems = items.stream().filter(item -> "faci".equals(item.getLayer())).toList();
        if (!faciItems.isEmpty()) {
            String faciQuestionMarks = String.join(",", faciItems.stream().map(item -> "?::int").toList());
            statements.add("""
                          SELECT 'faci' AS layer,
                                  wtf.wtf_id AS id,
                                  ad.ad_address AS description
                     FROM ntis.ntis_wastewater_treatment_faci wtf
                     LEFT JOIN ntis.ntis_adr_addresses ad
                            ON ad.ad_id = wtf.wtf_ad_id
                    WHERE wtf.wtf_id IN (""" + faciQuestionMarks + ") ");
            faciItems.forEach(item -> {
                statementParams.add(item.getId());
            });
        }
        List<NtisMapClickedPoint> discItems = items.stream().filter(item -> "disc".equals(item.getLayer())).toList();
        if (!discItems.isEmpty()) {
            String discQuestionMarks = String.join(",", discItems.stream().map(item -> "?::int").toList());
            statements.add("""
                          SELECT 'disc' AS layer,
                                  wtf.wtf_id AS id,
                                  ad.ad_address || ' / ' || ROUND(wtf_discharge_latitude) || ', ' || ROUND(wtf_discharge_longitude) AS description
                     FROM ntis.ntis_wastewater_treatment_faci wtf
                     LEFT JOIN ntis.ntis_adr_addresses ad
                            ON ad.ad_id = wtf.wtf_ad_id
                    WHERE wtf.wtf_id IN (""" + discQuestionMarks + ") ");
            discItems.forEach(item -> {
                statementParams.add(item.getId());
            });
        }
        List<NtisMapClickedPoint> buildItems = items.stream().filter(item -> "build".equals(item.getLayer())).toList();
        if (!buildItems.isEmpty()) {
            String buildQuestionMarks = String.join(",", buildItems.stream().map(item -> "?::int").toList());
            statements.add("""
                    SELECT DISTINCT ON (po.po_id) 'build' AS layer,
                           po.po_id AS id,
                           STRING_AGG(ad.ad_address, ',') AS description
                      FROM tiles.point_buildings po
                      LEFT JOIN ntis.ntis_served_objects so
                             ON so.so_bn_id = po.bn_id
                      LEFT JOIN ntis.ntis_adr_addresses ad
                             ON ad.ad_id = so.so_ad_id
                     WHERE po.po_id IN (""" + buildQuestionMarks + ") " + """
                     GROUP BY id, layer, so_bn_id
                    """);
            buildItems.forEach(item -> {
                statementParams.add(item.getId());
            });
        }
        List<NtisMapClickedPoint> disposalItems = items.stream().filter(item -> "disposal".equals(item.getLayer())).toList();
        if (!disposalItems.isEmpty()) {
            String questionMarks = String.join(",", disposalItems.stream().map(item -> "?::int").toList());
            statements.add("""
                    SELECT 'disposal' AS layer,
                           dis.id,
                           ad_address || ' (ID: ' || dis.wtf_id || ')' AS description
                      FROM tiles.disposal dis
                      LEFT JOIN ntis.ntis_wastewater_treatment_faci wtf
                             ON wtf.wtf_id = dis.wtf_id
                      LEFT JOIN ntis.ntis_adr_addresses ad
                             ON ad.ad_id = wtf.wtf_ad_id
                     WHERE dis.id IN (%s)
                    """.formatted(questionMarks));
            disposalItems.forEach(item -> {
                statementParams.add(item.getId());
            });
        }
        List<NtisMapClickedPoint> researchItems = items.stream().filter(item -> "research".equals(item.getLayer())).toList();
        if (!researchItems.isEmpty()) {
            String questionMarks = String.join(",", researchItems.stream().map(item -> "?::int").toList());
            statements.add("""
                    SELECT 'research' AS layer,
                           res.id,
                           ad_address || ' (ID: ' || res.wtf_id || ')' AS description
                      FROM tiles.research res
                      LEFT JOIN ntis.ntis_wastewater_treatment_faci wtf
                             ON wtf.wtf_id = res.wtf_id
                      LEFT JOIN ntis.ntis_adr_addresses ad
                             ON ad.ad_id = wtf.wtf_ad_id
                     WHERE res.id IN (%s)
                    """.formatted(questionMarks));
            researchItems.forEach(item -> {
                statementParams.add(item.getId());
            });
        }
        if (statements.isEmpty()) {
            return new ArrayList<>();
        }
        StatementAndParams statementAndParams = new StatementAndParams(String.join(" UNION ALL ", statements));
        for (Integer statementParam : statementParams) {
            statementAndParams.addSelectParam(Utils.getDouble(statementParam));
        }
        return this.baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams, NtisMapClickedPoint.class);
    }

    public ResponseEntity<Resource> getTilesWithAuth(Connection conn, HttpHeaders headers, String layer, String z, String x, String y)
            throws IOException, SparkBusinessException {
        if ("disposal".equals(layer)) {
            this.checkIsFormActionAssigned(conn, NtisMap.ACTION_READ_DISPOSAL_LAYER);
        } else if ("research".equals(layer)) {
            this.checkIsFormActionAssigned(conn, NtisMap.ACTION_READ_RESEARCH_LAYER);
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
        String url = this.tilesServerUrl + "/maps/ntis/" + layer + "/" + z + "/" + x + "/" + y + ".pbf";
        HttpHeaders newRequestHeaders = new HttpHeaders();
        List<String> acceptEncoding = headers.get(HttpHeaders.ACCEPT_ENCODING);
        if (acceptEncoding != null) {
            newRequestHeaders.addAll(HttpHeaders.ACCEPT_ENCODING, acceptEncoding);
        }
        HttpEntity<Object> entity = new HttpEntity<>(newRequestHeaders);
        return restTemplate.exchange(url, HttpMethod.GET, entity, Resource.class);
    }

}
