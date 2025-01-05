import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ConfirmExitGuard } from '../guard/confirm-exit.guard';
import { CacheManagerPageComponent } from './admin-pages/cache-manager-page/cache-manager-page.component';
import { DictionariesBrowsePageComponent } from './admin-pages/classifier/dictionaries-browse-page/dictionaries-browse-page.component';
import { DictionariesEditPageComponent } from './admin-pages/classifier/dictionaries-edit-page/dictionaries-edit-page.component';
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
import { PersonEditResolver } from './admin-resolvers/person-edit.resolver';
import { RoleActionsPageComponent } from './admin-pages/roles/role-actions-page/role-actions-page.component';
import { RoleActionsRoleBrowsePageComponent } from './admin-pages/roles/role-actions-role-page/role-actions-role-page.component';
import { RoleEditPageComponent } from './admin-pages/roles/role-edit-page/role-edit-page.component';
import { RolesBrowsePageComponent } from './admin-pages/roles/roles-browse-page/roles-browse-page.component';
import { UserSessionsClosedBrowsePageComponent } from './admin-pages/sessions/user-sessions-closed-browse-page/user-sessions-closed-browse-page.component';
import { TemplateEditPageComponent } from './admin-pages/templates/template-edit-page/template-edit-page.component';
import { TemplatesBrowsePageComponent } from './admin-pages/templates/templates-browse-page/templates-browse-page.component';
import { UserSessionOpenBrowsePageComponent } from './admin-pages/user-session-open-browse-page/user-session-open-browse-page.component';
import { UserRolesPageComponent } from './admin-pages/users-roles/user-roles-page/user-roles-page.component';
import { UserEditPageComponent } from './admin-pages/users/user-edit-page/user-edit-page.component';
import { UsersBrowsePageComponent } from './admin-pages/users/users-browse-page/users-browse-page.component';
import { JobsBrowsePageComponent } from './admin-pages/jobs/jobs-browse-page/jobs-browse-page.component';
import { JobRequestsBrowsePageComponent } from './admin-pages/jobs/job-requests-browse-page/job-requests-browse-page.component';
import { JobEditPageComponent } from './admin-pages/jobs/job-edit-page/job-edit-page.component';
import { AuditableTablesBrowsePageComponent } from './admin-pages/audit/auditable-tables-browse-page/auditable-tables-browse-page.component';
import { AuditableTableRecBrowsePageComponent } from './admin-pages/audit/auditable-table-rec-browse-page/auditable-table-rec-browse-page.component';
import { CodeEditPageComponent } from './admin-pages/classifier/code-edit-page/code-edit-page.component';
import { TranslationsResolver } from './admin-resolvers/translations.resolver';
import { CodesTranslationsPageComponent } from './admin-pages/classifier/codes-translations-page/codes-translations-page.component';
import { RoleUsersPageComponent } from './admin-pages/roles/role-users-page/role-users-page.component';
import { RoleOrganizationsPageComponent } from './admin-pages/roles/role-organizations-page/role-organizations-page.component';
import { OrganizationsRolesPageComponent } from './admin-pages/organizations-roles/organizations-roles-page/organizations-roles-page.component';
import { SprRoutes } from '@itree-commons/src/lib/types/routing';
import { OrganizationUserRolesPageComponent } from './admin-pages/organization-user-roles/organization-user-roles-page/organization-user-roles-page.component';
import { OrganizationUsersPageComponent } from './admin-pages/organizations/organization-users-page/organization-users-page.component';
import { UserOrganizationsPageComponent } from './admin-pages/users/user-organizations-page/user-organizations-page.component';
import { NotificationsBrowseComponent } from '@itree-commons/src/pages/notifications/notifications-browse/notifications-browse.component';
import {
  SPR_API_KEYS_LIST,
  SPR_AUDITABLE_TABLES,
  SPR_CACHE_MANAGER,
  SPR_FORMS_LIST,
  SPR_FORM_EDIT,
  SPR_JOBS_LIST,
  SPR_JOB_EDIT,
  SPR_JOB_REQUESTS,
  SPR_MENU_STR_EDIT,
  SPR_MENU_STR_LIST,
  SPR_MENU_STR_TREE,
  SPR_NOTIFICATIONS,
  SPR_ORGANIZATION_ROLES_LIST,
  SPR_ORGS_BROWSE,
  SPR_ORGS_EDIT,
  SPR_ORG_USERS_LIST,
  SPR_ORG_USER_ROLES_LIST,
  SPR_PERSON_LIST,
  SPR_PROPERTIES_LIST,
  SPR_PROPERTY_EDIT,
  SPR_REF_CLASSIFIER_EDIT,
  SPR_REF_CLASSIFIER_LIST,
  SPR_REF_DICTIONARIES_LIST,
  SPR_REF_DICTIONARY_EDIT,
  SPR_ROLES_LIST,
  SPR_ROLE_ACTIONS_LIST,
  SPR_ROLE_ACTIONS_ROLE_LIST,
  SPR_ROLE_EDIT,
  SPR_ROLE_ORGANIZATIONS_LIST,
  SPR_ROLE_USERS_LIST,
  SPR_TEMPLATES_LIST,
  SPR_TEMPLATE_EDIT,
  SPR_USERS_BROWSE,
  SPR_USERS_EDIT,
  SPR_USER_ORGS_LIST,
  SPR_USER_ROLES_LIST,
  SPR_USR_SES_CLSD_LST,
  SPR_USR_SES_OPN_LST,
} from '@itree-commons/src/constants/forms.constants';
import { NewsBrowseComponent } from './admin-pages/news/news-browse/news-browse.component';
import { NewsEditComponent } from './admin-pages/news/news-edit/news-edit.component';
import { NewsViewComponent } from './admin-pages/news/news-view/news-view.component';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NewsEditResolver } from './admin-resolvers/news-edit.resolver';
import { NewsViewResolver } from './admin-resolvers/news-view.resolver';
import { UsersApiKeysBrowsePageComponent } from './admin-pages/users-api-keys/users-api-keys-browse-page/users-api-keys-browse-page.component';
import { UserApiKeyEditPageComponent } from './admin-pages/users-api-keys/user-api-key-edit-page/user-api-key-edit-page.component';
import { UserApiKeyResolver } from './admin-resolvers/user-api-key-edit.resolver';
import { cacheManagerPageResolver } from './admin-pages/cache-manager-page/cache-manager-page.resolver';
import { NtisRoutingConst } from '../ntis-shared/constants/ntis-routing.const';
import { NTIS_REVIEWS_FOR_ADMIN_LIST, NTIS_REVIEWS_FOR_SRV_LIST, SYSTEM_WORKS_EDIT } from '../ntis-shared/constants/forms.const';

export const adminRoutes: SprRoutes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: `${RoutingConst.SPARK_PROPERTIES_BROWSE}`,
  },
  {
    path: '',
    data: {
      breadcrumb: 'common.generalUse.systemAdministration',
      breadcrumbIcon: { iconStyle: 'fas', iconName: 'faCogs' },
      breadcrumbUrl: null,
    },
    children: [
      {
        path: RoutingConst.SPARK_CACHE_MANAGER_BROWSE,
        component: CacheManagerPageComponent,
        data: { formCode: SPR_CACHE_MANAGER },
        resolve: {
          [RoutingConst.DATA]: cacheManagerPageResolver,
        },
      },
      {
        path: NtisRoutingConst.PASL_REVIEWS,
        data: { formCode: NTIS_REVIEWS_FOR_SRV_LIST, breadcrumb: '' },
        loadComponent: () =>
          import('../institutions-admin/pages/reviews-for-srv-list/reviews-for-srv-list.component').then(
            (m) => m.ReviewsForSrvListComponent
          ),
      },
      {
        path: NtisRoutingConst.SYSTEM_WORKS,
        data: { formCode: SYSTEM_WORKS_EDIT, breadcrumb: '' },
        loadComponent: () =>
          import('./admin-pages/system-works-edit/system-works-edit.component').then(
            (m) => m.SystemWorksEditComponent
          ),
      },
      {
        path: NtisRoutingConst.REVIEWS,
        data: { formCode: NTIS_REVIEWS_FOR_ADMIN_LIST },
        loadComponent: () =>
          import('./admin-pages/reviews-for-admin-list/reviews-for-admin-list.component').then(
            (m) => m.ReviewsForAdminListComponent
          ),
      },
      {
        path: RoutingConst.SPARK_PROPERTIES_BROWSE,
        data: { breadcrumb: 'pages.sprParametersBrowse.headerText' },
        children: [
          {
            path: '',
            component: PropertiesBrowsePageComponent,
            data: { formCode: SPR_PROPERTIES_LIST },
          },
          {
            path: RoutingConst.EDIT_WITH_ID,
            component: PropertiesEditPageComponent,
            pathMatch: 'full',
            canDeactivate: [ConfirmExitGuard],
            data: { formCode: SPR_PROPERTY_EDIT },
          },
        ],
      },
      {
        path: RoutingConst.SPARK_MENU_STRUCTURE_BROWSE,
        data: { breadcrumb: 'pages.sprMenuStructure.headerText' },
        children: [
          { path: '', component: MenuStructureBrowsePageComponent, data: { formCode: SPR_MENU_STR_LIST } },
          {
            path: RoutingConst.EDIT_WITH_ID,
            component: MenuStructureEditPageComponent,
            pathMatch: 'full',
            canDeactivate: [ConfirmExitGuard],
            data: { formCode: SPR_MENU_STR_EDIT },
          },
        ],
      },
      {
        path: RoutingConst.SPARK_MENU_STRUCTURE_TREE,
        component: MenuStructureTreePageComponent,
        data: { breadcrumb: 'pages.sprMenuStructureTree.headerText', formCode: SPR_MENU_STR_TREE },
      },
      {
        path: RoutingConst.SPARK_REF_DICTIONARIES_BROWSE,
        data: { breadcrumb: 'pages.sprClassifierBrowse.headerText' },
        children: [
          { path: '', component: DictionariesBrowsePageComponent, data: { formCode: SPR_REF_DICTIONARIES_LIST } },
          {
            path: RoutingConst.EDIT_WITH_ID,
            component: DictionariesEditPageComponent,
            pathMatch: 'full',
            canDeactivate: [ConfirmExitGuard],
            data: { formCode: SPR_REF_DICTIONARY_EDIT },
          },
          {
            path: `${RoutingConst.SPARK_REF_CODES_BROWSE}/${RoutingConst.PARAM_ID}/:tableName`,
            data: { breadcrumb: 'pages.sprCodesTranslationsBrowse.headerText' },
            children: [
              {
                path: '',
                component: CodesTranslationsPageComponent,
                data: {
                  formCode: SPR_REF_CLASSIFIER_LIST,
                },
                resolve: {
                  translations: TranslationsResolver,
                },
              },
              {
                path: RoutingConst.EDIT_WITH_ID,
                component: CodeEditPageComponent,
                pathMatch: 'full',
                canDeactivate: [ConfirmExitGuard],
                data: { formCode: SPR_REF_CLASSIFIER_EDIT },
              },
            ],
          },
        ],
      },
      {
        path: RoutingConst.SPARK_FORMS_BROWSE,
        data: { breadcrumb: 'pages.sprFormsBrowse.headerText' },
        children: [
          { path: '', component: FormsBrowsePageComponent, data: { formCode: SPR_FORMS_LIST } },
          {
            path: RoutingConst.EDIT_WITH_ID,
            component: FormEditPageComponent,
            pathMatch: 'full',
            canDeactivate: [ConfirmExitGuard],
            data: { formCode: SPR_FORM_EDIT },
          },
        ],
      },
      {
        path: RoutingConst.SPARK_JOBS_BROWSE,
        data: { breadcrumb: 'pages.sprJobsBrowse.headerText' },
        children: [
          { path: '', pathMatch: 'full', component: JobsBrowsePageComponent, data: { formCode: SPR_JOBS_LIST } },
          {
            path: RoutingConst.EDIT_WITH_ID,
            component: JobEditPageComponent,
            pathMatch: 'full',
            canDeactivate: [ConfirmExitGuard],
            data: { formCode: SPR_JOB_EDIT },
          },
          {
            path: RoutingConst.SPARK_JOB_REQUESTS,
            data: { breadcrumb: 'common.generalUse.log' },
            children: [
              {
                path: '',
                pathMatch: 'full',
                component: JobRequestsBrowsePageComponent,
                data: { formCode: SPR_JOB_REQUESTS },
              },
              {
                path: RoutingConst.PARAM_ID,
                pathMatch: 'full',
                component: JobRequestsBrowsePageComponent,
                data: { formCode: SPR_JOB_REQUESTS },
              },
            ],
          },
        ],
      },
      {
        path: RoutingConst.SPARK_JOB_REQUESTS,
        data: { breadcrumb: 'common.generalUse.log' },
        children: [
          {
            path: '',
            pathMatch: 'full',
            component: JobRequestsBrowsePageComponent,
            data: { formCode: SPR_JOB_REQUESTS },
          },
          {
            path: RoutingConst.PARAM_ID,
            pathMatch: 'full',
            component: JobRequestsBrowsePageComponent,
            data: { formCode: SPR_JOB_REQUESTS },
          },
        ],
      },
      {
        path: RoutingConst.SPARK_ROLES_BROWSE,
        data: { breadcrumb: 'pages.sprRolesBrowse.headerText' },
        children: [
          { path: '', component: RolesBrowsePageComponent, data: { formCode: SPR_ROLES_LIST } },
          {
            path: RoutingConst.EDIT_WITH_ID,
            component: RoleEditPageComponent,
            pathMatch: 'full',
            canDeactivate: [ConfirmExitGuard],
            data: { formCode: SPR_ROLE_EDIT },
          },
          {
            path: `${RoutingConst.SPARK_ROLE_ACTIONS}/${RoutingConst.PARAM_ID}`,
            component: RoleActionsPageComponent,
            pathMatch: 'full',
            data: { breadcrumb: 'pages.sprRoleActions.headerText', formCode: SPR_ROLE_ACTIONS_LIST },
          },
          {
            path: `${RoutingConst.SPARK_ROLE_ACTIONS_ROLE}/${RoutingConst.PARAM_ID}`,
            component: RoleActionsRoleBrowsePageComponent,
            pathMatch: 'full',
            data: { breadcrumb: 'pages.sprUserRolesBrowse.headerTextRoles', formCode: SPR_ROLE_ACTIONS_ROLE_LIST },
          },
          {
            path: `${RoutingConst.SPARK_ROLE_USERS}/${RoutingConst.PARAM_ID}`,
            component: RoleUsersPageComponent,
            pathMatch: 'full',
            data: { breadcrumb: 'pages.sprRoleUsersBrowse.headerText', formCode: SPR_ROLE_USERS_LIST },
          },
          {
            path: `${RoutingConst.SPARK_ROLE_ORGANIZATIONS}/${RoutingConst.PARAM_ID}`,
            component: RoleOrganizationsPageComponent,
            pathMatch: 'full',
            data: { breadcrumb: 'pages.sprRoleOrganizations.headerText', formCode: SPR_ROLE_ORGANIZATIONS_LIST },
          },
        ],
      },
      {
        path: RoutingConst.SPARK_USERS_BROWSE,
        data: { breadcrumb: 'pages.sprUsersBrowse.headerText' },
        children: [
          { path: '', component: UsersBrowsePageComponent, data: { formCode: SPR_USERS_BROWSE } },
          {
            path: RoutingConst.EDIT_WITH_ID,
            component: UserEditPageComponent,
            pathMatch: 'full',
            canDeactivate: [ConfirmExitGuard],
            data: { formCode: SPR_USERS_EDIT },
          },
          {
            path: `${RoutingConst.SPARK_USERS_ROLES_BROWSE}/${RoutingConst.PARAM_ID}`,
            component: UserRolesPageComponent,
            pathMatch: 'full',
            data: { breadcrumb: 'pages.sprUserRolesBrowse.headerText', formCode: SPR_USER_ROLES_LIST },
          },
          {
            path: `${RoutingConst.SPARK_ORG_USER_ROLES}/${RoutingConst.PARAM_ID}`,
            component: OrganizationUserRolesPageComponent,
            pathMatch: 'full',
            data: { breadcrumb: 'pages.sprOrgUserRoles.headerText', formCode: SPR_ORG_USER_ROLES_LIST },
          },
          {
            path: `${RoutingConst.SPARK_USER_ORGS_LIST}/${RoutingConst.PARAM_ID}`,
            component: UserOrganizationsPageComponent,
            pathMatch: 'full',
            data: { breadcrumb: 'pages.sprUserOrgsList.headerText', formCode: SPR_USER_ORGS_LIST },
          },
        ],
      },

      {
        path: RoutingConst.SPARK_USER_SESSION_OPEN_BROWSE,
        component: UserSessionOpenBrowsePageComponent,
        data: { breadcrumb: 'pages.sprOpenSessionsBrowse.headerText', formCode: SPR_USR_SES_OPN_LST },
      },

      {
        path: RoutingConst.SPARK_USER_SESSION_CLOSED_BROWSE,
        component: UserSessionsClosedBrowsePageComponent,
        data: { breadcrumb: 'pages.sprClosedSessionsBrowse.headerText', formCode: SPR_USR_SES_CLSD_LST },
      },

      {
        path: RoutingConst.SPARK_ORGANIZATIONS_BROWSE,
        data: { breadcrumb: 'pages.sprOrganizationsBrowse.headerText' },
        children: [
          { path: '', component: OrganizationsBrowsePageComponent, data: { formCode: SPR_ORGS_BROWSE } },
          {
            path: RoutingConst.EDIT_WITH_ID,
            component: OrganizationEditPageComponent,
            pathMatch: 'full',
            canDeactivate: [ConfirmExitGuard],
            data: { formCode: SPR_ORGS_EDIT },
          },
          {
            path: `${RoutingConst.SPARK_ORGANIZATION_ROLES_BROWSE}/${RoutingConst.PARAM_ID}`,
            component: OrganizationsRolesPageComponent,
            pathMatch: 'full',
            data: { breadcrumb: 'pages.sprOrgAvailableRolesBrowse.headerText', formCode: SPR_ORGANIZATION_ROLES_LIST },
          },
          {
            path: `${RoutingConst.SPARK_ORG_USERS_LIST}/${RoutingConst.PARAM_ID}`,
            component: OrganizationUsersPageComponent,
            pathMatch: 'full',
            data: { breadcrumb: 'pages.sprOrgUsersList.headerText', formCode: SPR_ORG_USERS_LIST },
          },
        ],
      },
      {
        path: RoutingConst.SPARK_PERSONS_BROWSE,
        data: { breadcrumb: 'pages.sprPersonsBrowse.headerText' },
        children: [
          { path: '', component: PersonsBrowsePageComponent, data: { formCode: SPR_PERSON_LIST } },
          {
            path: RoutingConst.EDIT_WITH_ID,
            component: PersonsEditPageComponent,
            resolve: {
              personData: PersonEditResolver,
            },
            pathMatch: 'full',
            canDeactivate: [ConfirmExitGuard],
          },
        ],
      },

      {
        path: RoutingConst.SPARK_API_KEYS_LIST,
        data: { breadcrumb: 'pages.sprApiKeys.breadcrumb' },
        children: [
          { path: '', component: UsersApiKeysBrowsePageComponent, data: { formCode: SPR_API_KEYS_LIST } },
          {
            path: RoutingConst.EDIT_WITH_ID,
            component: UserApiKeyEditPageComponent,
            resolve: {
              [RoutingConst.DATA]: UserApiKeyResolver,
            },
            pathMatch: 'full',
            canDeactivate: [ConfirmExitGuard],
          },
        ],
      },

      {
        path: RoutingConst.SPARK_TEMPLATES_BROWSE,
        data: { breadcrumb: 'pages.sprTemplatesBrowse.headerText' },
        children: [
          {
            path: '',
            component: TemplatesBrowsePageComponent,
            data: { formCode: SPR_TEMPLATES_LIST },
          },
          {
            path: RoutingConst.EDIT_WITH_ID,
            component: TemplateEditPageComponent,
            pathMatch: 'full',
            canDeactivate: [ConfirmExitGuard],
            data: { formCode: SPR_TEMPLATE_EDIT },
          },
        ],
      },
      {
        path: RoutingConst.SPARK_AUDITABLE_TABLES_BROWSE,
        data: { breadcrumb: 'pages.sprAuditableTables.headerText', formCode: SPR_AUDITABLE_TABLES },
        children: [
          { path: '', component: AuditableTablesBrowsePageComponent },
          {
            path: `${RoutingConst.SPARK_AUDITABLE_TABLE_REC_BROWSE}/${RoutingConst.PARAM_ID}`,
            component: AuditableTableRecBrowsePageComponent,
            pathMatch: 'full',
            data: { breadcrumb: 'pages.sprAuditableTableRec.headerText' },
          },
          // {
          //   path: RoutingConst.EDIT_WITH_ID,
          //   component: AuditableTableEditPageComponent,
          //   pathMatch: 'full',
          //   data: { breadcrumb: 'pages.sprAuditableTableRec.headerText' },
          // },
        ],
      },
      {
        path: RoutingConst.SPARK_NOTIFICATIONS_LIST,
        data: { breadcrumb: 'pages.sprNotifications.headerText', formCode: SPR_NOTIFICATIONS },
        children: [{ path: '', component: NotificationsBrowseComponent }],
      },

      {
        path: RoutingConst.NEWS_LIST,
        children: [
          {
            path: '',
            children: [
              {
                path: `${RoutingConst.PARAM_TYPE}/${RoutingConst.VIEW_WITH_ID}`,
                component: NewsViewComponent,
                // data: {
                //   formCode: SPR_NEWS_VIEW,
                // },
                resolve: {
                  newsViewData: NewsViewResolver,
                },
              },
              {
                path: `${RoutingConst.PARAM_TYPE}/${RoutingConst.NEW}`,
                component: NewsEditComponent,
                // data: {
                //   formCode: SPR_NEWS_EDIT,
                // },
              },
              {
                path: `${RoutingConst.PARAM_TYPE}/${RoutingConst.EDIT_WITH_ID}`,
                component: NewsEditComponent,
                // data: {
                //   formCode: SPR_NEWS_EDIT,
                // },
                resolve: {
                  newsEditData: NewsEditResolver,
                },
              },
              {
                path: RoutingConst.PARAM_TYPE,
                component: NewsBrowseComponent,
                // data: {
                //   formCode: SPR_NEWS_LIST,
                // },
              },
            ],
          },
        ],
      },
    ],
  },
];

@NgModule({
  imports: [
    RouterModule.forRoot(adminRoutes, {
      scrollPositionRestoration: 'enabled',
    }),
  ],
  exports: [RouterModule],
  providers: [PersonEditResolver, TranslationsResolver],
})
export class AdminRoutingModule {}
