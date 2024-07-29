package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2ViolatedConstraint;
import eu.itreegroup.s2.server.jwt.BackendUserSession;
import eu.itreegroup.s2.server.rest.S2RestRequestContext;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.ForeignKeyParams;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprOrgUsersDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBService;
import eu.itreegroup.spark.modules.admin.service.SprUsersDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

@Component
public class SprUsersEdit extends FormBase {

    public static final String NO_LIMIT_ON_ORG_LEVEL = "NO_LIMIT_ON_ORG_LEVEL";

    public static final String NO_LIMIT_ON_ORG_LEVEL_NAME = "No limitation on user organization level";

    private static final Logger log = LoggerFactory.getLogger(SprUsersEdit.class);

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    protected S2RestRequestContext<BackendUserSession> requestContext;

    @Autowired
    SprUsersDBService sprUsersDBService;

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    SprOrgUsersDBService sprOrgUsersDBService;

    @Override
    public String getFormName() {
        return "SPR_USERS_EDIT";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark users edit", "Spark users edit");
        addFormActionCRUD();
        addFormAction(NO_LIMIT_ON_ORG_LEVEL, NO_LIMIT_ON_ORG_LEVEL_NAME, NO_LIMIT_ON_ORG_LEVEL_NAME);
    }

    public SprUsersDAO get(Connection conn, RecordIdentifier recordIdentifier, Double orgId) throws NumberFormatException, Exception {
        SprUsersDAO answerObj = null;
        if ("new".equalsIgnoreCase(recordIdentifier.getId()) || "new".equalsIgnoreCase(recordIdentifier.getActionType())) {
            log.debug("Will create new record");
            this.checkIsFormActionAssigned(conn, FormBase.ACTION_CREATE);
            answerObj = sprUsersDBService.newRecord();
            answerObj.setFormActions(getFormActions(conn));
        } else {
            log.debug("Will return record by id: " + recordIdentifier.getId());
            this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
            if (this.isFormActionAssigned(conn, NO_LIMIT_ON_ORG_LEVEL)) {
                answerObj = sprUsersDBService.loadRecord(conn, Double.parseDouble(recordIdentifier.getId()));
            } else {
                SprOrgUsersDAO sprOrgUsersDAO = sprOrgUsersDBService.loadRecordByParams(conn, "  ou_usr_id = ? and ou_org_id = ?",
                        new SelectParamValue(Double.parseDouble(recordIdentifier.getId())), new SelectParamValue(orgId));
                if (sprOrgUsersDAO != null) {
                    answerObj = sprUsersDBService.loadRecord(conn, Double.parseDouble(recordIdentifier.getId()));
                }
            }
            answerObj.setFormActions(getFormActions(conn));
            if (ACTION_COPY.equalsIgnoreCase(recordIdentifier.getActionType())) {
                answerObj.setUsr_id(null);
                answerObj.setRec_create_timestamp(null);
                answerObj.setRec_timestamp(null);
                answerObj.setRec_userid(null);
                answerObj.setRec_version(null);
            }
            answerObj.hideSystemData();
        }
        return answerObj;
    }

    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, SprUsersDAO record) throws SparkBusinessException, Exception {
        this.checkIsFormActionAssigned(conn, record.getRecordIdentifier() == null ? FormBase.ACTION_CREATE : FormBase.ACTION_UPDATE);
        return sprUsersDBService.getViolatedConstraints(conn, record, null);
    }

    public SprUsersDAO save(Connection conn, SprUsersDAO record, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, record.getRecordIdentifier() == null ? FormBase.ACTION_CREATE : FormBase.ACTION_UPDATE);
        if (record.getRecordIdentifier() == null) {
            if (record.getUsr_date_from() == null) {
                record.setUsr_date_from(new Date());
            }
            if (record.getUsr_email_confirmed() == null) {
                record.setUsr_email_confirmed("N");
            }
        } else {
            record = sprUsersDBService.restoreSystemData(conn, record);
        }
        record = sprUsersDBService.saveRecord(conn, record);
        boolean manageOrganizations = false;
        SprOrgUsersDAO sprOrgUsersDAO = null;
        if (this.isFormActionAssigned(conn, NO_LIMIT_ON_ORG_LEVEL)) {
            if (record.getUsr_org_id() != null) {
                sprOrgUsersDAO = sprOrgUsersDBService.loadRecordByParams(conn, "  ou_usr_id = ? and ou_org_id = ?", new SelectParamValue(record.getUsr_id()),
                        new SelectParamValue(record.getUsr_org_id()));
                manageOrganizations = true;
            }
        } else {
            sprOrgUsersDAO = sprOrgUsersDBService.loadRecordByParams(conn, "  ou_usr_id = ? and ou_org_id = ?", new SelectParamValue(record.getUsr_id()),
                    new SelectParamValue(orgId));
            manageOrganizations = true;
        }
        if (manageOrganizations && sprOrgUsersDAO == null) {
            sprOrgUsersDAO = sprOrgUsersDBService.newRecord();
            sprOrgUsersDAO.setOu_usr_id(record.getUsr_id());
            if (this.isFormActionAssigned(conn, NO_LIMIT_ON_ORG_LEVEL)) {
                sprOrgUsersDAO.setOu_org_id(record.getUsr_org_id());
            } else {
                sprOrgUsersDAO.setOu_org_id(orgId);
            }
            sprOrgUsersDAO.setOu_date_from(Utils.getDate());
            sprOrgUsersDAO.setOu_date_to(null);
            sprOrgUsersDBService.saveRecord(conn, sprOrgUsersDAO);
        }
        return record;
    }

    public void delete(Connection conn, RecordIdentifier recordIdentifier) throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_DELETE);
        sprUsersDBService.deleteRecord(conn, Double.parseDouble(recordIdentifier.getId()));
    }

    public String getOrganizations(Connection conn) throws Exception {
        log.debug("Start extract information from db");
        this.checkIsFormActionAssigned(conn, SprUsersEdit.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT ORG_ID, ORG_NAME, ORG_CODE " + "FROM SPR_ORGANIZATIONS");
        return queryController.selectQueryAsJSON(conn, stmt);
    }

    public String findPersons(Connection conn, ForeignKeyParams foreignKeyParam) throws Exception {
        log.debug("Start extract person information from db");
        this.checkIsFormActionAssigned(conn, SprUsersEdit.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT t1.per_name, t1.per_surname, t1.per_id, t1.per_email FROM spr_persons t1");
        stmt.addParam4WherePart("upper(" + dbStatementManager.colNamesToConcatString("per_name", "per_surname") + ") like '%'||?||'%'",
                foreignKeyParam.getFilterValueModified4Search());
        stmt.setStatementOrderPart(dbStatementManager.colNamesToConcatString("per_name", "per_surname"));
        stmt.setRecordCountLimitPart(dbStatementManager.getRecordLimitString(foreignKeyParam.getRecordCount()));
        return queryController.selectQueryAsJSON(conn, stmt);
    }
}
