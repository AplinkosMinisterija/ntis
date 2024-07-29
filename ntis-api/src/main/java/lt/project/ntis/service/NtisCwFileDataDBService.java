package lt.project.ntis.service;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import lt.project.ntis.dao.NtisCwFileDataDAO;
import lt.project.ntis.service.gen.NtisCwFileDataDBServiceGen;

@Service
public class NtisCwFileDataDBService extends NtisCwFileDataDBServiceGen {

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(NtisCwFileDataDBService.class);

    public NtisCwFileDataDBService() {
    }

    @Override
    public NtisCwFileDataDAO newRecord() {
        NtisCwFileDataDAO daoObject = super.newRecord();
        return daoObject;
    }

    public void deleteRecordByCwfId(Connection conn, Double identifier) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("DELETE FROM NTIS_CW_FILE_DATA WHERE CWFD_CWF_ID=?::int ");
        stmt.setWhereExists(true);
        stmt.addSelectParam(identifier);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);
    }

}