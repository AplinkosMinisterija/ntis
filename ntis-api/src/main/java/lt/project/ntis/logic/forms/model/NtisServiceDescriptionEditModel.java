package lt.project.ntis.logic.forms.model;

import java.util.List;

import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;

/**
 * Klasė skirta formos "Paslaugos aprašymas" biznio logikai apibrėžti ir duomenų perdavimui
 */

public class NtisServiceDescriptionEditModel {

    public NtisServiceDescription ntisServiceDescription;

    public SprFile labInstructions;

    public SprFile contractFile;

    public List<NtisMunicipalitiesRequest> municipalities;

    public NtisServiceDescription getNtisServiceDescription() {
        return ntisServiceDescription;
    }

    public void setNtisServiceDescription(NtisServiceDescription ntisServiceDescription) {
        this.ntisServiceDescription = ntisServiceDescription;
    }

    public List<NtisMunicipalitiesRequest> getMunicipalities() {
        return municipalities;
    }

    public void setMunicipalities(List<NtisMunicipalitiesRequest> municipalities) {
        this.municipalities = municipalities;
    }

    public SprFile getContractFile() {
        return contractFile;
    }

    public void setContractFile(SprFile contractFile) {
        this.contractFile = contractFile;
    }

    public SprFile getLabInstructions() {
        return labInstructions;
    }

    public void setLabInstructions(SprFile labInstructions) {
        this.labInstructions = labInstructions;
    }

}
