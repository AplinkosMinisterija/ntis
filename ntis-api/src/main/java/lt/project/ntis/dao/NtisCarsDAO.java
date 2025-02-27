package lt.project.ntis.dao;

import java.util.Date;

import lt.project.ntis.dao.gen.NtisCarsDAOGen;
import lt.project.ntis.models.NtisCar;

public class NtisCarsDAO extends NtisCarsDAOGen {

    public NtisCarsDAO() {
        super();
    }

    public NtisCarsDAO(Double cr_id, String cr_reg_no, String cr_model, Double cr_capacity, Double cr_tube_length, Date cr_date_from, Date cr_date_to,
            Double cr_org_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03,
            Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05, String cr_type) {
        super(cr_id, cr_reg_no, cr_model, cr_capacity, cr_tube_length, cr_date_from, cr_date_to, cr_org_id, rec_version, rec_create_timestamp, rec_userid,
                rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05, cr_type);
    }

    public NtisCar getNtisCarModel() {
        NtisCar car = new NtisCar();
        if (this.getCr_id() != null) {
            car.setId(this.getCr_id().intValue());
        }
        car.setRegNo(this.getCr_reg_no());
        car.setCarType(this.getCr_type());
        car.setModel(this.getCr_model());
        car.setCapacity(this.getCr_capacity());
        car.setTubeLength(this.getCr_tube_length());
        car.setIsInUse(this.getCr_date_to() == null || new Date().compareTo(this.getCr_date_to()) < 0);
        return car;
    }
}