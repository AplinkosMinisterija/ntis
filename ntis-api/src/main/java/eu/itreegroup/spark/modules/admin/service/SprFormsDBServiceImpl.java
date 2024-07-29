package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.modules.admin.dao.SprFormsDAO;
import eu.itreegroup.spark.modules.admin.service.gen.SprFormsDBServiceGen;

@Service
public class SprFormsDBServiceImpl extends SprFormsDBServiceGen implements SprFormsDBService {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprFormsDBServiceImpl.class);

    public SprFormsDBServiceImpl() {
    }

    @Override
    public SprFormsDAO newRecord() {
        SprFormsDAO daoObject = super.newRecord();
        return daoObject;
    }

    @Override
    public SprFormsDAO loadRecordByCode(Connection conn, String frmCode) throws Exception {
        return this.loadRecordByParams(conn, "  WHERE FRM_CODE = ? ", new SelectParamValue(frmCode));
    }
}