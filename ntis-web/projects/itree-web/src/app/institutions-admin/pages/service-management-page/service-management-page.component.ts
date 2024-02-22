import { Component, OnInit } from '@angular/core';
import { NtisServiceDetails, NtisServiceManagementItem } from '@itree-commons/src/lib/model/api/api';
import { booleanToString } from '@itree-commons/src/lib/utils/booleanToString';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationService } from 'primeng/api';
import { SRV_STS_ACTIVE, SRV_STS_INACTIVE, SRV_STS_SUSPENDED } from '../../../ntis-shared/constants/classifiers.const';
import { ServiceType } from '../../../ntis-shared/enums/classifiers.enums';
import { NtisTableRowActionsItem } from '../../../ntis-shared/models/table-row-actions';
import { InstitutionsAdminService } from '../../services/institutions-admin.service';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { TableModule } from 'primeng/table';
import { DialogModule } from 'primeng/dialog';
import { ServiceViewComponent } from '../../components/service-view/service-view.component';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';

interface NtisServiceManagementItemView {
  availableInNtisPortal: string;
  contractAvailable: string;
  isConfirmed: string;
  serviceArea: string;
  service: string;
  status: string;
  actions: NtisTableRowActionsItem[];
}

@Component({
  selector: 'app-service-management-page',
  templateUrl: './service-management-page.component.html',
  styleUrls: ['./service-management-page.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, NtisSharedModule, TableModule, DialogModule, ServiceViewComponent],
})
export class ServiceManagementPageComponent implements OnInit {
  readonly translationsReference = 'institutionsAdmin.pages.serviceManagement';
  readonly notRegistered = 'Neregistruota';
  data: NtisServiceManagementItem[] = [];
  dataView: NtisServiceManagementItemView[] = [];
  serviceDetails: NtisServiceDetails;
  showServiceDetails = false;
  serviceDetailsConfirmMode = false;
  unconfirmedServices: string[] = [];

  cols: string[] = ['service', 'status', 'availableInNtisPortal', 'contractAvailable', 'serviceArea', 'isConfirmed'];

  constructor(
    private institutionsAdminService: InstitutionsAdminService,
    private confirmationService: ConfirmationService,
    private translateService: TranslateService,
    private commonFormServices: CommonFormServices
  ) {}

  ngOnInit(): void {
    this.loadData(false);
  }

  loadData(checkAlert: boolean): void {
    this.unconfirmedServices = [];
    let yes: string = '';
    let no: string = '';
    this.institutionsAdminService.getServicesInfo().subscribe((result) => {
      this.translateService.get('common.generalUse.yes').subscribe((translation: string) => {
        yes = translation;
      });
      this.translateService.get('common.generalUse.no').subscribe((translation: string) => {
        no = translation;
      });
      this.data = result;
      this.dataView = result.map((item) => {
        const itemActions: NtisTableRowActionsItem[] = [];
        if (!item.isConfirmed && item.status === SRV_STS_ACTIVE && typeof item.srvId === 'number') {
          itemActions.push({
            icon: { iconStyle: 'solid', iconName: 'faCircleCheck' },
            actionName: 'confirm',
            iconTheme: 'success',
            action: () => {
              this.institutionsAdminService.getServiceDetails(item.srvId).subscribe((serviceDetails) => {
                this.serviceDetails = serviceDetails;
                this.serviceDetailsConfirmMode = true;
                this.showServiceDetails = true;
              });
            },
          });
        }
        if ((item.isConfirmed && item.status === SRV_STS_ACTIVE) || (item.status === SRV_STS_SUSPENDED && item.srvId)) {
          itemActions.push({
            icon: { iconStyle: 'solid', iconName: 'faEye' },
            actionName: 'view',
            iconTheme: 'default',
            action: () => {
              this.institutionsAdminService.getServiceDetails(item.srvId).subscribe((serviceDetails) => {
                this.serviceDetails = serviceDetails;
                this.serviceDetailsConfirmMode = false;
                this.showServiceDetails = true;
              });
            },
          });
        }
        if (item.status === SRV_STS_ACTIVE) {
          itemActions.push({
            icon: { iconStyle: 'solid', iconName: 'faPencil' },
            iconTheme: 'default',
            actionName: 'update',
            url: `/${this.commonFormServices.router.url}/${NtisRoutingConst.INST_SERVICE_DESCRIPTION}/${item.sriId}`,
          });
          itemActions.push({
            icon: { iconStyle: 'solid', iconName: 'faCircleXmark' },
            actionName: 'deregister',
            iconTheme: 'risk',
            action: () => {
              this.translateService
                .get(this.translationsReference + '.deregisterMessage')
                .subscribe((translation: string) => {
                  this.confirmationService.confirm({
                    message: translation,
                    accept: () => {
                      this.institutionsAdminService.deregisterService(item.sriId).subscribe(() => {
                        this.loadData(true);
                      });
                    },
                  });
                });
            },
          });
        }
        if (item.status === SRV_STS_SUSPENDED || item.status === SRV_STS_INACTIVE) {
          itemActions.push({
            icon: { iconStyle: 'solid', iconName: 'faFileLines' },
            actionName: 'createServiceRequest',
            iconTheme: 'default',
            url: `/${this.commonFormServices.router.url}/${
              NtisRoutingConst.SERVICE_REQUEST_CREATE
            }/${ServiceType.serviceProvider.toLowerCase()}`,
          });
        }
        if (item.status === SRV_STS_ACTIVE && !item.isConfirmed) {
          this.unconfirmedServices.push(item.serviceName);
        }
        return {
          availableInNtisPortal: booleanToString(item.availableInNtisPortal, yes, no),
          contractAvailable: booleanToString(item.contractAvailable, yes, no),
          isConfirmed: booleanToString(item.isConfirmed, yes, no),
          serviceArea:
            item.lithuanianLevel === true
              ? 'serviceAreaLithuania'
              : item.lithuanianLevel === false
              ? 'serviceAreaMunicipalities'
              : null,
          service: item.serviceName,
          status: item.statusName,
          actions: itemActions,
        };
      });

      if (checkAlert) {
        this.institutionsAdminService.serviceStatusSubject.next();
      }
    });
  }

  handleConfirmService(): void {
    if (this.serviceDetails?.srvId) {
      this.institutionsAdminService.confirmService(this.serviceDetails.srvId).subscribe(() => {
        this.commonFormServices.translate.get('common.message.serviceConfirmed').subscribe((translate: string) => {
          this.commonFormServices.appMessages.showSuccess('', translate);
        });
        this.showServiceDetails = false;
        this.loadData(true);
      });
    }
  }

  onReturn(): void {
    void this.commonFormServices.router.navigate([
      RoutingConst.INTERNAL,
      NtisRoutingConst.INSTITUTIONS,
      NtisRoutingConst.INST_SERVICE_PROVIDER_SETTINGS,
    ]);
  }
}
