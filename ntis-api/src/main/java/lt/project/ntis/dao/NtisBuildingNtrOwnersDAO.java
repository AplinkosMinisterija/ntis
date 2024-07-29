package lt.project.ntis.dao;

import lt.project.ntis.dao.gen.NtisBuildingNtrOwnersDAOGen;
import java.util.Date;

public class NtisBuildingNtrOwnersDAO extends NtisBuildingNtrOwnersDAOGen {
   public NtisBuildingNtrOwnersDAO() {
      super();
   }
   public NtisBuildingNtrOwnersDAO(Double bno_id, String bno_type, String bno_code, String bno_org_name, String bno_name, String bno_lastname, Double bno_bn_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(bno_id, bno_type, bno_code, bno_org_name, bno_name, bno_lastname, bno_bn_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}