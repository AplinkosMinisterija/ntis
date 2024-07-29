package eu.itreegroup.spark.app.scheduler;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.itreegroup.spark.app.scheduler.base.Job;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprJobDefinitionsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;
import eu.itreegroup.spark.modules.admin.service.SprJobRequestsDBService;

/**
 * SchedulerJob will check  SprPrintRequestTypes table and create active job for executor.
 * Function can be controlled by changing server properties: 
 *    itreeScheduler.schedulerJob.rate, 
 *    itreeScheduler.schedulerJob.enabled
 */
public class SchedulerJob extends Job {

    public SchedulerJob(SprJobDefinitionsDBService sprJobDefinitionsDBService, SprJobRequestsDBService sprJobRequestsDBService) {
        super(sprJobDefinitionsDBService, sprJobRequestsDBService);
    }

    private static final Logger log = LoggerFactory.getLogger(SchedulerJob.class);

    /**
     * Main method to start the job.
     */
    public void execute() {
        if (jobEnabled) {
            log.debug("start  schedulerJob enabled:" + jobEnabled);
            try (Connection conn = jobDataSource.getConnection()) {
                for (SprJobDefinitionsDAO job : sprJobDefinitionsDBService.loadActiveJobs(conn)) {
                    log.debug(" Job Name -" + job.getJde_name());
                    log.debug(" Job period  -" + job.getJde_period());
                    Date currentDate = new Date();
                    if (job.getJde_next_action_time() == null) {
                        log.debug("next action time is null will be used: " + currentDate);
                        job.setJde_next_action_time(currentDate);
                    }
                    Date nextActionDate = job.getJde_next_action_time();
                    if (currentDate.compareTo(nextActionDate) >= 0) {
                        boolean forwardRequired = currentDate.compareTo(getNextExecutionTime(nextActionDate, job)) >= 0;
                        while (forwardRequired) {
                            if (job.getJde_adjust_to_current_date() != null && !YesNo.valueOf(job.getJde_adjust_to_current_date()).getBoolean()) {
                                createJobRequest(conn, job);
                            }
                            nextActionDate = getNextExecutionTime(nextActionDate, job);
                            if (forwardRequired) {
                                job.setJde_last_action_time(new Date());
                                job.setJde_next_action_time(nextActionDate);
                                sprJobDefinitionsDBService.saveRecord(conn, job);
                            }
                            forwardRequired = currentDate.compareTo(getNextExecutionTime(nextActionDate, job)) >= 0;
                            log.debug("INTERNAL new Date():" + currentDate + " nextActionDate: " + nextActionDate + " status: " + forwardRequired);
                        }
                        createJobRequest(conn, job);
                        job.setJde_last_action_time(new Date());
                        job.setJde_next_action_time(getNextExecutionTime(nextActionDate, job));
                        sprJobDefinitionsDBService.saveRecord(conn, job);
                    } else {
                        log.debug(" Don't need to create  - " + job.getJde_period());
                    }
                }
            } catch (Exception e) {
                log.error("SchedulerJob err:  ", e);
            }
            log.debug("end SchedulerJob");
        }
    }

    private SprJobRequestsDAO createJobRequest(Connection conn, SprJobDefinitionsDAO job) throws Exception {
        SprJobRequestsDAO sprJobRequestsDAO = sprJobRequestsDBService.newRecord();
        sprJobRequestsDAO.setJrq_jde_id(job.getJde_id());
        sprJobRequestsDAO.setJrq_status(SprJobRequestsDBService.REQUEST_STATUS_WAITING);
        sprJobRequestsDAO.setJrq_request_time(job.getJde_next_action_time());
        if (job.getJde_default_executer() != null) {
            sprJobRequestsDAO.setJrq_executer_name(job.getJde_default_executer());
        }
        return sprJobRequestsDBService.saveRecord(conn, sprJobRequestsDAO);
    }

    private Date getNextExecutionTime(Date startDate, SprJobDefinitionsDAO jobDef) throws Exception {
        if (startDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            Period p = Period.valueOf(jobDef.getJde_period());
            switch (p) {
                case MONTH:
                    cal.add(Calendar.MONTH, 1);
                    break;
                case WEEK:
                    cal.add(Calendar.DATE, 7);
                    break;
                case DAY:
                    cal.add(Calendar.DATE, 1);
                    break;
                case HOUR:
                    cal.add(Calendar.HOUR, 1);
                    break;
                case MIN:
                    if (jobDef.getJde_period_in_minutes() != null) {
                        cal.add(Calendar.MINUTE, jobDef.getJde_period_in_minutes().intValue());
                    }
                    break;
            }
            startDate = cal.getTime();
        }
        return startDate;
    }

    /**
     * Function will set data source that should be used for data processing
     * @param scheduleDataSource
     * @deprecated This method is no longer acceptable
     * <p> instead should be used {@link setJobDataSource(scheduleDataSource)
     */
    @Deprecated
    public void setScheduleDataSource(DataSource scheduleDataSource) {
        setJobDataSource(scheduleDataSource);
    }

    /**
     * Function will set job status. In case if true job will be executed otherwise this job will be not executed
     * @param schedulerJobEnabled - true - job is enabled otherwise not.
     * @deprecated This method is no longer acceptable
     * <p> instead should be used {@link setJobEnabled(jobEnabled)}
     */
    @Deprecated
    public void setSchedulerJobEnabled(boolean schedulerJobEnabled) {
        setJobEnabled(schedulerJobEnabled);
    }

    /**
     * Function will set job name.
     * @param executorName
     * @deprecated This method is no longer acceptable
     * <p> Use {@link setJobName(executorName)} instead.
     */
    @Deprecated
    public void setExecutorName(String executorName) {
        setJobName(executorName);
    }

    /**
     * Function will return job name. 
     * @return job name
     * @deprecated This method is no longer acceptable
     * <p> Use {@link getJobName()} instead.
     */
    @Deprecated
    public String getExecutorName() {
        return getJobName();
    }

    enum Period {

        MIN("MIN"), HOUR("HOUR"), DAY("DAY"), WEEK("WEEK"), MONTH("MONTH");

        String value;

        Period(String value) {
            this.value = value;
        }

    }
}
