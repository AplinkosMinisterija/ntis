package eu.itreegroup.spark.modules.admin.service;

import eu.itreegroup.spark.modules.admin.service.gen.SprApiKeysDBServiceGen;
import eu.itreegroup.spark.modules.admin.dao.SprApiKeysDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class SprApiKeysDBServiceImpl extends SprApiKeysDBServiceGen implements SprApiKeysDBService {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(SprApiKeysDBServiceImpl.class);

   public SprApiKeysDBServiceImpl() {
   }
   @Override
   public SprApiKeysDAO newRecord() {
      SprApiKeysDAO daoObject = super.newRecord();
      return daoObject;
   }

}