package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisMessagesDBServiceGen;
import lt.project.ntis.dao.NtisMessagesDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisMessagesDBService extends NtisMessagesDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisMessagesDBService.class);

   public NtisMessagesDBService() {
   }
   @Override
   public NtisMessagesDAO newRecord() {
      NtisMessagesDAO daoObject = super.newRecord();
      return daoObject;
   }

}