package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisAdrResidencesDBServiceGen;
import lt.project.ntis.dao.NtisAdrResidencesDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisAdrResidencesDBService extends NtisAdrResidencesDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisAdrResidencesDBService.class);

   public NtisAdrResidencesDBService() {
   }
   @Override
   public NtisAdrResidencesDAO newRecord() {
      NtisAdrResidencesDAO daoObject = super.newRecord();
      return daoObject;
   }

}