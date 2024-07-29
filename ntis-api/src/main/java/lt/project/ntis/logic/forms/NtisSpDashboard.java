package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.service.SprRefCodesDBService;
import lt.project.ntis.enums.NtisGraphDataPeriod;
import lt.project.ntis.enums.NtisOrderStatus;
import lt.project.ntis.enums.NtisServiceItemType;

/**
 * Klasė skirta formos "Paslaugų teikėjo pagrindinis puslapis" biznio logikai apibrėžti
 */
@Component
public class NtisSpDashboard extends FormBase {

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    SprRefCodesDBService refCodesService;

    @Override
    public String getFormName() {
        return "NTIS_SP_DASHBOARD";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Paslaugų teikėjo pagrindinis puslapis", "Paslaugų teikėjo pagrindinio puslapio forma");
        addFormActions(FormBase.ACTION_READ, FormBase.ACTION_CREATE);
    }

    /**
     * Metodas grąžins paslaugų teikėjo teikiamų paslaugų pateiktų ir patvirtintų užsakymų sąrašą
     * @param conn - prisijungimas prie DB
     * @param orgId - sesijos organizacijos id
     * @param lang - sesijos kalba
     * @return json objektas
     * @throws Exception
     */
    public String getOrders(Connection conn, Double orgId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisSpDashboard.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(" select coalesce(typ.rft_display_code, typ.rfc_meaning) as service, " //
                + " srv.srv_type, " //
                + " org.org_name || ', ' || org.org_address as service_provider, " //
                + " count(case when ord.ord_state = '" + NtisOrderStatus.ORD_STS_SUBMITTED + "' then 1 else null end) total_submitted, "//
                + " count(case when ord.ord_state = '" + NtisOrderStatus.ORD_STS_CONFIRMED + "' then 1 else null end) total_confirmed "//
                + " from ntis.ntis_services srv "//
                + " inner join spark.spr_organizations org on org.org_id = srv.srv_org_id and org.org_id = ? "//
                + " left join ntis.ntis_orders ord on "//
                + "     ord.ord_srv_id = srv.srv_id and " //
                + "     ord.ord_state in ('" + NtisOrderStatus.ORD_STS_SUBMITTED + "', '" + NtisOrderStatus.ORD_STS_CONFIRMED + "') " //
                + " left join spr_ref_codes_vw typ on typ.rfc_code = srv.srv_type and typ.rfc_domain = 'NTIS_SRV_ITEM_TYPE' and typ.rft_lang = ? " //
                + "where  srv.srv_type <> 'VALYMAS'");
        stmt.setWhereExists(true);
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(lang);
        stmt.setStatementOrderPart("order by typ.rft_display_code desc ");
        stmt.setStatementGroupPart("group by typ.rft_display_code, typ.rfc_meaning, org.org_name, org.org_address, srv.srv_type ");
        return queryController.selectQueryAsJSON(conn, stmt);
    }

    /**
     * Metodas grąžins įvykdytų paslaugų teikėjo  užsakymų sąrašą pasirinktame laikotarpyje
     * @param conn - prisijungimas prie DB
     * @param orgId - sesijos organizacijos id
     * @param period - pasirinkto periodo klasifikatorius
     * @return json objektas
     * @throws Exception
     */
    public String getFinishedOrders(Connection conn, Double orgId, String period) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisSpDashboard.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        ArrayList<String> dates = NtisGraphDataPeriod.checkPeriod(period);
        if (period.equalsIgnoreCase(NtisGraphDataPeriod.GRP_DAT_PRD_THIS_YEAR.getCode())) {
            stmt.setStatement("select count(ord.ord_id) as order_count, " //
                    + " to_char(date_trunc('month', gs.d), 'FMMM') as label" //
                    + " from ntis.ntis_services srv " //
                    + " inner join spark.spr_organizations org on org.org_id = srv.srv_org_id and org.org_id = ?  " //
                    + " left join ntis.ntis_orders ord on ord.ord_srv_id = srv.srv_id and srv.srv_org_id = org.org_id "//
                    + "     and ord.ord_state = '" + NtisOrderStatus.ORD_STS_FINISHED + "' "//
                    + " left join ntis.ntis_order_completed_works ocw on ocw.ocw_ord_id = ord.ord_id " //
                    + " right join generate_series( ?::date,  ?::date , '1 day'::interval) gs(d) "//
                    + "     on date_trunc('day', gs.d) = date_trunc('day', ocw.ocw_completed_date ) ");
            stmt.addSelectParam(orgId);
            stmt.addSelectParam(dates.get(0));
            stmt.addSelectParam(dates.get(1));
            stmt.setStatementGroupPart(" group by date_trunc('month', gs.d) ");
            stmt.setStatementOrderPart(" order by date_trunc('month', gs.d) ");
        } else {
            stmt.setStatement("select count(ord.ord_id) as order_count, " //
                    + " to_char(gs.d, 'MM-DD') as label " //
                    + " from ntis.ntis_services srv " //
                    + " inner join spark.spr_organizations org on org.org_id = srv.srv_org_id and org.org_id = ?  " //
                    + " left join ntis.ntis_orders ord on ord.ord_srv_id = srv.srv_id and srv.srv_org_id = org.org_id "//
                    + "     and ord.ord_state = '" + NtisOrderStatus.ORD_STS_FINISHED + "' "//
                    + " left join ntis.ntis_order_completed_works ocw on ocw.ocw_ord_id = ord.ord_id " //
                    + " right join generate_series( ?::date,  ?::date , '1 day'::interval) gs(d) "//
                    + "     on date_trunc('day', gs.d) = date_trunc('day', ocw.ocw_completed_date) ");
            stmt.addSelectParam(orgId);
            stmt.addSelectParam(dates.get(0));
            stmt.addSelectParam(dates.get(1));
            stmt.setStatementGroupPart(" group by gs.d ");
            stmt.setStatementOrderPart(" order by gs.d ");
        }
        return queryController.selectQueryAsJSON(conn, stmt);
    }

    /**
     * Metodas grąžins paslaugų teikėjo išvežtų nuotekų kiekį gautuose nuotekų išvežimo užsakymuose pasirinktame laikotarpyje
     * @param conn - prisijungimas prie DB
     * @param orgId - sesijos organizacijos id
     * @param period - pasirinkto periodo klasifikatorius
     * @return json objektas
     * @throws Exception
     */
    public String getDischargedSludge(Connection conn, Double orgId, String period) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisSpDashboard.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        ArrayList<String> dates = NtisGraphDataPeriod.checkPeriod(period);
        if (period.equalsIgnoreCase(NtisGraphDataPeriod.GRP_DAT_PRD_THIS_YEAR.getCode())) {
            stmt.setStatement("select sum(ocw.ocw_discharged_sludge_amount) as sludge_amount, " //
                    + " to_char(date_trunc('month', gs.d), 'FMMM') as label" //
                    + " from ntis.ntis_orders ord " //
                    + " inner join ntis.ntis_services srv on srv.srv_id = ord.ord_srv_id and srv.srv_type = '" + NtisServiceItemType.VEZIMAS + "' " //
                    + " inner join spark.spr_organizations org on org.org_id = srv.srv_org_id  and org.org_id = ?  " //
                    + " inner join ntis.ntis_order_completed_works ocw on ocw.ocw_ord_id = ord.ord_id "//
                    + "     and ord.ord_state = '" + NtisOrderStatus.ORD_STS_FINISHED + "' " //
                    + " right join generate_series( ?::date,  ?::date , '1 day'::interval) gs(d) "//
                    + "     on date_trunc('day', gs.d) = date_trunc('day', ocw.ocw_completed_date) ");
            stmt.addSelectParam(orgId);
            stmt.addSelectParam(dates.get(0));
            stmt.addSelectParam(dates.get(1));
            stmt.setStatementGroupPart(" group by date_trunc('month', gs.d) ");
            stmt.setStatementOrderPart(" order by date_trunc('month', gs.d) ");
        } else {
            stmt.setStatement("select sum(ocw.ocw_discharged_sludge_amount) as sludge_amount, " //
                    + " to_char(gs.d, 'MM-DD') as label " //
                    + " from ntis.ntis_orders ord " //
                    + " inner join ntis.ntis_services srv on srv.srv_id = ord.ord_srv_id and srv.srv_type = '" + NtisServiceItemType.VEZIMAS + "' " //
                    + " inner join spark.spr_organizations org on org.org_id = srv.srv_org_id  and org.org_id = ?  " //
                    + " inner join ntis.ntis_order_completed_works ocw on ocw.ocw_ord_id = ord.ord_id "//
                    + "     and ord.ord_state = '" + NtisOrderStatus.ORD_STS_FINISHED + "' " //
                    + " right join generate_series( ?::date,  ?::date , '1 day'::interval) gs(d) "//
                    + "     on date_trunc('day', gs.d) = date_trunc('day', ocw.ocw_completed_date) ");
            stmt.addSelectParam(orgId);
            stmt.addSelectParam(dates.get(0));
            stmt.addSelectParam(dates.get(1));
            stmt.setStatementGroupPart(" group by gs.d ");
            stmt.setStatementOrderPart(" order by gs.d ");
        }
        return queryController.selectQueryAsJSON(conn, stmt);
    }
}
