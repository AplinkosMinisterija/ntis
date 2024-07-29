package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisFacilityModelDBServiceGen;
import lt.project.ntis.dao.NtisFacilityModelDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisFacilityModelDBService extends NtisFacilityModelDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisFacilityModelDBService.class);

   public NtisFacilityModelDBService() {
   }
   @Override
   public NtisFacilityModelDAO newRecord() {
      NtisFacilityModelDAO daoObject = super.newRecord();
      return daoObject;
   }

}