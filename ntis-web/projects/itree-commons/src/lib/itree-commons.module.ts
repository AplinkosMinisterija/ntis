import { AccessibilityButtonComponent } from './components/accessibility/accessibility-button/accessibility-button.component';
import { AccessibilityComponent } from './components/accessibility/accessibility.component';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { BreadcrumbsComponent } from './components/breadcrumbs/breadcrumbs.component';
import { BrowseSearchFormComponent } from './components/browse-search-form/browse-search-form.component';
import { ButtonAddComponent } from './components/button-add/button-add.component';
import { ButtonBackComponent } from './components/button-back/button-back.component';
import { ButtonComponent } from './components/button/button.component';
import { ButtonLoginComponent } from './components/button-login/button-login.component';
import { ButtonModule } from 'primeng/button';
import { CalendarModule } from 'primeng/calendar';
import { CommonModule, DatePipe } from '@angular/common';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ContentBoxComponent } from './components/content-box/content-box.component';
import { DateAgoPipe } from './pipes/date-ago.pipe';
import { DialogModule } from 'primeng/dialog';
import { DropdownComponent } from './components/dropdown/dropdown.component';
import { EditFormComponent } from './components/edit-form/edit-form.component';
import { FileUploadModule } from 'primeng/fileupload';
import { FileUploadWithProgressComponent } from './components/file-upload-with-progress/file-upload-with-progress.component';
import { FileUploadComponent } from '@itree/ngx-s2-ui';
import { FilterInputComponent } from './components/filter-input/filter-input.component';
import { FilterManagerComponent } from './components/filter-manager/filter-manager.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FormFieldComponent } from './components/form-field/form-field.component';
import { FormFieldGroupComponent } from './components/form-field-group/form-field-group.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IconComponent } from './components/icon/icon.component';
import { InfoLineComponent } from './components/info-line/info-line.component';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputPasswordComponent } from './components/input-password/input-password.component';
import { InputSwitchModule } from 'primeng/inputswitch';
import { InternalComponent } from './components/internal/internal.component';
import { LanguageSwitchComponent } from './components/language-switch/language-switch.component';
import { LayoutComponent } from './components/layout/layout.component';
import { LinkCardComponent } from './components/link-card/link-card.component';
import { LoaderComponent } from './components/loader/loader.component';
import { NgModule } from '@angular/core';
import { NotificationsBrowseComponent } from '../pages/notifications/notifications-browse/notifications-browse.component';
import { NotificationsComponent } from './components/notifications/notifications.component';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { PublicComponent } from './components/public/public.component';
import { RadioButtonModule } from 'primeng/radiobutton';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { SafeHtmlPipe } from './pipes/safe-html.pipe';
import { SelectButtonModule } from 'primeng/selectbutton';
import { SelectComponent } from './components/select/select.component';
import { SpinnerComponent } from './components/spinner/spinner.component';
import { SwitchComponent } from './components/switch/switch.component';
import { TableDataExportComponent } from './components/table-data-export/table-data-export.component';
import { TableModule } from 'primeng/table';
import { TableNoRecFoundComponent } from './components/table-no-rec-found/table-no-rec-found.component';
import { TableRecActionsComponent } from './components/table-rec-actions/table-rec-actions.component';
import { TabViewModule } from 'primeng/tabview';
import { TemplateDirective } from './directives/template.directive';
import { TextTruncateComponent } from './components/text-truncate/text-truncate.component';
import { TieredMenuModule } from 'primeng/tieredmenu';
import { TooltipModule } from 'primeng/tooltip';
import { TranslateModule } from '@ngx-translate/core';
import { UserMenuComponent } from './components/user-menu/user-menu.component';
import { ViispMockComponent } from './components/viisp-mock/viisp-mock.component';
import { HeaderNavComponent } from './components/header-nav/header-nav.component';
import { EditorViewerComponent } from './components/editor-viewer/editor-viewer.component';
import { DividerComponent } from './components/divider/divider.component';
import { TableDataImportComponent } from './components/table-data-import/table-data-import.component';
import { AlertComponent } from './components/alert/alert.component';
import { ParseFileObjectPipe } from './pipes/parse-file-object.pipe';

@NgModule({
  declarations: [
    AccessibilityButtonComponent,
    AccessibilityComponent,
    BreadcrumbsComponent,
    BrowseSearchFormComponent,
    ButtonAddComponent,
    ButtonBackComponent,
    ButtonComponent,
    ButtonLoginComponent,
    ContentBoxComponent,
    DateAgoPipe,
    DividerComponent,
    DropdownComponent,
    EditFormComponent,
    EditorViewerComponent,
    FileUploadWithProgressComponent,
    FilterInputComponent,
    FilterManagerComponent,
    FormFieldComponent,
    FormFieldGroupComponent,
    HeaderNavComponent,
    IconComponent,
    InfoLineComponent,
    InputPasswordComponent,
    InternalComponent,
    LanguageSwitchComponent,
    LayoutComponent,
    LinkCardComponent,
    LoaderComponent,
    NotificationsBrowseComponent,
    NotificationsComponent,
    PublicComponent,
    S2DatePipe,
    SafeHtmlPipe,
    ParseFileObjectPipe,
    SelectComponent,
    SpinnerComponent,
    SwitchComponent,
    TableDataExportComponent,
    TableDataImportComponent,
    TableNoRecFoundComponent,
    TableRecActionsComponent,
    TemplateDirective,
    TextTruncateComponent,
    UserMenuComponent,
    ViispMockComponent,
    AlertComponent,
  ],
  imports: [
    ButtonModule,
    CalendarModule,
    CommonModule,
    ConfirmDialogModule,
    DialogModule,
    FileUploadModule,
    FileUploadComponent,
    FontAwesomeModule,
    FormsModule,
    InputNumberModule,
    InputSwitchModule,
    OverlayPanelModule,
    RadioButtonModule,
    ReactiveFormsModule,
    SelectButtonModule,
    TableModule,
    TabViewModule,
    TieredMenuModule,
    TooltipModule,
    TranslateModule,
  ],
  exports: [
    AccessibilityButtonComponent,
    AccessibilityComponent,
    BreadcrumbsComponent,
    BrowseSearchFormComponent,
    ButtonAddComponent,
    ButtonBackComponent,
    ButtonComponent,
    ButtonLoginComponent,
    ContentBoxComponent,
    DividerComponent,
    DropdownComponent,
    EditFormComponent,
    EditorViewerComponent,
    FileUploadWithProgressComponent,
    FilterInputComponent,
    FormFieldComponent,
    FormFieldGroupComponent,
    HeaderNavComponent,
    IconComponent,
    InfoLineComponent,
    InputPasswordComponent,
    InternalComponent,
    LanguageSwitchComponent,
    LayoutComponent,
    LinkCardComponent,
    LoaderComponent,
    NotificationsBrowseComponent,
    NotificationsComponent,
    PublicComponent,
    S2DatePipe,
    SafeHtmlPipe,
    ParseFileObjectPipe,
    SelectComponent,
    SpinnerComponent,
    SwitchComponent,
    TableDataExportComponent,
    TableDataImportComponent,
    TableNoRecFoundComponent,
    TableRecActionsComponent,
    TemplateDirective,
    TextTruncateComponent,
    TranslateModule,
    UserMenuComponent,
    ViispMockComponent,
    AlertComponent,
  ],
  providers: [DatePipe, S2DatePipe, CommonFormServices],
})
export class ItreeCommonsModule {}
