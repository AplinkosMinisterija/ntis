package eu.itreegroup.spark.modules.admin.service;

import eu.itreegroup.spark.modules.admin.service.gen.SprNewsFilesDBServiceGen;
import eu.itreegroup.spark.modules.admin.dao.SprNewsFilesDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class SprNewsFilesDBServiceImpl extends SprNewsFilesDBServiceGen implements SprNewsFilesDBService {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(SprNewsFilesDBServiceImpl.class);

   public SprNewsFilesDBServiceImpl() {
   }
   @Override
   public SprNewsFilesDAO newRecord() {
      SprNewsFilesDAO daoObject = super.newRecord();
      return daoObject;
   }

}