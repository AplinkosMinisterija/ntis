package eu.itreegroup.spark.modules.tasks.dao;

import java.util.Date;

import eu.itreegroup.spark.modules.tasks.dao.gen.SprTasksDAOGen;

public class SprTasksDAO extends SprTasksDAOGen {

    public SprTasksDAO() {
        super();
    }

    public SprTasksDAO(Double tas_id, String tas_name, String tas_type, String tas_status, Date tas_date_from, Date tas_date_to, String tas_reject_reason,
            String tas_priority, String tas_repetitive, String tas_description, Double tas_usr_id, Double tas_tas_id, Double rec_version,
            Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01,
            String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
        super(tas_id, tas_name, tas_type, tas_status, tas_date_from, tas_date_to, tas_reject_reason, tas_priority, tas_repetitive, tas_description, tas_usr_id,
                tas_tas_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04,
                d05);
    }
}