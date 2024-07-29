package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;

import eu.itreegroup.spark.dao.common.SprBaseDBService;
import eu.itreegroup.spark.modules.admin.dao.SprRolesDAO;

public interface SprRolesDBService extends SprBaseDBService<SprRolesDAO> {

    public SprRolesDAO loadRecordByRoleCode(Connection conn, String roleCode) throws Exception;
}