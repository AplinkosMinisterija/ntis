package lt.project.ntis.service;

import java.sql.Connection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import lt.project.ntis.dao.NtisAdrAddressesDAO;
import lt.project.ntis.models.AddressSearchResponse;
import lt.project.ntis.service.gen.NtisAdrAddressesDBServiceGen;

@Service
public class NtisAdrAddressesDBService extends NtisAdrAddressesDBServiceGen {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(NtisAdrAddressesDBService.class);

    @Autowired
    BaseControllerJDBC queryController;

    public NtisAdrAddressesDBService() {
    }

    @Override
    public NtisAdrAddressesDAO newRecord() {
        NtisAdrAddressesDAO daoObject = super.newRecord();
        return daoObject;
    }

    public List<AddressSearchResponse> getAddressById(Connection conn, Double addressId) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT ntrs.bn_id AS ntr_building_id, "//
                + "           addr.full_address_text, "//
                + "           addr.address_id, "//
                + "           addr.latitude, "//
                + "           addr.longitude "//
                + "      FROM ntis.ntis_address_vw AS addr "//
                + " LEFT JOIN ntis.ntis_building_ntrs AS ntrs "//
                + "        ON ntrs.bn_ad_id = addr.address_id"//
                + "     WHERE addr.address_id = ?::int LIMIT 1");
        stmt.addSelectParam(addressId);

        List<AddressSearchResponse> queryResult = queryController.selectQueryAsObjectArrayList(conn, stmt, AddressSearchResponse.class);

        return queryResult;
    }

}