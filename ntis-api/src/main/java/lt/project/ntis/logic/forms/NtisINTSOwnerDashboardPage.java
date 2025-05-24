package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import lt.project.common.NtisCommonMethods;
import lt.project.ntis.constants.NtisCommonActionsConstants;
import lt.project.ntis.constants.NtisRolesConstants;
import lt.project.ntis.dao.NtisFacilityOwnersDAO;
import lt.project.ntis.dao.NtisSelectedFacilitiesDAO;
import lt.project.ntis.enums.NtisServiceItemType;
import lt.project.ntis.models.NtisINTSDashboardContract;
import lt.project.ntis.models.NtisINTSDashboardLab;
import lt.project.ntis.models.NtisINTSDashboardModel;
import lt.project.ntis.models.NtisINTSDashboardOrder;
import lt.project.ntis.models.NtisINTSDashboardWastewater;
import lt.project.ntis.service.NtisFacilityOwnersDBService;
import lt.project.ntis.service.NtisSelectedFacilitiesDBService;

/**
 * Klasė skirta formos "INTS savininko pagrindinio puslapio"  biznio logikai apibrėžti
 * "Nuotekų įrenginio savininko pagrindinio puslapio nuotekų (dumblo) išvežimo ir techninės priežiūros blokai"
 * "Forma Kliento sritis"
 * "Blokas NUOTEKŲ TVARKYMO ĮRENGINIAI (INTS savininko darbo aplinka)"
 */

@Component
public class NtisINTSOwnerDashboardPage extends eu.itreegroup.spark.app.common.FormBase {

    public static final String VIEW_INTS_INFO_ACTION = "VIEW_INTS_INFO";

    public static final String VIEW_INTS_INFO_ACTION_NAME = "View INTS information";

    public static final String RENEW_INTS_INFO_ACTION = "RENEW_INTS_INFO";

    public static final String RENEW_INTS_INFO_ACTION_NAME = "Renew INTS information";

    public static final String MANAGE_INTS_MANAGERS_ACTION = "MANAGE_INTS_MANAGERS";

    public static final String MANAGE_INTS_MANAGERS_ACTION_NAME = "Manage INTS managers";

    public static final String REMOVE_FROM_ACCOUNT_ACTION = "REMOVE_FROM_ACCOUNT";

    public static final String REMOVE_FROM_ACCOUNT_ACTION_NAME = "Remove from account";

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    NtisFacilityOwnersDBService ntisFacilityOwnersDBService;

    @Autowired
    NtisSelectedFacilitiesDBService ntisSelectedFacilitiesDBService;

    @Autowired
    NtisCommonMethods ntisCommonMethods;

    @Override
    public String getFormName() {
        return "NTIS_INTS_OWNER_DASHBOARD_PAGE";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "INTS savininko pagrindinis puslapis", "INTS savininko pagrindinio puslapio forma");
        addFormActions(NtisINTSOwnerDashboardPage.ACTION_CREATE, NtisINTSOwnerDashboardPage.ACTION_UPDATE);
        addFormAction(NtisCommonActionsConstants.INTS_OWNER_ACTIONS, NtisCommonActionsConstants.INTS_OWNER_ACTIONS_DESC,
                NtisCommonActionsConstants.INTS_OWNER_ACTIONS_DESC);
        addFormAction(VIEW_INTS_INFO_ACTION, VIEW_INTS_INFO_ACTION_NAME, VIEW_INTS_INFO_ACTION_NAME);
        addFormAction(RENEW_INTS_INFO_ACTION, RENEW_INTS_INFO_ACTION_NAME, RENEW_INTS_INFO_ACTION_NAME);
        addFormAction(MANAGE_INTS_MANAGERS_ACTION, MANAGE_INTS_MANAGERS_ACTION_NAME, MANAGE_INTS_MANAGERS_ACTION_NAME);
        addFormAction(REMOVE_FROM_ACCOUNT_ACTION, REMOVE_FROM_ACCOUNT_ACTION_NAME, REMOVE_FROM_ACCOUNT_ACTION_NAME);
    }

    /**
     * Metodas grąžins individualaus nuotekų tvarkymo įrenginio/įrenginių savininko pagrindinio puslapio informaciją: duomenis apie
     * nuotekų tvarkymo įrenginius, tech. priežiūros, nuotekų tvarkymo užsakymus, sutartis ir laboratorinius tyrimus.
     * @param conn - prisijungimas prie DB
     * @param orgId - naudotojo organizacijios id, kuriai priklauso dirbantis naudotojas
     * @param perId - nuoroda į asmens informaciją, kuriai yra priskirtas prisijungęs naudotojas.
     * @param lang - sesijos kalba
     * @param usrId - nuoroda į naudotojo informaciją, kuriai yra priskirtas prisijungęs naudotojas.
     * @returns NtisINTSDashboardModel objektas
     * @throws Exception
     */
    public NtisINTSDashboardModel getDashboardInfo(Connection conn, Double orgId, Double perId, String lang, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisCommonActionsConstants.INTS_OWNER_ACTIONS);
        NtisINTSDashboardModel dashboardInfo = new NtisINTSDashboardModel();
        ArrayList<NtisINTSDashboardWastewater> facilities = getFacilities(conn, orgId, perId, usrId, lang);
        dashboardInfo.setContracts(new ArrayList<>());
        dashboardInfo.setDisposalOrders(new ArrayList<>());
        dashboardInfo.setLabs(new ArrayList<>());
        dashboardInfo.setTechOrders(new ArrayList<>());
        boolean isSelected = false;
        if (facilities != null && !facilities.isEmpty()) {
            Double selectedWtf = ntisSelectedFacilitiesDBService.getSelecteFacility(conn, usrId, orgId);
            if (selectedWtf != null) {
                dashboardInfo.setDisposalOrders(getOrders(conn, perId, orgId, NtisServiceItemType.VEZIMAS.getCode(), lang, selectedWtf, usrId));
                dashboardInfo.setTechOrders(getOrders(conn, perId, orgId, NtisServiceItemType.PRIEZIURA.getCode(), lang, selectedWtf, usrId));
                dashboardInfo.setContracts(getContracts(conn, perId, orgId, selectedWtf, lang));
                dashboardInfo.setLabs(getLabOrders(conn, perId, orgId, selectedWtf, usrId, lang));
                isSelected = true;
            }
        }
        if (!isSelected && !facilities.isEmpty()) {
            updateSelectedWtf(conn, usrId, orgId, facilities.get(0).getWtf_id());
            dashboardInfo.setDisposalOrders(getOrders(conn, perId, orgId, NtisServiceItemType.VEZIMAS.getCode(), lang, facilities.get(0).getWtf_id(), usrId));
            dashboardInfo.setTechOrders(getOrders(conn, perId, orgId, NtisServiceItemType.PRIEZIURA.getCode(), lang, facilities.get(0).getWtf_id(), usrId));
            dashboardInfo.setContracts(getContracts(conn, perId, orgId, facilities.get(0).getWtf_id(), lang));
            dashboardInfo.setLabs(getLabOrders(conn, perId, orgId, facilities.get(0).getWtf_id(), usrId, lang));
        }
        dashboardInfo.setFacilities(getFacilities(conn, orgId, perId, usrId, lang));
        if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.INTS_OWNER_ACTIONS)) {

            String[] recordActionMenu = { NtisINTSOwnerDashboardPage.ACTION_UPDATE, NtisINTSOwnerDashboardPage.ACTION_CREATE,
                    NtisINTSOwnerDashboardPage.VIEW_INTS_INFO_ACTION, NtisINTSOwnerDashboardPage.RENEW_INTS_INFO_ACTION,
                    NtisINTSOwnerDashboardPage.MANAGE_INTS_MANAGERS_ACTION, NtisINTSOwnerDashboardPage.REMOVE_FROM_ACCOUNT_ACTION };
            dashboardInfo.setAvailableActions(recordActionMenu);
        }
        return dashboardInfo;
    }

    /**
     * Metodas atnaujins nuotekų tvarkymo įrenginio savininko nuotekų tvarkymo įrenginio pasirinkimą
     * @param conn - prisijungimas prie DB
     * @param userId - naudotojas, kuris nori pasižymėti nuotekų tašką
     * @param orgId - organizacija, su kuria naudotojas šiuo metu dirba
     * @param selectedWtf - naujai pasirinkto nuotekų tvarkymo įrenginio id.
     * @throws Exception
     */
    public void updateSelectedWtf(Connection conn, Double userId, Double orgId, Double selectedWtf) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisINTSOwnerDashboardPage.ACTION_UPDATE);
        NtisSelectedFacilitiesDAO selectedFacility = null;
        if (orgId != null) {
            selectedFacility = ntisSelectedFacilitiesDBService.loadRecordByParams(conn, " fs_usr_id = ?::int and fs_org_id = ?::int ",
                    new SelectParamValue(userId), new SelectParamValue(orgId));
        } else {
            selectedFacility = ntisSelectedFacilitiesDBService.loadRecordByParams(conn, " fs_usr_id = ?::int and fs_org_id is null ",
                    new SelectParamValue(userId));
        }
        if (selectedFacility == null) {
            selectedFacility = ntisSelectedFacilitiesDBService.newRecord();
            selectedFacility.setFs_usr_id(userId);
            selectedFacility.setFs_org_id(orgId);
        }
        selectedFacility.setFs_wtf_id(selectedWtf);
        ntisSelectedFacilitiesDBService.saveRecord(conn, selectedFacility);
    }

    private ArrayList<NtisINTSDashboardOrder> getOrders(Connection conn, Double perId, Double orgId, String srvType, String lang, Double wtfId, Double usrId)
            throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT " + //
                "SRV.SRV_ID, " + //
                "ORD.ORD_ID, " + //
                "ORD.ORD_CREATED AS ORD_DATE, " + //
                "ORD.ORD_WTF_ID AS WTF_ID, " + //
                "COALESCE(RFC.RFT_DISPLAY_CODE, RFC.RFC_MEANING) AS ORD_STATE, " + //
                "REV.REV_ID, " + //
                "REV.REV_SCORE, " + //
                "REV.REV_COMMENT, " + //
                "ORG.ORG_NAME AS SRV_PROVIDER " + //
                "FROM NTIS.NTIS_ORDERS ORD " + //
                "LEFT JOIN NTIS_REVIEWS REV ON REV.REV_ORD_ID = ORD.ORD_ID AND REV.REV_USR_ID = ?::int " + //
                "INNER JOIN NTIS.NTIS_SERVICES SRV ON SRV.SRV_ID = ORD.ORD_SRV_ID AND SRV_TYPE = ? AND ORD.ORD_WTF_ID = ?::int " + //
                "INNER JOIN SPARK.SPR_ORGANIZATIONS ORG ON SRV.SRV_ORG_ID = ORG.ORG_ID " + //
                "LEFT JOIN SPR_REF_CODES_VW RFC ON RFC.RFC_CODE = ORD.ORD_STATE AND RFC.RFC_DOMAIN = 'NTIS_ORDER_STATUS' AND RFC.RFT_LANG = ? ");
        this.ntisCommonMethods.addConditionForOwner(stmt, orgId, perId, usrId);
        stmt.addSelectParam(usrId);
        stmt.addSelectParam(srvType);
        stmt.addSelectParam(wtfId);
        stmt.addSelectParam(lang);
        stmt.setStatementOrderPart("order by ord.ord_created desc limit 3");
        List<NtisINTSDashboardOrder> data = queryController.selectQueryAsObjectArrayList(conn, stmt, NtisINTSDashboardOrder.class);
        return (ArrayList<NtisINTSDashboardOrder>) data;
    }

    private ArrayList<NtisINTSDashboardWastewater> getFacilities(Connection conn, Double orgId, Double perId, Double usrId, String lang) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        Double selectedWts = ntisSelectedFacilitiesDBService.getSelecteFacility(conn, usrId, orgId);
        if (selectedWts == null) {
            selectedWts = Double.valueOf(-1);
        }
        String objView = "";
        String objViewID = " 1  as fo_so_id";

        if (hasUserRole(conn, NtisRolesConstants.INTS_OWNER) || hasUserRole(conn, NtisRolesConstants.INTS_OWNER_ORG_ADMIN)) {
            objView = """
                    LEFT JOIN ( select fo_so_id
                    from  ntis_facility_owners
                    where fo_so_id is not null and fo_owner_type in ( 'OWNER', 'MANAGER')  and current_date between fo_date_from and COALESCE(fo_date_to, current_date)
                            """
                    + (orgId != null ? " and  fo_org_id = ?::int " : " and  fo_per_id = ?::int ") + //
                    " group by fo_so_id ) fo on so_id = fo_so_id";
            objViewID = " fo_so_id ";
        } else {

        }

        String statement = null;
        if (orgId != null) {
            statement = "with wtf_assigned as " + //
                    "    (select o.fo_wtf_id wtf_id, " + //
                    "    STRING_AGG (o.fo_owner_type ,', ') owner_type " + //
                    "    from ntis_facility_owners o where o.fo_org_id = ?::int and " + //
                    "    CURRENT_DATE between DATE_TRUNC('day', o.FO_DATE_FROM) and COALESCE(DATE_TRUNC('day', o.FO_DATE_TO),CURRENT_DATE) " + //
                    "    group by o.fo_wtf_id) ";
            stmt.addSelectParam(orgId);

        } else {
            statement = "with wtf_assigned as " + //
                    "    (select o.fo_wtf_id wtf_id, " + //
                    "    STRING_AGG (o.fo_owner_type ,', ') owner_type " + //
                    "    from ntis_facility_owners o where o.fo_per_id = ?::int and " + //
                    "    CURRENT_DATE between DATE_TRUNC('day', o.FO_DATE_FROM) and COALESCE(DATE_TRUNC('day', o.FO_DATE_TO),CURRENT_DATE) " + //
                    "    group by o.fo_wtf_id) ";
            stmt.addSelectParam(perId);
        }
        stmt.setStatement(statement + //
                """
                        SELECT WTF.WTF_ID,
                               coalesce(RFC.RFC_MEANING, WTF.WTF_TYPE) AS WTF_TYPE,
                               WTF.WTF_STATE,
                               CASE
                                 WHEN WTF.WTF_AD_ID IS NOT NULL THEN
                                  WAV.FULL_ADDRESS_TEXT || ' (' || WTF.WTF_FACILITY_LATITUDE || ', ' ||
                                  WTF.WTF_FACILITY_LONGITUDE || ') '
                                 ELSE
                                  WTF.WTF_FACILITY_LATITUDE || ', ' || WTF.WTF_FACILITY_LONGITUDE
                               END WTF_ADDRESS,
                               WTF.WTF_FACILITY_LATITUDE AS LATITUDE,
                               WTF.WTF_FACILITY_LONGITUDE AS LONGITUDE,
                               CASE
                                 WHEN WTF.WTF_ID != ? THEN
                                  'N'
                                 ELSE
                                  'Y'
                               END FO_SELECTED,
                               case
                                 when POSITION('OWNER' IN asign.owner_type) != 0 then
                                  'OWNER'
                                 else
                                  case
                                    when POSITION('SELF_ASSIGNED' IN asign.owner_type) != 0 then
                                     'SELF_ASSIGNED'
                                    else
                                     case
                                       when POSITION('MANAGER' IN asign.owner_type) != 0 then
                                        'MANAGER'
                                       else
                                        ''
                                     end
                                  end
                               end fo_owner_type,
                               wtf.*,
                               (SELECT JSONB_AGG(JSONB_BUILD_OBJECT('so_id',
                                    SO.SO_ID,
                                    'so_ad_id',
                                    SO.SO_AD_ID,
                                    'so_address',
                                    WAV.FULL_ADDRESS_TEXT,
                                    'so_latitude',
                                    SO.SO_COORDINATE_LATITUDE,
                                    'so_longitude',
                                    SO.SO_COORDINATE_LONGITUDE,
                                   'restrict',
                                    'Y',
                                    'fo_so_id',
                                     """ + objViewID
                + """
                              ) order by WAV.street , nullif(regexp_replace(WAV.BUILDING_NO, '[^0-9]','', 'g'), '')::numeric, nullif(regexp_replace(WAV.FLAT_NO , '[^0-9]','', 'g'), '')::numeric ) AS WTF_SERVED_OBJECTS
                        FROM NTIS.NTIS_SERVED_OBJECTS SO
                        """
                + objView + """
                                LEFT JOIN ntis.ntis_building_ntrs ntrs ON so.so_bn_id = ntrs.bn_id
                                LEFT JOIN ntis.ntis_address_vw WAV ON WAV.address_id = ntrs.bn_ad_id
                             WHERE SO.SO_WTF_ID = WTF.WTF_ID
                             GROUP BY SO.SO_WTF_ID) as WTF_SERVED_OBJECTS,
                               FUA.FUA_STATE AS WTF_FUA_STATE,
                               FUA.FUA_ID AS FUA_ID,
                               ful.ful_id
                          FROM NTIS.NTIS_WASTEWATER_TREATMENT_FACI WTF
                         inner join wtf_assigned asign
                            on asign.wtf_id = wtf.wtf_id
                          LEFT JOIN NTIS.NTIS_ADDRESS_VW WAV
                            ON WAV.ADDRESS_ID = WTF.WTF_AD_ID
                          LEFT JOIN NTIS.NTIS_FACILITY_UPDATE_AGREEMENT FUA
                            ON FUA.FUA_WTF_ID = WTF.WTF_ID
                           and FUA.FUA_STATE = 'SUBMITTED'
                         INNER JOIN SPARK.SPR_REF_CODES_VW RFC
                            ON WTF.WTF_TYPE = RFC.RFC_CODE
                           and RFC.RFC_DOMAIN = 'NTIS_WTF_TYPE'
                           and RFC.RFT_LANG = ?
                          LEFT JOIN (select ful_id, ful_wtf_id
                                     from ntis_facility_update_logs
                                     where ful_operation = 'INSERT'
                                     and case when ? is not null then ful_org_id = ?::int
                                         else ful_usr_id = ?::int
                                         end
                                     ) ful
                            on   ful_wtf_id = wtf.WTF_ID
                        """);

        stmt.addSelectParam(selectedWts);
        if (hasUserRole(conn, NtisRolesConstants.INTS_OWNER) || hasUserRole(conn, NtisRolesConstants.INTS_OWNER_ORG_ADMIN)) {
            if (hasUserRole(conn, NtisRolesConstants.INTS_OWNER_ORG_ADMIN) && orgId != null) {
                stmt.addSelectParam(orgId);
            } else {
                stmt.addSelectParam(orgId != null ? orgId : perId);
            }
        }
        stmt.addSelectParam(lang);
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(usrId);
        return (ArrayList<NtisINTSDashboardWastewater>) queryController.selectQueryAsObjectArrayList(conn, stmt, NtisINTSDashboardWastewater.class);
    }

    private ArrayList<NtisINTSDashboardContract> getContracts(Connection conn, Double perId, Double orgId, Double wtfId, String lang) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT " + //
                "COT.COT_ID, " + //
                "COT.COT_CREATED, " + //
                "COT.COT_WTF_ID, " + //
                "coalesce(RFC.RFC_MEANING, COT.COT_STATE) AS COT_STATE, " + //
                "ORG.ORG_NAME AS COT_SRV_PROVIDER " + //
                "FROM NTIS.NTIS_CONTRACTS COT " + //
                "INNER JOIN NTIS.NTIS_CONTRACT_SERVICES CS ON COT.COT_ID = CS.CS_COT_ID AND COT.COT_WTF_ID = ?::int " + //
                "INNER JOIN NTIS.NTIS_SERVICES SRV ON SRV.SRV_ID = CS.CS_SRV_ID " + //
                "INNER JOIN SPARK.SPR_ORGANIZATIONS ORG ON ORG.ORG_ID = SRV.SRV_ORG_ID " + //
                "INNER JOIN SPARK.SPR_REF_CODES_VW RFC ON RFC.RFC_CODE = COT.COT_STATE AND RFC.RFC_DOMAIN = 'NTIS_COT_STATE' AND RFC.RFT_LANG = ? ");
        stmt.addSelectParam(wtfId);
        stmt.addSelectParam(lang);
        if (orgId != null) {
            stmt.addParam4WherePart("COT.COT_ORG_ID = ?::int ", orgId);
        } else {
            stmt.addParam4WherePart("COT.COT_PER_ID = ?::int ", perId);
        }
        stmt.setStatementGroupPart(" group by cot_id, cot_created, cot_wtf_id, rfc_meaning, cot_state, org_name ");
        stmt.setStatementOrderPart("order by cot.cot_created desc limit 3");
        return (ArrayList<NtisINTSDashboardContract>) queryController.selectQueryAsObjectArrayList(conn, stmt, NtisINTSDashboardContract.class);
    }

    private ArrayList<NtisINTSDashboardLab> getLabOrders(Connection conn, Double perId, Double orgId, Double wtfId, Double usrId, String lang)
            throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT " + //
                "ORD.ORD_ID, " + //
                "ORD.ORD_CREATED, " + //
                "coalesce(RFCC.RFC_MEANING, '-') as COMPLIANCE_NORMS, " + //
                "coalesce(RFC.RFC_MEANING, ORD.ORD_STATE) as ORD_STATE, " + //
                "REV.REV_ID, " + //
                "REV.REV_SCORE, " + //
                "REV.REV_COMMENT, " + //
                "ORG.ORG_NAME AS LAB_NAME " + //
                "FROM NTIS.NTIS_ORDERS ORD " + //
                "LEFT JOIN NTIS.NTIS_REVIEWS REV ON REV.REV_ORD_ID = ORD.ORD_ID AND REV.REV_USR_ID = ?::int " + //
                "INNER JOIN NTIS.NTIS_SERVICES SRV ON ORD.ORD_SRV_ID = SRV.SRV_ID AND SRV.SRV_TYPE = '" + NtisServiceItemType.TYRIMAI + "' " + //
                "INNER JOIN SPARK.SPR_ORGANIZATIONS ORG ON ORG.ORG_ID = SRV.SRV_ORG_ID AND ORD.ORD_WTF_ID = ?::int " + //
                "LEFT JOIN SPARK.SPR_REF_CODES_VW RFCC ON RFCC.RFC_CODE = ORD.ORD_COMPLIANCE_NORMS AND RFCC.RFC_DOMAIN = 'NTIS_ORD_RESEARCH_NORMS' AND RFCC.RFT_LANG = ? "
                + //
                "INNER JOIN SPARK.SPR_REF_CODES_VW RFC ON RFC.RFC_CODE = ORD.ORD_STATE AND RFC.RFC_DOMAIN = 'NTIS_ORDER_STATUS' AND RFC.RFT_LANG = ? ");
        stmt.addSelectParam(usrId);
        stmt.addSelectParam(wtfId);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(lang);
        this.ntisCommonMethods.addConditionForOwner(stmt, orgId, perId, usrId);
        stmt.setStatementOrderPart("order by ord.ord_created desc limit 3");
        ArrayList<NtisINTSDashboardLab> data = (ArrayList<NtisINTSDashboardLab>) queryController.selectQueryAsObjectArrayList(conn, stmt,
                NtisINTSDashboardLab.class);
        return data;
    }

    /**
     * Pagal perduodamą nuotekų tvarkymo savininko įrašo id, metodas atnaujins įrašui priskirtą "iki" datą
     * @param conn - prisijungimas prie DB
     * @param wtfId - nuotekų tvarkymo įrenginio id
     * @param orgId - naudotojo organizacijios id, kuriai priklauso dirbantis naudotojas
     * @param perId - nuoroda į asmens informaciją, kuriai yra priskirtas prisijungęs naudotojas.
     * @param userId - nuoroda į naudotojo informaciją, kuriai yra priskirtas prisijungęs naudotojas.
     * @throws Exception
     */
    public void removeFacility(Connection conn, Double wtfId, Double orgId, Double perId, Double userId) throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, NtisINTSOwnerDashboardPage.ACTION_UPDATE);
        NtisFacilityOwnersDAO ntisFacilityOwnersDAO = null;
        if (orgId != null) {
            ntisFacilityOwnersDAO = ntisFacilityOwnersDBService.loadRecordByParams(conn, " fo_wtf_id = ?::int and fo_org_id::int = ? ",
                    new SelectParamValue(wtfId), new SelectParamValue(orgId));
        } else {
            ntisFacilityOwnersDAO = ntisFacilityOwnersDBService.loadRecordByParams(conn, " fo_wtf_id = ?::int and fo_per_id = ?::int ",
                    new SelectParamValue(wtfId), new SelectParamValue(perId));
        }
        ntisFacilityOwnersDAO.setFo_date_to(Utils.getDate(new Date((new Date()).getTime() - (1000 * 60 * 60 * 24))));
        ntisFacilityOwnersDBService.updateRecord(conn, ntisFacilityOwnersDAO);

        // check and remove selected facility
        NtisSelectedFacilitiesDAO selectedFacility = null;
        if (orgId != null) {
            selectedFacility = ntisSelectedFacilitiesDBService.loadRecordByParams(conn, " fs_usr_id = ?::int and fs_org_id = ?::int and fs_wtf_id::int = ? ",
                    new SelectParamValue(userId), new SelectParamValue(orgId), new SelectParamValue(wtfId));
        } else {
            selectedFacility = ntisSelectedFacilitiesDBService.loadRecordByParams(conn, " fs_usr_id = ?::int and fs_org_id is null and fs_wtf_id = ?::int ",
                    new SelectParamValue(userId), new SelectParamValue(wtfId));
        }
        if (selectedFacility != null) {
            ntisSelectedFacilitiesDBService.deleteRecord(conn, selectedFacility);
        }
    }
}