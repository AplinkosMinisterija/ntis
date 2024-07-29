package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;

import eu.itreegroup.spark.dao.common.SprBaseDBService;
import eu.itreegroup.spark.modules.admin.dao.SprFormDataFiltersDAO;

public interface SprFormDataFiltersDBService extends SprBaseDBService<SprFormDataFiltersDAO> {

    public SprFormDataFiltersDAO loadFilterForUser(Connection conn, Object identifier, Double userId) throws Exception;

}