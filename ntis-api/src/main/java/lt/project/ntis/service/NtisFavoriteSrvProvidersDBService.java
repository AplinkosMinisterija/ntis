package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisFavoriteSrvProvidersDBServiceGen;
import lt.project.ntis.dao.NtisFavoriteSrvProvidersDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisFavoriteSrvProvidersDBService extends NtisFavoriteSrvProvidersDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisFavoriteSrvProvidersDBService.class);

   public NtisFavoriteSrvProvidersDBService() {
   }
   @Override
   public NtisFavoriteSrvProvidersDAO newRecord() {
      NtisFavoriteSrvProvidersDAO daoObject = super.newRecord();
      return daoObject;
   }

}