package lt.project.ntis.service;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.SelectParamValue;
import lt.project.ntis.dao.NtisDischargeWastewaterDAO;
import lt.project.ntis.service.gen.NtisDischargeWastewaterDBServiceGen;

@Service
public class NtisDischargeWastewaterDBService extends NtisDischargeWastewaterDBServiceGen {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(NtisDischargeWastewaterDBService.class);

    public NtisDischargeWastewaterDBService() {
    }

    @Override
    public NtisDischargeWastewaterDAO newRecord() {
        NtisDischargeWastewaterDAO daoObject = super.newRecord();
        return daoObject;
    }

    public NtisDischargeWastewaterDAO getRecordByWtfId(Connection conn, Double dwId) throws Exception {
        return this.loadRecordByParams(conn, "WHERE dw_wtf_id = ?::int ", new SelectParamValue(dwId));
    }

}