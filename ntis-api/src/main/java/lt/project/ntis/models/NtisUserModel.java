package lt.project.ntis.models;

import java.util.Date;

import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;

public class NtisUserModel extends SprUsersDAO {

    private String personCodeConfirmed;

    public NtisUserModel() {
        super();
    }

    public NtisUserModel(Double usr_id, String usr_username, String usr_email, String usr_email_confirmed, String usr_person_name, String usr_person_surname,
            Double usr_wrong_login_attempts, Date usr_lock_date, String usr_salt, String usr_password_algorithm, String usr_password_hash,
            Date usr_password_reset_req_date, Date usr_password_change_date, Date usr_password_next_change_date, String usr_password_history,
            String usr_phone_number, String usr_language, String usr_type, Date usr_date_from, Date usr_date_to, Double usr_subscripted_month,
            String usr_terms_accepted, Date usr_terms_accepted_date, String usr_2fa_key, String usr_2fa_key_confirm, String usr_2fa_used,
            String usr_confirmed_release_version, String usr_profile_token, Double usr_per_id, Double usr_org_id, Double usr_rol_id, Double usr_photo_fil_id,
            Double usr_avatar_fil_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03,
            Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
        super(usr_id, usr_username, usr_email, usr_email_confirmed, usr_person_name, usr_person_surname, usr_wrong_login_attempts, usr_lock_date, usr_salt,
                usr_password_algorithm, usr_password_hash, usr_password_reset_req_date, usr_password_change_date, usr_password_next_change_date,
                usr_password_history, usr_phone_number, usr_language, usr_type, usr_date_from, usr_date_to, usr_subscripted_month, usr_terms_accepted,
                usr_terms_accepted_date, usr_2fa_key, usr_2fa_key_confirm, usr_2fa_used, usr_confirmed_release_version, usr_profile_token, usr_per_id,
                usr_org_id, usr_rol_id, usr_photo_fil_id, usr_avatar_fil_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04,
                n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
    }

    public String getPersonCodeConfirmed() {
        return personCodeConfirmed;
    }

    public void setPersonCodeConfirmed(String personCodeConfirmed) {
        this.personCodeConfirmed = personCodeConfirmed;
    }

}
