package eu.itreegroup.spark.modules.admin.service;

import eu.itreegroup.spark.modules.admin.service.gen.SprQuestionFilesDBServiceGen;
import eu.itreegroup.spark.modules.admin.dao.SprQuestionFilesDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class SprQuestionFilesDBServiceImpl extends SprQuestionFilesDBServiceGen implements SprQuestionFilesDBService {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(SprQuestionFilesDBServiceImpl.class);

   public SprQuestionFilesDBServiceImpl() {
   }
   @Override
   public SprQuestionFilesDAO newRecord() {
      SprQuestionFilesDAO daoObject = super.newRecord();
      return daoObject;
   }

}