import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { DATA_TYPE_NUMBER, DATA_TYPE_STRING } from '@itree-commons/src/constants/classificators.constants';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { Subject, takeUntil } from 'rxjs';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { TableModule } from 'primeng/table';
import { GraphDataPeriod, SewageDeliveryStatus } from 'projects/itree-web/src/app/ntis-shared/enums/classifiers.enums';
import { SelectOption } from 'projects/itree-web/src/app/ntis-shared/models/wtf-search';
import { NtisTechSupportService } from '../../../shared/services/ntis-tech-support.service';
import { WmDashboardRow } from '../../models/browse-pages';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { NTIS_WM_DASHBOARD } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';

@Component({
  selector: 'app-water-manager-dashboard-page',
  templateUrl: './water-manager-dashboard-page.component.html',
  styleUrls: ['./water-manager-dashboard-page.component.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule, ItreeCommonsModule, NtisSharedModule, TableModule],
})
export class WaterManagerDashboardPageComponent implements OnInit {
  readonly translationsReference = 'wteMaintenanceAndOperation.techSupport.waterManager.pages.dashboard';
  readonly swg_sts_submitted = SewageDeliveryStatus.submitted;
  readonly lastSevenDays = GraphDataPeriod.lastSevenDays;
  readonly thisYear = GraphDataPeriod.thisYear;
  readonly destroy$ = new Subject<void>();

  wtoSelection: SelectOption[] = [];
  deliveries: WmDashboardRow[] = [];

  carrierLabels: string[] = [];
  totalSubmitted: number[] = [];
  submittedChartName: string;

  labelsInPeriod: string[] = [];
  confirmedCountInPeriod: number[] = [];
  sludgePlaceHolder: string = this.translationsReference + '.' + this.lastSevenDays;
  confirmedPlaceHolder: string = this.translationsReference + '.' + this.lastSevenDays;

  labelsForSludge: string[] = [];
  sludgeSum: number[] = [];
  sludgeColors: string[] = ['rgb(39, 174, 97)'];

  wto_id: string = '';

  cols: TableColumn[] = [
    { field: 'carrier', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'total_submitted', export: true, visible: true, type: DATA_TYPE_NUMBER },
  ];

  constructor(
    private techSupport: NtisTechSupportService,
    private router: Router,
    private commonFormService: CommonFormServices,
    private authService: AuthService
  ) {
    this.commonFormService.translate
      .get(this.translationsReference + '.submitted_deliveries')
      .subscribe((translation: string) => {
        this.submittedChartName = translation;
      });
  }

  ngOnInit(): void {
    this.techSupport.getWmFacilities().subscribe((result) => {
      this.wtoSelection = result.data;
    });

    this.authService.loadedFormActions$.pipe(takeUntil(this.destroy$)).subscribe(() => {
      if (this.authService.isFormActionEnabled(NTIS_WM_DASHBOARD, ActionsEnum.ACTIONS_READ)) {
        this.wto_id = null;
        this.loadDashboardPage(this.wto_id);
        this.changeConfirmedPeriod(this.lastSevenDays);
        this.changeSludgePeriod(this.lastSevenDays);
      }
    });
  }

  loadDashboardPage(id: string): void {
    this.loadTableData(id);
    this.loadConfirmedGraph(id);
    this.loadAcceptedSludgeGraph(id);
  }

  loadTableData(id: string): void {
    this.carrierLabels = [];
    this.totalSubmitted = [];
    this.techSupport.getWmDashboard(id).subscribe((result) => {
      this.deliveries = result.data;
      result.data.forEach((row) => {
        if (row.total_submitted > 0) {
          if (this.carrierLabels.length === 5) {
            this.commonFormService.translate
              .get(this.translationsReference + '.other_carriers')
              .subscribe((translation: string) => {
                this.carrierLabels.push(translation);
                this.totalSubmitted.push(row.total_submitted);
              });
          } else if (this.carrierLabels.length > 5) {
            this.totalSubmitted[5] = this.totalSubmitted[5] + row.total_submitted;
          } else {
            this.carrierLabels.push(row.carrier);
            this.totalSubmitted.push(row.total_submitted);
          }
        }
      });
    });
  }

  loadConfirmedGraph(wtoId: string): void {
    this.labelsInPeriod = [];
    this.confirmedCountInPeriod = [];
    this.techSupport.getWmConfirmedDeliveries(this.lastSevenDays, wtoId).subscribe((result) => {
      result.data.forEach((row) => {
        this.labelsInPeriod.push(row.label);
        this.confirmedCountInPeriod.push(row.count);
      });
    });
  }

  loadAcceptedSludgeGraph(wtoId: string): void {
    this.labelsForSludge = [];
    this.sludgeSum = [];
    this.techSupport.getWmAcceptedSludge(this.lastSevenDays, wtoId).subscribe((result) => {
      result.data.forEach((row) => {
        this.labelsForSludge.push(row.label);
        this.sludgeSum.push(row.count);
      });
    });
  }

  changeConfirmedPeriod(period: string): void {
    period == null ? this.lastSevenDays : period;
    this.confirmedPlaceHolder = this.translationsReference + '.' + period;
    this.confirmedCountInPeriod = [];
    this.labelsInPeriod = [];
    this.techSupport.getWmConfirmedDeliveries(period, this.wto_id).subscribe((result) => {
      if (period == this.thisYear) {
        this.commonFormService.translate
          .get('common.date.monthByNumber')
          .subscribe((translations: Record<string, string>) => {
            result.data.forEach((row) => {
              this.labelsInPeriod.push(translations[row.label]);
              this.confirmedCountInPeriod.push(row.count);
            });
          });
      } else {
        result.data.forEach((row) => {
          this.labelsInPeriod.push(row.label);
          this.confirmedCountInPeriod.push(row.count);
        });
      }
    });
  }

  changeSludgePeriod(period: string): void {
    period == null ? this.lastSevenDays : period;
    this.sludgePlaceHolder = this.translationsReference + '.' + period;
    this.sludgeSum = [];
    this.labelsForSludge = [];
    this.techSupport.getWmAcceptedSludge(period, this.wto_id).subscribe((result) => {
      if (period == this.thisYear) {
        this.commonFormService.translate
          .get('common.date.monthByNumber')
          .subscribe((translations: Record<string, string>) => {
            result.data.forEach((row) => {
              this.labelsForSludge.push(translations[row.label]);
              this.sludgeSum.push(row.count);
            });
          });
      } else {
        result.data.forEach((row) => {
          this.labelsForSludge.push(row.label);
          this.sludgeSum.push(row.count);
        });
      }
    });
  }

  navigateToSubmittedOrders(row: WmDashboardRow): void {
    void this.router.navigate([
      RoutingConst.INTERNAL,
      NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
      NtisRoutingConst.SLUDGE_TREATMENT,
      NtisRoutingConst.WATER_MANAGER,
      NtisRoutingConst.SLUDGE_TREATMENT_DELIVERIES_LIST,
      this.swg_sts_submitted,
      row.org_id,
    ]);
  }

  navigateToDeliveriesList(): void {
    void this.router.navigate([
      RoutingConst.INTERNAL,
      NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
      NtisRoutingConst.SLUDGE_TREATMENT,
      NtisRoutingConst.WATER_MANAGER,
      NtisRoutingConst.SLUDGE_TREATMENT_DELIVERIES_LIST,
    ]);
  }
}
