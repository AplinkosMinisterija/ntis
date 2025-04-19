package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import lt.project.ntis.logic.forms.model.NtisSludgeDeliveryDetails;
import lt.project.ntis.models.NtisSewageOriginFacility;
import lt.project.ntis.service.NtisWastewaterDeliveriesDBService;

/**
 * Klasė skirta formos "Peržiūrėti nuotekų tvarkymo pristatymą (vandentvarka)" biznio logikai apibrėžti
 */
@Component
public class NtisSludgeTreatmentDelivery extends FormBase {

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbstatementManager;

    @Autowired
    NtisWastewaterDeliveriesDBService ntisWastewaterDeliveriesDBService;

    @Override
    public String getFormName() {
        return "NTIS_SLUDGE_TREATMENT_DELIVERY";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Peržiūrėti nuotekų tvarkymo pristatymą", "Peržiūrėti nuotekų tvarkymo pristatymą (vandentvarka)");
        addFormActions(FormBase.ACTION_READ);
        addFormActions(FormBase.ACTION_UPDATE);
    }

    /**
     * Metodas grąžins NtisSludgeDeliveryDetails objektą pagal nurodytą pristatymo ID
     * @param conn - prisijungimas prie DB
     * @param lang - kalbos kodas, naudojamas vertimams
     * @param orgId - sesijos organizacijos id
     * @param wdId - pristatymo ID
     * @return - NtisSludgeDeliveryDetails objektas
     * @throws Exception
     */
    public NtisSludgeDeliveryDetails getSludgeDeliveryInfo(Connection conn, String lang, Double orgId, Double userId, Double wdId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisSludgeTreatmentDelivery.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(" SELECT WD_ID, " //
                + " WD_STATE AS WD_STATE_CLSF, " //
                + " COALESCE(WS.RFT_DISPLAY_CODE, WS.RFC_MEANING) AS WD_STATE, " //
                + " TO_CHAR(WD_DELIVERY_DATE, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') AS WD_DELIVERY_DATE, " //
                + " TO_CHAR(WD.REC_CREATE_TIMESTAMP, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_DB)
                + "') AS REC_CREATE_TIMESTAMP, " //
                + " COALESCE(WST.RFT_DISPLAY_CODE, WST.RFC_MEANING) AS WD_SEWAGE_TYPE, " //
                + " WD_DELIVERED_QUANTITY, " //
                + " WD_ACCEPTED_SEWAGE_QUANTITY, " //
                + " WD_USED_SLUDGE_QUANTITY, " //
                + " CR_REG_NO, " //
                + " WTO_NAME, " //
                + " WTO_ADDRESS, " //
                + " ORG_NAME, " //
                + " ORG_CODE, " //
                + " ORG_EMAIL, " //
                + " ORG_PHONE, " //
                + " WD_DESCRIPTION, " //
                + " WD_DELIVERED_WASTEWATER_DESCRIPTION, " //
                + " WD_REJECTION_REASON " //
                + " FROM NTIS_WASTEWATER_DELIVERIES AS WD " //
                + " LEFT JOIN NTIS_CARS ON CR_ID = WD_CR_ID " //
                + " LEFT JOIN SPR_ORGANIZATIONS ORG ON ORG_ID = CR_ORG_ID " //
                + " INNER JOIN NTIS_WASTEWATER_TREATMENT_ORG WTO ON WTO_ID = WD_WTO_ID AND WTO_ORG_ID = ?::int" //
                + " INNER JOIN SPR_ORG_USERS OU ON OU.OU_ORG_ID = WTO.WTO_ORG_ID AND CURRENT_DATE BETWEEN OU.OU_DATE_FROM AND COALESCE(OU.OU_DATE_TO, now()) AND OU_USR_ID = ?::int"
                + " LEFT JOIN SPR_REF_CODES_VW WS ON WS.RFC_CODE = WD_STATE AND WS.RFC_DOMAIN = 'SEWAGE_DELIV_STATUS' AND WS.RFT_LANG = ? " //
                + " LEFT JOIN SPR_REF_CODES_VW WST ON WST.RFC_CODE = WD_SEWAGE_TYPE AND WST.RFC_DOMAIN = 'SEWAGE_TYPE' AND WST.RFT_LANG = ? " //
                + " WHERE WD_ID = ?::int");
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(userId);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(wdId);
        List<NtisSludgeDeliveryDetails> queryResult = queryController.selectQueryAsObjectArrayList(conn, stmt, NtisSludgeDeliveryDetails.class);
        if (queryResult != null && !queryResult.isEmpty()) {
            queryResult.get(0).setOriginFacilities(getSewageOriginFacilities(conn, lang, wdId, orgId, userId));
            return queryResult.get(0);
        } else {
            throw new Exception("No information for wastewater delivery with id " + wdId + " was found");
        }
    }

    private List<NtisSewageOriginFacility> getSewageOriginFacilities(Connection conn, String lang, Double wdId, Double orgId, Double userId) throws Exception {
        StatementAndParams stmt = new StatementAndParams("""
                   select ord.ord_id,
                       df.df_id,
                       df.df_wtf_id as wtf_id,
                       wtf.c02 || ' - ' || coalesce(wav.full_address_text, '(' || wtf.wtf_facility_latitude || ', ' || wtf.wtf_facility_longitude || ')')  as name,
                       coalesce(typ.rfc_meaning, wtf.wtf_type) as wtf_type,     
                       wtf.wtf_type as wtf_type_code,
                       df.df_delivery_sludge_quentity as ocw_discharged_sludge_amount,
                       coalesce(wav.full_address_text || ' (' || wtf.wtf_facility_latitude || ', ' || wtf.wtf_facility_longitude || ')', '(' || wtf.wtf_facility_latitude || ', ' || wtf.wtf_facility_longitude || ')')  as wtf_address,
                       case when ord.ord_id is not null then 'ORDER' when ord.ord_id is null and wtf.wtf_id is not null then 'FACILITY' end as type
                       from ntis_wastewater_deliveries wd
                       inner join ntis_wastewater_treatment_org wto on wto.wto_id = wd.wd_wto_id and wto.wto_org_id = ?::int
                       inner join ntis_delivery_facilities df on df.df_wd_id = wd.wd_id
                       left join ntis_orders ord on ord.ord_id = df.df_ord_id and ord.ord_wtf_id = df.df_wtf_id
                       inner join ntis_wastewater_treatment_faci wtf on df.df_wtf_id = wtf.wtf_id
                       left join spr_ref_codes_vw typ on typ.rfc_code = wtf.wtf_type and typ.rfc_domain = 'NTIS_WTF_TYPE' and typ.rft_lang = ?
                       left join ntis_address_vw wav on wav.address_id = wtf.wtf_ad_id
                       where wd.wd_id = ?::int 
                """);

        stmt.addSelectParam(orgId);

        stmt.addSelectParam(lang);
        stmt.addSelectParam(wdId);
        List<NtisSewageOriginFacility> data = queryController.selectQueryAsObjectArrayList(conn, stmt, NtisSewageOriginFacility.class);
        return data;
    }

}
