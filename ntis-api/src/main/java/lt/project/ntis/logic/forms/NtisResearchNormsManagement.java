package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.Date;
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
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.YesNo;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.dao.NtisResearchNormDAO;
import lt.project.ntis.logic.forms.security.NtisResearchNormsManagementSecurityManager;
import lt.project.ntis.models.NtisResearchNormEditModel;
import lt.project.ntis.service.NtisResearchNormDBService;

/**
 * Klasė skirta tyrimo normų valdymo formos (kodas T3120) biznio logikai apibrėžti
 */
@Component
public class NtisResearchNormsManagement extends FormBase {

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    NtisResearchNormDBService researchNormDbService;

    @Override
    public String getFormName() {
        return "NTIS_RESEARCH_NORMS_MANAGEMENT";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Tyrimų normų valdymas", "Tyrimų normų valdymo forma");
        addFormActions(ACTION_READ, ACTION_UPDATE, ACTION_CREATE);
    }

    /**
     * Metodas grąžins sistemoje įvestų tyrimų normų sąrašą
     * @param conn - prisijungimas prie DB
     * @param params - select parametrai
     * @param lang - sesijos kalba
     * @return json objektas
     * @throws Exception
     */
    public String getResearchNormsList(Connection conn, SelectRequestParams params, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisResearchNormsManagement.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("select rn_id, " + //
                "coalesce(type.rfc_meaning, rn_research_type) as rn_research_type, " + //
                "rn_research_type as research_type_clsf, " + //
                "rn_research_norm, " + //
                "coalesce(inst.rfc_meaning, rn_facility_installation_date) as rn_facility_installation_date, " + //
                "rn_facility_installation_date as installation_clsf, " + //
                "to_char(rn_date_from, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as rn_date_from " + //
                "from ntis_research_norms " + //
                "left join spr_ref_codes_vw type on type.rfc_code = rn_research_type and type.rfc_domain = 'NTIS_RESEARCH_TYPE' and type.rft_lang = ? " + //
                "left join spr_ref_codes_vw inst on inst.rfc_code = rn_facility_installation_date and inst.rfc_domain = 'NTIS_WTF_INSTALL_PERIOD' and inst.rft_lang = ? "
                + //
                "where current_date >= rn_date_from and (current_date <= rn_date_to or rn_date_to is null)");
        stmt.addSelectParam(lang);
        stmt.addSelectParam(lang);
        stmt.setWhereExists(true);
        stmt.setStatementOrderPart(" order by rn_research_type ");

        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        stmt.addParam4WherePart("rn_research_type", StatementAndParams.PARAM_STRING, advancedParamList.get("rn_research_type"));
        stmt.addParam4WherePart("rn_research_norm", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("rn_research_norm"));
        stmt.addParam4WherePart("rn_facility_installation_date", StatementAndParams.PARAM_STRING, advancedParamList.get("rn_facility_installation_date"));
        stmt.addParam4WherePart("rn_date_from", StatementAndParams.PARAM_DATE, advancedParamList.get("rn_date_from"),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(
                dbStatementManager.colNamesToConcatString("type.rfc_meaning", "rn_research_norm", "inst.rfc_meaning",
                        "TO_CHAR(rn_date_from,'" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        NtisResearchNormsManagementSecurityManager sqm = new NtisResearchNormsManagementSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { NtisResearchNormsManagement.ACTION_READ, NtisResearchNormsManagement.ACTION_UPDATE };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    /**
     * Pagal perduodamą NtisResearchNormEditModel objektą, metodas išsaugos naują tyrimo normos įrašą pasirinktam tyrimo parametrui.
     * Prieš išsaugojimą bus patikrinama, ar buvo įvesta nauja norma ir nauja galiojimo data;
     * Taip pat vykdomas patikrinimas ar naujai įvesta datos nuo galiojimo reikšmė yra didesnė nei esamos registruotos 
     * normos datos nuo reikšmė, ir ar pasirinktam tyrimo parametrui jau yra sukurtas normos įrašas su galiojimo datos nuo reikšme,
     * kuri yra didesnė už naujai įvestą galiojimo reikšmę. 
     * @param conn - prisijungimas prie DB
     * @param normEdit - NtisResearchNormEditModel objektas
     * @param userId - nuoroda į naudotojo informaciją, kuriai yra priskirtas prisijungęs naudotojas
     * @throws Exception
     */
    public void saveResearchNorm(Connection conn, NtisResearchNormEditModel normEdit, Double userId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisResearchNormsManagement.ACTION_UPDATE);
        NtisResearchNormDAO researchNorm = researchNormDbService.loadRecord(conn, normEdit.getRn_id());
        if (researchNorm.getRn_research_norm() != null && researchNorm.getRn_research_norm().compareTo(normEdit.getRn_research_norm()) == 0
                && researchNorm.getRn_date_from().compareTo(Utils.getDate(normEdit.getRn_date_from())) == 0
                || researchNorm.getRn_date_from().compareTo(Utils.getDate(normEdit.getRn_date_from())) == 0) {
            throw new SparkBusinessException(
                    new S2Message("common.error.researchNormAlreadyExists", SparkMessageType.ERROR, "Such research norm already exists"));
        }
        if (researchNorm.getRn_date_from().compareTo(Utils.getDate(normEdit.getRn_date_from())) < 0) {
            NtisResearchNormDAO laterDateResearchNorm = null;
            if (researchNorm.getRn_facility_installation_date() != null && !researchNorm.getRn_facility_installation_date().equalsIgnoreCase("")) {
                laterDateResearchNorm = researchNormDbService.loadRecordByParams(conn,
                        " rn_research_type = ? and rn_facility_installation_date = ? and rn_date_from > ? ",
                        new SelectParamValue(researchNorm.getRn_research_type()), new SelectParamValue(researchNorm.getRn_facility_installation_date()),
                        new SelectParamValue(researchNorm.getRn_date_from()));
            } else {
                laterDateResearchNorm = researchNormDbService.loadRecordByParams(conn, " rn_research_type = ? and rn_date_from > ? ",
                        new SelectParamValue(researchNorm.getRn_research_type()), new SelectParamValue(researchNorm.getRn_date_from()));
            }
            if (laterDateResearchNorm != null) {
                throw new SparkBusinessException(
                        new S2Message("common.error.researchNormPeriodExists", SparkMessageType.ERROR, "Research norm for this period already exists"));
            }
            researchNorm.setRn_newest(YesNo.N.getCode());
            researchNorm.setRn_date_to(Utils.getDate(new Date(normEdit.getRn_date_from().getTime() - (1000 * 60 * 60 * 24))));
            researchNormDbService.saveRecord(conn, researchNorm);
            researchNorm = researchNormDbService.newRecord();
            researchNorm.setRn_date_from(Utils.getDate(normEdit.getRn_date_from()));
            researchNorm.setRn_created(Utils.getDate(new Date()));
            researchNorm.setRn_research_norm(normEdit.getRn_research_norm());
            researchNorm.setRn_date_to(normEdit.getRn_date_to() != null ? Utils.getDate(normEdit.getRn_date_to()) : null);
            researchNorm.setRn_newest(YesNo.Y.getCode());
            researchNorm
                    .setRn_facility_installation_date(normEdit.getRn_facility_installation_date() != null ? normEdit.getRn_facility_installation_date() : null);
            researchNorm.setRn_usr_id(userId);
            researchNorm.setRn_research_type(normEdit.getRn_research_type());
            researchNormDbService.saveRecord(conn, researchNorm);
        } else {
            throw new SparkBusinessException(new S2Message("common.error.researchNormPeriodNotPossible", SparkMessageType.ERROR,
                    "The \"date from\" cannot be earlier than the currently fixed one"));
        }
    }
}
