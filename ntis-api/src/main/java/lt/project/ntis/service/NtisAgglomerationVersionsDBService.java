package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisAgglomerationVersionsDBServiceGen;
import lt.project.ntis.dao.NtisAgglomerationVersionsDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisAgglomerationVersionsDBService extends NtisAgglomerationVersionsDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisAgglomerationVersionsDBService.class);

   public NtisAgglomerationVersionsDBService() {
   }
   @Override
   public NtisAgglomerationVersionsDAO newRecord() {
      NtisAgglomerationVersionsDAO daoObject = super.newRecord();
      return daoObject;
   }

}