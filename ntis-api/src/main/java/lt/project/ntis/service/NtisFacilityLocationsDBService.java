package lt.project.ntis.service;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.SelectParamValue;
import lt.project.ntis.dao.NtisFacilityLocationsDAO;
import lt.project.ntis.service.gen.NtisFacilityLocationsDBServiceGen;

@Service
public class NtisFacilityLocationsDBService extends NtisFacilityLocationsDBServiceGen {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(NtisFacilityLocationsDBService.class);

    public NtisFacilityLocationsDBService() {
    }

    @Override
    public NtisFacilityLocationsDAO newRecord() {
        NtisFacilityLocationsDAO daoObject = super.newRecord();
        return daoObject;
    }

    public NtisFacilityLocationsDAO getRecordByWtfId(Connection conn, Double wtfId) throws Exception {
        return this.loadRecordByParams(conn, "WHERE fl_wtf_id = ?::int ", new SelectParamValue(wtfId));
    }

}