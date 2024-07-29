package eu.itreegroup.spark.modules.admin.dao;

import java.util.Date;

import eu.itreegroup.spark.modules.admin.dao.gen.SprSessionClosedDAOGen;

public class SprSessionClosedDAO extends SprSessionClosedDAOGen {

    public SprSessionClosedDAO() {
        super();
    }

    public SprSessionClosedDAO(Double sec_id, String sec_key, String sec_username, Date sec_login_time, Date sec_logout_time, String sec_logout_cause,
            Date sec_last_action_time, String sec_login_method, String sec_ip, String sec_os, String sec_browser, Double sec_screen_width,
            Double sec_screen_height, String sec_person_name, String sec_person_surname, String sec_role, String sec_language, String sec_terms_accepted,
            Date sec_terms_accepted_date, Double sec_subscripted_month, Date sec_date_from, Date sec_date_to, String sec_2fa_confirmed, String sec_2fa_code,
            String sec_locked_form_code, String sec_confirmed_release_version, String sec_rol_type, String sec_usr_type, String sec_profile_token,
            Double sec_rol_id, Double sec_per_id, Double sec_usr_id, Double sec_org_id, Date sec_password_reset_req_date, Double rec_version,
            Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01,
            String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
        super(sec_id, sec_key, sec_username, sec_login_time, sec_logout_time, sec_logout_cause, sec_last_action_time, sec_login_method, sec_ip, sec_os,
                sec_browser, sec_screen_width, sec_screen_height, sec_person_name, sec_person_surname, sec_role, sec_language, sec_terms_accepted,
                sec_terms_accepted_date, sec_subscripted_month, sec_date_from, sec_date_to, sec_2fa_confirmed, sec_2fa_code, sec_locked_form_code,
                sec_confirmed_release_version, sec_rol_type, sec_usr_type, sec_profile_token, sec_rol_id, sec_per_id, sec_usr_id, sec_org_id,
                sec_password_reset_req_date, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05,
                d01, d02, d03, d04, d05);
    }

    public void initFromOpenSession(SprSessionOpenDAO openSession) {
        this.setSec_id(openSession.getSes_id());
        this.setSec_key(openSession.getSes_key());
        this.setSec_usr_id(openSession.getSes_usr_id());
        this.setSec_username(openSession.getSes_username());
        this.setSec_login_time(openSession.getSes_login_time());
        this.setSec_logout_time(openSession.getSes_logout_time());
        this.setSec_logout_cause(openSession.getSes_logout_cause());
        this.setSec_last_action_time(openSession.getSes_last_action_time());
        this.setSec_login_method(openSession.getSes_login_method());
        this.setSec_ip(openSession.getSes_ip());
        this.setSec_os(openSession.getSes_os());
        this.setSec_browser(openSession.getSes_browser());
        this.setSec_screen_width(openSession.getSes_screen_width());
        this.setSec_screen_height(openSession.getSes_screen_height());
        this.setSec_person_name(openSession.getSes_person_name());
        this.setSec_person_surname(openSession.getSes_person_surname());
        this.setSec_role(openSession.getSes_role());
        this.setSec_org_id(openSession.getSes_org_id());
        this.setSec_language(openSession.getSes_language());
        this.setSec_terms_accepted(openSession.getSes_terms_accepted());
        this.setSec_terms_accepted_date(openSession.getSes_terms_accepted_date());
        this.setSec_subscripted_month(openSession.getSes_subscripted_month());
        this.setSec_date_from(openSession.getSes_date_from());
        this.setSec_date_to(openSession.getSes_date_to());
        this.setSec_2fa_confirmed(openSession.getSes_2fa_confirmed());
        this.setSec_locked_form_code(openSession.getSes_locked_form_code());
        this.setSec_confirmed_release_version(openSession.getSes_confirmed_release_version());
        this.setSec_2fa_code(openSession.getSes_2fa_code());
        this.setSec_profile_token(openSession.getSes_profile_token());
        this.setSec_per_id(openSession.getSes_per_id());
        this.setSec_org_id(openSession.getSes_org_id());
        this.setSec_usr_id(openSession.getSes_usr_id());
        this.setSec_rol_id(openSession.getSes_rol_id());
        this.setSec_rol_type(openSession.getSes_rol_type());
        this.setSec_usr_type(openSession.getSes_usr_type());
        this.setSec_password_reset_req_date(openSession.getSes_password_reset_req_date());
    }
}