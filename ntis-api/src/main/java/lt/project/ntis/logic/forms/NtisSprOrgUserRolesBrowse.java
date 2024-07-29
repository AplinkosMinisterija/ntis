package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.logic.forms.SprOrgUserRolesBrowse;
import eu.itreegroup.spark.modules.admin.logic.forms.SprUserRolesBrowse;
import eu.itreegroup.spark.modules.admin.logic.forms.security.SprUserRolesBrowseSecurityManager;
import eu.itreegroup.spark.modules.admin.service.SprOrgUserRolesDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBService;
import lt.project.ntis.constants.NtisRolesConstants;

@Component
public class NtisSprOrgUserRolesBrowse extends SprOrgUserRolesBrowse {

    public NtisSprOrgUserRolesBrowse(BaseControllerJDBC queryController, SprOrgUserRolesDBService sprOrgUserRolesDBService,
            DBStatementManager dbstatementManager, SprOrgUsersDBService sprOrgUserDBService) {
        super(queryController, sprOrgUserRolesDBService, dbstatementManager, sprOrgUserDBService);
    }

    private static final Logger log = LoggerFactory.getLogger(NtisSprOrgUserRolesBrowse.class);

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Override
    public String getRecList(Connection conn, SelectRequestParams params, Double userId, String lang) throws Exception {
        log.debug("Start extract information from db");
        this.checkIsFormActionAssigned(conn, SprOrgUserRolesBrowse.ACTION_READ);

        String conditionalStatement = "";

        if (!hasUserRole(conn, NtisRolesConstants.ADMIN)) {
            conditionalStatement = """
                        where rol_code not in (?)
                    """;
        }
        StatementAndParams stmt = new StatementAndParams("""
                   SELECT our.our_id,
                          to_char(our_date_from, '%s') as our_date_from,
                          to_char(our_date_to, '%s') as our_date_to,
                          ou.ou_usr_id as our_usr_id,
                          sr.rol_id,
                          ou.ou_id,
                          sr.rol_code,
                          coalesce(typ.rft_display_code, sr.rol_type) as rol_type,
                          sr.rol_name,
                          sr.rol_description
                     FROM SPR_ROLES sr
                     JOIN SPR_ORG_AVAILABLE_ROLES oar on sr.rol_id = oar.oar_rol_id and now() between oar.oar_date_from and coalesce(oar.oar_date_to, now())
                     JOIN SPR_ORG_USERS ou on ou.ou_org_id = oar.oar_org_id and ou.ou_id = ?::int
                LEFT JOIN SPR_ORG_USER_ROLES our on sr.rol_id = our.our_rol_id and our.our_ou_id = ou.ou_id
                LEFT JOIN spr_ref_codes_vw typ on typ.rfc_code = sr.rol_type and typ.rfc_domain = 'SPR_ROLE_TYPE' and typ.rft_lang = ?
                """.formatted(dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)));
        stmt.setStatement(stmt.getStatement() + conditionalStatement);
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);
        HashMap<String, String> paramList = params.getParamList();

        stmt.addSelectParam(Utils.getDouble(paramList.get("p_ou_id")));
        if (!hasUserRole(conn, NtisRolesConstants.ADMIN)) {
            stmt.addSelectParam(NtisRolesConstants.INTS_OWNER);
            stmt.setWhereExists(true);
        }
        stmt.addSelectParam(lang);
        stmt.addParam4WherePart("ROL_ID", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("rol_id"));
        stmt.addParam4WherePart("ROL_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("rol_name"));
        stmt.addParam4WherePart("ROL_CODE", StatementAndParams.PARAM_STRING, advancedParamList.get("rol_code"));
        stmt.addParam4WherePart("ROL_TYPE", StatementAndParams.PARAM_STRING, advancedParamList.get("rol_type"));
        stmt.addParam4WherePart("ROL_DESCRIPTION", StatementAndParams.PARAM_STRING, advancedParamList.get("rol_description"));
        stmt.addParam4WherePart(dbStatementManager.getTruncatedColumnCommand("URO_DATE_FROM"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("uro_date_from"), dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(dbStatementManager.getTruncatedColumnCommand("URO_DATE_TO"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("uro_date_to"), dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(
                dbStatementManager.colNamesToConcatString("ROL_ID", "ROL_NAME", "ROL_CODE", "ROL_TYPE", "ROL_DESCRIPTION",
                        "TO_CHAR(OUR_DATE_FROM, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA) + "')",
                        "TO_CHAR(OUR_DATE_TO, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        SprUserRolesBrowseSecurityManager sqm = new SprUserRolesBrowseSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { SprUserRolesBrowse.ACTION_READ, SprUserRolesBrowse.ACTION_UPDATE };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt, params, sqm);
    }

}
