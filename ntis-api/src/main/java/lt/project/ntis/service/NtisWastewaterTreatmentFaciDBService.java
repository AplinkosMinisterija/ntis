package lt.project.ntis.service;

import java.sql.Connection;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import lt.project.ntis.dao.NtisWastewaterTreatmentFaciDAO;
import lt.project.ntis.service.gen.NtisWastewaterTreatmentFaciDBServiceGen;

@Service
public class NtisWastewaterTreatmentFaciDBService extends NtisWastewaterTreatmentFaciDBServiceGen {

    @Autowired
    BaseControllerJDBC queryController;

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(NtisWastewaterTreatmentFaciDBService.class);

    public NtisWastewaterTreatmentFaciDBService() {
    }

    @Override
    public NtisWastewaterTreatmentFaciDAO newRecord() {
        NtisWastewaterTreatmentFaciDAO daoObject = super.newRecord();
        return daoObject;
    }

    public ArrayList<SprFile> getFile(Connection conn, Double fileId) throws Exception {

        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT FIL_CONTENT_TYPE, " + //
                "FIL_KEY, " + //
                "FIL_NAME, " + //
                "FIL_SIZE, " + //
                "FIL_STATUS, " + //
                "FIL_STATUS_DATE " + //
                "FROM SPARK.SPR_FILES ");
        stmt.addParam4WherePart("FIL_ID = ?::int ", fileId);

        return (ArrayList<SprFile>) (queryController.selectQueryAsObjectArrayList(conn, stmt, SprFile.class));

    }
}