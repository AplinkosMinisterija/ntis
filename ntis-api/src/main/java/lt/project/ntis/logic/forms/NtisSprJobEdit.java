package lt.project.ntis.logic.forms;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.modules.admin.dao.SprJobDefinitionsDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.SprJobEdit;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;
import lt.project.ntis.logic.forms.model.NtisJobEditModel;

@Component
public class NtisSprJobEdit extends SprJobEdit {

    private static final Logger log = LoggerFactory.getLogger(NtisSprJobEdit.class);

    public NtisSprJobEdit(SprJobDefinitionsDBService sprJobDefinitionsDBService) {
        super(sprJobDefinitionsDBService);

    }

    public NtisJobEditModel getJobWithDetails(Connection conn, RecordIdentifier recordIdentifier) throws NumberFormatException, Exception {
        SprJobDefinitionsDAO result = super.getJob(conn, recordIdentifier);
        NtisJobEditModel jobWithDetails = new NtisJobEditModel();
        jobWithDetails.setJobDefinitionsDAO(result);
        jobWithDetails.setNumberOfAttempts(result.getN01());
        jobWithDetails.setPeriodAfterAttempt(result.getN02());
        return jobWithDetails;
    }

    public NtisJobEditModel save(Connection conn, NtisJobEditModel job) throws Exception {
        job.getJobDefinitionsDAO().setN01(job.getNumberOfAttempts());
        job.getJobDefinitionsDAO().setN02(job.getPeriodAfterAttempt());
        super.setJob(conn, job.getJobDefinitionsDAO());
        job.setNumberOfAttempts(job.getJobDefinitionsDAO().getN01());
        job.setPeriodAfterAttempt(job.getJobDefinitionsDAO().getN02());
        return job;
    }
}
