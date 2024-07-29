package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;

import eu.itreegroup.spark.dao.common.SprBaseDBService;
import eu.itreegroup.spark.modules.admin.dao.SprRoleDisabledActionsDAO;

public interface SprRoleDisabledActionsDBService extends SprBaseDBService<SprRoleDisabledActionsDAO> {

    public void deleteSpecifiedRoleFormActions(Connection conn, Double fra_id, Double roa_id) throws Exception;

    public void deleteSpecifiedRoleFormActions(Connection conn, Double identifier, String[] frm_ids) throws Exception;

    public void deleteAllRoleFormActions(Connection conn, Double identifier) throws Exception;
}