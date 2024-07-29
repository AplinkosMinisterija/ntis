package lt.project.ntis.service;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import lt.project.ntis.dao.NtisServiceMunicipalitiesDAO;
import lt.project.ntis.service.gen.NtisServiceMunicipalitiesDBServiceGen;

@Service
public class NtisServiceMunicipalitiesDBService extends NtisServiceMunicipalitiesDBServiceGen {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(NtisServiceMunicipalitiesDBService.class);

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    public NtisServiceMunicipalitiesDBService() {
    }

    @Override
    public NtisServiceMunicipalitiesDAO newRecord() {
        NtisServiceMunicipalitiesDAO daoObject = super.newRecord();
        return daoObject;
    }

    public void deleteRecordSrv(Connection conn, Double identifier) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("DELETE FROM NTIS_SERVICE_MUNICIPALITIES WHERE SMN_SRV_ID=?::int");
        stmt.setWhereExists(true);
        stmt.addSelectParam(identifier);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);
    }

}