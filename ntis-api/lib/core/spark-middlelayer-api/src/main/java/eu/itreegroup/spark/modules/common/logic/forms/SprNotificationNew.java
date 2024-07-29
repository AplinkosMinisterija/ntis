package eu.itreegroup.spark.modules.common.logic.forms;

import java.sql.Connection;
import java.util.List;

import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.common.rest.model.NotificationRecipient;
import eu.itreegroup.spark.modules.common.rest.model.NtfRecipientsSearchReq;

@Component
public class SprNotificationNew extends FormBase {

    private final BaseControllerJDBC queryController;

    private final DBStatementManager dbStatementManager;

    public SprNotificationNew(DBStatementManager dbStatementManager, BaseControllerJDBC queryController) {
        super();
        this.queryController = queryController;
        this.dbStatementManager = dbStatementManager;
    }

    @Override
    public String getFormName() {
        return "SPR_NOTIFICATION_NEW";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark notification new", "Spark notification new");
        addFormAction(FormBase.ACTION_READ, FormBase.ACTION_READ_DESC, FormBase.ACTION_READ_DESC);
    }

    public List<NotificationRecipient> findRecipients(Connection conn, NtfRecipientsSearchReq searchRequest, Double senderUsrId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT usr_id AS usrId, " //
                + " usr_person_name || ' ' || usr_person_surname AS name, " //
                + " usr_org_id AS orgId, " //
                + " usr_rol_id AS rolId" //
                + " FROM spr_users "//
                + " WHERE usr_id <> ? " //
        );
        stmt.addSelectParam(senderUsrId);
        stmt.setWhereExists(true);
        stmt.addParam4WherePart("upper(" + dbStatementManager.colNamesToConcatString("usr_person_name", "usr_person_surname") + ") like '%'||?||'%'",
                searchRequest.getForeignKeyParams().getFilterValueModified4Search());
        if (!searchRequest.getSelectedIds().isEmpty()) {
            for (Double id : searchRequest.getSelectedIds()) {
                stmt.addParam4WherePart("usr_id != ?", id);
            }
        }
        stmt.setStatementOrderPart(dbStatementManager.colNamesToConcatString("usr_person_name", "usr_person_surname"));
        stmt.setRecordCountLimitPart(dbStatementManager.getRecordLimitString(searchRequest.getForeignKeyParams().getRecordCount()));
        return queryController.selectQueryAsObjectArrayList(conn, stmt, NotificationRecipient.class);

    }

}