package eu.itreegroup.spark.app.job.request;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobRequestLogicManager {

    private static final Logger log = LoggerFactory.getLogger(JobRequestLogicManager.class);

    List<JobRequest> printRequestManagersList;

    @Autowired
    public JobRequestLogicManager(List<JobRequest> printRequestManagers) {
        log.debug("JobRequestLogicManager list of JobRequestImpl: " + printRequestManagers);
        printRequestManagersList = printRequestManagers;
    }

    public String toString() {
        if (printRequestManagersList == null) {
            return "In system PrintRequestManagers not exists!!!!";
        }
        String answer = "total Request managers: " + printRequestManagersList.size();
        for (int i = 0; i < printRequestManagersList.size(); i++) {
            answer = answer + "/n" + printRequestManagersList.get(i).getName();
        }
        return answer;
    }

}
