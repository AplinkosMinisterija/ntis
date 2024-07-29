package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisOrderFilesDBServiceGen;
import lt.project.ntis.dao.NtisOrderFilesDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisOrderFilesDBService extends NtisOrderFilesDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisOrderFilesDBService.class);

   public NtisOrderFilesDBService() {
   }
   @Override
   public NtisOrderFilesDAO newRecord() {
      NtisOrderFilesDAO daoObject = super.newRecord();
      return daoObject;
   }

}