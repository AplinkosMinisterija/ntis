package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisResearchOrderServiceDBServiceGen;
import lt.project.ntis.dao.NtisResearchOrderServiceDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisResearchOrderServiceDBService extends NtisResearchOrderServiceDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisResearchOrderServiceDBService.class);

   public NtisResearchOrderServiceDBService() {
   }
   @Override
   public NtisResearchOrderServiceDAO newRecord() {
      NtisResearchOrderServiceDAO daoObject = super.newRecord();
      return daoObject;
   }

}