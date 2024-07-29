package lt.project.ntis.service;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import lt.project.ntis.dao.NtisUsedSludgesDAO;
import lt.project.ntis.service.gen.NtisUsedSludgesDBServiceGen;

@Service
public class NtisUsedSludgesDBService extends NtisUsedSludgesDBServiceGen {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(NtisUsedSludgesDBService.class);

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    public NtisUsedSludgesDBService() {
    }

    @Override
    public NtisUsedSludgesDAO newRecord() {
        NtisUsedSludgesDAO daoObject = super.newRecord();
        return daoObject;
    }

    public void deleteRecordByWdId(Connection conn, Double identifier) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("DELETE FROM NTIS_USED_SLUDGES WHERE US_WD_ID=?::int");
        stmt.setWhereExists(true);
        stmt.addSelectParam(identifier);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);
    }
}
