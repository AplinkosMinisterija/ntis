package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.modules.admin.dao.SprJobDefinitionsDAO;
import eu.itreegroup.spark.modules.admin.service.gen.SprJobDefinitionsDBServiceGen;

@Service
public class SprJobDefinitionsDBServiceImpl extends SprJobDefinitionsDBServiceGen implements SprJobDefinitionsDBService {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprJobDefinitionsDBServiceImpl.class);

    public SprJobDefinitionsDBServiceImpl() {
    }

    @Override
    public SprJobDefinitionsDAO newRecord() {
        SprJobDefinitionsDAO daoObject = super.newRecord();
        return daoObject;
    }

    @Override
    public SprJobDefinitionsDAO loadRecordByCode(Connection conn, String jde_type, String jde_code) throws Exception {
        log.debug("Start loadRecordByCode");
        log.debug("jde_type: " + jde_type + " jde_code: " + jde_code);
        return this.loadRecordByParams(conn, " WHERE  jde_type = ? and jde_code = ? ", new SelectParamValue(jde_type), new SelectParamValue(jde_code));
    }

    @Override
    public ArrayList<SprJobDefinitionsDAO> loadActiveJobs(Connection conn) throws Exception {
        return (ArrayList<SprJobDefinitionsDAO>) this.loadRecordsByParams(conn,
                " WHERE jde_type = 'SCHEDULER' and JDE_STATUS = 'ENABLED' and JDE_period is not null");
    }
}