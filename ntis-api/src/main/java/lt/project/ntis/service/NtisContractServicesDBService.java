package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisContractServicesDBServiceGen;
import lt.project.ntis.dao.NtisContractServicesDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisContractServicesDBService extends NtisContractServicesDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisContractServicesDBService.class);

   public NtisContractServicesDBService() {
   }
   @Override
   public NtisContractServicesDAO newRecord() {
      NtisContractServicesDAO daoObject = super.newRecord();
      return daoObject;
   }

}