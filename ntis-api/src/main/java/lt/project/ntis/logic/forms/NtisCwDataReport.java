package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import lt.project.ntis.enums.NtisCwFilStatus;
import lt.project.ntis.enums.NtisOrgType;
import lt.project.ntis.logic.forms.security.NtisCentralizedWastewaterDataReportSecurityManager;

/**
 * Klasė skirta formos "Centraliztuoto nuotekų tvarkymo duomenų teikimo suvestinė" (formos kodas N4230) biznio logikai apibrėžti
 */

@Component
public class NtisCwDataReport extends FormBase {

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbStatementManager;

    @Override
    public String getFormName() {
        return "NTIS_CW_DATA_REPORT";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Centraliztuoto nuotekų tvarkymo duomenų teikimo suvestinė",
                "Centraliztuoto nuotekų tvarkymo duomenų teikimo suvestinės forma");
        addFormActions(FormBase.ACTION_READ);
    }

    /**
     * Metodas grąžins centralizuoto nuotekų tvarkymo suvestinę, skirtą sistemos administratoriui
     * @param conn - prisijungimas prie DB
     * @param params - filtravimo parametrai
     * @throws Exception
     */
    public String getReport(Connection conn, SelectRequestParams params) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisCwDataReport.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("with period_data as (select t1.cwf_id," + //
                "                            t1.cwf_org_id, " + //
                "                            t1.cwf_import_date, " + //
                "                            row_number() over (partition by t1.cwf_org_id order by t1.cwf_import_date desc, t1.cwf_status desc) rownum " + //
                "                            from ntis.ntis_cw_files t1 " + //
                "                            where date_trunc('day', t1.cwf_import_date) between ? and ? " + //
                "                       ) " + //
                " select org.org_id,  " + //
                "        concat_ws(', ', org.org_name , org.org_type , org.org_code) as org_name,  " + //
                "        org.org_address,  " + //
                "        org.org_delegation_person,  " + //
                "        org.org_email,  " + //
                "        org.org_phone,  " + //
                "        org.c03 as org_website,  " + //
                "        to_char(max(f.cwf_import_date),'" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') latest_date, " + //
                "           (select f2.cwf_status " + //
                "           from ntis.ntis_cw_files f2 " + //
                "           where f2.cwf_org_id = org.org_id " + //
                "           order by f2.cwf_import_date desc, f2.cwf_status desc" + //
                "           limit 1 " + //
                "           ) state_latest_date, " + //
                "        max(case when pd.cwf_import_date is not null then f.cwf_status else null end) state_in_period, " + //
                "        case when sum(case when pd.cwf_import_date is not null then 1 else 0 end) > 1 then to_char(max(pd.cwf_import_date),'"
                + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') || ' (' || " + //
                "        sum(case when pd.cwf_import_date is not null then 1 else 0 end) || ') ' " + //
                "        else to_char(max(pd.cwf_import_date),'" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)
                + "') end date_in_period " + //
                "   from spark.spr_organizations org " + //
                "   inner join spark.spr_organizations org2 on org.org_id = org2.org_id and org2.c01 in ('" + NtisOrgType.VANDEN + "', '"
                + NtisOrgType.PASLAUG_VANDEN + "')" + //
                "   left join ntis.ntis_cw_files f on f.cwf_org_id = org.org_id " + //
                "   left join period_data pd on pd.cwf_id = f.cwf_id ");

        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();

        if (advancedParamList.get("cwf_period_from") != null && advancedParamList.get("cwf_period_from").getParamValue().getValue() != null) {
            stmt.addSelectParam(Utils.getDateFromString(advancedParamList.get("cwf_period_from").getParamValue().getValue(),
                    dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA)));
        } else {
            LocalDate firstDay = LocalDate.now().withDayOfMonth(1);
            Date date = Date.from(firstDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
            stmt.addSelectParam(Utils.getDate(date));
        }
        if (advancedParamList.get("cwf_period_to") != null && advancedParamList.get("cwf_period_to").getParamValue().getValue() != null) {
            stmt.addSelectParam(Utils.getDateFromString(advancedParamList.get("cwf_period_to").getParamValue().getValue(),
                    dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA)));
        } else {
            stmt.addSelectParam(Utils.getDate(new Date()));
        }

        if (advancedParamList.get("latest_date") != null && advancedParamList.get("latest_date").getParamValue().getValue() != null) {
            stmt.addParam4WherePart("f.cwf_import_date", StatementAndParams.PARAM_DATE, advancedParamList.get("latest_date"),
                    dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        }

        stmt.addParam4WherePart("org2.org_id", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("org_id"));

        if (advancedParamList.get("cwf_status") != null
                && advancedParamList.get("cwf_status").getParamValue().getValues().contains(NtisCwFilStatus.NOT_SUBMITTED.getCode())
                && !advancedParamList.get("cwf_status").getParamValue().getValues().contains(NtisCwFilStatus.CW_FIL_FINAL.getCode())
                && !advancedParamList.get("cwf_status").getParamValue().getValues().contains(NtisCwFilStatus.CW_FIL_PENDING.getCode())) {
            stmt.addCondition4WherePart(" f.cwf_status is null ", " and ");
        }
        if (advancedParamList.get("cwf_status") != null
                && (advancedParamList.get("cwf_status").getParamValue().getValues().contains(NtisCwFilStatus.CW_FIL_FINAL.getCode())
                        || advancedParamList.get("cwf_status").getParamValue().getValues().contains(NtisCwFilStatus.CW_FIL_PENDING.getCode()))) {
            stmt.addParam4WherePart("f.cwf_status", StatementAndParams.PARAM_STRING, advancedParamList.get("cwf_status"));
            if (advancedParamList.get("cwf_status").getParamValue().getValues().contains(NtisCwFilStatus.NOT_SUBMITTED.getCode())) {
                stmt.addCondition4WherePart(" f.cwf_status is null ", " or ");
            }
        }

        stmt.addParam4WherePart(dbStatementManager.colNamesToConcatString("org.org_name", "org.org_code", "org.org_type"), StatementAndParams.PARAM_STRING,
                advancedParamList.get("quickSearch"));

        stmt.setStatementGroupPart(" group by org.org_id,  " + //
                "        org.org_name || ', '|| org.org_type || ', ' || org.org_code,  " + //
                "        org.org_address,  " + //
                "        org.org_delegation_person,  " + //
                "        org.org_email,  " + //
                "        org.org_phone,  " + //
                "        org.c03 ");

        NtisCentralizedWastewaterDataReportSecurityManager sqm = new NtisCentralizedWastewaterDataReportSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { NtisCwDataReport.ACTION_READ };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);

        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    /**
     * Metodas grąžins vandentvarkos įmonių sąrašą
     * @param conn - prisijungimas prie DB
     * @throws Exception
     */
    public String getWaterManagers(Connection conn) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisCwDataReport.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("select org.org_id as id, " + //
                "concat_ws(', ',org.org_name, org.org_type, org.org_code) as value " + //
                "from spr_organizations org " + //
                "where org.c01 in ('" + NtisOrgType.VANDEN + "', '" + NtisOrgType.PASLAUG_VANDEN + "') ");
        return queryController.selectQueryAsJSON(conn, stmt);
    }

}