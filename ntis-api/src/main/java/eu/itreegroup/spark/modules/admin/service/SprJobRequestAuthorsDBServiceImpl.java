package eu.itreegroup.spark.modules.admin.service;

import eu.itreegroup.spark.modules.admin.service.gen.SprJobRequestAuthorsDBServiceGen;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestAuthorsDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class SprJobRequestAuthorsDBServiceImpl extends SprJobRequestAuthorsDBServiceGen implements SprJobRequestAuthorsDBService {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(SprJobRequestAuthorsDBServiceImpl.class);

   public SprJobRequestAuthorsDBServiceImpl() {
   }
   @Override
   public SprJobRequestAuthorsDAO newRecord() {
      SprJobRequestAuthorsDAO daoObject = super.newRecord();
      return daoObject;
   }

}