package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisFacilityUpdateAgreementDBServiceGen;
import lt.project.ntis.dao.NtisFacilityUpdateAgreementDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisFacilityUpdateAgreementDBService extends NtisFacilityUpdateAgreementDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisFacilityUpdateAgreementDBService.class);

   public NtisFacilityUpdateAgreementDBService() {
   }
   @Override
   public NtisFacilityUpdateAgreementDAO newRecord() {
      NtisFacilityUpdateAgreementDAO daoObject = super.newRecord();
      return daoObject;
   }

}