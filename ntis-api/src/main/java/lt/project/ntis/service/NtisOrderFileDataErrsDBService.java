package lt.project.ntis.service;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import lt.project.ntis.dao.NtisOrderFileDataErrsDAO;
import lt.project.ntis.service.gen.NtisOrderFileDataErrsDBServiceGen;

@Service
public class NtisOrderFileDataErrsDBService extends NtisOrderFileDataErrsDBServiceGen {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(NtisOrderFileDataErrsDBService.class);

    private final BaseControllerJDBC baseControllerJDBC;

    public NtisOrderFileDataErrsDBService(BaseControllerJDBC baseControllerJDBC) {
        super();
        this.baseControllerJDBC = baseControllerJDBC;
    }

    @Override
    public NtisOrderFileDataErrsDAO newRecord() {
        NtisOrderFileDataErrsDAO daoObject = super.newRecord();
        return daoObject;
    }

    public void deleteRecordByOrfId(Connection conn, Double identifier) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("DELETE FROM NTIS_ORDER_FILE_DATA_ERRS WHERE ORFDE_ORF_ID=?::int ");
        stmt.setWhereExists(true);
        stmt.addSelectParam(identifier);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);
    }
}