package eu.itreegroup.spark.modules.admin.dao;

import eu.itreegroup.spark.modules.admin.dao.gen.SprQuestionAnswersDAOGen;
import java.util.Date;

public class SprQuestionAnswersDAO extends SprQuestionAnswersDAOGen {
   public SprQuestionAnswersDAO() {
      super();
   }
   public SprQuestionAnswersDAO(Double fac_id, String fac_group, String fac_type, String fac_lang, String fac_code, String fac_question, String fac_answer, String fac_content_for_search, Date fac_date_from, Date fac_date_to, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(fac_id, fac_group, fac_type, fac_lang, fac_code, fac_question, fac_answer, fac_content_for_search, fac_date_from, fac_date_to, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}