import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NtisServedBuildingUpdateModel, SprFile, SprOrganizationsDAO } from '@itree-commons/src/lib/model/api/api';
import { CommonFormServices, DeprecatedBaseEditForm } from '@itree/ngx-s2-commons';
import { Observable } from 'rxjs';
import { CommonModule, Location } from '@angular/common';
import { BuildingDataService } from '../../../services/building-data.service';
import { FileUploadService } from '@itree-commons/src/lib/services/file-upload.service';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { CalendarModule } from 'primeng/calendar';
import { TableModule } from 'primeng/table';
import { NtisBuildingDataRoutingModule } from '../../../ntis-building-data-routing.module';
@Component({
  selector: 'app-wastewater-facility-building-edit',
  templateUrl: './wastewater-facility-building-edit.component.html',
  styleUrls: ['./wastewater-facility-building-edit.component.scss'],
  standalone: true,
  imports: [
    CalendarModule,
    CommonModule,
    FontAwesomeModule,
    FormsModule,
    ItreeCommonsModule,
    NtisBuildingDataRoutingModule,
    NtisSharedModule,
    ReactiveFormsModule,
    TableModule,
  ],
})
export class WastewaterFacilityBuildingEditComponent extends DeprecatedBaseEditForm<NtisServedBuildingUpdateModel> {
  readonly formTranslationsReference = 'ntisBuildingData.pages.wastewaterFacilityBuildingEditComponent';
  uploadedFiles: SprFile[] = [];

  updateBy: string = null;
  maxDate: Date = new Date();
  minDate: Date;

  managementCompanies: SprOrganizationsDAO[] = [];

  constructor(
    protected override formBuilder: FormBuilder,
    protected override commonFormServices: CommonFormServices,
    private location: Location,
    private buildingDataService: BuildingDataService,
    private fileUploadService: FileUploadService,
    protected override activatedRoute?: ActivatedRoute
  ) {
    super(formBuilder, commonFormServices, activatedRoute);
  }

  protected buildForm(formBuilder: FormBuilder): void {
    this.form = formBuilder.group({
      ba_network_connection_date: new FormControl<NtisServedBuildingUpdateModel['ba_network_connection_date']>(
        null,
        Validators.required
      ),
      ba_org_id: new FormControl<NtisServedBuildingUpdateModel['ba_org_id']>(null, Validators.required),
    });

    this.getWaterManagementCompanies();
  }
  protected doSave(value: NtisServedBuildingUpdateModel): void | Observable<NtisServedBuildingUpdateModel> {
    this.buildingDataService.updateBuildingAgreement(value).subscribe((response) => {
      this.commonFormServices.translate
        .get(this.formTranslationsReference + (response ? '.needApprove' : '.buildingUpdated'))
        .subscribe((translate: string) => {
          this.goBack();
          this.commonFormServices.appMessages.showSuccess('', translate);
        });
    });
  }
  protected doLoad(): void | Observable<NtisServedBuildingUpdateModel> {
    this.onLoadSuccess(
      this.activatedRoute.snapshot.data['wastewaterFacilityBuildingEdit'] as NtisServedBuildingUpdateModel
    );
  }
  protected setData(data: NtisServedBuildingUpdateModel): void {
    this.data = data;
    this.form.controls.ba_org_id.setValue(data.ba_org_id);
    this.form.controls.ba_network_connection_date.setValue(
      data.ba_network_connection_date ? new Date(data.ba_network_connection_date) : null
    );
    this.uploadedFiles = data?.attachments ? [data?.attachments] : [];
  }
  protected getData(): NtisServedBuildingUpdateModel {
    const result = this.data != null ? this.data : ({} as NtisServedBuildingUpdateModel);
    result.ba_network_connection_date = this.form.controls.ba_network_connection_date
      .value as NtisServedBuildingUpdateModel['ba_network_connection_date'];
    result.ba_org_id = this.form.controls.ba_org_id.value as NtisServedBuildingUpdateModel['ba_org_id'];
    result.attachments = this.uploadedFiles[0];
    return result;
  }

  getWaterManagementCompanies(): void {
    this.buildingDataService.getWaterManagementCompanies().subscribe((results) => {
      this.managementCompanies = results;
    });
  }

  onDeleteFile(fileToDelete: SprFile): void {
    if (fileToDelete.fil_name) {
      this.fileUploadService.deleteFile(fileToDelete).subscribe();
    }
    this.buildingDataService.deleteFuaFile(fileToDelete).subscribe();
  }

  goBack(): void {
    this.location.back();
  }
}
