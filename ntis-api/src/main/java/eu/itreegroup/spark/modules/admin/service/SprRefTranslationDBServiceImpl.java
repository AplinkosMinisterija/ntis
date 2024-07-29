package eu.itreegroup.spark.modules.admin.service;

import eu.itreegroup.spark.modules.admin.service.gen.SprRefTranslationDBServiceGen;
import eu.itreegroup.spark.modules.admin.dao.SprRefTranslationsDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class SprRefTranslationDBServiceImpl extends SprRefTranslationDBServiceGen implements SprRefTranslationDBService {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(SprRefTranslationDBServiceImpl.class);

   public SprRefTranslationDBServiceImpl() {
   }
   @Override
   public SprRefTranslationsDAO newRecord() {
      SprRefTranslationsDAO daoObject = super.newRecord();
      return daoObject;
   }

}