package eu.itreegroup.spark.modules.admin.service;

import eu.itreegroup.spark.modules.admin.service.gen.SprNewsDBServiceGen;
import eu.itreegroup.spark.modules.admin.dao.SprNewsDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class SprNewsDBServiceImpl extends SprNewsDBServiceGen implements SprNewsDBService {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(SprNewsDBServiceImpl.class);

   public SprNewsDBServiceImpl() {
   }
   @Override
   public SprNewsDAO newRecord() {
      SprNewsDAO daoObject = super.newRecord();
      return daoObject;
   }

}