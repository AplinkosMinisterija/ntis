package lt.project.ntis.dao;

import java.util.Date;

import lt.project.ntis.dao.gen.NtisAdrAddressesDAOGen;

public class NtisAdrAddressesDAO extends NtisAdrAddressesDAOGen {

    public NtisAdrAddressesDAO() {
        super();
    }

    public NtisAdrAddressesDAO(Double ad_id, String ad_address, String ad_address_search, Double ad_apl_id, Double ad_ads_id, Double ad_coordinate_latitude,
            Double ad_coordinate_longitude, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02,
            Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05,
            Date ad_date_from, Date ad_date_to) {
        super(ad_id, ad_address, ad_address_search, ad_apl_id, ad_ads_id, ad_coordinate_latitude, ad_coordinate_longitude, rec_version, rec_create_timestamp,
                rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05, ad_date_from, ad_date_to);
    }

}