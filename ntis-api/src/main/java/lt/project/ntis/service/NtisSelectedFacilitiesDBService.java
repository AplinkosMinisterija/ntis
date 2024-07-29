package lt.project.ntis.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import lt.project.ntis.dao.NtisFacilityOwnersDAO;
import lt.project.ntis.dao.NtisSelectedFacilitiesDAO;
import lt.project.ntis.dao.NtisWastewaterTreatmentFaciDAO;
import lt.project.ntis.enums.NtisFacilityOwnerType;
import lt.project.ntis.models.NtisCheckWtfSelectionRequest;
import lt.project.ntis.models.NtisCheckWtfSelectionResponse;
import lt.project.ntis.models.NtisCheckWtfSelectionResponseWtf;
import lt.project.ntis.service.gen.NtisSelectedFacilitiesDBServiceGen;

@Service
public class NtisSelectedFacilitiesDBService extends NtisSelectedFacilitiesDBServiceGen {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(NtisSelectedFacilitiesDBService.class);

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    NtisWastewaterTreatmentFaciDBService ntisWastewaterTreatmentFaciDBService;

    @Autowired
    NtisFacilityOwnersDBService ntisFacilityOwnersDBService;

    @Override
    public NtisSelectedFacilitiesDAO newRecord() {
        return super.newRecord();
    }

    /**
     * Funkcija gražina įrėnginio id, kuris yra naudotojo pažymėtas kaip aktyvus. Funkcija vertin ar naudotojas dirba kaip savarankiškas
     * žmogus ar kaip organizacijos atstovas. 
     * @param conn - prisijungimas prie bazės
     * @param userId - naudotojo identifikacinis ID
     * @param orgId - organizacijos ID
     * @return - naudotojo pažymeto valymo įrenginio ID, jei naudotojas nėra pažymėjęs įrenginio funkcija gražins null.
     * @throws Exception
     */
    public Double getSelecteFacility(Connection conn, Double userId, Double orgId) throws Exception {
        NtisSelectedFacilitiesDAO selectedFacility = null;
        if (orgId != null) {
            selectedFacility = this.loadRecordByParams(conn, " fs_usr_id = ?::int and fs_org_id = ?::int ", new SelectParamValue(userId),
                    new SelectParamValue(orgId));
        } else {
            selectedFacility = this.loadRecordByParams(conn, " fs_usr_id = ?::int and fs_org_id is null ", new SelectParamValue(userId));
        }
        if (selectedFacility == null) {
            return null;
        } else {
            return selectedFacility.getFs_wtf_id();
        }
    }

    public NtisCheckWtfSelectionResponse checkWtfSelection(Connection conn, NtisCheckWtfSelectionRequest wtfInfo, Double usrId, Double orgId, Double perId,
            String lang) throws Exception {
        if (wtfInfo == null || (wtfInfo.getAdId() == null && (wtfInfo.getLksX() == null || wtfInfo.getLksY() == null))) {
            throw new IllegalArgumentException("No address id or coordinates provided");
        }
        NtisCheckWtfSelectionResponse response = new NtisCheckWtfSelectionResponse(false, null);
        Double selectedWtfId = getSelecteFacility(conn, usrId, orgId);
        if (selectedWtfId != null) {
            NtisWastewaterTreatmentFaciDAO selectedWtfDao = this.ntisWastewaterTreatmentFaciDBService.loadRecord(conn, selectedWtfId);
            if (((wtfInfo.getAdId() != null && selectedWtfDao.getWtf_ad_id() != null && wtfInfo.getAdId().equals(selectedWtfDao.getWtf_ad_id().intValue()))
                    || (wtfInfo.getAdId() == null && selectedWtfDao.getWtf_facility_latitude() != null && selectedWtfDao.getWtf_facility_longitude() != null
                            && wtfInfo.getLksX().equals(selectedWtfDao.getWtf_facility_latitude())
                            && wtfInfo.getLksY().equals(selectedWtfDao.getWtf_facility_longitude())))
                    && (wtfInfo.getWtfType() == null || wtfInfo.getWtfType().equals(selectedWtfDao.getWtf_type()))) {
                response.setSelected(true);
            }
        }
        if (Boolean.FALSE.equals(response.getSelected())) {
            StatementAndParams statementAndParams = new StatementAndParams("" //
                    + "SELECT wtf.wtf_id AS wtfId, " //
                    + "       wtf_type_rfc.rfc_meaning AS wtfType, " //
                    + "       wtf.wtf_type AS wtfTypeCode, " //
                    + "       ad.ad_id AS adId, " //
                    + "       ad.ad_address AS address, " //
                    + "       wtf.wtf_facility_latitude AS lksX, " //
                    + "       wtf.wtf_facility_longitude AS lksY, " //
                    + "       owner_type_rfc.rfc_meaning as ownerType, " //
                    + "       fo.fo_owner_type as ownerTypeCode " //
                    + "  FROM ntis.ntis_wastewater_treatment_faci wtf " //
                    + "  LEFT JOIN ntis.ntis_adr_addresses ad ON ad.ad_id = wtf.wtf_ad_id "
                    + "  LEFT JOIN ntis.ntis_facility_owners fo ON fo.fo_wtf_id = wtf.wtf_id AND " + (orgId != null ? "fo.fo_org_id::int" : "fo.fo_per_id::int")
                    + " = ? " //
                    + "  LEFT JOIN spark.spr_ref_codes_vw wtf_type_rfc ON wtf_type_rfc.rfc_code = wtf.wtf_type AND wtf_type_rfc.rfc_domain = 'NTIS_WTF_TYPE' AND wtf_type_rfc.rft_lang = ? " //
                    + "  LEFT JOIN spark.spr_ref_codes_vw owner_type_rfc ON owner_type_rfc.rfc_code = fo.fo_owner_type AND owner_type_rfc.rfc_domain = 'NTIS_FACILITY_OWNER_TYPE' AND owner_type_rfc.rft_lang = ? ");
            statementAndParams.addSelectParam(orgId != null ? orgId : perId);
            statementAndParams.addSelectParam(lang);
            statementAndParams.addSelectParam(lang);
            if (wtfInfo.getWtfId() != null) {
                statementAndParams.addParam4WherePart("wtf.wtf_id::int = ?", wtfInfo.getWtfId());
            }
            if (wtfInfo.getAdId() != null) {
                statementAndParams.addParam4WherePart("wtf.wtf_ad_id IS NOT NULL AND wtf.wtf_ad_id::int = ?", wtfInfo.getAdId());
            } else {
                statementAndParams.addParam4WherePart(
                        "ST_DWithin(COALESCE(ad.ad_geom, ST_SetSRID(ST_MakePoint(wtf.wtf_facility_latitude, wtf.wtf_facility_longitude), 3346)), ST_SetSRID(ST_MakePoint(?, ?), 3346), 100)",
                        new SelectParamValue(wtfInfo.getLksX()), new SelectParamValue(wtfInfo.getLksY()));
            }
            if (wtfInfo.getWtfType() != null) {
                statementAndParams.addParam4WherePart("wtf.wtf_type = ?", wtfInfo.getWtfType());
            }
            statementAndParams.setStatementOrderPart("ORDER BY fo.fo_owner_type ASC");
            List<NtisCheckWtfSelectionResponseWtf> wtfs = this.baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams,
                    NtisCheckWtfSelectionResponseWtf.class);
            response.setWtfs(new ArrayList<>(wtfs));
        }
        return response;
    }

    public void selectedWtf(Connection conn, String wtfId, Double usrId, Double orgId, Double perId) throws Exception {

        NtisFacilityOwnersDAO ownersDao = null;
        if (orgId != null) {
            ownersDao = this.ntisFacilityOwnersDBService.loadRecordByParams(conn, "fo_wtf_id = ?::int AND fo_org_id = ?::int", new SelectParamValue(wtfId),
                    new SelectParamValue(orgId));
        } else {
            ownersDao = this.ntisFacilityOwnersDBService.loadRecordByParams(conn, "fo_wtf_id = ?::int AND fo_per_id = ?::int", new SelectParamValue(wtfId),
                    new SelectParamValue(perId));
        }
        if (ownersDao == null) {
            ownersDao = this.ntisFacilityOwnersDBService.newRecord();
            ownersDao.setFo_owner_type(NtisFacilityOwnerType.SELF_ASSIGNED.getCode());
            if (orgId != null) {
                ownersDao.setFo_org_id(orgId);
            } else {
                ownersDao.setFo_per_id(perId);
            }
            ownersDao.setFo_date_from(Utils.getDate());
            ownersDao.setFo_wtf_id(Utils.getDouble(wtfId));
            this.ntisFacilityOwnersDBService.saveRecord(conn, ownersDao);
        }

        NtisSelectedFacilitiesDAO facilitiesDao = null;
        if (orgId != null) {
            facilitiesDao = loadRecordByParams(conn, "fs_usr_id = ?::int AND fs_org_id = ?::int", new SelectParamValue(usrId), new SelectParamValue(orgId));
        } else {
            facilitiesDao = loadRecordByParams(conn, "fs_usr_id = ?::int AND fs_org_id IS NULL", new SelectParamValue(usrId));
        }
        if (facilitiesDao == null) {
            facilitiesDao = newRecord();
            facilitiesDao.setFs_usr_id(usrId);
            facilitiesDao.setFs_org_id(orgId);
        }
        facilitiesDao.setFs_wtf_id(Utils.getDouble((wtfId)));
        saveRecord(conn, facilitiesDao);
    }
}