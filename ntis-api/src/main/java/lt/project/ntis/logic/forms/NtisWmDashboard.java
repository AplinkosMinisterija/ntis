package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.constants.DbConstants;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.service.SprRefCodesDBService;
import lt.project.ntis.enums.NtisGraphDataPeriod;
import lt.project.ntis.enums.NtisOrgType;
import lt.project.ntis.enums.NtisSewageDeliveryStatus;

/**
 * Klasė skirta formos "Vandentvarkos pagrindinis puslapis" biznio logikai apibrėžti
 */
@Component
public class NtisWmDashboard extends FormBase {

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    SprRefCodesDBService refCodesService;

    @Override
    public String getFormName() {
        return "NTIS_WM_DASHBOARD";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Vandentvarkos pagrindinis puslapis", "Vandentvarkos pagrindinio puslapio forma");
        addFormActions(FormBase.ACTION_READ);
    }

    /**
     * Metodas grąžins vandentvarkos įmonei pateiktų aktualių nuotekų (dumblo) tvarkymo pristatymų sąrašą sugrupuotą 
     * pagal paslaugų teikėjų organizacijas
     * @param conn - prisijungimas prie DB
     * @param orgId - sesijos organizacijos id
     * @return json objektas
     * @throws Exception
     */
    public String getDeliveryOrdersBySp(Connection conn, Double orgId, Double wtoId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisWmDashboard.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(" select sp.org_name as carrier,  " //
                + " sp.org_id, " //
                + " count(*) total_submitted "//
                + " from spark.spr_organizations sp " //
                + " inner join ntis.ntis_cars cr on cr.cr_org_id = sp.org_id " //
                + " inner join ntis.ntis_wastewater_deliveries wd on wd.wd_cr_id = cr.cr_id  "//
                + "     and wd.wd_state = '" + NtisSewageDeliveryStatus.SWG_DLV_STS_SUBMITTED + "' " //
                + " inner join ntis.ntis_wastewater_treatment_org wto on wto.wto_id = wd.wd_wto_id  "
                + " inner join spark.spr_organizations wm on wm.org_id = wto.wto_org_id and wm.org_id = ? "//
                + "     and wm.c01 in ('" + NtisOrgType.VANDEN + "', '" + NtisOrgType.PASLAUG_VANDEN + "') ");
        stmt.addSelectParam(orgId);
        stmt.setStatementGroupPart("group by sp.org_name, sp.org_id ");
        stmt.setStatementOrderPart("order by count(case when wd.wd_state = '" + NtisSewageDeliveryStatus.SWG_DLV_STS_SUBMITTED + "' then 1 else 0 end) desc");
        stmt.addParam4WherePart("wto.wto_id = ? ", wtoId);
        return queryController.selectQueryAsJSON(conn, stmt);
    }

    /**
     * Metodas grąžins vandentvarkos įmonės nuotekų pristatymų kiekio sąrašą pasirinktame laikotarpyje
     * @param conn - prisijungimas prie DB
     * @param orgId - sesijos organizacijos id
     * @param period - pasirinkto periodo klasifikatorius
     * @return json objektas
     * @throws Exception
     */
    public String getConfirmedDeliveries(Connection conn, Double orgId, String period, Double wtoId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisWmDashboard.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        ArrayList<String> dates = NtisGraphDataPeriod.checkPeriod(period);
        if (period.equalsIgnoreCase(NtisGraphDataPeriod.GRP_DAT_PRD_THIS_YEAR.getCode())) {
            stmt.setStatement("select count(wd.wd_id) as count, " //
                    + " to_char(date_trunc('month', gs.d), 'FMMM') as label" //
                    + " from ntis.ntis_wastewater_deliveries wd " //
                    + " inner join ntis.ntis_wastewater_treatment_org wto on wd.wd_wto_id = wto.wto_id "
                    + " inner join spark.spr_organizations org on org.org_id = wto.wto_org_id and org.org_id = ? " //
                    + "     and wd.wd_state = '" + NtisSewageDeliveryStatus.SWG_DLV_STS_CONFIRMED + "' " //
                    + " right join generate_series( ?::date  ,  ?::date ," + "  '1 day'::interval) gs(d) "//
                    + "     on date_trunc('day', gs.d) = date_trunc('day', wd.wd_delivery_date ) ");
            stmt.addSelectParam(orgId);
            stmt.addSelectParam(dates.get(0));
            stmt.addSelectParam(dates.get(1));
            stmt.addParam4WherePart("wto.wto_id is null or wto.wto_id = ? ", wtoId);
            stmt.setStatementGroupPart(" group by date_trunc('month', gs.d) ");
            stmt.setStatementOrderPart(" order by date_trunc('month', gs.d) ");
        } else {
            stmt.setStatement("select count(wd.wd_id) as count, " //
                    + " to_char(gs.d, 'MM-DD') as label " //
                    + " from ntis.ntis_wastewater_deliveries wd " //
                    + " inner join ntis.ntis_wastewater_treatment_org wto on wd.wd_wto_id = wto.wto_id "
                    + " inner join spark.spr_organizations org on org.org_id = wto.wto_org_id and org.org_id = ? " //
                    + "     and wd.wd_state = '" + NtisSewageDeliveryStatus.SWG_DLV_STS_CONFIRMED + "' " //
                    + " right join generate_series( ?::date,  ?::date , '1 day'::interval) gs(d) "//
                    + "     on date_trunc('day', gs.d) = date_trunc('day', wd.wd_delivery_date) ");
            stmt.addSelectParam(orgId);
            stmt.addSelectParam(dates.get(0));
            stmt.addSelectParam(dates.get(1));
            stmt.addParam4WherePart("wto.wto_id is null or wto.wto_id = ? ", wtoId);
            stmt.setStatementGroupPart(" group by gs.d ");
            stmt.setStatementOrderPart(" order by gs.d ");
        }
        return queryController.selectQueryAsJSON(conn, stmt);
    }

    /**
     * Metodas grąžins vandentvarkos įmonės priimtų nuotekų kiekio sąrašą pasirinktame laikotarpyje
     * @param conn - prisijungimas prie DB
     * @param orgId - sesijos organizacijos id
     * @param period - pasirinkto periodo klasifikatorius
     * @return json objektas
     * @throws Exception
     */
    public String getAcceptedSludge(Connection conn, Double orgId, String period, Double wtoId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisWmDashboard.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        ArrayList<String> dates = NtisGraphDataPeriod.checkPeriod(period);
        if (period.equalsIgnoreCase(NtisGraphDataPeriod.GRP_DAT_PRD_THIS_YEAR.getCode())) {
            stmt.setStatement("select sum(wd.wd_delivered_quantity) as count, " //
                    + " to_char(date_trunc('month', gs.d), 'FMMM') as label" //
                    + " from ntis.ntis_wastewater_deliveries wd" //
                    + " inner join ntis.ntis_wastewater_treatment_org wto on wd.wd_wto_id = wto.wto_id "
                    + " inner join spark.spr_organizations org on org.org_id = wto.wto_org_id and org.org_id = ? " //
                    + "     and wd.wd_state = '" + NtisSewageDeliveryStatus.SWG_DLV_STS_CONFIRMED + "' " //
                    + " right join generate_series( ?::date,  ?::date , '1 day'::interval) gs(d) "//
                    + "     on date_trunc('day', gs.d) = date_trunc('day', wd.wd_delivery_date) ");
            stmt.addSelectParam(orgId);
            stmt.addSelectParam(dates.get(0));
            stmt.addSelectParam(dates.get(1));
            stmt.addParam4WherePart("wto.wto_id is null or wto.wto_id = ? ", wtoId);
            stmt.setStatementGroupPart(" group by date_trunc('month', gs.d) ");
            stmt.setStatementOrderPart(" order by date_trunc('month', gs.d) ");
        } else {
            stmt.setStatement("select sum(wd.wd_delivered_quantity) as count, " //
                    + " to_char(gs.d, 'MM-DD') as label " //
                    + " from ntis.ntis_wastewater_deliveries wd" //
                    + " inner join ntis.ntis_wastewater_treatment_org wto on wd.wd_wto_id = wto.wto_id "
                    + " inner join spark.spr_organizations org on org.org_id = wto.wto_org_id and org.org_id = ? " //
                    + "     and wd.wd_state = '" + NtisSewageDeliveryStatus.SWG_DLV_STS_CONFIRMED + "' " //
                    + " right join generate_series( ?::date,  ?::date , '1 day'::interval) gs(d) "//
                    + "     on date_trunc('day', gs.d) = date_trunc('day', wd.wd_delivery_date) ");
            stmt.addSelectParam(orgId);
            stmt.addSelectParam(dates.get(0));
            stmt.addSelectParam(dates.get(1));
            stmt.addParam4WherePart("wto.wto_id is null or wto.wto_id = ? ", wtoId);
            stmt.setStatementGroupPart(" group by gs.d ");
            stmt.setStatementOrderPart(" order by gs.d ");
        }
        return queryController.selectQueryAsJSON(conn, stmt);
    }

    /**
     * Metodas grąžins vandentvarkos įmonei priklausančių valyklų sąrašą
     * @param conn - prisijungimas prie DB
     * @param orgId - sesijos organizacijos id
     * @return json objektas
     * @throws Exception
     */
    public String getWastewaterFacilities(Connection conn, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisWmDashboard.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("select wto_id as value, " //
                + "wto_name || ', ' || wto_address as option " //
                + "from ntis.ntis_wastewater_treatment_org " //
                + "where wto_org_id = ? and wto_is_it_used = '" + DbConstants.BOOLEAN_TRUE + "'");
        stmt.addSelectParam(orgId);
        return queryController.selectQueryAsJSON(conn, stmt);
    }
}
