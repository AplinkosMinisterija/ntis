package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisCwFilesDBServiceGen;
import lt.project.ntis.dao.NtisCwFilesDAO;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.SelectParamValue;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisCwFilesDBService extends NtisCwFilesDBServiceGen {
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(NtisCwFilesDBService.class);

    public NtisCwFilesDBService() {
    }

    @Override
    public NtisCwFilesDAO newRecord() {
        NtisCwFilesDAO daoObject = super.newRecord();
        return daoObject;
    }

    public NtisCwFilesDAO loadRecordByFileId(Connection conn, Object identifier) throws Exception {
        return this.loadRecordByParams(conn, " WHERE CWF_FIL_ID = ?::int for update ", new SelectParamValue(Utils.getDouble(identifier)));
    }

}