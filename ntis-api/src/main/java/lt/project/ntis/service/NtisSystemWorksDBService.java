package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisSystemWorksDBServiceGen;
import lt.project.ntis.dao.NtisSystemWorksDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisSystemWorksDBService extends NtisSystemWorksDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisSystemWorksDBService.class);

   public NtisSystemWorksDBService() {
   }
   @Override
   public NtisSystemWorksDAO newRecord() {
      NtisSystemWorksDAO daoObject = super.newRecord();
      return daoObject;
   }

}