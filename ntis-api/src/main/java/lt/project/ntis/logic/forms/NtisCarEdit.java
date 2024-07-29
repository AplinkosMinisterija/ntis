package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.Date;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBServiceImpl;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.dao.NtisCarsDAO;
import lt.project.ntis.models.NtisCar;
import lt.project.ntis.service.NtisCarsDBService;

/**
 * Klasė skirta formos "Transporto priemonės redagavimas" biznio logikai apibrėžti
 */
@Component
public class NtisCarEdit extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(NtisCarEdit.class);

    private final NtisCarsDBService ntisCarsDBService;

    private final SprOrgUsersDBServiceImpl sprOrgUsersDBService;

    public NtisCarEdit(NtisCarsDBService ntisCarsDBService, SprOrgUsersDBServiceImpl sprOrgUsersDBService) {
        super();
        this.ntisCarsDBService = ntisCarsDBService;
        this.sprOrgUsersDBService = sprOrgUsersDBService;
    }

    @Override
    public String getFormName() {
        return "CAR_EDIT";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "TP redagavimas", "Transporto priemonių redagavimo forma");
        addFormActionCRUD();
    }

    /**
     * Function will return NtisCarsDAO object for provided car id
     * @param conn - connection to the DB
     * @param recordIdentifier - car identifier, which data should be returned
     * @param orgId - organisation session id
     * @param usrId - user session id
     * @return NtisCarsDAO object
     * @throws NumberFormatException
     * @throws Exception
     */
    public NtisCar get(Connection conn, RecordIdentifier recordIdentifier, Double orgId, Double usrId) throws NumberFormatException, Exception {
        NtisCarsDAO ntisCarsDao = null;
        if ("new".equalsIgnoreCase(recordIdentifier.getId()) || "new".equalsIgnoreCase(recordIdentifier.getActionType())) {
            log.debug("Will create new record");
            this.checkIsFormActionAssigned(conn, NtisCarEdit.ACTION_CREATE);
            ntisCarsDao = ntisCarsDBService.newRecord();
        } else {
            log.debug("Will return record by id: " + recordIdentifier.getId());
            this.checkIsFormActionAssigned(conn, NtisCarEdit.ACTION_READ);
            ntisCarsDao = ntisCarsDBService.loadRecord(conn, recordIdentifier.getIdAsDouble());
            if ("copy".equalsIgnoreCase(recordIdentifier.getActionType())) {
                ntisCarsDao.setCr_id(null);
            }
            if (!Objects.equals(ntisCarsDao.getCr_org_id(), orgId) || !sprOrgUsersDBService.checkIsUserInOrganization(conn, usrId, orgId)) {
                log.error(String.format("Can't get a car that belongs to other organization (car organization id: %.0f , user organization id: %.0f)",
                        ntisCarsDao.getCr_org_id(), orgId));
                throw new SparkBusinessException(new S2Message("common.error.action_not_granted", SparkMessageType.ERROR));
            }
        }
        return ntisCarsDao.getNtisCarModel();
    }

    /**
     * Function will save provided object to the db. Before save will be performed below listed actions:
     *      1. check if user has right perform this action (insert or update)
     *      2. validate provided data
     *      3. save data to the db.
     * @param conn - connection to the db, that will be used for data saving.
     * @param record - object that should be stored in db
     * @param orgId - user session organization id
     * @param usrId - user session user id
     * @return 
     * @return - saved object.
     * @throws Exception
     */
    public NtisCar save(Connection conn, NtisCar record, Double orgId, Double usrId) throws Exception {
        NtisCarsDAO ntisCarsDao = null;
        if (record.getId() == null) {
            this.checkIsFormActionAssigned(conn, FormBase.ACTION_CREATE);
            ntisCarsDao = ntisCarsDBService.newRecord();
            ntisCarsDao.setCr_date_from(new Date());
            ntisCarsDao.setCr_org_id(orgId);
        } else {
            this.checkIsFormActionAssigned(conn, FormBase.ACTION_UPDATE);
            ntisCarsDao = ntisCarsDBService.loadRecord(conn, record.getId().doubleValue());
        }
        if (record.getId() == null || record.getIsInUse()) {
            ntisCarsDao.setCr_reg_no(record.getRegNo());
            ntisCarsDao.setCr_model(record.getModel());
            ntisCarsDao.setCr_capacity(record.getCapacity());
            ntisCarsDao.setCr_tube_length(record.getTubeLength());
            ntisCarsDao.setCr_date_to(null);
        } else if (ntisCarsDao.getCr_date_to() == null) {
            ntisCarsDao.setCr_date_to(new Date());
        }
        if (!Objects.equals(ntisCarsDao.getCr_org_id(), orgId) || !sprOrgUsersDBService.checkIsUserInOrganization(conn, usrId, orgId)) {
            log.error(String.format("Can't edit a car that belongs to other organization (car organization id: %.0f , user organization id: %.0f)",
                    ntisCarsDao.getCr_org_id(), orgId));
            throw new SparkBusinessException(new S2Message("common.error.action_not_granted", SparkMessageType.ERROR));
        }
        return ntisCarsDBService.saveRecord(conn, ntisCarsDao).getNtisCarModel();
    }

    /**
     * Function will delete record in db that has provided identifier.
     * @param conn - db connection used for data deletion
     * @param recordIdentifier - used for record identification in db that should be deleted.
     * @param orgId - session organization id
     * @param usrId - session user id
     * @throws NumberFormatException
     * @throws Exception
     */
    public void delete(Connection conn, Double crId, Double orgId, Double usrId) throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, NtisCarEdit.ACTION_DELETE);
        NtisCarsDAO ntisCarsDao = ntisCarsDBService.loadRecord(conn, crId);
        if (!Objects.equals(ntisCarsDao.getCr_org_id(), orgId) || !sprOrgUsersDBService.checkIsUserInOrganization(conn, usrId, orgId)) {
            log.error(String.format("Can't delete a car that belongs to other organization (car organization id: %.0f , user organization id: %.0f)",
                    ntisCarsDao.getCr_org_id(), orgId));
            throw new SparkBusinessException(new S2Message("common.error.action_not_granted", SparkMessageType.ERROR));
        }
        ntisCarsDBService.deleteRecord(conn, crId);
    }
}
