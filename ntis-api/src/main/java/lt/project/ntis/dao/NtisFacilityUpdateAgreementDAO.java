package lt.project.ntis.dao;

import java.util.Date;

import lt.project.ntis.dao.gen.NtisFacilityUpdateAgreementDAOGen;

public class NtisFacilityUpdateAgreementDAO extends NtisFacilityUpdateAgreementDAOGen {

    public NtisFacilityUpdateAgreementDAO() {
        super();
    }

    public NtisFacilityUpdateAgreementDAO(Double fua_id, String fua_manufecturer, String fua_model, String fua_type, String fua_state, Date fua_created,
            Double fua_per_id, Double fua_wtf_id, Double fua_confirmed_usr_id, String fua_wtf_old_info_json, String fua_wtf_new_info_json,
            String fua_wtf_object_info_json, Date fua_network_connection_date, Double fua_fil_id, Double fua_bn_id, Double fua_org_id, Double fua_so_id,
            Double fua_req_org_id, String fua_cancellation_reason, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp,
            Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02,
            Date d03, Date d04, Date d05) {
        super(fua_id, fua_manufecturer, fua_model, fua_type, fua_state, fua_created, fua_per_id, fua_wtf_id, fua_confirmed_usr_id, fua_wtf_old_info_json,
                fua_wtf_new_info_json, fua_wtf_object_info_json, fua_network_connection_date, fua_fil_id, fua_bn_id, fua_org_id, fua_so_id, fua_req_org_id,
                fua_cancellation_reason, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01,
                d02, d03, d04, d05);
    }

}