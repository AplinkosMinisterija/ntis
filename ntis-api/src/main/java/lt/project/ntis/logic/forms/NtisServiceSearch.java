package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.constants.DbConstants;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.Data2ObjectProcessor;
import eu.itreegroup.spark.dao.query.MethodCaller;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementDataGetter;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementStringGetter;
import lt.project.ntis.enums.NtisOrgType;
import lt.project.ntis.models.NtisAddressSuggestion;
import lt.project.ntis.models.NtisServiceSearchRequest;
import lt.project.ntis.models.NtisServiceSearchResult;

/**
 * Klasė skirta formos "S1000" biznio logikai apibrėžti
 */
@Component
public class NtisServiceSearch extends FormBase {

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    DBStatementManager dbStatementManager;

    @Override
    public String getFormName() {
        return "NTIS_SERVICE_SEARCH";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Paslaugų paieška", "Ieškoti paslaugų teikėjo");
        addFormActions(NtisServiceSearch.ACTION_READ);
    }

    public List<NtisAddressSuggestion> getAddressSuggestions(Connection conn, String searchText) throws Exception {
        if (searchText == null || searchText.length() < 3) {
            throw new Exception("Provided search text must be at least 3 characters long");
        } else {
            StatementAndParams stmt = new StatementAndParams();
            stmt.setStatement("""
                     SELECT ad_id AS ID,
                           ad_address AS address,
                           st_x(ad_geom) AS lksX,
                           st_y(ad_geom) as lksY
                        FROM ntis.ntis_adr_addresses
                     WHERE ad_address_search like '%'||?||'%'
                     ORDER by ad_address
                     LIMIT 30
                    """);
            stmt.addSelectParam(Utils.replaceNationalCharacters(searchText).toLowerCase());
            return baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisAddressSuggestion.class);
        }
    }

    public NtisServiceSearchRequest getSelectedWtfInfo(Connection conn, Double usrId, Double orgId, Double perId) throws Exception {
        this.checkIsFormActionAssigned(conn, ACTION_READ);
        if (orgId == null && perId == null) {
            throw new IllegalArgumentException("No organization ID or person ID provided");
        }
        StatementAndParams statementAndParams = new StatementAndParams("" //
                + "SELECT wtf.wtf_id AS wtfId,  " //
                + "       wtf.wtf_type AS equipmentType,  " //
                + "       wtf.wtf_distance::INTEGER AS distanceToObject, " //
                + "       ad.ad_address AS address, " //
                + "       CASE WHEN wtf.wtf_ad_id IS NOT NULL THEN st_x(ad.ad_geom) ELSE wtf.wtf_facility_latitude END AS lksX, " //
                + "       CASE WHEN wtf.wtf_ad_id IS NOT NULL THEN st_y(ad.ad_geom) ELSE wtf.wtf_facility_longitude END AS lksY " //
                + "  FROM ntis.ntis_facility_owners fo " //
                + " INNER JOIN ntis.ntis_wastewater_treatment_faci wtf ON wtf.wtf_id = fo.fo_wtf_id " //
                + " INNER JOIN ntis.ntis_selected_facilities fs ON fs.fs_wtf_id = wtf.wtf_id " //
                + "  LEFT JOIN ntis_adr_addresses ad ON ad.ad_id = wtf.wtf_ad_id " //
                + " WHERE fs.fs_usr_id = ?::int " //
                + "   AND wtf.wtf_deleted = '" + DbConstants.BOOLEAN_FALSE + "' ");
        statementAndParams.addSelectParam(usrId);
        statementAndParams.setWhereExists(true);
        if (orgId != null) {
            statementAndParams.addParam4WherePart("fs.fs_org_id = ?::int", orgId);
            statementAndParams.addParam4WherePart("fo.fo_org_id = ?::int", orgId);
        } else {
            statementAndParams.addCondition4WherePart("fs.fs_org_id IS NULL", StatementAndParams.AND_OPERAND);
            statementAndParams.addParam4WherePart("fo.fo_per_id = ?::int", perId);
        }
        statementAndParams.setRecordCountLimitPart("LIMIT 1");

        List<NtisServiceSearchRequest> queryResult = baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams,
                new Data2ObjectProcessor<NtisServiceSearchRequest>(NtisServiceSearchRequest.class));
        return queryResult == null || queryResult.isEmpty() ? null : queryResult.get(0);
    }

    public ArrayList<NtisServiceSearchResult> getSearchResult(Connection conn, NtisServiceSearchRequest request) throws SQLException, Exception {
        this.checkIsFormActionAssigned(conn, ACTION_READ);
        String srvTypeInQuestionMarks = "";
        for (int i = 0; i < request.getServices().size(); i++) {
            srvTypeInQuestionMarks = srvTypeInQuestionMarks + (i > 0 ? ", ?" : "?");
        }
        String orderByStmt = "";
        String orderByPriceStmt = "";
        if (request.getPriceClause() != null || request.getRatingClause() != null) {
            if (request.getPriceClause() != null) {
                if (request.getPriceClause().equals("1")) {
                    orderByStmt = " order by srv_price_from asc ";
                    orderByPriceStmt = " order by srv_price_from asc ";
                } 
                if (request.getRatingClause() != null) {
                    if (request.getRatingClause().equals("1")) {
                        orderByStmt = orderByStmt + ", reviews.score desc nulls last";
                    } 
                }
            } else if (request.getRatingClause() != null) {
                if (request.getRatingClause().equals("1")) {
                    orderByStmt = " order by reviews.score desc nulls last";
                } 
            }
        }

        StatementAndParams statementAndParams = new StatementAndParams("""
                 SELECT org.org_id AS orgId,
                        org.org_name AS orgName,
                         srvs.services AS servicesJson,
                         srvs.srv_price_from,
                         reviews.score,
                         COALESCE(car.hasCar,'%s') as hasCar
                         FROM spr_organizations org
                INNER JOIN (
                  SELECT srv.srv_org_id,
                         max(srv.srv_price_from) as srv_price_from,
                         JSONB_AGG(JSONB_BUILD_OBJECT(
                             'id', srv.srv_id,
                             'name', rfc.rfc_meaning,
                             'type', srv.srv_type,
                             'email', srv.srv_email,
                             'phone', srv.srv_phone_no,
                             'description', COALESCE(srv.srv_description, ''),
                             'priceFrom', srv.srv_price_from,
                             'priceTo', coalesce(srv.srv_price_to, 0),
                             'completionInDays', srv.srv_completion_in_days_from,
                             'completionInDaysTo', COALESCE(srv.srv_completion_in_days_to, 0),
                             'contractAvailable', srv.srv_contract_available )""".formatted(DbConstants.BOOLEAN_FALSE) + orderByPriceStmt + """
                        ) AS services
                 FROM ntis_services srv
                INNER JOIN spr_ref_codes_vw rfc ON rfc.rfc_code = srv.srv_type AND rfc.rfc_domain = 'NTIS_SRV_ITEM_TYPE' AND rfc.rft_lang = ?
                 LEFT JOIN ntis_service_municipalities smn ON smn.smn_srv_id = srv.srv_id
                 LEFT JOIN ntis_municipalities mp ON mp.mp_code = smn.smn_municipality AND st_contains(mp.mp_geom, ST_SetSRID(ST_MakePoint(?, ?), 3346)) = 't'
                WHERE srv.srv_type IN (%s)
                  AND srv.srv_available_in_ntis_portal = '%s'
                  AND (srv.srv_lithuanian_level = '%s' OR mp.mp_code IS NOT NULL)
                  AND CURRENT_DATE between srv.srv_date_from and COALESCE(srv.srv_date_to - INTERVAL '1 DAY', now())
                       GROUP BY srv.srv_org_id """.formatted(srvTypeInQuestionMarks, DbConstants.BOOLEAN_TRUE, DbConstants.BOOLEAN_TRUE) + orderByPriceStmt
                + """

                         ) AS srvs ON srvs.srv_org_id = org.org_id
                         LEFT JOIN ( SELECT distinct '%s' as hasCar,
                                    cr_org_id
                                    from ntis_cars cr
                                    WHERE cr.cr_capacity >= ?
                                       AND cr.cr_tube_length >= ?
                                  AND %s
                             ) as car ON car.cr_org_id = org.org_id
                         LEFT JOIN ( SELECT round(avg(rev.rev_score), 2) as score,
                                            rev.rev_pasl_org_id as org_id
                                       FROM ntis_reviews rev
                                       WHERE (select count(rev_id) from ntis_reviews r2 where r2.rev_pasl_org_id = rev.rev_pasl_org_id group by rev.rev_pasl_org_id) >= 10
                                       GROUP BY rev.rev_pasl_org_id) as reviews on reviews.org_id = org.org_id
                        WHERE org.c01 in ('%s', '%s')
                        AND %s

                                         """
                        .formatted(DbConstants.BOOLEAN_TRUE, dbStatementManager.getPeriodValidationForCurrentDateStr("cr_date_from", "cr_date_to", false),
                                NtisOrgType.PASLAUG, NtisOrgType.PASLAUG_VANDEN,
                                dbStatementManager.getPeriodValidationForCurrentDateStr("org.d01", "org.d02", false))
                + orderByStmt);
        statementAndParams.addSelectParam(request.getLang());
        statementAndParams.addSelectParam(request.getLksX());
        statementAndParams.addSelectParam(request.getLksY());
        for (

        String service : request.getServices()) {
            statementAndParams.addSelectParam(service);
        }
        statementAndParams.addSelectParam(Utils.getDouble(request.getCarCapacity() != null ? request.getCarCapacity() : 0));
        statementAndParams.addSelectParam(Utils.getDouble(request.getDistanceToObject()));

        ArrayList<MethodCaller> methods = new ArrayList<MethodCaller>();
        methods.add(new MethodCaller("setServicesJson", new StatementDataGetter[] { new StatementStringGetter("servicesJson") }));

        Data2ObjectProcessor<NtisServiceSearchResult> dataProcessor = new Data2ObjectProcessor<NtisServiceSearchResult>(NtisServiceSearchResult.class, methods);

        List<NtisServiceSearchResult> queryResult = baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams, dataProcessor);

        ArrayList<NtisServiceSearchResult> result = new ArrayList<NtisServiceSearchResult>(queryResult);
        return result;
    }

}
