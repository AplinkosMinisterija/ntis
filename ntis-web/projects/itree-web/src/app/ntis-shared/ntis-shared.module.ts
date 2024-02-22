import { AccordionComponent } from './components/accordion/accordion.component';
import { AddressListComponent } from './components/address-list/address-list.component';
import { AddressSearchFormComponent } from './components/address-search-form/address-search-form.component';
import { BarChartComponent } from './components/bar-chart/bar-chart.component';
import { CommonModule } from '@angular/common';
import { DeliveredSludgeInfoComponent } from './components/delivered-sludge-info/delivered-sludge-info.component';
import { DetailedSearchButtonComponent } from './components/detailed-search-button/detailed-search-button.component';
import { DialogModule } from 'primeng/dialog';
import { DoughnutChartComponent } from './components/doughnut-chart/doughnut-chart.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { MessageBoxComponent } from './components/message-box/message-box.component';
import { NgModule } from '@angular/core';
import { NtisFormFieldComponent } from './components/ntis-form-field/ntis-form-field.component';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { RecordStatusComponent } from './components/record-status/record-status.component';
import { RouterModule } from '@angular/router';
import { ServiceProviderInfoComponent } from './components/service-provider-info/service-provider-info.component';
import { SewageDeliveryInfoComponent } from './components/sewage-delivery-info/sewage-delivery-info.component';
import { SewageOriginFacilityComponent } from './components/sewage-origin-facility/sewage-origin-facility.component';
import { TableModule } from 'primeng/table';
import { TableRowActionsComponent } from './components/table-row-actions/table-row-actions.component';
import { TooltipModule } from 'primeng/tooltip';
import { ViewServedObjectsComponent } from './components/view-served-objects/view-served-objects.component';
import { WtfInfoComponent } from './components/wtf-info/wtf-info.component';
import { NtisClientInfoComponent } from './components/ntis-client-info/ntis-client-info.component';
import { WtfSearchComponent } from './components/wtf-search/wtf-search.component';
import { ExistingFacilitySearchComponent } from './components/existing-facility-search/existing-facility-search.component';
import { FaqThemesListComponent } from './pages/faq/faq-themes-list/faq-themes-list.component';
import { FaqThemeComponent } from './pages/faq/faq-theme/faq-theme.component';

@NgModule({
  declarations: [
    AccordionComponent,
    AddressListComponent,
    AddressSearchFormComponent,
    BarChartComponent,
    DeliveredSludgeInfoComponent,
    DetailedSearchButtonComponent,
    DoughnutChartComponent,
    MessageBoxComponent,
    NtisClientInfoComponent,
    NtisFormFieldComponent,
    RecordStatusComponent,
    ServiceProviderInfoComponent,
    SewageDeliveryInfoComponent,
    SewageOriginFacilityComponent,
    TableRowActionsComponent,
    ViewServedObjectsComponent,
    WtfInfoComponent,
    WtfSearchComponent,
    ExistingFacilitySearchComponent,
    FaqThemesListComponent,
    FaqThemeComponent,
  ],
  imports: [
    CommonModule,
    DialogModule,
    FontAwesomeModule,
    FormsModule,
    ItreeCommonsModule,
    OverlayPanelModule,
    ReactiveFormsModule,
    RouterModule,
    TableModule,
    TooltipModule,
  ],
  exports: [
    AccordionComponent,
    AddressListComponent,
    AddressSearchFormComponent,
    BarChartComponent,
    DeliveredSludgeInfoComponent,
    DeliveredSludgeInfoComponent,
    DetailedSearchButtonComponent,
    DoughnutChartComponent,
    ExistingFacilitySearchComponent,
    MessageBoxComponent,
    NtisClientInfoComponent,
    NtisFormFieldComponent,
    RecordStatusComponent,
    ServiceProviderInfoComponent,
    SewageDeliveryInfoComponent,
    SewageOriginFacilityComponent,
    TableRowActionsComponent,
    ViewServedObjectsComponent,
    WtfInfoComponent,
    WtfSearchComponent,
  ],
})
export class NtisSharedModule {}
