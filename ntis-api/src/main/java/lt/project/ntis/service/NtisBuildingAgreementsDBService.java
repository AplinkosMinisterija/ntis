package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisBuildingAgreementsDBServiceGen;
import lt.project.ntis.dao.NtisBuildingAgreementsDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisBuildingAgreementsDBService extends NtisBuildingAgreementsDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisBuildingAgreementsDBService.class);

   public NtisBuildingAgreementsDBService() {
   }
   @Override
   public NtisBuildingAgreementsDAO newRecord() {
      NtisBuildingAgreementsDAO daoObject = super.newRecord();
      return daoObject;
   }

}