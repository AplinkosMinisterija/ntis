import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { MissingTranslationHandler, TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { AppMissingTranslationHandler } from '@itree/ngx-s2-commons';
import { InputSwitchModule } from 'primeng/inputswitch';
import { ButtonModule } from 'primeng/button';
import { CalendarModule } from 'primeng/calendar';
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';
import { EditorModule } from 'primeng/editor';
import { InputNumberModule } from 'primeng/inputnumber';
import { SelectButtonModule } from 'primeng/selectbutton';
import { TableModule } from 'primeng/table';
import { TooltipModule } from 'primeng/tooltip';
import { TreeModule } from 'primeng/tree';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { HttpLoaderFactory } from '../app.module';
import { CacheManagerPageComponent } from './admin-pages/cache-manager-page/cache-manager-page.component';
import { DictionariesBrowsePageComponent } from './admin-pages/classifier/dictionaries-browse-page/dictionaries-browse-page.component';
import { FormEditPageComponent } from './admin-pages/forms/form-edit-page/form-edit-page.component';
import { FormsBrowsePageComponent } from './admin-pages/forms/forms-browse-page/forms-browse-page.component';
import { MenuStructureBrowsePageComponent } from './admin-pages/menu/menu-structure-browse-page/menu-structure-browse-page.component';
import { MenuStructureEditPageComponent } from './admin-pages/menu/menu-structure-edit-page/menu-structure-edit-page.component';
import { MenuStructureTreePageComponent } from './admin-pages/menu/menu-structure-tree-page/menu-structure-tree-page.component';
import { OrganizationEditPageComponent } from './admin-pages/organizations/organization-edit-page/organization-edit-page.component';
import { OrganizationsBrowsePageComponent } from './admin-pages/organizations/organizations-browse-page/organizations-browse-page.component';
import { PersonsBrowsePageComponent } from './admin-pages/persons/persons-browse-page/persons-browse-page.component';
import { PersonsEditPageComponent } from './admin-pages/persons/persons-edit-page/persons-edit-page.component';
import { PropertiesBrowsePageComponent } from './admin-pages/properties/properties-browse-page/properties-browse-page.component';
import { PropertiesEditPageComponent } from './admin-pages/properties/properties-edit-page/properties-edit-page.component';
import { RoleActionsPageComponent } from './admin-pages/roles/role-actions-page/role-actions-page.component';
import { RoleEditPageComponent } from './admin-pages/roles/role-edit-page/role-edit-page.component';
import { RolesBrowsePageComponent } from './admin-pages/roles/roles-browse-page/roles-browse-page.component';
import { UserSessionsClosedBrowsePageComponent } from './admin-pages/sessions/user-sessions-closed-browse-page/user-sessions-closed-browse-page.component';
import { UserSessionOpenBrowsePageComponent } from './admin-pages/user-session-open-browse-page/user-session-open-browse-page.component';
import { UserRolesPageComponent } from './admin-pages/users-roles/user-roles-page/user-roles-page.component';
import { UserEditPageComponent } from './admin-pages/users/user-edit-page/user-edit-page.component';
import { UsersBrowsePageComponent } from './admin-pages/users/users-browse-page/users-browse-page.component';
import { adminRoutes } from './admin-routing.module';
import { TabMenuModule } from 'primeng/tabmenu';
import { TabViewModule } from 'primeng/tabview';
import { RoleActionsRoleBrowsePageComponent } from './admin-pages/roles/role-actions-role-page/role-actions-role-page.component';
import { TemplatesBrowsePageComponent } from './admin-pages/templates/templates-browse-page/templates-browse-page.component';
import { TemplateEditPageComponent } from './admin-pages/templates/template-edit-page/template-edit-page.component';
import { TemplateTextEditComponent } from './components/template-text-edit/template-text-edit.component';
import { JobsBrowsePageComponent } from './admin-pages/jobs/jobs-browse-page/jobs-browse-page.component';
import { JobEditPageComponent } from './admin-pages/jobs/job-edit-page/job-edit-page.component';
import { JobRequestsBrowsePageComponent } from './admin-pages/jobs/job-requests-browse-page/job-requests-browse-page.component';
import { DictionariesEditPageComponent } from './admin-pages/classifier/dictionaries-edit-page/dictionaries-edit-page.component';
import { AuditableTablesBrowsePageComponent } from './admin-pages/audit/auditable-tables-browse-page/auditable-tables-browse-page.component';
import { AuditableTableRecBrowsePageComponent } from './admin-pages/audit/auditable-table-rec-browse-page/auditable-table-rec-browse-page.component';
import { CodesTranslationsPageComponent } from './admin-pages/classifier/codes-translations-page/codes-translations-page.component';
import { CodeEditPageComponent } from './admin-pages/classifier/code-edit-page/code-edit-page.component';
import { RoleUsersPageComponent } from './admin-pages/roles/role-users-page/role-users-page.component';
import { RoleOrganizationsPageComponent } from './admin-pages/roles/role-organizations-page/role-organizations-page.component';
import { OrganizationsRolesPageComponent } from './admin-pages/organizations-roles/organizations-roles-page/organizations-roles-page.component';
import { OrganizationUserRolesPageComponent } from './admin-pages/organization-user-roles/organization-user-roles-page/organization-user-roles-page.component';
import { OrganizationUsersPageComponent } from './admin-pages/organizations/organization-users-page/organization-users-page.component';
import { UserOrganizationsPageComponent } from './admin-pages/users/user-organizations-page/user-organizations-page.component';
import { NewsBrowseComponent } from './admin-pages/news/news-browse/news-browse.component';
import { NewsEditComponent } from './admin-pages/news/news-edit/news-edit.component';
import { NewsViewComponent } from './admin-pages/news/news-view/news-view.component';
import { NewsCardComponent } from './components/news-card/news-card.component';
import { CKEditorModule } from '@ckeditor/ckeditor5-angular';
import { UsersApiKeysBrowsePageComponent } from './admin-pages/users-api-keys/users-api-keys-browse-page/users-api-keys-browse-page.component';
import { UserApiKeyEditPageComponent } from './admin-pages/users-api-keys/user-api-key-edit-page/user-api-key-edit-page.component';
import { NtisSharedModule } from '../ntis-shared/ntis-shared.module';

@NgModule({
  declarations: [
    AuditableTableRecBrowsePageComponent,
    AuditableTablesBrowsePageComponent,
    CacheManagerPageComponent,
    CodeEditPageComponent,
    CodesTranslationsPageComponent,
    DictionariesBrowsePageComponent,
    DictionariesEditPageComponent,
    FormEditPageComponent,
    FormsBrowsePageComponent,
    JobEditPageComponent,
    JobRequestsBrowsePageComponent,
    JobsBrowsePageComponent,
    MenuStructureBrowsePageComponent,
    MenuStructureEditPageComponent,
    MenuStructureTreePageComponent,
    NewsBrowseComponent,
    NewsEditComponent,
    NewsViewComponent,
    OrganizationEditPageComponent,
    OrganizationsBrowsePageComponent,
    OrganizationsRolesPageComponent,
    OrganizationUserRolesPageComponent,
    OrganizationUsersPageComponent,
    PersonsBrowsePageComponent,
    PersonsEditPageComponent,
    PropertiesBrowsePageComponent,
    PropertiesEditPageComponent,
    RoleActionsPageComponent,
    RoleActionsRoleBrowsePageComponent,
    RoleEditPageComponent,
    RoleOrganizationsPageComponent,
    RolesBrowsePageComponent,
    RoleUsersPageComponent,
    TemplateEditPageComponent,
    TemplatesBrowsePageComponent,
    TemplateTextEditComponent,
    UserEditPageComponent,
    UserOrganizationsPageComponent,
    UserRolesPageComponent,
    UsersBrowsePageComponent,
    UserSessionOpenBrowsePageComponent,
    UserSessionsClosedBrowsePageComponent,
    NewsCardComponent,
    UsersApiKeysBrowsePageComponent,
    UserApiKeyEditPageComponent,
  ],
  imports: [
    ButtonModule,
    CalendarModule,
    CommonModule,
    CKEditorModule,
    DialogModule,
    DropdownModule,
    EditorModule,
    FontAwesomeModule,
    FormsModule,
    InputNumberModule,
    InputSwitchModule,
    ItreeCommonsModule,
    ReactiveFormsModule,
    SelectButtonModule,
    TableModule,
    TabMenuModule,
    TabViewModule,
    TooltipModule,
    NtisSharedModule,
    TreeModule,
    RouterModule.forChild(adminRoutes),
    TranslateModule.forChild({
      missingTranslationHandler: { provide: MissingTranslationHandler, useClass: AppMissingTranslationHandler },
      useDefaultLang: true,
      defaultLanguage: 'lt',
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient],
      },
      extend: true,
    }),
  ],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AdminModule {}
