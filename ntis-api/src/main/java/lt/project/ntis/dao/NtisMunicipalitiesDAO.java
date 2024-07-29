package lt.project.ntis.dao;

import java.util.Date;

import lt.project.ntis.dao.gen.NtisMunicipalitiesDAOGen;

public class NtisMunicipalitiesDAO extends NtisMunicipalitiesDAOGen {

    public NtisMunicipalitiesDAO() {
        super();
    }

    public NtisMunicipalitiesDAO(Double mp_id, String mp_code, String mp_name, Date mp_date_from, Date mp_date_to, String rec_userid, Date rec_timestamp,
            Date rec_create_timestamp, Double rec_version) {
        super(mp_id, mp_code, mp_name, mp_date_from, mp_date_to, rec_userid, rec_timestamp, rec_create_timestamp, rec_version);
    }

}