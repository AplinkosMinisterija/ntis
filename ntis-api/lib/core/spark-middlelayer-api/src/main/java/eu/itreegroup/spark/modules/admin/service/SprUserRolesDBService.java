package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;

import eu.itreegroup.spark.dao.common.SprBaseDBService;
import eu.itreegroup.spark.modules.admin.dao.SprUserRolesDAO;

public interface SprUserRolesDBService extends SprBaseDBService<SprUserRolesDAO> {

    public void refreshUserProfile(Connection conn, Double userId) throws Exception;
}