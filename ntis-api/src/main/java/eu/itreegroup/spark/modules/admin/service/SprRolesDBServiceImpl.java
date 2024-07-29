package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.modules.admin.dao.SprRolesDAO;
import eu.itreegroup.spark.modules.admin.service.gen.SprRolesDBServiceGen;

@Service
public class SprRolesDBServiceImpl extends SprRolesDBServiceGen implements SprRolesDBService {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprRolesDBServiceImpl.class);

    public SprRolesDBServiceImpl() {
    }

    @Override
    public SprRolesDAO newRecord() {
        SprRolesDAO daoObject = super.newRecord();
        return daoObject;
    }

    @Override
    public SprRolesDAO loadRecordByRoleCode(Connection conn, String roleCode) throws Exception {
        return this.loadRecordByParams(conn, "  WHERE ROL_CODE = ? ", new SelectParamValue(roleCode));

    }
}