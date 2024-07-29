package eu.itreegroup.spark.modules.admin.service;

import eu.itreegroup.spark.modules.admin.service.gen.SprSessionClosedDBServiceGen;
import eu.itreegroup.spark.modules.admin.dao.SprSessionClosedDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class SprSessionClosedDBServiceImpl extends SprSessionClosedDBServiceGen implements SprSessionClosedDBService {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(SprSessionClosedDBServiceImpl.class);

   public SprSessionClosedDBServiceImpl() {
   }
   @Override
   public SprSessionClosedDAO newRecord() {
      SprSessionClosedDAO daoObject = super.newRecord();
      return daoObject;
   }

}