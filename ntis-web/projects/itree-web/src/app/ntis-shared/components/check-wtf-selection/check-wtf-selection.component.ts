import { AfterViewInit, Component, OnDestroy, ViewChild } from '@angular/core';
import { CheckWtfSelectionService } from '../../services/check-wtf-selection.service';
import { CommonModule } from '@angular/common';
import { DialogModule } from 'primeng/dialog';
import { Feature, MapBrowserEvent } from 'ol';
import { getCenter } from 'ol/extent';
import { MapComponent } from '@itree-web/src/app/standalone/map/components/map/map.component';
import { MapLayer } from '@itree-web/src/app/standalone/map/types/map-layer';
import { NtisCheckWtfSelectionResponseWtf } from '@itree-commons/src/lib/model/api/api';
import { NtisCommonService } from '../../services/ntis-common.service';
import { NtisFacilityOwnerType } from '../../enums/classifiers.enums';
import { NtisRoutingConst } from '../../constants/ntis-routing.const';
import { Point } from 'ol/geom';
import { ProjectionLikeCode } from '@itree-web/src/app/standalone/map/enums/projection-like-code';
import { Router } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { Subscription } from 'rxjs';
import { TranslateModule } from '@ngx-translate/core';
import * as proj from 'ol/proj';
import Circle from 'ol/style/Circle';
import Fill from 'ol/style/Fill';
import Stroke from 'ol/style/Stroke';
import Style from 'ol/style/Style';
import VectorLayer from 'ol/layer/Vector';
import VectorSource from 'ol/source/Vector';

@Component({
  selector: 'ntis-check-wtf-selection',
  standalone: true,
  imports: [CommonModule, DialogModule, MapComponent, TranslateModule],
  templateUrl: './check-wtf-selection.component.html',
  styleUrls: ['./check-wtf-selection.component.scss'],
})
export class CheckWtfSelectionComponent implements OnDestroy, AfterViewInit {
  readonly translationsRef = 'ntisShared.components.checkWtfSelection';
  @ViewChild(MapComponent) private mapComponent: MapComponent;
  private subscription: Subscription;
  dialogMode: 'noResults' | 'exactWtf' | 'map';
  wtfLegendItems: string[] = [];
  selectedWtf: NtisCheckWtfSelectionResponseWtf;
  selectedWtfText: string;

  readonly wtfColorsHsl: Record<NtisFacilityOwnerType | string, number[]> = {
    [NtisFacilityOwnerType.OWNER]: [140, 90, 40],
    [NtisFacilityOwnerType.SELF_ASSIGNED]: [60, 95, 45],
    [NtisFacilityOwnerType.SERVICE_PROVIDER]: [195, 90, 40],
  };
  readonly wtfColors: Record<NtisFacilityOwnerType | string, string> = {
    [NtisFacilityOwnerType.OWNER]: `hsla(${this.wtfColorsHsl[NtisFacilityOwnerType.OWNER][0]}, ${
      this.wtfColorsHsl[NtisFacilityOwnerType.OWNER][1]
    }%, ${this.wtfColorsHsl[NtisFacilityOwnerType.OWNER][2]}%, 0.8)`,
    [NtisFacilityOwnerType.SELF_ASSIGNED]: `hsla(${this.wtfColorsHsl[NtisFacilityOwnerType.SELF_ASSIGNED][0]}, ${
      this.wtfColorsHsl[NtisFacilityOwnerType.SELF_ASSIGNED][1]
    }%, ${this.wtfColorsHsl[NtisFacilityOwnerType.SELF_ASSIGNED][2]}%, 0.8)`,
    [NtisFacilityOwnerType.SERVICE_PROVIDER]: `hsla(${this.wtfColorsHsl[NtisFacilityOwnerType.SERVICE_PROVIDER][0]}, ${
      this.wtfColorsHsl[NtisFacilityOwnerType.SERVICE_PROVIDER][1]
    }%, ${this.wtfColorsHsl[NtisFacilityOwnerType.SERVICE_PROVIDER][2]}%, 0.8)`,
  };
  readonly defaultWtfColorHsl = [200, 20, 35];
  readonly defaultWtfColor = `hsla(${this.defaultWtfColorHsl[0]}, ${this.defaultWtfColorHsl[1]}%, ${Math.round(
    this.defaultWtfColorHsl[2] / 2
  )}%, 0.8)`;

  olSource = new VectorSource();
  olLayer = new VectorLayer({
    source: this.olSource,
    style: (feature): Style[] => {
      const wtf = feature.getProperties()['wtf'] as NtisCheckWtfSelectionResponseWtf;
      if (!wtf) {
        return null;
      }
      const fillColorHsl = (wtf.ownerTypeCode && this.wtfColorsHsl[wtf.ownerTypeCode]) || this.defaultWtfColorHsl;
      const fill = new Fill({ color: `hsla(${fillColorHsl[0]}, ${fillColorHsl[1]}%, ${fillColorHsl[2]}%, 0.9)` });
      return [
        new Style({
          image: new Circle({
            radius: 10,
            fill,
            stroke:
              wtf.wtfId === this.selectedWtf?.wtfId
                ? new Stroke({
                    color: `hsla(${fillColorHsl[0]}, ${fillColorHsl[1]}%, ${Math.round(fillColorHsl[2] / 2)}%, 0.9)`,
                    width: 3,
                  })
                : undefined,
          }),
        }),
        new Style({
          image: new Circle({
            radius: 6,
            stroke: new Stroke({
              color: 'hsla(0, 0%, 100%, 0.9)',
              width: 2,
            }),
          }),
        }),
      ];
    },
  });

  mapLayers: MapLayer[] = [
    {
      key: 'wtfs',
      name: 'wtfs',
      translateName: false,
      show: true,
      transparency: 0,
      removable: false,
      layers: [this.olLayer],
    },
  ];

  constructor(
    private checkWtfSelectionService: CheckWtfSelectionService,
    private ntisCommonService: NtisCommonService,
    private router: Router
  ) {}

  ngAfterViewInit(): void {
    this.subscription = this.checkWtfSelectionService.check$.subscribe((params) => {
      this.reset();
      this.ntisCommonService.checkWtfSelection(params.request).subscribe((result) => {
        if (result.selected) {
          void this.router.navigate([params.navigateUrl]);
        } else if (result.wtfs?.length) {
          const exactWtf = result.wtfs.find(
            (wtf) =>
              ((params.request.adId && params.request.adId === wtf.adId) ||
                (wtf.lksX === params.request.lksX && wtf.lksY === params.request.lksY)) &&
              wtf.ownerTypeCode
          );
          if (exactWtf) {
            this.selectedWtf = exactWtf;
            this.dialogMode = 'exactWtf';
          } else {
            result.wtfs.forEach((wtf) => {
              const point = new Point(
                proj.transform([wtf.lksX, wtf.lksY], ProjectionLikeCode.LKS94, ProjectionLikeCode.WebMercator)
              );
              const feature = new Feature({ geometry: point, wtf });
              this.olSource.addFeature(feature);
              if (!this.wtfLegendItems.includes(wtf.ownerTypeCode)) {
                this.wtfLegendItems.push(wtf.ownerTypeCode);
              }
            });
            this.dialogMode = 'map';
            setTimeout(() => {
              this.mapComponent?.setBoundingExtent(this.olSource.getExtent());
            }, 1000);
          }
        } else {
          this.dialogMode = 'noResults';
        }
      });
    });
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
    this.olLayer.dispose();
    this.olSource.dispose();
  }

  reset(): void {
    this.wtfLegendItems = [];
    this.dialogMode = undefined;
    this.setSelectedWtf(undefined);
    this.olSource.clear();
  }

  setSelectedWtf(selectedWtf: typeof this.selectedWtf): void {
    this.selectedWtf = selectedWtf;
    this.selectedWtfText = selectedWtf
      ? `${selectedWtf.wtfType}: ${selectedWtf.address || `${selectedWtf.lksX}, ${selectedWtf.lksY}`}`
      : undefined;
    this.olLayer.changed();
  }

  handleMapClick(event: MapBrowserEvent<UIEvent>): void {
    this.setSelectedWtf(undefined);
    this.mapComponent.map.forEachFeatureAtPixel(event.pixel, (feature, layer) => {
      if (layer === this.olLayer) {
        const wtf = feature.getProperties()['wtf'] as NtisCheckWtfSelectionResponseWtf;
        if (wtf) {
          this.setSelectedWtf(wtf);
          if (this.mapComponent.mapView.getZoom() < 15) {
            this.mapComponent.setCenterCoordinates(getCenter(feature.getGeometry().getExtent()), 15);
          }
        }
      }
    });
  }

  handleClickCreateNew(): void {
    void this.router.navigate(
      [
        RoutingConst.INTERNAL,
        NtisRoutingConst.BUILDING_DATA,
        NtisRoutingConst.BUILDING_DATA_WASTEWATER_FACILITY,
        RoutingConst.EDIT,
        RoutingConst.NEW,
      ],
      {
        queryParams: this.checkWtfSelectionService.lastCheckParams
          ? {
              returnUrl: this.checkWtfSelectionService.lastCheckParams.navigateUrl,
              adId: this.checkWtfSelectionService.lastCheckParams.request.adId,
              x: !this.checkWtfSelectionService.lastCheckParams.request.adId
                ? this.checkWtfSelectionService.lastCheckParams.request.lksX
                : undefined,
              y: !this.checkWtfSelectionService.lastCheckParams.request.adId
                ? this.checkWtfSelectionService.lastCheckParams.request.lksY
                : undefined,
              type: this.checkWtfSelectionService.lastCheckParams.request.wtfType,
              address: this.checkWtfSelectionService.lastCheckParams.request.address,
              distance: this.checkWtfSelectionService.lastCheckParams.request.wtfDistance,
            }
          : undefined,
      }
    );
  }

  handleConfirmSelection(): void {
    if (this.selectedWtf) {
      this.ntisCommonService.selectWtf(this.selectedWtf.wtfId).subscribe(() => {
        this.reset();
        if (this.checkWtfSelectionService.lastCheckParams?.navigateUrl) {
          void this.router.navigate([this.checkWtfSelectionService.lastCheckParams?.navigateUrl]);
        }
      });
    }
  }
}
