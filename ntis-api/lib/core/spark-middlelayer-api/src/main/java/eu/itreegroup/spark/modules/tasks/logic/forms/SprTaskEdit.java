package eu.itreegroup.spark.modules.tasks.logic.forms;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.server.jwt.BackendUserSession;
import eu.itreegroup.s2.server.rest.S2RestRequestContext;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.admin.service.SprPersonsDBService;
import eu.itreegroup.spark.modules.admin.service.SprUsersDBService;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.logic.forms.SprNotifications;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import eu.itreegroup.spark.modules.tasks.dao.SprTaskAssignmentsDAO;
import eu.itreegroup.spark.modules.tasks.dao.SprTaskFilesDAO;
import eu.itreegroup.spark.modules.tasks.dao.SprTasksDAO;
import eu.itreegroup.spark.modules.tasks.logic.forms.model.SprTask;
import eu.itreegroup.spark.modules.tasks.logic.forms.model.SprTaskEditModel;
import eu.itreegroup.spark.modules.tasks.logic.forms.model.SprTaskFileKey;
import eu.itreegroup.spark.modules.tasks.service.SprTaskAssignmentsDBService;
import eu.itreegroup.spark.modules.tasks.service.SprTaskFilesDBService;
import eu.itreegroup.spark.modules.tasks.service.SprTasksDBService;

@Component
public class SprTaskEdit extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprTaskEdit.class);

    /**
     * Reference to the properties DB service instance.
     */
    @Autowired
    protected S2RestRequestContext<BackendUserSession> requestContext;

    /**
     * Reference to the properties DB service instance.
     */
    @Autowired
    SprTasksDBService taskDBService;

    @Autowired
    SprPersonsDBService personsDBService;

    @Autowired
    SprTaskAssignmentsDBService sprTasksAssignmentsService;

    @Autowired
    SprTaskFilesDBService sprTaskFilesDBService;

    @Autowired
    SprFilesDBService sprFilesDBService;

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    SprNotifications sprNotifications;

    @Autowired
    SprUsersDBService usersDBService;

    /**
     * Method will return Angula form name. Same name should be defined in DB as
     * well (in case if enabled data synchronization with db it name will be used
     * for form definition in DB).
     */
    @Override
    public String getFormName() {
        return "SPR_TASK_EDIT";
    }

    /**
     * Function will define setup information that is related with current form. In
     * this form should be defined: 1) from name (for form definition should be used
     * method "setFormInfo") 2) form available actions (for action definition should
     * be used method "addFormAction"). In case of define standard CRUD form action
     * can be used method by name: addFormActionCRUD.
     */
    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark task edit", "Spark task edit");
        addFormActionCRUD();
    }

    /*
     * getTaskAssigments: The assigned persons are added to the main task performed
     * by join to get the user name
     */
    public SprTaskEditModel getTaskAssigments(Connection conn, SprTaskEditModel task) throws Exception {

        StatementAndParams stmt = new StatementAndParams();

        stmt.setStatement("SELECT t1.tat_id, "//
                + " t1.tat_usr_id "//
                + " FROM spr_task_assignments t1 "//
                + " WHERE tat_tas_id = ?");
        stmt.addSelectParam(task.getSprTask().getTas_id());

        List<HashMap<String, String>> assignObj = queryController.selectQueryAsDataArrayList(conn, stmt);
        List<String> taskAssignments = new ArrayList<String>();

        for (int i = 0; i < assignObj.size(); i++) {
            HashMap<String, String> row = assignObj.get(i);
            String val = new String();
            val = row.get("tat_usr_id");
            taskAssignments.add(val);
        }
        task.setAssigneesIds(taskAssignments);

        return task;
    }

    /*
     * loadTaskData: function retrieves data based on the task id, performed to join
     * the user table, and obtains the user name and surname.
     */
    public SprTaskEditModel loadTaskData(Connection conn, RecordIdentifier recordIdentifier) throws Exception {

        // Create new object, to fill data
        SprTaskEditModel taskModelObj = new SprTaskEditModel();

        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT tas_id, " + //
                "        tas_name, " + //
                "        tas_type, " + //
                "        tas_status, " + //
                "        TO_CHAR(TAS_DATE_FROM, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_DB) + "') as TAS_DATE_FROM, " + //
                "        TO_CHAR(TAS_DATE_TO, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_DB) + "') as TAS_DATE_TO, " + //
                "        tas_reject_reason, " + //
                "        tas_priority, " + //
                "        tas_repetitive, " + //
                "        tas_description, " + //
                "        tas_usr_id, " + //
                "        t2.usr_person_name usr_person_name, " + //
                "        t2.usr_person_surname usr_person_surname, " + //
                "        tas_tas_id " + //
                "   FROM spr_tasks t1 " + //
                "   LEFT JOIN spr_users t2 " + //
                "     ON t2.usr_id = t1.tas_usr_id");

        stmt.addParam4WherePart("tas_id = ? ", recordIdentifier.getIdAsDouble());
        List<HashMap<String, String>> dataObj = queryController.selectQueryAsDataArrayList(conn, stmt);

        SprTask taskObj = new SprTask();
        SimpleDateFormat dateFormat = new SimpleDateFormat(dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_JAVA));
        for (int i = 0; i < dataObj.size(); i++) {
            HashMap<String, String> rec = dataObj.get(i);

            taskObj.setTas_id(Double.parseDouble(rec.get("tas_id")));
            taskObj.setTas_name(rec.get("tas_name"));
            taskObj.setTas_type(rec.get("tas_type"));
            taskObj.setTas_status(rec.get("tas_status"));
            taskObj.setTas_date_from(dateFormat.parse(rec.get("tas_date_from")));
            if (rec.get("tas_date_to") != null) {
                taskObj.setTas_date_to(dateFormat.parse(rec.get("tas_date_to")));
            }

            taskObj.setTas_reject_reason(rec.get("tas_reject_reason"));
            taskObj.setTas_priority(rec.get("tas_priority"));
            taskObj.setTas_repetitive(rec.get("tas_repetitive"));
            taskObj.setTas_description(rec.get("tas_description"));

            if (rec.get("tas_usr_id") != null) {
                taskObj.setTas_usr_id(Double.parseDouble(rec.get("tas_usr_id")));
            }
            if (rec.get("usr_person_name") != null) {
                taskObj.setTas_usr_userName(rec.get("usr_person_name"));
            }
            if (rec.get("usr_person_surname") != null) {
                taskObj.setTas_usr_userSurname(rec.get("usr_person_surname"));
            }
            if (rec.get("tas_tas_id") != null) {
                taskObj.setTas_tas_id(Double.parseDouble(rec.get("tas_tas_id")));
            }
        }

        taskModelObj.setSprTask(taskObj);
        return taskModelObj;
    }

    public SprTaskEditModel getSubTask(Connection conn, SprTaskEditModel task) throws Exception {

        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT tas_id "//
                + "FROM spr_tasks "//
                + "WHERE tas_tas_id = ?");

        stmt.addSelectParam(task.getSprTask().getTas_id());

        List<HashMap<String, String>> dataObj = queryController.selectQueryAsDataArrayList(conn, stmt);

        List<SprTaskEditModel> subTaskObj = new ArrayList<SprTaskEditModel>();

        for (int i = 0; i < dataObj.size(); i++) {
            RecordIdentifier recordIdentifier = new RecordIdentifier();

            HashMap<String, String> rec = dataObj.get(i);
            recordIdentifier.setId(rec.get("tas_id"));

            subTaskObj.add(loadTaskData(conn, recordIdentifier));

        }

        task.setSubTasks(subTaskObj.toArray(new SprTaskEditModel[subTaskObj.size()]));

        return task;
    }

    /**
     * Function fill get properties record object. In case if recordIdentifier
     * provided with value "NEW" will be constructed new object, otherwise will be
     * object will be constructed from DB
     * 
     * @param conn             - db connections
     * @param recordIdentifier record ID. In case identifier is equal to "NEW"
     *                         function will construct new object.
     * @return properties object.
     * @throws NumberFormatException
     * @throws Exception
     */

    public SprTaskEditModel get(Connection conn, RecordIdentifier recordIdentifier) throws Exception {

        SprTaskEditModel taskEditModel = loadTaskData(conn, recordIdentifier);
        log.debug("Query main task data: " + taskEditModel);
        taskEditModel = getSubTask(conn, taskEditModel);
        log.debug("Query sub task data: " + taskEditModel);

        taskEditModel = getTaskAssigments(conn, taskEditModel);
        log.debug("Query task assigments data: " + taskEditModel);

        // Get all attachments
        taskEditModel.setAttachments(sprTaskFilesDBService.getTaskFiles(conn, taskEditModel.getSprTask().getTas_id()));
        log.debug("Full task query: " + taskEditModel);

        if (ACTION_COPY.equalsIgnoreCase(recordIdentifier.getActionType())) {
            taskEditModel.getSprTask().setTas_id(null);
        }

        return taskEditModel;
    }

    /*
     * The list of persons is for the multiselect field
     */
    public String getPersonsList(Connection conn) throws Exception {

        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT usr_id,"//
                + " usr_person_name,"//
                + " usr_person_surname"//
                + " FROM spr_users"//
                + " ORDER BY usr_person_name ASC");

        log.debug("The list of people has been created");
        return queryController.selectQueryAsJSON(conn, stmt);

    }

    /**
     * Function will save provided object to the db. Before save will be performed
     * below listed actions: 1. check if user has right perform this action (insert
     * or update) 2. validate provided data 3. save data to the db.
     * 
     * @param conn   - connection to the db, that will be used for data saving.
     * @param record - object that should be stored in db
     * @return - saved object.
     * @throws Exception
     */

    /*
     * save: The function saves task assignments, the main task, and subtasks
     */
    public SprTaskEditModel save(Connection conn, SprTaskEditModel record, Double orgId) throws Exception {

        this.checkIsFormActionAssigned(conn, record.sprTask.getTas_id() == null ? SprTasksBrowse.ACTION_CREATE : SprTasksBrowse.ACTION_UPDATE);

        SprTasksDAO taskDao = null;

        /*
         * Identify whether it is a new entry or an update
         */
        if (record.sprTask.getTas_id() != null) {
            taskDao = taskDBService.loadRecord(conn, record.sprTask.getTas_id());
        } else {
            taskDao = taskDBService.newRecord();
        }

        taskDao.setTas_name(record.sprTask.getTas_name());
        taskDao.setTas_description(record.sprTask.getTas_description());
        taskDao.setTas_date_from(record.sprTask.getTas_date_from());
        taskDao.setTas_date_to(record.sprTask.getTas_date_to());
        taskDao.setTas_type(record.sprTask.getTas_type());
        if (record.sprTask.getTas_usr_id() == null) {
            record.sprTask.setTas_usr_id(taskDBService.getUserSession().getUsrId());
        }
        taskDao.setTas_usr_id(record.sprTask.getTas_usr_id());
        taskDao.setTas_status(record.sprTask.getTas_status());
        taskDao.setTas_repetitive(record.sprTask.getTas_repetitive());
        taskDao.setTas_priority(record.sprTask.getTas_priority());
        taskDao = taskDBService.saveRecord(conn, taskDao);
        record.sprTask.setTas_id(taskDao.getTas_id());

        /*
         * We get the people assigned to the main task
         */
        List<String> assignedIds = record.getAssigneesIds();

        /*
         * For which the request receives data from the assignments table by task ID:
         * tas_id, the data is required to match the received data from the user
         * interface
         */
        StatementAndParams assigStmt = new StatementAndParams();

        assigStmt.setStatement("SELECT tat_id,"//
                + " tat_usr_id"//
                + " FROM spr_task_assignments"//
                + " WHERE tat_tas_id = ?");

        assigStmt.addSelectParam(record.getSprTask().getTas_id());

        List<HashMap<String, String>> assigObj = queryController.selectQueryAsDataArrayList(conn, assigStmt);
        for (int i = 0; i < assigObj.size(); i++) {

            HashMap<String, String> assigRow = assigObj.get(i);

            if (!assignedIds.contains(assigRow.get("tat_usr_id"))) {
                /*
                 * If the task assignment user ID: tat_usr_id no longer appears in the received
                 * data from the user interface, the record is deleted
                 */
                sprTasksAssignmentsService.deleteRecord(conn, Double.parseDouble(assigRow.get("tat_id")));
            } else {
                assignedIds.remove(assigRow.get("tat_usr_id"));
            }
        }
        /*
         * Adds adjusted new task assignments
         */
        for (String assignedUser : record.getAssigneesIds()) {
            if (assignedIds.contains(assignedUser)) {

                Double userId = Utils.getDouble(assignedUser);
                SprTaskAssignmentsDAO daoAssigment = sprTasksAssignmentsService.newRecord();
                daoAssigment.setTat_usr_id(userId);
                daoAssigment.setTat_tas_id(record.getSprTask().getTas_id());
                daoAssigment.setTat_date_from(record.getSprTask().getTas_date_from());
                daoAssigment.setTat_date_to(record.getSprTask().getTas_date_to());
                daoAssigment.setTat_org_id(orgId);
                daoAssigment = sprTasksAssignmentsService.saveRecord(conn, daoAssigment);

                Map<String, String> params = new HashMap<>();
                params.put("taskId", Integer.toString(record.getSprTask().getTas_id().intValue()));

                sprNotifications.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_TASK, record.getSprTask().getTas_id(), "NOTIFICATION_TASK",
                        "NOTIFICATION_TASK_SUBJECT", "NOTIFICATION_TASK_NEW", params, null, userId, orgId, null);

            }
        }

        /*
         * This section checks the subtasks received from the user interface
         */
        if (record.getSubTasks() != null) {

            ArrayList<Double> subTaskList = new ArrayList<Double>();

            /*
             * We check if the subtask has no null values and add it to the subTaskList
             * array
             */
            for (SprTaskEditModel subTaskRow : record.getSubTasks()) {
                if (subTaskRow.getSprTask().getTas_id() != null) {
                    subTaskList.add(subTaskRow.getSprTask().getTas_id());
                }
            }

            /*
             * The request is for retrieving data from the task table, it checks if it is
             * subtask according to tas_tas_id, we compare the received data with the
             * received data from the user interface
             */
            StatementAndParams taskStmt = new StatementAndParams();

            taskStmt.setStatement("SELECT "//
                    + "tas_id, "//
                    + "tas_usr_id "//
                    + "FROM spr_tasks");

            taskStmt.addParam4WherePart("tas_tas_id = ?", record.getSprTask().getTas_id());

            List<HashMap<String, String>> dataObj = queryController.selectQueryAsDataArrayList(conn, taskStmt);
            for (int i = 0; i < dataObj.size(); i++) {

                HashMap<String, String> taskRow = dataObj.get(i);

                /*
                 * If the data does not match between the one received from the user interface
                 * and the database, the record is removed from both the task table and the
                 * assignments
                 */
                if (!subTaskList.contains(Double.parseDouble(taskRow.get("tas_id")))) {

                    StatementAndParams taskAssigmentsStmt = new StatementAndParams();

                    taskAssigmentsStmt.setStatement("SELECT tat_id FROM spr_task_assignments WHERE tat_tas_id = ?");

                    taskAssigmentsStmt.addSelectParam(taskRow.get("tas_id"));

                    List<HashMap<String, String>> assigments = queryController.selectQueryAsDataArrayList(conn, taskAssigmentsStmt);

                    for (int j = 0; j < assigments.size(); j++) {

                        HashMap<String, String> assigmentRow = assigments.get(j);

                        sprTasksAssignmentsService.deleteRecord(conn, Double.valueOf(assigmentRow.get("tat_id")));
                        log.debug("Assignments record deleted: " + taskRow.get("tat_id"));

                    }
                    taskDBService.deleteRecord(conn, Double.parseDouble(taskRow.get("tas_id")));
                    log.debug("Task record deleted: " + taskRow.get("tas_id"));

                }
            }

        } else {
            log.debug("Empty sub task object");
        }
        /*
         * In this section we check if this is a new sub task entry and needs to be
         * updated.
         */
        if (record.getSubTasks() != null) {
            for (SprTaskEditModel subTaskRow : record.getSubTasks()) {

                SprTasksDAO daoSubTask = null;
                SprTaskAssignmentsDAO daoAssigment = null;

                if (subTaskRow.getSprTask().getTas_id() != null) {
                    daoSubTask = taskDBService.loadRecord(conn, subTaskRow.getSprTask().getTas_id());

                    StatementAndParams taskStmtx = new StatementAndParams();

                    taskStmtx.setStatement("SELECT "//
                            + "tat_id, "//
                            + "tat_usr_id "//
                            + "FROM spr_task_assignments");

                    taskStmtx.addParam4WherePart("tat_usr_id = ?", subTaskRow.getSprTask().getTas_usr_id());
                    taskStmtx.addParam4WherePart("tat_tas_id = ?", subTaskRow.getSprTask().getTas_id());

                    List<HashMap<String, String>> dataObj = queryController.selectQueryAsDataArrayList(conn, taskStmtx);
                    for (int i = 0; i < dataObj.size(); i++) {

                        Map<String, String> taskRow = dataObj.get(i);

                        log.debug("Loaded subtask assignments id: " + taskRow.get("tat_id"));
                        daoAssigment = sprTasksAssignmentsService.loadRecord(conn, Double.valueOf(taskRow.get("tat_id")));
                    }

                } else {
                    daoSubTask = taskDBService.newRecord();
                    daoAssigment = sprTasksAssignmentsService.newRecord();
                }

                daoSubTask.setTas_name(subTaskRow.getSprTask().getTas_name());
                daoSubTask.setTas_description(subTaskRow.getSprTask().getTas_description());
                daoSubTask.setTas_date_from(subTaskRow.getSprTask().getTas_date_from());
                daoSubTask.setTas_date_to(subTaskRow.getSprTask().getTas_date_to());
                daoSubTask.setTas_type(subTaskRow.getSprTask().getTas_type());
                if (subTaskRow.getSprTask().getTas_usr_id() == null) {
                    subTaskRow.getSprTask().setTas_usr_id(taskDBService.getUserSession().getUsrId());
                }
                daoSubTask.setTas_usr_id(subTaskRow.getSprTask().getTas_usr_id());

                daoSubTask.setTas_tas_id(record.getSprTask().getTas_id());

                daoSubTask.setTas_status(subTaskRow.getSprTask().getTas_status());
                daoSubTask.setTas_repetitive(subTaskRow.getSprTask().getTas_repetitive());
                daoSubTask.setTas_priority(subTaskRow.getSprTask().getTas_priority());
                daoSubTask = taskDBService.saveRecord(conn, daoSubTask);

                daoAssigment.setTat_usr_id(subTaskRow.getSprTask().getTas_usr_id());
                daoAssigment.setTat_tas_id(daoSubTask.getTas_id());
                daoAssigment.setTat_date_from(subTaskRow.getSprTask().getTas_date_from());
                daoAssigment.setTat_date_to(subTaskRow.getSprTask().getTas_date_to());
                daoAssigment = sprTasksAssignmentsService.saveRecord(conn, daoAssigment);

            }

        }

        // Save task files to DB
        saveFiles(conn, record.getSprTask().getTas_id(), record.getAttachments());

        return record;
    }

    /*
     * Deletes the task and task assignment by task ID: tas_id
     */
    public void delete(Connection conn, RecordIdentifier recordIdentifier) throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, SprTasksBrowse.ACTION_DELETE);
        taskDBService.deleteAllTaskInfo(conn, recordIdentifier);

        // Delete all task files
        ArrayList<SprTaskFileKey> existingTaskFiles = sprTaskFilesDBService.loadAllTaskRecords(conn, Double.parseDouble(recordIdentifier.getId()));
        for (SprTaskFileKey file : existingTaskFiles) {
            // Delete task-file reference
            sprTaskFilesDBService.deleteRecord(conn, file.getTfi_id());
            // Delete file table
            sprFilesDBService.deleteRecord(conn, file.getFil_id());
            // Delete files form server file storage
            fileStorageService.deleteFile(file.getFil_key(), null);
        }
    }

    /**
     * Function will save provided object to the db. Before save will be performed
     * below listed actions: 1. check if user has right perform this action (insert
     * or update) 2. validate provided data 3. save data to the db.
     * 
     * @param conn  - connection to the db, that will be used for data saving.
     * @param tasId - Task ID
     * @param files - Task files object
     * @return - saved object.
     * @throws Exception
     */
    public SprFile[] saveFiles(Connection conn, Double tasId, SprFile[] attachments) throws Exception {

        ArrayList<SprTaskFileKey> existingTaskFiles = sprTaskFilesDBService.loadAllTaskRecords(conn, tasId);
        // Check and delete all files from DB that do not belong to task anymore.
        for (SprTaskFileKey file : existingTaskFiles) {
            if (Arrays.asList(attachments).stream().filter(f -> f.getFil_key().equals(file.getFil_key())).count() < 1) {
                // Delete task-file reference
                sprTaskFilesDBService.deleteRecord(conn, file.getTfi_id());
                // Delete file table
                sprFilesDBService.deleteRecord(conn, file.getFil_id());
                // Delete files form server file storage
                try {
                    fileStorageService.deleteFile(file.getFil_key(), file.getFil_path());
                } catch (Exception e) {
                    // if file does not exist, already deleted or ..
                    e.printStackTrace();
                }
            }
            ;
        }
        /// Create newly added files
        for (SprFile file : attachments) {
            // If file already exists then do not create new reference.
            if (existingTaskFiles.stream().filter(f -> f.getFil_key().equals(file.getFil_key())).count() < 1) {
                SprTaskFilesDAO insFile = sprTaskFilesDBService.newRecord();
                SprFilesDAO fileDB = sprFilesDBService.loadRecordByKey(conn, file.getFil_key());
                insFile.setTfi_tas_id(tasId);
                insFile.setTfi_fil_id(fileDB.getFil_id());
                insFile.setTfi_date_from(new Date());
                insFile.setTfi_usr_id(this.requestContext.getUserSession().getUsrId());
                sprTaskFilesDBService.saveRecord(conn, insFile);
                // Set file as confimed
                fileDB.setFil_status("CONFIRMED");
                fileDB.isForceUpdate();
                sprFilesDBService.saveRecord(conn, fileDB);
            }
        }

        return attachments;
    }

}
