package lt.project.ntis.dao;

import lt.project.ntis.dao.gen.NtisAglomeracijosDAOGen;
import java.util.Date;

public class NtisAglomeracijosDAO extends NtisAglomeracijosDAOGen {
   public NtisAglomeracijosDAO() {
      super();
   }
   public NtisAglomeracijosDAO(Double ogc_fid, String geom, String a_uuid, Double a_ter_id, String pav, Double sav_kodas, Double ge, Double gyv_tankis, Double a_plotas, String a_dok_pav, String a_dok_nr, Date a_dok_data) {
      super(ogc_fid, geom, a_uuid, a_ter_id, pav, sav_kodas, ge, gyv_tankis, a_plotas, a_dok_pav, a_dok_nr, a_dok_data);
   }
}