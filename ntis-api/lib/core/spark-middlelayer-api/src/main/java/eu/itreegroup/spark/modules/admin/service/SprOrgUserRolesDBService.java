package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;

import eu.itreegroup.spark.dao.common.SprBaseDBService;
import eu.itreegroup.spark.modules.admin.dao.SprOrgUserRolesDAO;

public interface SprOrgUserRolesDBService extends SprBaseDBService<SprOrgUserRolesDAO> {

    public static final String USER_ORGANIZATION_PROFILE_TOKEN_REBUILD = "userOrganizationProfileTokenRebuild";

    public void refreshUserOrgProfile(Connection conn, Double orgId, Double userId) throws Exception;
}