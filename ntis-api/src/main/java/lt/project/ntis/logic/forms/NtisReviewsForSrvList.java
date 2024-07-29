package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.YesNo;
import lt.project.ntis.dao.NtisReviewsDAO;
import lt.project.ntis.logic.forms.security.NtisReviewsForSrvListSecurityManager;
import lt.project.ntis.service.NtisReviewsDBService;

@Component
public class NtisReviewsForSrvList extends FormBase {

    private final BaseControllerJDBC baseControllerJDBC;

    private final DBStatementManager dbStatementManager;

    private final NtisReviewsDBService ntisReviewsDBService;

    public NtisReviewsForSrvList(BaseControllerJDBC baseControllerJDBC, DBStatementManager dbStatementManager, NtisReviewsDBService ntisReviewsDBService) {
        super();
        this.baseControllerJDBC = baseControllerJDBC;
        this.dbStatementManager = dbStatementManager;
        this.ntisReviewsDBService = ntisReviewsDBService;
    }

    @Override
    public String getFormName() {
        return "NTIS_REVIEWS_FOR_SRV_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Atsiliepimų sąrašo forma paslaugų teikėjams", "Atsiliepimų sąrašo forma paslaugų teikėjams");
        this.addFormActions(ACTION_UPDATE, ACTION_READ);
    }

    public String getReviewsList(Connection conn, SelectRequestParams params, Double orgId, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams("""
                select rev_id,
                       rev_score,
                       rev_comment,
                       rev_receiver_read
                  from ntis_reviews
                 where rev_score is not null and rev_completed_date is not null and rev_pasl_org_id = ?::int
                 """);
        stmt.setWhereExists(true);
        stmt.addSelectParam(orgId);
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), usrId, stmt, advancedParamList);
        stmt.addParam4WherePart("rev_score", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("rev_score"));
        stmt.addParam4WherePart("rev_comment", StatementAndParams.PARAM_STRING, advancedParamList.get("rev_comment"));
        stmt.addParam4WherePart("rev_receiver_read", StatementAndParams.PARAM_STRING, advancedParamList.get("rev_receiver_read"));

        stmt.addParam4WherePart(dbStatementManager.colNamesToConcatString("rev_score", "rev_comment"), StatementAndParams.PARAM_STRING,
                advancedParamList.get("quickSearch"));

        NtisReviewsForSrvListSecurityManager sqm = new NtisReviewsForSrvListSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { NtisReviewsForAdminList.ACTION_READ, NtisReviewsForAdminList.ACTION_UPDATE };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    public void markReviewAsRead(Connection conn, String id) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_UPDATE);
        NtisReviewsDAO reviewDAO = ntisReviewsDBService.loadRecord(conn, Utils.getDouble(id));
        reviewDAO.setRev_receiver_read(YesNo.Y.getCode());
        ntisReviewsDBService.saveRecord(conn, reviewDAO);
    }

}
