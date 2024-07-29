package lt.project.ntis.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import lt.project.ntis.dao.NtisFacilityOwnersDAO;
import lt.project.ntis.enums.NtisFacilityOwnerType;
import lt.project.ntis.service.gen.NtisFacilityOwnersDBServiceGen;

@Service
public class NtisFacilityOwnersDBService extends NtisFacilityOwnersDBServiceGen {

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    public NtisFacilityOwnersDBService() {
    }

    @Override
    public NtisFacilityOwnersDAO newRecord() {
        NtisFacilityOwnersDAO daoObject = super.newRecord();
        return daoObject;
    }

    public Boolean isOrganizationAnOwnerOfFacility(Connection conn, Double orgId, Double wtfId) throws SQLException {
        StatementAndParams stmt = new StatementAndParams("SELECT 1 FROM NTIS_FACILITY_OWNERS WHERE FO_ORG_ID = ?::int AND FO_WTF_ID = ?::int");
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(wtfId);
        List<HashMap<String, String>> queryResult = baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
        return !queryResult.isEmpty();
    }

    public Boolean isPersonAnOwnerOfFacility(Connection conn, Double perId, Double wtfId) throws SQLException {
        StatementAndParams stmt = new StatementAndParams("SELECT 1 FROM NTIS_FACILITY_OWNERS WHERE FO_PER_ID = ?::int AND FO_WTF_ID = ?::int");
        stmt.addSelectParam(perId);
        stmt.addSelectParam(wtfId);
        List<HashMap<String, String>> queryResult = baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
        return !queryResult.isEmpty();
    }

    public NtisFacilityOwnersDAO loadRecordByWtfId(Connection conn, Double identifier) throws Exception {
        return this.loadRecordByParams(conn, " where fo_wtf_id = ?::int ", new SelectParamValue(identifier));
    }

    public void deleteByServedObjectId(Connection conn, Double soId) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("DELETE FROM ntis.ntis_facility_owners WHERE fo_so_id = ?::int ");
        stmt.setWhereExists(true);
        stmt.addSelectParam(soId);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);
    }

    /**
     * Function will check if for provided point exists provided owner (role provided by NtisFacilityOwnerType). If not exists
     * this reference will be added.
     * @param conn - connection to the db
     * @param wtfId - reference to the point
     * @param orgId - organization id, that should has reference to the point
     * @param perId - person id, that should has reference to the point
     * @param soId - served object id, that should has reference to the person or organization
     * @param dateFrom - date from when this record is valid
     * @param dateTo - date to till when this record is valid
     * @param status - relation role in this relation (OWNER, SELF_ASSIGNED, SERVICE_PROVIDER, MANAGER)
     * @throws Exception
     */
    public void manageWtfOwners(Connection conn, Double wtfId, Double orgId, Double perId, Double soId, Date dateFrom, Date dateTo,
            NtisFacilityOwnerType status) throws Exception {
        ArrayList<SelectParamValue> paramList = new ArrayList<SelectParamValue>();
        String str = " WHERE fo_wtf_id = ?::int and fo_owner_type = ? and now() between fo_date_from and COALESCE(fo_date_to, now()) ";
        paramList.add(new SelectParamValue(wtfId));
        paramList.add(new SelectParamValue(status.getCode()));
        if (orgId != null) {
            str = str + " and fo_org_id = ?::int ";
            paramList.add(new SelectParamValue(orgId));
        } else {
            str = str + " and fo_org_id is null ";
        }
        if (perId != null) {
            str = str + " and fo_per_id = ?::int ";
            paramList.add(new SelectParamValue(perId));
        } else {
            str = str + " and fo_per_id is null ";
        }
        if (soId != null) {
            str = str + " and fo_so_id = ?::int ";
            paramList.add(new SelectParamValue(soId));
        } else {
            str = str + " and fo_so_id is null ";
        }
        SelectParamValue[] a = new SelectParamValue[paramList.size()];
        NtisFacilityOwnersDAO daoObject = this.loadRecordByParams(conn, str, paramList.toArray(a));
        if (daoObject == null) {
            daoObject = this.newRecord();
            daoObject.setFo_org_id(orgId);
            daoObject.setFo_per_id(perId);
            daoObject.setFo_wtf_id(wtfId);
            daoObject.setFo_so_id(soId);
            daoObject.setFo_owner_type(status.getCode());
            daoObject.setFo_date_from(dateFrom);
        }
        daoObject.setFo_date_to(dateTo);
        this.saveRecord(conn, daoObject);
    }

}