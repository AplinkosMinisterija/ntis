package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;
import java.util.List;

import eu.itreegroup.spark.dao.common.SprBaseDBService;
import eu.itreegroup.spark.modules.admin.dao.SprJobDefinitionsDAO;

public interface SprJobDefinitionsDBService extends SprBaseDBService<SprJobDefinitionsDAO> {

    public static final String REPORT_TYPE = "REPORT";

    public static final String EMAIL_TYPE = "EMAIL";

    public static final String PROGRAM_TYPE = "PROGRAM";

    public static final String EXECUTOR_TYPE_JOB = "JOB";

    public static final String EXECUTOR_TYPE_MANUAL = "MANUAL";

    public SprJobDefinitionsDAO loadRecordByCode(Connection conn, String jde_type, String jde_code) throws Exception;

    public List<SprJobDefinitionsDAO> loadActiveJobs(Connection conn) throws Exception;

}