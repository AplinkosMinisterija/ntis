package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisCarsDBServiceGen;
import lt.project.ntis.dao.NtisCarsDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisCarsDBService extends NtisCarsDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisCarsDBService.class);

   public NtisCarsDBService() {
   }
   @Override
   public NtisCarsDAO newRecord() {
      NtisCarsDAO daoObject = super.newRecord();
      return daoObject;
   }

}