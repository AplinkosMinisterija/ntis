package lt.project.ntis.dao;

import java.util.Date;

import lt.project.ntis.dao.gen.NtisBuildingNtrsDAOGen;

public class NtisBuildingNtrsDAO extends NtisBuildingNtrsDAOGen {

    public NtisBuildingNtrsDAO() {
        super();
    }

    public NtisBuildingNtrsDAO(Double bn_id, Double bn_aob_code, String bn_status, String bn_status_desc, String bn_obj_inv_code, String bn_reg_nr,
            Date bn_reg_date, Double bn_adr_id, String bn_municipality_code, String bn_municipality, String bn_sen_code, String bn_sen_name,
            String bn_place_code, String bn_place_name, String bn_street_code, String bn_street, String bn_house_nr, String bn_housing_nr, String bn_flat_nr,
            Double bn_coordinate_latitude_ntr, Double bn_coordinate_longitude_ntr, Double bn_coordinate_latitude_adr, Double bn_coordinate_longitude_adr,
            String bn_obj_inv_parent_code, Date bn_object_inv_date, Double bn_object_type, String bn_object_name, Double bn_pask_type, String bn_pask_name,
            Double bn_construction_start_year, Double bn_construction_end_year, Double bn_construction_completion, Double bn_total_area, Double bn_useable_area,
            Double bn_living_area, String bn_wastewater_treatment, String bn_water_supply, String bn_declr_type, Double bn_live_count, Double bn_ad_id,
            Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05,
            String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05, Date bn_date_from, Date bn_date_to) {
        super(bn_id, bn_aob_code, bn_status, bn_status_desc, bn_obj_inv_code, bn_reg_nr, bn_reg_date, bn_adr_id, bn_municipality_code, bn_municipality,
                bn_sen_code, bn_sen_name, bn_place_code, bn_place_name, bn_street_code, bn_street, bn_house_nr, bn_housing_nr, bn_flat_nr,
                bn_coordinate_latitude_ntr, bn_coordinate_longitude_ntr, bn_coordinate_latitude_adr, bn_coordinate_longitude_adr, bn_obj_inv_parent_code,
                bn_object_inv_date, bn_object_type, bn_object_name, bn_pask_type, bn_pask_name, bn_construction_start_year, bn_construction_end_year,
                bn_construction_completion, bn_total_area, bn_useable_area, bn_living_area, bn_wastewater_treatment, bn_water_supply, bn_declr_type,
                bn_live_count, bn_ad_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01,
                d02, d03, d04, d05, bn_date_from, bn_date_to);
    }

}