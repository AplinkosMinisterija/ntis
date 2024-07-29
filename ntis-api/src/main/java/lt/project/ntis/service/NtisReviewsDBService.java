package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisReviewsDBServiceGen;
import lt.project.ntis.dao.NtisReviewsDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisReviewsDBService extends NtisReviewsDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisReviewsDBService.class);

   public NtisReviewsDBService() {
   }
   @Override
   public NtisReviewsDAO newRecord() {
      NtisReviewsDAO daoObject = super.newRecord();
      return daoObject;
   }

}