package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.constants.DbConstants;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.Data2ObjectProcessor;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.YesNo;
import lt.project.ntis.dao.NtisWastewaterTreatmentFaciDAO;
import lt.project.ntis.enums.NtisContractStatus;
import lt.project.ntis.enums.NtisOrgType;
import lt.project.ntis.enums.NtisServiceItemType;
import lt.project.ntis.logic.forms.model.NtisWtfInfo;
import lt.project.ntis.logic.forms.security.NtisResearchListBrowseSecurityManager;
import lt.project.ntis.service.NtisSelectedFacilitiesDBService;
import lt.project.ntis.service.NtisWastewaterTreatmentFaciDBService;

/**
 * Klasė skirta formos "Tyrimų teikėjų sąrašas su filtru" biznio logikai apibrėžti
 * Formos kodas: T3010
 */

@Component
public class NtisResearchProvidersList extends FormBase {

    public static final String ORDER_LAB_SERVICE = "ORDER_LAB_SERVICE";

    public static final String ORDER_LAB_SERVICE_NAME = "Laboratorinių tyrimų užasakymas";

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbstatementManager;

    @Autowired
    NtisSelectedFacilitiesDBService ntisSelectedFacilitiesDBService;

    @Autowired
    NtisWastewaterTreatmentFaciDBService ntisWastewaterTreatmentFaciDBService;

    @Override
    public String getFormName() {
        return "NTIS_RESEARCH_PROVIDERS_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Tyrimų teikėjų sąrašas su filtru", "Tyrimų teikėjų sąrašo su filtru forma");
        addFormActions(FormBase.ACTION_READ);
        addFormAction(ORDER_LAB_SERVICE, ORDER_LAB_SERVICE_NAME, ORDER_LAB_SERVICE_NAME);
    }

    public NtisWtfInfo getSelectedWtfInfo(Connection conn, Double usrId, Double orgId, Double perId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisResearchProvidersList.ACTION_READ);
        if (orgId == null && perId == null) {
            throw new Exception("No organization ID or person ID provided");
        }
        StatementAndParams statementAndParams = new StatementAndParams("" //
                + "SELECT wtf.wtf_id AS id,  " //
                + "       wtf.wtf_capacity ||' m³' AS capacity, " //
                + "       addr.full_address_text AS address " //
                + "  FROM ntis.ntis_facility_owners fo " //
                + " INNER JOIN ntis.ntis_wastewater_treatment_faci wtf ON wtf.wtf_id = fo.fo_wtf_id " //
                + " INNER JOIN ntis.ntis_selected_facilities fs ON fs.fs_wtf_id = wtf.wtf_id " //
                + " LEFT JOIN spr_org_users ou on fo.fo_org_id = ou.ou_org_id and current_date between ou.ou_date_from and coalesce(ou.ou_date_to, now()) " //
                + "  LEFT JOIN ntis.ntis_address_vw addr ON addr.address_id = wtf.wtf_ad_id " //
                + " WHERE fs.fs_usr_id = ?::int " //
                + "   AND wtf.wtf_deleted = '" + DbConstants.BOOLEAN_FALSE + "' ");
        statementAndParams.addSelectParam(usrId);
        statementAndParams.setWhereExists(true);
        if (orgId != null) {
            statementAndParams.addParam4WherePart("fs.fs_org_id = ?::int ", orgId);
            statementAndParams.addParam4WherePart("fo.fo_org_id = ?::int ", orgId);
            statementAndParams.addParam4WherePart("ou.ou_usr_id = ?::int ", usrId);
        } else {
            statementAndParams.addCondition4WherePart("fs.fs_org_id IS NULL", StatementAndParams.AND_OPERAND);
            statementAndParams.addParam4WherePart("fo.fo_per_id = ?::int", perId);
        }
        statementAndParams.setRecordCountLimitPart("LIMIT 1");

        List<NtisWtfInfo> queryResult = queryController.selectQueryAsObjectArrayList(conn, statementAndParams,
                new Data2ObjectProcessor<NtisWtfInfo>(NtisWtfInfo.class));
        if (queryResult == null || queryResult.isEmpty()) {
            throw new Exception(
                    "No selected water treatment facility found for user with id " + usrId + " (organization id " + orgId + " , person id " + perId + ")");
        }
        return queryResult.get(0);
    }

    public String getLaboratoriesList(Connection conn, SelectRequestParams params, Double orgId, Double userId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisResearchProvidersList.ACTION_READ);
        Double selectedFacility = ntisSelectedFacilitiesDBService.getSelecteFacility(conn, userId, orgId);
        if (selectedFacility == null) {
            throw new Exception("Nepasirinktas įrenginys");
        }
        NtisWastewaterTreatmentFaciDAO wtfDAO = ntisWastewaterTreatmentFaciDBService.loadRecord(conn, selectedFacility);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("" //
                + "SELECT serv.srv_price_from,"//
                + "       org.org_name,"//
                + "       org.org_phone, "//
                + "       org.org_email, "//
                + "       serv.srv_description, "//
                + "       serv.srv_id"//
                + "       FROM spark.spr_organizations org"//
                + " INNER JOIN ntis.ntis_service_requests ser_req "//
                + "       ON ser_req.sr_org_id = org.org_id " //
                + "       AND ser_req.sr_type = '" + NtisOrgType.PASLAUG + "' AND ser_req.sr_status = '" + NtisContractStatus.CONFIRMED + "'" //
                + " INNER JOIN ntis.ntis_service_req_items sri "//
                + "       ON sri.sri_sr_id = ser_req.sr_id " //
                + "       AND sri.sri_service_type = '" + NtisServiceItemType.TYRIMAI + "'"//
                + " INNER JOIN ntis.ntis_services serv "//
                + "       ON serv.srv_id = sri.sri_srv_id"//
                + " WHERE org.c01 IN ('" + NtisOrgType.PASLAUG + "', '" + NtisOrgType.PASLAUG_VANDEN + "') "//
                + "   AND (serv.srv_lithuanian_level='" + YesNo.Y.getCode()
                + "'   OR EXISTS (SELECT 1 FROM ntis.ntis_service_municipalities smn WHERE smn.smn_municipality::int = ?::int AND smn.smn_srv_id = serv.srv_id))");

        stmt.addSelectParam(wtfDAO.getWtf_facility_municipality_code());
        NtisResearchListBrowseSecurityManager sqm = new NtisResearchListBrowseSecurityManager();
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }
}
