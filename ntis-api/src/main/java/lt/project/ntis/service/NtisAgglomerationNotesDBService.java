package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisAgglomerationNotesDBServiceGen;
import lt.project.ntis.dao.NtisAgglomerationNotesDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisAgglomerationNotesDBService extends NtisAgglomerationNotesDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisAgglomerationNotesDBService.class);

   public NtisAgglomerationNotesDBService() {
   }
   @Override
   public NtisAgglomerationNotesDAO newRecord() {
      NtisAgglomerationNotesDAO daoObject = super.newRecord();
      return daoObject;
   }

}