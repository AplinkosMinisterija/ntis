package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisBuildingNtrOwnersDBServiceGen;
import lt.project.ntis.dao.NtisBuildingNtrOwnersDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisBuildingNtrOwnersDBService extends NtisBuildingNtrOwnersDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisBuildingNtrOwnersDBService.class);

   public NtisBuildingNtrOwnersDBService() {
   }
   @Override
   public NtisBuildingNtrOwnersDAO newRecord() {
      NtisBuildingNtrOwnersDAO daoObject = super.newRecord();
      return daoObject;
   }

}