package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisAdrPatLrsDBServiceGen;
import lt.project.ntis.dao.NtisAdrPatLrsDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisAdrPatLrsDBService extends NtisAdrPatLrsDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisAdrPatLrsDBService.class);

   public NtisAdrPatLrsDBService() {
   }
   @Override
   public NtisAdrPatLrsDAO newRecord() {
      NtisAdrPatLrsDAO daoObject = super.newRecord();
      return daoObject;
   }

}