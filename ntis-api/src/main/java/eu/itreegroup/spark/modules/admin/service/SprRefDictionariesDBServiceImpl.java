package eu.itreegroup.spark.modules.admin.service;

import eu.itreegroup.spark.modules.admin.service.gen.SprRefDictionariesDBServiceGen;
import eu.itreegroup.spark.modules.admin.dao.SprRefDictionariesDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class SprRefDictionariesDBServiceImpl extends SprRefDictionariesDBServiceGen implements SprRefDictionariesDBService {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(SprRefDictionariesDBServiceImpl.class);

   public SprRefDictionariesDBServiceImpl() {
   }
   @Override
   public SprRefDictionariesDAO newRecord() {
      SprRefDictionariesDAO daoObject = super.newRecord();
      return daoObject;
   }

}