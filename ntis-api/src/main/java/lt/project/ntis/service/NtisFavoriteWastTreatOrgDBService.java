package lt.project.ntis.service;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import lt.project.ntis.dao.NtisFavoriteWastTreatOrgDAO;
import lt.project.ntis.service.gen.NtisFavoriteWastTreatOrgDBServiceGen;

@Service
public class NtisFavoriteWastTreatOrgDBService extends NtisFavoriteWastTreatOrgDBServiceGen {

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(NtisFavoriteWastTreatOrgDBService.class);

    public NtisFavoriteWastTreatOrgDBService() {
    }

    @Override
    public NtisFavoriteWastTreatOrgDAO newRecord() {
        NtisFavoriteWastTreatOrgDAO daoObject = super.newRecord();
        return daoObject;
    }

    public void deleteFavoritesForOrganizations(Connection conn, Double orgId) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("delete from NTIS_FAVORITE_WAST_TREAT_ORGS where Fwto_org_id = ?::int");
        stmt.addSelectParam(orgId);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);
    }

}