package lt.project.ntis.service;

import lt.project.ntis.service.gen.NtisServiceReqStatusLogsDBServiceGen;
import lt.project.ntis.dao.NtisServiceReqStatusLogsDAO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class NtisServiceReqStatusLogsDBService extends NtisServiceReqStatusLogsDBServiceGen {
   @SuppressWarnings("unused")
   private static final Logger log = LoggerFactory.getLogger(NtisServiceReqStatusLogsDBService.class);

   public NtisServiceReqStatusLogsDBService() {
   }
   @Override
   public NtisServiceReqStatusLogsDAO newRecord() {
      NtisServiceReqStatusLogsDAO daoObject = super.newRecord();
      return daoObject;
   }

}