package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBServiceImpl;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBServiceImpl;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.dao.NtisWastewaterTreatmentOrgDAO;
import lt.project.ntis.enums.NtisOrgType;
import lt.project.ntis.logic.forms.security.NtisWaterManagerFacilitiesListSecurityManager;
import lt.project.ntis.models.NtisWaterManagerFacility;
import lt.project.ntis.service.NtisWastewaterTreatmentOrgDBService;

/**
 * Klasė skirta formos "Valyklų sąrašas" (formos kodas A1130) biznio logikai apibrėžti
 */

@Component
public class NtisWaterManagerFacilitiesList extends FormBase {

    private final BaseControllerJDBC baseControllerJDBC;

    private final NtisWastewaterTreatmentOrgDBService wastewaterTreatmentOrgDbService;

    private final DBStatementManager dbStatementManager;

    private final SprOrgUsersDBServiceImpl sprOrgUsersDBService;

    private final SprOrganizationsDBServiceImpl sprOrganizationsDBService;

    public NtisWaterManagerFacilitiesList(BaseControllerJDBC baseControllerJDBC, NtisWastewaterTreatmentOrgDBService wastewaterTreatmentOrgDbService,
            DBStatementManager dbStatementManager, SprOrgUsersDBServiceImpl sprOrgUsersDBService, SprOrganizationsDBServiceImpl sprOrganizationsDBService) {
        super();
        this.baseControllerJDBC = baseControllerJDBC;
        this.wastewaterTreatmentOrgDbService = wastewaterTreatmentOrgDbService;
        this.dbStatementManager = dbStatementManager;
        this.sprOrgUsersDBService = sprOrgUsersDBService;
        this.sprOrganizationsDBService = sprOrganizationsDBService;
    }

    @Override
    public String getFormName() {
        return "NTIS_WATER_MANAGER_FACILITIES_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Vandentvarkos įmonės valyklų sąrašas", "Vandentvarkos įmonės valyklų sąrašo forma");
        addFormActions(FormBase.ACTION_READ, FormBase.ACTION_UPDATE, FormBase.ACTION_CREATE);
    }

    /**
     * Metodas grąžins vandentvarkos įmonei priklausančių nuotekų valyklų sąrašą
     * @param conn - prisijungimas prie DB
     * @param params - select parametrai
     * @param orgId - naudotojo organizacijios id, kuriai priklauso dirbantis naudotojas
     * @param usrId - naudotojo sesijos id
     * @return json objektas
     * @throws Exception
     */
    public String getFacilitiesList(Connection conn, SelectRequestParams params, Double orgId, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisWaterManagerFacilitiesList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT WTO_ID, " + //
                "WTO_NAME, " + //
                "WTO_ADDRESS, " + //
                "WTO_PRODUCTIVITY, " + //
                "WTO_DOMESTIC_SEWAGE, " + //
                "WTO_INDUSTRIAL_SEWAGE, " + //
                "WTO_SLUDGE, " + //
                "WTO_IS_IT_USED, " + //
                "VW.FULL_ADDRESS_TEXT AS WTO_ADDRESS, " + //
                "WTO_AD_ID AS ADDRESS_ID, " + //
                "coalesce(wto_auto_accept, 'N') AS wto_auto_accept, " + //
                "coalesce(wto_no_notifications, 'N') AS wto_no_notifications " + //
                "FROM NTIS_WASTEWATER_TREATMENT_ORG WTO " + //
                "INNER JOIN SPR_ORG_USERS ON WTO_ORG_ID = OU_ORG_ID AND OU_USR_ID = ?::int and CURRENT_DATE between OU_DATE_FROM AND COALESCE(OU_DATE_TO, now()) "
                + //
                "INNER JOIN NTIS_ADDRESS_VW VW ON WTO_AD_ID = VW.ADDRESS_ID " + //
                "WHERE WTO_ORG_ID = ?::int");

        stmt.setWhereExists(true);
        stmt.addSelectParam(usrId);
        stmt.addSelectParam(orgId);
        stmt.setStatementOrderPart("order by wto_is_it_used desc, wto_id desc");

        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), orgId, stmt, advancedParamList);
        stmt.addParam4WherePart("wto_name", StatementAndParams.PARAM_STRING, advancedParamList.get("wto_name"));
        stmt.addParam4WherePart("wto_address", StatementAndParams.PARAM_STRING, advancedParamList.get("wto_address"));
        stmt.addParam4WherePart("wto_productivity", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("wto_productivity"));
        stmt.addParam4WherePart("wto_domestic_sewage", StatementAndParams.PARAM_STRING, advancedParamList.get("wto_domestic_sewage"));
        stmt.addParam4WherePart("wto_industrial_sewage", StatementAndParams.PARAM_STRING, advancedParamList.get("wto_industrial_sewage"));
        stmt.addParam4WherePart("wto_sludge", StatementAndParams.PARAM_STRING, advancedParamList.get("wto_sludge"));
        stmt.addParam4WherePart("wto_is_it_used ", StatementAndParams.PARAM_STRING, advancedParamList.get("wto_is_it_used"));
        stmt.addParam4WherePart("coalesce(wto_auto_accept, 'N')", StatementAndParams.PARAM_STRING, advancedParamList.get("wto_auto_accept"));

        stmt.addParam4WherePart(dbStatementManager.colNamesToConcatString("wto_name", "wto_address", "wto_productivity"), StatementAndParams.PARAM_STRING,
                advancedParamList.get("quickSearch"));

        NtisWaterManagerFacilitiesListSecurityManager sqm = new NtisWaterManagerFacilitiesListSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { NtisWaterManagerFacilitiesList.ACTION_READ, NtisWaterManagerFacilitiesList.ACTION_UPDATE,
                NtisWaterManagerFacilitiesList.ACTION_CREATE };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);

        return baseControllerJDBC.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    /**
     * Pagal nurodytą NtisWaterManagerFacility objektą, sukurs naują įrašą arba atnaujins jau sukurto vandentvarkos įmonės valyklos įrašo duomenis
     * @param record - NtisWaterManagerFacility objektas
     * @param conn - prisijungimas prie DB
     * @param orgId - sesijos organizacijos id
     * @param usrId - sesijos naudotojo id
     * @return NtisWastewaterTreatmentOrgDAO objektas
     * @throws Exception
     */
    public NtisWastewaterTreatmentOrgDAO save(Connection conn, NtisWaterManagerFacility record, Double orgId, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn,
                record.getWto_id() == null ? NtisWaterManagerFacilitiesList.ACTION_CREATE : NtisWaterManagerFacilitiesList.ACTION_UPDATE);
        NtisWastewaterTreatmentOrgDAO daoObj = null;
        if (!sprOrgUsersDBService.checkIsUserInOrganization(conn, usrId, orgId)) {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
        if (record.getWto_id() != null) {
            daoObj = wastewaterTreatmentOrgDbService.loadRecord(conn, record.getWto_id());
            if (!Objects.equals(daoObj.getWto_org_id(), orgId)) {
                throw new SparkBusinessException(
                        new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
            }
        } else {
            daoObj = wastewaterTreatmentOrgDbService.newRecord();
        }
        daoObj.setWto_ad_id(record.getAddress_id());
        daoObj.setWto_id(record.getWto_id());
        daoObj.setWto_name(record.getWto_name());
        daoObj.setWto_domestic_sewage(record.getWto_domestic_sewage());
        daoObj.setWto_industrial_sewage(record.getWto_industrial_sewage());
        daoObj.setWto_sludge(record.getWto_sludge());
        daoObj.setWto_org_id(orgId);
        daoObj.setWto_is_it_used(record.getWto_is_it_used());
        daoObj.setWto_productivity(record.getWto_productivity());
        daoObj.setWto_address(record.getWto_address());
        daoObj.setWto_auto_accept(record.getWto_auto_accept());
        daoObj.setWto_no_notifications(record.getWto_no_notifications());
        return wastewaterTreatmentOrgDbService.saveRecord(conn, daoObj);
    }

    public Boolean checkIfFacilitiesRegistered(Connection conn, Double orgId, Double usrId) throws Exception {

        if (orgId != null) {
            SprOrganizationsDAO organization = sprOrganizationsDBService.loadRecordByParams(conn, "org_id = ?::int and c01 in (? , ?) ",
                    new SelectParamValue(orgId), new SelectParamValue(NtisOrgType.VANDEN.getCode()),
                    new SelectParamValue(NtisOrgType.PASLAUG_VANDEN.getCode()));
            if (organization != null) {
                StatementAndParams stmt = new StatementAndParams("""
                            select wto_id
                              from ntis_wastewater_treatment_org
                        inner join spr_org_users on wto_org_id = ou_org_id and ou_usr_id = ?::int
                             where wto_org_id = ?::int and wto_is_it_used = 'Y';
                            """);
                stmt.addSelectParam(usrId);
                stmt.addSelectParam(organization.getOrg_id());
                List<NtisWastewaterTreatmentOrgDAO> facilities = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt,
                        NtisWastewaterTreatmentOrgDAO.class);
                if (facilities != null) {
                    return !facilities.isEmpty();
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else {
            return true;
        }

    }
}
