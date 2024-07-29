package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisResearchesDBServiceGen;
import lt.project.ntis.dao.NtisResearchesDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisResearchesDBService extends NtisResearchesDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisResearchesDBService.class);

   public NtisResearchesDBService() {
   }
   @Override
   public NtisResearchesDAO newRecord() {
      NtisResearchesDAO daoObject = super.newRecord();
      return daoObject;
   }

}