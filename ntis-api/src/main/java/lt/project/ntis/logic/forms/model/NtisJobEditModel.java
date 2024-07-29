package lt.project.ntis.logic.forms.model;

import eu.itreegroup.spark.modules.admin.dao.SprJobDefinitionsDAO;

public class NtisJobEditModel {

    SprJobDefinitionsDAO jobDefinitionsDAO;

    Double numberOfAttempts;

    Double periodAfterAttempt;

    public SprJobDefinitionsDAO getJobDefinitionsDAO() {
        return jobDefinitionsDAO;
    }

    public void setJobDefinitionsDAO(SprJobDefinitionsDAO jobDefinitionsDAO) {
        this.jobDefinitionsDAO = jobDefinitionsDAO;
    }

    public Double getNumberOfAttempts() {
        return numberOfAttempts;
    }

    public void setNumberOfAttempts(Double numberOfAttempts) {
        this.numberOfAttempts = numberOfAttempts;
    }

    public Double getPeriodAfterAttempt() {
        return periodAfterAttempt;
    }

    public void setPeriodAfterAttempt(Double periodAfterAttempt) {
        this.periodAfterAttempt = periodAfterAttempt;
    }

}
