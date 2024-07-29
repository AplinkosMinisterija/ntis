package lt.project.ntis.service;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import lt.project.ntis.dao.NtisContractsDAO;
import lt.project.ntis.enums.NtisContractParties;
import lt.project.ntis.service.gen.NtisContractsDBServiceGen;

@Service
public class NtisContractsDBService extends NtisContractsDBServiceGen {

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(NtisContractsDBService.class);

    public NtisContractsDBService() {
    }

    @Override
    public NtisContractsDAO newRecord() {
        NtisContractsDAO daoObject = super.newRecord();
        return daoObject;
    }

    /**
     * Metodas grąžina sutarties/prašymo DAO objektą pagal pateiktą sutarties ID ir paslaugų teikėjo organizacijos ID
     * @param conn - prisijungimas prie DB
     * @param orgId - paslaugų teikėjo organizacijos ID
     * @param identifier - sutarties ID
     * @return NtisContractsDAO objektas
     * @throws Exception
     */
    public NtisContractsDAO loadRecordByIdAndServiceProviderId(Connection conn, Double orgId, Double identifier) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT COT_ID AS id " + //
                "FROM NTIS_CONTRACTS COT " + //
                "LEFT JOIN NTIS_CONTRACT_SERVICES CS ON CS.CS_COT_ID = COT_ID " + //
                "LEFT JOIN NTIS_SERVICES SRV ON SRV.SRV_ID = CS.CS_SRV_ID " + //
                "WHERE COT_ID = ?::int AND SRV.SRV_ORG_ID = ?::int ");
        stmt.addSelectParam(identifier);
        stmt.addSelectParam(orgId);
        return this.loadRecord(conn, baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, RecordIdentifier.class).get(0).getIdAsDouble());
    }

    /**
     * Metodas grąžina sutarties/prašymo DAO objektą pagal pateiktą sutarties ID ir paslaugų gavėjo (kliento) organizacijos ID arba asmens ID
     * @param conn - prisijungimas prie DB
     * @param orgId - paslaugų gavėjo (kliento) organizacijos ID 
     * @param perId - paslaugų gavėjo (kliento) asmens ID
     * @param identifier - sutarties ID
     * @return NtisContractsDAO objektas
     * @throws Exception
     */
    public NtisContractsDAO loadRecordByIdAndClientId(Connection conn, Double orgId, Double perId, Double identifier) throws Exception {
        if (orgId != null) {
            return this.loadRecordByParams(conn, " where cot_id = ?::int and cot_org_id = ?::int ", new SelectParamValue(identifier),
                    new SelectParamValue(orgId));
        } else {
            return this.loadRecordByParams(conn, " where cot_id = ?::int and cot_per_id = ?::int ", new SelectParamValue(identifier),
                    new SelectParamValue(perId));
        }
    }

    /**
     * Metodas nustatys ar pagal pateiktus sutarties, organizacijos ir asmens ID tai yra sutarties dalyvis 
     * (ir koks jis - paslaugos teikėjas ar gavėjas), ar ne ir grąžins NtisContractParties konstantos 
     * reikšmę (service_provider/client/none)
     * @param conn - prisijungimas prie DB
     * @param orgId - organizacijos ID
     * @param perId - asmens ID
     * @param identifier - sutarties ID
     * @return NtisContractParties konstantos reikšmė
     * @throws Exception
     */
    public NtisContractParties isServiceProviderOrClient(Connection conn, Double orgId, Double perId, Double identifier, Boolean isClient) throws Exception {
        NtisContractParties result = NtisContractParties.NONE;
        if (isClient) {
            NtisContractsDAO contractDAO = this.loadRecordByIdAndClientId(conn, orgId, perId, identifier);
            if (contractDAO != null) {
                result = NtisContractParties.CLIENT;
            }
        } else {
            NtisContractsDAO contractDAO = this.loadRecordByIdAndServiceProviderId(conn, orgId, identifier);
            if (contractDAO != null) {
                result = NtisContractParties.SERVICE_PROVIDER;
            }
        }
        return result;
    }
}