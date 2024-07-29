package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisResearchDBServiceGen;
import lt.project.ntis.dao.NtisResearchDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisResearchDBService extends NtisResearchDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisResearchDBService.class);

   public NtisResearchDBService() {
   }
   @Override
   public NtisResearchDAO newRecord() {
      NtisResearchDAO daoObject = super.newRecord();
      return daoObject;
   }

}