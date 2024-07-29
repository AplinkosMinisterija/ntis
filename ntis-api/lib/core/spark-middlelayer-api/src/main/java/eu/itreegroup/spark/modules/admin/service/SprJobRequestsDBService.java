package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;
import java.util.List;

import eu.itreegroup.spark.dao.common.SprBaseDBService;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;

public interface SprJobRequestsDBService extends SprBaseDBService<SprJobRequestsDAO> {

    public final static String REQUEST_STATUS_ERROR = "ERROR";

    public static final String REQUEST_STATUS_WAITING = "WAITING";

    public final static String REQUEST_STATUS_PROCESSING = "PROCESSING";

    public final static String REQUEST_STATUS_COMPLETED = "COMPLETED";

    public List<SprJobRequestsDAO> loadActiveJobReqest(Connection conn, String executerName) throws Exception;

    public SprJobRequestsDAO loadRecord4Update(Connection conn, Object identifier) throws Exception;

    public List<SprJobRequestsDAO> loadActiveNotAssignedPrintReqest(Connection conn) throws Exception;

    public void deleteRecordsAndChildrenByParent(Connection conn, Double identifier) throws Exception;

}