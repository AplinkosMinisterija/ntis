import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { CommonFormServices, DeprecatedBaseEditForm } from '@itree/ngx-s2-commons';
import { fadeInFields } from '@itree-commons/src/lib/animations/animations';
import { SprTask, SprTaskEditModel } from '@itree-commons/src/lib/model/api/api';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { Component, Input } from '@angular/core';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';

export interface PersonCardList {
  id: number;
  code: string;
}

@Component({
  selector: 'app-sub-task-form-card',
  templateUrl: './sub-task-form-card.component.html',
  styleUrls: ['./sub-task-form-card.component.scss'],
  animations: [fadeInFields],
})
export class SubTaskFormCardComponent extends DeprecatedBaseEditForm<SprTaskEditModel> {
  showCardContent: boolean = true;
  maximumDate = new Date();
  maximumDateFrom: Date;
  dateInputDisable: boolean = true;

  subTaskList: Array<SprTaskEditModel> = [];

  SubTaskDialog: boolean = false;
  subPersonName: string;
  taskId: number;

  @Input() subTaskInput: Array<SprTaskEditModel> = [];

  constructor(
    protected override formBuilder: FormBuilder,
    protected override commonFormServices: CommonFormServices,
    private datePipe: S2DatePipe,
    public faIconsService: FaIconsService,
    protected override activatedRoute?: ActivatedRoute
  ) {
    super(formBuilder, commonFormServices, activatedRoute);
  }

  protected buildForm(): void {
    this.form = this.formBuilder.group({
      // Sub task fields
      sub_tas_name: new FormControl('', Validators.compose([Validators.maxLength(100), Validators.required])),
      sub_tas_description: new FormControl('', Validators.compose([Validators.maxLength(2000), Validators.required])),
      sub_tas_date_from: new FormControl('', Validators.compose([Validators.maxLength(10), Validators.required])),
      sub_tas_date_to: new FormControl('', Validators.compose([Validators.maxLength(10), Validators.required])),
      sub_tas_type: new FormControl('', Validators.compose([Validators.maxLength(50), Validators.required])),
      sub_tas_status: new FormControl('', Validators.compose([Validators.maxLength(50), Validators.required])),
      sub_tas_repetitive: new FormControl('', Validators.compose([Validators.maxLength(50), Validators.required])),
      sub_tas_priority: new FormControl('', Validators.compose([Validators.maxLength(50), Validators.required])),
    });
  }

  protected doSave(): void {
    this.setTaskData();
    this.form.reset();
  }

  protected doLoad(): void {
    this.subTaskInput.forEach((element) => {
      this.subTaskList.push({
        sprTask: {
          tas_id: element.sprTask.tas_id,
          tas_name: element.sprTask.tas_name,
          tas_type: element.sprTask.tas_type,
          tas_status: element.sprTask.tas_status,
          tas_date_from: element.sprTask.tas_date_from,
          tas_date_to: element.sprTask.tas_date_to,
          tas_reject_reason: element.sprTask.tas_reject_reason,
          tas_priority: element.sprTask.tas_priority,
          tas_repetitive: element.sprTask.tas_repetitive,
          tas_description: element.sprTask.tas_description,
          tas_usr_id: element.sprTask.tas_usr_id,
          tas_tas_id: element.sprTask.tas_tas_id,
          tas_usr_userName: element.sprTask.tas_usr_userName,
          tas_usr_userSurname: element.sprTask.tas_usr_userSurname,
        },
        progress: null,
        mainUser: null,
        assignees: null,
        assigneesIds: null,
        subTasks: null,
        attachments: null,
      });
    });
  }

  protected setData(): void {
    return;
  }
  protected getData(): SprTaskEditModel {
    const result: SprTaskEditModel = this.data != null ? this.data : ({ sprTask: {} } as SprTaskEditModel);

    this.form.controls.sub_tas_date_to.disable();
    // Sub task fields
    result.sprTask.tas_name = this.form.controls.sub_tas_name.value as SprTask['tas_name'];
    result.sprTask.tas_description = this.form.controls.sub_tas_description.value as SprTask['tas_description'];
    result.sprTask.tas_date_from = this.form.controls.sub_tas_date_from.value as SprTask['tas_date_from'];
    result.sprTask.tas_date_to = this.form.controls.sub_tas_date_to.value as SprTask['tas_date_to'];
    result.sprTask.tas_type = this.form.controls.sub_tas_type.value as SprTask['tas_type'];
    result.sprTask.tas_status = this.form.controls.sub_tas_status.value as SprTask['tas_status'];
    result.sprTask.tas_repetitive = this.form.controls.sub_tas_repetitive.value as SprTask['tas_repetitive'];
    result.sprTask.tas_priority = this.form.controls.sub_tas_priority.value as SprTask['tas_priority'];

    return result;
  }

  addPersonToList(event: PersonCardList): void {
    if (event) {
      this.subTaskList.unshift({
        sprTask: {
          tas_id: null,
          tas_name: null,
          tas_type: null,
          tas_status: null,
          tas_date_from: null,
          tas_date_to: null,
          tas_reject_reason: null,
          tas_priority: null,
          tas_repetitive: null,
          tas_description: null,
          tas_usr_id: event.id,
          tas_tas_id: null,
          tas_usr_userName: event.code,
          tas_usr_userSurname: null,
        },
        progress: null,
        mainUser: null,
        assignees: null,
        assigneesIds: null,
        subTasks: null,
        attachments: null,
      });
    }
  }

  showDialog(person: SprTaskEditModel): void {
    if (person.sprTask.tas_usr_userSurname !== null) {
      this.subPersonName = `${person.sprTask.tas_usr_userName} ${person.sprTask.tas_usr_userSurname}`;
    } else {
      this.subPersonName = person.sprTask.tas_usr_userName;
    }

    this.taskId = person.sprTask.tas_usr_id;
    this.SubTaskDialog = true;
    this.setDialogValues(person);
  }

  removeTask(person: SprTaskEditModel): void {
    for (let i = 0; i < this.subTaskList.length; i++) {
      if (this.subTaskList[i].sprTask.tas_usr_id === person.sprTask.tas_usr_id) {
        this.subTaskList.splice(i, 1);
      }
    }
  }

  checkDateInput(): void {
    if (this.form.controls.sub_tas_date_from.value === null) {
      this.maximumDateFrom = null;
      this.form.controls.sub_tas_date_to.disable();
      this.form.controls.sub_tas_date_to.setValue(null);
    }

    if (this.form.controls.sub_tas_date_from.value !== null) {
      this.form.controls.sub_tas_date_to.enable();
      this.maximumDateFrom = this.form.controls.sub_tas_date_from.value as Date;
    }
  }

  setTaskData(): void {
    const objIndex = this.subTaskList.findIndex((obj) => obj.sprTask.tas_usr_id === this.taskId);
    this.subTaskList[objIndex].sprTask.tas_name = this.form.controls.sub_tas_name.value as SprTask['tas_name'];
    this.subTaskList[objIndex].sprTask.tas_description = this.form.controls.sub_tas_description
      .value as SprTask['tas_description'];
    this.subTaskList[objIndex].sprTask.tas_date_from = this.form.controls.sub_tas_date_from
      .value as SprTask['tas_date_from'];
    this.subTaskList[objIndex].sprTask.tas_date_to = this.form.controls.sub_tas_date_to.value as SprTask['tas_date_to'];
    this.subTaskList[objIndex].sprTask.tas_type = this.form.controls.sub_tas_type.value as SprTask['tas_type'];
    this.subTaskList[objIndex].sprTask.tas_status = this.form.controls.sub_tas_status.value as SprTask['tas_status'];
    this.subTaskList[objIndex].sprTask.tas_repetitive = this.form.controls.sub_tas_repetitive
      .value as SprTask['tas_repetitive'];
    this.subTaskList[objIndex].sprTask.tas_priority = this.form.controls.sub_tas_priority
      .value as SprTask['tas_priority'];

    this.SubTaskDialog = false;
    this.form.reset();
  }

  setDialogValues(person: SprTaskEditModel): void {
    this.form.controls.sub_tas_name.setValue(person.sprTask.tas_name);
    this.form.controls.sub_tas_description.setValue(person.sprTask.tas_description);
    this.form.controls.sub_tas_date_from.setValue(
      this.datePipe.transform(person.sprTask.tas_date_from) != null
        ? new Date(this.datePipe.transform(person.sprTask.tas_date_from))
        : null
    );
    this.form.controls.sub_tas_date_to.setValue(
      this.datePipe.transform(person.sprTask.tas_date_to) != null
        ? new Date(this.datePipe.transform(person.sprTask.tas_date_to))
        : null
    );

    this.form.controls.sub_tas_type.setValue(person.sprTask.tas_type);
    this.form.controls.sub_tas_status.setValue(person.sprTask.tas_status);
    this.form.controls.sub_tas_repetitive.setValue(person.sprTask.tas_repetitive);
    this.form.controls.sub_tas_priority.setValue(person.sprTask.tas_priority);
  }
}
