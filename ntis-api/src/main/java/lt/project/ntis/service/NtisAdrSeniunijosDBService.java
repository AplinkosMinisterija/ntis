package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisAdrSeniunijosDBServiceGen;
import lt.project.ntis.dao.NtisAdrSeniunijosDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisAdrSeniunijosDBService extends NtisAdrSeniunijosDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisAdrSeniunijosDBService.class);

   public NtisAdrSeniunijosDBService() {
   }
   @Override
   public NtisAdrSeniunijosDAO newRecord() {
      NtisAdrSeniunijosDAO daoObject = super.newRecord();
      return daoObject;
   }

}