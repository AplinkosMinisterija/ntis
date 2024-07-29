package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.modules.admin.dao.SprFormDataFiltersDAO;
import eu.itreegroup.spark.modules.admin.service.gen.SprFormDataFiltersDBServiceGen;

@Service
public class SprFormDataFiltersDBServiceImpl extends SprFormDataFiltersDBServiceGen implements SprFormDataFiltersDBService {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprFormDataFiltersDBServiceImpl.class);

    public SprFormDataFiltersDBServiceImpl() {
    }

    @Override
    public SprFormDataFiltersDAO newRecord() {
        SprFormDataFiltersDAO daoObject = super.newRecord();
        return daoObject;
    }

    @Override
    public SprFormDataFiltersDAO loadFilterForUser(Connection conn, Object identifier, Double userId) throws Exception {
        return this.loadRecordByParams(conn, " WHERE FDF_ID = ?::int and FDF_USR_ID = ?::int ", new SelectParamValue(Utils.getLong(identifier)),
                new SelectParamValue(userId));
    }
}