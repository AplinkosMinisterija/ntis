package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisAdrStreetsDBServiceGen;
import lt.project.ntis.dao.NtisAdrStreetsDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisAdrStreetsDBService extends NtisAdrStreetsDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisAdrStreetsDBService.class);

   public NtisAdrStreetsDBService() {
   }
   @Override
   public NtisAdrStreetsDAO newRecord() {
      NtisAdrStreetsDAO daoObject = super.newRecord();
      return daoObject;
   }

}