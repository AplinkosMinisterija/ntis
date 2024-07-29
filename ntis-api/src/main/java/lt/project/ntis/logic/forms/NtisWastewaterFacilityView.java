package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.constants.NtisCommonActionsConstants;
import lt.project.ntis.constants.NtisRolesConstants;
import lt.project.ntis.dao.NtisFacilityOwnersDAO;
import lt.project.ntis.enums.NtisClassifierDomains;
import lt.project.ntis.enums.NtisQueryOperations;
import lt.project.ntis.logic.forms.brokerws.RcBroker;
import lt.project.ntis.logic.forms.security.NtisWastewaterFacilityViewSecurityManager;
import lt.project.ntis.models.NtrOwnerModel;
import lt.project.ntis.service.NtisFacilityOwnersDBService;

@Component
public class NtisWastewaterFacilityView extends FormBase {

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    NtisFacilityOwnersDBService ntisFacilityOwnersDBService;

    @Autowired
    RcBroker ntr;

    @Autowired
    SprOrganizationsDBService sprOrganizationsDBService;

    @Override
    public String getFormName() {
        return "WASTEWATER_FACILITY_VIEW";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Įrenginio ir aptarnaujamo objekto peržiūra", "Įrenginio ir aptarnaujamo objekto peržiūra");
        addFormAction(FormBase.ACTION_READ, FormBase.ACTION_READ_DESC, FormBase.ACTION_READ_DESC);
        addFormAction(NtisCommonActionsConstants.NTIS_ADMIN_ACTIONS, NtisCommonActionsConstants.NTIS_ADMIN_ACTIONS_DESC,
                NtisCommonActionsConstants.NTIS_ADMIN_ACTIONS_DESC);
    }

    public String getNtrObjects(Connection conn, Double soId, Double perId, Double orgId, String language) throws Exception {

        checkIsFormActionAssigned(conn, NtisWastewaterFacilityView.ACTION_READ);

        Double condition = this.hasUserRole(conn, NtisRolesConstants.MUNICI_SPECIALIST) ? 1.0 : 0.0;

        if (this.hasUserRole(conn, NtisRolesConstants.INTS_OWNER)) {
            NtisFacilityOwnersDAO ownerRec = new NtisFacilityOwnersDAO();
            if (orgId == null) {
                ownerRec = ntisFacilityOwnersDBService.loadRecordByParams(conn,
                        " WHERE   fo_owner_type in ( 'OWNER', 'MANAGER', 'SELF_ASSIGNED') and fo_so_id = ?::int and fo_per_id = ?::int  and   current_date between fo_date_from and COALESCE(fo_date_to, current_date) ",
                        new SelectParamValue(soId), new SelectParamValue(perId));
            } else {
                ownerRec = ntisFacilityOwnersDBService.loadRecordByParams(conn,
                        " WHERE   fo_owner_type in ( 'OWNER', 'MANAGER', 'SELF_ASSIGNED') and fo_so_id = ?::int and fo_org_id = ?::int  and   current_date between fo_date_from and COALESCE(fo_date_to, current_date) ",
                        new SelectParamValue(soId), new SelectParamValue(orgId));
            }

            if (ownerRec == null || ownerRec.getFo_id() == null) {
                throw new SparkBusinessException(
                        new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));

            }
        }

        StatementAndParams stmt = new StatementAndParams();

        stmt.setStatement(
                """
                        SELECT ba_state as  ba_state,
                               org_a.org_name,
                               org_a.org_code,
                               coalesce(org_m.org_name,  case when ba_source ='MANUAL' then org_a.org_name else null end) AS org_name_m,
                               coalesce(org_m.org_code , case when ba_source ='MANUAL' then org_a.org_code else null end) AS org_code_m,
                               ba_a.ba_network_connection_date,
                               ba_a.ba_network_disconnection_date,
                               rfc_aggrx.rfc_meaning AS ba_wastewater_treatment_m,
                               rfc_aggrx.rfc_meaning AS ba_wastewater_treatment,
                               coalesce(ba_wastewater_treatment, 'LOCAL') as ba_wastewater_treatment_clsf,
                               coalesce(fua_network_connection_date, case when ba_source ='MANUAL' then ba_network_connection_date else null end)   AS ba_network_connection_date_m,
                               null as cwfd_atjungimo_data,
                               cwf.cwf_import_date,
                               CASE
                                 WHEN 1=? THEN
                                  (SELECT JSONB_AGG(JSONB_BUILD_OBJECT('bno_name',
                                                                       bno.bno_name,
                                                                       'bno_lastname',
                                                                       bno.bno_lastname,
                                                                       'bno_org_name',
                                                                       bno.bno_org_name,
                                                                       'bno_code',
                                                                       bno.bno_code))
                                     FROM ntis.ntis_building_ntr_owners AS bno
                                    WHERE bn.bn_id = bno.bno_bn_id)
                               END AS ntr_owner,
                               ad.full_address_text AS bn_full_address,
                               bn.*,
                               so.so_id,
                               so.rec_create_timestamp AS so_created,
                               so.rec_timestamp AS so_updated,
                               CASE
                                 WHEN per.usr_person_name IS NOT NULL THEN
                                  per.usr_person_name || ' ' || per.usr_person_surname
                                 ELSE
                                  'automatinis procesų servisas'
                               END AS so_updated_by,
                               fua.fua_id
                          FROM ntis.ntis_served_objects AS so
                         INNER JOIN ntis.ntis_ntr_building_vw AS bn
                            ON bn.bn_id = so.so_bn_id
                         INNER JOIN ntis.ntis_address_vw AS ad
                            ON ad.address_id = bn.bn_ad_id
                          LEFT JOIN ntis.ntis_building_agreements ba_a
                            ON ba_a.ba_bn_id = so.so_bn_id
                          LEFT JOIN spark.spr_organizations AS org_a
                            ON org_a.org_id = ba_a.ba_org_id
                          LEFT JOIN ntis.ntis_cw_files AS cwf
                            ON cwf.cwf_org_id = ba_a.ba_org_id
                           AND cwf.cwf_status = 'CW_FIL_FINAL'
                          LEFT JOIN spark.spr_users_vw AS per
                                ON per.usr_username = so.rec_userid
                               AND per.rft_lang = ?
                              LEFT JOIN spark.spr_ref_codes_vw AS rfc_aggrx
                                ON rfc_aggrx.rfc_code = coalesce(ba_a.ba_wastewater_treatment,'LOCAL')
                               AND rfc_aggrx.rft_lang = ?
                               AND rfc_aggrx.rfc_domain ='NTIS_TYPE_WASTEWATER_TREATMENT'
                              LEFT JOIN (select fua_id,
                                                fua_bn_id,
                                                fua_so_id,
                                                fua_org_id,
                                                fua_network_connection_date
                                           from ntis.ntis_facility_update_agreement
                                          where fua_so_id = ?::int
                                            and fua_state = 'SUBMITTED'
                                          order by fua_id desc limit 1) AS fua
                                ON fua.fua_bn_id = so.so_bn_id
                               AND fua_so_id = so.so_id
                              LEFT JOIN spark.spr_organizations AS org_m
                                ON org_a.org_id = fua_org_id
                             WHERE so.so_id = ?::int
                             ORDER BY cwf.cwf_import_date DESC
                             LIMIT 1
                            """);

        stmt.addSelectParam(condition);
        stmt.addSelectParam(language);
        stmt.addSelectParam(language);
        stmt.addSelectParam(soId);
        stmt.addSelectParam(soId);

        NtisWastewaterFacilityViewSecurityManager sqm = new NtisWastewaterFacilityViewSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        if (formActions != null) {
            String[] recordActionMenu = { FormBase.ACTION_READ };
            formActions.prepareAvailableMenuAction(recordActionMenu);
            sqm.setFormActions(this.getFormActions(conn));
        }
        return queryController.selectQueryAsJSON(conn, stmt, null, sqm);
    }

    /**
     *
     * Retrieves detailed information about a wastewater treatment facility from the database.
     * @param conn a Connection object representing the database connection
     * @param recordIdentifier a RecordIdentifier object containing the ID of the facility to retrieve information for
     * @return a String representing the detailed information about the facility in JSON format
     * @throws Exception if there is an error executing the SELECT statement or parsing the result set
     */
    public String getTreatmentFacility(Connection conn, Double recordIdentifier, String language, Double orgId, Double perId) throws Exception {

        checkIsFormActionAssigned(conn, NtisWastewaterFacilityView.ACTION_READ);

        Boolean queryByOwner = (!this.hasUserRole(conn, NtisRolesConstants.MUNICI_SPECIALIST) && !this.hasUserRole(conn, NtisRolesConstants.NTIS_ADMIN)
                && !this.hasUserRole(conn, NtisRolesConstants.INST_WORK) && !this.hasUserRole(conn, NtisRolesConstants.INST_ADMIN)
                && !this.hasUserRole(conn, NtisRolesConstants.VAND_ADMIN) && !this.hasUserRole(conn, NtisRolesConstants.CAR_SPECIALIST)
                && !this.hasUserRole(conn, NtisRolesConstants.PASL_ADMIN));

        if (queryByOwner) {
            if (orgId != null) {
                recordIdentifier = ntisFacilityOwnersDBService.loadRecordByParams(conn, " WHERE fo_org_id = ?::int AND fo_wtf_id = ?::int ",
                        new SelectParamValue(orgId), new SelectParamValue(recordIdentifier)).getFo_wtf_id();
            } else {
                recordIdentifier = ntisFacilityOwnersDBService.loadRecordByParams(conn, " WHERE fo_per_id = ?::int AND fo_wtf_id = ?::int ",
                        new SelectParamValue(perId), new SelectParamValue(recordIdentifier)).getFo_wtf_id();
            }
        }

        String objView = "";
        String objViewID = " 1 ";

        if (queryByOwner) {
            objView = """
                    LEFT JOIN ( select fo_so_id
                    from  ntis_facility_owners
                    where fo_so_id is not null and fo_owner_type in ( 'OWNER', 'MANAGER')  and current_date between fo_date_from and COALESCE(fo_date_to, current_date)
                            """
                    + (orgId != null ? " and  fo_org_id = ?::int " : " and  fo_per_id = ?::int ") + //
                    " group by fo_so_id ) fo on so_id = fo_so_id";
            objViewID = " fo_so_id ";

        }

        StatementAndParams stmt = new StatementAndParams();

        stmt.setStatement("""
                SELECT wtf.wtf_id,
                       wtf.wtf_facility_latitude AS wtf_latitude,
                       wtf.wtf_facility_longitude AS wtf_longitude,
                       wtf.wtf_ad_id,
                       addr.full_address_text AS full_address_text,
                       rfc_ty.rfc_meaning AS wtf_title,
                       wtf.wtf_type,
                       rfc_st.rfc_meaning AS wtf_state,
                       rfc_ma.rfc_meaning AS wtf_manufacturer,
                       rfc_mo.rfc_meaning AS wtf_model,
                       wtf.wtf_capacity,
                       wtf.wtf_checkout_date,
                       wtf.wtf_distance,
                       wtf.wtf_installation_date,
                       wtf.wtf_manufacturer_description,
                       coalesce(fm.fam_tech_pass, wtf.wtf_technical_passport_id) as wtf_technical_passport_id,
                       coalesce(fm.fam_pop_equivalent, fm2.fam_pop_equivalent) as fam_pop_equivalent,
                       coalesce(fm.fam_chds, fm2.fam_chds) as fam_chds,
                       coalesce(fm.fam_bds, fm2.fam_bds) as fam_bds,
                       coalesce(fm.fam_float_material, fm2.fam_float_material) as fam_float_material,
                       coalesce(fm.fam_phosphor, fm2.fam_phosphor) as fam_phosphor,
                       coalesce(fm.fam_nitrogen, fm2.fam_nitrogen) as fam_nitrogen,
                       fm2.fam_manufacturer,
                       fm2.fam_model,
                       fm2.fam_description,
                       rfc_so.rfc_meaning AS wtf_data_source,
                       rfc_dw.rfc_meaning AS wtf_discharge_wastewater_type,
                       wtf.wtf_discharge_latitude,
                       wtf.wtf_discharge_longitude,
                       wtf.c02 as wtf_identification_number,
                       coalesce(fuli.ful_created, wtf.rec_create_timestamp) AS wtf_created_at,
                       CASE
                         WHEN orgi.org_name IS NOT NULL THEN
                          usri.usr_person_name || ' ' || usri.usr_person_surname || ' (' ||
                          orgi.org_name || ')'
                         ELSE
                          usri.usr_person_name || ' ' || usri.usr_person_surname
                       END AS wtf_created_by,
                       fulu.ful_created AS wtf_updated_at,
                       CASE
                         WHEN orgu.org_name IS NOT NULL THEN
                          usru.usr_person_name || ' ' || usru.usr_person_surname || ' (' ||
                          orgu.org_name || ')'
                         ELSE
                          usru.usr_person_name || ' ' || usru.usr_person_surname
                       END AS wtf_updated_by,
                       (SELECT JSONB_AGG(JSONB_BUILD_OBJECT('so_id',
                                                            so.so_id,
                                                            'so_ad_id',
                                                            so.so_ad_id,
                                                            'so_address',
                                                            so_addr.full_address_text,
                                                            'so_building_no',
                                                            so_addr.building_no,
                                                            'so_flat_no',
                                                            so_addr.flat_no,
                                                            'so_latitude',
                                                            so.so_coordinate_latitude,
                                                            'so_longitude',
                                                            so.so_coordinate_longitude,
                                                            'so_inv_code',
                                                            ntrs.bn_obj_inv_code,
                                                            'so_purp_name',
                                                            ntrs.bn_pask_name,
                                                            'fo_so_id',
                                                            """ + objViewID
                + """
                                                                    )
                                                          order by so_addr.street , nullif(regexp_replace(so_addr.BUILDING_NO, '[^0-9]','', 'g'), '')::numeric, nullif(regexp_replace(so_addr.FLAT_NO , '[^0-9]','', 'g'), '')::numeric  ) AS wtf_served_objects_json
                        FROM ntis.ntis_served_objects so
                         """
                + objView
                + """
                               INNER JOIN ntis.ntis_building_ntrs ntrs
                                  ON so.so_bn_id = ntrs.bn_id
                                LEFT JOIN ntis.ntis_address_vw so_addr
                                  ON so_addr.address_id =bn_ad_id
                                LEFT JOIN ntis_building_agreements ba
                                  ON ba.ba_bn_id = so.so_bn_id
                               WHERE so.so_wtf_id = wtf.wtf_id and wtf.wtf_state != 'CLOSED' and (ba.ba_wastewater_treatment != 'CENTRALIZED' or ba.ba_wastewater_treatment IS NULL)
                               GROUP BY so.so_wtf_id) AS wtf_served_objects_json,
                             (SELECT JSONB_AGG(JSONB_BUILD_OBJECT('fil_content_type',
                                                                  coalesce(spr_fil1.fil_content_type, spr_fil2.fil_content_type),
                                                                  'fil_key',
                                                                  coalesce(spr_fil1.fil_key, spr_fil2.fil_key),
                                                                  'fil_name',
                                                                  coalesce(spr_fil1.fil_name, spr_fil2.fil_name),
                                                                  'fil_size',
                                                                  coalesce(spr_fil1.fil_size, spr_fil2.fil_size),
                                                                  'fil_status',
                                                                  coalesce(spr_fil1.fil_status, spr_fil2.fil_status),
                                                                  'fil_status_date',
                                                                  coalesce(spr_fil1.fil_status_date, spr_fil2.fil_status_date))) AS wtf_files_json
                                FROM ntis.ntis_wastewater_treatment_faci wtf2
                                LEFT JOIN spark.spr_ref_codes ref_type
                                   ON ref_type.rfc_code = wtf2.wtf_model and ref_type.rfc_code = wtf.wtf_model and current_date between ref_type.rfc_date_from and coalesce(ref_type.rfc_date_to, now())
                                LEFT JOIN ntis.ntis_facility_model AS fac_fil on ref_type.rfc_id = fac_fil.fam_rfc_id

                                LEFT JOIN spark.spr_files AS spr_fil1
                                  ON spr_fil1.fil_id = fac_fil.fam_fil_id
                                LEFT JOIN ntis.ntis_facility_files fac_fil2
                                  ON fac_fil2.ff_wtf_id = wtf2.wtf_id
                                LEFT JOIN spark.spr_files as spr_fil2
                                  ON spr_fil2.fil_id = fac_fil2.ff_fil_id

                               WHERE wtf.wtf_id = wtf2.wtf_id) AS wtf_files_json
                        FROM ntis.ntis_wastewater_treatment_faci wtf
                        LEFT JOIN spark.spr_ref_codes rm on rm.rfc_code = wtf.wtf_model and rm.rfc_domain = 'NTIS_FACIL_MODEL'
                        LEFT JOIN ntis.ntis_facility_model fm on fm.fam_rfc_id = rm.rfc_id
                        LEFT JOIN ntis.ntis_facility_model fm2 on fm2.fam_id = wtf.wtf_fam_id
                        LEFT JOIN ntis.ntis_facility_update_logs AS fuli
                          ON fuli.ful_wtf_id = wtf.wtf_id
                         AND fuli.ful_operation = ?
                        LEFT JOIN spark.spr_users AS usri
                          ON usri.usr_id = fuli.ful_usr_id
                        LEFT JOIN spark.spr_organizations AS orgi
                          ON orgi.org_id = fuli.ful_org_id
                        LEFT JOIN ntis.ntis_facility_update_logs AS fulu
                          ON fulu.ful_wtf_id = wtf.wtf_id
                         AND fulu.ful_operation = ?
                        LEFT JOIN spark.spr_users AS usru
                          ON usru.usr_id = fulu.ful_usr_id
                        LEFT JOIN spark.spr_organizations AS orgu
                          ON orgu.org_id = fulu.ful_org_id
                        LEFT JOIN ntis.ntis_address_vw AS addr
                          ON addr.address_id = wtf.wtf_ad_id
                        LEFT JOIN spark.spr_ref_codes_vw AS rfc_ty
                          ON rfc_ty.rfc_code = wtf.wtf_type
                         AND rfc_ty.rft_lang = ?
                         AND rfc_ty.rfc_domain = ?
                        LEFT JOIN spark.spr_ref_codes_vw AS rfc_st
                          ON rfc_st.rfc_code = wtf.wtf_state
                         AND rfc_st.rft_lang = ?
                         AND rfc_st.rfc_domain = ?
                        LEFT JOIN spark.spr_ref_codes_vw AS rfc_ma
                          ON rfc_ma.rfc_code = wtf.wtf_manufacturer
                         AND rfc_ma.rft_lang = ?
                         AND rfc_ma.rfc_domain = ?
                        LEFT JOIN spark.spr_ref_codes_vw AS rfc_mo
                          ON rfc_mo.rfc_code = wtf.wtf_model
                         AND rfc_mo.rft_lang = ?
                         AND rfc_mo.rfc_domain = ?
                        LEFT JOIN spark.spr_ref_codes_vw AS rfc_dw
                          ON rfc_dw.rfc_code = wtf.wtf_discharge_type
                         AND rfc_dw.rft_lang = ?
                         AND rfc_dw.rfc_domain = ?
                        LEFT JOIN spark.spr_ref_codes_vw AS rfc_so
                          ON rfc_so.rfc_code = wtf.wtf_data_source
                         AND rfc_so.rft_lang = ?
                         AND rfc_so.rfc_domain = ?
                         where  wtf.wtf_id = ?::int
                         """);
        stmt.setWhereExists(true);
        if (queryByOwner) {
            if (orgId != null) {
                stmt.addSelectParam(orgId);
            } else {
                stmt.addSelectParam(perId);
            }
        }

        stmt.addSelectParam(NtisQueryOperations.INSERT.getCode());
        stmt.addSelectParam(NtisQueryOperations.UPDATE.getCode());
        stmt.addSelectParam(language);
        stmt.addSelectParam(NtisClassifierDomains.NTIS_WTF_TYPE.getCode());
        stmt.addSelectParam(language);
        stmt.addSelectParam(NtisClassifierDomains.NTIS_INTS_STATUS.getCode());
        stmt.addSelectParam(language);
        stmt.addSelectParam(NtisClassifierDomains.NTIS_FACIL_MANUFA.getCode());
        stmt.addSelectParam(language);
        stmt.addSelectParam(NtisClassifierDomains.NTIS_FACIL_MODEL.getCode());
        stmt.addSelectParam(language);
        stmt.addSelectParam(NtisClassifierDomains.DISCHARGE_WASTEWATER_TYPE.getCode());
        stmt.addSelectParam(language);
        stmt.addSelectParam(NtisClassifierDomains.NTIS_FACIL_DATA_SOURCE.getCode());
        stmt.addSelectParam(recordIdentifier);
        if (!queryByOwner && this.hasUserRole(conn, NtisRolesConstants.MUNICI_SPECIALIST)) {
            stmt.addParam4WherePart(" wtf.wtf_facility_municipality_code::int = ?::int ", sprOrganizationsDBService.loadRecord(conn, orgId).getN01());
        }
        stmt.setStatementOrderPart("ORDER BY fulu.rec_create_timestamp DESC LIMIT 1");

        NtisWastewaterFacilityViewSecurityManager sqm = new NtisWastewaterFacilityViewSecurityManager();

        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { NtisWastewaterFacilityView.ACTION_READ };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(this.getFormActions(conn));

        return queryController.selectQueryAsJSON(conn, stmt, null, sqm);
    }

    public List<NtrOwnerModel> loadNTROwners(Connection conn, String bnRegNr) throws Exception {
        this.isFormActionAssigned(conn, NtisCommonActionsConstants.NTIS_ADMIN_ACTIONS);
        return ntr.retrieveNtrOwnersData(bnRegNr);
    }
}