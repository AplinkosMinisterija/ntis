
/**
 *
 * Form is used to view the user's registered wastewater treatment facilities.
 *
 */
package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.constants.DbConstants;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameter;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import lt.project.ntis.constants.FacilityUpdateAgreementConstants;
import lt.project.ntis.constants.NtisRolesConstants;
import lt.project.ntis.dao.NtisWastewaterTreatmentFaciDAO;
import lt.project.ntis.enums.NtisClassifierDomains;
import lt.project.ntis.enums.NtisFacilityStatus;
import lt.project.ntis.logic.forms.security.NtisWastewaterFacilityListSecurityManager;
import lt.project.ntis.service.NtisFacilityOwnersDBService;
import lt.project.ntis.service.NtisWastewaterTreatmentFaciDBService;
import lt.project.ntis.service.TilesDBService;
import lt.project.ntis.service.TilesDBService.TilesAction;

@Component
public class NtisWastewaterFacilityList extends FormBase {

	private static final Logger log = LoggerFactory.getLogger(NtisWastewaterFacilityList.class);

	public static final String VIEW_INTS_INFO_ACTION = "VIEW_INTS_INFO";

	public static final String VIEW_INTS_INFO_ACTION_NAME = "View INTS information";

	public static final String INTS_APPROVED_ACTION = "INTS_APPROVED";

	public static final String INTS_APPROVED_ACTION_NAME = "INTS approved";

	public static final String REMOVE_FROM_ACCOUNT_ACTION = "REMOVE_FROM_ACCOUNT";

	public static final String REMOVE_FROM_ACCOUNT_ACTION_NAME = "Remove from account";

	@Autowired
	BaseControllerJDBC queryController;

	@Autowired
	BaseControllerJDBC baseControllerJDBC;

	@Autowired
	DBStatementManager dbStatementManager;

	@Autowired
	SprOrganizationsDBService organizationsDBService;

	@Autowired
	NtisWastewaterTreatmentFaciDBService ntisWastewaterTreatmentFaciDBService;

	@Autowired
	TilesDBService tilesDBService;

	@Autowired
	NtisFacilityOwnersDBService ntisFacilityOwnersDBService;

	/**
	 * Returns the name of the form as a String.
	 *
	 * @return a String representing the name of the form
	 */
	@Override
	public String getFormName() {
		return "NTIS_WASTEWATER_FACILITY_LIST";
	}

	/**
	 * Defines the form and actions for the NTIS Wastewater Facility List form. This
	 * includes adding form actions for creating, reading, updating, viewing
	 * facility information, approving facilities, and removing them from accounts.
	 */
	@Override
	public void defineFormAndActions() {
		setFormInfo(getFormName(), "Wastewater facility list", "Wastewater facility list");
		addFormAction(FormBase.ACTION_CREATE, FormBase.ACTION_CREATE_DESC, FormBase.ACTION_CREATE_DESC);
		addFormAction(FormBase.ACTION_READ, FormBase.ACTION_READ_DESC, FormBase.ACTION_READ_DESC);
		addFormAction(FormBase.ACTION_UPDATE, FormBase.ACTION_UPDATE_DESC, FormBase.ACTION_UPDATE_DESC);
		addFormAction(VIEW_INTS_INFO_ACTION, VIEW_INTS_INFO_ACTION_NAME, VIEW_INTS_INFO_ACTION_NAME);
		addFormAction(INTS_APPROVED_ACTION, INTS_APPROVED_ACTION_NAME, INTS_APPROVED_ACTION_NAME);
		addFormAction(REMOVE_FROM_ACCOUNT_ACTION, REMOVE_FROM_ACCOUNT_ACTION_NAME, REMOVE_FROM_ACCOUNT_ACTION_NAME);
	}

	/**
	 *
	 * Removes a wastewater treatment facility from the list, setting the
	 * wtf_deleted field to true.
	 *
	 * @param conn             a Connection object representing the database
	 *                         connection
	 * @param recordIdentifier a RecordIdentifier object containing the ID of the
	 *                         facility to remove
	 * @param orgId            - sesijos organizacijos ID
	 * @param perId            - sesijos asmens ID
	 * @throws NumberFormatException if the ID in the recordIdentifier object cannot
	 *                               be parsed to a double
	 * @throws Exception             if there is an error executing the UPDATE
	 *                               statement
	 */
	public void removeFacility(Connection conn, RecordIdentifier recordIdentifier, Double orgId, Double perId)
			throws NumberFormatException, Exception {
		this.isFormActionAssigned(conn, FormBase.ACTION_UPDATE);
		Double wtfId = recordIdentifier.getIdAsDouble();
		Boolean queryByOwner = (!this.hasUserRole(conn, NtisRolesConstants.MUNICI_SPECIALIST)
				&& !this.hasUserRole(conn, NtisRolesConstants.NTIS_ADMIN)
				&& !this.hasUserRole(conn, NtisRolesConstants.INST_WORK)
				&& !this.hasUserRole(conn, NtisRolesConstants.INST_ADMIN)
				&& !this.hasUserRole(conn, NtisRolesConstants.VAND_ADMIN)
				&& !this.hasUserRole(conn, NtisRolesConstants.CAR_SPECIALIST)
				&& !this.hasUserRole(conn, NtisRolesConstants.PASL_ADMIN));

		if (queryByOwner) {
			if (orgId != null) {
				wtfId = ntisFacilityOwnersDBService.loadRecordByParams(conn,
						" WHERE fo.fo_org_id = ?::int AND fo.fo_wtf_id = ?::int GROUP BY fo.fo_wtf_id ",
						new SelectParamValue(orgId), new SelectParamValue(wtfId)).getFo_wtf_id();
			} else {
				wtfId = ntisFacilityOwnersDBService.loadRecordByParams(conn,
						" WHERE fo.fo_per_id = ?::int AND fo.fo_wtf_id = ?::int GROUP BY fo.fo_wtf_id ",
						new SelectParamValue(perId), new SelectParamValue(wtfId)).getFo_wtf_id();
			}
		}
		NtisWastewaterTreatmentFaciDAO ntisWastewaterTreatmentFaciDAO = new NtisWastewaterTreatmentFaciDAO();
		ntisWastewaterTreatmentFaciDAO = ntisWastewaterTreatmentFaciDBService.loadRecord(conn, wtfId);
		ntisWastewaterTreatmentFaciDAO.setWtf_deleted(DbConstants.BOOLEAN_TRUE);
		ntisWastewaterTreatmentFaciDBService.updateRecord(conn, ntisWastewaterTreatmentFaciDAO);
		tilesDBService.updateOneWastewater(conn, ntisWastewaterTreatmentFaciDAO.getWtf_id(), TilesAction.DELETE);
		tilesDBService.cleanWastewaterCache(conn);
	}

	/**
	 * Approves a wastewater treatment facility, setting its state to CONFIRMED.
	 *
	 * @param conn             a Connection object representing the database
	 *                         connection
	 * @param recordIdentifier a RecordIdentifier object containing the ID of the
	 *                         facility to approve
	 * @param orgId            - sesijos organizacijos ID
	 * @param perId            - sesijos asmens ID
	 * @throws NumberFormatException if the ID in the recordIdentifier object cannot
	 *                               be parsed to a double
	 * @throws Exception             if there is an error executing the UPDATE
	 *                               statement
	 */

	public void approveFacility(Connection conn, RecordIdentifier recordIdentifier, Double orgId, Double perId)
			throws NumberFormatException, Exception {
		this.isFormActionAssigned(conn, FormBase.ACTION_UPDATE);
		Double wtfId = recordIdentifier.getIdAsDouble();
		Boolean queryByOwner = (!this.hasUserRole(conn, NtisRolesConstants.MUNICI_SPECIALIST)
				&& !this.hasUserRole(conn, NtisRolesConstants.NTIS_ADMIN)
				&& !this.hasUserRole(conn, NtisRolesConstants.INST_WORK)
				&& !this.hasUserRole(conn, NtisRolesConstants.INST_ADMIN)
				&& !this.hasUserRole(conn, NtisRolesConstants.VAND_ADMIN)
				&& !this.hasUserRole(conn, NtisRolesConstants.CAR_SPECIALIST)
				&& !this.hasUserRole(conn, NtisRolesConstants.PASL_ADMIN));

		if (queryByOwner) {
			if (orgId != null) {
				wtfId = ntisFacilityOwnersDBService
						.loadRecordByParams(conn, " WHERE fo_org_id = ?::int AND fo_wtf_id = ?::int limit 1",
								new SelectParamValue(orgId), new SelectParamValue(wtfId))
						.getFo_wtf_id();
			} else {
				wtfId = ntisFacilityOwnersDBService
						.loadRecordByParams(conn, " WHERE fo_per_id = ?::int AND fo_wtf_id = ?::int limit 1",
								new SelectParamValue(perId), new SelectParamValue(wtfId))
						.getFo_wtf_id();
			}
		}

		NtisWastewaterTreatmentFaciDAO ntisWastewaterTreatmentFaciDAO = ntisWastewaterTreatmentFaciDBService
				.loadRecord(conn, wtfId);
		ntisWastewaterTreatmentFaciDAO.setWtf_state(NtisFacilityStatus.CONFIRMED.getCode());
		ntisWastewaterTreatmentFaciDBService.updateRecord(conn, ntisWastewaterTreatmentFaciDAO);
	}

	/**
	 * Retrieves a list of wastewater treatment facilities from the database. The
	 * returned list includes information such as the facility's state, type,
	 * address, and served objects.
	 *
	 * @param conn           a Connection object representing the database
	 *                       connection
	 * @param params         a SelectRequestParams object containing the parameters
	 *                       for the select request
	 * @param userId         a Double representing the ID of the user making the
	 *                       request
	 * @param personId       a Double representing the ID of the person associated
	 *                       with the user
	 * @param organizationId a Double representing the ID of the organization
	 *                       associated with the user
	 * @return a String containing the list of wastewater treatment facilities in
	 *         JSON format
	 * @throws Exception if there is an error executing the SELECT statement or
	 *                   formatting the results as JSON
	 */

	public String getRecList(Connection conn, SelectRequestParams params, Double userId, Double personId,
			Double organizationId, String language) throws Exception {

		checkIsFormActionAssigned(conn, NtisWastewaterFacilityList.ACTION_READ);
		StatementAndParams stmt = new StatementAndParams();

		Boolean queryByOwner = (!this.hasUserRole(conn, NtisRolesConstants.MUNICI_SPECIALIST)
				&& !this.hasUserRole(conn, NtisRolesConstants.NTIS_ADMIN)
				&& !this.hasUserRole(conn, NtisRolesConstants.INST_ADMIN)
				&& !this.hasUserRole(conn, NtisRolesConstants.INST_WORK)
				&& !this.hasUserRole(conn, NtisRolesConstants.VAND_ADMIN)
				&& !this.hasUserRole(conn, NtisRolesConstants.CAR_SPECIALIST)
				&& !this.hasUserRole(conn, NtisRolesConstants.PASL_ADMIN));

		StringBuilder statement = new StringBuilder();

		if (queryByOwner) {
			if (organizationId != null) {
				statement.append(
						"WITH wtf_assigned AS (SELECT fo.fo_wtf_id wtf_id FROM ntis_facility_owners fo WHERE fo.fo_org_id = ?::int GROUP BY fo.fo_wtf_id) ");
			} else {
				statement.append(
						"WITH wtf_assigned AS (SELECT fo.fo_wtf_id wtf_id FROM ntis_facility_owners fo WHERE fo.fo_per_id = ?::int GROUP BY fo.fo_wtf_id) ");
			}
		}

		statement.append(" SELECT  "//
				+ " wtf.wtf_id,  "//
				+ " wtf.wtf_state,  "//
				+ " coalesce(wst.rfc_meaning, wtf.wtf_state) as wtf_state_meaning, "//
				+ " wtf.wtf_type,  "//
				+ " (  "//
				+ "   SELECT fua.fua_id  "//
				+ "   FROM ntis.ntis_facility_update_agreement fua  "//
				+ "   WHERE fua.fua_wtf_id = wtf.wtf_id AND fua.fua_state = ? " //
				+ "   limit 1"//
				+ " ) AS wtf_fua_id,  "//
				+ " rfc.rfc_meaning AS wtf_title,  "//
				+ " wtf.wtf_ad_id,  "//
				+ " wtf.wtf_facility_latitude AS wtf_fl_latitude,  "//
				+ " wtf.wtf_facility_longitude AS wtf_fl_longitude,  "//
				+ " wtf.wtf_facility_latitude || ', ' || wtf.wtf_facility_longitude AS coordinates, " //
				+ " coalesce(ad.full_address_text, wtf.wtf_facility_latitude || ', ' || wtf.wtf_facility_longitude) AS wtf_address ,  "//
				+ " (  "//
				+ "  SELECT "//
				+ "   JSONB_AGG( "//
				+ "   JSONB_BUILD_OBJECT( "//
				+ "          'so_id', so.so_id,   "//
				+ "          'so_ad_id', so.so_ad_id,   "//
				+ "          'so_address', adw.full_address_text,  "//
				+ "          'so_building_no', adw.building_no, "//
				+ "          'so_flat_no', adw.flat_no, "//
				+ "          'so_latitude', so.so_coordinate_latitude,  "//
				+ "          'so_longitude', so.so_coordinate_longitude,  "//
				+ "          'so_inv_code', ntrs.bn_obj_inv_code,  "//
				+ "          'so_purp_name', ntrs.bn_pask_name  "//
				+ " )  "//
				+ "   ORDER BY ADW.street , nullif(regexp_replace(BUILDING_NO, '[^0-9]','', 'g'), '')::numeric, nullif(regexp_replace(adw.FLAT_NO , '[^0-9]','', 'g'), '')::numeric ) AS wtf_served_objects "//
				+ "  FROM ntis.ntis_served_objects so  "//
				+ "  JOIN ntis.ntis_building_ntrs ntrs ON so.so_bn_id = ntrs.bn_id  ");

		if (params.getAdvancedParameters().get("bn_aob_code") != null) {
			statement.append(" and ntrs.bn_aob_code::text = ? ");
		}
		if (params.getAdvancedParameters().get("bn_obj_inv_code") != null) {
			statement.append(" and ntrs.bn_obj_inv_code = ?::text ");
		}
		statement.append("  LEFT JOIN ntis.ntis_address_vw adw ON adw.address_id = ntrs.bn_ad_id  "//
				+ "  LEFT JOIN ntis_building_agreements ba ON ba.ba_bn_id = so.so_bn_id"//
				+ "   WHERE so.so_wtf_id = wtf.wtf_id and wtf.wtf_state != 'CLOSED' and (ba.ba_wastewater_treatment != 'CENTRALIZED' or ba.ba_wastewater_treatment IS NULL)"//
				+ "   GROUP BY so.so_wtf_id  "//
				+ " ) AS wtf_served_objects "//
				+ " FROM ntis.ntis_wastewater_treatment_faci wtf " //
				+ " LEFT JOIN spark.spr_ref_codes_vw wst ON wst.rfc_code = wtf.wtf_state and wst.rfc_domain = 'NTIS_INTS_STATUS' and wst.rft_lang = ?");
		if (queryByOwner) {
			statement.append(" JOIN wtf_assigned asig ON wtf.wtf_id = asig.wtf_id ");
		}
		statement.append(" LEFT JOIN ntis.ntis_address_vw AS ad ON ad.address_id = wtf.Wtf_ad_id  "//
				+ " LEFT JOIN spark.spr_ref_codes_vw AS rfc ON rfc.rfc_code = wtf.wtf_type AND rfc.rft_lang = wst.rft_lang  "//
				+ " AND rfc.rfc_domain = ?  "//
				+ " WHERE wtf.wtf_deleted = ? ");
		if (!queryByOwner && this.hasUserRole(conn, NtisRolesConstants.MUNICI_SPECIALIST)) {
			statement.append(" AND wtf.wtf_facility_municipality_code::int = ?::int ");
		}
		stmt.setStatement(statement.toString());
		if (queryByOwner) {
			if (organizationId != null) {
				stmt.addSelectParam(organizationId);
			} else {
				stmt.addSelectParam(personId);
			}
		}
		stmt.addSelectParam(FacilityUpdateAgreementConstants.SUBMITTED);
		if (params.getAdvancedParameters().get("bn_aob_code") != null) {
			stmt.addSelectParam(params.getAdvancedParameters().get("bn_aob_code").getParamValue().getValue());
			stmt.addCondition4WherePart(" exists (select 1 from ntis_served_objects "//
					+ "join ntis_building_ntrs ntrs on so_bn_id = bn_id and bn_aob_code::text = '"
					+ params.getAdvancedParameters().get("bn_aob_code").getParamValue().getValue()
					+ "' where so_wtf_id = wtf.wtf_id)  ", " and");
		}
		if (params.getAdvancedParameters().get("bn_obj_inv_code") != null) {
			stmt.addSelectParam(params.getAdvancedParameters().get("bn_obj_inv_code").getParamValue().getValue());
			stmt.addCondition4WherePart(" exists (select 1 from ntis_served_objects "//
					+ "join ntis_building_ntrs ntrs on so_bn_id = bn_id and bn_obj_inv_code = '"
					+ params.getAdvancedParameters().get("bn_obj_inv_code").getParamValue().getValue()
					+ "' where so_wtf_id = wtf.wtf_id)  ", " and");

		}
		stmt.addSelectParam(language);
		stmt.addSelectParam(NtisClassifierDomains.NTIS_WTF_TYPE.getCode());

		HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
		this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt,
				advancedParamList);

		if (advancedParamList.get("wtf_address") != null && !advancedParamList.get("wtf_address").isEmpty()) {
			AdvancedSearchParameterStatement paramStmt = advancedParamList.get("wtf_address");
			AdvancedSearchParameter param = paramStmt.getParamValue();
			param.setValue(Utils.replaceNationalCharacters(param.getValue()).toLowerCase());
			param.setUpperLower(AdvancedSearchParameter.REGURAL);
			stmt.addParam4WherePart("ad.ad_address_search", StatementAndParams.PARAM_STRING, paramStmt);
		}

		stmt.addParam4WherePart("wtf.wtf_state", StatementAndParams.PARAM_STRING, advancedParamList.get("wtf_state"));
		stmt.addParam4WherePart("wtf.wtf_type ", StatementAndParams.PARAM_STRING, advancedParamList.get("wtf_type"));
		stmt.addParam4WherePart("wtf.wtf_facility_latitude::text ", StatementAndParams.PARAM_STRING,
				advancedParamList.get("wtf_latitude"));
		stmt.addParam4WherePart("wtf.wtf_facility_longitude::text ", StatementAndParams.PARAM_STRING,
				advancedParamList.get("wtf_longitude"));
		stmt.setWhereExists(true);
		stmt.addSelectParam(DbConstants.BOOLEAN_FALSE);

		if (queryByOwner == false && this.hasUserRole(conn, NtisRolesConstants.MUNICI_SPECIALIST) == true) {
			if (organizationId != null) {
				stmt.addSelectParam(organizationsDBService.loadRecord(conn, organizationId).getN02());
			}
		}
		stmt.setStatementOrderPart(" order by wtf_id desc ");
		NtisWastewaterFacilityListSecurityManager sqm = new NtisWastewaterFacilityListSecurityManager();
		FormActions formActions = this.getFormActions(conn);
		String[] recordActionMenu = { NtisWastewaterFacilityList.ACTION_UPDATE, NtisWastewaterFacilityList.ACTION_READ,
				NtisWastewaterFacilityList.VIEW_INTS_INFO_ACTION, NtisWastewaterFacilityList.INTS_APPROVED_ACTION,
				NtisWastewaterFacilityList.REMOVE_FROM_ACCOUNT_ACTION, };
		formActions.prepareAvailableMenuAction(recordActionMenu);
		sqm.setFormActions(this.getFormActions(conn));
		return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
	}

}
