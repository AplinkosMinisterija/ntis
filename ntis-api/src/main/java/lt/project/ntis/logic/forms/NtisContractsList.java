package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.ForeignKeyParams;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import lt.project.common.NtisCommonMethods;
import lt.project.ntis.constants.NtisCommonActionsConstants;
import lt.project.ntis.logic.forms.security.NtisContractsListSecurityManager;
import lt.project.ntis.service.NtisContractsDBService;

/**
 * Klasė skirta formos "Ieškoti prašymų" biznio logikai apibrėžti
 */
@Component
public class NtisContractsList extends FormBase {

    public static String UPLOAD_CONTRACT = "UPLOAD_CONTRACT";

    public static String UPLOAD_CONTRACT_DESC = "Contract upload right";

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbstatementManager;

    @Autowired
    NtisContractsDBService ntisContractsDBService;

    @Autowired
    NtisCommonMethods ntisCommonMethods;

    @Override
    public String getFormName() {
        return "NTIS_CONTRACTS_SEARCH";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "NTIS prašymų ir sutarčių sąrašas", "NTIS prašymų ir sutarčių sąrašas");
        addFormActions(FormBase.ACTION_READ);
        addFormAction(NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS, NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS_DESC,
                NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS);
        addFormAction(NtisCommonActionsConstants.INTS_OWNER_ACTIONS, NtisCommonActionsConstants.INTS_OWNER_ACTIONS_DESC,
                NtisCommonActionsConstants.INTS_OWNER_ACTIONS);
        addFormAction(UPLOAD_CONTRACT, UPLOAD_CONTRACT_DESC, UPLOAD_CONTRACT);
    }

    public String getRecList(Connection conn, SelectRequestParams params, String lang, Double usrId, Double personId, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisContractsList.ACTION_READ);
        if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.INTS_OWNER_ACTIONS)
                && this.isFormActionAssigned(conn, NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS)) {
            throw new Exception("Both, INTS owner actions and Service provider actions cannot be assigned to the same user.");
        }
        StatementAndParams stmt = new StatementAndParams();
        String stmtStr = """
                with service_item_type as (select RSR.RFC_CODE,
                                   COALESCE(RSR.RFT_DISPLAY_CODE, RSR.RFC_MEANING) RFC_DISPLAY,
                                   RSR.RFC_DATE_FROM,
                                   RSR.RFC_DATE_TO
                              from SPR_REF_CODES_VW RSR
                             where RSR.RFC_DOMAIN = 'NTIS_SRV_ITEM_TYPE'
                               and COALESCE(RSR.RFT_LANG, ?) = ?),
                  contract_state as (select RSR.RFC_CODE,
                                   COALESCE(RSR.RFT_DISPLAY_CODE, RSR.RFC_MEANING) RFC_DISPLAY,
                                   RSR.RFC_DATE_FROM,
                                   RSR.RFC_DATE_TO
                              from SPR_REF_CODES_VW RSR
                             where RSR.RFC_DOMAIN = 'NTIS_COT_STATE'
                               and COALESCE(RSR.RFT_LANG, ?) = ?),
                  contract_method as (select RSR.RFC_CODE,
                                   COALESCE(RSR.RFT_DISPLAY_CODE, RSR.RFC_MEANING) RFC_DISPLAY,
                                   RSR.RFC_DATE_FROM,
                                   RSR.RFC_DATE_TO
                              from SPR_REF_CODES_VW RSR
                             where RSR.RFC_DOMAIN = 'NTIS_COT_METHOD'
                               and COALESCE(RSR.RFT_LANG, ?) = ?)
                select T.COT_ID,
                       T.COT_CODE,
                       TO_CHAR(T.COT_CREATED, '%s') AS COT_CREATED,
                       T.COT_SERVICES_DESC AS COT_SERVICES,
                       T.COT_SERVICES_JSON,
                       T.COT_SERVICE_PROVIDER,
                       T.COT_CUSTOMER,
                       T.COT_PER_ID,
                       T.COT_ORG_ID,
                       T.COT_WTF_ID,
                       T.COT_STATE,
                       T.COT_STATE_MEANING,
                       T.COT_METHOD,
                       T.COT_CREATED_IN_NTIS_PORTAL AS COT_METHOD_CODE
                  from (SELECT COT.COT_ID,
                       COT.COT_CODE,
                       COT.COT_CREATED AS COT_CREATED,
                       TO_CHAR(COT.COT_CREATED, '%s') AS COT_CREATED_CHR,
                       STRING_AGG(RSR.RFC_DISPLAY, ', ') AS COT_SERVICES_DESC,
                       STRING_AGG(SRV.SRV_TYPE, ', ') AS COT_SERVICES,
                        COT.C01 AS COT_SERVICES_JSON,
                       COALESCE(SPO.ORG_NAME, SPO1.ORG_NAME) AS COT_SERVICE_PROVIDER,
                       COALESCE(ORG.ORG_NAME, PER.PER_NAME || ' ' || PER.PER_SURNAME) AS COT_CUSTOMER,
                       COT.COT_PER_ID,
                       COT.COT_ORG_ID,
                       COT.COT_WTF_ID,
                       COT.COT_STATE,
                       RST.RFC_DISPLAY AS COT_STATE_MEANING,
                       COT.COT_CREATED_IN_NTIS_PORTAL,
                       RM.RFC_DISPLAY AS COT_METHOD
                  FROM NTIS_CONTRACTS AS COT
                  LEFT JOIN NTIS_CONTRACT_SERVICES AS CS ON CS.CS_COT_ID = COT_ID
                  LEFT JOIN NTIS_SERVICES AS SRV ON SRV.SRV_ID = CS.CS_SRV_ID
                  LEFT JOIN service_item_type AS RSR ON RSR.RFC_CODE = SRV.SRV_TYPE
                                                   and COT.COT_CREATED between RSR.RFC_DATE_FROM and COALESCE(RSR.RFC_DATE_TO, CURRENT_TIMESTAMP)
                  LEFT JOIN contract_state RST ON RST.RFC_CODE = COT.COT_STATE
                  LEFT JOIN contract_method RM  ON RM.RFC_CODE = COT.COT_CREATED_IN_NTIS_PORTAL
                  LEFT JOIN SPR_ORGANIZATIONS AS ORG ON COT.COT_ORG_ID = ORG.ORG_ID
                  LEFT JOIN SPR_ORGANIZATIONS AS SPO ON SRV.SRV_ORG_ID = SPO.ORG_ID
                        LEFT JOIN SPR_ORGANIZATIONS AS SPO1 ON COT.N01 = SPO1.ORG_ID
                  LEFT JOIN SPR_PERSONS AS PER ON COT.COT_PER_ID = PER.PER_ID
                """.formatted(dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB),
                dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB));
        stmt.addSelectParam(lang);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(lang);
        if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.INTS_OWNER_ACTIONS)) {
            if (orgId != null) {
                stmtStr = stmtStr + " WHERE COT.COT_ORG_ID = ? ";
                stmt.addSelectParam(orgId);
            } else if (personId != null) {
                stmtStr = stmtStr + " WHERE COT.COT_PER_ID = ? ";
                stmt.addSelectParam(personId);

            }
        } else {
            if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS)) {
                stmtStr = stmtStr + " WHERE (SPO.ORG_ID = ? OR SPO1.ORG_ID = ?) ";
                stmt.addSelectParam(orgId);
                stmt.addSelectParam(orgId);
            }
        }
        stmtStr = stmtStr + """
                group by COT.COT_ID,
                         COT.COT_CODE,
                         COT.COT_CREATED,
                         COALESCE(ORG.ORG_NAME, PER.PER_NAME || ' ' || PER.PER_SURNAME),
                         SPO.ORG_NAME,
                                SPO1.ORG_NAME,
                         COT.COT_PER_ID,
                         COT.COT_ORG_ID,
                         COT.COT_WTF_ID,
                         COT.COT_STATE,
                         RST.RFC_DISPLAY,
                         COT.COT_CREATED_IN_NTIS_PORTAL,
                         RM.RFC_DISPLAY) T
                        """;
        stmt.setStatement(stmtStr);
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), usrId, stmt, advancedParamList);
        HashMap<String, String> paramList = params.getParamList();

        stmt.addParam4WherePart("T.COT_WTF_ID", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("cot_wtf_id"));
        stmt.addParam4WherePart("T.COT_CODE", StatementAndParams.PARAM_STRING, advancedParamList.get("cot_code"));
        stmt.addParam4WherePart("T.COT_SERVICE_PROVIDER", StatementAndParams.PARAM_STRING, advancedParamList.get("cot_service_provider"));
        stmt.addParam4WherePart("T.COT_CUSTOMER", StatementAndParams.PARAM_STRING, advancedParamList.get("cot_customer"));
        stmt.addParam4WherePart("T.COT_CREATED_IN_NTIS_PORTAL = ? ", paramList.get("p_cot_method"));
        stmt.addParam4WherePart("T.COT_STATE = ? ", paramList.get("p_cot_state"));
        stmt.addParam4WherePart("T.COT_SERVICES like '%'||?||'%' ", paramList.get("p_cot_services"));
        stmt.addParam4WherePart("COT_CREATED", StatementAndParams.PARAM_DATE, advancedParamList.get("cot_created"),
                dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(dbstatementManager.colNamesToConcatString("T.COT_CODE", "T.COT_CREATED_CHR", "T.COT_SERVICES_DESC", "T.COT_SERVICE_PROVIDER",
                "T.COT_CUSTOMER", "T.COT_PER_ID", "T.COT_STATE_MEANING", "T.COT_METHOD"), StatementAndParams.PARAM_STRING,
                advancedParamList.get("quickSearch"));

        stmt.setStatementOrderPart("order by T.cot_created desc, T.cot_id desc");
        NtisContractsListSecurityManager sqm = new NtisContractsListSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { NtisContractsList.ACTION_READ };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    /**
     * Metodas grąžins įmonių sąrašą pagal nurodytą filtro parametrą
     * @param conn - prisijungimas prie DB
     * @param foreignKeyParams - filtravimo parametras
     * @return JSON objektas
     * @throws Exception
     */
    public String findServiceProviders(Connection conn, ForeignKeyParams foreignKeyParam) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                      SELECT org_id,
                             org_name,
                             org_code
                      FROM spr_organizations org
                INNER JOIN ntis_services on srv_org_id = org_id
                           and current_date between srv_date_from and coalesce(srv_date_to, current_date)
                     WHERE upper(org_name||' '||org_code) like '%'||upper(?)||'%'
                                         and org.c01 in ('PASLAUG', 'PASLAUG_VANDEN') """);
        stmt.addSelectParam(foreignKeyParam.getFilterValue());
        stmt.setStatementOrderPart("org_name");
        stmt.setStatementGroupPart("group by org_id");
        stmt.setRecordCountLimitPart(dbstatementManager.getRecordLimitString(foreignKeyParam.getRecordCount()));
        return queryController.selectQueryAsJSON(conn, stmt);
    }

    /**
     * Metodas grąžins true, jeigu įmonė yra registruotas paslaugų teikėjas ir turi užregistruotų paslaugų, ir false, jeigu ne. 
     * @param conn - prisijungimas prie DB
     * @param orgId - organizacijos id
     * @return JSON objektas
     * @throws Exception
     */
    public Boolean checkSpServices(Connection conn, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                      SELECT org_id,
                             org_name,
                             org_code
                      FROM spr_organizations org
                INNER JOIN ntis_services on srv_org_id = org_id
                           and current_date between srv_date_from and coalesce(srv_date_to, current_date)
                     WHERE org_id = ?
                       and org.c01 in ('PASLAUG', 'PASLAUG_VANDEN')
                       and org.n01 = 1
                       and srv_contract_available = 'Y'""");
        stmt.addSelectParam(orgId);
        List<SprOrganizationsDAO> queryResult = queryController.selectQueryAsObjectArrayList(conn, stmt, SprOrganizationsDAO.class);
        return (queryResult != null && !queryResult.isEmpty());
    }
}