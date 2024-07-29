package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisResearchOrderDBServiceGen;
import lt.project.ntis.dao.NtisResearchOrderDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisResearchOrderDBService extends NtisResearchOrderDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisResearchOrderDBService.class);

   public NtisResearchOrderDBService() {
   }
   @Override
   public NtisResearchOrderDAO newRecord() {
      NtisResearchOrderDAO daoObject = super.newRecord();
      return daoObject;
   }

}