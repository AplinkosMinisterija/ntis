package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisOrderCompletedWorksDBServiceGen;
import lt.project.ntis.dao.NtisOrderCompletedWorksDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisOrderCompletedWorksDBService extends NtisOrderCompletedWorksDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisOrderCompletedWorksDBService.class);

   public NtisOrderCompletedWorksDBService() {
   }
   @Override
   public NtisOrderCompletedWorksDAO newRecord() {
      NtisOrderCompletedWorksDAO daoObject = super.newRecord();
      return daoObject;
   }

}