package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisResearchNormDBServiceGen;
import lt.project.ntis.dao.NtisResearchNormDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisResearchNormDBService extends NtisResearchNormDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisResearchNormDBService.class);

   public NtisResearchNormDBService() {
   }
   @Override
   public NtisResearchNormDAO newRecord() {
      NtisResearchNormDAO daoObject = super.newRecord();
      return daoObject;
   }

}