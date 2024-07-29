package eu.itreegroup.spark.modules.admin.service;

import eu.itreegroup.spark.modules.admin.service.gen.SprMenuStructuresDBServiceGen;
import eu.itreegroup.spark.modules.admin.dao.SprMenuStructuresDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class SprMenuStructuresDBServiceImpl extends SprMenuStructuresDBServiceGen implements SprMenuStructuresDBService {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(SprMenuStructuresDBServiceImpl.class);

   public SprMenuStructuresDBServiceImpl() {
   }
   @Override
   public SprMenuStructuresDAO newRecord() {
      SprMenuStructuresDAO daoObject = super.newRecord();
      return daoObject;
   }

}