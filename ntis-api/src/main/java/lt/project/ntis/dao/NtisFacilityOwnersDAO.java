package lt.project.ntis.dao;

import java.util.Date;

import lt.project.ntis.dao.gen.NtisFacilityOwnersDAOGen;

public class NtisFacilityOwnersDAO extends NtisFacilityOwnersDAOGen {

    public NtisFacilityOwnersDAO() {
        super();
    }

    public NtisFacilityOwnersDAO(Double fo_id, String fo_owner_type, String fo_selected, Date fo_date_from, Date fo_date_to, Double fo_org_id, Double fo_per_id,
            Double fo_wtf_id, Double fo_so_id, Double fo_managing_per_id, Double fo_managing_org_id, Double fo_fo_id, Double rec_version,
            Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01,
            String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
        super(fo_id, fo_owner_type, fo_selected, fo_date_from, fo_date_to, fo_org_id, fo_per_id, fo_wtf_id, fo_so_id, fo_managing_per_id, fo_managing_org_id,
                fo_fo_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04,
                d05);
    }

}