package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.SprAuthorization;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.app.tools.NotificationManager;
import eu.itreegroup.spark.constants.DbConstants;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.service.SprTemplatesDBService;
import eu.itreegroup.spark.modules.admin.service.SprUsersDBService;
import eu.itreegroup.spark.modules.common.dao.SprNotificationsDAO;
import eu.itreegroup.spark.modules.common.service.SprNotificationsDBServiceImpl;
import lt.project.ntis.logic.forms.security.NtisNotificationsListSecurityManager;

/**
 * Klasė skirta formos "Gauti pranešimai" (P2100, P2180, T3030, T3040, S1200, S1240) biznio logikai apibrėžti
 */
@Component
public class NtisNotificationsList extends FormBase {

    public static final String READ = "read";

    public static final String IMPORTANT = "important";

    public static final String NOT_IMPORTANT = "not_important";

    public static final String DELETE = "delete";

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    SprNotificationsDBServiceImpl notificationsDBService;

    @Autowired
    SprUsersDBService usersDBService;

    @Autowired
    SprTemplatesDBService sprTemplatesDBService;

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    NotificationManager notificationManager;

    @Autowired
    SprAuthorization<?> sprAuthorization;

    @Override
    public String getFormName() {
        return "NTIS_NOTIFICATIONS_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Gautų pranešimų sąrašas", "Gautų pranešimų sąrašo forma");
        addFormActions(ACTION_READ, ACTION_UPDATE, ACTION_DELETE);
    }

    /**
     * Metodas grąžins pranešimų sąrašą, pagal sesijos naudotojo id, jo organizacijos ir jo rolės id bei pateikiamus paieškos parametrus
     * @param conn - prisijungimas prie DB
     * @param params - paieškos ir filtravimo parametrai
     * @param userId - sesijos naudotojo id
     * @param orgId - sesijos organizacijos id
     * @param rolId - sesijos rolės id
     * @param language - kalba
     * @return pranešimų sąrašas, json objektas
     * @throws Exception
     */
    public String getNotificationsList(Connection conn, SelectRequestParams params, Double userId, Double orgId, Double rolId, String language)
            throws Exception {
        checkIsFormActionAssigned(conn, NtisNotificationsList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT ntf_id,
                        ntf_title,
                        ntf_message,
                        ntf_usr_id,
                        ntf_org_id,
                        ntf_rol_id,
                        case when ntf_mark_as_read_date is null then 'Y' else 'N' end AS not_read,
                        case when n.d01 is not null then 'Y' else 'N' end AS is_important,
                        ntf_creation_date
                FROM spark.spr_notifications n
                WHERE %s
                AND ntf_usr_id = ?::int
                                """.formatted(dbStatementManager.getPeriodValidationForCurrentDateStr("ntf_date_from", "ntf_date_to", false)));
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);
        stmt.setWhereExists(true);
        if (orgId != null) {
            stmt.addParam4WherePart(" ntf_org_id = ?::int ", orgId);
        }
        stmt.addSelectParam(userId);
        if (advancedParamList.get("not_read") != null && advancedParamList.get("not_read").getParamValue().getValues().contains(DbConstants.BOOLEAN_TRUE)
                && !advancedParamList.get("not_read").getParamValue().getValues().contains(DbConstants.BOOLEAN_FALSE)) {
            stmt.addCondition4WherePart(" ntf_mark_as_read_date is null ", " and ");
        }
        if (advancedParamList.get("not_read") != null && advancedParamList.get("not_read").getParamValue().getValues().contains(DbConstants.BOOLEAN_FALSE)
                && !advancedParamList.get("not_read").getParamValue().getValues().contains(DbConstants.BOOLEAN_TRUE)) {
            stmt.addCondition4WherePart(" ntf_mark_as_read_date is not null ", " and ");
        }

        if (advancedParamList.get("is_important") != null
                && advancedParamList.get("is_important").getParamValue().getValues().contains(DbConstants.BOOLEAN_TRUE)
                && !advancedParamList.get("is_important").getParamValue().getValues().contains(DbConstants.BOOLEAN_FALSE)) {
            stmt.addCondition4WherePart(" n.d01 is not null ", " and ");
        }
        if (advancedParamList.get("is_important") != null
                && advancedParamList.get("is_important").getParamValue().getValues().contains(DbConstants.BOOLEAN_FALSE)
                && !advancedParamList.get("is_important").getParamValue().getValues().contains(DbConstants.BOOLEAN_TRUE)) {
            stmt.addCondition4WherePart(" n.d01 is null ", " and ");
        }
        stmt.addParam4WherePart("ntf_title", StatementAndParams.PARAM_STRING, advancedParamList.get("ntf_title"));
        stmt.addParam4WherePart("ntf_message", StatementAndParams.PARAM_STRING, advancedParamList.get("ntf_message"));
        stmt.addParam4WherePart("date_trunc('second',ntf_creation_date)", StatementAndParams.PARAM_DATE_TIME, advancedParamList.get("ntf_creation_date"),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_JAVA));
        stmt.addParam4WherePart(
                dbStatementManager.colNamesToConcatString("ntf_title", "ntf_message",
                        "TO_CHAR(ntf_creation_date, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_DB) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        NtisNotificationsListSecurityManager sqm = new NtisNotificationsListSecurityManager();

        FormActions formActions = this.getFormActions(conn);

        String[] recordActionMenu = { NtisNotificationsList.ACTION_READ, NtisNotificationsList.ACTION_UPDATE, NtisNotificationsList.ACTION_DELETE };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        stmt.setStatementOrderPart(" ORDER BY ntf_creation_date DESC, ntf_id DESC ");
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    /**
     * Metodas pažymi pranešimą duomenų bazėje kaip perskaitytą, svarbų arba ištrintą, pagal tai, 
     * koks actionType (read, important, not_important, delete) nurodytas pateikiamame RecordIdentifier įraše. 
     * Pažymėti pranešimą reiškia nustatyti vieno iš datos laukų (ntf_mark_as_read_date, ntf_date_to, d01) reikšmę.
     * @param conn - prisijungimas prie DB
     * @param recordIdentifier - įrašas su norimo pažymėti pranešimo id ir pažymėjimo veiksmo reikšmėmis
     * @param userId - sesijos naudotojo id
     * @param orgId - sesijos organizacijos id
     * @throws Exception
     */
    public void markNotification(Connection conn, RecordIdentifier recordIdentifier, Double userId, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_UPDATE);
        SprNotificationsDAO notificationDAO = notificationsDBService.loadRecordByUsrAndOrgId(conn, recordIdentifier.getIdAsDouble(), userId, orgId);
        if (recordIdentifier.getActionType().equals(READ)) {
            notificationDAO.setNtf_mark_as_read_date(new Date());
        } else if (recordIdentifier.getActionType().equals(IMPORTANT)) {
            notificationDAO.setD01(new Date());
        } else if (recordIdentifier.getActionType().equals(NOT_IMPORTANT)) {
            notificationDAO.setD01(null);
        } else if (recordIdentifier.getActionType().equals(DELETE)) {
            this.checkIsFormActionAssigned(conn, FormBase.ACTION_DELETE);
            notificationDAO.setNtf_date_to(new Date());
        }
        notificationsDBService.saveRecord(conn, notificationDAO);
    }
}
