package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisMunicipalitiesDBServiceGen;
import lt.project.ntis.dao.NtisMunicipalitiesDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisMunicipalitiesDBService extends NtisMunicipalitiesDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisMunicipalitiesDBService.class);

   public NtisMunicipalitiesDBService() {
   }
   @Override
   public NtisMunicipalitiesDAO newRecord() {
      NtisMunicipalitiesDAO daoObject = super.newRecord();
      return daoObject;
   }

}