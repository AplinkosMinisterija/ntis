package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBServiceImpl;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.dao.NtisCarsDAO;
import lt.project.ntis.dao.NtisSystemWorksDAO;
import lt.project.ntis.logic.forms.model.NtisSystemWorksEditModel;
import lt.project.ntis.models.NtisCar;
import lt.project.ntis.service.NtisCarsDBService;
import lt.project.ntis.service.NtisSystemWorksDBService;

/**
 * Klasė skirta formos "Pranešimas apie sistemos darbus" biznio logikai apibrėžti
 */

@Component
public class NtisSystemWorksEdit extends FormBase {

    private final NtisSystemWorksDBService ntisSystemWorksDBService;

    private final BaseControllerJDBC baseControllerJDBC;

    public NtisSystemWorksEdit(NtisSystemWorksDBService ntisSystemWorksDBService, BaseControllerJDBC baseControllerJDBC) {
        super();
        this.ntisSystemWorksDBService = ntisSystemWorksDBService;
        this.baseControllerJDBC = baseControllerJDBC;
    }

    @Override
    public String getFormName() {
        return "SYSTEM_WORKS_EDIT";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Pranešimas apie sistemos darbus", "Pranešimo apie sistemos darbus kūrimo ir redagavimo forma");
        addFormActions(NtisSystemWorksEdit.ACTION_READ, NtisSystemWorksEdit.ACTION_CREATE);
    }

    public NtisSystemWorksEditModel get(Connection conn) throws Exception {
        this.checkIsFormActionAssigned(conn, ACTION_READ);
        StatementAndParams stmt = new StatementAndParams("""
                  SELECT nsw_id as nswId,
                         nsw_show_date_from as startDate,
                         nsw_show_date_To as endDate,
                         nsw_works_date_from as worksDateFrom,
                         nsw_works_date_to as worksDateTo,
                         nsw_additional_information as additionalInformation,
                         nsw_is_active as isActive
                    FROM ntis_system_works
                ORDER BY nsw_id desc
                   LIMIT 1
                  """);
        List<NtisSystemWorksEditModel> selectResult = this.baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisSystemWorksEditModel.class);
        NtisSystemWorksEditModel resultToReturn = selectResult != null && selectResult.size() > 0 ? selectResult.get(0) : new NtisSystemWorksEditModel();
        return resultToReturn;
    }

    public NtisSystemWorksEditModel save(Connection conn, NtisSystemWorksEditModel record) throws Exception {
        this.checkIsFormActionAssigned(conn, ACTION_CREATE);
                StatementAndParams stmt = new StatementAndParams("""
                update ntis_system_works
                    set nsw_is_active = 'N'
                    where nsw_is_active = 'Y' 
                """);
        this.baseControllerJDBC.adjustRecordsInDB(conn, stmt);
        NtisSystemWorksDAO ntisSystemWorksDAO = this.ntisSystemWorksDBService.newRecord();
        ntisSystemWorksDAO.setNsw_show_date_from(record.getStartDate());
        ntisSystemWorksDAO.setNsw_show_date_to(record.getEndDate());
        ntisSystemWorksDAO.setNsw_works_date_from(record.getWorksDateFrom());
        ntisSystemWorksDAO.setNsw_works_date_to(record.getWorksDateTo());
        ntisSystemWorksDAO.setNsw_additional_information(record.getAdditionalInformation());
        ntisSystemWorksDAO.setNsw_is_active(record.getIsActive());
        ntisSystemWorksDAO.setNsw_notification_sent(YesNo.N.getCode());
        ntisSystemWorksDBService.saveRecord(conn, ntisSystemWorksDAO);
        record.setNswId(ntisSystemWorksDAO.getNsw_id());
        return record;
    }
    
    public void updateSysWorksStatus(Connection conn, Double id) throws Exception {
        this.checkIsFormActionAssigned(conn, ACTION_CREATE);
        NtisSystemWorksDAO ntisSystemWorksDAO = this.ntisSystemWorksDBService.loadRecord(conn, id);
        String currentState = ntisSystemWorksDAO.getNsw_is_active();
        ntisSystemWorksDAO.setNsw_is_active(YesNo.Y.getCode().equals(currentState) ? YesNo.N.getCode() : YesNo.Y.getCode());
        this.ntisSystemWorksDBService.saveRecord(conn, ntisSystemWorksDAO);
    }
}
