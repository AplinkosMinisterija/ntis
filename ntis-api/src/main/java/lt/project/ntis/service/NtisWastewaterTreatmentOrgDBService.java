package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisWastewaterTreatmentOrgDBServiceGen;
import lt.project.ntis.dao.NtisWastewaterTreatmentOrgDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisWastewaterTreatmentOrgDBService extends NtisWastewaterTreatmentOrgDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisWastewaterTreatmentOrgDBService.class);

   public NtisWastewaterTreatmentOrgDBService() {
   }
   @Override
   public NtisWastewaterTreatmentOrgDAO newRecord() {
      NtisWastewaterTreatmentOrgDAO daoObject = super.newRecord();
      return daoObject;
   }

}