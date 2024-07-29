package eu.itreegroup.spark.modules.admin.dao;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.itreegroup.spark.modules.admin.dao.gen.SprRefCodesDAOGen;

public class SprRefCodesDAO extends SprRefCodesDAOGen {

    public SprRefCodesDAO() {
        super();
    }

    public SprRefCodesDAO(Double rfc_id, String rfc_domain, String rfc_code, String rfc_meaning, String rfc_description, String rfc_parent_domain,
            String rfc_parent_domain_code, Double rfc_order, Date rfc_date_from, Date rfc_date_to, Double rfc_rfd_id, String rfc_ref_code_1,
            String rfc_ref_code_2, String rfc_ref_code_3, String rfc_ref_code_4, String rfc_ref_code_5, Double rec_version, Date rec_create_timestamp,
            String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04,
            String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
        super(rfc_id, rfc_domain, rfc_code, rfc_meaning, rfc_description, rfc_parent_domain, rfc_parent_domain_code, rfc_order, rfc_date_from, rfc_date_to,
                rfc_rfd_id, rfc_ref_code_1, rfc_ref_code_2, rfc_ref_code_3, rfc_ref_code_4, rfc_ref_code_5, rec_version, rec_create_timestamp, rec_userid,
                rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
    }

    @Override
    @JsonIgnore(false)
    public void setN01(Double n01) {
        super.setN01(n01);
    }

    @Override
    @JsonIgnore(false)
    public Double getN01() {
        return super.getN01();
    }

    @Override
    @JsonIgnore(false)
    public void setN02(Double n02) {
        super.setN02(n02);
    }

    @Override
    @JsonIgnore(false)
    public Double getN02() {
        return super.getN02();
    }

    @Override
    @JsonIgnore(false)
    public void setN03(Double n03) {
        super.setN03(n03);
    }

    @Override
    @JsonIgnore(false)
    public Double getN03() {
        return super.getN03();
    }

    @Override
    @JsonIgnore(false)
    public void setN04(Double n04) {
        super.setN04(n04);
    }

    @Override
    @JsonIgnore(false)
    public Double getN04() {
        return super.getN04();
    }

    @Override
    @JsonIgnore(false)
    public void setN05(Double n05) {
        super.setN05(n05);
    }

    @Override
    @JsonIgnore(false)
    public Double getN05() {
        return super.getN05();
    }

    @Override
    @JsonIgnore(false)
    public void setC01(String c01) {
        super.setC01(c01);
    }

    @Override
    @JsonIgnore(false)
    public String getC01() {
        return super.getC01();
    }

    @Override
    @JsonIgnore(false)
    public void setC02(String c02) {
        super.setC02(c02);

    }

    @Override
    @JsonIgnore(false)
    public String getC02() {
        return super.getC02();
    }

    @Override
    @JsonIgnore(false)
    public void setC03(String c03) {
        super.setC03(c03);

    }

    @Override
    @JsonIgnore(false)
    public String getC03() {
        return super.getC03();
    }

    @Override
    @JsonIgnore(false)
    public void setC04(String c04) {
        super.setC04(c04);

    }

    @Override
    @JsonIgnore(false)
    public String getC04() {
        return super.getC04();
    }

    @Override
    @JsonIgnore(false)
    public void setC05(String c05) {
        super.setC05(c05);

    }

    @Override
    @JsonIgnore(false)
    public String getC05() {
        return super.getC05();
    }

    @Override
    @JsonIgnore(false)
    public void setD01(Date d01) {
        super.setD01(d01);

    }

    @Override
    @JsonIgnore(false)
    public Date getD01() {
        return super.getD01();
    }

    @Override
    @JsonIgnore(false)
    public void setD02(Date d02) {
        super.setD02(d02);
    }

    @Override
    @JsonIgnore(false)
    public Date getD02() {
        return super.getD02();
    }

    @Override
    @JsonIgnore(false)
    public void setD03(Date d03) {
        super.setD03(d03);
    }

    @Override
    @JsonIgnore(false)
    public Date getD03() {
        return super.getD03();
    }

    @Override
    @JsonIgnore(false)
    public void setD04(Date d04) {
        super.setD04(d04);
    }

    @Override
    @JsonIgnore(false)
    public Date getD04() {
        return super.getD04();
    }

    @Override
    @JsonIgnore(false)
    public void setD05(Date d05) {
        super.setD05(d05);
    }

    @Override
    @JsonIgnore(false)
    public Date getD05() {
        return super.getD05();
    }
}