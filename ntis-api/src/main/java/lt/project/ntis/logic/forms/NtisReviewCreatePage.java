package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;

import lt.project.ntis.dao.NtisReviewsDAO;

import lt.project.ntis.logic.forms.model.NtisReviewCreationModel;
import lt.project.ntis.logic.forms.model.NtisSludgeDeliveryDetails;
import lt.project.ntis.service.NtisReviewsDBService;

@Component
public class NtisReviewCreatePage extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(NtisReviewCreatePage.class);

    private final NtisReviewsDBService ntisReviewsDBService;

    private final BaseControllerJDBC baseControllerJDBC;

    private final DBStatementManager dbStatementManager;

    public NtisReviewCreatePage(NtisReviewsDBService ntisReviewsDBService, BaseControllerJDBC baseControllerJDBC, DBStatementManager dbStatementManager) {
        super();
        this.ntisReviewsDBService = ntisReviewsDBService;
        this.baseControllerJDBC = baseControllerJDBC;
        this.dbStatementManager = dbStatementManager;

    }

    @Override
    public String getFormName() {
        return "NTIS_REVIEW_CREATE";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Atsiliepimo sukūrimo forma", "Atsiliepimo sukūrimo forma");
        this.addFormActions(ACTION_CREATE, ACTION_READ);
    }

    public NtisReviewCreationModel getReview(Connection conn, String id, Double orgId, Double usrId, String lang) throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, NtisReviewCreatePage.ACTION_READ);
        NtisReviewCreationModel review = new NtisReviewCreationModel();
        NtisReviewsDAO ntisReviewsDAO = null;
        log.debug("Getting prepared review form: " + id);
        ntisReviewsDAO = ntisReviewsDBService.loadRecord(conn, Utils.getDouble(id));
        if (ntisReviewsDAO != null && ntisReviewsDAO.getRev_usr_id() != null && usrId.compareTo(ntisReviewsDAO.getRev_usr_id()) == 0) {
            if (ntisReviewsDAO.getRev_score() != null) {
                throw new SparkBusinessException(new S2Message("common.error.reviewCreated", SparkMessageType.ERROR));
            }
            review.setReviewInfo(ntisReviewsDAO);

            StatementAndParams stmt = new StatementAndParams("""
                    select org_name as orgName,
                           ord_id as ordId,
                           coalesce(rfc_meaning, srv_type) as srvType,
                           to_char(ocw_completed_date, '%s') as ordDate
                           from ntis_reviews
                           left join ntis_orders on ord_id = rev_ord_id
                           left join ntis_services on srv_id = ord_srv_id
                           left join spr_organizations on rev_pasl_org_id = org_id
                           left join ntis_order_completed_works on ocw_ord_id = ord_id
                           left join spr_ref_codes_vw on srv_type = rfc_code and rfc_domain = 'NTIS_SRV_ITEM_TYPE' and rft_lang = ?
                           where rev_id = ?::int
                    """.formatted(dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)));
            stmt.addSelectParam(lang);
            stmt.addSelectParam(ntisReviewsDAO.getRev_id());
            List<NtisReviewCreationModel> queryResult = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisReviewCreationModel.class);
            if (queryResult != null && !queryResult.isEmpty()) {
                review = queryResult.get(0);
                review.setReviewInfo(ntisReviewsDAO);
            }
            return review;
        } else {
            throw new SparkBusinessException(new S2Message("common.error.action_not_granted", SparkMessageType.ERROR));
        }
    }

    public NtisReviewsDAO save(Connection conn, NtisReviewsDAO record, Double usrId) throws Exception {
        NtisReviewsDAO reviewRecord = ntisReviewsDBService.loadRecord(conn, record.getRev_id());
        if (reviewRecord != null && reviewRecord.getRev_usr_id() != null && reviewRecord.getRev_usr_id().compareTo(usrId) == 0
                && record.getRev_score() != null) {
            this.checkIsFormActionAssigned(conn, NtisReviewCreatePage.ACTION_CREATE);
            record.setRev_completed_date(new Date());
            log.debug("Saving completed review " + record.getRev_id());
            return ntisReviewsDBService.saveRecord(conn, record);
        } else {
            throw new SparkBusinessException(new S2Message("common.error.action_not_granted", SparkMessageType.ERROR));
        }
    }
}
