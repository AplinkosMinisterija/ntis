package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.dao.NtisFacilityOwnersDAO;
import lt.project.ntis.enums.NtisFacilityOwnerType;
import lt.project.ntis.enums.NtisIntsManagerStatus;
import lt.project.ntis.logic.forms.security.NtisWfManagersListSecurityManager;
import lt.project.ntis.models.NtisFacilityManagerEditModel;
import lt.project.ntis.models.NtisWastewaterTreatmentFacility;
import lt.project.ntis.service.NtisFacilityOwnersDBService;

/**
 * Klasė skirta formos "Nuotekų tvarkymo įrenginio valdytojai" (formos kodas N4070)  biznio logikai apibrėžti
 */
@Component
public class NtisWfManagersList extends FormBase {

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    NtisFacilityOwnersDBService facilityOwnersDbService;

    @Override
    public String getFormName() {
        return "NTIS_WF_MANAGERS_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Nuotekų tvarkymo įrenginio valdytojai", "Nuotekų tvarkymo įrenginio valdytojų forma");
        addFormActions(FormBase.ACTION_READ, FormBase.ACTION_UPDATE, FormBase.ACTION_CREATE);
    }

    /**
     * Metodas grąžins nurodytam nuotekų tvarkymo įrenginiui priklausančių valdytojų (fizinių asmenų) sąrašą
     * @param conn - prisijungimas prie DB
     * @param params - select parametrai
     * @param orgId - naudotojo organizacijios id, kuriai priklauso dirbantis naudotojas
     * @param perId - nuoroda į asmens informaciją, kuriai yra priskirtas prisijungęs naudotojas.
     * @return json objektas
     * @throws Exception
     */
    public String getWfManagersList(Connection conn, SelectRequestParams params, Double perId, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisWfManagersList.ACTION_READ);
        checkIsOwner(conn, Utils.getDouble(params.getParamList().get("wtfId")), perId, orgId);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("select  " + //
                "per.per_name || ' ' || per.per_surname as manager, " + //
                "fo.fo_id, " + //
                "case when " + //
                "     fo.fo_date_to is not null and fo.fo_date_to >= current_date and fo.fo_date_from is not null " + //
                "     then '" + NtisIntsManagerStatus.ACTIVE + "' " + //
                "     when fo.fo_date_from is not null and fo.fo_date_from <= current_date and fo.fo_date_to is null " + //
                "     then '" + NtisIntsManagerStatus.ACTIVE + "' " + //
                "     else '" + NtisIntsManagerStatus.INACTIVE + "' " + //
                "end as status, " + //
                "to_char(fo.fo_date_from, " + //
                "   '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as date_from, " + //
                "to_char(fo.fo_date_to, " + //
                "   '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as date_to, " + //
                "to_char(fo.fo_date_from, " + //
                "   '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') || ' - ' || " + //
                "coalesce(to_char(fo.fo_date_to, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "'), ' ') as full_date " + //
                "from ntis_facility_owners fo " + //
                "inner join ntis_wastewater_treatment_faci wtf on fo.fo_wtf_id = wtf.wtf_id " + //
                "inner join spr_persons per on fo.fo_per_id = per.per_id " + //
                "where fo_so_id is null and  fo_wtf_id = ?::int and fo_owner_type = '" + NtisFacilityOwnerType.MANAGER + "'");
        stmt.setWhereExists(true);
        stmt.addSelectParam(Utils.getDouble(params.getParamList().get("wtfId")));
        stmt.setStatementOrderPart("order by fo.fo_date_to desc, fo.fo_id desc");

        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), orgId, stmt, advancedParamList);
        stmt.addParam4WherePart("per.per_name || ' ' || per.per_surname", StatementAndParams.PARAM_STRING, advancedParamList.get("manager"));
        stmt.addParam4WherePart("fo_date_from", StatementAndParams.PARAM_DATE, advancedParamList.get("fo_date_from"),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart("fo_date_to", StatementAndParams.PARAM_DATE, advancedParamList.get("fo_date_to"),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));

        stmt.addParam4WherePart(
                dbStatementManager.colNamesToConcatString("per.per_name || ' ' || per.per_surname",
                        "to_char(fo_date_from,'" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')",
                        "to_char(fo_date_to,'" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        NtisWfManagersListSecurityManager sqm = new NtisWfManagersListSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { NtisWaterManagerFacilitiesList.ACTION_READ, NtisWaterManagerFacilitiesList.ACTION_UPDATE,
                NtisWaterManagerFacilitiesList.ACTION_CREATE };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    /**
     * Pagal perduodamą įrašo id, metodas grąžins pasirinkto nuotekų tvarkymo įrenginio duomenis
     * @param conn - prisijungimas prie DB
     * @param wtfId - nuotekų tvarkymo įrenginio įrašo NTIS_WASTEWATER_TREATMENT_FACI lentelėje id
     * @param orgId - naudotojo organizacijios id, kuriai priklauso dirbantis naudotojas
     * @param perId - nuoroda į asmens informaciją, kuriai yra priskirtas prisijungęs naudotojas.
     * @return NtisWastewaterTreatmentFacility objektas
     * @throws Exception
     */
    public NtisWastewaterTreatmentFacility getSelectedWtf(Connection conn, Double wtfId, Double perId, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisWfManagersList.ACTION_READ);
        checkIsOwner(conn, wtfId, perId, orgId);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("select wtf.wtf_id, " + //
                "coalesce (wav.full_address_text, " + //
                "        '('||wtf.wtf_facility_latitude || ', ' || wtf.wtf_facility_longitude || ')') as wtf_address " + //
                "from ntis.ntis_wastewater_treatment_faci wtf " + //
                "left join ntis.ntis_address_vw wav on wav.address_id = wtf.wtf_ad_id " + //
                "where wtf.wtf_id = ?::int ");
        stmt.setWhereExists(true);
        stmt.addSelectParam(wtfId);
        ArrayList<NtisWastewaterTreatmentFacility> data = (ArrayList<NtisWastewaterTreatmentFacility>) baseControllerJDBC.selectQueryAsObjectArrayList(conn,
                stmt, NtisWastewaterTreatmentFacility.class);
        if (data != null && !data.isEmpty()) {
            return data.get(0);
        } else {
            return null;
        }
    }

    /**
     * Pagal perduodamus parametrus, funkcija atnaujins NTIS_FACILITY_OWNERS lentelėje išsaugoto įrašo FO_DATE_TO laukelį.
     * @param conn - prisijungimas prie DB
     * @param managerDetails - NtisFacilityManagerEditModel objektas, kuriuo perduodamas NTIS_FACILITY_OWNERS įrašo id ir naudotojo įvesta data
     * @param orgId - naudotojo organizacijios id, kuriai priklauso dirbantis naudotojas
     * @param perId - nuoroda į asmens informaciją, kuriai yra priskirtas prisijungęs naudotojas.
     * @throws Exception
     * 
     */
    public void updateFacilityManagerDate(Connection conn, NtisFacilityManagerEditModel managerDetails, Double perId, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisWfManagersList.ACTION_UPDATE);
        checkIsOwner(conn, managerDetails.getFo_wtf_id(), perId, orgId);
        NtisFacilityOwnersDAO owner = facilityOwnersDbService.loadRecord(conn, managerDetails.getFo_id());
        owner.setFo_date_to(Utils.getDate(managerDetails.getFo_date_to()));
        facilityOwnersDbService.saveRecord(conn, owner);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                        update ntis_facility_owners
                          set fo_date_to = ?
                        where fo_fo_Id = ?::int
                """);
        stmt.addSelectParam(managerDetails.getFo_date_to());
        stmt.addSelectParam(owner.getFo_id());
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);
    }

    /**
     * Metodas patikrins ar naudotojas yra pasirinkto nuotekų tvarkymo įrenginio savininkas 
     * ir ar turi teisę matyti/redaguoti jam priskirtus valdytojus.
     * Jeigu naudotojas nėra savininkas, bus rodoma klaida.
     * @param conn - prisijungimas prie DB
     * @param wtfId - pasirinkto nuotekų tvarkymo įrenginio id 
     * @param orgId - naudotojo organizacijios id, kuriai priklauso dirbantis naudotojas
     * @param perId - nuoroda į asmens informaciją, kuriai yra priskirtas prisijungęs naudotojas.
     * @return boolean
     * @throws Exception
     * 
     */
    public boolean checkIsOwner(Connection conn, Double wtfId, Double perId, Double orgId) throws Exception {
        boolean isOwner;
        if (orgId != null) {
            isOwner = facilityOwnersDbService.isOrganizationAnOwnerOfFacility(conn, orgId, wtfId);
        } else {
            isOwner = facilityOwnersDbService.isPersonAnOwnerOfFacility(conn, perId, wtfId);
        }
        if (isOwner) {
            return true;
        } else {
            throw new SparkBusinessException(new S2Message("common.error.notAnOwner", SparkMessageType.ERROR, "User is not the owner of selected facility"));
        }
    }
}
