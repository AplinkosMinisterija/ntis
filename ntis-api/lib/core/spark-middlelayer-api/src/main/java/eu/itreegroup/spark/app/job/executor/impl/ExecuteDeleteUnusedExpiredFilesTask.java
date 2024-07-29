package eu.itreegroup.spark.app.job.executor.impl;

import java.net.InetAddress;
import java.sql.Connection;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.app.job.executor.BaseRequestLogicExecutor;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;

@Service("FILE_REMOVAL_EXECUTOR")
public class ExecuteDeleteUnusedExpiredFilesTask extends BaseRequestLogicExecutor {

    private static final Logger log = LoggerFactory.getLogger(ExecuteDeleteUnusedExpiredFilesTask.class);

    @Override
    public void executeSpecificLogic(Connection conn, String scriptName, SprJobRequestsDAO request) throws Exception {
        log.debug("DeleteUnusedExpiredFilesJob started");
        Date timeLimit = findTimeLimit(conn, request);
        for (SprFilesDAO dao : collectDeletableFiles(conn, timeLimit)) {
            try {
                log.debug("Deleting spr_files record with fil_id {}.", dao.getFil_id());
                sprFilesDBService.deleteRecord(conn, dao.getFil_id());

                deleteFile(dao);

            } catch (Exception e) {
                log.error("Failed to delete spr_files record by fil_id '{}'.", dao.getFil_id(), e);
            }
        }
        log.debug("DeleteUnusedExpiredFilesJob ended");
    }

    private void deleteFile(SprFilesDAO dao) {
        try {
            log.debug("Deleting file by key '{}' and path '{}'.", dao.getFil_key(), dao.getFil_path());
            fileStorageService.deleteFile(dao.getFil_key(), dao.getFil_path());

        } catch (Exception e) {
            log.error("Failed to delete file by key '{}' and path '{}'.", dao.getFil_key(), dao.getFil_path(), e);
        }
    }

    private List<SprFilesDAO> collectDeletableFiles(Connection conn, Date timeLimit) throws Exception {
        String hostName = InetAddress.getLocalHost().getHostName();
        List<SprFilesDAO> result = sprFilesDBService.loadRecordsByParams(conn,
                "((fil_status = 'UPLOADED' and fil_status_date < ?) or (fil_status = 'DELETED')) and fil_server = ?", new SelectParamValue(timeLimit),
                new SelectParamValue(hostName));

        log.debug("Identified {} records that are older than {} on '{}' machine.", result.size(), timeLimit, hostName);
        return result;
    }

    private Date findTimeLimit(Connection conn, SprJobRequestsDAO request) throws Exception {
        long periodInHours = findPeriodInHours(conn, request);
        return toDate(LocalDateTime.now().minus(Duration.ofHours(periodInHours)));
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private long findPeriodInHours(Connection conn, SprJobRequestsDAO request) throws Exception {
        String executionParameter = sprJobDefinitionDBService.loadRecord(conn, request.getJrq_jde_id()).getJde_execution_parameter();
        if (executionParameter == null || executionParameter.isBlank()) {
            throw new Exception(
                    "Found empty execution parameter. Execution parameter must contain positive integer number identifying period in hours for how long files and records can remain undeleted.");
        }
        try {
            Long periodInHours = Utils.getLong(executionParameter);
            if (periodInHours == null) {
                throw new Exception(String.format("Could not convert execution parameter '%s' into value of type long.", executionParameter));
            }
            return periodInHours.longValue();
        } catch (Exception e) {
            throw new Exception(String.format("Failed to parse execution parameter '%s' into value of type long.", executionParameter), e);
        }
    }

}
