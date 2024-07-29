package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisAgglomerationsDBServiceGen;
import lt.project.ntis.dao.NtisAgglomerationsDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisAgglomerationsDBService extends NtisAgglomerationsDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisAgglomerationsDBService.class);

   public NtisAgglomerationsDBService() {
   }
   @Override
   public NtisAgglomerationsDAO newRecord() {
      NtisAgglomerationsDAO daoObject = super.newRecord();
      return daoObject;
   }

}