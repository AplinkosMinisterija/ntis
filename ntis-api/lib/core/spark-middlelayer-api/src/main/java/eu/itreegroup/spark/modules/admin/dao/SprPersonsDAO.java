package eu.itreegroup.spark.modules.admin.dao;

import java.util.Date;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.dao.common.SprBaseDBServiceImpl;
import eu.itreegroup.spark.modules.admin.dao.gen.SprPersonsDAOGen;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;

public class SprPersonsDAO extends SprPersonsDAOGen {

    public static String EMAIL_FORMAT_PROPERTY = "EMAIL_FORMAT";

    public static String EMAIL_FORMAT_DEFAULT = "^(?=.{1,254}$)(?=.{1,64}@)[-!#$%&'*+/0-9=?A-Z^_`a-z{|}~]+(\\.[-!#$%&'*+/0-9=?A-Z^_`a-z{|}~]+)*@[A-Za-z0-9]([A-Za-z0-9-]{0,61}[A-Za-z0-9])?(\\.[A-Za-z0-9]([A-Za-z0-9-]{0,61}[A-Za-z0-9])?)*$";

    public static final String DB_ERROR_EMAIL_FORMAT = "db.field.email.Format";

    private boolean performSyncWithUser = true;

    public SprPersonsDAO() {
        super();
    }

    public SprPersonsDAO(Double per_id, String per_name, String per_surname, String per_data_confirmed, Date per_confirmation_date, Date per_date_of_birth,
            String per_code_exists, String per_code, String per_phone_number, String per_email, String per_email_confirmed, String per_document_type,
            String per_document_number, Date per_document_valid_until, String per_lrt_resident, String per_address_country_code, String per_address_city,
            String per_address_street, String per_address_post_code, String per_address_house_number, String per_address_flat_number, Double per_photo_fil_id,
            Double per_avatar_fil_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03,
            Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
        super(per_id, per_name, per_surname, per_data_confirmed, per_confirmation_date, per_date_of_birth, per_code_exists, per_code, per_phone_number,
                per_email, per_email_confirmed, per_document_type, per_document_number, per_document_valid_until, per_lrt_resident, per_address_country_code,
                per_address_city, per_address_street, per_address_post_code, per_address_house_number, per_address_flat_number, per_photo_fil_id,
                per_avatar_fil_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02,
                d03, d04, d05);
    }

    public boolean isPerformSyncWithUser() {
        return performSyncWithUser;
    }

    public void setPerformSyncWithUser(boolean performSyncWithUser) {
        this.performSyncWithUser = performSyncWithUser;
    }

    @Override
    public void validateObject(int operation, SprBaseDBServiceImpl<?> baseService) throws SparkBusinessException {
        super.validateObject(operation, baseService);
        // Added check for empty string
        if (per_email_changed && this.getPer_email() != null &&  !this.getPer_email().isEmpty()) {
            String emailFormatRegEx = baseService.getDbPropertyManager().getPropertyByName(EMAIL_FORMAT_PROPERTY, EMAIL_FORMAT_DEFAULT);
            if ( !this.getPer_email().matches(emailFormatRegEx)) {
                throw new SparkBusinessException(new S2Message("SPR_PERSONS", "PER_EAMIL", DB_ERROR_EMAIL_FORMAT, SparkMessageType.ERROR));
            }
        }

    }
}