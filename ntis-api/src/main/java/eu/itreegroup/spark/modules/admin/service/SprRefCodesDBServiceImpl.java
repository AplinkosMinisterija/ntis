package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.app.model.SprListIdKeyValue;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.Data2ObjectProcessor;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprRefCodesDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.CodeDictionaryModel;
import eu.itreegroup.spark.modules.admin.logic.forms.model.IdKeyValuePair;
import eu.itreegroup.spark.modules.admin.service.gen.SprRefCodesDBServiceGen;

@Service
public class SprRefCodesDBServiceImpl extends SprRefCodesDBServiceGen implements SprRefCodesDBService {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprRefCodesDBServiceImpl.class);

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    DBStatementManager dbStatementManager;

    public SprRefCodesDBServiceImpl() {
    }

    @Override
    public SprRefCodesDAO newRecord() {
        SprRefCodesDAO daoObject = super.newRecord();
        return daoObject;
    }

    @Override
    public CodeDictionaryModel loadDictionary(Connection conn, Double id) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        CodeDictionaryModel codeModel = new CodeDictionaryModel();
        stmt.setStatement("SELECT " + "coalesce(RFD1.RFD_CODE_COLNAME, RFD.RFD_REF_DOMAIN_1) as RFD_REF_DOMAIN_1, "
                + "coalesce(RFD2.RFD_CODE_COLNAME, RFD.RFD_REF_DOMAIN_2) as RFD_REF_DOMAIN_2, "
                + "coalesce(RFD3.RFD_CODE_COLNAME, RFD.RFD_REF_DOMAIN_3) as RFD_REF_DOMAIN_3, "
                + "coalesce(RFD4.RFD_CODE_COLNAME, RFD.RFD_REF_DOMAIN_4) as RFD_REF_DOMAIN_4, "
                + "coalesce(RFD5.RFD_CODE_COLNAME, RFD.RFD_REF_DOMAIN_5) as RFD_REF_DOMAIN_5, " + "RFD.RFD_REF_DOMAIN_1 as RFD_REF_DOMAIN_1_clsf, "
                + "RFD.RFD_REF_DOMAIN_2 as RFD_REF_DOMAIN_2_clsf, " + "RFD.RFD_REF_DOMAIN_3 as RFD_REF_DOMAIN_3_clsf, "
                + "RFD.RFD_REF_DOMAIN_4 as RFD_REF_DOMAIN_4_clsf, " + "RFD.RFD_REF_DOMAIN_5 as RFD_REF_DOMAIN_5_clsf, "
                + "RFD.RFD_N1_COLNAME, RFD.RFD_N2_COLNAME, RFD.RFD_N3_COLNAME, RFD.RFD_N4_COLNAME, RFD.RFD_N5_COLNAME, "
                + "RFD.RFD_C1_COLNAME, RFD.RFD_C2_COLNAME, RFD.RFD_C3_COLNAME, RFD.RFD_C4_COLNAME, RFD.RFD_C5_COLNAME, "
                + "RFD.RFD_D1_COLNAME, RFD.RFD_D2_COLNAME, RFD.RFD_D3_COLNAME, RFD.RFD_D4_COLNAME, RFD.RFD_D5_COLNAME FROM SPR_REF_DICTIONARIES RFD "
                + "LEFT JOIN SPR_REF_DICTIONARIES RFD1 ON RFD.RFD_REF_DOMAIN_1 = RFD1.RFD_TABLE_NAME "
                + "LEFT JOIN SPR_REF_DICTIONARIES RFD2 ON RFD.RFD_REF_DOMAIN_2 = RFD2.RFD_TABLE_NAME "
                + "LEFT JOIN SPR_REF_DICTIONARIES RFD3 ON RFD.RFD_REF_DOMAIN_3 = RFD3.RFD_TABLE_NAME "
                + "LEFT JOIN SPR_REF_DICTIONARIES RFD4 ON RFD.RFD_REF_DOMAIN_4 = RFD4.RFD_TABLE_NAME "
                + "LEFT JOIN SPR_REF_DICTIONARIES RFD5 ON RFD.RFD_REF_DOMAIN_5 = RFD5.RFD_TABLE_NAME ");
        stmt.addParam4WherePart(" RFD.RFD_ID = ?::int ", id);
        List<CodeDictionaryModel> data = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, CodeDictionaryModel.class);
        if (data != null && !data.isEmpty()) {
            codeModel = data.get(0);
        }
        return codeModel;

    }

    @Override
    public void deleteRecord(Connection conn, Double identifier) throws Exception {
        String trStmt = "DELETE FROM SPR_REF_TRANSLATIONS WHERE RFT_RFC_ID=?::int";
        log.debug("delete stmt: " + trStmt);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(trStmt);
        stmt.addSelectParam(identifier);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);
        super.deleteRecord(conn, identifier);
    }

    /**
     * Function will return classifier codes and values for provided classifier
     * code.
     * 
     * @param conn     - connection to the DB
     * @param refCode  - classifier identifier, which data shoule be returned
     * @param language - language code, which should be used for classifier value
     *                 translation
     * @return JSON object that contain array of objec: id - value id key_value -
     *         value code display_text - value display text
     * @throws Exception
     */
    @Override
    @Cacheable(value = "refCodesManagerJSON", key = "{#refCode, #language}")
    public ArrayList<SprListIdKeyValue> getRefCodes(Connection conn, String refCode, String language) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("select rc.rfc_id as id, " + //
                "       rc.rfc_code as key, " + //
                "       case when rt.rft_display_code is null then rc.rfc_meaning else rt.rft_display_code end as display" + //
                "  from spr_ref_codes rc  " + //
                "       left join spr_ref_translations rt on  (rt.rft_rfc_id = rc.rfc_id and rt.rft_lang=?)" + //
                " where " + dbStatementManager.getPeriodValidationForCurrentDateStr(" rc.rfc_date_from", "rc.rfc_date_to"));
        stmt.addSelectParam(language);
        stmt.setWhereExists(true);
        stmt.addParam4WherePart(" rc.rfc_domain = ? ", refCode);
        stmt.setStatementOrderPart("ORDER BY rc.rfc_order ASC");

        return (ArrayList<SprListIdKeyValue>) baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt,
                new Data2ObjectProcessor<SprListIdKeyValue>(SprListIdKeyValue.class));
    }

    @Override
    @Cacheable(value = "refCodesManager", key = "{#refCode, #language}")
    public ArrayList<IdKeyValuePair> getRefCodesByName(Connection conn, String refCode, String language) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        if (language != null) {
            stmt.setStatement("select rc.rfc_code as id,\r\n" + //
                    "       rc.rft_display_code as value\r\n" + //
                    " from spr_ref_codes_vw rc\r\n" + //
                    "where " + dbStatementManager.getPeriodValidationForCurrentDateStr(" rc.rfc_date_from", "rc.rfc_date_to"));
            stmt.setWhereExists(true);
            stmt.addParam4WherePart(" rc.rft_lang = ? ", language);
            stmt.addParam4WherePart(" rc.rfc_domain = ? ", refCode);
        } else {
            stmt.setStatement("select rc.rfc_code as id,\r\n" + //
                    "       rc.rfc_meaning as value\r\n" + //
                    " from spr_ref_codes rc\r\n" + //
                    "where " + dbStatementManager.getPeriodValidationForCurrentDateStr(" rc.rfc_date_from", "rc.rfc_date_to"));
            stmt.setWhereExists(true);
            stmt.addParam4WherePart(" rc.rfc_domain = ? ", refCode);
        }
        List<IdKeyValuePair> list = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, IdKeyValuePair.class);
        return (ArrayList<IdKeyValuePair>) list;
    }

    @Override
    @CacheEvict(value = "refCodesManagerJSON", key = "{#refCode, #language}")
    public void clearCasheForRefCodesManagerJSON(String refCode, String language) {
        log.debug("clear cashe for refCodesManagerJSON");
    }

    @Override
    @CacheEvict(value = "refCodesManager", key = "{#refCode, #language}")
    public void clearCacheForRefCodesManager(String refCode, String language) {
        log.debug("clear cashe for refCodesManagerJSON");
    }
}