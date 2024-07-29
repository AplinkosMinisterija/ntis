package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import lt.project.ntis.logic.forms.security.NtisAdrAdressesBrowseSecurityManager;

/**
 * Klasė skirta formos "Adresų registro duomenys" biznio logikai apibrėžti
 */
@Component
public class NtisAddressesList extends FormBase {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(NtisAddressesList.class);

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbstatementManager;

    @Override
    public String getFormName() {
        return "NTIS_ADR_ADDRESS_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Adresų registro duomenys", "Adresų registro duomenų forma");
        addFormActions(FormBase.ACTION_READ);
    }

    /**
     * Metodas grąžins adresų registro duomenų sąrašą
     * @param conn - prisijungimas prie DB
     * @param params - select parametrai
     * @param orgId - sesijos organizacijos id
     * @return json objektas
     * @throws Exception
     */
    public String getAddressList(Connection conn, SelectRequestParams params, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisAddressesList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(
                """
                               select ad.ad_id, apl.apl_pat_code,
                                ads.ads_aob_code,
                                ad.ad_coordinate_latitude,
                                ad.ad_coordinate_longitude,
                                case when ad.rec_timestamp != ad.rec_create_timestamp then ad.rec_timestamp else null end as update_timestamp,
                                  ad.ad_address,
                                  str.str_street_code,
                                  str.str_name,
                                  re.re_recidence_code,
                                  re.re_name,
                                  sen.sen_code,
                                  sen.sen_name
                             from ntis_adr_stats ads
                             join ntis_adr_addresses ad on ads.ads_id = ad.ad_ads_id
                             join ntis.ntis_adr_residences re on ads.ads_residence_code = re.re_recidence_code and now() between re.re_date_from and coalesce(re.re_date_to, now())
                        left join ntis.ntis_adr_streets str on ads.ads_street_code = str.str_street_code and now() between str.str_date_from and coalesce(str.str_date_to, now())
                        left join ntis.ntis_adr_seniunijos sen on sen.sen_code = re.re_sen_code:: text and now() between sen.sen_date_from and coalesce(sen.sen_date_to, now())
                        left join ntis_adr_pat_lrs apl ON ad.ad_apl_id = apl.apl_id
                                """);
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), orgId, stmt, advancedParamList);
        stmt.addParam4WherePart("ad_address", StatementAndParams.PARAM_STRING, advancedParamList.get("ad_address"));
        stmt.addParam4WherePart("case when ad.rec_timestamp != ad.rec_create_timestamp then ad.rec_timestamp else null end", StatementAndParams.PARAM_DATE,
                advancedParamList.get("update_timestamp"), dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart("apl_pat_code", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("apl_pat_code"));
        stmt.addParam4WherePart("ads_aob_code", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("ads_aob_code"));

        stmt.addParam4WherePart(
                dbstatementManager.colNamesToConcatString("ad_address", "apl.apl_pat_code", "ads_aob_code",
                        "TO_CHAR(case when ad.rec_timestamp != ad.rec_create_timestamp then ad.rec_timestamp else null end, '"
                                + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        NtisAdrAdressesBrowseSecurityManager sqm = new NtisAdrAdressesBrowseSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { NtisAddressesList.ACTION_READ };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        log.info(stmt.logData());
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }
}
