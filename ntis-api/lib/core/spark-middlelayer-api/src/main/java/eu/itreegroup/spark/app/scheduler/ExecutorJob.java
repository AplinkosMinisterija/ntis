package eu.itreegroup.spark.app.scheduler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;

import eu.itreegroup.spark.app.job.executor.RequestLogicExecutor;
import eu.itreegroup.spark.app.job.executor.model.ErrorInfo;
import eu.itreegroup.spark.app.scheduler.base.Job;
import eu.itreegroup.spark.modules.admin.dao.SprJobDefinitionsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestExecutionsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;
import eu.itreegroup.spark.modules.admin.service.SprJobRequestExecutionsDBService;
import eu.itreegroup.spark.modules.admin.service.SprJobRequestsDBService;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;

/**
 * Executor will check  SprPrintRequests table and will start execute not started jobs.
 * Function can be controlled by changing server properties: 
 *    itreeScheduler.executorJob.rate, 
 *    itreeScheduler.executorJob.enabled
 */
public class ExecutorJob extends Job {

    private BeanFactory beanFactory;

    private static final Logger log = LoggerFactory.getLogger(ExecutorJob.class);

    private final SprFilesDBService filesDBService;

    private final SprJobRequestExecutionsDBService sprJobRequestExecutionsDBService;

    public ExecutorJob(BeanFactory beanFactory, SprJobDefinitionsDBService sprJobDefinitionsDBService, SprJobRequestsDBService sprJobRequestsDBService,
            SprFilesDBService filesDBService, SprJobRequestExecutionsDBService sprJobRequestExecutionsDBService) {
        super(sprJobDefinitionsDBService, sprJobRequestsDBService);
        this.beanFactory = beanFactory;
        this.filesDBService = filesDBService;
        this.sprJobRequestExecutionsDBService = sprJobRequestExecutionsDBService;
    }

    public void execute(Connection conn, SprJobRequestsDAO task) throws Exception {
        SprJobDefinitionsDAO program = sprJobDefinitionsDBService.loadRecord(conn, task.getJrq_jde_id());
        task.setJrq_start_date(new Date());
        task.setJrq_error(null);
        task.setJrq_status(SprJobRequestsDBService.REQUEST_STATUS_PROCESSING);
        sprJobRequestsDBService.saveRecord(conn, task);
        // Task class will be calling DB setup
        try {
            conn.setAutoCommit(false);
            RequestLogicExecutor service = beanFactory.getBean(program.getJde_execution_unit(), RequestLogicExecutor.class);
            if (task.getJrq_result_type() == null) {
                task.setJrq_result_type(program.getJde_default_output_type());
            }
            service.execute(conn, program.getJde_execution_parameter(), task);
            task = sprJobRequestsDBService.loadRecord(conn, task.getJrq_id());
            task.setJrq_result_time(new Date());
            if (task.getJrq_error() == null) {
                task.setJrq_status(SprJobRequestsDBService.REQUEST_STATUS_COMPLETED);
                program.setJde_last_action_time(new Date());
                sprJobDefinitionsDBService.saveRecord(conn, program);
            } else {
                task.setJrq_status(SprJobRequestsDBService.REQUEST_STATUS_ERROR);
            }
        } catch (Exception e) {
            conn.rollback();
            log.error("error on job request execution:", e);
            task.setJrq_status(SprJobRequestsDBService.REQUEST_STATUS_ERROR);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            ErrorInfo errorInfo = new ErrorInfo();
            errorInfo.setHost(InetAddress.getLocalHost().getHostName());
            errorInfo.setErrorMessage(e.getMessage());
            errorInfo.setErrorTrace(sw.toString());
            String errorString = sw.toString();
            errorString = errorString.length() > 2000 ? errorString.substring(0, 1980) + "...." : errorString;
            task.setJrq_error(errorInfo.toJson(3900));
            SprJobRequestExecutionsDAO executionDAO = sprJobRequestExecutionsDBService.newRecord();
            executionDAO.setJre_jrq_id(task.getJrq_id());
            executionDAO.setJre_err(errorString);
            executionDAO.setJre_time(new Date());
            try {
                SprJobDefinitionsDAO jobDefinitionsDAO = sprJobDefinitionsDBService.loadRecord(conn, task.getJrq_jde_id());
                executionDAO.setJre_name(jobDefinitionsDAO.getJde_name());
            } catch (Exception ex1) {
                log.error("Error while trying to get information of job definition", ex1);
            }
            sprJobRequestExecutionsDBService.saveRecord(conn, executionDAO);
        }
        task.setJrq_end_date(new Date());
        try {
            sprJobRequestsDBService.saveRecord(conn, task);
        } catch (Exception ex) {
            log.error("Error on saving job request record", ex);
        }
        conn.commit();
    }

    /**
     * Function will generate and return file info. 
     * Function save file in OS storage and save file metadata in spr_files table.
     * Jasper will take all parameter from  spr_job_definiotions and spr_job_requset tables.
     * @param conn - connection to the DB, used for data about user extraction
     * @param jreId - ID of spr_job_requests table.
     * @throws Exception
     */
    public SprFilesDAO generateReportByRequest(Connection conn, Double requestId) throws Exception {
        execute(requestId);
        SprJobRequestsDAO req = sprJobRequestsDBService.loadRecord(conn, requestId);
        if (SprJobRequestsDBService.REQUEST_STATUS_COMPLETED.equals(req.getJrq_status()) && req.getJrq_fil_id() != null) {
            SprFilesDAO file = filesDBService.loadRecord(conn, req.getJrq_fil_id());
            file.setFil_id(null);
            file.setFil_path(null);
            file.setFil_server(null);
            return file;
        } else {
            throw new Exception(req.getJrq_error());
        }
    }

    public void execute(Double requestId) {
        try (Connection conn = jobDataSource.getConnection()) {
            conn.setAutoCommit(false);
            SprJobRequestsDAO task = sprJobRequestsDBService.loadRecord4Update(conn, requestId);
            execute(conn, task);
        } catch (Exception e) {
            log.error("ExecutorJob err: ", e);
        }
        log.debug("end ExecutorJob");
    }

    /**
     * Main method to start the job.
     */
    public void execute() {
        if (jobEnabled) {
            log.debug("start  ExecutorJob: {} ", jobEnabled);
            try (Connection conn = jobDataSource.getConnection()) {
                conn.setAutoCommit(false);
                List<SprJobRequestsDAO> tasks = sprJobRequestsDBService.loadActiveJobReqest(conn, jobName);
                log.debug("Selected request count {} for execution", tasks.size());
                for (SprJobRequestsDAO task : tasks) {
                    execute(conn, task);
                }
            } catch (Exception e) {
                log.error("ExecutorJob err: ", e);
            }
            log.debug("end ExecutorJob");
        }
    }

    /**
     * Function will set job status. In case if true job will be executed otherwise this job will be not executed
     * @param schedulerJobEnabled - true - job is enabled otherwise not.
     * @deprecated This method is no longer acceptable
     * <p> instead should be used {@link setJobEnabled(executorJobEnabled)
     */
    @Deprecated
    public void setExecutorJobEnabled(boolean executorJobEnabled) {
        setJobEnabled(executorJobEnabled);
    }

    /**
     * Function will set job name.
     * @param executorName
     * @deprecated This method is no longer acceptable
     * <p> instead should be used {@link setJobName(jobName)
     */
    @Deprecated
    public void setExecutorName(String executorName) {
        setJobName(executorName);
    }

    /**
     * Function will set data source that should be used for data processing
     * @param scheduleDataSource
     * @deprecated This method is no longer acceptable
     * <p> instead should be used {@link setJobDataSource(jobDataSource)
     */
    @Deprecated
    public void setScheduleDataSource(DataSource scheduleDataSource) {
        setJobDataSource(scheduleDataSource);
    }

}
