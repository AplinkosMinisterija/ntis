package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;

import eu.itreegroup.spark.dao.common.SprBaseDBService;
import eu.itreegroup.spark.modules.admin.dao.SprOrgUsersDAO;

public interface SprOrgUsersDBService extends SprBaseDBService<SprOrgUsersDAO> {

    public String getProfileForUserInOrganization(Connection conn, Double userId, Double orgId) throws Exception;

}