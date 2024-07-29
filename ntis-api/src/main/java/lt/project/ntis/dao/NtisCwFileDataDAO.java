package lt.project.ntis.dao;

import java.util.Date;

import lt.project.ntis.dao.gen.NtisCwFileDataDAOGen;

public class NtisCwFileDataDAO extends NtisCwFileDataDAOGen {

    public NtisCwFileDataDAO() {
        super();
    }

    public NtisCwFileDataDAO(Double cwfd_id, Double cwfd_eil_nr, String cwfd_pastato_kodas, String cwfd_patalpos_kodas, String cwfd_pastato_adr_kodas,
            String cwfd_savivaldybes_kodas, String cwfd_savivaldybe, String cwfd_seniunijos_kodas, String cwfd_seniunija, String cwfd_gyv_vietos_kodas,
            String cwfd_gyv_vieta, String cwfd_gatves_kodas, String cwfd_gatve, String cwfd_pastato_nr, String cwfd_korpuso_nr, String cwfd_buto_nr,
            String cwfd_statinio_vald_kodas, String cwfd_vandentvarkos_im_kod, String cwfd_nuot_salinimo_budas, String cwfd_prijungimo_data,
            String cwfd_atjungimo_data, String cwfd_status, Double cwfd_cwf_id, Double rec_version, Date rec_create_timestamp, String rec_userid,
            Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05,
            Date d01, Date d02, Date d03, Date d04, Date d05) {
        super(cwfd_id, cwfd_eil_nr, cwfd_pastato_kodas, cwfd_patalpos_kodas, cwfd_pastato_adr_kodas, cwfd_savivaldybes_kodas, cwfd_savivaldybe,
                cwfd_seniunijos_kodas, cwfd_seniunija, cwfd_gyv_vietos_kodas, cwfd_gyv_vieta, cwfd_gatves_kodas, cwfd_gatve, cwfd_pastato_nr, cwfd_korpuso_nr,
                cwfd_buto_nr, cwfd_statinio_vald_kodas, cwfd_vandentvarkos_im_kod, cwfd_nuot_salinimo_budas, cwfd_prijungimo_data, cwfd_atjungimo_data,
                cwfd_status, cwfd_cwf_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01,
                d02, d03, d04, d05);
    }

}