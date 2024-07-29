package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisBuildingNtrsDBServiceGen;
import lt.project.ntis.dao.NtisBuildingNtrsDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisBuildingNtrsDBService extends NtisBuildingNtrsDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisBuildingNtrsDBService.class);

   public NtisBuildingNtrsDBService() {
   }
   @Override
   public NtisBuildingNtrsDAO newRecord() {
      NtisBuildingNtrsDAO daoObject = super.newRecord();
      return daoObject;
   }

}