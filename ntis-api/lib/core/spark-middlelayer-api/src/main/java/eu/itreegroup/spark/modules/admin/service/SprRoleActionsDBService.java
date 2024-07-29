package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;
import java.util.Date;

import eu.itreegroup.spark.dao.common.SprBaseDBService;
import eu.itreegroup.spark.modules.admin.dao.SprRoleActionsDAO;

public interface SprRoleActionsDBService extends SprBaseDBService<SprRoleActionsDAO> {

    public void deleteDisabledForms(Connection conn, Double rol_id, Double frm_id) throws Exception;

    public void updateRecord(Connection conn, Double rol_id, Double frm_id, Date roa_date_from) throws Exception;

    public SprRoleActionsDAO loadRecord(Connection conn, Double rol_id, Double frm_id) throws Exception;

    public void removeCachedRole(Double rolId);
}