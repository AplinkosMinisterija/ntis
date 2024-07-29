package lt.project.ntis.service;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import lt.project.ntis.dao.NtisOrderFileDataDAO;
import lt.project.ntis.service.gen.NtisOrderFileDataDBServiceGen;

@Service
public class NtisOrderFileDataDBService extends NtisOrderFileDataDBServiceGen {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(NtisOrderFileDataDBService.class);

    private final BaseControllerJDBC baseControllerJDBC;

    public NtisOrderFileDataDBService(BaseControllerJDBC baseControllerJDBC) {
        super();
        this.baseControllerJDBC = baseControllerJDBC;
    }

    @Override
    public NtisOrderFileDataDAO newRecord() {
        NtisOrderFileDataDAO daoObject = super.newRecord();
        return daoObject;
    }

    public void deleteRecordByOrfId(Connection conn, Double identifier) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("DELETE FROM NTIS_ORDER_FILE_DATA WHERE ORFD_ORF_ID=?::int ");
        stmt.setWhereExists(true);
        stmt.addSelectParam(identifier);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);
    }
}