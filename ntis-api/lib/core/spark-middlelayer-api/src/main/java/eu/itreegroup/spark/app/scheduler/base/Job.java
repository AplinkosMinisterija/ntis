package eu.itreegroup.spark.app.scheduler.base;

import javax.sql.DataSource;

import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;
import eu.itreegroup.spark.modules.admin.service.SprJobRequestsDBService;

public class Job {

    protected final SprJobDefinitionsDBService sprJobDefinitionsDBService;

    protected final SprJobRequestsDBService sprJobRequestsDBService;

    protected boolean jobEnabled;

    protected String jobName;

    protected DataSource jobDataSource;

    public Job(SprJobDefinitionsDBService sprJobDefinitionsDBService, SprJobRequestsDBService sprJobRequestsDBService) {
        this.sprJobRequestsDBService = sprJobRequestsDBService;
        this.sprJobDefinitionsDBService = sprJobDefinitionsDBService;
    }

    /**
     * Function will return job status 
     * @return true - job is enabled otherwise not.
     */
    public boolean isJobEnabled() {
        return jobEnabled;
    }

    /**
     * Function will set job status. In case if true job will be executed otherwise this job will be marked how suspended and his
     * logic will be not executed
     * @param schedulerJobEnabled - true - job is enabled otherwise not.
     */
    public void setJobEnabled(boolean jobEnabled) {
        this.jobEnabled = jobEnabled;
    }

    /**
     * Function will return job name, that is used form job identification and can be used for task assignation.
     * @return job name
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * Function will set job name.
     * @param jobName - job identifier
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * Function will return data source that should be used for data processing 
     * @return data source 
     */
    public DataSource getJobDataSource() {
        return jobDataSource;
    }

    /**
     * Function will set data source that should be used for data processing
     * @param jobDataSource 
     */
    public void setJobDataSource(DataSource jobDataSource) {
        this.jobDataSource = jobDataSource;
    }

    /**
     * Function will set job definition DB service
     * @return
     */
    public SprJobDefinitionsDBService getSprJobDefinitionsDBService() {
        return sprJobDefinitionsDBService;
    }

    /**
     * Function will set job request DB service
     * @return
     */
    public SprJobRequestsDBService getSprJobRequestsDBService() {
        return sprJobRequestsDBService;
    }

}