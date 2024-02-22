import {
  AfterContentInit,
  AfterViewInit,
  Component,
  ContentChildren,
  ElementRef,
  EventEmitter,
  HostBinding,
  Input,
  OnChanges,
  OnDestroy,
  Output,
  QueryList,
  SimpleChanges,
  TemplateRef,
  ViewChild,
} from '@angular/core';
import { AppMessages } from '@itree/ngx-s2-commons';
import { Attribution, ScaleLine, defaults as defaultControls } from 'ol/control';
import { BehaviorSubject, Subject, auditTime, takeUntil } from 'rxjs';
import { CommonModule } from '@angular/common';
import { Coordinate } from 'ol/coordinate';
import { Extent } from 'ol/extent';
import { Feature, MapBrowserEvent } from 'ol';
import { getDefaultMapMenus } from '../../utils/getDefaultMapMenus';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { MAP_BG_KEY_BASE, MAP_BG_KEY_ORTO } from '../../constants/map-background-keys.const';
import { MAP_DEFAULT_CENTER_COORDINATES, MAP_DEFAULT_ZOOM } from '../../constants/map-defaults';
import { MapBackgroundLayer } from '../../types/map-background-layer';
import { MapLayer } from '../../types/map-layer';
import { MapLayerTemplateType } from '../../types/map-layer-template-type';
import { MapLocationSearchResult } from '../../types/map-location-search-result';
import { MapMenu } from '../../types/map-menu';
import { MapMenuLayersComponent } from '../map-menu-layers/map-menu-layers.component';
import { MapMenuMode } from '../../enums/map-menu-mode';
import { MapMenuSearchComponent } from '../map-menu-search/map-menu-search.component';
import { MapService } from '../../services/map.service';
import { MapSettings } from '../../types/map-settings';
import { MapTemplateDirective } from '../../directives/map-template.directive';
import { MouseWheelZoom, defaults as defaultInteractions } from 'ol/interaction';
import { Point } from 'ol/geom';
import { ProjectionLikeCode } from '../../enums/projection-like-code';
import { register } from 'ol/proj/proj4';
import { SearchByCoordinatesEvent } from '../../types/search-by-coordinates-event';
import { TooltipModule } from 'primeng/tooltip';
import { TranslateService } from '@ngx-translate/core';
import * as proj from 'ol/proj';
import * as WMTS from 'ol/source/WMTS';
import BaseLayer from 'ol/layer/Base';
import Icon from 'ol/style/Icon';
import OlMap from 'ol/Map';
import OlView, { AnimationOptions, FitOptions } from 'ol/View';
import proj4 from 'proj4';
import Style from 'ol/style/Style';
import TileLayer from 'ol/layer/Tile';
import VectorLayer from 'ol/layer/Vector';
import VectorSource from 'ol/source/Vector';
import WMTSCapabilities from 'ol/format/WMTSCapabilities';

@Component({
  selector: 'map-component',
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, TooltipModule, MapMenuLayersComponent, MapMenuSearchComponent],
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss'],
})
export class MapComponent implements OnChanges, AfterViewInit, AfterContentInit, OnDestroy {
  readonly translationsRef = 'map';
  readonly destroy$ = new Subject<void>();
  readonly MapMenuMode = MapMenuMode;
  @ViewChild('mapElement') mapElementRef: ElementRef<HTMLDivElement>;
  @ViewChild('mapMenu') mapMenuElementRef: ElementRef<HTMLDivElement>;
  @ViewChild('overlayBottom') mapOverlayBottomElementRef: ElementRef<HTMLDivElement>;
  @ContentChildren(MapTemplateDirective, { descendants: true })
  templatesQueryList: QueryList<MapTemplateDirective>;

  templates: {
    'custom-menu'?: TemplateRef<unknown>;
    'overlay-top'?: TemplateRef<unknown>;
    'overlay-bottom'?: TemplateRef<unknown>;
    'layers-menu-header'?: TemplateRef<unknown>;
    layer?: Record<string, Record<MapLayerTemplateType, TemplateRef<unknown>>>;
  } = {};

  mapView = new OlView({
    maxZoom: 18,
    minZoom: 6,
    enableRotation: false,
    zoom: MAP_DEFAULT_ZOOM,
    extent: [2000000, 7000000, 3150000, 7800000],
  });

  map: OlMap;

  pinLayer = new VectorLayer({
    source: new VectorSource(),
    style: (_feature, resolution): Style => {
      return new Style({
        image: new Icon({
          scale: Math.round((0.4 + 0.25 / resolution) * 10) / 10,
          anchor: [0.5, 1],
          src: './assets/map/location-pin.svg',
        }),
      });
    },
    zIndex: 1000,
  });

  @HostBinding('class.full-screen') fullScreen = false;
  @Input() backgroundLayers: MapBackgroundLayer<BaseLayer>[] = [
    { key: MAP_BG_KEY_BASE, layer: new TileLayer<WMTS.default>(), imagePath: 'assets/map/bg_base.png' },
    { key: MAP_BG_KEY_ORTO, layer: new TileLayer<WMTS.default>(), imagePath: 'assets/map/bg_orto.png' },
  ];
  @Input() showLocationButton = true;
  @Input() showFullScreenButton = true;
  @Input() menuItems: MapMenu[] = getDefaultMapMenus();
  @Input() layers: MapLayer[] = [];
  @Input() infoMessage: string;
  @Input() translateInfoMessage = true;
  @Input() settings: MapSettings;
  @Input() overlayBottomExpanded = false;

  @Output() mapSingleClick = new EventEmitter<MapBrowserEvent<UIEvent>>();
  @Output() mapDoubleClick = new EventEmitter<MapBrowserEvent<UIEvent>>();
  @Output() searchByCoordinates = new EventEmitter<SearchByCoordinatesEvent>();
  @Output() clickSearchResult = new EventEmitter<MapLocationSearchResult>();
  @Output() toggleShowLayer = new EventEmitter<MapLayer>();

  isLocationButtonDisabled = false;
  selectedMenuIndex$ = new BehaviorSubject<number | null>(null);
  selectedBackgroundLayerIndex = 0;
  mobileMenuExpanded = false;

  resize$ = new Subject<void>();

  private mapResizeObserver: ResizeObserver;
  private mapMenuResizeObserver: ResizeObserver;

  constructor(
    private mapService: MapService,
    private translateService: TranslateService,
    private appMessages: AppMessages
  ) {
    this.map = new OlMap({
      controls: defaultControls({
        rotate: false,
        attribution: false,
        zoom: false,
      }).extend([
        new ScaleLine({
          units: 'metric',
        }),
        new Attribution({
          tipLabel: this.translateService.instant('components.map.view.attributions') as string,
        }),
      ]),
      interactions: defaultInteractions({ mouseWheelZoom: false }).extend([
        new MouseWheelZoom({ constrainResolution: true, duration: 10, timeout: 0 }),
      ]),
    });
    this.loadLks();
    this.loadBackgroundLayersSources();
    this.map.setView(this.mapView);
    this.map.setLayers(
      this.backgroundLayers.map((backgroundLayer, index) => {
        backgroundLayer.layer.setVisible(index === 0);
        return backgroundLayer.layer;
      })
    );
    this.mapView.setCenter(
      proj.transform(MAP_DEFAULT_CENTER_COORDINATES, ProjectionLikeCode.WGS84, ProjectionLikeCode.WebMercator)
    );
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.layers) {
      this.updateTemplates();

      const currentLayers = changes.layers.currentValue as typeof this.layers;
      currentLayers.forEach((mapLayer) => {
        const allLayers = this.map.getAllLayers();
        // add new layers:
        mapLayer.layers.forEach((olLayer) => {
          if (!allLayers.some((addedLayer) => olLayer === addedLayer)) {
            olLayer.setVisible(mapLayer.show);
            olLayer.setProperties({ mapLayer: true });
            this.map.addLayer(olLayer);
          }
        });
      });

      // remove old layers:
      this.map
        .getAllLayers()
        .filter(
          (layer) =>
            layer.get('mapLayer') === true && !currentLayers.some((mapLayer) => mapLayer.layers.indexOf(layer) !== -1)
        )
        .forEach((layer) => {
          this.map.removeLayer(layer);
        });

      this.resetLayersZIndexes();
    }
  }

  ngAfterViewInit(): void {
    this.map.setTarget(this.mapElementRef.nativeElement);
    this.map.addLayer(this.pinLayer);
    this.mapResizeObserver = new ResizeObserver(() => {
      this.resize$.next();
    });
    this.mapResizeObserver.observe(this.mapElementRef.nativeElement);
    this.resize$.pipe(takeUntil(this.destroy$), auditTime(250)).subscribe(() => {
      this.map?.updateSize();
    });

    this.map.on('singleclick', (event: MapBrowserEvent<UIEvent>) => {
      this.mapSingleClick.emit(event);
    });

    this.map.on('dblclick', (event: MapBrowserEvent<UIEvent>) => {
      this.mapDoubleClick.emit(event);
    });

    this.mapMenuResizeObserver = new ResizeObserver((entries) => {
      for (const entry of entries) {
        const target = entry.target as HTMLElement;
        if (target === this.mapMenuElementRef.nativeElement) {
          this.mapElementRef.nativeElement.style.setProperty(
            '--map-menu-width',
            `${target.offsetWidth || target.clientWidth}px`
          );
        } else if (target === this.mapOverlayBottomElementRef.nativeElement) {
          this.mapElementRef.nativeElement.style.setProperty(
            '--map-overlay-bottom-height',
            `${target.offsetHeight || target.clientHeight}px`
          );
        }
      }
    });
    this.mapMenuResizeObserver.observe(this.mapMenuElementRef.nativeElement);
    this.mapMenuResizeObserver.observe(this.mapOverlayBottomElementRef.nativeElement);
  }

  ngAfterContentInit(): void {
    this.updateTemplates();
    this.templatesQueryList.changes.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.updateTemplates();
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.unsubscribe();
    this.mapResizeObserver.disconnect();
    this.mapMenuResizeObserver.disconnect();
  }

  private loadLks(): void {
    proj4.defs(
      ProjectionLikeCode.LKS94,
      '+proj=tmerc +lat_0=0 +lon_0=24 +k=0.9998+x_0=500000 +y_0=0 +ellps=GRS80 +towgs84=0,0,0,0,0,0,0 +units=m+no_defs'
    );
    register(proj4);
    const lksExtent = [307558.9491752772, 5970000.0, 685218.1236202281, 6264605.2051798785];
    proj.get(ProjectionLikeCode.LKS94).setExtent(lksExtent);
  }

  private loadBackgroundLayersSources(): void {
    const loadBackgroundsInfo = [
      {
        key: MAP_BG_KEY_BASE,
        url: '/assets/map/WMTSCapabilities.xml',
        layer: 'geoportal_public_background_Lietuva_102100',
      },
      {
        key: MAP_BG_KEY_ORTO,
        url: 'https://www.geoportal.lt/arcgis/rest/services/NZT/ORT10LT_Web_Mercator_102100/MapServer/WMTS/1.0.0/WMTSCapabilities.xml',
        layer: 'NZT_ORT10LT_Web_Mercator_102100',
      },
    ];

    loadBackgroundsInfo.forEach((item) => {
      this.mapService.getCapabilitiesResponse(item.url).subscribe({
        next: (result) => {
          const parser = new WMTSCapabilities();
          const parserResult: unknown = parser.read(result);
          const options = WMTS.optionsFromCapabilities(parserResult, {
            layer: item.layer,
            matrix: 'default028mm',
          });
          if (options) {
            options.crossOrigin = '';
            this.getBackgroundByKey<TileLayer<WMTS.default>>(item.key).layer.setSource(new WMTS.default(options));
          } else {
            this.showGeoportalWarning();
          }
        },
        error: () => {
          this.showGeoportalWarning();
        },
      });
    });
  }

  changeBackgroundLayer(): void {
    this.selectedBackgroundLayerIndex =
      this.backgroundLayers.length - 1 > this.selectedBackgroundLayerIndex ? this.selectedBackgroundLayerIndex + 1 : 0;
    this.backgroundLayers.forEach((bgLayer, index) => {
      bgLayer.layer.setVisible(index === this.selectedBackgroundLayerIndex);
    });
  }

  closeMenu(): void {
    this.setSelectedMenuIndex(null);
  }

  toggleMobileMenuExpand(): void {
    this.mobileMenuExpanded = !this.mobileMenuExpanded;
  }

  resetLayersZIndexes(): void {
    this.layers.forEach((mapLayer, index) => {
      mapLayer.layers.forEach((layer) => {
        layer.setZIndex(this.layers.length - index);
      });
    });
  }

  toggleFullScreen(): void {
    this.fullScreen = !this.fullScreen;
  }

  showClientLocation(): void {
    if (!this.isLocationButtonDisabled) {
      this.isLocationButtonDisabled = true;
      navigator.geolocation.getCurrentPosition(
        (position) => {
          this.mapView.animate({
            center: proj.fromLonLat([position.coords.longitude, position.coords.latitude]),
            zoom: window.innerWidth <= 480 ? 18 : 16.5,
          });
          this.isLocationButtonDisabled = false;
        },
        (error) => {
          this.isLocationButtonDisabled = true;
          console.error(error);
        }
      );
    }
  }

  updateTemplates(): void {
    this.templatesQueryList?.forEach((template) => {
      if (!this.templates[template.mapTemplate]) {
        if (template.mapTemplate === 'layer') {
          this.templates[template.mapTemplate] = {};
        } else {
          this.templates[template.mapTemplate] = template.template;
        }
      }
      if (template.mapTemplate === 'layer') {
        if (!this.templates[template.mapTemplate][template.mapLayerTemplateKey]) {
          this.templates[template.mapTemplate][template.mapLayerTemplateKey] = {} as Record<
            MapLayerTemplateType,
            TemplateRef<unknown>
          >;
        }
        if (
          this.templates[template.mapTemplate][template.mapLayerTemplateKey][template.mapLayerTemplateType] !==
          template.template
        ) {
          this.templates[template.mapTemplate][template.mapLayerTemplateKey][template.mapLayerTemplateType] =
            template.template;
        }
      }
    });
  }

  getSelectedMenuIndex(): number | null {
    return this.selectedMenuIndex$.value;
  }

  /**
   *
   * @param value Index of menuItems item to be selected, `-1` to show custom menu, `null` to hide menu
   */
  setSelectedMenuIndex(value: number | null): void {
    this.selectedMenuIndex$.next(value);
  }

  setBoundingExtent(
    extent: Extent,
    projectionLikeCode: ProjectionLikeCode = ProjectionLikeCode.WebMercator,
    fitOptions?: FitOptions
  ): void {
    fitOptions = fitOptions || { padding: [32, 32, 32, 32], duration: 1000 };
    if (this.mapView) {
      if (projectionLikeCode != ProjectionLikeCode.WebMercator) {
        extent = proj.transformExtent(extent, projectionLikeCode, ProjectionLikeCode.WebMercator);
      }
      this.mapView.fit(extent, fitOptions);
    }
  }

  /**
   *
   * @param {Coordinate} coordinates An array of numbers representing an xy coordinate.
   * @param zoom Optional: Zoom level (provide `undefined` to leave current zoom level) (default: undefined)
   * @param projectionLikeCode Optional: ProjectionLikeCode of provided coordinates (default: ProjectionLikeCode.WebMercator)
   * @param animate Optional: Should movement be animated (default: true)
   */
  setCenterCoordinates(
    coordinates: Coordinate,
    zoom: number = undefined,
    projectionLikeCode: ProjectionLikeCode = ProjectionLikeCode.WebMercator,
    animate: boolean = true
  ): void {
    if (projectionLikeCode != ProjectionLikeCode.WebMercator) {
      coordinates = proj.transform(coordinates, projectionLikeCode, ProjectionLikeCode.WebMercator);
    }
    if (animate) {
      const animationOptions: AnimationOptions = {
        center: coordinates,
      };
      if (zoom !== undefined) {
        animationOptions.zoom = zoom;
      }
      this.mapView.animate(animationOptions);
    } else {
      this.mapView.setCenter(coordinates);
      if (zoom !== undefined) {
        this.mapView.setZoom(zoom);
      }
    }
  }

  showPinInCoordinates(
    coordinates: Coordinate,
    projection: ProjectionLikeCode = ProjectionLikeCode.WebMercator,
    properties?: Record<string, unknown>,
    clearSource = false
  ): void {
    if (projection !== ProjectionLikeCode.WebMercator) {
      coordinates = proj.transform(coordinates, projection, ProjectionLikeCode.WebMercator);
    }
    if (clearSource) {
      this.pinLayer.getSource().clear();
    }
    const feature = new Feature(new Point(coordinates));
    if (properties) {
      feature.setProperties(properties);
    }
    this.pinLayer.getSource().addFeature(feature);
  }

  clearPins(): void {
    this.pinLayer.getSource().clear();
  }

  showGeoportalWarning(): void {
    this.translateService.get(this.translationsRef + '.common.error.geoportal').subscribe((translation: string) => {
      this.appMessages.showWarning('', translation);
    });
  }

  zoom(zoomIn: boolean): void {
    const currentZoom = this.mapView.getZoom();
    if (zoomIn ? this.mapView.getMaxZoom() > currentZoom : this.mapView.getMinZoom() < currentZoom) {
      this.mapView.animate({ zoom: this.mapView.getZoom() + (zoomIn ? 1 : -1), duration: 200 });
    }
  }

  getSetting(property: keyof MapSettings): MapSettings[keyof MapSettings] {
    const settings = this.settings || {};
    const value = settings[property];
    switch (property) {
      case 'showPinOnSearch': {
        return value === undefined ? true : value;
      }
    }
    return value;
  }

  // helper functions:

  getBackgroundByKey<T extends BaseLayer>(key: string): MapBackgroundLayer<T> {
    return (this.backgroundLayers as MapBackgroundLayer<T>[]).find((backgroundLayer) => backgroundLayer.key === key);
  }

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  public static getLayerByKey<T = any>(layers: MapLayer[], key: string): MapLayer<T> {
    return layers.find((layer) => layer.key === key) as MapLayer<T>;
  }

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  getLayerByKey<T = any>(key: string): MapLayer<T> {
    return MapComponent.getLayerByKey<T>(this.layers, key);
  }
}
