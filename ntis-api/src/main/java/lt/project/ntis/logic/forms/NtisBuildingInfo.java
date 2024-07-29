package lt.project.ntis.logic.forms;

import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import lt.project.ntis.service.NtisWastewaterTreatmentFaciDBService;

@Component
public class NtisBuildingInfo extends FormBase {

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    NtisWastewaterTreatmentFaciDBService ntisWastewaterTreatmentFaciDBService;

    @Override
    public String getFormName() {
        return "NTIS_WASTEWATER_FACILITY_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Wastewater facility list", "Wastewater facility list");
        addFormAction(FormBase.ACTION_CREATE, FormBase.ACTION_CREATE_DESC, FormBase.ACTION_CREATE_DESC);
        addFormAction(FormBase.ACTION_READ, FormBase.ACTION_READ_DESC, FormBase.ACTION_READ_DESC);
        addFormAction(FormBase.ACTION_UPDATE, FormBase.ACTION_UPDATE_DESC, FormBase.ACTION_UPDATE_DESC);
    }

    public String getBuildingInfo(Connection conn, RecordIdentifier recordIdentifier, Double userId) throws Exception {

        return null;
    }

}
