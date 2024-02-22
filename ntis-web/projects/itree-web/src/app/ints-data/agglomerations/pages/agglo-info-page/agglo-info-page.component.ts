import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { ActivatedRoute, NavigationExtras, Router, RouterModule } from '@angular/router';
import { AfterViewInit, ChangeDetectorRef, Component, OnInit, ViewChild, signal } from '@angular/core';
import { AgglomerationsService } from '../../services/agglomerations.service';
import { AGLO_STATUS_CHECKING, AGLO_STATUS_REJECTED } from '@itree-web/src/app/ntis-shared/constants/classifiers.const';
import { AppMessages } from '@itree/ngx-s2-commons';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { CommonModule } from '@angular/common';
import { DialogModule } from 'primeng/dialog';
import { FileUploadService } from '@itree-commons/src/lib/services/file-upload.service';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ItreeCommonsModule } from '@itree-commons/src/lib/itree-commons.module';
import { MapLayer } from '@itree-web/src/app/standalone/map/types/map-layer';
import { NTIS_AGGLO_INFO } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import {
  NtisAggloRejectRequest,
  NtisSubmittedAggloData,
  NtisSubmittedAggloDataGeom,
  SprFile,
} from '@itree-commons/src/lib/model/api/api';
import { NtisMapComponent } from '@itree-web/src/app/ntis-shared/components/map/map.component';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { Subject, takeUntil } from 'rxjs';
import { SubmitAggloDataComponent } from '../../components/submit-agglo-data/submit-agglo-data.component';
import { TranslateService } from '@ngx-translate/core';
import Fill from 'ol/style/Fill';
import GeoJSON from 'ol/format/GeoJSON';
import Stroke from 'ol/style/Stroke';
import Style from 'ol/style/Style';
import VectorLayer from 'ol/layer/Vector';
import VectorSource from 'ol/source/Vector';
import { Feature } from 'ol';
import Geometry from 'ol/geom/Geometry';

@Component({
  selector: 'app-agglo-info-page',
  standalone: true,
  imports: [
    CommonModule,
    ItreeCommonsModule,
    RouterModule,
    ReactiveFormsModule,
    DialogModule,
    SubmitAggloDataComponent,
    NtisMapComponent,
  ],
  templateUrl: './agglo-info-page.component.html',
  styleUrls: ['./agglo-info-page.component.scss'],
})
export class AggloInfoPageComponent implements OnInit, AfterViewInit {
  readonly translationsRef = 'intsData.agglomerations.pages.aggloInfo';
  readonly AGLO_STATUS_CHECKING = AGLO_STATUS_CHECKING;
  readonly AGLO_STATUS_REJECTED = AGLO_STATUS_REJECTED;
  readonly LAYER_KEY_THISAGGL = 'thisaggl';
  readonly RoutingConst = RoutingConst;
  readonly NtisRoutingConst = NtisRoutingConst;
  @ViewChild(NtisMapComponent) ntisMapComponent: NtisMapComponent;
  destroy$: Subject<boolean> = new Subject<boolean>();
  data: NtisSubmittedAggloData;
  canUpdate = false;
  canUpdateState = false;
  showSubmitNewDataDialog = false;
  showRejectDialog = false;
  rejectForm = new FormGroup({
    description: new FormControl<string>(undefined, Validators.required),
    file: new FormControl<SprFile[]>([]),
  });
  layers = signal<MapLayer[]>([]);
  geomsOlLayer = new VectorLayer({
    source: new VectorSource(),
    style: (feature, resolution): Style => {
      let fillAlpha = 0.7;
      if (resolution < 5) {
        fillAlpha = 0.1;
      } else if (resolution < 10) {
        fillAlpha = 0.2;
      } else if (resolution < 25) {
        fillAlpha = 0.3;
      } else if (resolution < 50) {
        fillAlpha = 0.4;
      } else if (resolution < 100) {
        fillAlpha = 0.5;
      } else if (resolution < 200) {
        fillAlpha = 0.65;
      }
      return new Style({
        stroke: new Stroke({
          color: 'hsl(310, 30%, 40%)',
          width: 1,
        }),
        fill: new Fill({
          color: `hsla(320, 40%, 55%, ${fillAlpha})`,
        }),
      });
    },
  });
  geomsMapLayer: MapLayer = {
    key: this.LAYER_KEY_THISAGGL,
    name: this.translationsRef + '.agglomeration',
    show: true,
    transparency: 0,
    removable: false,
    layers: [this.geomsOlLayer],
  };

  constructor(
    private activatedRoute: ActivatedRoute,
    private agglomerationsService: AgglomerationsService,
    private translateService: TranslateService,
    private appMessages: AppMessages,
    public fileUploadService: FileUploadService,
    authService: AuthService,
    private changeDetectorRef: ChangeDetectorRef,
    private router: Router
  ) {
    this.canUpdate = authService.isFormActionEnabled(NTIS_AGGLO_INFO, ActionsEnum.ACTIONS_UPDATE);
    this.canUpdateState = authService.isFormActionEnabled(NTIS_AGGLO_INFO, ActionsEnum.UPDATE_STATE);
  }

  ngOnInit(): void {
    this.activatedRoute.queryParams.pipe(takeUntil(this.destroy$)).subscribe((queryParams) => {
      this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((params) => {
        if (queryParams['token']) {
          this.agglomerationsService
            .loadUploadedAggloByTokenToken(queryParams['token'] as string)
            .subscribe((result) => {
              this.data = result;
            });
        } else if (params['id']) {
          this.loadData(params['id'] as string);
        }
      });
    });
  }

  ngAfterViewInit(): void {
    this.layers.set([this.geomsMapLayer, this.ntisMapComponent.aggloLayer]);
    this.changeDetectorRef.detectChanges();
  }

  loadData(aggId: string | number): void {
    this.agglomerationsService.getAggloData(`${aggId}`).subscribe((result) => {
      this.data = result;
      this.loadGeoms(this.data.geoms);
    });
  }

  loadGeoms(geoms: NtisSubmittedAggloDataGeom[]): void {
    this.geomsOlLayer.getSource().clear();
    const geoJSON = new GeoJSON();
    if (geoms) {
      geoms.forEach((geom) => {
        this.geomsOlLayer.getSource().addFeature(geoJSON.readFeature(geom.geom) as Feature<Geometry>);
      });
      this.travelToGeomsExtent(3);
    }
  }

  travelToGeomsExtent(retry = 0): void {
    if (this.ntisMapComponent?.mapComponent) {
      const extent = this.geomsOlLayer.getSource().getExtent();
      const isExtentFinite = isFinite(extent[0]);
      if (isExtentFinite) {
        this.ntisMapComponent.mapComponent.setBoundingExtent(extent);
      }
    } else if (retry > 0) {
      setTimeout(this.travelToGeomsExtent.bind(this, retry - 1), 1000);
    }
  }

  handleClickSubmitNewData(): void {
    this.showSubmitNewDataDialog = true;
  }

  handleClickReject(): void {
    this.showRejectDialog = true;
  }

  handleHideReject(): void {
    this.showRejectDialog = false;
    this.rejectForm.reset({
      description: undefined,
      file: [],
    });
  }

  handleClickApprove(): void {
    this.agglomerationsService.approveAgglo(this.data.id).subscribe((result) => {
      this.translateService.get('common.message.saveSuccess').subscribe((translation: string) => {
        this.appMessages.showSuccess('', translation);
      });
      this.data = result;
      this.agglomerationsService.sendAggloNotifications(result.id).subscribe();
    });
  }

  onRejectionFileUpload(files: SprFile[]): void {
    this.rejectForm.controls.file.setValue(files);
  }

  onRejectionFileDelete(file: SprFile): void {
    if (file.fil_key) {
      this.fileUploadService.deleteFile(file).subscribe();
    }
    this.rejectForm.controls.file.setValue([]);
  }

  handleSubmitRejection(): void {
    this.rejectForm.markAllAsTouched();
    if (this.rejectForm.valid) {
      const request: NtisAggloRejectRequest = {
        aggId: this.data.id,
        description: this.rejectForm.controls.description.value,
        file: this.rejectForm.controls.file.value?.[0] || null,
      };
      this.agglomerationsService.rejectAgglo(request).subscribe((result) => {
        this.handleHideReject();
        this.translateService.get('common.message.saveSuccess').subscribe((translation: string) => {
          this.appMessages.showSuccess('', translation);
        });
        this.data = result;
        this.agglomerationsService.sendAggloNotifications(result.id).subscribe();
      });
    } else {
      this.translateService.get('common.message.correctValidationErrors').subscribe((translation: string) => {
        this.appMessages.showError('', translation);
      });
    }
  }

  handleSubmittedNewData(): void {
    this.loadData(this.data.id);
    this.agglomerationsService.sendAggloNotifications(this.data.id).subscribe();
  }

  onReturn(): void {
    const navigationExtras: NavigationExtras = { state: { keepFilters: true } };
    void this.router.navigate(
      [
        RoutingConst.INTERNAL,
        NtisRoutingConst.DATA,
        NtisRoutingConst.AGGLOMERATIONS,
        NtisRoutingConst.SUBMITTED_AGGLO_DATA_LIST,
      ],
      navigationExtras
    );
  }
}
