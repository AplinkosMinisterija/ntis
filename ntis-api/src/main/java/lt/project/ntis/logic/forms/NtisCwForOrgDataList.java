package lt.project.ntis.logic.forms;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;

/**
 * Klasė skirta formos "Centralizuoto nuotekų tvarkymo duomenys" (formos kodas N4200) biznio logikai apibrėžti, kai naudotojas turi vandentvarkos admin teises
 */

@Component
@Qualifier("ntisCwForOrgDataList")
public class NtisCwForOrgDataList extends NtisCwDataList {

    @Override
    public String getFormName() {
        return "NTIS_CW_FOR_ORG_DATA_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Centralizuoto nuotekų tvarkymo duomenys", "Centralizuoto nuotekų tvarkymo duomenų forma vandentvarkai");
        addFormActions(FormBase.ACTION_READ, FormBase.ACTION_CREATE, FormBase.ACTION_UPDATE, FormBase.ACTION_DELETE);
    }

}
