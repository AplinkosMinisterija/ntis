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
import lt.project.ntis.models.NtisUsedSewageFacility;

/**
 * Klasė skirta formos "Peržiūrėti nuotekų tvarkymo pristatymą (pasl. teikėjas)"  biznio logikai apibrėžti
 */

@Component
public class NtisSewageDeliveryViewPage extends FormBase {

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbstatementManager;

    @Override
    public String getFormName() {
        return "NTIS_SEWAGE_DELIVERY_VIEW";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Nuotekų (dumblo) pristatymo valyti peržiūra", "Nuotekų (dumblo) pristatymo valyti peržiūros forma");
        addFormActions(FormBase.ACTION_READ);
    }

    /**
     * Pagal nurodomą nuotekų tvarkymo įrašo id, metodas grąžins NtisSludgeDeliveryDetails objektą
     * @param conn - prisijungimas prie DB
     * @param wdId - nuotekų tvarkymo pristatymo įrašo id
     * @param orgId - sesijos organizacijos id
     * @param lang - sesijos kalba
     * @param usrId - sesijos naudotojo id
     * @return NtisSludgeDeliveryDetails objektas
     * @throws Exception
     */
    public NtisSludgeDeliveryDetails getDeliveryRecordView(Connection conn, Double wdId, Double orgId, String lang, Double usrId) throws Exception {
        NtisSludgeDeliveryDetails delivery = new NtisSludgeDeliveryDetails();
        checkIsFormActionAssigned(conn, NtisSewageDeliveryViewPage.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT WD.WD_ID, " + //
                "coalesce(RFC.RFC_MEANING, WD.WD_STATE) as WD_STATE, " + //
                "WD.WD_STATE as WD_STATE_CLSF, " + //
                "TO_CHAR(WD.REC_CREATE_TIMESTAMP, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_DB)
                + "') AS REC_CREATE_TIMESTAMP, " + //
                "TO_CHAR(WD.WD_DELIVERY_DATE, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') AS WD_DELIVERY_DATE, " + //
                "coalesce(RFCT.RFC_MEANING, WD.WD_SEWAGE_TYPE) as WD_SEWAGE_TYPE, " + //
                "WD.WD_DELIVERED_QUANTITY, " + //
                "WD.WD_USED_SLUDGE_QUANTITY, " + //
                "WD.WD_ACCEPTED_SEWAGE_QUANTITY, " + //
                "WD.WD_REJECTION_REASON, " + //
                "WD.WD_DELIVERED_WASTEWATER_DESCRIPTION, " + //
                "CASE WHEN CR.CR_TYPE IS NOT NULL THEN CR.CR_REG_NO || ' (' || CRC.RFC_MEANING || ')' " + //
                "ELSE CR.CR_REG_NO END AS CR_REG_NO, " + //
                "WTO.WTO_NAME || ', ' || WTO.WTO_ADDRESS AS WTO_NAME, " + //
                "ORG.ORG_NAME, " + //
                "ORG.ORG_CODE, " + //
                "ORG.ORG_EMAIL, " + //
                "ORG.ORG_PHONE, " + //
                "WD.WD_DESCRIPTION " + //
                "FROM NTIS.NTIS_WASTEWATER_DELIVERIES WD " + //
                "INNER JOIN SPR_ORG_USERS OU ON OU.OU_ORG_ID = WD.WD_ORG_ID AND OU.OU_ORG_ID = ?::int AND OU.OU_USR_ID = ?::int AND CURRENT_DATE BETWEEN OU.OU_DATE_FROM AND COALESCE(OU.OU_DATE_TO, now()) "
                + //
                "LEFT JOIN NTIS.NTIS_CARS CR ON WD.WD_CR_ID = CR.CR_ID " + //
                "INNER JOIN SPARK.SPR_REF_CODES_VW RFC ON RFC.RFC_CODE = WD.WD_STATE AND RFC.RFC_DOMAIN = 'SEWAGE_DELIV_STATUS' AND WD.WD_ID = ?::int AND rfc.rft_lang = ? "
                + //
                "INNER JOIN SPARK.SPR_REF_CODES_VW RFCT ON RFCT.RFC_CODE = WD.WD_SEWAGE_TYPE AND RFCT.RFC_DOMAIN = 'SEWAGE_TYPE' AND rfct.rft_lang = ? " + //
                "LEFT JOIN NTIS.NTIS_WASTEWATER_TREATMENT_ORG WTO ON WTO.WTO_ID = WD.WD_WTO_ID " + //
                "LEFT JOIN SPARK.SPR_ORGANIZATIONS ORG ON WTO.WTO_ORG_ID = ORG.ORG_ID " +//
                "LEFT JOIN SPR_REF_CODES_VW CRC ON CRC.RFC_DOMAIN = 'NTIS_CAR_TYPE' AND CRC.RFC_CODE = CR.CR_TYPE AND CRC.RFT_LANG = ? ");
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(usrId);
        stmt.addSelectParam(wdId);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(lang);
        List<NtisSludgeDeliveryDetails> data = queryController.selectQueryAsObjectArrayList(conn, stmt, NtisSludgeDeliveryDetails.class);
        if (data != null && !data.isEmpty()) {
            delivery = data.get(0);
            delivery.setUsedFacilities(getUsedFacilitiesList(conn, delivery.getWd_id(), lang, orgId, usrId));
            delivery.setOriginFacilities(getSewageOriginFacilities(conn, delivery.getWd_id(), orgId, usrId, lang));
            return delivery;
        } else {
            throw new Exception("No sewage delivery record with id " + wdId + " found");
        }
    }

    private List<NtisSewageOriginFacility> getSewageOriginFacilities(Connection conn, Double wdId, Double orgId, Double usrId, String lang) throws Exception {
        StatementAndParams stmt = new StatementAndParams("""
                select ord.ord_id,
                       df.df_id,
                       df.df_wtf_id as wtf_id,
                       wtf.c02 || ' - ' || coalesce(wav.full_address_text, '(' || wtf.wtf_facility_latitude || ', ' || wtf.wtf_facility_longitude || ')')  as name,
                       coalesce(typ.rfc_meaning, wtf.wtf_type) as wtf_type,
                       df.df_delivery_sludge_quentity as ocw_discharged_sludge_amount,
                       case when ord.ord_id is not null then 'ORDER' when ord.ord_id is null and wtf.wtf_id is not null then 'FACILITY' end as type
                       from ntis_wastewater_deliveries wd
                       inner join ntis_delivery_facilities df on df.df_wd_id = wd.wd_id
                       left join ntis_orders ord on ord.ord_id = df.df_ord_id and ord.ord_wtf_id = df.df_wtf_id
                       inner join ntis_wastewater_treatment_faci wtf on df.df_wtf_id = wtf.wtf_id
                       left join spr_ref_codes_vw typ on typ.rfc_code = wtf.wtf_type and typ.rfc_domain = 'NTIS_WTF_TYPE' and typ.rft_lang = ?
                       left join ntis_address_vw wav on wav.address_id = wtf.wtf_ad_id
                       where wd.wd_id = ?::int and wd.wd_org_id = ?::int
                """);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(wdId);
        stmt.addSelectParam(orgId);
        stmt.setWhereExists(true);
        List<NtisSewageOriginFacility> data = queryController.selectQueryAsObjectArrayList(conn, stmt, NtisSewageOriginFacility.class);
        return data;
    }

    private List<NtisUsedSewageFacility> getUsedFacilitiesList(Connection conn, Double wdId, String lang, Double orgId, Double usrId) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT " + //
                "WTF.WTF_ID, " + //
                "COALESCE (WAV.FULL_ADDRESS_TEXT, '('|| WTF.WTF_FACILITY_LATITUDE || ', ' || WTF.WTF_FACILITY_LONGITUDE || ') ') AS WTF_ADDRESS, " + //
                "WTF.WTF_TECHNICAL_PASSPORT_ID, " + //
                "coalesce(TYP.RFT_DISPLAY_CODE, TYP.RFC_MEANING) as WTF_TYPE, " + //
                "coalesce(MAN.RFT_DISPLAY_CODE, MAN.RFC_MEANING) as WTF_MANUFACTURER, " + //
                "coalesce(MOD.RFT_DISPLAY_CODE, MOD.RFC_MEANING) as WTF_MODEL, " + //
                "TO_CHAR(WTF.WTF_INSTALLATION_DATE, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)
                + "') AS WTF_INSTALLATION_DATE, " + //
                "US.US_ID, " + //
                "US.US_WD_ID, " + //
                "WTF.WTF_DISTANCE " + //
                "FROM NTIS.NTIS_WASTEWATER_TREATMENT_FACI WTF " + //
                "LEFT JOIN SPR_REF_CODES_VW TYP ON TYP.RFC_CODE = WTF.WTF_TYPE AND TYP.RFC_DOMAIN = 'NTIS_WTF_TYPE' AND TYP.RFT_LANG = ? " + //
                "LEFT JOIN SPR_REF_CODES_VW MAN ON MAN.RFC_CODE = WTF.WTF_MANUFACTURER AND MAN.RFC_DOMAIN = 'NTIS_FACIL_MANUFA' AND MAN.RFT_LANG = ? " + //
                "LEFT JOIN SPR_REF_CODES_VW MOD ON MOD.RFC_CODE = WTF.WTF_MODEL AND MOD.RFC_DOMAIN = 'NTIS_FACIL_MODEL' AND MOD.RFT_LANG = ? " + //
                "LEFT JOIN NTIS.NTIS_ADDRESS_VW WAV ON WAV.ADDRESS_ID = WTF.WTF_AD_ID " + //
                "INNER JOIN NTIS.NTIS_USED_SLUDGES US ON US.US_WTF_ID = WTF.WTF_ID AND US.US_WD_ID = ?::int " + //
                "INNER JOIN NTIS_WASTEWATER_DELIVERIES WD ON WD.WD_ID = US.US_WD_ID " + //
                "INNER JOIN SPR_ORG_USERS OU ON OU.OU_ORG_ID = WD.WD_ORG_ID AND OU.OU_ORG_ID = ?::int AND OU.OU_USR_ID = ?::int AND CURRENT_DATE BETWEEN OU.OU_DATE_FROM AND COALESCE(OU.OU_DATE_TO, now()) ");
        stmt.addSelectParam(lang);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(wdId);
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(usrId);
        List<NtisUsedSewageFacility> data = queryController.selectQueryAsObjectArrayList(conn, stmt, NtisUsedSewageFacility.class);
        return data;
    }

}
