package lt.project.ntis.service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import lt.project.ntis.dao.NtisServicesDAO;
import lt.project.ntis.service.gen.NtisServicesDBServiceGen;

@Service
public class NtisServicesDBService extends NtisServicesDBServiceGen {

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    FileStorageService fileStorageService;

    public NtisServicesDBService() {
    }

    @Override
    public NtisServicesDAO newRecord() {
        NtisServicesDAO daoObject = super.newRecord();
        return daoObject;
    }

    public SprFile getServiceFile(Connection conn, Double srvId) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT FIL_CONTENT_TYPE, " + //
                "FIL_KEY, " + //
                "FIL_NAME, " + //
                "FIL_SIZE, " + //
                "FIL_STATUS, " + //
                "FIL_STATUS_DATE " + //
                "FROM NTIS.NTIS_SERVICES S " + //
                "JOIN SPARK.SPR_FILES F ON S.SRV_FIL_ID = F.FIL_ID ");
        stmt.addParam4WherePart("SRV_FIL_ID = ?::int ", srvId);
        List<HashMap<String, String>> dataObj = queryController.selectQueryAsDataArrayList(conn, stmt);
        SprFile fileObj = new SprFile();
        for (int i = 0; i < dataObj.size(); i++) {
            HashMap<String, String> rec = dataObj.get(i);
            fileObj.setFil_content_type(rec.get("fil_content_type"));
            fileObj.setFil_key(fileStorageService.encryptFileKey(rec.get("fil_key")));
            fileObj.setFil_name(rec.get("fil_name"));
            fileObj.setFil_size(Utils.getDouble(rec.get("fil_size")));
            fileObj.setFil_status(rec.get("fil_status"));
        }
        return fileObj;
    }

    public SprFile getServiceLabInstructions(Connection conn, Double srvLabInstrFilId) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT FIL_CONTENT_TYPE, " + //
                "FIL_KEY, " + //
                "FIL_NAME, " + //
                "FIL_SIZE, " + //
                "FIL_STATUS, " + //
                "FIL_STATUS_DATE " + //
                "FROM NTIS.NTIS_SERVICES S " + //
                "JOIN SPARK.SPR_FILES F ON S.SRV_LAB_INSTR_FIL_ID = F.FIL_ID ");
        stmt.addParam4WherePart("SRV_LAB_INSTR_FIL_ID = ?::int ", srvLabInstrFilId);
        List<HashMap<String, String>> dataObj = queryController.selectQueryAsDataArrayList(conn, stmt);
        SprFile fileObj = new SprFile();
        for (int i = 0; i < dataObj.size(); i++) {
            HashMap<String, String> rec = dataObj.get(i);
            fileObj.setFil_content_type(rec.get("fil_content_type"));
            fileObj.setFil_key(fileStorageService.encryptFileKey(rec.get("fil_key")));
            fileObj.setFil_name(rec.get("fil_name"));
            fileObj.setFil_size(Utils.getDouble(rec.get("fil_size")));
            fileObj.setFil_status(rec.get("fil_status"));
        }
        return fileObj;
    }

    public SprFile getFileByFileKey(Connection conn, String key) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT  " + //
                "FIL_KEY, " + //
                "FIL_ID, " + //
                "FIL_PATH " + //
                "FROM SPARK.SPR_FILES ");
        stmt.addParam4WherePart("FIL_KEY = ? ", key);
        List<HashMap<String, String>> dataObj = queryController.selectQueryAsDataArrayList(conn, stmt);
        SprFile fileObj = new SprFile();
        for (int i = 0; i < dataObj.size(); i++) {
            HashMap<String, String> rec = dataObj.get(i);
            fileObj.setFil_content_type(rec.get("fil_content_type"));
            fileObj.setFil_key(rec.get("fil_key"));
            fileObj.setFil_name(rec.get("fil_name"));
            fileObj.setFil_size(Utils.getDouble(rec.get("fil_size")));
            fileObj.setFil_status(rec.get("fil_status"));
        }
        return fileObj;
    }
}