package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisFacilityUpdateLogDBServiceGen;
import lt.project.ntis.dao.NtisFacilityUpdateLogDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisFacilityUpdateLogDBService extends NtisFacilityUpdateLogDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisFacilityUpdateLogDBService.class);

   public NtisFacilityUpdateLogDBService() {
   }
   @Override
   public NtisFacilityUpdateLogDAO newRecord() {
      NtisFacilityUpdateLogDAO daoObject = super.newRecord();
      return daoObject;
   }

}