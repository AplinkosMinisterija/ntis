package eu.itreegroup.spark.modules.admin.service;

import eu.itreegroup.spark.modules.admin.service.gen.SprQuestionAnswersDBServiceGen;
import eu.itreegroup.spark.modules.admin.dao.SprQuestionAnswersDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class SprQuestionAnswersDBServiceImpl extends SprQuestionAnswersDBServiceGen implements SprQuestionAnswersDBService {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(SprQuestionAnswersDBServiceImpl.class);

   public SprQuestionAnswersDBServiceImpl() {
   }
   @Override
   public SprQuestionAnswersDAO newRecord() {
      SprQuestionAnswersDAO daoObject = super.newRecord();
      return daoObject;
   }

}