package eu.itreegroup.spark.modules.admin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.modules.admin.dao.SprAuditableTablesDAO;
import eu.itreegroup.spark.modules.admin.service.gen.SprAuditableTablesDBServiceGen;

@Service
public class SprAuditableTablesDBServiceImpl extends SprAuditableTablesDBServiceGen implements SprAuditableTablesDBService {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprAuditableTablesDBServiceImpl.class);

    public SprAuditableTablesDBServiceImpl() {
    }

    @Override
    public SprAuditableTablesDAO newRecord() {
        SprAuditableTablesDAO daoObject = super.newRecord();
        return daoObject;
    }

}