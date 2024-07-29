package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisAdrMappingsDBServiceGen;
import lt.project.ntis.dao.NtisAdrMappingsDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisAdrMappingsDBService extends NtisAdrMappingsDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisAdrMappingsDBService.class);

   public NtisAdrMappingsDBService() {
   }
   @Override
   public NtisAdrMappingsDAO newRecord() {
      NtisAdrMappingsDAO daoObject = super.newRecord();
      return daoObject;
   }

}