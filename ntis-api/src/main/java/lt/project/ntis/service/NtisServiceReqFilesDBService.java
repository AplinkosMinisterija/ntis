package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisServiceReqFilesDBServiceGen;
import lt.project.ntis.dao.NtisServiceReqFilesDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisServiceReqFilesDBService extends NtisServiceReqFilesDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisServiceReqFilesDBService.class);

   public NtisServiceReqFilesDBService() {
   }
   @Override
   public NtisServiceReqFilesDAO newRecord() {
      NtisServiceReqFilesDAO daoObject = super.newRecord();
      return daoObject;
   }

}