package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprPropertiesDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.IdKeyValuePair;
import eu.itreegroup.spark.modules.admin.service.gen.SprPropertiesDBServiceGen;

@Service
public class SprPropertiesDBServiceImpl extends SprPropertiesDBServiceGen implements SprPropertiesDBService {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprPropertiesDBServiceImpl.class);

    public static final String IS_IDENTIFICATION_ENABLED = "iSense-identification-ENABLED";

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    private String[] publicPropertyNames = { "DATE_FORMAT_DAY_JAVA", "DATE_FORMAT_DAY_TIME_JAVA", "GOOGLE_AUTH_CLIENT_ID", "MULTI_TAB_SESSION_ENABLED",
            "UPLOAD_FILE_SIZE", "UPLOAD_FILE_EXTENTIONS", "NTIS_TERMS_URL", "LABS_CERTIFICATES_URL", "DEFAULT_NEWS_CATEGORY", IS_IDENTIFICATION_ENABLED,
            "FOOTER_VAR_LT_1", "FOOTER_VAR_EN_1", "FOOTER_VAR_LT_2", "FOOTER_VAR_EN_2", "FOOTER_VAR_LT_3", "FOOTER_VAR_EN_3" };

    public SprPropertiesDBServiceImpl() {
    }

    @Override
    public SprPropertiesDAO newRecord() {
        SprPropertiesDAO daoObject = super.newRecord();
        return daoObject;
    }

    @Override
    public SprPropertiesDAO loadRecordByName(Connection conn, String propertyName, String serverId) throws Exception {
        return this.loadRecordByParams(conn, "WHERE PRP_NAME = ? and PRP_INSTALL_INSTANCE = ? ", new SelectParamValue(propertyName),
                new SelectParamValue(serverId));
    }

    @Override
    public IdKeyValuePair[] loadPublicProperties(Connection conn) throws Exception {
        StatementAndParams stmt = new StatementAndParams("""
                SELECT prp_name AS id,
                       prp_value AS value
                  FROM spr_properties
                 WHERE prp_name in ( """
                + String.join(",", Arrays.stream(this.getPublicPropertyNames()).map(property -> "'" + property + "'").collect(Collectors.toList())) + ")");
        List<IdKeyValuePair> data = queryController.selectQueryAsObjectArrayList(conn, stmt, IdKeyValuePair.class);
        IdKeyValuePair[] result = new IdKeyValuePair[data.size()];
        result = data.toArray(result);
        return result;
    }

    @Override
    public String[] getPublicPropertyNames() {
        return this.publicPropertyNames;
    }

}