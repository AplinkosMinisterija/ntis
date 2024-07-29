package lt.project.ntis.logic.forms;

import java.io.InputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.constants.DbConstants;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.IdKeyValuePair;
import eu.itreegroup.spark.modules.admin.service.SprPersonsDBService;
import eu.itreegroup.spark.modules.admin.service.SprRefCodesDBService;
import eu.itreegroup.spark.modules.admin.service.SprUsersDBService;
import eu.itreegroup.spark.modules.common.logic.forms.SprNotifications;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.constants.FacilityUpdateAgreementConstants;
import lt.project.ntis.constants.NtisRolesConstants;
import lt.project.ntis.dao.NtisBuildingAgreementsDAO;
import lt.project.ntis.dao.NtisFacilityModelDAO;
import lt.project.ntis.dao.NtisFacilityUpdateAgreementDAO;
import lt.project.ntis.dao.NtisServedObjectsDAO;
import lt.project.ntis.dao.NtisWastewaterTreatmentFaciDAO;
import lt.project.ntis.enums.NtisAggreementSource;
import lt.project.ntis.enums.NtisBuildingAgreementStatus;
import lt.project.ntis.enums.NtisBuildingType;
import lt.project.ntis.enums.NtisClassifierDomains;
import lt.project.ntis.enums.NtisFacilityStatus;
import lt.project.ntis.enums.NtisNtfRefType;
import lt.project.ntis.enums.NtisQueryOperations;
import lt.project.ntis.enums.NtisTypeWastewaterTreatment;
import lt.project.ntis.logic.forms.processRequests.FaciUpdateAgreementProcessRequest;
import lt.project.ntis.logic.forms.security.NtisCarsBrowseSecurityManager;
import lt.project.ntis.logic.forms.security.NtisWastewaterFacilityViewSecurityManager;
import lt.project.ntis.service.NtisBuildingAgreementsDBService;
import lt.project.ntis.service.NtisFacilityModelDBService;
import lt.project.ntis.service.NtisFacilityOwnersDBService;
import lt.project.ntis.service.NtisFacilityUpdateAgreementDBService;
import lt.project.ntis.service.NtisServedObjectsDBService;
import lt.project.ntis.service.NtisServedObjectsVersionDBService;
import lt.project.ntis.service.NtisWastewaterTreatmentFaciDBService;

/**
 * Manage building update agreement info
 */

@Component
public class NtisWfAgreementsLists extends FormBase {

    public static final String CANCEL = "CANCEL";

    public static final String CONFIRM = "CONFIRM";

    public static final String REJECT = "REJECT";

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbstatementManager;

    @Autowired
    NtisWastewaterTreatmentFaciDBService ntisWastewaterTreatmentFaciDBService;

    @Autowired
    NtisFacilityUpdateAgreementDBService ntisFacilityUpdateAgreementDBService;

    @Autowired
    NtisWastewaterFacilityView ntisWastewaterFacilityView;

    @Autowired
    NtisServedObjectsDBService ntisServedObjectsDBService;

    @Autowired
    NtisFacilityOwnersDBService ntisFacilityOwnersDBService;

    @Autowired
    NtisWastewaterFacilityEdit ntisWastewaterFacilityEdit;

    @Autowired
    NtisServedObjectsVersionDBService ntisServedObjectsVersionDBService;

    @Autowired
    NtisBuildingAgreementsDBService ntisBuildingAgreementsDBService;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    SprFilesDBService sprFilesDBService;

    @Autowired
    SprUsersDBService sprUsersDBService;

    @Autowired
    SprPersonsDBService sprPersonsDBService;

    @Autowired
    SprRefCodesDBService sprRefCodesDBService;

    @Autowired
    NtisNotificationsManager ntisNotifications;

    @Autowired
    FaciUpdateAgreementProcessRequest faciUpdateAgreementProcessRequest;

    @Autowired
    NtisFacilityModelDBService ntisFacilityModelDBService;

    @Override
    public String getFormName() {
        return "NTIS_AGREEMENTS_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Prašymai", "Prašymai");
        addFormActionCRUD();
        addFormAction(NtisWfAgreementsLists.CANCEL, NtisWfAgreementsLists.CANCEL, NtisWfAgreementsLists.CANCEL);
        addFormAction(NtisWfAgreementsLists.CONFIRM, NtisWfAgreementsLists.CONFIRM, NtisWfAgreementsLists.CONFIRM);
        addFormAction(NtisWfAgreementsLists.REJECT, NtisWfAgreementsLists.REJECT, NtisWfAgreementsLists.REJECT);

    }

    /**
    * Retrieves a list based on the specified parameters.
    *
    * @param conn   The database connection.
    * @param params The select request parameters.
    * @param lang   The language to use for localization.
    * @param orgId  The organization ID.
    * @param usrId  The user ID.
    * @return A string representing the retrieved list.
    * @throws Exception If an error occurs during the retrieval process.
    */
    public String getList(Connection conn, SelectRequestParams params, String lang, Double perId, Double orgId, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(
                """
                         select   fua_id,
                                  fua_created :: date,
                                  per_name ||' '||per_surname person,
                                  coalesce(
                                      coalesce(aa.full_address_text,ad.full_address_text),
                                      coalesce(
                                          coalesce(aa.latitude ||', '|| aa.longitude, ad.latitude ||', '|| ad.longitude),
                                          coalesce(wtf.wtf_facility_latitude ||', '|| wtf.wtf_facility_longitude, wtf.wtf_nc_facility_latitude ||', '|| wtf.wtf_nc_facility_longitude)
                                      )
                                      )as full_address_text,
                                  fua_manufecturer,
                                  fua_model,
                                  fua_type,
                                  ft.rfc_meaning fua_type_text,
                                  fua_state,
                                  rc.rfc_meaning fua_state_text,
                                  fua_per_id,
                                  fua_wtf_id,
                                  fua_so_id
                        from ntis_facility_update_agreement fua
                             left join ntis_wastewater_treatment_faci wtf on fua_wtf_id = wtf_id
                             left join ntis.ntis_address_vw aa on address_id = wtf_ad_id
                             left join spr_persons per on fua_per_id = per_id
                             left JOIN ntis.ntis_ntr_building_vw AS bn ON bn.bn_id = fua_bn_id
                             left JOIN ntis.ntis_address_vw AS ad  ON ad.address_id = bn.bn_ad_id
                             left join SPR_REF_CODES_VW rc on rc.rfc_code = fua_state and  rc.rfc_domain = 'NTIS_WF_AGRREMENT_STATE' and rc.rft_lang = ?
                             left join SPR_REF_CODES_VW ft on ft.rfc_code = fua_type and  ft.rfc_domain = 'NTIS_BUILDING_TYPE' and ft.rft_lang = ?
                        """);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(lang);
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), usrId, stmt, advancedParamList);

        if (!hasUserRole(conn, NtisRolesConstants.NTIS_ADMIN)) {
            if ((hasUserRole(conn, NtisRolesConstants.INTS_OWNER))) {
                if (orgId == null) {
                    stmt.addParam4WherePart("fua_per_id = ?::int", perId);
                } else {
                    stmt.addParam4WherePart("fua_req_org_id = ?::int", orgId);
                }

            } else {
                if ((hasUserRole(conn, NtisRolesConstants.INST_WORK) || hasUserRole(conn, NtisRolesConstants.INST_ADMIN)
                        || hasUserRole(conn, NtisRolesConstants.MUNICI_SPECIALIST))) {

                    stmt.addParam4WherePart("""
                             exists (
                              select 1
                                from spr_organizations org
                                 where org_id = ?::int
                                   and  ((n02=wtf.wtf_facility_municipality_code::numeric) or  c02 = 'INST_LT'))
                            """, orgId);

                } else {

                    if (orgId == null) {
                        stmt.addParam4WherePart("fua_per_id = ?::int", perId);
                    } else {
                        stmt.addParam4WherePart("fua_req_org_id = ?::int", orgId);
                    }
                }

            }
        }

        stmt.addParam4WherePart("fua_state", StatementAndParams.PARAM_STRING, advancedParamList.get("fua_state_text"));
        stmt.addParam4WherePart("fua_id", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("fua_id"));
        stmt.addParam4WherePart("per_name ||' '||per_surname", StatementAndParams.PARAM_STRING, advancedParamList.get("person"));
        stmt.addParam4WherePart("fua_created", StatementAndParams.PARAM_DATE, advancedParamList.get("fua_created"),
                dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart("fua_type", StatementAndParams.PARAM_STRING, advancedParamList.get("fua_type_text"));
        stmt.addParam4WherePart("  coalesce(aa.full_address_text,ad.full_address_text)", StatementAndParams.PARAM_STRING,
                advancedParamList.get("full_address_text"));

        stmt.addParam4WherePart(
                dbstatementManager.colNamesToConcatString("fua_id", "per_name ||' '||per_surname", "coalesce(rc.rfc_meaning, fua_state)",
                        "coalesce(ft.rfc_meaning, fua_type)", "  coalesce(aa.full_address_text,ad.full_address_text)"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        stmt.setStatementOrderPart(" order by fua_id desc");
        NtisCarsBrowseSecurityManager sqm = new NtisCarsBrowseSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { FormBase.ACTION_READ };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    /**
    Retrieves the confirmation information.
    @param conn The database connection.
    @param recordIdentifier The identifier of the record associated with the workflow.
    @param language The language to use for retrieving the information.
    @param perId The identifier of the person associated with the workflow.
    @param orgId The identifier of the organization associated with the workflow.
    @param usrId The identifier of the user associated with the workflow.
    @return The confirmation information as a string.
    @throws Exception If an error occurs while retrieving the information.
    */
    public String getWfConfirmationInfo(Connection conn, Double recordIdentifier, String language, Double perId, Double orgId) throws Exception {
        checkIsFormActionAssigned(conn, NtisWastewaterFacilityView.ACTION_READ);

        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(
                """
                        SELECT
                               fua_id,
                               fua_created :: date,
                               per_name ||' '||per_surname person,
                               per_email,
                               per_phone_number,
                               fua_manufecturer,
                               fua_model,
                               fua_type,
                               fua_state,
                               Fua_cancellation_reason,
                               fua_wtf_old_info_json,
                               fua_wtf_new_info_json,
                               fua_wtf_object_info_json,
                               rc.rfc_meaning fua_state_text,
                               fua_per_id,
                               fua_wtf_id,
                               wtf.wtf_nc_facility_latitude AS wtf_latitude,
                               wtf.wtf_nc_facility_longitude AS wtf_longitude,
                               wtf.wtf_ad_id as wtf_ad_id, --// Papildyto
                               addr.full_address_text AS full_address_text,
                               rfc_ty.rfc_meaning AS wtf_title,
                               wtf.wtf_type as wtf_type , --// Papildyto
                               rfc_st.rfc_meaning AS wtf_state,
                               wtf_state as wtf_state_code,
                               rfc_ma.rfc_meaning AS wtf_manufacturer,
                               rfc_mo.rfc_meaning AS wtf_model,
                               wtf.wtf_nc_capacity as wtf_capacity,
                               wtf.wtf_nc_checkout_date as wtf_checkout_date,
                               wtf.wtf_nc_distance as wtf_distance,
                               wtf.wtf_nc_installation_date as wtf_installation_date,
                               wtf.wtf_nc_manufacturer_description as wtf_manufacturer_description,
                               wtf.wtf_nc_technical_passport_id wtf_technical_passport_id,
                               rfc_so.rfc_meaning AS wtf_data_source,
                               rfc_dw.rfc_meaning AS wtf_discharge_wastewater_type,
                               wtf.wtf_nc_discharge_latitude as wtf_discharge_latitude,
                               wtf.wtf_nc_discharge_longitude as wtf_discharge_longitude,
                               wtf.c03 as wtf_identification_number,
                               wtf.wtf_nc_fam_pop_equivalent as fam_pop_equivalent,
                               wtf.wtf_nc_fam_tech_pass as fam_tech_pass,
                               wtf.wtf_nc_fam_chds as fam_chds,
                               wtf.wtf_nc_fam_bds as fam_bds,
                               wtf.wtf_nc_fam_float_material as fam_float_material,
                               wtf.wtf_nc_fam_phosphor as fam_phosphor,
                               wtf.wtf_nc_fam_nitrogen as fam_nitrogen,
                               wtf.wtf_nc_fam_manufacturer as fam_manufacturer,
                               wtf.wtf_nc_fam_model as fam_model,
                               wtf.wtf_nc_fam_description as fam_description,
                               fuli.ful_created AS wtf_created_at,
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
                                                                     sov.sov_id,
                                                                    'so_ad_id',
                                                                    sov.sov_ad_id,
                                                                    'so_address',
                                                                    so_addr.full_address_text,
                                                                    'so_latitude',
                                                                    sov.sov_coordinate_latitude,
                                                                    'so_longitude',
                                                                    sov.sov_coordinate_longitude,
                                                                    'so_inv_code',
                                                                    ntrs.bn_obj_inv_code,
                                                                    'so_purp_name',
                                                                    ntrs.bn_pask_name)
                        		order by so_addr.street , nullif(regexp_replace(so_addr.BUILDING_NO, '[^0-9]','', 'g'), '')::numeric, nullif(regexp_replace(so_addr.FLAT_NO , '[^0-9]','', 'g'), '')::numeric  ) AS wtf_served_objects_json
                                  FROM ntis.ntis_served_objects_version sov
                                 INNER JOIN ntis.ntis_building_ntrs ntrs
                                    ON sov.sov_bn_id = ntrs.bn_id
                                  LEFT JOIN ntis.ntis_address_vw so_addr
                                    ON so_addr.address_id = sov.sov_ad_id
                                 WHERE sov.sov_wtf_id = wtf.wtf_id
                                 and   sov.sov_fua_id = fua_id
                                 GROUP BY sov.sov_wtf_id) AS wtf_served_objects_json,
                               (SELECT JSONB_AGG(JSONB_BUILD_OBJECT('fil_content_type',
                                                                    spr_fil.fil_content_type,
                                                                    'fil_key',
                                                                    spr_fil.fil_key,
                                                                    'fil_name',
                                                                    spr_fil.fil_name,
                                                                    'fil_size',
                                                                    spr_fil.fil_size,
                                                                    'fil_status',
                                                                    spr_fil.fil_status,
                                                                    'fil_status_date',
                                                                    spr_fil.fil_status_date)) AS wtf_files_json
                                  FROM ntis.ntis_facility_files AS fac_fil
                                  LEFT JOIN spark.spr_files AS spr_fil
                                    ON spr_fil.fil_id = fac_fil.ff_fil_id
                                 WHERE fac_fil.ff_wtf_id = wtf.wtf_id) AS wtf_files_json
                          FROM ntis.ntis_wastewater_treatment_faci wtf
                          JOIN ntis_facility_update_agreement on fua_wtf_id = wtf_id
                          LEFT JOIN SPR_REF_CODES_VW rc on rfc_code = fua_state and  rfc_domain = 'NTIS_WF_AGRREMENT_STATE' and rc.rft_lang = ?
                          JOIN spr_persons per on fua_per_id = per_id
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
                            ON rfc_ty.rfc_code = wtf.wtf_nc_type
                           AND rfc_ty.rft_lang = ?
                           AND rfc_ty.rfc_domain = ?
                          LEFT JOIN spark.spr_ref_codes_vw AS rfc_st
                            ON rfc_st.rfc_code = wtf.wtf_state
                           AND rfc_st.rft_lang = ?
                           AND rfc_st.rfc_domain = ?
                          LEFT JOIN spark.spr_ref_codes_vw AS rfc_ma
                            ON rfc_ma.rfc_code = wtf.wtf_nc_manufacturer
                           AND rfc_ma.rft_lang = ?
                           AND rfc_ma.rfc_domain = ?
                          LEFT JOIN spark.spr_ref_codes_vw AS rfc_mo
                            ON rfc_mo.rfc_code = wtf.wtf_nc_model
                           AND rfc_mo.rft_lang = ?
                           AND rfc_mo.rfc_domain = ?
                          LEFT JOIN spark.spr_ref_codes_vw AS rfc_dw
                            ON rfc_dw.rfc_code = wtf.wtf_nc_discharge_type
                           AND rfc_dw.rft_lang = ?
                           AND rfc_dw.rfc_domain = ?
                          LEFT JOIN spark.spr_ref_codes_vw AS rfc_so
                            ON rfc_so.rfc_code = wtf.wtf_nc_data_source
                           AND rfc_so.rft_lang = ?
                           AND rfc_so.rfc_domain = ?
                           where  fua_id = ?::int
                        """);
        stmt.isWhereExists();
        stmt.addSelectParam(language);
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
        if (!this.hasUserRole(conn, NtisRolesConstants.MUNICI_SPECIALIST) && !this.hasUserRole(conn, NtisRolesConstants.NTIS_ADMIN)
                && !this.hasUserRole(conn, NtisRolesConstants.INST_WORK) && !this.hasUserRole(conn, NtisRolesConstants.INST_ADMIN)
                && !this.hasUserRole(conn, NtisRolesConstants.VAND_ADMIN) && !this.hasUserRole(conn, NtisRolesConstants.CAR_SPECIALIST)
                && !this.hasUserRole(conn, NtisRolesConstants.PASL_ADMIN)) {
            checkIsUserWtfOwner(conn, recordIdentifier, orgId, perId);
        }
        stmt.setStatementOrderPart("ORDER BY fulu.rec_create_timestamp DESC LIMIT 1");

        NtisWastewaterFacilityViewSecurityManager sqm = new NtisWastewaterFacilityViewSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { FormBase.ACTION_READ };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(this.getFormActions(conn));

        return queryController.selectQueryAsJSON(conn, stmt, null, sqm);
    }

    /**
    Retrieves the confirmation information.
    @param conn The database connection.
    @param recordIdentifier The identifier of the record associated with the workflow.
    @param language The language to use for retrieving the information.
    @param perId The identifier of the person associated with the workflow.
    @param orgId The identifier of the organization associated with the workflow.
    @param usrId The identifier of the user associated with the workflow.
    @return The confirmation information as a string.
    @throws Exception If an error occurs while retrieving the information.
    */
    public String getWfObjConfirmationInfo(Connection conn, Double recordIdentifier, String language, Double perId, Double orgId) throws Exception {
        checkIsFormActionAssigned(conn, NtisWastewaterFacilityView.ACTION_READ);

        StatementAndParams stmt = new StatementAndParams();

        stmt.setStatement(
                """
                          select  fua_id,
                                  fua_created :: date,
                                  per_name ||' '||per_surname||coalesce(', '||req_org.org_name,' ')||coalesce(', '||per_email, '')|| coalesce(', '||per_phone_number, '')  person,
                                  full_address_text,
                                  fua_manufecturer,
                                  fua_model,
                                  fua_type,
                                  fua_state,
                                  rfc_meaning fua_state_text,
                                  fua_per_id,
                                  fua_so_id,
                                  org.org_name,
                                  fua_cancellation_reason,
                                  fua_network_connection_date :: date,
                                  JSONB_BUILD_OBJECT('fil_content_type',
                                                                    spr_fil.fil_content_type,
                                                                    'fil_key',
                                                                     fil_key,
                                                                    'fil_name',
                                                                    spr_fil.fil_name,
                                                                    'fil_size',
                                                                    spr_fil.fil_size,
                                                                    'fil_status',
                                                                    spr_fil.fil_status,
                                                                    'fil_status_date',
                                                                    spr_fil.fil_status_date) file_ot
                        from ntis_facility_update_agreement fua
                             INNER JOIN ntis.ntis_ntr_building_vw AS bn
                                ON bn.bn_id = fua_bn_id
                             INNER JOIN ntis.ntis_address_vw AS ad
                                ON ad.address_id = bn.bn_ad_id
                             left join spr_persons per on fua_per_id = per_id
                             left join spr_organizations org on fua_org_id = org.org_id
                             left join spr_organizations req_org on fua_req_org_id = req_org.org_id
                             left join spark.spr_files AS spr_fil on  fua_fil_id = spr_fil.fil_id
                             left join SPR_REF_CODES_VW rc on rfc_code = fua_state and  rfc_domain = 'NTIS_WF_AGRREMENT_STATE' and rc.rfc_domain = ?
                        where fua_id = ?::int
                        """);
        stmt.setWhereExists(true);
        stmt.addSelectParam(language);
        stmt.addSelectParam(recordIdentifier);
        if (!this.hasUserRole(conn, NtisRolesConstants.MUNICI_SPECIALIST) && !this.hasUserRole(conn, NtisRolesConstants.NTIS_ADMIN)
                && !this.hasUserRole(conn, NtisRolesConstants.INST_WORK) && !this.hasUserRole(conn, NtisRolesConstants.INST_ADMIN)
                && !this.hasUserRole(conn, NtisRolesConstants.VAND_ADMIN) && !this.hasUserRole(conn, NtisRolesConstants.CAR_SPECIALIST)
                && !this.hasUserRole(conn, NtisRolesConstants.PASL_ADMIN)) {
            if (orgId == null) {
                stmt.addParam4WherePart("fua_per_id = ?::int", perId);
            } else {
                stmt.addParam4WherePart("fua_req_org_id = ?::int", orgId);
            }
        }
        stmt.setStatementOrderPart("ORDER BY fua_id DESC LIMIT 1");

        NtisWastewaterFacilityViewSecurityManager sqm = new NtisWastewaterFacilityViewSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { NtisWastewaterFacilityView.ACTION_READ };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(this.getFormActions(conn));

        return queryController.selectQueryAsJSON(conn, stmt, null, sqm);
    }

    /**
     * Cancel the object agreement  
     *
     * @param conn   the database connection
     * @param fauId  the ID of the agreement
     * @param perId  the ID of the person
     * @param orgId  the ID of the organization
     * @param usrId  the ID of the user
     * @throws Exception if an error occurs during the confirmation process
     */
    public void cancelObjAgreement(Connection conn, IdKeyValuePair result, Double perId, Double orgId, Double usrId) throws Exception {
        checkIsFormActionAssigned(conn, NtisWfAgreementsLists.CANCEL);

        NtisFacilityUpdateAgreementDAO updateAgreement;
        if (orgId != null) {
            updateAgreement = ntisFacilityUpdateAgreementDBService.loadRecordByParams(conn, " WHERE fua_id = ?::int AND fua_req_org_id = ?::int",
                    new SelectParamValue(Utils.getLong(result.getId())), new SelectParamValue(orgId));
        } else {
            updateAgreement = ntisFacilityUpdateAgreementDBService.loadRecordByParams(conn, " WHERE fua_id = ?::int AND fua_per_id = ?::int",
                    new SelectParamValue(Utils.getLong(result.getId())), new SelectParamValue(perId));
        }
        if (updateAgreement.getFua_id() != null) {
            updateAgreement.setFua_state(FacilityUpdateAgreementConstants.CANCELED);
            updateAgreement.setFua_cancellation_reason(result.getValue());
            updateAgreement.setFua_confirmed_usr_id(usrId);
            ntisFacilityUpdateAgreementDBService.saveRecord(conn, updateAgreement);
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }

    }

    /**
     * reject the object agreement  
     *
     * @param conn   the database connection
     * @param fauId  the ID of the agreement
     * @param perId  the ID of the person
     * @param orgId  the ID of the organization
     * @param usrId  the ID of the user
     * @throws Exception if an error occurs during the confirmation process
     */
    public void rejectObjAgreement(Connection conn, IdKeyValuePair result, Double perId, Double orgId, Double usrId, String lang) throws Exception {
        checkIsFormActionAssigned(conn, NtisWfAgreementsLists.REJECT);

        NtisFacilityUpdateAgreementDAO updateAgreement = ntisFacilityUpdateAgreementDBService.loadRecordByParams(conn, " WHERE fua_id = ?::int ",
                new SelectParamValue(Utils.getLong(result.getId())));
        if (updateAgreement.getFua_id() != null) {
            updateAgreement.setFua_state(FacilityUpdateAgreementConstants.REJECTED);
            updateAgreement.setFua_confirmed_usr_id(usrId);
            updateAgreement.setFua_cancellation_reason(result.getValue());
            ntisFacilityUpdateAgreementDBService.saveRecord(conn, updateAgreement);
            sendNotification(conn, Utils.getDouble(result.getId()), usrId, orgId, perId, lang, updateAgreement);
        }
    }

    /**
     * reject the object agreement  
     *
     * @param conn   the database connection
     * @param fauId  the ID of the agreement
     * @param perId  the ID of the person
     * @param orgId  the ID of the organization
     * @param usrId  the ID of the user
     * @return 
     * @throws Exception if an error occurs during the confirmation process
     */
    public InputStream agreementFile(Connection conn, Double fuaId, Double perId, Double orgId) throws Exception {
        checkIsFormActionAssigned(conn, NtisWastewaterFacilityView.ACTION_READ);

        NtisFacilityUpdateAgreementDAO updateAgreement;
        if (hasUserRole(conn, NtisRolesConstants.NTIS_ADMIN) || hasUserRole(conn, NtisRolesConstants.INST_ADMIN)
                || hasUserRole(conn, NtisRolesConstants.INST_WORK) || hasUserRole(conn, NtisRolesConstants.MUNICI_SPECIALIST)
                || hasUserRole(conn, NtisRolesConstants.VAND_ADMIN) || hasUserRole(conn, NtisRolesConstants.CAR_SPECIALIST)
                || hasUserRole(conn, NtisRolesConstants.PASL_ADMIN)) {
            updateAgreement = ntisFacilityUpdateAgreementDBService.loadRecord(conn, fuaId);

        } else {
            if (orgId == null) {
                updateAgreement = ntisFacilityUpdateAgreementDBService.loadRecordByParams(conn, " WHERE fua_id = ?::int and fua_per_id = ?::int",
                        new SelectParamValue(fuaId), new SelectParamValue(perId));
            } else {
                updateAgreement = ntisFacilityUpdateAgreementDBService.loadRecordByParams(conn, " WHERE fua_id = ?::int and fua_req_org_id = ?::int",
                        new SelectParamValue(fuaId), new SelectParamValue(orgId));
            }
        }

        if (updateAgreement.getFua_fil_id() != null) {
            return fileStorageService.getFileByFileKey(sprFilesDBService.loadRecord(conn, updateAgreement.getFua_fil_id()).getFil_key(), conn);
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }

    }

    /**
     * Cancel the agreement .
     *
     * @param conn   the database connection
     * @param fauId  the ID of the agreement
     * @param perId  the ID of the person
     * @param orgId  the ID of the organization
     * @param usrId  the ID of the user
     * @throws Exception if an error occurs during the confirmation process
     */
    public void cancelAgreement(Connection conn, IdKeyValuePair result, Double perId, Double orgId, Double usrId) throws Exception {
        checkIsFormActionAssigned(conn, NtisWfAgreementsLists.CANCEL);

        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT w.*
                from ntis_facility_update_agreement fua
                     join ntis_wastewater_treatment_faci w on fua_wtf_id = wtf_id
                 AND fua_id = ?::int
                 """);
        stmt.addSelectParam(Utils.getDouble(result.getId()));
        if (orgId == null) {
            stmt.addParam4WherePart("fua_per_id = ?::int", perId);
        } else {
            stmt.addParam4WherePart("fua_req_org_id = ?::int", orgId);
        }

        List<NtisWastewaterTreatmentFaciDAO> queryResult = queryController.selectQueryAsObjectArrayList(conn, stmt, NtisWastewaterTreatmentFaciDAO.class);
        if (!queryResult.isEmpty()) {

            NtisWastewaterTreatmentFaciDAO facilityForm = ntisWastewaterTreatmentFaciDBService.loadRecord(conn,
                    Utils.getDouble(queryResult.get(0).getWtf_id()));
            facilityForm.setWtf_waiting_update_confirmation("N");
            var updateAgreement = ntisFacilityUpdateAgreementDBService.loadRecord(conn, Utils.getDouble(result.getId()));
            updateAgreement.setFua_state(FacilityUpdateAgreementConstants.CANCELED);
            updateAgreement.setFua_cancellation_reason(result.getValue());
            updateAgreement.setFua_wtf_new_info_json(getWfConfirmationInfo(conn, Utils.getDouble(result.getId()), "lt", perId, orgId));
            updateAgreement.setFua_confirmed_usr_id(usrId);
            ntisFacilityUpdateAgreementDBService.saveRecord(conn, updateAgreement);
            facilityForm.setWtf_nc_type(null);
            facilityForm.setWtf_nc_capacity(null);
            facilityForm.setWtf_nc_installation_date(null);
            facilityForm.setWtf_nc_checkout_date(null);
            facilityForm.setWtf_nc_manufacturer(null);
            facilityForm.setWtf_nc_manufacturer_description(null);
            facilityForm.setWtf_nc_distance(null);
            facilityForm.setWtf_nc_model(null);
            facilityForm.setWtf_nc_deleted(null);
            facilityForm.setWtf_nc_data_source(null);
            facilityForm.setWtf_nc_technical_passport_id(null);
            facilityForm.setWtf_nc_facility_latitude(null);
            facilityForm.setWtf_nc_facility_longitude(null);
            facilityForm.setWtf_nc_discharge_latitude(null);
            facilityForm.setWtf_nc_discharge_longitude(null);
            facilityForm.setWtf_nc_discharge_type(null);
            facilityForm.setWtf_nc_ad_id(null);
            facilityForm.setWtf_nc_facility_municipality_code(null);
            facilityForm.setWtf_nc_fam_manufacturer(null);
            facilityForm.setWtf_nc_fam_description(null);
            facilityForm.setWtf_nc_fam_model(null);
            facilityForm.setWtf_nc_fam_tech_pass(null);
            facilityForm.setWtf_nc_fam_pop_equivalent(null);
            facilityForm.setWtf_nc_fam_chds(null);
            facilityForm.setWtf_nc_fam_bds(null);
            facilityForm.setWtf_nc_fam_chds(null);
            facilityForm.setWtf_nc_fam_float_material(null);
            facilityForm.setWtf_nc_fam_phosphor(null);
            facilityForm.setWtf_nc_fam_nitrogen(null);
            facilityForm.setC03(null);
            ntisWastewaterTreatmentFaciDBService.saveRecord(conn, facilityForm);

        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
    }

    /**
     * Reject the agreement .
     *
     * @param conn   the database connection
     * @param fauId  the ID of the agreement
     * @param perId  the ID of the person
     * @param orgId  the ID of the organization
     * @param usrId  the ID of the user
     * @throws Exception if an error occurs during the confirmation process
     */
    public void rejectAgreement(Connection conn, IdKeyValuePair result, Double perId, Double orgId, Double usrId, String lang) throws Exception {
        checkIsFormActionAssigned(conn, NtisWfAgreementsLists.REJECT);

        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT w.*
                from ntis_facility_update_agreement fua
                     join ntis_wastewater_treatment_faci w on fua_wtf_id = wtf_id
                 AND fua_id = ?::int
                 """);
        stmt.addSelectParam(Utils.getDouble(result.getId()));
        if (!hasUserRole(conn, NtisRolesConstants.NTIS_ADMIN) && (hasUserRole(conn, NtisRolesConstants.INST_WORK)
                || hasUserRole(conn, NtisRolesConstants.INST_ADMIN) || hasUserRole(conn, NtisRolesConstants.MUNICI_SPECIALIST))) {
            stmt.addParam4WherePart("""
                     exists (
                      select 1
                        from spr_organizations org
                         where org_id = ?::int
                           and  ((n02=wtf_facility_municipality_code::numeric  ) or  c02 = 'INST_LT'))
                    """, orgId);

        }

        List<NtisWastewaterTreatmentFaciDAO> queryResult = queryController.selectQueryAsObjectArrayList(conn, stmt, NtisWastewaterTreatmentFaciDAO.class);
        if (!queryResult.isEmpty()) {

            NtisWastewaterTreatmentFaciDAO facilityForm = ntisWastewaterTreatmentFaciDBService.loadRecord(conn,
                    Utils.getDouble(queryResult.get(0).getWtf_id()));
            facilityForm.setWtf_waiting_update_confirmation("N");
            var updateAgreement = ntisFacilityUpdateAgreementDBService.loadRecord(conn, Utils.getDouble(result.getId()));
            updateAgreement.setFua_state(FacilityUpdateAgreementConstants.REJECTED);
            updateAgreement.setFua_cancellation_reason(result.getValue());
            updateAgreement.setFua_confirmed_usr_id(usrId);
            updateAgreement.setFua_wtf_new_info_json(getWfConfirmationInfo(conn, Utils.getDouble(result.getId()), "lt", perId, orgId));
            ntisFacilityUpdateAgreementDBService.saveRecord(conn, updateAgreement);
            facilityForm.setWtf_nc_type(null);
            facilityForm.setWtf_nc_capacity(null);
            facilityForm.setWtf_nc_installation_date(null);
            facilityForm.setWtf_nc_checkout_date(null);
            facilityForm.setWtf_nc_manufacturer(null);
            facilityForm.setWtf_nc_manufacturer_description(null);
            facilityForm.setWtf_nc_distance(null);
            facilityForm.setWtf_nc_model(null);
            facilityForm.setWtf_nc_deleted(null);
            facilityForm.setWtf_nc_data_source(null);
            facilityForm.setWtf_nc_technical_passport_id(null);
            facilityForm.setWtf_nc_facility_latitude(null);
            facilityForm.setWtf_nc_facility_longitude(null);
            facilityForm.setWtf_nc_discharge_latitude(null);
            facilityForm.setWtf_nc_discharge_longitude(null);
            facilityForm.setWtf_nc_discharge_type(null);
            facilityForm.setWtf_nc_ad_id(null);
            facilityForm.setWtf_nc_fam_manufacturer(null);
            facilityForm.setWtf_nc_fam_description(null);
            facilityForm.setWtf_nc_fam_model(null);
            facilityForm.setWtf_nc_fam_tech_pass(null);
            facilityForm.setWtf_nc_fam_pop_equivalent(null);
            facilityForm.setWtf_nc_fam_chds(null);
            facilityForm.setWtf_nc_fam_bds(null);
            facilityForm.setWtf_nc_fam_chds(null);
            facilityForm.setWtf_nc_fam_float_material(null);
            facilityForm.setWtf_nc_fam_phosphor(null);
            facilityForm.setWtf_nc_fam_nitrogen(null);
            facilityForm.setC03(null);
            facilityForm.setWtf_nc_facility_municipality_code(null);
            ntisWastewaterTreatmentFaciDBService.saveRecord(conn, facilityForm);
            sendNotification(conn, Utils.getDouble(result.getId()), usrId, orgId, perId, lang, updateAgreement);
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
    }

    /**
     * Confirms the agreement .
     *
     * @param conn   the database connection
     * @param fauId  the ID of the agreement
     * @param perId  the ID of the person
     * @param orgId  the ID of the organization
     * @param usrId  the ID of the user
     * @throws Exception if an error occurs during the confirmation process
     */

    public void confirmAgreement(Connection conn, Double fauId, Double perId, Double orgId, Double usrId, String lang) throws Exception {
        checkIsFormActionAssigned(conn, NtisWfAgreementsLists.CONFIRM);

        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT w.*
                from ntis_facility_update_agreement fua
                     join ntis_wastewater_treatment_faci w on fua_wtf_id = wtf_id
                 AND fua_id = ?::int
                 """);
        stmt.addSelectParam(fauId);
        if (!hasUserRole(conn, NtisRolesConstants.NTIS_ADMIN) && (hasUserRole(conn, NtisRolesConstants.INST_WORK)
                || hasUserRole(conn, NtisRolesConstants.INST_ADMIN) || hasUserRole(conn, NtisRolesConstants.MUNICI_SPECIALIST))) {

            stmt.addParam4WherePart("""
                     exists (
                      select 1
                        from spr_organizations org
                         where org_id = ?::int
                           and  ((n02=wtf_facility_municipality_code::numeric ) or  c02 = 'INST_LT'))
                    """, orgId);

        }

        List<NtisWastewaterTreatmentFaciDAO> queryResult = queryController.selectQueryAsObjectArrayList(conn, stmt, NtisWastewaterTreatmentFaciDAO.class);
        if (!queryResult.isEmpty()) {

            NtisWastewaterTreatmentFaciDAO facilityForm = ntisWastewaterTreatmentFaciDBService.loadRecord(conn,
                    Utils.getDouble(queryResult.get(0).getWtf_id()));
            Double modelId = null;
            facilityForm.setWtf_waiting_update_confirmation("N");
            facilityForm.setWtf_type(facilityForm.getWtf_nc_type());
            facilityForm.setWtf_capacity(facilityForm.getWtf_nc_capacity());
            facilityForm.setWtf_installation_date(facilityForm.getWtf_nc_installation_date());
            facilityForm.setWtf_checkout_date(facilityForm.getWtf_nc_checkout_date());

            if (facilityForm.getWtf_manufacturer() != null && facilityForm.getWtf_nc_manufacturer() != null && facilityForm.getWtf_manufacturer().equalsIgnoreCase("KITA") && !facilityForm.getWtf_nc_manufacturer().equalsIgnoreCase("KITA")
                    && facilityForm.getWtf_fam_id() != null) {
                modelId = facilityForm.getWtf_fam_id();
                facilityForm.setWtf_fam_id(null);
            } else if (facilityForm.getWtf_manufacturer() != null && facilityForm.getWtf_nc_manufacturer() != null && facilityForm.getWtf_manufacturer().equalsIgnoreCase("KITA") && facilityForm.getWtf_nc_manufacturer().equalsIgnoreCase("KITA") && facilityForm.getWtf_fam_id() != null) {
                modelId = null;
                NtisFacilityModelDAO modelDAO = this.ntisFacilityModelDBService.loadRecord(conn, facilityForm.getWtf_fam_id());
                modelDAO.setFam_bds(facilityForm.getWtf_nc_fam_bds());
                modelDAO.setFam_chds(facilityForm.getWtf_nc_fam_chds());
                modelDAO.setFam_float_material(facilityForm.getWtf_nc_fam_float_material());
                modelDAO.setFam_pop_equivalent(facilityForm.getWtf_nc_fam_pop_equivalent());
                modelDAO.setFam_phosphor(facilityForm.getWtf_nc_fam_phosphor());
                modelDAO.setFam_nitrogen(facilityForm.getWtf_nc_fam_nitrogen());
                modelDAO.setFam_manufacturer(facilityForm.getWtf_nc_fam_manufacturer());
                modelDAO.setFam_description(facilityForm.getWtf_nc_fam_description());
                modelDAO.setFam_model(facilityForm.getWtf_nc_fam_model());
                modelDAO.setFam_tech_pass(facilityForm.getWtf_nc_technical_passport_id());
                this.ntisFacilityModelDBService.saveRecord(conn, modelDAO);
            } else if (facilityForm.getWtf_manufacturer() != null && facilityForm.getWtf_nc_manufacturer() != null && !facilityForm.getWtf_manufacturer().equalsIgnoreCase("KITA") && facilityForm.getWtf_nc_manufacturer().equalsIgnoreCase("KITA")) {
                modelId = null;
                NtisFacilityModelDAO modelDAO = this.ntisFacilityModelDBService.newRecord();
                modelDAO.setFam_bds(facilityForm.getWtf_nc_fam_bds());
                modelDAO.setFam_chds(facilityForm.getWtf_nc_fam_chds());
                modelDAO.setFam_float_material(facilityForm.getWtf_nc_fam_float_material());
                modelDAO.setFam_pop_equivalent(facilityForm.getWtf_nc_fam_pop_equivalent());
                modelDAO.setFam_phosphor(facilityForm.getWtf_nc_fam_phosphor());
                modelDAO.setFam_nitrogen(facilityForm.getWtf_nc_fam_nitrogen());
                modelDAO.setFam_manufacturer(facilityForm.getWtf_nc_fam_manufacturer());
                modelDAO.setFam_description(facilityForm.getWtf_nc_fam_description());
                modelDAO.setFam_model(facilityForm.getWtf_nc_fam_model());
                modelDAO.setFam_tech_pass(facilityForm.getWtf_nc_technical_passport_id());
                this.ntisFacilityModelDBService.saveRecord(conn, modelDAO);
                facilityForm.setWtf_fam_id(modelDAO.getFam_id());
            }

            facilityForm.setWtf_manufacturer(facilityForm.getWtf_nc_manufacturer());
            facilityForm.setWtf_manufacturer_description(facilityForm.getWtf_nc_manufacturer_description());
            facilityForm.setWtf_distance(facilityForm.getWtf_nc_distance());
            facilityForm.setWtf_model(facilityForm.getWtf_nc_model());
            facilityForm.setWtf_deleted(facilityForm.getWtf_nc_deleted());
            facilityForm.setWtf_data_source(facilityForm.getWtf_nc_data_source());
            facilityForm.setWtf_technical_passport_id(facilityForm.getWtf_nc_technical_passport_id());
            facilityForm.setWtf_facility_latitude(facilityForm.getWtf_nc_facility_latitude());
            facilityForm.setWtf_facility_longitude(facilityForm.getWtf_nc_facility_longitude());
            facilityForm.setWtf_discharge_latitude(facilityForm.getWtf_nc_discharge_latitude());
            facilityForm.setWtf_discharge_longitude(facilityForm.getWtf_nc_discharge_longitude());
            facilityForm.setWtf_discharge_type(facilityForm.getWtf_nc_discharge_type());
            facilityForm.setWtf_ad_id(facilityForm.getWtf_nc_ad_id());

            facilityForm.setC02(facilityForm.getC03());
            if (facilityForm.getWtf_nc_facility_municipality_code() != null) {
                facilityForm.setWtf_facility_municipality_code(facilityForm.getWtf_nc_facility_municipality_code());
            }

            ntisWastewaterTreatmentFaciDBService.saveRecord(conn, facilityForm);
            
            if(modelId !=null) {
                this.ntisFacilityModelDBService.deleteRecord(conn, modelId);            }

            ArrayList<NtisServedObjectsDAO> ntisServedObjects = ntisServedObjectsDBService.getRecordByWtfId(conn, Utils.getDouble(facilityForm.getWtf_id()));
            ArrayList<NtisServedObjectsDAO> newServedObjects = ntisServedObjectsVersionDBService.getRecordByWtfFauId(conn, facilityForm.getWtf_id(), fauId);

            if (!newServedObjects.isEmpty()) {

                List<NtisServedObjectsDAO> recordsToDelete = new ArrayList<>();
                List<NtisServedObjectsDAO> recordsToInsert = new ArrayList<>();

                for (NtisServedObjectsDAO servedObject : ntisServedObjects) {
                    boolean recordMatches = false;
                    for (NtisServedObjectsDAO servedObjectrecord : newServedObjects) {
                        if (servedObject.getSo_bn_id().equals(servedObjectrecord.getSo_bn_id())) {
                            recordMatches = true;
                            break;
                        }
                    }
                    if (!recordMatches) {
                        recordsToDelete.add(servedObject);
                    }
                }

                for (NtisServedObjectsDAO servedObjectrecord : newServedObjects) {
                    boolean recordMatches = false;
                    for (NtisServedObjectsDAO servedObject : ntisServedObjects) {
                        if (servedObject.getSo_bn_id().equals(servedObjectrecord.getSo_bn_id())) {
                            recordMatches = true;
                            break;
                        }
                    }
                    if (!recordMatches) {
                        servedObjectrecord.setSo_wtf_id(Utils.getDouble(facilityForm.getWtf_id()));
                        recordsToInsert.add(servedObjectrecord);
                    }
                }

                for (NtisServedObjectsDAO servedObject : recordsToDelete) {
                    ntisFacilityOwnersDBService.deleteByServedObjectId(conn, servedObject.getSo_id());
                    ntisServedObjectsDBService.deleteRecord(conn, servedObject.getSo_id());

                }

                for (NtisServedObjectsDAO servedObject : recordsToInsert) {
                    servedObject.setSo_wtf_id(facilityForm.getWtf_id());
                    NtisServedObjectsDAO servedObj = ntisServedObjectsDBService.insertRecord(conn, servedObject);
                    ntisWastewaterFacilityEdit.manageFacilityOwners(conn, servedObj, facilityForm.getWtf_id(), orgId, perId);
                }
            }

            var updateAgreement = ntisFacilityUpdateAgreementDBService.loadRecord(conn, fauId);
            updateAgreement.setFua_state(FacilityUpdateAgreementConstants.CONFIRMED);
            updateAgreement.setFua_confirmed_usr_id(usrId);
            updateAgreement.setFua_wtf_new_info_json(ntisWastewaterFacilityView.getTreatmentFacility(conn, facilityForm.getWtf_id(), "lt", orgId, perId));
            updateAgreement.setFua_confirmed_usr_id(usrId);
            ntisFacilityUpdateAgreementDBService.saveRecord(conn, updateAgreement);
            sendNotification(conn, fauId, usrId, orgId, perId, lang, updateAgreement);
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
    }

    /**
     * Confirms the agreement .
     *
     * @param conn   the database connection
     * @param fauId  the ID of the agreement
     * @param perId  the ID of the person
     * @param orgId  the ID of the organization
     * @param usrId  the ID of the user
     * @throws Exception if an error occurs during the confirmation process
     */

    public void confirmObjAgreement(Connection conn, Double fauId, Double perId, Double orgId, Double usrId, String lang) throws Exception {
        checkIsFormActionAssigned(conn, NtisWfAgreementsLists.CONFIRM);
        NtisFacilityUpdateAgreementDAO updateAgreement = ntisFacilityUpdateAgreementDBService.loadRecordByParams(conn, " WHERE fua_id = ?::int ",
                new SelectParamValue(Utils.getLong(fauId)));

        if (updateAgreement.getFua_id() != null) {
            NtisBuildingAgreementsDAO buildingAgreements = ntisBuildingAgreementsDBService.newRecord();
            buildingAgreements.setBa_bn_id(updateAgreement.getFua_bn_id());
            buildingAgreements.setBa_network_connection_date(updateAgreement.getFua_network_connection_date());
            buildingAgreements.setBa_org_id(updateAgreement.getFua_org_id());
            buildingAgreements.setBa_wastewater_treatment(NtisTypeWastewaterTreatment.CENTRALIZED.getCode());
            buildingAgreements.setBa_state(NtisBuildingAgreementStatus.CONFIRMED.getCode());
            buildingAgreements.setBa_created(Utils.getDate());
            buildingAgreements.setBa_source(NtisAggreementSource.MANUAL.getCode());
            buildingAgreements.setBa_manual_network_con_date(updateAgreement.getFua_network_connection_date());
            buildingAgreements.setBa_manual_org_id(updateAgreement.getFua_org_id());
            buildingAgreements.setBa_fil_id(updateAgreement.getFua_fil_id());
            this.ntisBuildingAgreementsDBService.saveRecord(conn, buildingAgreements);

            if (updateAgreement.getFua_id() != null) {
                updateAgreement.setFua_state(FacilityUpdateAgreementConstants.CONFIRMED);
                updateAgreement.setFua_confirmed_usr_id(usrId);
                ntisFacilityUpdateAgreementDBService.saveRecord(conn, updateAgreement);
            }
            closeWtf(conn, updateAgreement.getFua_so_id(), updateAgreement.getFua_network_connection_date());
            sendNotification(conn, fauId, usrId, orgId, perId, "lt", updateAgreement);
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
    }

    /**
     * Will close WTF if last building connect to centralized.
     *
     * @param conn   the database connection
     * @param fauId  the ID of the agreement
     * @param perId  the ID of the person
     * @param orgId  the ID of the organization
     * @param usrId  the ID of the user
     * @throws Exception if an error occurs during the confirmation process
     */

    public void closeWtf(Connection conn, Double soId, Date checkoutDate) throws Exception {
        NtisWastewaterTreatmentFaciDAO wtfRec = ntisWastewaterTreatmentFaciDBService.loadRecordByParams(conn, """
                  WHERE WTF_ID  = (SELECT SO_WTF_ID FROM NTIS.ntis_served_objects WHERE SO_ID = ?::int)
                    and  (SELECT  count(1)
                        FROM NTIS.ntis_served_objects
                         left join ntis.ntis_building_agreements on so_bn_id = ba_bn_id
                        WHERE so_wtf_id = wtf_id
                        AND ba_bn_id is null) = 0
                """, new SelectParamValue(Utils.getLong(soId)));

        if (wtfRec != null && wtfRec.getWtf_id() != null) {
            wtfRec.setWtf_state(NtisFacilityStatus.CLOSED.getCode());
            wtfRec.setWtf_checkout_date(checkoutDate);
            ntisWastewaterTreatmentFaciDBService.saveRecord(conn, wtfRec);
        }
    }

    /**
     * Metodas išsiųs pranešimus ir email'us paslaugų teikėjui arba INTS savininkui, pagal pateiktą prašymo id
     * @param conn - prisijungimas prie DB
     * @param fuaId - prašymo id
     * @param usrId - sesijos naudotojo id
     * @param orgId - sesijos organizacijos id
     * @param perId - sesijos asmens id
     * @param lang - sesijos kalba
     * @throws Exception
     */
    public void sendNotification(Connection conn, Double fuaId, Double usrId, Double orgId, Double perId, String lang, NtisFacilityUpdateAgreementDAO fuaDAO)
            throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_CREATE);
        Locale localeLT = new Locale("lt", "LT");
        SimpleDateFormat dateFormatLT = new SimpleDateFormat("yyyy 'm.' MMMMM dd 'd.'", localeLT);

        // applicant info
        SprPersonsDAO applicantPerson = sprPersonsDBService.loadRecord(conn, fuaDAO.getFua_per_id());
        List<SprUsersDAO> applicantUsers = sprUsersDBService.loadRecordsByParams(conn, " where usr_per_id = ? ", new SelectParamValue(fuaDAO.getFua_per_id()));

        // reviewer info
        SprUsersDAO reviewerUser = sprUsersDBService.loadRecord(conn, fuaDAO.getFua_confirmed_usr_id());

        // handle context
        Map<String, Object> context = new HashMap<>();
        context.put("fuaId", fuaId.intValue());
        context.put("fuaDate", dateFormatLT.format(fuaDAO.getFua_created()));
        context.put("fuaState", sprRefCodesDBService.loadRecordByParams(conn, """
                where rfc_domain = 'NTIS_WF_AGRREMENT_STATE' and rfc_code = ?
                """, new SelectParamValue(fuaDAO.getFua_state())).getRfc_description());
        if (fuaDAO.getFua_type().equals(NtisBuildingType.BUILDING.getCode())) {
            context.put("fuaUrl", fuaDAO.getFua_so_id().intValue() + "/" + NtisBuildingType.BUILDING.getCode().toLowerCase());
        } else {
            context.put("fuaUrl", fuaDAO.getFua_wtf_id().intValue());
        }
        String reviewer = "";
        if (reviewerUser != null) {
            reviewer = reviewerUser.getUsr_phone_number() != null
                    ? reviewerUser.getUsr_person_name() + " " + reviewerUser.getUsr_person_surname() + ", " + reviewerUser.getUsr_phone_number()
                    : reviewerUser.getUsr_person_name() + " " + reviewerUser.getUsr_person_surname();
        }
        context.put("reviewer", reviewer);

        // send notifications and emails
        if (applicantPerson.getPer_email() != null && applicantPerson.getC01() != null && applicantPerson.getC01().equals(DbConstants.BOOLEAN_TRUE)) {
            faciUpdateAgreementProcessRequest.createFuaStatusChangeRequest(conn, usrId, fuaId, applicantPerson.getPer_email(),
                    Languages.getLanguageByCode(lang), context);
        }
        for (SprUsersDAO applicantUser : applicantUsers) {
            ntisNotifications.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, fuaId, "NTIS_FUA_STS_NOTIF", "FUA_STATUS_SUBJECT",
                    "FUA_STATUS_BODY", context, NtisNtfRefType.FUA_AGREEMENT.getCode(), null, new Date(), applicantUser.getUsr_id(), fuaDAO.getFua_req_org_id(),
                    null);
        }
    }

    private void checkIsUserWtfOwner(Connection conn, Double fuaId, Double orgId, Double perId) throws Exception {
        NtisFacilityUpdateAgreementDAO agreementObj = ntisFacilityUpdateAgreementDBService.loadRecord(conn, fuaId);
        boolean isOwner;
        if (orgId != null) {
            isOwner = ntisFacilityOwnersDBService.isOrganizationAnOwnerOfFacility(conn, orgId, agreementObj.getFua_wtf_id());
        } else {
            isOwner = ntisFacilityOwnersDBService.isPersonAnOwnerOfFacility(conn, perId, agreementObj.getFua_wtf_id());
        }
        if (!isOwner) {
            throw new SparkBusinessException(new S2Message("common.error.notAnOwner", SparkMessageType.ERROR, "User is not the owner of selected facility"));
        }
    }
}