import { Component, Input, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonFormServices, DeprecatedBaseEditForm } from '@itree/ngx-s2-commons';
import { fadeInFields } from '@itree-commons/src/lib/animations/animations';
import { SprFile, SprTask, SprTaskEditModel } from '@itree-commons/src/lib/model/api/api';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { FileUploadService } from '@itree-commons/src/lib/services/file-upload.service';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { TasksAdminService } from '../../task-services/tasks-admin.service';
import { SubTaskFormCardComponent } from '../../tasks-components/sub-task-form-card/sub-task-form-card.component';
import { NO, YES } from '@itree-commons/src/constants/validation.constants';

@Component({
  selector: 'app-task-edit-page',
  templateUrl: './task-edit-page.component.html',
  styleUrls: ['./task-edit-page.component.scss'],
  animations: [fadeInFields],
})
export class TaskEditPageComponent extends DeprecatedBaseEditForm<SprTaskEditModel> {
  selectedPersons: Array<{ id: string; code: string }> = [];
  PersonList: Array<{ id: number; code: string }> = [];
  @Input() formControlName: string;
  maximumDate = new Date();
  maximumDateFrom: Date;
  isPartial: boolean = false;
  uploadedFiles: SprFile[] = [];
  subTaskList: Array<SprTaskEditModel> = [];
  appChildObj: Array<SprTaskEditModel> = [];
  selectedValue: string = NO;

  @ViewChild(SubTaskFormCardComponent) appChild: SubTaskFormCardComponent;

  constructor(
    protected override formBuilder: FormBuilder,
    private fileUploadService: FileUploadService,
    protected override commonFormServices: CommonFormServices,
    private router: Router,
    private adminService: TasksAdminService,
    public faIconsService: FaIconsService,
    protected override activatedRoute?: ActivatedRoute
  ) {
    super(formBuilder, commonFormServices, activatedRoute);
  }

  protected buildForm(): void {
    this.loadPersonsData();

    this.form = this.formBuilder.group({
      // General task data
      tas_name: new FormControl('', Validators.compose([Validators.maxLength(100), Validators.required])),
      tas_persons: new FormControl('', Validators.required),
      tas_groups: new FormControl({ value: '', disabled: true }),
      tas_partial: new FormControl('N', Validators.required),
      tas_type: new FormControl('', Validators.compose([Validators.maxLength(50), Validators.required])),
      tas_status: new FormControl('', Validators.compose([Validators.maxLength(50), Validators.required])),
      // Task date time data
      tas_date_from: new FormControl('', Validators.compose([Validators.maxLength(10), Validators.required])),
      tas_date_to: new FormControl('', Validators.compose([Validators.maxLength(10)])),
      tas_repetitive: new FormControl('', Validators.compose([Validators.maxLength(50), Validators.required])),
      // Additional task data
      tas_priority: new FormControl('', Validators.compose([Validators.maxLength(50), Validators.required])),
      tas_description: new FormControl('', Validators.compose([Validators.maxLength(1000), Validators.required])),
      tas_sub_task_person: new FormControl(''),
    });
  }

  protected setData(data: SprTaskEditModel): void {
    this.data = data;
    // General task data
    this.form.controls.tas_name.setValue(data.sprTask.tas_name);
    this.form.controls.tas_persons.setValue(data.assigneesIds);
    this.form.controls.tas_type.setValue(data.sprTask.tas_type);
    this.form.controls.tas_status.setValue(data.sprTask.tas_status);
    // Task date time data
    this.form.controls.tas_date_from.setValue(
      data.sprTask?.tas_date_from != null ? new Date(data.sprTask.tas_date_from) : null
    );
    this.form.controls.tas_partial.setValue(YES);
    this.form.controls.tas_date_to.setValue(
      data.sprTask?.tas_date_to != null ? new Date(data.sprTask.tas_date_to) : null
    );
    this.form.controls.tas_repetitive.setValue(data.sprTask.tas_repetitive);
    // Additional task data
    this.form.controls.tas_priority.setValue(data.sprTask.tas_priority);
    this.form.controls.tas_description.setValue(data.sprTask.tas_description);
    this.checkSubTask(this.data.subTasks?.length);
    this.subTaskList = this.data.subTasks;
    // Task files

    if (this.data.attachments !== null) {
      this.uploadedFiles = this.data.attachments;
    }
  }

  protected getData(): SprTaskEditModel {
    const result: SprTaskEditModel = this.data != null ? this.data : ({ sprTask: {} } as SprTaskEditModel);
    // General task data

    if (this.form.controls) {
      result.sprTask.tas_name = this.form.controls.tas_name.value as SprTask['tas_name'];
      result.assigneesIds = this.form.controls.tas_persons.value as string[];
      result.sprTask.tas_type = this.form.controls.tas_type.value as SprTask['tas_type'];
      result.sprTask.tas_status = this.form.controls.tas_status.value as SprTask['tas_status'];
      // Task date time data
      result.sprTask.tas_date_from = this.form.controls.tas_date_from.value as SprTask['tas_date_from'];
      result.sprTask.tas_date_to = this.form.controls.tas_date_to.value as SprTask['tas_date_to'];
      result.sprTask.tas_repetitive = this.form.controls.tas_repetitive.value as SprTask['tas_repetitive'];
      // Additional task data
      result.sprTask.tas_priority = this.form.controls.tas_priority.value as SprTask['tas_priority'];
      result.sprTask.tas_description = this.form.controls.tas_description.value as SprTask['tas_description'];
      result.subTasks = this.appChild?.subTaskList.filter((data) => data.sprTask.tas_name !== null);
      // Attachments
      result.attachments = this.uploadedFiles;
      this.data = result;

      return result;
    }
    return null;
  }

  protected doLoad(id: string, actionType?: string): void {
    this.adminService.getTaskRecord(id, actionType).subscribe((response: SprTaskEditModel) => {
      this.onLoadSuccess(response);
    });
  }

  checkDateInput(): void {
    if (this.form.controls.tas_date_from.value === null) {
      this.maximumDateFrom = null;
      this.form.controls.tas_date_to.disable();
      this.form.controls.tas_date_to.setValue(null);
    }

    if (this.form.controls.tas_date_from.value !== null) {
      this.form.controls.tas_date_to.enable();
      this.maximumDateFrom = this.form.controls.tas_date_from.value as Date;
    }
  }

  checkSubTask(num: number): void {
    if (num > 0) {
      this.form.controls.tas_partial.setValue(YES);
      this.isPartial = true;
    } else {
      this.form.controls.tas_partial.setValue(NO);
      this.isPartial = false;
    }
  }

  async naviigateToEdit(id: number): Promise<void> {
    const url = this.router.url.split(/([0-9]+)/)[0];
    await this.router.navigate([`${url}/${id}`]);
  }

  handleChange(): void {
    this.isPartial = this.form.controls.tas_partial.value === 'Y';
  }

  private loadPersonsData(): void {
    this.adminService.getTaskPersonList().subscribe((response) => {
      for (const row of response.data) {
        this.PersonList.push({ id: row.usr_id, code: row.usr_person_name + ' ' + row.usr_person_surname });
      }
    });
  }

  protected doSave(value: SprTaskEditModel): void {
    this.adminService.setTaskRecord(value).subscribe((results) => {
      this.onSaveSuccess(results);
      this.onCancel();
    });
  }

  onDeleteFile(fileToDelete: SprFile): void {
    if (fileToDelete.fil_name) {
      this.fileUploadService.deleteFile(fileToDelete).subscribe();
    }
  }

  onCancel(): void {
    this.backToBrowseForm(`${RoutingConst.INTERNAL}/${RoutingConst.TASKS}/${RoutingConst.SPARK_TASKS_BROWSE}`);
  }
}
