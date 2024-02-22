import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { EMAIL_PATTERN, PHONE_PATTERN, WEBSITE_PATTERN } from '@itree-commons/src/constants/validation.constants';
import { NtisNewServiceRequest } from '@itree-commons/src/lib/model/api/api';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-service-request-props',
  templateUrl: './service-request-props.component.html',
  styleUrls: ['./service-request-props.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, ReactiveFormsModule],
})
export class ServiceRequestPropsComponent implements OnChanges, OnInit, OnDestroy {
  readonly translationsReference = 'institutionsAdmin.components.serviceRequestProps';
  @Input() isEditable: boolean;
  @Input() isServiceProvider: boolean;
  @Input() applicant: string;
  @Input() organization: string;
  @Input() orgAddress: string;
  @Input() orgEmail: string;
  @Input() orgPhone: string;
  @Input() orgWebsite: string;
  @Input() markFormAsTouched: Subject<boolean>;
  @Output() setOrgProps = new EventEmitter<NtisNewServiceRequest>();

  form = new FormGroup({
    org_email: new FormControl<string>(
      null,
      Validators.compose([
        Validators.required,
        Validators.pattern(new RegExp(EMAIL_PATTERN)),
        Validators.maxLength(100),
      ])
    ),
    org_phone: new FormControl<string>(
      null,
      Validators.compose([Validators.required, Validators.pattern(new RegExp(PHONE_PATTERN))])
    ),
    org_website: new FormControl<string>(
      null,
      Validators.compose([Validators.pattern(new RegExp(WEBSITE_PATTERN)), Validators.maxLength(200)])
    ),
  });

  ngOnInit(): void {
    this.markFormAsTouched?.subscribe(() => {
      this.form.markAllAsTouched();
    });
  }

  ngOnDestroy(): void {
    this.markFormAsTouched?.unsubscribe();
  }

  ngOnChanges(changes: SimpleChanges): void {
    changes.orgEmail ? this.form.controls.org_email.setValue(changes.orgEmail.currentValue as string) : null;
    changes.orgPhone ? this.form.controls.org_phone.setValue(changes.orgPhone.currentValue as string) : null;
    changes.orgWebsite ? this.form.controls.org_website.setValue(changes.orgWebsite.currentValue as string) : null;
  }

  onFormChange(): void {
    this.form.markAllAsTouched();
    const result: NtisNewServiceRequest = {} as NtisNewServiceRequest;
    result.org_email = this.form.controls.org_email.valid ? this.form.controls.org_email.value : null;
    result.org_phone = this.form.controls.org_phone.valid ? this.form.controls.org_phone.value : null;
    result.org_website = this.form.controls.org_website.value;
    this.setOrgProps.emit(result);
  }
}
