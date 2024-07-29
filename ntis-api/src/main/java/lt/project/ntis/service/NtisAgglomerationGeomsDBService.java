package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisAgglomerationGeomsDBServiceGen;
import lt.project.ntis.dao.NtisAgglomerationGeomsDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisAgglomerationGeomsDBService extends NtisAgglomerationGeomsDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisAgglomerationGeomsDBService.class);

   public NtisAgglomerationGeomsDBService() {
   }
   @Override
   public NtisAgglomerationGeomsDAO newRecord() {
      NtisAgglomerationGeomsDAO daoObject = super.newRecord();
      return daoObject;
   }

}