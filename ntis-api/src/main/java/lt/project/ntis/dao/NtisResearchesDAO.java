package lt.project.ntis.dao;

import java.util.Date;

import lt.project.ntis.dao.gen.NtisResearchesDAOGen;

public class NtisResearchesDAO extends NtisResearchesDAOGen {

    public NtisResearchesDAO() {
        super();
    }

    public NtisResearchesDAO(Double res_id, String res_reserch_type, Date res_sample_date, Date res_research_date, Double res_value, String res_description,
            Date res_created, Double res_ord_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02,
            Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05,
            Double res_rn_id) {
        super(res_id, res_reserch_type, res_sample_date, res_research_date, res_value, res_description, res_created, res_ord_id, rec_version,
                rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05, res_rn_id);
    }

}