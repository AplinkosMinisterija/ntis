package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBServiceImpl;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.dao.NtisFavoriteWastTreatOrgDAO;
import lt.project.ntis.enums.NtisOrgType;
import lt.project.ntis.logic.forms.security.NtisPriorityFacilitiesListSecurityManager;
import lt.project.ntis.models.NtisPriorityFacilities;
import lt.project.ntis.service.NtisFavoriteWastTreatOrgDBService;

/**
 * Klasė skirta formos "Prioritetinių vandentvarkos įmonių valyklų pasirinkimas"  biznio logikai apibrėžti
 */

@Component
public class NtisPriorityFacilitiesList extends FormBase {

    public static final String MANAGE_WF_PRIORITY_LIST = "MANAGE_WF_PRIORITY_LIST";

    private final BaseControllerJDBC baseControllerJDBC;

    private final NtisFavoriteWastTreatOrgDBService ntisFavoriteWastTreatOrgDBService;

    private final DBStatementManager dbStatementManager;

    private final SprOrgUsersDBServiceImpl sprOrgUsersDBService;

    public NtisPriorityFacilitiesList(BaseControllerJDBC baseControllerJDBC, NtisFavoriteWastTreatOrgDBService ntisFavoriteWastTreatOrgDBService,
            DBStatementManager dbStatementManager, SprOrgUsersDBServiceImpl sprOrgUsersDBService) {
        super();
        this.baseControllerJDBC = baseControllerJDBC;
        this.ntisFavoriteWastTreatOrgDBService = ntisFavoriteWastTreatOrgDBService;
        this.dbStatementManager = dbStatementManager;
        this.sprOrgUsersDBService = sprOrgUsersDBService;
    }

    @Override
    public String getFormName() {
        return "NTIS_PRIORITY_WF_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Prioritetinių vandentvarkos įmonių valyklų pasirinkimas", "Prioritetinių vandentvarkos įmonių valyklų pasirinkimo forma");
        addFormActions(FormBase.ACTION_READ);
        addFormAction(MANAGE_WF_PRIORITY_LIST, MANAGE_WF_PRIORITY_LIST, MANAGE_WF_PRIORITY_LIST);
    }

    public String getWaterManagersList(Connection conn, Double usrId, SelectRequestParams params, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisPriorityFacilitiesList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                   		with wto_info as
                (select wto.wto_id,
                        wto.wto_name,
                        case
                          when fav.fwto_wto_id is not null then
                           'Y'
                          else
                           'N'
                        end as wto_selected,
                        wto.wto_address,
                        org.org_id,
                        org.org_name,
                        org.org_phone,
                        ORG.ORG_EMAIL,
                        ORG.C03
                   FROM SPARK.SPR_ORGANIZATIONS AS ORG
                   JOIN NTIS.NTIS_WASTEWATER_TREATMENT_ORG AS WTO
                     ON WTO.WTO_ORG_ID = ORG.ORG_ID
                    AND WTO.WTO_IS_IT_USED = 'Y'
                   LEFT JOIN NTIS.NTIS_FAVORITE_WAST_TREAT_ORGS AS FAV
                     ON FAV.FWTO_WTO_ID = WTO.WTO_ID
                    AND FAV.FWTO_ORG_ID = ?::int
                   JOIN SPARK.SPR_ORGANIZATIONS AS ORGF
                     ON ORGF.ORG_ID = WTO.WTO_ORG_ID
                  WHERE ORG.C01 IN ('""" + NtisOrgType.VANDEN + "', '" + NtisOrgType.PASLAUG_VANDEN + "'" + """
                   		)
                   	 AND  ORG.N01 = 1
                     AND EXISTS (SELECT 1
                            FROM NTIS_WASTEWATER_TREATMENT_ORG CWTO
                           WHERE CWTO.WTO_ORG_ID = ORG.ORG_ID
                             AND CWTO.WTO_IS_IT_USED = 'Y'
                           GROUP BY CWTO.WTO_ORG_ID))
                SELECT ORG_ID,
                       ORG_NAME,
                       ORG_PHONE,
                       ORG_EMAIL,
                       C03 AS ORG_WEBSITE,
                       JSONB_AGG(JSONB_BUILD_OBJECT('wto_id',
                                                    wto_id,
                                                    'wto_name',
                                                    wto_name,
                                                    'wto_selected',
                                                    wto_selected,
                                                    'wto_address',
                                                    wto_address)) AS FACILITIES_INFO
                  FROM wto_info
                    		""");

        stmt.setStatementGroupPart("wto_info.ORG_ID, wto_info.ORG_NAME, wto_info.ORG_PHONE, wto_info.ORG_EMAIL, wto_info.C03");
        stmt.addSelectParam(orgId);

        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), usrId, stmt, advancedParamList);

        stmt.addParam4WherePart("org_name", StatementAndParams.PARAM_STRING, advancedParamList.get("org_name"));
        stmt.addParam4WherePart("wto_name", StatementAndParams.PARAM_STRING, advancedParamList.get("wto_name"));
        stmt.addParam4WherePart("wto_selected", StatementAndParams.PARAM_STRING, advancedParamList.get("wto_selected"));
        stmt.addParam4WherePart("wto_address", StatementAndParams.PARAM_STRING, advancedParamList.get("wto_address"));
        stmt.addParam4WherePart(dbStatementManager.colNamesToConcatString("org_name", "wto_name", "wto_selected", "wto_address"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));
        NtisPriorityFacilitiesListSecurityManager sqm = new NtisPriorityFacilitiesListSecurityManager();
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    public void setPriorityFacilitiesList(Connection conn, Double orgId, Double usrId, ArrayList<NtisPriorityFacilities> facilities) throws Exception {
        this.checkIsFormActionAssigned(conn, MANAGE_WF_PRIORITY_LIST);
        if (!sprOrgUsersDBService.checkIsUserInOrganization(conn, usrId, orgId)) {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
        ntisFavoriteWastTreatOrgDBService.deleteFavoritesForOrganizations(conn, orgId);
        for (NtisPriorityFacilities facility : facilities) {
            ArrayList<Double> wtoIds = facility.getWto_id();
            NtisFavoriteWastTreatOrgDAO priorityWtoDAO;
            for (Double newWtoId : wtoIds) {
                priorityWtoDAO = ntisFavoriteWastTreatOrgDBService.newRecord();
                priorityWtoDAO.setFwto_wto_id(newWtoId);
                priorityWtoDAO.setFwto_org_id(orgId);
                ntisFavoriteWastTreatOrgDBService.saveRecord(conn, priorityWtoDAO);
            }
        }
    }
}