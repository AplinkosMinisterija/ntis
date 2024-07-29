package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;

import eu.itreegroup.spark.dao.common.SprBaseDBService;
import eu.itreegroup.spark.enums.EidType;
import eu.itreegroup.spark.modules.admin.dao.SprUserExtIdentificationsDAO;

public interface SprUserExtIdentificationsDBService extends SprBaseDBService<SprUserExtIdentificationsDAO> {

    public Double getUserId(Connection conn, EidType identificationType, String identificationCode) throws Exception;

}