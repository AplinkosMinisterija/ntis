import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DATA_TYPE_NUMBER, DATA_TYPE_STRING } from '@itree-commons/src/constants/classificators.constants';
import { PRIEZIURA, TYRIMAI, VEZIMAS } from '@itree-web/src/app/ntis-shared/constants/classifiers.const';
import { NTIS_SP_DASHBOARD } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { GraphDataPeriod, OrderStatus } from 'projects/itree-web/src/app/ntis-shared/enums/classifiers.enums';
import { NtisTechSupportService } from '../../../shared/services/ntis-tech-support.service';
import { ServiceProviderDashboardBrowse } from '../../models/browse-pages';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { FormsModule } from '@angular/forms';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TableModule } from 'primeng/table';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';

@Component({
  selector: 'app-service-provider-dashboard-page',
  templateUrl: './service-provider-dashboard-page.component.html',
  styleUrls: ['./service-provider-dashboard-page.component.scss'],
  standalone: true,
  imports: [CommonModule, FontAwesomeModule, FormsModule, ItreeCommonsModule, NtisSharedModule, TableModule],
})
export class ServiceProviderDashboardPageComponent implements OnInit {
  readonly translationsReference = 'wteMaintenanceAndOperation.techSupport.serviceProvider.pages.dashboard';
  readonly formCode = NTIS_SP_DASHBOARD;
  // classifiers
  readonly VEZIMAS = VEZIMAS;
  readonly PRIEZIURA = PRIEZIURA;
  readonly TYRIMAI = TYRIMAI;
  readonly confirmed = OrderStatus.confirmed;
  readonly submitted = OrderStatus.submitted;
  readonly lastSevenDays = GraphDataPeriod.lastSevenDays;
  readonly thisYear = GraphDataPeriod.thisYear;

  userCanCreate: boolean;
  userCanRead: boolean;
  serviceProvider: string;
  orders: ServiceProviderDashboardBrowse[] = [];
  isDisposalAvailable: boolean = false;
  // doughnut charts
  labels: string[] = [];
  submittedOrdersCount: number[] = [];
  confirmedOrdersCount: number[] = [];
  showConfirmedChart: boolean = false;
  currentConfirmedOrders: string;
  currentSubmittedOrders: string;
  showSubmittedChart: boolean = false;
  // bar chart - finished orders
  placeHolderFP: string;
  labelsInPeriod: string[];
  finishedCountInPeriod: number[];
  // bar chart - discharged sludge
  placeHolderSL: string;
  labelsForSludge: string[];
  sludgeSum: number[];
  sludgeColors: string[] = ['rgb(39, 174, 97)'];

  cols: TableColumn[] = [
    { field: 'service', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'total_submitted', export: true, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'total_confirmed', export: true, visible: true, type: DATA_TYPE_NUMBER },
  ];

  constructor(
    private authService: AuthService,
    private commonFormService: CommonFormServices,
    private router: Router,
    private techSupp: NtisTechSupportService,
    public faIconsService: FaIconsService
  ) {
    this.userCanCreate = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_CREATE);
    this.userCanRead = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_READ);
    this.commonFormService.translate
      .get(this.translationsReference + '.current_confirmed_orders')
      .subscribe((translation: string) => {
        this.currentConfirmedOrders = translation;
      });
    this.commonFormService.translate
      .get(this.translationsReference + '.current_submitted_orders')
      .subscribe((translation: string) => {
        this.currentSubmittedOrders = translation;
      });
  }

  ngOnInit(): void {
    this.loadTableData();
    this.loadFinishedPeriodData(this.lastSevenDays);
    this.loadSludgePeriodData(this.lastSevenDays);
  }

  loadTableData(): void {
    this.techSupp.getSpDasboard().subscribe((result) => {
      if (result.data != null) {
        this.serviceProvider = result.data[0]?.service_provider;
      }
      result.data.forEach((row) => {
        if (row.total_confirmed > 0 || row.total_submitted > 0) {
          this.labels.push(row.service);
          this.confirmedOrdersCount.push(row.total_confirmed > 0 ? row.total_confirmed : null);
          this.submittedOrdersCount.push(row.total_submitted > 0 ? row.total_submitted : null);
          if (row.total_confirmed > 0) {
            this.showConfirmedChart = true;
          }
          if (row.total_submitted > 0) {
            this.showSubmittedChart = true;
          }
        }
        if (row.srv_type === this.VEZIMAS) {
          this.isDisposalAvailable = true;
        }
      });
      this.orders = result.data;
    });
  }

  loadFinishedPeriodData(period: string): void {
    period == null ? this.lastSevenDays : period;
    this.placeHolderFP = this.translationsReference + '.' + period;
    this.finishedCountInPeriod = [];
    this.labelsInPeriod = [];
    this.techSupp.getSpFinishedOrders(period).subscribe((result) => {
      if (period == this.thisYear) {
        this.commonFormService.translate
          .get('common.date.monthByNumber')
          .subscribe((translations: Record<string, string>) => {
            result.data.forEach((row) => {
              this.labelsInPeriod.push(translations[row.label]);
              this.finishedCountInPeriod.push(row.order_count);
            });
          });
      } else {
        result.data.forEach((row) => {
          this.labelsInPeriod.push(row.label);
          this.finishedCountInPeriod.push(row.order_count);
        });
      }
    });
  }

  loadSludgePeriodData(period: string): void {
    period == null ? this.lastSevenDays : period;
    this.placeHolderSL = this.translationsReference + '.' + period;
    this.sludgeSum = [];
    this.labelsForSludge = [];
    this.techSupp.getSpDischargedSludge(period).subscribe((result) => {
      if (period == this.thisYear) {
        this.commonFormService.translate
          .get('common.date.monthByNumber')
          .subscribe((translations: Record<string, string>) => {
            result.data.forEach((row) => {
              this.labelsForSludge.push(translations[row.label]);
              this.sludgeSum.push(row.sludge_amount);
            });
          });
      } else {
        result.data.forEach((row) => {
          this.labelsForSludge.push(row.label);
          this.sludgeSum.push(row.sludge_amount);
        });
      }
    });
  }

  navigateToConfirmedOrders(row: ServiceProviderDashboardBrowse): void {
    if (row.srv_type == VEZIMAS) {
      void this.router.navigate([
        RoutingConst.INTERNAL,
        NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
        NtisRoutingConst.TECH_SUPPORT,
        NtisRoutingConst.DISPOSAL_ORDERS_LIST,
        this.confirmed,
      ]);
    } else if (row.srv_type == PRIEZIURA) {
      void this.router.navigate([
        RoutingConst.INTERNAL,
        NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
        NtisRoutingConst.TECH_SUPPORT,
        NtisRoutingConst.TECH_ORDERS_LIST,
        this.confirmed,
      ]);
    } else if (row.srv_type == TYRIMAI) {
      void this.router.navigate([
        RoutingConst.INTERNAL,
        NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
        NtisRoutingConst.RESEARCH,
        NtisRoutingConst.SP_RESEARCH_LIST,
        this.confirmed,
      ]);
    }
  }
  navigateToSubmittedOrders(row: ServiceProviderDashboardBrowse): void {
    if (row.srv_type == VEZIMAS) {
      void this.router.navigate([
        RoutingConst.INTERNAL,
        NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
        NtisRoutingConst.TECH_SUPPORT,
        NtisRoutingConst.DISPOSAL_ORDERS_LIST,
        this.submitted,
      ]);
    } else if (row.srv_type == PRIEZIURA) {
      void this.router.navigate([
        RoutingConst.INTERNAL,
        NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
        NtisRoutingConst.TECH_SUPPORT,
        NtisRoutingConst.TECH_ORDERS_LIST,
        this.submitted,
      ]);
    } else if (row.srv_type == TYRIMAI) {
      void this.router.navigate([
        RoutingConst.INTERNAL,
        NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
        NtisRoutingConst.RESEARCH,
        NtisRoutingConst.SP_RESEARCH_LIST,
        this.submitted,
      ]);
    }
  }

  navigateToNewOrder(row: ServiceProviderDashboardBrowse): void {
    if (row.srv_type != TYRIMAI) {
      void this.router.navigate([
        RoutingConst.INTERNAL,
        NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
        NtisRoutingConst.TECH_SUPPORT,
        row.srv_type === PRIEZIURA ? NtisRoutingConst.TECH_ORDERS_LIST : NtisRoutingConst.DISPOSAL_ORDERS_LIST,
        NtisRoutingConst.ORDER_OUTSIDE_NTIS_CREATE,
      ]);
    } else {
      void this.router.navigate([
        RoutingConst.INTERNAL,
        NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
        NtisRoutingConst.RESEARCH,
        NtisRoutingConst.SP_RESEARCH_LIST,
        NtisRoutingConst.RESEARCH_OUTSIDE_NTIS,
      ]);
    }
  }

  navigateToOrderList(row: ServiceProviderDashboardBrowse): void {
    if (row.srv_type == VEZIMAS) {
      void this.router.navigate([
        RoutingConst.INTERNAL,
        NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
        NtisRoutingConst.TECH_SUPPORT,
        NtisRoutingConst.DISPOSAL_ORDERS_LIST,
      ]);
    } else if (row.srv_type == PRIEZIURA) {
      void this.router.navigate([
        RoutingConst.INTERNAL,
        NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
        NtisRoutingConst.TECH_SUPPORT,
        NtisRoutingConst.TECH_ORDERS_LIST,
      ]);
    } else if (row.srv_type == TYRIMAI) {
      void this.router.navigate([
        RoutingConst.INTERNAL,
        NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
        NtisRoutingConst.RESEARCH,
        NtisRoutingConst.SP_RESEARCH_LIST,
      ]);
    }
  }
}
