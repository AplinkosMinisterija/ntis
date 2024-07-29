package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisFacilityFilesDBServiceGen;
import lt.project.ntis.dao.NtisFacilityFilesDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisFacilityFilesDBService extends NtisFacilityFilesDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisFacilityFilesDBService.class);

   public NtisFacilityFilesDBService() {
   }
   @Override
   public NtisFacilityFilesDAO newRecord() {
      NtisFacilityFilesDAO daoObject = super.newRecord();
      return daoObject;
   }

}