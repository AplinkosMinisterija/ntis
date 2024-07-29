package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.s2.server.jwt.BackendUserSession;
import eu.itreegroup.s2.server.rest.S2RestRequestContext;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.ForeignKeyParams;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.app.tools.DBPropertyManager;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.Data2ObjectProcessor;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import eu.itreegroup.spark.modules.admin.service.SprPersonsDBService;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.service.SprFilesDBServiceImpl;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.constants.FacilityUpdateAgreementConstants;
import lt.project.ntis.constants.NtisCommonActionsConstants;
import lt.project.ntis.constants.NtisRolesConstants;
import lt.project.ntis.dao.NtisAdrResidencesDAO;
import lt.project.ntis.dao.NtisAdrStreetsDAO;
import lt.project.ntis.dao.NtisFacilityFilesDAO;
import lt.project.ntis.dao.NtisFacilityModelDAO;
import lt.project.ntis.dao.NtisFacilityOwnersDAO;
import lt.project.ntis.dao.NtisFacilityUpdateLogDAO;
import lt.project.ntis.dao.NtisMunicipalitiesDAO;
import lt.project.ntis.dao.NtisSelectedFacilitiesDAO;
import lt.project.ntis.dao.NtisServedObjectsDAO;
import lt.project.ntis.dao.NtisServedObjectsVersionDAO;
import lt.project.ntis.dao.NtisWastewaterTreatmentFaciDAO;
import lt.project.ntis.enums.NtisBuildingOwnerType;
import lt.project.ntis.enums.NtisFacilityOwnerType;
import lt.project.ntis.enums.NtisFacilityRegType;
import lt.project.ntis.enums.NtisFacilityStatus;
import lt.project.ntis.enums.NtisQueryOperations;
import lt.project.ntis.enums.NtisWtfType;
import lt.project.ntis.models.AddressSearch;
import lt.project.ntis.models.AddressSearchResponse;
import lt.project.ntis.models.FacilityModelAdditionalDetails;
import lt.project.ntis.models.NtisFacilityModel;
import lt.project.ntis.models.NtisWastewaterTreatmentFaci;
import lt.project.ntis.models.NtrOwnerModel;
import lt.project.ntis.service.NtisAdrAddressesDBService;
import lt.project.ntis.service.NtisBuildingNtrsDBService;
import lt.project.ntis.service.NtisDischargeWastewaterDBService;
import lt.project.ntis.service.NtisFacilityFilesDBService;
import lt.project.ntis.service.NtisFacilityLocationsDBService;
import lt.project.ntis.service.NtisFacilityModelDBService;
import lt.project.ntis.service.NtisFacilityOwnersDBService;
import lt.project.ntis.service.NtisFacilityUpdateAgreementDBService;
import lt.project.ntis.service.NtisFacilityUpdateLogDBService;
import lt.project.ntis.service.NtisMunicipalitiesDBService;
import lt.project.ntis.service.NtisSelectedFacilitiesDBService;
import lt.project.ntis.service.NtisServedObjectsDBService;
import lt.project.ntis.service.NtisServedObjectsVersionDBService;
import lt.project.ntis.service.NtisWastewaterTreatmentFaciDBService;
import lt.project.ntis.service.TilesDBService;
import lt.project.ntis.service.TilesDBService.TilesAction;

@Component
public class NtisWastewaterFacilityEdit extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(NtisWastewaterFacilityEdit.class);

    static final int NEW_ORG = 1;

    static final int NEW_PERSON = 2;

    public static final String CREATE_OFFICIAL_ACTION = "CREATE_OFFICIAL";

    public static final String CREATE_OFFICIAL_ACTION_DESC = "Create official INTS";

    @Autowired
    protected S2RestRequestContext<BackendUserSession> requestContext;

    @Autowired
    NtisWastewaterTreatmentFaciDBService ntisWastewaterTreatmentFaciDBService;

    @Autowired
    NtisDischargeWastewaterDBService ntisDischargeWastewaterDBService;

    @Autowired
    NtisServedObjectsDBService ntisServedObjectsDBService;

    @Autowired
    NtisFacilityLocationsDBService ntisFacilityLocationsDBService;

    @Autowired
    SprFilesDBServiceImpl filesDBService;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    NtisFacilityOwnersDBService ntisFacilityOwnersDBService;

    @Autowired
    NtisAdrAddressesDBService ntisAdrAddressesDBService;

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    NtisFacilityFilesDBService ntisFacilityFilesDBService;

    @Autowired
    NtisFacilityUpdateLogDBService facilityUpdateLogDBService;

    @Autowired
    SprOrganizationsDBService organizationsDBService;

    @Autowired
    SprPersonsDBService sprPersonsDBService;

    @Autowired
    NtisSelectedFacilitiesDBService ntisSelectedFacilitiesDBService;

    @Autowired
    DBPropertyManager dBPropertyManager;

    @Autowired
    NtisMunicipalitiesDBService ntisMunicipalitiesDBService;

    @Autowired
    NtisBuildingNtrsDBService ntisBuildingNtrsDBService;

    @Autowired
    NtisFacilityUpdateAgreementDBService ntisFacilityUpdateAgreementDBService;

    @Autowired
    NtisWastewaterFacilityView ntisWastewaterFacilityView;

    @Autowired
    NtisServedObjectsVersionDBService ntisServedObjectsVersionDBService;

    @Autowired
    TilesDBService tilesDBService;

    @Autowired
    NtisFacilityModelDBService ntisFacilityModelDBService;

    @Override
    public String getFormName() {
        return "WASTEWATER_FACILITY_EDIT";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Įrenginio redagavimas", "Nuotekų valymo įrenginio redagavimo forma");
        addFormAction(FormBase.ACTION_CREATE, FormBase.ACTION_CREATE_DESC, FormBase.ACTION_CREATE_DESC);
        addFormAction(FormBase.ACTION_READ, FormBase.ACTION_READ_DESC, FormBase.ACTION_READ_DESC);
        addFormAction(FormBase.ACTION_UPDATE, FormBase.ACTION_UPDATE_DESC, FormBase.ACTION_UPDATE_DESC);
        addFormAction(CREATE_OFFICIAL_ACTION, CREATE_OFFICIAL_ACTION_DESC, CREATE_OFFICIAL_ACTION_DESC);
    }

    public List<AddressSearchResponse> checkNtrObjects(Connection conn, Double addressId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisWastewaterFacilityEdit.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT "//
                + "       addr.address_id,  "//
                + "       addr.full_address_text, "//
                + "       ntrs.bn_id AS ntr_building_id, "//
                + "       ntrs.bn_pask_name AS purpose_of_building, "//
                + "       ntrs.bn_coordinate_latitude_adr AS latitude, "//
                + "       ntrs.bn_coordinate_longitude_adr AS longitude, "//
                + "       ntrs.bn_obj_inv_code AS inv_code "//
                + "  FROM ntis.ntis_address_vw AS addr "//
                + " INNER JOIN ntis.ntis_building_ntrs AS ntrs "//
                + "    ON ntrs.bn_ad_id = addr.address_id " //
                + " WHERE addr.address_id = ?::int");
        stmt.addSelectParam(addressId.longValue());

        return queryController.selectQueryAsObjectArrayList(conn, stmt, AddressSearchResponse.class);
    }

    // PAGAL KOORDINATES
    public List<AddressSearchResponse> findAddressByCoorinates(Connection conn, ArrayList<Double> coordinates) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisWastewaterFacilityEdit.ACTION_READ);
        String radiusLength = dBPropertyManager.getPropertyByName("COORDINATE_RADIUS_RANGE", "50");
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT "//
                + "       addr.address_id,  "//
                + "       addr.full_address_text, "//
                + "       ntrs.bn_id AS ntr_building_id, "//
                + "       ntrs.bn_pask_name AS purpose_of_building, "//
                + "       ntrs.bn_coordinate_latitude_adr AS latitude, "//
                + "       ntrs.bn_coordinate_longitude_adr AS longitude, "//
                + "       ntrs.bn_obj_inv_code AS inv_code "//
                + "         FROM ntis.ntis_address_vw AS addr "//
                + "   INNER JOIN ntis.ntis_building_ntrs AS ntrs"//
                + "           ON ntrs.bn_ad_id = addr.address_id"//
                + "  WHERE ST_DWithin(ad_geom, ST_SetSRID(ST_MakePoint(?,?), 3346), ?)");
        stmt.addSelectParam(coordinates.get(0));
        stmt.addSelectParam(coordinates.get(1));
        stmt.addSelectParam(Utils.getDouble(radiusLength));
        stmt.setStatementOrderPart(
                "ORDER BY REGEXP_REPLACE(addr.building_no, '[[:alpha:]]','','g')::int ,addr.building_no,  REGEXP_REPLACE(addr.flat_no, '[[:alpha:]]','','g')::int NULLS FIRST, addr.flat_no  NULLS FIRST");
        return queryController.selectQueryAsObjectArrayList(conn, stmt, AddressSearchResponse.class);
    }

    public boolean verifyAddress(Connection conn, Double facilityAddressId, String facilityType) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisWastewaterFacilityEdit.ACTION_READ);
        NtisWastewaterTreatmentFaciDAO facilityLocation = ntisWastewaterTreatmentFaciDBService.loadRecordByParams(conn, " wtf_ad_id = ?::int and wtf_type = ? ",
                new SelectParamValue(facilityAddressId), new SelectParamValue(facilityType));
        return facilityLocation != null;
    }

    public boolean compareWtfCoordinates(ArrayList<Double> firstCoordinates, ArrayList<Double> secondCoordinates) {

        if (secondCoordinates.get(0) != null && secondCoordinates.get(1) != null) {
            return (Double.compare(firstCoordinates.get(0).doubleValue(), secondCoordinates.get(0).doubleValue()) == 0
                    && Double.compare(firstCoordinates.get(1).doubleValue(), secondCoordinates.get(1).doubleValue()) == 0);
        } else {
            return false;
        }

    }

    private NtisWastewaterTreatmentFaciDAO getWF(Connection conn, Double wtfId, Double orgId, Double perId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisWastewaterFacilityEdit.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                select *
                from
                ntis_wastewater_treatment_faci wtf
                """);
        stmt.addParam4WherePart("wtf_id = ?::int ", wtfId);
        if (!hasUserRole(conn, NtisRolesConstants.NTIS_ADMIN)) {
            if (orgId == null && (hasUserRole(conn, NtisRolesConstants.INTS_OWNER))) {
                stmt.addParam4WherePart("""
                         exists (
                          select 1
                            from ntis_facility_owners
                             where fo_owner_type in ( 'OWNER', 'MANAGER', 'SELF_ASSIGNED')
                             and now() between fo_date_from and COALESCE(fo_date_to, now())
                             and wtf.wtf_id = fo_wtf_id
                               and fo_per_id = ?::int)
                        """, perId);

            } else {
                if (hasUserRole(conn, NtisRolesConstants.INST_WORK) || hasUserRole(conn, NtisRolesConstants.MUNICI_SPECIALIST)
                        || hasUserRole(conn, NtisRolesConstants.INST_ADMIN)) {

                    stmt.addParam4WherePart("""
                             exists (
                              select 1
                                from spr_organizations org
                                 where org_id = ?::int
                                   and  ((n02 = wtf.wtf_facility_municipality_code::int) or  c01 = 'INST_LT'))
                            """, orgId);

                } else if (!hasUserRole(conn, NtisRolesConstants.CAR_SPECIALIST) && !hasUserRole(conn, NtisRolesConstants.PASL_ADMIN)
                        && !hasUserRole(conn, NtisRolesConstants.VAND_ADMIN)) {
                    stmt.addParam4WherePart("""
                             exists (
                              select 1
                                from ntis_facility_owners
                                 where fo_owner_type in ( 'OWNER', 'MANAGER', 'SELF_ASSIGNED', 'SERVICE_PROVIDER')
                                   and now() between fo_date_from and COALESCE(fo_date_to, now())
                                   and wtf.wtf_id = fo_wtf_id
                                   and fo_org_id = ?::int)
                            """, orgId);
                }
            }
        }
        List<NtisWastewaterTreatmentFaciDAO> result = queryController.selectQueryAsObjectArrayList(conn, stmt, NtisWastewaterTreatmentFaciDAO.class);
        return !result.isEmpty() ? result.get(0) : null;

    }

    public NtisWastewaterTreatmentFaci getFacility(Connection conn, RecordIdentifier recordIdentifier, Double orgId, Double perId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisWastewaterFacilityEdit.ACTION_READ);
        NtisWastewaterTreatmentFaciDAO result = getWF(conn, recordIdentifier.getIdAsDouble(), orgId, perId);
        if (result != null) {
            if (hasUserRole(conn, NtisRolesConstants.INTS_OWNER) && "Y".equals(result.getWtf_waiting_update_confirmation())) {
                return getFacilityNotConfirmed(conn, recordIdentifier, result);
            } else {
                return getFacilityConfirmed(conn, recordIdentifier, result);
            }
        } else {
            throw new SparkBusinessException(new S2Message("common.error.action_not_granted", SparkMessageType.ERROR));
        }
    }

    private NtisWastewaterTreatmentFaci getFacilityConfirmed(Connection conn, RecordIdentifier recordIdentifier, NtisWastewaterTreatmentFaciDAO result)
            throws Exception {

        NtisWastewaterTreatmentFaci formData = new NtisWastewaterTreatmentFaci();
        List<AddressSearchResponse> addressData = new ArrayList<>();
        formData.setWtf_data_source(result.getWtf_data_source());
        formData.setWtf_capacity(result.getWtf_capacity());
        formData.setWtf_distance(result.getWtf_distance());
        formData.setWtf_id(result.getWtf_id().intValue());
        formData.setWtf_installation_date(result.getWtf_installation_date());
        formData.setWtf_checkout_date(result.getWtf_checkout_date());
        formData.setWtf_manufacturer(result.getWtf_manufacturer());
        formData.setWtf_manufacturer_description(result.getWtf_manufacturer_description());
        formData.setWtf_model(result.getWtf_model());
        formData.setWtf_state(result.getWtf_state());
        formData.setWtf_technical_passport_id(result.getWtf_technical_passport_id());
        formData.setWtf_type(result.getWtf_type());
        formData.setWtf_discharge_type(result.getWtf_discharge_type());
        formData.setWtf_facility_latitude(result.getWtf_facility_latitude());
        formData.setWtf_facility_longitude(result.getWtf_facility_longitude());
        formData.setWtf_discharge_latitude(result.getWtf_discharge_latitude());
        formData.setWtf_discharge_longitude(result.getWtf_discharge_longitude());
        formData.setWtf_identification_number(result.getC02());
        formData.setWtf_fam_id(result.getWtf_fam_id());

        if (formData.getWtf_fam_id() != null) {
            NtisFacilityModelDAO facilityModelDAO = this.ntisFacilityModelDBService.loadRecord(conn, formData.getWtf_fam_id());
            if (facilityModelDAO != null) {
                formData.setFam_model(facilityModelDAO.getFam_model());
                formData.setFam_manufacturer(facilityModelDAO.getFam_manufacturer());
                formData.setFam_description(facilityModelDAO.getFam_description());
                formData.setFam_bds(facilityModelDAO.getFam_bds());
                formData.setFam_chds(facilityModelDAO.getFam_chds());
                formData.setFam_float_material(facilityModelDAO.getFam_float_material());
                formData.setFam_pop_equivalent(facilityModelDAO.getFam_pop_equivalent());
                formData.setFam_nitrogen(facilityModelDAO.getFam_nitrogen());
                formData.setFam_phosphor(facilityModelDAO.getFam_phosphor());
            }
        }

        ArrayList<Double> firstCoordinates = new ArrayList<>();
        firstCoordinates.clear();
        firstCoordinates.add(result.getWtf_facility_latitude());
        firstCoordinates.add(result.getWtf_facility_longitude());

        ArrayList<Double> secondCoordinates = new ArrayList<>();
        secondCoordinates.clear();
        secondCoordinates.add(result.getWtf_discharge_latitude());
        secondCoordinates.add(result.getWtf_discharge_longitude());

        if (result.getWtf_ad_id() != null) {

            StatementAndParams stmt = new StatementAndParams();
            stmt.setStatement("SELECT "//
                    + "       addr.full_address_text, "//
                    + "       addr.address_id, "//
                    + "       addr.latitude, "//
                    + "       addr.longitude, "//
                    + "       ntrs.bn_obj_inv_code AS inv_code, "//
                    + "       ntrs.bn_pask_name AS purpose_of_building, "//
                    + "       ntrs.bn_id AS ntr_building_id "//
                    + "      FROM ntis.ntis_address_vw AS addr "//
                    + " LEFT JOIN ntis.ntis_building_ntrs AS ntrs "//
                    + "        ON ntrs.bn_ad_id = addr.address_id"//
                    + "     WHERE addr.address_id = ?::int LIMIT 1 ");
            stmt.addSelectParam(Utils.getDouble(result.getWtf_ad_id()));

            List<AddressSearchResponse> queryResult = queryController.selectQueryAsObjectArrayList(conn, stmt, AddressSearchResponse.class);

            formData.setFacilityLocationAddr(queryResult.get(0));

            if (!compareWtfCoordinates(firstCoordinates, secondCoordinates)) {

                AddressSearchResponse dischargeWastewaterAddr = new AddressSearchResponse();
                dischargeWastewaterAddr.setLatitude(result.getWtf_discharge_latitude());
                dischargeWastewaterAddr.setLongitude(result.getWtf_discharge_longitude());
                formData.setDischargeWastewaterAddr(dischargeWastewaterAddr);
            } else {
                formData.setDischargeWastewaterAddr(queryResult.get(0));
            }

        } else {
            AddressSearchResponse facilityLocationAddr = new AddressSearchResponse();
            facilityLocationAddr.setLatitude(result.getWtf_facility_latitude());
            facilityLocationAddr.setLongitude(result.getWtf_facility_longitude());
            formData.setFacilityLocationAddr(facilityLocationAddr);

            AddressSearchResponse dischargeWastewaterAddr = new AddressSearchResponse();
            dischargeWastewaterAddr.setLatitude(result.getWtf_discharge_latitude());
            dischargeWastewaterAddr.setLongitude(result.getWtf_discharge_longitude());
            formData.setDischargeWastewaterAddr(dischargeWastewaterAddr);
        }

        if (result.getWtf_id() != null) {
            StatementAndParams stmt = new StatementAndParams();
            stmt.setStatement("""
                          SELECT
                           addr.address_id,
                           addr.full_address_text,
                           ntrs.bn_id AS ntr_building_id,
                           ntrs.bn_pask_name AS purpose_of_building,
                           ntrs.bn_coordinate_latitude_adr AS latitude,
                           ntrs.bn_coordinate_longitude_adr AS longitude,
                           ntrs.bn_obj_inv_code AS inv_code
                          FROM ntis.ntis_building_ntrs AS ntrs
                    INNER JOIN ntis.ntis_address_vw AS addr
                            ON addr.address_id = ntrs.bn_ad_id
                         WHERE ntrs.bn_id in (SELECT so_bn_id FROM ntis_served_objects WHERE so_wtf_id = ?::int)
                          """);

            stmt.addSelectParam(result.getWtf_id());
            addressData.addAll(queryController.selectQueryAsObjectArrayList(conn, stmt, AddressSearchResponse.class));
            formData.setServedObjectsAddr(addressData);
        }

        if (formData.getWtf_fam_id() != null) {
            ArrayList<SprFile> filesAttachments = new ArrayList<>();

            StatementAndParams stmt = new StatementAndParams();
            stmt.setStatement("SELECT * FROM ntis_facility_files WHERE ff_wtf_id = ?::int");
            stmt.addSelectParam(Utils.getDouble(recordIdentifier.getId()));

            ArrayList<NtisFacilityFilesDAO> facilityFilesResult = (ArrayList<NtisFacilityFilesDAO>) queryController.selectQueryAsObjectArrayList(conn, stmt,
                    NtisFacilityFilesDAO.class);

            for (

            NtisFacilityFilesDAO fileRecord : facilityFilesResult) {
                if (fileRecord.getFf_fil_id() != null) {
                    filesAttachments.addAll(ntisWastewaterTreatmentFaciDBService.getFile(conn, fileRecord.getFf_fil_id()));
                }
            }
            formData.setAttachments(filesAttachments);
        }

        if (formData.getWtf_model() != null) {
            addFacilityAdditionalDetails(conn, formData.getWtf_model(), formData);
        }
        // if(result.getWtf_fam_id() != null) {
        // addFacilityModel(conn, result.getWtf_fam_id(), formData);
        // }

        return formData;

    }

    private NtisWastewaterTreatmentFaci getFacilityNotConfirmed(Connection conn, RecordIdentifier recordIdentifier, NtisWastewaterTreatmentFaciDAO result)
            throws Exception {

        NtisWastewaterTreatmentFaci formData = new NtisWastewaterTreatmentFaci();
        List<AddressSearchResponse> addressData = new ArrayList<>();
        formData.setWtf_data_source(result.getWtf_data_source());
        formData.setWtf_capacity(result.getWtf_nc_capacity());
        formData.setWtf_distance(result.getWtf_nc_distance());
        formData.setWtf_id(result.getWtf_id().intValue());
        formData.setWtf_installation_date(result.getWtf_nc_installation_date());
        formData.setWtf_checkout_date(result.getWtf_nc_checkout_date());
        formData.setWtf_manufacturer(result.getWtf_nc_manufacturer());
        formData.setWtf_manufacturer_description(result.getWtf_nc_manufacturer_description());
        formData.setWtf_model(result.getWtf_nc_model());
        formData.setWtf_state(result.getWtf_state());
        formData.setWtf_technical_passport_id(result.getWtf_nc_technical_passport_id());
        formData.setWtf_type(result.getWtf_nc_type());
        formData.setWtf_discharge_type(result.getWtf_nc_discharge_type());
        formData.setFam_bds(result.getWtf_nc_fam_bds());
        formData.setFam_chds(result.getWtf_nc_fam_chds());
        formData.setFam_float_material(result.getWtf_nc_fam_float_material());
        formData.setFam_phosphor(result.getWtf_nc_fam_phosphor());
        formData.setFam_nitrogen(result.getWtf_nc_fam_nitrogen());
        formData.setFam_pop_equivalent(result.getWtf_nc_fam_pop_equivalent());
        formData.setFam_manufacturer(result.getWtf_nc_fam_manufacturer());
        formData.setFam_description(result.getWtf_nc_fam_description());
        formData.setFam_model(result.getWtf_nc_fam_model());
        formData.setWtf_facility_latitude(result.getWtf_nc_facility_latitude());
        formData.setWtf_facility_longitude(result.getWtf_nc_facility_longitude());
        formData.setWtf_discharge_latitude(result.getWtf_nc_discharge_latitude());
        formData.setWtf_discharge_longitude(result.getWtf_nc_discharge_longitude());
        formData.setWtf_identification_number(result.getC03());

        ArrayList<Double> firstCoordinates = new ArrayList<>();
        firstCoordinates.clear();
        firstCoordinates.add(result.getWtf_nc_facility_latitude());
        firstCoordinates.add(result.getWtf_nc_facility_longitude());

        ArrayList<Double> secondCoordinates = new ArrayList<>();
        secondCoordinates.clear();
        secondCoordinates.add(result.getWtf_nc_discharge_latitude());
        secondCoordinates.add(result.getWtf_nc_discharge_longitude());

        if (result.getWtf_nc_ad_id() != null) {

            StatementAndParams stmt = new StatementAndParams();
            stmt.setStatement("SELECT "//
                    + "       addr.full_address_text, "//
                    + "       addr.address_id, "//
                    + "       addr.latitude, "//
                    + "       addr.longitude, "//
                    + "       ntrs.bn_obj_inv_code AS inv_code, "//
                    + "       ntrs.bn_pask_name AS purpose_of_building, "//
                    + "       ntrs.bn_id AS ntr_building_id "//
                    + "      FROM ntis.ntis_address_vw AS addr "//
                    + " LEFT JOIN ntis.ntis_building_ntrs AS ntrs "//
                    + "        ON ntrs.bn_ad_id = addr.address_id"//
                    + "     WHERE addr.address_id = ?::int LIMIT 1");
            stmt.addSelectParam(Utils.getDouble(result.getWtf_nc_ad_id()));

            List<AddressSearchResponse> queryResult = queryController.selectQueryAsObjectArrayList(conn, stmt, AddressSearchResponse.class);

            formData.setFacilityLocationAddr(queryResult.get(0));

            if (!compareWtfCoordinates(firstCoordinates, secondCoordinates)) {

                AddressSearchResponse dischargeWastewaterAddr = new AddressSearchResponse();
                dischargeWastewaterAddr.setLatitude(result.getWtf_nc_discharge_latitude());
                dischargeWastewaterAddr.setLongitude(result.getWtf_nc_discharge_longitude());
                formData.setDischargeWastewaterAddr(dischargeWastewaterAddr);
            } else {
                formData.setDischargeWastewaterAddr(queryResult.get(0));
            }

        } else {
            AddressSearchResponse facilityLocationAddr = new AddressSearchResponse();
            facilityLocationAddr.setLatitude(result.getWtf_nc_facility_latitude());
            facilityLocationAddr.setLongitude(result.getWtf_nc_facility_longitude());
            formData.setFacilityLocationAddr(facilityLocationAddr);

            AddressSearchResponse dischargeWastewaterAddr = new AddressSearchResponse();
            dischargeWastewaterAddr.setLatitude(result.getWtf_nc_discharge_latitude());
            dischargeWastewaterAddr.setLongitude(result.getWtf_nc_discharge_longitude());
            formData.setDischargeWastewaterAddr(dischargeWastewaterAddr);
        }

        if (result.getWtf_id() != null) {
            StatementAndParams stmt = new StatementAndParams();
            stmt.setStatement("""
                          SELECT
                           addr.address_id,
                           addr.full_address_text,
                           ntrs.bn_id AS ntr_building_id,
                           ntrs.bn_pask_name AS purpose_of_building,
                           ntrs.bn_coordinate_latitude_adr AS latitude,
                           ntrs.bn_coordinate_longitude_adr AS longitude,
                           ntrs.bn_obj_inv_code AS inv_code
                          FROM ntis.ntis_building_ntrs AS ntrs
                    INNER JOIN ntis.ntis_address_vw AS addr
                            ON addr.address_id = ntrs.bn_ad_id
                         WHERE ntrs.bn_id in (SELECT sov_bn_id FROM ntis_served_objects_version WHERE sov_wtf_id = ?::int)
                          """);

            stmt.addSelectParam(result.getWtf_id());
            addressData.addAll(queryController.selectQueryAsObjectArrayList(conn, stmt, AddressSearchResponse.class));
            formData.setServedObjectsAddr(addressData);
        }

        if (formData.getWtf_model() != null) {
            addFacilityAdditionalDetails(conn, formData.getWtf_model(), formData);
        }

        return formData;

    }

    public Double checkIdentificationNumber(Connection conn, String identificationNumber, Double orgId, Double perId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        Boolean queryByOwner = (!this.hasUserRole(conn, NtisRolesConstants.MUNICI_SPECIALIST) && !this.hasUserRole(conn, NtisRolesConstants.NTIS_ADMIN)
                && !this.hasUserRole(conn, NtisRolesConstants.INST_WORK) && !this.hasUserRole(conn, NtisRolesConstants.INST_ADMIN));
        if (queryByOwner) {
            StatementAndParams stmt = new StatementAndParams("""
                    select wtf.wtf_id
                    from ntis_wastewater_treatment_faci wtf
                    left join ntis_facility_owners on wtf.wtf_id = fo_wtf_id
                    where wtf.wtf_state <> 'CLOSED'
                    and wtf.c02 = ?
                    """);
            stmt.addSelectParam(identificationNumber);
            stmt.setWhereExists(true);
            if (orgId != null) {
                stmt.addParam4WherePart(" fo_org_id = ?::int ", orgId);
            } else if (perId != null) {
                stmt.addParam4WherePart(" fo_per_id = ?::int ", perId);
            }
            List<NtisWastewaterTreatmentFaciDAO> list = queryController.selectQueryAsObjectArrayList(conn, stmt, NtisWastewaterTreatmentFaciDAO.class);
            if (list != null && !list.isEmpty()) {
                return list.get(0).getWtf_id();
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    private void addFacilityAdditionalDetails(Connection conn, String rfcCode, NtisWastewaterTreatmentFaci facility) throws Exception {
        this.isFormActionAssigned(conn, FormBase.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams("""
                select fam_rfc_id as rfc_id,
                       fam_pop_equivalent,
                       fam_tech_pass,
                       fam_fil_id as fil_id,
                       fam_chds,
                       fam_bds,
                       fam_float_material,
                       fam_phosphor,
                       fam_nitrogen
                 from ntis_facility_model
                 left join spr_files on fam_fil_id = fil_id
                 inner join spr_ref_codes on rfc_id = fam_rfc_id and rfc_code = ? and rfc_domain = 'NTIS_FACIL_MODEL'
                 where current_date between rfc_date_from and coalesce(rfc_date_to, now())
                """);
        stmt.addSelectParam(rfcCode);
        List<FacilityModelAdditionalDetails> list = queryController.selectQueryAsObjectArrayList(conn, stmt, FacilityModelAdditionalDetails.class);
        if (list != null && list.size() > 0) {
            FacilityModelAdditionalDetails additionalDetails = list.get(0);
            if (additionalDetails != null && additionalDetails.getFil_id() != null) {
                SprFile passport = new SprFile();
                SprFilesDAO fileDAO = filesDBService.loadRecord(conn, Utils.getDouble(additionalDetails.getFil_id()));
                passport.setFil_content_type(fileDAO.getFil_content_type());
                passport.setFil_name(fileDAO.getFil_name());
                passport.setFil_size(fileDAO.getFil_size());
                passport.setFil_status(fileDAO.getFil_status());
                passport.setFil_status_date(fileDAO.getFil_status_date());
                passport.setFil_key(fileStorageService.encryptFileKey(fileDAO.getFil_key()));
                ArrayList<SprFile> file = new ArrayList<SprFile>();
                file.add(passport);
                facility.setAttachments(file);
            }
            if (additionalDetails != null) {
                facility.setWtf_technical_passport_id(additionalDetails.getFam_tech_pass());
                facility.setFam_bds(additionalDetails.getFam_bds());
                facility.setFam_chds(additionalDetails.getFam_chds());
                facility.setFam_float_material(additionalDetails.getFam_float_material());
                facility.setFam_nitrogen(additionalDetails.getFam_nitrogen());
                facility.setFam_phosphor(additionalDetails.getFam_phosphor());
                facility.setFam_pop_equivalent(additionalDetails.getFam_pop_equivalent());
                facility.setFam_rfc_id(additionalDetails.getRfc_id());
            }
        }
    }

    public List<AddressSearch> findAddressById(Connection conn, RecordIdentifier recordIdentifier) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisWastewaterFacilityEdit.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT
                municipality_code AS municipality,
                residence_code AS city,
                street_code AS street,
                building_no AS houseNr,
                flat_no AS flatNr,
                longitude,
                latitude
                FROM ntis.ntis_address_vw
                """);
        stmt.addParam4WherePart("address_id = ?::int ", Utils.getDouble(recordIdentifier.getId()));
        return queryController.selectQueryAsObjectArrayList(conn, stmt, AddressSearch.class);
    }

    public String detailedAddressSearch(Connection conn, AddressSearch params) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisWastewaterFacilityEdit.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();

        if (params.getImmovablePropertyNo() == null) {
            stmt = detailedAddressSearchDt(conn, params);
        } else {
            stmt = detailedAddressSearchNtr(conn, params);
        }

        return queryController.selectQueryAsJSON(conn, stmt);
    }

    public StatementAndParams detailedAddressSearchNtr(Connection conn, AddressSearch params) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisWastewaterFacilityEdit.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();

        stmt.setStatement("SELECT addr.* FROM ntis.ntis_building_ntrs bn JOIN ntis.ntis_address_vw AS addr ON addr.address_id = bn.bn_ad_id");
        stmt.addParam4WherePart("bn.bn_obj_inv_code = ? ", params.getImmovablePropertyNo());
        return stmt;
    }

    public StatementAndParams detailedAddressSearchDt(Connection conn, AddressSearch params) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisWastewaterFacilityEdit.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT ntrs.bn_id AS ntr_building_id, "//
                + "           addr.full_address_text, "//
                + "           addr.address_id, "//
                + "           addr.latitude, "//
                + "           addr.longitude, "//
                + "           ntrs.bn_pask_name AS purpose_of_building, "//
                + "           ntrs.bn_obj_inv_code AS inv_code "//
                + "  FROM ntis.ntis_adr_stats AS building " //
                + "  LEFT JOIN ntis.ntis_address_vw AS addr ON addr.building_id = building.ads_id "//
                + "  LEFT JOIN ntis.ntis_adr_pat_lrs AS flat ON flat.apl_id = addr.flat_id "//
                + "  LEFT JOIN ntis.ntis_building_ntrs AS ntrs ON ntrs.bn_ad_id = addr.address_id");
        if (params.getLatitude() == null || params.getLongitude() == null) {
            stmt.addParam4WherePart(" building.ads_municipality_code = ? ", params.getMunicipality());
            stmt.addParam4WherePart(" building.ads_residence_code = ? ", params.getCity());
            stmt.addParam4WherePart(" building.ads_street_code = ? ", params.getStreet());
            stmt.addParam4WherePart(" building.ads_nr = ? ", params.getHouseNr());
            stmt.addParam4WherePart(" flat.apl_pat_nr = ? ", params.getFlatNr());
        } else {
            stmt.addParam4WherePart(" building.ads_coordinate_latitude = ? ", params.getLatitude());
            stmt.addParam4WherePart(" building.ads_coordinate_longitude = ? ", params.getLongitude());
        }
        return stmt;
    }

    public List<NtisAdrResidencesDAO> getCitiesById(Connection conn, Double id) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisWastewaterFacilityEdit.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT re_id, "//
                + "re_recidence_code, " //
                + " re_municipality_code, " //
                + " CASE WHEN re_type_abbreviation = 'm.' THEN re_name " //
                + " ELSE re_name ||' '|| re_type_abbreviation END AS re_name " //
                + " FROM ntis_adr_residences " //
                + " WHERE re_municipality_code = ? ");
        stmt.addSelectParam(id);
        List<NtisAdrResidencesDAO> queryResult = queryController.selectQueryAsObjectArrayList(conn, stmt, NtisAdrResidencesDAO.class);
        return queryResult;

    }

    public List<NtisAdrStreetsDAO> getStreetsById(Connection conn, Double residenceCode) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisWastewaterFacilityEdit.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT str_id, "//
                + " str_street_code, " //
                + " str_residence_code, " //
                + " str_name ||' '|| coalesce( str_type_abbreviation , '' ) AS str_name " //
                + " FROM ntis_adr_streets " //
                + " WHERE str_residence_code = ? ");
        stmt.addSelectParam(residenceCode);
        List<NtisAdrStreetsDAO> queryResult = queryController.selectQueryAsObjectArrayList(conn, stmt, NtisAdrStreetsDAO.class);

        return queryResult;
    }

    public List<AddressSearchResponse> findAddress(Connection conn, ForeignKeyParams foreignKeyParam, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisWastewaterFacilityEdit.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT "//
                + "           addr.full_address_text, "//
                + "           addr.address_id, "//
                + "           addr.latitude, "//
                + "           addr.longitude "//
                + "      FROM ( select full_address_text,address_id,latitude, longitude,ad_address_search "//
                + "               from ntis.ntis_address_vw "//
                + "              WHERE ad_address_search like '%'||?||'%' limit 200 ) AS addr "//
                + "     ");
        stmt.addSelectParam(Utils.replaceNationalCharacters(foreignKeyParam.getFilterValue().toLowerCase().trim()));
        stmt.setStatementOrderPart(dbStatementManager.colNamesToConcatString("ad_address_search"));
        return queryController.selectQueryAsObjectArrayList(conn, stmt, AddressSearchResponse.class);
    }

    public List<AddressSearchResponse> findNtrAddress(Connection conn, ForeignKeyParams foreignKeyParam, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisWastewaterFacilityEdit.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                    SELECT NTRS.BN_ID AS NTR_BUILDING_ID,
                                    ADDR.FULL_ADDRESS_TEXT,
                                    ADDR.ADDRESS_ID,
                                    ADDR.LATITUDE,
                                    ADDR.LONGITUDE,
                                    NTRS.BN_PASK_NAME AS PURPOSE_OF_BUILDING,
                                    NTRS.BN_OBJ_INV_CODE AS INV_CODE
                                FROM
                                    (SELECT FULL_ADDRESS_TEXT,
                                            ADDRESS_ID,
                                            LATITUDE,
                                            LONGITUDE,
                                            ad_address_search
                                        FROM NTIS.NTIS_ADDRESS_VW
                                        WHERE AD_ADDRESS_SEARCH like '%' || ? || '%'
                                      limit 200) AS ADDR
                INNER JOIN NTIS.NTIS_BUILDING_NTRS AS NTRS ON NTRS.BN_AD_ID = ADDR.ADDRESS_ID
                """);

        stmt.addSelectParam(Utils.replaceNationalCharacters(foreignKeyParam.getFilterValue().toLowerCase().trim()));
        stmt.setStatementOrderPart(dbStatementManager.colNamesToConcatString("ad_address_search"));
        return queryController.selectQueryAsObjectArrayList(conn, stmt, AddressSearchResponse.class);
    }

    public ArrayList<NtrOwnerModel> getFacilityOwner(Connection conn, Double addressId, Double wtfId, Double soId, Double ntrId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();

        stmt.setStatement("SELECT CASE "//
                + "   WHEN fac_owner.fo_id IS NOT NULL OR org_owner.fo_org_id IS NOT NULL THEN '" + YesNo.Y.getCode() + "' "//
                + "   ELSE '" + YesNo.N.getCode() + "' "//
                + " END AS owner_exists, "//
                + " owners.bno_id AS owner_id, "//
                + " org.org_id AS organization_id, "//
                + " per.per_id AS person_id, "//
                + " owners.bno_code AS owner_code, "//
                + " owners.bno_type AS owner_type, "//
                + " owners.bno_org_name AS owner_organization_name, "//
                + " owners.bno_name AS owner_name, "//
                + " owners.bno_lastname AS owner_lastname "//
                + " FROM ntis.ntis_building_ntrs AS ntr "//
                + " JOIN ntis.ntis_building_ntr_owners AS owners "//
                + " ON owners.bno_bn_id = ntr.bn_id "//
                + " LEFT JOIN spark.spr_persons AS per "//
                + " ON per.per_code = owners.bno_code "//
                + " AND owners.bno_type = '" + NtisBuildingOwnerType.F.getCode() + "' "//
                + " LEFT JOIN spark.spr_organizations AS org "//
                + " ON org.org_code = owners.bno_code "//
                + " AND owners.bno_type = '" + NtisBuildingOwnerType.J.getCode() + "' "//
                + " LEFT JOIN ntis.ntis_facility_owners AS fac_owner "//
                + " ON fac_owner.fo_per_id = per.per_id and fac_owner.fo_wtf_id = ?::int and fac_owner.fo_so_id = ?::int "//
                + " AND fac_owner.fo_owner_type in ('OWNER','MANAGER')   and current_date between fac_owner.fo_date_from and COALESCE(fac_owner.fo_date_to, current_date) "//
                + " LEFT JOIN ntis.ntis_facility_owners AS org_owner "//
                + " ON org_owner.fo_org_id = org.org_id and org_owner.fo_wtf_id = ?::int and fac_owner.fo_so_id = ?::int "//
                + " AND org_owner.fo_owner_type in ('OWNER','MANAGER') "//
                + " WHERE ntr.bn_ad_id = ?::int AND ntr.bn_id = ?::int");
        stmt.addSelectParam(wtfId);
        stmt.addSelectParam(soId);
        stmt.addSelectParam(wtfId);
        stmt.addSelectParam(soId);
        stmt.addSelectParam(addressId);
        stmt.addSelectParam(ntrId);
        ArrayList<NtrOwnerModel> ntrOwnerResult = (ArrayList<NtrOwnerModel>) queryController.selectQueryAsObjectArrayList(conn, stmt, NtrOwnerModel.class);

        for (NtrOwnerModel ntrOwner : ntrOwnerResult) {

            if (NtisBuildingOwnerType.F.getCode().equals(ntrOwner.getOwner_type()) && ntrOwner.getPerson_id() == null) {
                SprPersonsDAO personData = sprPersonsDBService.newRecord();
                personData.setPer_code(ntrOwner.getOwner_code());
                personData.setPer_name(ntrOwner.getOwner_name());
                personData.setPer_surname(ntrOwner.getOwner_lastname());
                personData.setPer_code_exists(YesNo.Y.getCode());
                personData.setPer_date_of_birth(Utils.getDate());
                personData.setPer_lrt_resident(YesNo.Y.getCode());
                sprPersonsDBService.saveRecord(conn, personData);
                personData = sprPersonsDBService.saveRecord(conn, personData);
                ntrOwner.setPerson_id(personData.getPer_id());
            }

            if (NtisBuildingOwnerType.J.getCode().equals(ntrOwner.getOwner_type()) && ntrOwner.getOrganization_id() == null) {
                SprOrganizationsDAO organizationsData = organizationsDBService.newRecord();
                organizationsData.setOrg_code(ntrOwner.getOwner_code());
                organizationsData.setOrg_name(ntrOwner.getOwner_organization_name());
                organizationsData.setOrg_date_from(Utils.getDate());
                organizationsData = organizationsDBService.saveRecord(conn, organizationsData);
                ntrOwner.setOrganization_id(organizationsData.getOrg_id());
            }
        }
        return ntrOwnerResult;

    }

    private void manageFacilityFormFiles(Connection conn, NtisWastewaterTreatmentFaci record, Double wtfId) throws Exception {

        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT * FROM ntis_facility_files WHERE ff_wtf_id = ?::int");
        stmt.addSelectParam(Utils.getDouble(record.getWtf_id()));

        ArrayList<NtisFacilityFilesDAO> facilityFilesResult = (ArrayList<NtisFacilityFilesDAO>) queryController.selectQueryAsObjectArrayList(conn, stmt,
                NtisFacilityFilesDAO.class);

        if (record.getAttachments() != null) {
            ArrayList<NtisFacilityFilesDAO> fileToDelete = new ArrayList<>();
            ArrayList<SprFilesDAO> fileToInsert = new ArrayList<>();
            for (NtisFacilityFilesDAO fileRecord : facilityFilesResult) {
                boolean recordMatches = false;
                for (SprFile fileAttachments : record.getAttachments()) {
                    SprFilesDAO fileData = filesDBService.loadRecordByKey(conn, fileAttachments.getFil_key());
                    if (fileData != null) {
                        if (Double.compare(fileRecord.getFf_fil_id(), fileData.getFil_id()) == 0) {
                            recordMatches = true;
                            break;
                        }
                    }
                }
                if (!recordMatches) {
                    fileToDelete.add(fileRecord);
                }
            }

            for (SprFile fileAttachments : record.getAttachments()) {
                boolean recordMatches = false;
                for (NtisFacilityFilesDAO fileRecord : facilityFilesResult) {
                    SprFilesDAO fileData = filesDBService.loadRecordByKey(conn, fileAttachments.getFil_key());
                    if (fileData != null) {
                        if (Double.compare(fileRecord.getFf_fil_id(), fileData.getFil_id()) == 0) {
                            recordMatches = true;
                            break;
                        }
                    }
                }

                if (!recordMatches) {
                    fileToInsert.add(filesDBService.loadRecordByKey(conn, fileStorageService.decryptFileKey(fileAttachments.getFil_key())));
                }
            }

            for (SprFilesDAO file : fileToInsert) {
                NtisFacilityFilesDAO newRecord = new NtisFacilityFilesDAO();
                newRecord.setFf_fil_id(file.getFil_id());
                newRecord.setFf_wtf_id(Utils.getDouble(wtfId));
                ntisFacilityFilesDBService.insertRecord(conn, newRecord);
            }

            for (NtisFacilityFilesDAO file : fileToDelete) {
                filesDBService.markAsDeleted(conn, file.getFf_fil_id());
                ntisFacilityFilesDBService.deleteRecord(conn, file.getFf_id());
            }

        }

    }

    public void manageFacilityOwners(Connection conn, NtisServedObjectsDAO servedObj, Double wtfId, Double orgId, Double perId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisWastewaterFacilityEdit.ACTION_CREATE);
        ArrayList<NtrOwnerModel> facilityOwnerData = getFacilityOwner(conn, servedObj.getSo_ad_id(), wtfId, servedObj.getSo_id(), servedObj.getSo_bn_id());

        NtisFacilityOwnersDAO ntisFacilityOwnersDAO;
        int editorType = orgId != null ? NEW_ORG : NEW_PERSON;
        boolean editorExistInOwnerList = false;

        for (NtrOwnerModel facilityOwner : facilityOwnerData) {

            if (YesNo.N.getCode().equals(facilityOwner.getOwner_exists())) {
                ntisFacilityOwnersDAO = ntisFacilityOwnersDBService.newRecord();
                ntisFacilityOwnersDAO.setFo_owner_type(NtisFacilityOwnerType.OWNER.getCode());
                ntisFacilityOwnersDAO.setFo_date_from(Utils.getDate());
                ntisFacilityOwnersDAO.setFo_wtf_id(wtfId);
                ntisFacilityOwnersDAO.setFo_so_id(servedObj.getSo_id());

                if (NtisBuildingOwnerType.J.getCode().equals(facilityOwner.getOwner_type())) {
                    ntisFacilityOwnersDAO.setFo_org_id(facilityOwner.getOrganization_id());
                } else {
                    ntisFacilityOwnersDAO.setFo_per_id(facilityOwner.getPerson_id());
                }

                ntisFacilityOwnersDBService.saveRecord(conn, ntisFacilityOwnersDAO);
            }
            if (editorType == NEW_ORG) {
                if (NtisBuildingOwnerType.J.getCode().equals(facilityOwner.getOwner_type())) {
                    if (facilityOwner.getOrganization_id().equals(orgId)) {
                        editorExistInOwnerList = true;
                    }
                }
            } else {
                if (NtisBuildingOwnerType.F.getCode().equals(facilityOwner.getOwner_type())) {
                    if (facilityOwner.getPerson_id().equals(perId)) {
                        editorExistInOwnerList = true;
                    }
                }
            }

        }
        if (!editorExistInOwnerList) {
            ntisFacilityOwnersDAO = ntisFacilityOwnersDBService.newRecord();
            ntisFacilityOwnersDAO.setFo_date_from(Utils.getDate());
            ntisFacilityOwnersDAO.setFo_wtf_id(wtfId);
            ntisFacilityOwnersDAO.setFo_so_id(servedObj.getSo_id());

            if (editorType == NEW_ORG) {
                ntisFacilityOwnersDAO.setFo_org_id(orgId);
                if (this.hasUserRole(conn, NtisRolesConstants.INTS_OWNER)) {
                    ntisFacilityOwnersDAO.setFo_owner_type(NtisFacilityOwnerType.SELF_ASSIGNED.getCode());
                } else {
                    ntisFacilityOwnersDAO.setFo_owner_type(NtisFacilityOwnerType.SERVICE_PROVIDER.getCode());
                }
            } else if (editorType == NEW_PERSON) {
                ntisFacilityOwnersDAO.setFo_per_id(perId);
                ntisFacilityOwnersDAO.setFo_owner_type(NtisFacilityOwnerType.SELF_ASSIGNED.getCode());
            }

            ntisFacilityOwnersDBService.saveRecord(conn, ntisFacilityOwnersDAO);
        }
    }

    public NtisWastewaterTreatmentFaci save(Connection conn, NtisWastewaterTreatmentFaci record, Double orgId, Double perId, Double userId) throws Exception {
        this.checkIsFormActionAssigned(conn, record.getWtf_id() == null ? NtisWastewaterFacilityEdit.ACTION_CREATE : NtisWastewaterFacilityEdit.ACTION_UPDATE);

        NtisWastewaterTreatmentFaciDAO facilityForm = null;
        NtisSelectedFacilitiesDAO selectedFacilities = null;
        NtisFacilityUpdateLogDAO facilityUpdateLog = null;
        NtisWastewaterTreatmentFaciDAO facilityResult = null;
        Boolean queryByOwner = (!this.hasUserRole(conn, NtisRolesConstants.MUNICI_SPECIALIST) && !this.hasUserRole(conn, NtisRolesConstants.NTIS_ADMIN)
                && !this.hasUserRole(conn, NtisRolesConstants.INST_WORK) && !this.hasUserRole(conn, NtisRolesConstants.INST_ADMIN)
                && !this.hasUserRole(conn, NtisRolesConstants.VAND_ADMIN) && !this.hasUserRole(conn, NtisRolesConstants.CAR_SPECIALIST)
                && !this.hasUserRole(conn, NtisRolesConstants.PASL_ADMIN));
        if (record.getWtf_id() != null) {
            if (queryByOwner) {
                Double wtfId;
                if (orgId != null) {
                    wtfId = ntisFacilityOwnersDBService.loadRecordByParams(conn, " WHERE fo_org_id = ?::int AND fo_wtf_id = ?::int ",
                            new SelectParamValue(orgId), new SelectParamValue(Utils.getDouble(record.getWtf_id()))).getFo_wtf_id();
                } else {
                    wtfId = ntisFacilityOwnersDBService.loadRecordByParams(conn, " WHERE fo_per_id = ?::int AND fo_wtf_id = ?::int ",
                            new SelectParamValue(perId), new SelectParamValue(Utils.getDouble(record.getWtf_id()))).getFo_wtf_id();
                }
                facilityForm = ntisWastewaterTreatmentFaciDBService.loadRecord(conn, wtfId);
            } else {
                facilityForm = ntisWastewaterTreatmentFaciDBService.loadRecord(conn, Utils.getDouble(record.getWtf_id()));
            }
        } else {
            facilityForm = ntisWastewaterTreatmentFaciDBService.newRecord();
        }

        ArrayList<Double> firstCoordinates = new ArrayList<>();
        firstCoordinates.add(record.getWtf_facility_latitude());
        firstCoordinates.add(record.getWtf_facility_longitude());

        ArrayList<Double> secondCoordinates = new ArrayList<>();
        secondCoordinates.add(facilityForm.getWtf_facility_latitude());
        secondCoordinates.add(facilityForm.getWtf_facility_longitude());

        if (record.getWtf_id() != null && record.getWtf_checkout_date() != null) {
            facilityForm.setWtf_state(NtisFacilityStatus.CLOSED.getCode());
        } else if (record.getWtf_id() == null) {
            facilityForm.setWtf_state(
                    this.isFormActionAssigned(conn, CREATE_OFFICIAL_ACTION) ? NtisFacilityStatus.CONFIRMED.getCode() : NtisFacilityStatus.REGISTERED.getCode());
        }
        var updateAgreement = ntisFacilityUpdateAgreementDBService.newRecord();
        // START confirmed record version
        if (facilityForm.getWtf_state().equals(NtisFacilityStatus.CONFIRMED.getCode()) && facilityForm.getWtf_id() != null
                && (hasUserRole(conn, NtisRolesConstants.INTS_OWNER))) {

            if (facilityForm.getWtf_waiting_update_confirmation() == null || "N".equals(facilityForm.getWtf_waiting_update_confirmation())) {
                facilityForm.setWtf_waiting_update_confirmation("Y");

                updateAgreement.setFua_per_id(perId);
                updateAgreement.setFua_req_org_id(orgId);
                updateAgreement.setFua_wtf_id(facilityForm.getWtf_id());
                updateAgreement.setFua_state(FacilityUpdateAgreementConstants.SUBMITTED);
                updateAgreement.setFua_type("WASTEWATER");
                updateAgreement.setFua_created(new Date());
                updateAgreement.setFua_wtf_old_info_json(ntisWastewaterFacilityView.getTreatmentFacility(conn, facilityForm.getWtf_id(), "lt", orgId, perId));
                ntisFacilityUpdateAgreementDBService.saveRecord(conn, updateAgreement);
            }
            facilityForm.setWtf_nc_type(record.getWtf_type());
            facilityForm.setWtf_nc_capacity(record.getWtf_capacity());
            facilityForm.setWtf_nc_installation_date(record.getWtf_installation_date());
            facilityForm.setWtf_nc_checkout_date(record.getWtf_checkout_date());
            facilityForm.setWtf_nc_manufacturer(record.getWtf_manufacturer());
            facilityForm.setWtf_nc_manufacturer_description(record.getWtf_manufacturer_description());
            facilityForm.setWtf_nc_distance(Utils.getDouble(record.getWtf_distance()));
            facilityForm.setWtf_nc_model(record.getWtf_model());
            facilityForm.setWtf_nc_deleted(YesNo.N.getCode());
            facilityForm.setWtf_nc_data_source(record.getWtf_data_source() != null ? record.getWtf_data_source() :  NtisFacilityRegType.MANUALLY.getCode());
            facilityForm.setWtf_nc_technical_passport_id(record.getWtf_technical_passport_id());
            facilityForm.setWtf_nc_facility_latitude(record.getWtf_facility_latitude());
            facilityForm.setWtf_nc_facility_longitude(record.getWtf_facility_longitude());
            facilityForm.setWtf_nc_discharge_latitude(record.getWtf_discharge_latitude());
            facilityForm.setWtf_nc_discharge_longitude(record.getWtf_discharge_longitude());
            facilityForm.setWtf_nc_discharge_type(record.getWtf_discharge_type());
            facilityForm.setWtf_nc_ad_id(record.getWtf_ad_id());
            facilityForm.setWtf_nc_fam_manufacturer(record.getFam_manufacturer());
            facilityForm.setWtf_nc_fam_description(record.getFam_description());
            facilityForm.setWtf_nc_fam_model(record.getFam_model());
            facilityForm.setWtf_nc_fam_tech_pass(record.getWtf_technical_passport_id());
            facilityForm.setWtf_nc_fam_pop_equivalent(record.getFam_pop_equivalent());
            facilityForm.setWtf_nc_fam_chds(record.getFam_chds());
            facilityForm.setWtf_nc_fam_bds(record.getFam_bds());
            facilityForm.setWtf_nc_fam_chds(record.getFam_chds());
            facilityForm.setWtf_nc_fam_float_material(record.getFam_float_material());
            facilityForm.setWtf_nc_fam_phosphor(record.getFam_phosphor());
            facilityForm.setWtf_nc_fam_nitrogen(record.getFam_nitrogen());
            if (record.getWtf_type().equalsIgnoreCase(NtisWtfType.PORTABLE_RESERVOIR.getCode())) {
                facilityForm.setC03(record.getWtf_identification_number());
            } else {
                facilityForm.setC02(null);
            }

            if (compareWtfCoordinates(firstCoordinates, secondCoordinates) == false || facilityForm.getWtf_facility_municipality_code() == null) {
                NtisMunicipalitiesDAO municipalityResult = ntisMunicipalitiesDBService.loadRecordByParams(conn,
                        "WHERE st_contains(mp_geom, ST_SetSRID(ST_MakePoint(?,?), 3346))", //
                        new SelectParamValue(record.getWtf_facility_latitude()), //
                        new SelectParamValue(record.getWtf_facility_longitude())); //
                facilityForm.setWtf_nc_facility_municipality_code(municipalityResult.getMp_code());
            }

            facilityResult = ntisWastewaterTreatmentFaciDBService.saveRecord(conn, facilityForm);
            if (updateAgreement.getFua_id() == null) {
                updateAgreement = ntisFacilityUpdateAgreementDBService.loadRecordByParams(conn, "WHERE fua_wtf_id =?::int and fua_state = ? ", //
                        new SelectParamValue(facilityResult.getWtf_id()), //
                        new SelectParamValue(FacilityUpdateAgreementConstants.SUBMITTED)); //
            }

            ntisServedObjectsVersionDBService.deleteAllRecords(conn, facilityForm.getWtf_id());
            if (record.getServedObjects() != null && !record.getServedObjects().isEmpty()
                    && !record.getWtf_type().equalsIgnoreCase(NtisWtfType.PORTABLE_RESERVOIR.getCode())) {

                for (NtisServedObjectsDAO servedObjectrecord : record.getServedObjects()) {
                    NtisServedObjectsVersionDAO sovRec = ntisServedObjectsVersionDBService.newRecord();
                    sovRec.setSov_wtf_id(facilityResult.getWtf_id());
                    sovRec.setN01(servedObjectrecord.getSo_id());
                    sovRec.setSov_coordinate_latitude(servedObjectrecord.getSo_coordinate_latitude());
                    sovRec.setSov_coordinate_longitude(servedObjectrecord.getSo_coordinate_longitude());
                    sovRec.setSov_ad_id(servedObjectrecord.getSo_ad_id());
                    sovRec.setSov_bn_id(servedObjectrecord.getSo_bn_id());
                    sovRec.setSov_fua_id(updateAgreement.getFua_id());
                    ntisServedObjectsVersionDBService.saveRecord(conn, sovRec);
                }

            } else if (facilityResult.getWtf_type().equalsIgnoreCase(NtisWtfType.PORTABLE_RESERVOIR.getCode())
                    || (record.getServedObjects() == null || record.getServedObjects().isEmpty())) {
                Boolean queryByIntsOwner = (this.hasUserRole(conn, NtisRolesConstants.INTS_OWNER));
                StatementAndParams stmtOwners = new StatementAndParams();
                if (queryByOwner) {
                    if (queryByIntsOwner) {
                        stmtOwners.setStatement("""
                                select fo_id,
                                fo_owner_type
                                  from ntis_facility_owners
                                  where now() between fo_date_from and coalesce(fo_date_to, now())
                                  and fo_wtf_id = ?::int
                                  and fo_owner_type in ('SELF_ASSIGNED', 'OWNER')
                                """);
                        stmtOwners.setWhereExists(true);
                        if (orgId != null) {
                            stmtOwners.addParam4WherePart(" fo_org_id = ?::int ", orgId);
                        } else if (perId != null) {
                            stmtOwners.addParam4WherePart(" fo_per_id = ?::int ", perId);
                        }
                    } else {
                        stmtOwners.setStatement("""
                                select fo_id,
                                fo_owner_type
                                  from ntis_facility_owners
                                  where now() between fo_date_from and coalesce(fo_date_to, now())
                                  and fo_org_id = ?::int
                                  and fo_wtf_id = ?::int
                                  and fo_owner_type = 'SERVICE_PROVIDER'
                                """);
                        stmtOwners.addSelectParam(orgId);
                    }
                    stmtOwners.addSelectParam(facilityResult.getWtf_id());
                    ArrayList<NtisFacilityOwnersDAO> ownersResult = (ArrayList<NtisFacilityOwnersDAO>) queryController.selectQueryAsObjectArrayList(conn,
                            stmtOwners, NtisFacilityOwnersDAO.class);
                    if (ownersResult == null || ownersResult.isEmpty()) {
                        NtisFacilityOwnersDAO facilityOwner = ntisFacilityOwnersDBService.newRecord();
                        if (queryByIntsOwner) {
                            facilityOwner.setFo_owner_type(NtisFacilityOwnerType.SELF_ASSIGNED.getCode());
                        } else {
                            facilityOwner.setFo_owner_type(NtisFacilityOwnerType.SERVICE_PROVIDER.getCode());
                        }
                        facilityOwner.setFo_wtf_id(facilityResult.getWtf_id());
                        if (orgId != null) {
                            facilityOwner.setFo_org_id(orgId);
                        } else {
                            facilityOwner.setFo_per_id(perId);
                        }
                        facilityOwner.setFo_date_from(new Date());
                        ntisFacilityOwnersDBService.saveRecord(conn, facilityOwner);
                    }
                }

            }

            // END confirmed record version
        } else {
            facilityForm.setWtf_type(record.getWtf_type());
            facilityForm.setWtf_capacity(record.getWtf_capacity());
            facilityForm.setWtf_installation_date(record.getWtf_installation_date());
            facilityForm.setWtf_checkout_date(record.getWtf_checkout_date());
            facilityForm.setWtf_manufacturer(record.getWtf_manufacturer());
            facilityForm.setWtf_manufacturer_description(record.getWtf_manufacturer_description());
            facilityForm.setWtf_distance(Utils.getDouble(record.getWtf_distance()));
            facilityForm.setWtf_model(record.getWtf_model());
            facilityForm.setWtf_deleted(YesNo.N.getCode());
            facilityForm.setWtf_data_source(record.getWtf_data_source() != null ? record.getWtf_data_source() : NtisFacilityRegType.MANUALLY.getCode());
            facilityForm.setWtf_technical_passport_id(record.getWtf_technical_passport_id());
            facilityForm.setWtf_ad_id(record.getWtf_ad_id());
            facilityForm.setWtf_facility_latitude(record.getWtf_facility_latitude());
            facilityForm.setWtf_facility_longitude(record.getWtf_facility_longitude());
            if (record.getWtf_type().equalsIgnoreCase(NtisWtfType.PORTABLE_RESERVOIR.getCode())) {
                facilityForm.setC02(record.getWtf_identification_number());
            } else {
                facilityForm.setC02(null);
            }
            if (!record.getWtf_type().equalsIgnoreCase(NtisWtfType.RESERVOIR.getCode())
                    && !record.getWtf_type().equalsIgnoreCase(NtisWtfType.PORTABLE_RESERVOIR.getCode())) {
                facilityForm.setWtf_discharge_latitude(record.getWtf_discharge_latitude());
                facilityForm.setWtf_discharge_longitude(record.getWtf_discharge_longitude());
                facilityForm.setWtf_discharge_type(record.getWtf_discharge_type());
            }

            if (compareWtfCoordinates(firstCoordinates, secondCoordinates) == false || facilityForm.getWtf_facility_municipality_code() == null) {
                NtisMunicipalitiesDAO municipalityResult = ntisMunicipalitiesDBService.loadRecordByParams(conn,
                        "WHERE st_contains(mp_geom, ST_SetSRID(ST_MakePoint(?,?), 3346))", //
                        new SelectParamValue(record.getWtf_facility_latitude()), //
                        new SelectParamValue(record.getWtf_facility_longitude())); //
                facilityForm.setWtf_facility_municipality_code(municipalityResult.getMp_code());
            }

            facilityResult = ntisWastewaterTreatmentFaciDBService.saveRecord(conn, facilityForm);

            if (record.getWtf_manufacturer() != null && record.getWtf_manufacturer().equalsIgnoreCase("KITA")) {
                manageManufacturerData(conn, record, facilityResult.getWtf_id());
            } else {
                Double famId = facilityResult.getWtf_fam_id();
                facilityResult.setWtf_fam_id(null);
                ntisWastewaterTreatmentFaciDBService.saveRecord(conn, facilityResult);
                ntisFacilityModelDBService.deleteRecord(conn, famId);
            }

            if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.INTS_OWNER_ACTIONS)) {
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
                selectedFacility.setFs_wtf_id(facilityResult.getWtf_id());
                ntisSelectedFacilitiesDBService.saveRecord(conn, selectedFacility);
            }

            if (ntisSelectedFacilitiesDBService.getSelecteFacility(conn, userId, orgId) == null) {
                selectedFacilities = ntisSelectedFacilitiesDBService.newRecord();
                selectedFacilities.setFs_usr_id(userId);
                selectedFacilities.setFs_org_id(orgId);
                selectedFacilities.setFs_wtf_id(facilityResult.getWtf_id());
                ntisSelectedFacilitiesDBService.saveRecord(conn, selectedFacilities);
            }

            facilityUpdateLog = facilityUpdateLogDBService.newRecord();
            facilityUpdateLog.setFul_wtf_id(Utils.getDouble(record.getWtf_id() != null ? record.getWtf_id() : facilityResult.getWtf_id()));
            facilityUpdateLog.setFul_usr_id(userId);
            facilityUpdateLog.setFul_org_id(orgId);
            facilityUpdateLog.setFul_created(Utils.getDate());
            facilityUpdateLog.setFul_operation(record.getWtf_id() != null ? NtisQueryOperations.UPDATE.getCode() : NtisQueryOperations.INSERT.getCode());
            facilityUpdateLogDBService.saveRecord(conn, facilityUpdateLog);

            ArrayList<NtisServedObjectsDAO> ntisServedObjects = ntisServedObjectsDBService.getRecordByWtfId(conn, Utils.getDouble(record.getWtf_id()));

            if ((record.getServedObjects() == null || !record.getServedObjects().isEmpty())
                    && (record.getServedObjectsAddr() == null || record.getServedObjectsAddr().isEmpty())) {
                for (NtisServedObjectsDAO servedObject : ntisServedObjects) {
                    ntisFacilityOwnersDBService.deleteByServedObjectId(conn, servedObject.getSo_id());
                    ntisServedObjectsDBService.deleteRecord(conn, servedObject.getSo_id());
                    tilesDBService.updateOneWastewater(conn, servedObject.getSo_wtf_id(), TilesAction.DELETE);
                    tilesDBService.updateOneBuilding(conn, servedObject.getSo_bn_id(), TilesAction.DELETE);
                }

                tilesDBService.cleanBuildingsCache(conn);
                tilesDBService.cleanWastewaterCache(conn);
            }

            if (record.getServedObjects() != null && !record.getServedObjects().isEmpty()
                    && !record.getWtf_type().equalsIgnoreCase(NtisWtfType.PORTABLE_RESERVOIR.getCode())) {

                List<NtisServedObjectsDAO> recordsToDelete = new ArrayList<>();
                List<NtisServedObjectsDAO> recordsToInsert = new ArrayList<>();

                for (NtisServedObjectsDAO servedObject : ntisServedObjects) {
                    boolean recordMatches = false;
                    for (NtisServedObjectsDAO servedObjectrecord : record.getServedObjects()) {
                        if (servedObject.getSo_bn_id().equals(servedObjectrecord.getSo_bn_id())) {
                            recordMatches = true;
                            break;
                        }
                    }
                    if (!recordMatches) {
                        recordsToDelete.add(servedObject);
                    }
                }

                for (NtisServedObjectsDAO servedObjectrecord : record.getServedObjects()) {
                    boolean recordMatches = false;
                    for (NtisServedObjectsDAO servedObject : ntisServedObjects) {
                        if (servedObject.getSo_bn_id().equals(servedObjectrecord.getSo_bn_id())) {
                            recordMatches = true;
                            break;
                        }
                    }
                    if (!recordMatches) {
                        servedObjectrecord.setSo_wtf_id(Utils.getDouble(record.getWtf_id()));
                        recordsToInsert.add(servedObjectrecord);
                    }
                }

                for (NtisServedObjectsDAO servedObject : recordsToDelete) {
                    ntisFacilityOwnersDBService.deleteByServedObjectId(conn, servedObject.getSo_id());
                    ntisServedObjectsDBService.deleteRecord(conn, servedObject.getSo_id());
                    tilesDBService.updateOneWastewater(conn, servedObject.getSo_wtf_id(), TilesAction.DELETE);
                    tilesDBService.updateOneBuilding(conn, servedObject.getSo_bn_id(), TilesAction.DELETE);
                }

                for (NtisServedObjectsDAO servedObject : recordsToInsert) {
                    servedObject.setSo_wtf_id(facilityResult.getWtf_id());
                    NtisServedObjectsDAO servedObj = ntisServedObjectsDBService.insertRecord(conn, servedObject);
                    manageFacilityOwners(conn, servedObj, facilityResult.getWtf_id(), orgId, perId);
                    tilesDBService.updateOneWastewater(conn, servedObject.getSo_wtf_id(), TilesAction.INSERT);
                    tilesDBService.updateOneBuilding(conn, servedObject.getSo_bn_id(), TilesAction.INSERT);
                }

                if (!recordsToDelete.isEmpty() || !recordsToInsert.isEmpty()) {
                    tilesDBService.cleanBuildingsCache(conn);
                    tilesDBService.cleanWastewaterCache(conn);
                }
            } else if (facilityResult.getWtf_type().equalsIgnoreCase(NtisWtfType.PORTABLE_RESERVOIR.getCode()) || record.getServedObjects() == null
                    || record.getServedObjects().isEmpty()) {
                StatementAndParams stmtOwners = new StatementAndParams();
                Boolean queryByIntsOwner = (this.hasUserRole(conn, NtisRolesConstants.INTS_OWNER));
                if (queryByOwner) {
                    if (queryByIntsOwner) {
                        stmtOwners.setStatement("""
                                select fo_id,
                                fo_owner_type
                                  from ntis_facility_owners
                                  where now() between fo_date_from and coalesce(fo_date_to, now())
                                  and fo_wtf_id = ?::int
                                  and fo_owner_type in ('SELF_ASSIGNED', 'OWNER')
                                """);
                        stmtOwners.setWhereExists(true);
                        if (orgId != null) {
                            stmtOwners.addParam4WherePart(" fo_org_id = ?::int ", orgId);
                        } else if (perId != null) {
                            stmtOwners.addParam4WherePart(" fo_per_id = ?::int ", perId);
                        }
                    } else {
                        stmtOwners.setStatement("""
                                select fo_id,
                                fo_owner_type
                                  from ntis_facility_owners
                                  where now() between fo_date_from and coalesce(fo_date_to, now())
                                  and fo_org_id = ?::int
                                  and fo_wtf_id = ?::int
                                  and fo_owner_type = 'SERVICE_PROVIDER'
                                """);
                        stmtOwners.addSelectParam(orgId);
                    }
                    stmtOwners.addSelectParam(facilityResult.getWtf_id());
                    ArrayList<NtisFacilityOwnersDAO> ownersResult = (ArrayList<NtisFacilityOwnersDAO>) queryController.selectQueryAsObjectArrayList(conn,
                            stmtOwners, NtisFacilityOwnersDAO.class);
                    if (ownersResult == null || ownersResult.isEmpty()) {
                        NtisFacilityOwnersDAO facilityOwner = ntisFacilityOwnersDBService.newRecord();
                        if (queryByIntsOwner) {
                            facilityOwner.setFo_owner_type(NtisFacilityOwnerType.SELF_ASSIGNED.getCode());
                        } else {
                            facilityOwner.setFo_owner_type(NtisFacilityOwnerType.SERVICE_PROVIDER.getCode());
                        }
                        facilityOwner.setFo_wtf_id(facilityResult.getWtf_id());
                        if (orgId != null) {
                            facilityOwner.setFo_org_id(orgId);
                        } else {
                            facilityOwner.setFo_per_id(perId);
                        }
                        facilityOwner.setFo_date_from(new Date());
                        ntisFacilityOwnersDBService.saveRecord(conn, facilityOwner);
                    }
                }
            }
        }

        if (record.getWtf_fam_id() != null) {
            manageFacilityFormFiles(conn, record, facilityResult.getWtf_id());
        }
        NtisWastewaterTreatmentFaci responsedata = new NtisWastewaterTreatmentFaci();
        responsedata.setWtf_id(facilityResult.getWtf_id().intValue());
        return responsedata;
    }

    private void manageManufacturerData(Connection conn, NtisWastewaterTreatmentFaci record, Double wtfId) throws Exception {
        NtisFacilityModelDAO facilityModelDAO = null;
        NtisWastewaterTreatmentFaciDAO facilityDAO = this.ntisWastewaterTreatmentFaciDBService.loadRecord(conn, wtfId);

        if (record.getWtf_fam_id() != null) {
            facilityModelDAO = this.ntisFacilityModelDBService.loadRecord(conn, record.getWtf_fam_id());
        } else if (facilityDAO != null && facilityDAO.getWtf_fam_id() != null) {
            facilityModelDAO = this.ntisFacilityModelDBService.loadRecord(conn, facilityDAO.getWtf_fam_id());
        } else {
            facilityModelDAO = this.ntisFacilityModelDBService.newRecord();
        }
        facilityModelDAO.setFam_pop_equivalent(record.getFam_pop_equivalent());
        facilityModelDAO.setFam_tech_pass(record.getWtf_technical_passport_id());
        facilityModelDAO.setFam_chds(record.getFam_chds());
        facilityModelDAO.setFam_bds(record.getFam_bds());
        facilityModelDAO.setFam_float_material(record.getFam_float_material());
        facilityModelDAO.setFam_phosphor(record.getFam_phosphor());
        facilityModelDAO.setFam_nitrogen(record.getFam_nitrogen());
        facilityModelDAO.setFam_manufacturer(record.getWtf_manufacturer_description());
        facilityModelDAO.setFam_model(record.getFam_model());
        facilityModelDAO.setFam_description(record.getFam_description());
        this.ntisFacilityModelDBService.saveRecord(conn, facilityModelDAO);
        facilityDAO.setWtf_fam_id(facilityModelDAO.getFam_id());
        this.ntisWastewaterTreatmentFaciDBService.saveRecord(conn, facilityDAO);
        record.setWtf_fam_id(facilityModelDAO.getFam_id());
        manageFacilityFormFiles(conn, record, wtfId);
    }

    public boolean compareCoordinates(NtisServedObjectsDAO firstObject, NtisServedObjectsDAO secondObject) {
        boolean recordMatches = false;
        if (firstObject.getSo_ad_id() != null && secondObject.getSo_ad_id() != null) {
            recordMatches = Double.compare(firstObject.getSo_ad_id(), secondObject.getSo_ad_id()) == 0
                    && Double.compare(firstObject.getSo_coordinate_latitude(), secondObject.getSo_coordinate_latitude()) == 0
                    && Double.compare(firstObject.getSo_coordinate_longitude(), secondObject.getSo_coordinate_longitude()) == 0;
        } else {
            recordMatches = Double.compare(firstObject.getSo_coordinate_latitude(), secondObject.getSo_coordinate_latitude()) == 0
                    && Double.compare(firstObject.getSo_coordinate_longitude(), secondObject.getSo_coordinate_longitude()) == 0;
        }
        return recordMatches;
    }

    public List<NtisFacilityModel> getFacilityModels(Connection conn, String code) throws Exception {
        this.isFormActionAssigned(conn, FormBase.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT rc.rfc_id, " + //
                "                 rc.rfc_code, " + //
                "                 rc.rfc_meaning " + //
                "            FROM spr_ref_codes rc " + //
                "           WHERE " + dbStatementManager.getPeriodValidationForCurrentDateStr("rc.rfc_date_from", "rc.rfc_date_to") + //
                "             AND  rc.rfc_ref_code_1 = ? ");
        stmt.addSelectParam(code);
        stmt.setStatementOrderPart("ORDER BY rc.rfc_order ASC");
        return queryController.selectQueryAsObjectArrayList(conn, stmt, new Data2ObjectProcessor<NtisFacilityModel>(NtisFacilityModel.class));
    }

    public FacilityModelAdditionalDetails getFacilityModelAdditionalDetails(Connection conn, Double rfcId) throws Exception {
        this.isFormActionAssigned(conn, FormBase.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams("""
                select fam_rfc_id as rfc_id,
                       fam_pop_equivalent,
                       fam_tech_pass,
                       fam_fil_id as fil_id,
                       fam_chds,
                       fam_bds,
                       fam_float_material,
                       fam_phosphor,
                       fam_nitrogen
                 from ntis_facility_model
                 left join spr_files on fam_fil_id = fil_id
                 where fam_rfc_id = ?::int
                """);

        stmt.addSelectParam(rfcId);
        List<FacilityModelAdditionalDetails> list = queryController.selectQueryAsObjectArrayList(conn, stmt, FacilityModelAdditionalDetails.class);
        if (list != null && list.size() > 0) {
            FacilityModelAdditionalDetails additionalDetails = list.get(0);
            if (additionalDetails != null && additionalDetails.getFil_id() != null) {
                SprFile passport = new SprFile();
                SprFilesDAO fileDAO = filesDBService.loadRecord(conn, Utils.getDouble(additionalDetails.getFil_id()));
                passport.setFil_content_type(fileDAO.getFil_content_type());
                passport.setFil_name(fileDAO.getFil_name());
                passport.setFil_size(fileDAO.getFil_size());
                passport.setFil_status(fileDAO.getFil_status());
                passport.setFil_status_date(fileDAO.getFil_status_date());
                passport.setFil_key(fileStorageService.encryptFileKey(fileDAO.getFil_key()));
                additionalDetails.setPassport(passport);
            }
            return additionalDetails;
        }
        return null;
    }

    /**
     * Metodas įrašys asmenį ar organizaciją į facility_owners lentą, kaip pateikto įrenginio savininką (atitinkamai SELF_ASSIGNED arba SERVICE_PROVIDER tipo).
     * Taip pat, jeigu naudotojas yra INTS savininkas, pateiktą įrenginį jam priskirs kaip numatytąjį.
     * @param conn - prisijungimas prie DB
     * @param record - NtisWastewaterTreatmentFaci objektas
     * @param usrId - sesijos naudotojo ID
     * @param orgId - sesijos organizacijos ID
     * @param perId - sesijos asmens ID
     * @return įrenginio ID
     * @throws Exception
     */
    public Double assignExistingFacility(Connection conn, NtisWastewaterTreatmentFaci record, Double usrId, Double orgId, Double perId) throws Exception {
        this.isFormActionAssigned(conn, FormBase.ACTION_UPDATE);
        NtisWastewaterTreatmentFaciDAO existingFacility = ntisWastewaterTreatmentFaciDBService.loadRecordByParams(conn, " wtf_ad_id = ?::int and wtf_type = ? ",
                new SelectParamValue(record.getWtf_ad_id()), new SelectParamValue(record.getWtf_type()));
        if (existingFacility == null) {
            existingFacility = ntisWastewaterTreatmentFaciDBService.loadRecordByParams(conn, " wtf_ad_id = ?::int  ",
                    new SelectParamValue(record.getWtf_ad_id()));
        }
        List<NtisServedObjectsDAO> servedObjects = ntisServedObjectsDBService.loadRecordsByParams(conn, " where so_wtf_id = ?::int ",
                new SelectParamValue(existingFacility.getWtf_id()));

        // check and set organization or user as wtf owner
        for (NtisServedObjectsDAO servedObject : servedObjects) {
            String ownerType = NtisFacilityOwnerType.SELF_ASSIGNED.getCode();
            if (orgId != null && !this.hasUserRole(conn, NtisRolesConstants.INTS_OWNER)) {
                ownerType = NtisFacilityOwnerType.SERVICE_PROVIDER.getCode();
            }

            NtisFacilityOwnersDAO existingOwner;
            if (orgId != null) {
                existingOwner = ntisFacilityOwnersDBService.loadRecordByParams(conn,
                        " WHERE fo_wtf_id = ?::int AND fo_org_id = ?::int AND fo_so_id = ?::int AND fo_owner_type = ? AND now() BETWEEN fo_date_from AND COALESCE(fo_date_to, now())",
                        new SelectParamValue(existingFacility.getWtf_id()), new SelectParamValue(orgId), new SelectParamValue(servedObject.getSo_id()),
                        new SelectParamValue(ownerType));
            } else {
                existingOwner = ntisFacilityOwnersDBService.loadRecordByParams(conn,
                        " WHERE fo_wtf_id = ?::int AND fo_per_id = ?::int AND fo_so_id = ?::int AND fo_owner_type = ? AND now() BETWEEN fo_date_from AND COALESCE(fo_date_to, now())",
                        new SelectParamValue(existingFacility.getWtf_id()), new SelectParamValue(perId), new SelectParamValue(servedObject.getSo_id()),
                        new SelectParamValue(ownerType));
            }

            if (existingOwner == null) {
                manageFacilityOwners(conn, servedObject, existingFacility.getWtf_id(), orgId, perId);
            }
        }

        // set selected facility
        if (this.hasUserRole(conn, NtisRolesConstants.INTS_OWNER)) {
            NtisSelectedFacilitiesDAO selectedFacility = null;
            if (orgId != null) {
                selectedFacility = ntisSelectedFacilitiesDBService.loadRecordByParams(conn, " fs_usr_id = ?::int and fs_org_id = ?::int ",
                        new SelectParamValue(usrId), new SelectParamValue(orgId));
            } else {
                selectedFacility = ntisSelectedFacilitiesDBService.loadRecordByParams(conn, " fs_usr_id = ?::int and fs_org_id is null ",
                        new SelectParamValue(usrId));
            }
            if (selectedFacility == null) {
                selectedFacility = ntisSelectedFacilitiesDBService.newRecord();
                selectedFacility.setFs_usr_id(usrId);
                selectedFacility.setFs_org_id(orgId);
            }
            selectedFacility.setFs_wtf_id(existingFacility.getWtf_id());
            ntisSelectedFacilitiesDBService.saveRecord(conn, selectedFacility);
        }
        return existingFacility.getWtf_id();
    }
}