package eu.itreegroup.spark.modules.admin.service;

import eu.itreegroup.spark.modules.admin.service.gen.SprNewsCommentsDBServiceGen;
import eu.itreegroup.spark.modules.admin.dao.SprNewsCommentsDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class SprNewsCommentsDBServiceImpl extends SprNewsCommentsDBServiceGen implements SprNewsCommentsDBService {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(SprNewsCommentsDBServiceImpl.class);

   public SprNewsCommentsDBServiceImpl() {
   }
   @Override
   public SprNewsCommentsDAO newRecord() {
      SprNewsCommentsDAO daoObject = super.newRecord();
      return daoObject;
   }

}