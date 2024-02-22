import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ServiceDescriptionDetailsComponent } from './components/service-description-details/service-description-details.component';
import { ServiceOrdererDetailsComponent } from './components/service-orderer-details/service-orderer-details.component';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { ReactiveFormsModule } from '@angular/forms';
import { CalendarModule } from 'primeng/calendar';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@NgModule({
  declarations: [ServiceDescriptionDetailsComponent, ServiceOrdererDetailsComponent],
  imports: [CalendarModule, CommonModule, FontAwesomeModule, ItreeCommonsModule, NtisSharedModule, ReactiveFormsModule],
  exports: [ServiceDescriptionDetailsComponent, ServiceOrdererDetailsComponent],
})
export class SharedModule {}
