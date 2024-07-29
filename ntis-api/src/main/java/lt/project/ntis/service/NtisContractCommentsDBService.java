package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisContractCommentsDBServiceGen;
import lt.project.ntis.dao.NtisContractCommentsDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisContractCommentsDBService extends NtisContractCommentsDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisContractCommentsDBService.class);

   public NtisContractCommentsDBService() {
   }
   @Override
   public NtisContractCommentsDAO newRecord() {
      NtisContractCommentsDAO daoObject = super.newRecord();
      return daoObject;
   }

}