package lt.project.ntis.logic.forms;

import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;

@Component
public class OrderImportViewPage extends FormBase {

    public static final String PASL_ADMIN_ACTIONS = "PASL_ADMIN_ACTIONS";

    public static final String PASL_ADMIN_ACTIONS_DESC = "Paslaugų teikimo įmonės administratoriaus veiksmai";

    @Override
    public String getFormName() {
        return "NTIS_ORD_IMPORT_VIEW_PAGE";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Importuotas užsakymų failas", "Importuoto užsakymų failo peržiūros forma");
        addFormActions(FormBase.ACTION_READ, FormBase.ACTION_UPDATE, FormBase.ACTION_DELETE);
        addFormAction(PASL_ADMIN_ACTIONS, PASL_ADMIN_ACTIONS_DESC, PASL_ADMIN_ACTIONS);
    }

}
