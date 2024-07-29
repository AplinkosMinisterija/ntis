package lt.project.ntis.dao;

import java.util.Date;

import lt.project.ntis.dao.gen.NtisBuildingAgreementsDAOGen;

public class NtisBuildingAgreementsDAO extends NtisBuildingAgreementsDAOGen {

    public NtisBuildingAgreementsDAO() {
        super();
    }

    public NtisBuildingAgreementsDAO(Double ba_id, String ba_source, String ba_wastewater_treatment, String ba_state, String ba_rejection_reason,
            Date ba_network_connection_date, Date ba_network_disconnection_date, Date ba_created, Double ba_bn_id, Double ba_fil_id, Double ba_org_id,
            Date ba_manual_network_con_date, Double ba_manual_org_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp,
            Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02,
            Date d03, Date d04, Date d05) {
        super(ba_id, ba_source, ba_wastewater_treatment, ba_state, ba_rejection_reason, ba_network_connection_date, ba_network_disconnection_date, ba_created,
                ba_bn_id, ba_fil_id, ba_org_id, ba_manual_network_con_date, ba_manual_org_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01,
                n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
    }

}