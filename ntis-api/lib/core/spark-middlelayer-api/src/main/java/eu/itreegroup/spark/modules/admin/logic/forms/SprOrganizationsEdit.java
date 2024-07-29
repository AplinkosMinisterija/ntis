package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2ViolatedConstraint;
import eu.itreegroup.s2.server.jwt.BackendUserSession;
import eu.itreegroup.s2.server.rest.S2RestRequestContext;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

@Component
public class SprOrganizationsEdit extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprOrganizationsEdit.class);

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    protected S2RestRequestContext<BackendUserSession> requestContext;

    @Autowired
    SprOrganizationsDBService sprOrganizationsDBService;

    @Autowired
    DBStatementManager dbStatementManager;

    @Override
    public String getFormName() {
        return "SPR_ORGS_EDIT";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark organizations edit", "Spark organizations edit");
        addFormActionCRUD();
    }

    public SprOrganizationsDAO get(Connection conn, RecordIdentifier recordIdentifier) throws NumberFormatException, Exception {
        SprOrganizationsDAO answerObj = null;
        if ("new".equalsIgnoreCase(recordIdentifier.getId())) {
            log.debug("Will create new record");
            this.checkIsFormActionAssigned(conn, SprOrganizationsBrowse.ACTION_CREATE);
            answerObj = sprOrganizationsDBService.newRecord();
            answerObj.setFormActions(getFormActions(conn));
        } else {
            log.debug("Will return record by id: " + recordIdentifier.getId());
            this.checkIsFormActionAssigned(conn, SprOrganizationsBrowse.ACTION_READ);
            answerObj = sprOrganizationsDBService.loadRecord(conn, Double.parseDouble(recordIdentifier.getId()));
            answerObj.setFormActions(getFormActions(conn));
            if (ACTION_COPY.equalsIgnoreCase(recordIdentifier.getActionType())) {
                answerObj.setOrg_id(null);
                answerObj.setRec_create_timestamp(null);
                answerObj.setRec_timestamp(null);
                answerObj.setRec_userid(null);
                answerObj.setRec_version(null);
            }
        }
        return answerObj;
    }

    public SprOrganizationsDAO save(Connection conn, SprOrganizationsDAO record) throws Exception {
        this.checkIsFormActionAssigned(conn,
                record.getRecordIdentifier() == null ? SprOrganizationsBrowse.ACTION_CREATE : SprOrganizationsBrowse.ACTION_UPDATE);
        return sprOrganizationsDBService.saveRecord(conn, record);
    }

    public void delete(Connection conn, RecordIdentifier recordIdentifier) throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, SprOrganizationsBrowse.ACTION_DELETE);
        try {
            sprOrganizationsDBService.deleteRecord(conn, Double.parseDouble(recordIdentifier.getId()));
        } catch (SQLIntegrityConstraintViolationException e) {
            StatementAndParams stmt = new StatementAndParams();
            stmt.setStatement(" SELECT ORG_ID FROM SPR_ORGANIZATIONS WHERE ORG_ORG_ID=? ");
            stmt.addSelectParam(recordIdentifier.getId());
            List<HashMap<String, String>> childOrg = queryController.selectQueryAsDataArrayList(conn, stmt);
            for (var co : childOrg) {
                RecordIdentifier ri = new RecordIdentifier();
                ri.setId(co.get("org_id"));
                this.delete(conn, ri);
            }
            sprOrganizationsDBService.deleteRecord(conn, Double.parseDouble(recordIdentifier.getId()));
        }
    }

    /**
     * Function will return organizations that can be assigned as parent.
     * @param conn - connection to the db
     * @param orgIdStr - organization identifier, for which will be added parent organization
     * @param orgName - parent organization name fragment
     * @return list of organizations candidates for parent.
     * @throws Exception
     */
    public String getParentOrganization(Connection conn, String orgIdStr, String orgName) throws Exception {
        Double orgId = 0d;
        if ("new".equalsIgnoreCase(orgIdStr)) {
            log.debug("Will create for new record");
            this.checkIsFormActionAssigned(conn, SprOrganizationsBrowse.ACTION_CREATE);
        } else {
            log.debug("Will return for update record by id: " + orgIdStr);
            this.checkIsFormActionAssigned(conn, SprOrganizationsBrowse.ACTION_UPDATE);
            if (orgIdStr != null) {
                orgId = Double.parseDouble(orgIdStr);
            }
        }
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(dbStatementManager.getStatementByName("getNonChildrenOrgs"));
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(orgName);
        return queryController.selectQueryAsJSON(conn, stmt);
    }

    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, SprOrganizationsDAO record) throws SparkBusinessException, Exception {
        this.checkIsFormActionAssigned(conn, record.getRecordIdentifier() == null ? FormBase.ACTION_CREATE : FormBase.ACTION_UPDATE);
        return sprOrganizationsDBService.getViolatedConstraints(conn, record, null);
    }
}
