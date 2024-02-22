import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { NtisServiceProviderContacts } from '@itree-commons/src/lib/model/api/api';
import { InstitutionsAdminService } from '../../services/institutions-admin.service';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { EMAIL_PATTERN, PHONE_PATTERN, WEBSITE_PATTERN } from '@itree-commons/src/constants/validation.constants';

@Component({
  selector: 'app-contact-information',
  templateUrl: './contact-information.component.html',
  styleUrls: ['./contact-information.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, FormsModule, ReactiveFormsModule],
})
export class ContactInformationComponent implements OnChanges {
  readonly translationsReference = 'institutionsAdmin.components.contactInformation';
  editMode = false;
  @Input() editAvailable: boolean = true;
  form = new FormGroup({
    email: new FormControl<string>(null, [
      Validators.required,
      Validators.pattern(new RegExp(EMAIL_PATTERN)),
      Validators.maxLength(100),
    ]),
    emailNotifications: new FormControl<boolean>(false),
    phoneNumber: new FormControl<string>(null, [Validators.required, Validators.pattern(new RegExp(PHONE_PATTERN))]),
    website: new FormControl<string>(
      null,
      Validators.compose([Validators.pattern(new RegExp(WEBSITE_PATTERN)), Validators.maxLength(200)])
    ),
  });

  @Input() contacts: NtisServiceProviderContacts;
  @Input() isWaterManager = false;
  @Output() contactsChange = new EventEmitter<NtisServiceProviderContacts>();

  constructor(private institutionsAdminService: InstitutionsAdminService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.contacts) {
      const currVal = changes.contacts.currentValue as NtisServiceProviderContacts;
      this.setContactsValuesToForm(currVal);
    }
  }

  setContactsValuesToForm(contacts: NtisServiceProviderContacts = this.contacts): void {
    this.form.controls.email.setValue(contacts?.email || null);
    this.form.controls.emailNotifications.setValue(contacts?.emailNotifications || false);
    this.form.controls.phoneNumber.setValue(contacts?.phoneNumber || null);
    this.form.controls.website.setValue(contacts?.website || null);
  }

  handleClickEdit(): void {
    this.setContactsValuesToForm();
    this.editMode = true;
  }

  submitChanges(): void {
    this.form.markAllAsTouched();
    if (this.form.valid) {
      const contacts: NtisServiceProviderContacts = {
        orgId: this.contacts.orgId,
        email: this.form.controls.email.value,
        emailNotifications: this.form.controls.emailNotifications.value,
        phoneNumber: this.form.controls.phoneNumber.value,
        website: this.form.controls.website.value,
      };
      this.editMode = false;

      if (this.isWaterManager) {
        this.institutionsAdminService.saveWaterManagerContacts(contacts).subscribe((result) => {
          this.processSaveResult(result);
        });
      } else {
        this.institutionsAdminService.saveServiceProviderContacts(contacts).subscribe((result) => {
          this.processSaveResult(result);
        });
      }
    }
  }

  processSaveResult(result: NtisServiceProviderContacts): void {
    this.contacts = result;
    this.contactsChange.emit(result);
  }
}
