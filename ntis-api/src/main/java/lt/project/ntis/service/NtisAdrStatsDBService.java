package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisAdrStatsDBServiceGen;
import lt.project.ntis.dao.NtisAdrStatsDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisAdrStatsDBService extends NtisAdrStatsDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisAdrStatsDBService.class);

   public NtisAdrStatsDBService() {
   }
   @Override
   public NtisAdrStatsDAO newRecord() {
      NtisAdrStatsDAO daoObject = super.newRecord();
      return daoObject;
   }

}