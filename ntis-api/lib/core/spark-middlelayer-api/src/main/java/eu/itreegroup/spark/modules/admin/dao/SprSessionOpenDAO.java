package eu.itreegroup.spark.modules.admin.dao;

import java.io.Serializable;
import java.util.Date;

import eu.itreegroup.spark.modules.admin.dao.gen.SprSessionOpenDAOGen;

public class SprSessionOpenDAO extends SprSessionOpenDAOGen implements Serializable {

    private static final long serialVersionUID = 7331540280207695301L;

    public SprSessionOpenDAO() {
        super();
    }

    public SprSessionOpenDAO(Double ses_id, String ses_key, String ses_username, Date ses_login_time, Date ses_logout_time, String ses_logout_cause,
            Date ses_last_action_time, String ses_login_method, String ses_ip, String ses_os, String ses_browser, Double ses_screen_width,
            Double ses_screen_height, String ses_person_name, String ses_person_surname, String ses_role, String ses_language, String ses_terms_accepted,
            Date ses_terms_accepted_date, Double ses_subscripted_month, Date ses_date_from, Date ses_date_to, String ses_2fa_confirmed, String ses_2fa_code,
            String ses_locked_form_code, String ses_confirmed_release_version, String ses_rol_type, String ses_usr_type, String ses_profile_token,
            Double ses_rol_id, Double ses_per_id, Double ses_usr_id, Double ses_org_id, Date ses_password_reset_req_date, Double rec_version,
            Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01,
            String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
        super(ses_id, ses_key, ses_username, ses_login_time, ses_logout_time, ses_logout_cause, ses_last_action_time, ses_login_method, ses_ip, ses_os,
                ses_browser, ses_screen_width, ses_screen_height, ses_person_name, ses_person_surname, ses_role, ses_language, ses_terms_accepted,
                ses_terms_accepted_date, ses_subscripted_month, ses_date_from, ses_date_to, ses_2fa_confirmed, ses_2fa_code, ses_locked_form_code,
                ses_confirmed_release_version, ses_rol_type, ses_usr_type, ses_profile_token, ses_rol_id, ses_per_id, ses_usr_id, ses_org_id,
                ses_password_reset_req_date, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05,
                d01, d02, d03, d04, d05);
    }

    public void sessionIsLoaded() {
        super.markObjectAsInitial();
    }
}