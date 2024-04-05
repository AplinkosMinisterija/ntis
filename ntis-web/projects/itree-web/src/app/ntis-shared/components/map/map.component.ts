import { AfterViewInit, ChangeDetectorRef, Component, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import {
  AdvancedSearchParameterStatement,
  NtisAggloMapTableItem,
  NtisBuildingsMapTableItem,
  NtisMapCentDetails,
  NtisMapClickedPoint,
  NtisMapDisposalDetails,
  NtisMapFacilityDetails,
  NtisMapResearchDetails,
  NtisMapTableResult,
} from '@itree-commons/src/lib/model/api/api.d';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { AsArrayPipe } from '../../pipes/as-array.pipe';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { CheckboxTreeComponent } from '@itree-web/src/app/standalone/checkbox-tree/checkbox-tree.component';
import { CheckboxTreeItem } from 'projects/itree-web/src/app/standalone/checkbox-tree/checkbox-tree.types';
import { CommonModule } from '@angular/common';
import { CONFIRMED, REGISTERED } from '../../constants/classifiers.const';
import { Coordinate } from 'ol/coordinate';
import { DB_BOOLEAN_FALSE, DB_BOOLEAN_TRUE } from '@itree-commons/src/constants/db.const';
import { ExtendedSearchCondition, LoaderService } from '@itree/ngx-s2-commons';
import { Extent } from 'ol/extent';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FormsModule } from '@angular/forms';
import { GeoJSON } from '../../models/geojson';
import { IsArrayPipe } from '../../pipes/is-array.pipe';
import { isValidJSON } from '@itree-commons/src/lib/utils/is-valid-json';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { LineString, Point } from 'ol/geom';
import { MapBrowserEvent } from 'ol';
import { MapComponent } from 'projects/itree-web/src/app/standalone/map/components/map/map.component';
import { MapExportFormat, MapTableComponent } from '../map-table/map-table.component';
import { MapFilterField } from '../map-filter/map-filter.types';
import { MapFiltersFormComponent } from '../map-filters-form/map-filters-form.component';
import { MapLayer } from 'projects/itree-web/src/app/standalone/map/types/map-layer';
import { MapMenuMode } from 'projects/itree-web/src/app/standalone/map/enums/map-menu-mode';
import { MapTemplateDirective } from '@itree-web/src/app/standalone/map/directives/map-template.directive';
import { NTIS_MAP } from '../../constants/forms.const';
import { NtisActionsConst } from '../../constants/ntis-actions.const';
import { NtisIntsStatus, NtisTypeWastewaterTreatment, NtisWtfType, OrderStatus } from '../../enums/classifiers.enums';
import { NtisMapService } from '../../services/ntis-map.service';
import { NtisSharedModule } from '../../ntis-shared.module';
import { ProjectionLikeCode } from '../../../standalone/map/enums/projection-like-code';
import { REST_API_BASE_URL } from '@itree-commons/src/constants/rest.constants';
import { SelectTypesEnum } from '@itree-commons/src/lib/components/select/select.component';
import { Subject, takeUntil } from 'rxjs';
import { TableExportService } from '@itree-commons/src/lib/services/table-export.service';
import { TooltipModule } from 'primeng/tooltip';
import { transform } from 'ol/proj';
import { TranslateService } from '@ngx-translate/core';
import Circle from 'ol/style/Circle';
import Feature, { FeatureLike } from 'ol/Feature';
import FileSaver from 'file-saver';
import Fill from 'ol/style/Fill';
import MVT from 'ol/format/MVT';
import RegularShape from 'ol/style/RegularShape';
import Stroke from 'ol/style/Stroke';
import Style from 'ol/style/Style';
import VectorLayer from 'ol/layer/Vector';
import VectorSource from 'ol/source/Vector';
import VectorTileLayer from 'ol/layer/VectorTile';
import VectorTileSource from 'ol/source/VectorTile';

const LAYER_KEY_AGGLO = 'agglo';
const LAYER_KEY_PREL_AGGLO = 'prelAgglo';
const LAYER_KEY_BUILDINGS = 'build';
const LAYER_KEY_CENT = 'cent';
const LAYER_KEY_DISCHARGE = 'disc';
const LAYER_KEY_FACILITIES = 'faci';
const LAYER_KEY_WELLS = 'wells';
const LAYER_KEY_RESEARCH = 'research';
const LAYER_KEY_DISPOSAL = 'disposal';

/** Used for point geometry radius calculation */
const POINT_MAX_CNT = 30000;

type MapLayerKey =
  | typeof LAYER_KEY_AGGLO
  | typeof LAYER_KEY_PREL_AGGLO
  | typeof LAYER_KEY_BUILDINGS
  | typeof LAYER_KEY_DISCHARGE
  | typeof LAYER_KEY_FACILITIES
  | typeof LAYER_KEY_WELLS
  | typeof LAYER_KEY_CENT
  | typeof LAYER_KEY_RESEARCH
  | typeof LAYER_KEY_DISPOSAL;

interface NtisAggloMapTableItemView extends NtisAggloMapTableItem, Record<string, unknown> {}

interface MapTableDataItem extends Record<string, unknown> {
  maxX?: number;
  maxY?: number;
  minX?: number;
  minY?: number;
  x?: number;
  y?: number;
}

interface NtisMapLayerExtras {
  filterFields?: MapFilterField[];
  skipCheckForFilterFields?: string[];
  appliedFilters?: AdvancedSearchParameterStatement[];
  maxFilters?: number;
  optionsTree?: CheckboxTreeItem[];
  selectedOptions?: string[];
  tableData?: NtisMapTableResult<MapTableDataItem>;
  tableColumns?: string[];
  infoColumns?: string[];
  infoData?: Record<string, unknown>;
}

interface DetailsPointProperties extends Record<string, unknown> {
  layer: typeof LAYER_KEY_BUILDINGS | typeof LAYER_KEY_DISCHARGE | typeof LAYER_KEY_FACILITIES;
  wtf_id?: number;
  wtf_type?: string;
  wtf_state?: string;
  bn_id?: number;
  mainPoint?: boolean;
  cnt?: number;
}

@Component({
  selector: 'ntis-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ItreeCommonsModule,
    NtisSharedModule,
    FontAwesomeModule,
    FormsModule,
    AsArrayPipe,
    CheckboxTreeComponent,
    IsArrayPipe,
    MapComponent,
    MapFiltersFormComponent,
    MapTableComponent,
    MapTemplateDirective,
    TooltipModule,
    RouterModule,
  ],
})
export class NtisMapComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild(MapComponent) mapComponent: MapComponent;
  readonly destroy$ = new Subject<void>();
  readonly translationsRef = 'ntisShared.components.map';
  readonly layerFaciTranslationsRef = 'ntisShared.components.map.layerCols.faci';
  readonly layerAggloTranslationsRef = 'ntisShared.components.map.layerCols.agglo';
  readonly layerBuildTranslationsRef = 'ntisShared.components.map.layerCols.build';
  readonly layerCentTranslationsRef = 'ntisShared.components.map.layerCols.cent';

  readonly ProjectionLikeCode = ProjectionLikeCode;

  readonly LAYER_KEY_AGGLO = LAYER_KEY_AGGLO;
  readonly LAYER_KEY_PREL_AGGLO = LAYER_KEY_PREL_AGGLO;
  readonly LAYER_KEY_BUILDINGS = LAYER_KEY_BUILDINGS;
  readonly LAYER_KEY_CENT = LAYER_KEY_CENT;
  readonly LAYER_KEY_FACILITIES = LAYER_KEY_FACILITIES;
  readonly LAYER_KEY_DISCHARGE = LAYER_KEY_DISCHARGE;
  readonly LAYER_KEY_WELLS = LAYER_KEY_WELLS;
  readonly LAYER_KEY_RESEARCH = LAYER_KEY_RESEARCH;
  readonly LAYER_KEY_DISPOSAL = LAYER_KEY_DISPOSAL;

  showInfoMessage = false;

  canReadFaciTable: boolean;
  canReadBuildTable: boolean;
  canReadCentTable: boolean;
  canReadDiscTable: boolean;
  canReadDisposalLayer: boolean;
  canReadResearchLayer: boolean;
  canReadAggloTable: boolean;

  detailsOlLayer = new VectorLayer({
    source: new VectorSource(),
    style: (feature, resolution): Style[] | Style => this.getDetailsLayerStyle(feature, resolution),
    zIndex: 100,
  });

  aggloOlSource = new VectorTileSource({
    format: new MVT(),
    url: '/maps/ntis/aglo/{z}/{x}/{y}.pbf',
  });

  aggloOlLayer = new VectorTileLayer({
    visible: true,
    source: this.aggloOlSource,
    style: (feature, resolution): Style => {
      return this.getAggloLayerStyle(feature, resolution);
    },
  });

  prelAggloOlLayer = new VectorTileLayer({
    visible: true,
    source: this.aggloOlSource,
    style: (feature, resolution): Style => {
      return this.getPrelAggloLayerStyle(feature, resolution);
    },
  });

  aggloLayer: MapLayer<NtisMapLayerExtras> = {
    key: LAYER_KEY_AGGLO,
    name: this.translationsRef + '.agglomerations',
    show: true,
    transparency: 0,
    removable: false,
    layers: [this.aggloOlLayer],
    extras: {
      filterFields: [
        {
          name: this.layerAggloTranslationsRef + '.objectId',
          field: 'a_ter_id',
        },
        {
          name: this.layerAggloTranslationsRef + '.name',
          field: 'pav',
        },
        {
          name: this.layerAggloTranslationsRef + '.municipalityCode',
          field: 'sav_kodas',
        },
        {
          name: this.layerAggloTranslationsRef + '.populationEquivalent',
          field: 'ge',
        },
      ],
      tableColumns: [
        'id',
        'name',
        'municipalityCode',
        'populationEquivalent',
        'populationDensity',
        'area',
        'docName',
        'docNo',
        'docDate',
      ],
    },
  };

  prelAggloLayer: MapLayer<NtisMapLayerExtras> = {
    key: LAYER_KEY_PREL_AGGLO,
    name: this.translationsRef + '.preliminaryAgglomerations',
    show: false,
    transparency: 0,
    removable: true,
    layers: [this.prelAggloOlLayer],
    extras: {
      filterFields: [
        {
          name: this.layerAggloTranslationsRef + '.objectId',
          field: 'a_ter_id',
        },
        {
          name: this.layerAggloTranslationsRef + '.name',
          field: 'pav',
        },
        {
          name: this.layerAggloTranslationsRef + '.municipalityCode',
          field: 'sav_kodas',
        },
        {
          name: this.layerAggloTranslationsRef + '.populationEquivalent',
          field: 'ge',
        },
      ],
    },
  };

  buildingsOlLayer = new VectorTileLayer({
    visible: true,
    declutter: true,
    source: new VectorTileSource({
      format: new MVT(),
      url: '/maps/ntis/build/{z}/{x}/{y}.pbf',
    }),
    style: (feature, resolution): Style[] | Style => {
      return this.getBuildingsLayerStyle(feature, resolution);
    },
    minZoom: 9,
    renderBuffer: 1000,
  });

  buildingsLayer: MapLayer<NtisMapLayerExtras> = {
    key: LAYER_KEY_BUILDINGS,
    name: this.translationsRef + '.buildings',
    show: false,
    transparency: 0,
    removable: false,
    layers: [this.buildingsOlLayer],
    extras: {
      filterFields: [
        {
          name: this.layerBuildTranslationsRef + '.address',
          field: 'ad.ad_address',
          formActionName: NtisActionsConst.READ_BUILD_TABLE,
        },
        {
          name: this.layerBuildTranslationsRef + '.ntrNumber',
          field: 'bn.bn_obj_inv_code',
          formActionName: NtisActionsConst.READ_BUILD_TABLE,
        },
        {
          name: this.layerBuildTranslationsRef + '.isCentralized',
          field: 'treatment',
          classifierCode: 'NTIS_TYPE_WASTEWATER_TREATMENT',
        },
      ],
      tableColumns: [
        'ntrNumber',
        'status',
        'address',
        'belongsToNtrNumber',
        'purpose',
        'info',
      ] as (keyof NtisBuildingsMapTableItem)[],
      infoColumns: ['facilitiesCount', 'addresses'],
    },
  };

  facilitiesOlLayer = new VectorTileLayer({
    visible: true,
    declutter: true,
    source: new VectorTileSource({
      format: new MVT(),
      url: '/maps/ntis/faci/{z}/{x}/{y}.pbf',
    }),
    style: (feature, resolution): Style[] | Style => {
      return this.getFacilitiesLayerStyle(feature, resolution);
    },
    minZoom: 9,
  });

  facilitiesLayer: MapLayer<NtisMapLayerExtras> = {
    key: LAYER_KEY_FACILITIES,
    name: this.translationsRef + '.facilities',
    show: true,
    transparency: 0,
    removable: false,
    layers: [this.facilitiesOlLayer],
    extras: {
      filterFields: [
        {
          name: this.layerFaciTranslationsRef + '.address',
          field: 'ad.ad_address',
          formActionName: NtisActionsConst.READ_FACI_TABLE,
        },
        {
          name: this.layerFaciTranslationsRef + '.type',
          field: 'wtf_type',
          classifierCode: 'NTIS_WTF_TYPE',
          optionType: SelectTypesEnum.multiple,
        },
        {
          name: this.layerFaciTranslationsRef + '.state',
          field: 'wtf_state',
          classifierCode: 'NTIS_INTS_STATUS',
          optionType: SelectTypesEnum.multiple,
        },
      ],
      appliedFilters: [
        {
          paramName: 'wtf_state',
          paramValue: {
            condition: '=',
            upperLower: 'caseInsensitiveLatin',
            value: undefined,
            values: [REGISTERED, CONFIRMED],
          },
        },
      ],
      tableColumns: [
        'state',
        'address',
        'type',
        'distance',
        'installationDate',
        'checkoutDate',
        'capacity',
        'technicalPassport',
        'manufacturer',
        'model',
        'manufacturerDescription',
        'dischargeCoordinatesText',
        'dischargeType',
        'servedObjectAddresses',
      ] as (keyof NtisMapFacilityDetails)[],
    },
  };

  dischargesOlLayer = new VectorTileLayer({
    visible: true,
    declutter: true,
    source: new VectorTileSource({
      format: new MVT(),
      url: '/maps/ntis/disc/{z}/{x}/{y}.pbf',
    }),
    style: (feature, resolution): Style[] | Style => {
      return this.getDischargesLayerStyle(feature, resolution);
    },
    minZoom: 9,
  });

  dischargesLayer: MapLayer<NtisMapLayerExtras> = {
    key: LAYER_KEY_DISCHARGE,
    name: this.translationsRef + '.discharges',
    show: false,
    transparency: 0,
    removable: false,
    layers: [this.dischargesOlLayer],
    extras: {
      filterFields: [
        {
          name: 'ntisShared.components.map.layerCols.disc.dischargeType',
          field: 'wtf_discharge_type',
          classifierCode: 'DISCHARGE_WASTEWATER_TYPE',
        },
      ],
      tableColumns: ['address', 'dischargeType'] as (keyof NtisMapFacilityDetails)[],
      infoColumns: ['address', 'dischargeType', 'coordinates'],
    },
  };

  wellsOlLayer = new VectorTileLayer({
    visible: true,
    declutter: true,
    source: new VectorTileSource({
      format: new MVT(),
      url: '/maps/ntis/wells/{z}/{x}/{y}.pbf',
    }),
    style: (feature, resolution): Style[] | Style => {
      return this.getWellsLayerStyle(feature, resolution);
    },
    minZoom: 9,
  });

  wellsLayer: MapLayer<NtisMapLayerExtras> = {
    key: LAYER_KEY_WELLS,
    name: this.translationsRef + '.wells',
    show: false,
    transparency: 0,
    removable: false,
    layers: [this.wellsOlLayer],
    extras: {},
  };

  centOlLayer = new VectorTileLayer({
    visible: true,
    source: new VectorTileSource({
      format: new MVT(),
      url: '/maps/ntis/cent/{z}/{x}/{y}.pbf',
    }),
    minZoom: 15,
    style: (feature): Style | Style[] => {
      return this.getCentLayerStyle(feature);
    },
  });

  centLayer: MapLayer<NtisMapLayerExtras> = {
    key: LAYER_KEY_CENT,
    name: this.translationsRef + '.cent',
    show: false,
    transparency: 0,
    removable: false,
    layers: [this.centOlLayer],
    extras: {
      filterFields: [
        {
          name: this.layerCentTranslationsRef + '.ntrNumber',
          field: 'bn_obj_inv_code',
        },
        {
          name: this.layerCentTranslationsRef + '.municipality',
          field: 'bn_municipality',
        },
        {
          name: this.layerCentTranslationsRef + '.placeName',
          field: 'bn_place_name',
        },
        {
          name: this.layerCentTranslationsRef + '.street',
          field: 'bn_street',
        },
        {
          name: this.layerCentTranslationsRef + '.address',
          field: 'adresas',
        },
      ],
      tableColumns: ['ntrNumber', 'municipality', 'placeName', 'street', 'address'] as (keyof NtisMapCentDetails)[],
      infoColumns: ['ntrNumber', 'municipality', 'placeName', 'street', 'address'],
    },
  };

  researchOlLayer = new VectorTileLayer({
    visible: true,
    source: new VectorTileSource({
      format: new MVT(),
      url: REST_API_BASE_URL + '/ntis-map/get-tiles/research/{z}/{x}/{y}',
    }),
    minZoom: 15,
    style: (feature): Style => {
      return this.getResearchLayerStyle(feature);
    },
  });

  researchLayer: MapLayer<NtisMapLayerExtras> = {
    key: LAYER_KEY_RESEARCH,
    name: this.translationsRef + '.research',
    show: false,
    transparency: 0,
    removable: false,
    layers: [this.researchOlLayer],
    extras: {
      filterFields: [
        {
          name: 'ntisShared.components.map.layerCols.research.date',
          field: 'research_date',
          filterInputType: 'date',
        },
      ],
      maxFilters: 1,
      tableColumns: [
        'link',
        'address',
        'aDate',
        'aComplianceName',
        'aValue',
        'aNorm',
        'bDate',
        'bComplianceName',
        'bValue',
        'bNorm',
        'cDate',
        'cComplianceName',
        'cValue',
        'cNorm',
        'dDate',
        'dComplianceName',
        'dValue',
        'dNorm',
      ] as (keyof NtisMapResearchDetails)[],
      infoColumns: [
        'address',
        'aDate',
        'aComplianceName',
        'aValue',
        'aNorm',
        'bDate',
        'bComplianceName',
        'bValue',
        'bNorm',
        'cDate',
        'cComplianceName',
        'cValue',
        'cNorm',
        'dDate',
        'dComplianceName',
        'dValue',
        'dNorm',
        'link',
      ] as (keyof NtisMapResearchDetails)[],
      optionsTree: ['a', 'b', 'c', 'd'].map((key) => ({
        label: `${this.translationsRef}.researchOptions.${key}`,
        translateLabel: true,
        key,
      })),
      selectedOptions: ['a', 'b', 'c', 'd'],
    },
  };

  disposalOlLayer = new VectorTileLayer({
    visible: true,
    source: new VectorTileSource({
      format: new MVT(),
      url: REST_API_BASE_URL + '/ntis-map/get-tiles/disposal/{z}/{x}/{y}',
    }),
    minZoom: 15,
    style: (feature): Style => {
      return this.getDisposalLayerStyle(feature);
    },
  });

  disposalLayer: MapLayer<NtisMapLayerExtras> = {
    key: LAYER_KEY_DISPOSAL,
    name: this.translationsRef + '.disposal',
    show: false,
    transparency: 0,
    removable: false,
    layers: [this.disposalOlLayer],
    extras: {
      filterFields: [
        {
          name: 'ntisShared.components.map.layerCols.disposal.disposalDate',
          field: 'disposal_date',
          filterInputType: 'date',
        },
      ],
      maxFilters: 1,
      tableColumns: ['link', 'address', 'disposalDate', 'stateName'] as (keyof NtisMapDisposalDetails)[],
      infoColumns: ['address', 'disposalDate', 'stateName', 'link'] as (keyof NtisMapDisposalDetails)[],
    },
  };

  @Input() layers: MapLayer<NtisMapLayerExtras>[] = [
    this.facilitiesLayer,
    this.buildingsLayer,
    this.dischargesLayer,
    this.wellsLayer,
    this.centLayer,
  ];

  customMenuMode: 'filter' | 'object-info' | 'clicked-points';
  clearFiltersEnabled = false;
  filterLayerIndex: number;
  showingTable: MapLayerKey;
  tableExpanded = false;
  clickedPoints: NtisMapClickedPoint[];
  objInfoLayerIndex: number;

  constructor(
    private ntisMapService: NtisMapService,
    private activatedRoute: ActivatedRoute,
    private tableExportService: TableExportService,
    private translateService: TranslateService,
    private changeDetectorRef: ChangeDetectorRef,
    private loaderService: LoaderService,
    public faIconsService: FaIconsService,
    authService: AuthService
  ) {
    this.canReadFaciTable = authService.isFormActionEnabled(NTIS_MAP, NtisActionsConst.READ_FACI_TABLE);
    this.canReadBuildTable = authService.isFormActionEnabled(NTIS_MAP, NtisActionsConst.READ_BUILD_TABLE);
    this.canReadCentTable = authService.isFormActionEnabled(NTIS_MAP, NtisActionsConst.READ_CENT_TABLE);
    this.canReadDiscTable = authService.isFormActionEnabled(NTIS_MAP, NtisActionsConst.READ_DISC_TABLE);
    this.canReadDisposalLayer = authService.isFormActionEnabled(NTIS_MAP, NtisActionsConst.READ_DISPOSAL_LAYER);
    this.canReadResearchLayer = authService.isFormActionEnabled(NTIS_MAP, NtisActionsConst.READ_RESEARCH_LAYER);
    this.canReadAggloTable = authService.isFormActionEnabled(NTIS_MAP, NtisActionsConst.READ_AGGLO_TABLE);

    if (this.canReadDisposalLayer) {
      this.layers.push(this.disposalLayer);
    }
    if (this.canReadResearchLayer) {
      this.layers.push(this.researchLayer);
    }
    this.layers.push(this.aggloLayer, this.prelAggloLayer);

    this.disposalOlLayer
      .getSource()
      .setTileLoadFunction(this.ntisMapService.loadVectorTileWithAuth.bind(this.ntisMapService));
    this.researchOlLayer
      .getSource()
      .setTileLoadFunction(this.ntisMapService.loadVectorTileWithAuth.bind(this.ntisMapService));
  }

  ngOnInit(): void {
    this.ntisMapService.getCountiesWithMunicipalities().subscribe((result) => {
      const _selectedAggloItems: string[] = [];
      this.aggloLayer.extras.optionsTree = result.map((county) => ({
        label: county.description,
        translateLabel: false,
        key: county.code,
        children: county.children?.map((municipality) => {
          _selectedAggloItems.push(municipality.code);
          return {
            label: municipality.name,
            translateLabel: false,
            key: municipality.code,
          };
        }),
      }));
      this.aggloLayer.extras.selectedOptions = _selectedAggloItems;
      this.prelAggloLayer.extras.optionsTree = structuredClone(this.aggloLayer.extras.optionsTree);
      this.prelAggloLayer.extras.selectedOptions = [..._selectedAggloItems];
      this.allLayersChanged();
    });
  }

  ngAfterViewInit(): void {
    this.mapComponent.selectedMenuIndex$.pipe(takeUntil(this.destroy$)).subscribe((newValue) => {
      if (newValue != -1) {
        this.allLayersChanged();
        if (this.objInfoLayerIndex !== undefined) {
          this.clearDetails();
        }
      }
    });

    this.activatedRoute.queryParams.pipe(takeUntil(this.destroy$)).subscribe((queryParams) => {
      const extent = queryParams['extent'] && (JSON.parse(queryParams['extent'] as string) as Extent);
      if (extent && Array.isArray(extent)) {
        this.mapComponent.setBoundingExtent(extent, ProjectionLikeCode.WGS84);
      }
    });

    this.mapComponent.map.addLayer(this.detailsOlLayer);

    this.mapComponent.map.getView().on('change:resolution', () => {
      this.toggleInfoMessage();
    });
    this.toggleInfoMessage();
    this.changeDetectorRef.detectChanges();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.unsubscribe();
  }

  showObjInfoMenu(layer?: MapLayer<NtisMapLayerExtras>): void {
    if (layer) {
      this.objInfoLayerIndex = this.layers.indexOf(layer);
    }
    this.mapComponent.setSelectedMenuIndex(-1);
    this.customMenuMode = 'object-info';
    this.allLayersChanged();
  }

  isObjInfoMenuOpenForLayer(layer: MapLayer<NtisMapLayerExtras>): boolean {
    return (
      this.customMenuMode === 'object-info' &&
      this.mapComponent.getSelectedMenuIndex() === -1 &&
      this.layers.indexOf(layer) === this.objInfoLayerIndex
    );
  }

  showClickedPointsMenu(): void {
    this.mapComponent.setSelectedMenuIndex(-1);
    this.customMenuMode = 'clicked-points';
    this.allLayersChanged();
  }

  hideObjInfoMenu(): void {
    if (
      this.mapComponent.getSelectedMenuIndex() === -1 &&
      (this.customMenuMode === 'clicked-points' || this.customMenuMode === 'object-info')
    ) {
      this.mapComponent.setSelectedMenuIndex(null);
      this.customMenuMode = undefined;
      this.objInfoLayerIndex = undefined;
      this.allLayersChanged();
    }
  }

  toggleInfoMessage(): void {
    const zoom = this.mapComponent.map.getView().getZoom();
    const maxMinZoom = this.mapComponent.map
      .getAllLayers()
      .filter((layer) => layer.getVisible() && layer.getMinZoom() !== -Infinity)
      .reduce((accumulator, currentValue) => {
        if (accumulator < currentValue.getMinZoom()) {
          accumulator = currentValue.getMinZoom();
        }
        return accumulator;
      }, 0);
    if (zoom <= maxMinZoom) {
      if (!this.showInfoMessage) {
        this.showInfoMessage = true;
      }
    } else if (this.showInfoMessage) {
      this.showInfoMessage = false;
    }
  }

  handleToggleShowLayer(layer: MapLayer): void {
    this.toggleInfoMessage();
    if (this.showingTable === layer.key) {
      this.showingTable = undefined;
    }
  }

  clearDetails(): void {
    this.detailsOlLayer.getSource().clear();
  }

  drawDetailPoint(
    coordinates: Coordinate,
    id?: string | number,
    properties?: Record<string, unknown>,
    clearSource = false
  ): void {
    if (clearSource) {
      this.clearDetails();
    }
    const feature = new Feature({
      geometry: new Point(coordinates),
    });
    if (properties) {
      feature.setProperties(properties);
    }
    feature.setId(id);

    this.detailsOlLayer.getSource().addFeature(feature);
  }

  drawDetailLines(fromCoordinates: Coordinate, toCoordinatesList: Coordinate[], clearSource = true): void {
    if (clearSource) {
      this.clearDetails();
    }
    if (fromCoordinates && toCoordinatesList) {
      toCoordinatesList.forEach((toCoordinates) => {
        const feature = new Feature({
          geometry: new LineString([fromCoordinates, toCoordinates]),
        });
        this.detailsOlLayer.getSource().addFeature(feature);
      });
    }
  }

  handleMapSingleClick(event: MapBrowserEvent<UIEvent>): void {
    let clickedOnFeature = false;
    this.clearDetails();
    this.clickedPoints = [];
    if (!this.loaderService.showLoader.value?.show) {
      this.mapComponent.map.forEachFeatureAtPixel(
        event.pixel,
        (feature, layer) => {
          if (!clickedOnFeature) {
            if (
              layer === this.buildingsOlLayer ||
              (layer === this.detailsOlLayer &&
                feature.getGeometry().getType() === 'Point' &&
                (feature.getProperties() as DetailsPointProperties).layer === LAYER_KEY_BUILDINGS)
            ) {
              const id =
                typeof feature.getId() === 'number' && `${feature.getId()}`.endsWith('0')
                  ? `${feature.getId()}`.slice(0, -1)
                  : undefined;
              if (id) {
                const clickedPoint = {
                  id: parseInt(id),
                  layer: LAYER_KEY_BUILDINGS,
                  name: this.translationsRef + '.buildings',
                };
                if (
                  !this.clickedPoints.some(
                    (point) => point.layer === clickedPoint.layer && point.id === clickedPoint.id
                  )
                ) {
                  this.clickedPoints.push(clickedPoint);
                }
              }
            } else if (
              layer === this.facilitiesOlLayer ||
              (layer === this.detailsOlLayer &&
                feature.getGeometry().getType() === 'Point' &&
                (feature.getProperties() as DetailsPointProperties).layer === LAYER_KEY_FACILITIES)
            ) {
              const wtfId = feature.getProperties()['wtf_id'] as number;
              if (wtfId) {
                const clickedPoint = {
                  id: wtfId,
                  layer: LAYER_KEY_FACILITIES,
                  name: this.translationsRef + '.facilities',
                };
                if (
                  !this.clickedPoints.some(
                    (point) => point.layer === clickedPoint.layer && point.id === clickedPoint.id
                  )
                ) {
                  this.clickedPoints.push(clickedPoint);
                }
              }
            } else if (
              layer === this.dischargesOlLayer ||
              (layer === this.detailsOlLayer &&
                feature.getGeometry().getType() === 'Point' &&
                (feature.getProperties() as DetailsPointProperties).layer === LAYER_KEY_DISCHARGE)
            ) {
              const wtfId = feature.getProperties()['wtf_id'] as number;
              if (wtfId) {
                const clickedPoint = {
                  id: wtfId,
                  layer: LAYER_KEY_DISCHARGE,
                  name: this.translationsRef + '.discharges',
                };
                if (
                  !this.clickedPoints.some(
                    (point) => point.layer === clickedPoint.layer && point.id === clickedPoint.id
                  )
                ) {
                  this.clickedPoints.push(clickedPoint);
                }
              }
            } else if (layer === this.centOlLayer) {
              const properties = feature.getProperties();
              this.clickedPoints.push({
                id: feature.getId() as number,
                layer: LAYER_KEY_CENT,
                name: this.translationsRef + '.cent',
                description: `${properties['bn_street'] as string}, ${properties['bn_place_name'] as string}, ${
                  properties['bn_municipality'] as string
                }`,
              });
            } else if (layer === this.researchOlLayer) {
              this.clickedPoints.push({
                id: feature.getId() as number,
                layer: LAYER_KEY_RESEARCH,
                name: this.translationsRef + '.research',
              });
            } else if (layer === this.disposalOlLayer) {
              this.clickedPoints.push({
                id: feature.getId() as number,
                layer: LAYER_KEY_DISPOSAL,
                name: this.translationsRef + '.disposal',
              });
            } else if (this.clickedPoints.length === 0) {
              if (layer === this.aggloOlLayer) {
                clickedOnFeature = true;
                this.ntisMapService.getMapAggloObjectInfo(feature.getId() as number).subscribe((result) => {
                  this.aggloLayer.extras.infoData = result as NtisAggloMapTableItemView;
                  this.showObjInfoMenu(this.aggloLayer);
                  this.aggloOlLayer.changed();
                });
              }
            }
          }
        },
        { hitTolerance: 32 }
      );
      if (this.clickedPoints.length === 1) {
        this.selectClickedPoint(this.clickedPoints[0]);
      } else if (this.clickedPoints.length > 1) {
        this.clickedPoints.sort((a, b) => a.layer.localeCompare(b.layer) || a.name.localeCompare(b.name));
        this.ntisMapService.getNamesOfClickedPoints(this.clickedPoints).subscribe((result) => {
          result.forEach((item) => {
            const clickedPointsItem = this.clickedPoints.find(
              (point) => point.layer === item.layer && point.id === item.id && !point.description
            );
            if (clickedPointsItem) {
              clickedPointsItem.description = item.description;
            }
          });
          this.showClickedPointsMenu();
        });
      } else {
        this.hideObjInfoMenu();
      }
    }
  }

  selectClickedPoint(point: NtisMapClickedPoint): void {
    switch (point.layer) {
      case LAYER_KEY_BUILDINGS:
        this.ntisMapService.getBuildingDetails(point.id).subscribe((result) => {
          if(result.poId){
          this.drawDetailLines(
            result.coordinates,
            result.facilities.map((facility) => facility.facilityCoordinates)
          );
          result.facilities.forEach((facility) => {
            this.drawDetailPoint(facility.facilityCoordinates, undefined, {
              layer: LAYER_KEY_FACILITIES,
              wtf_id: facility.wtfId,
              wtf_type: facility.typeCode,
              wtf_state: facility.stateCode,
            } as DetailsPointProperties);
            if (facility.dischargeCoordinates) {
              this.drawDetailLines(facility.facilityCoordinates, [facility.dischargeCoordinates], false);
              this.drawDetailPoint(facility.dischargeCoordinates, undefined, {
                layer: LAYER_KEY_DISCHARGE,
                wtf_id: facility.wtfId,
              } as DetailsPointProperties);
            }
          });
          this.drawDetailPoint(result.coordinates, result.poId * 10, {
            layer: LAYER_KEY_BUILDINGS,
            mainPoint: true,
            cnt: 0,
          } as DetailsPointProperties);
          this.buildingsLayer.extras.infoData = { ...result, facilitiesCount: result.facilities.length };
          this.showObjInfoMenu(this.buildingsLayer);
        }});
        break;
      case LAYER_KEY_FACILITIES:
        this.ntisMapService.getFacilityDetails(point.id).subscribe((result) => {
          this.drawDetailLines(
            result.facilityCoordinates,
            result.servedObjects?.map((servedObject) => servedObject.coordinates) || []
          );
          this.drawDetailPoint(result.facilityCoordinates, undefined, {
            layer: LAYER_KEY_FACILITIES,
            wtf_id: result.wtfId,
            wtf_type: result.typeCode,
            wtf_state: result.stateCode,
            mainPoint: true,
          } as DetailsPointProperties);
          if (result.dischargeCoordinates) {
            this.drawDetailLines(result.facilityCoordinates, [result.dischargeCoordinates], false);
            this.drawDetailPoint(result.dischargeCoordinates, undefined, {
              layer: LAYER_KEY_DISCHARGE,
              wtf_id: result.wtfId,
            } as DetailsPointProperties);
          }
          result.servedObjects?.forEach((servedObject) => {
            this.drawDetailPoint(servedObject.coordinates, servedObject.id * 10, {
              layer: LAYER_KEY_BUILDINGS,
              cnt: 0,
            } as DetailsPointProperties);
          });
          this.facilitiesLayer.extras.infoData = { ...result };
          this.showObjInfoMenu(this.facilitiesLayer);
        });
        break;
      case LAYER_KEY_DISCHARGE:
        this.ntisMapService.getDischargeDetails(point.id).subscribe((result) => {
          this.drawDetailLines(
            result.facilityCoordinates,
            result.servedObjects?.map((servedObject) => servedObject.coordinates) || []
          );
          this.drawDetailPoint(result.facilityCoordinates, undefined, {
            layer: LAYER_KEY_FACILITIES,
            wtf_id: result.wtfId,
            wtf_type: result.typeCode,
            wtf_state: result.stateCode,
          } as DetailsPointProperties);
          this.drawDetailLines(result.facilityCoordinates, [result.dischargeCoordinates], false);
          this.drawDetailPoint(result.dischargeCoordinates, undefined, {
            layer: LAYER_KEY_DISCHARGE,
            wtf_id: result.wtfId,
            mainPoint: true,
          } as DetailsPointProperties);
          result.servedObjects?.forEach((servedObject) => {
            this.drawDetailPoint(servedObject.coordinates, servedObject.id * 10, {
              layer: LAYER_KEY_BUILDINGS,
              cnt: 0,
            } as DetailsPointProperties);
          });
          this.dischargesLayer.extras.infoData = {
            ...result,
            coordinates: transform(
              result.dischargeCoordinates,
              ProjectionLikeCode.WebMercator,
              ProjectionLikeCode.LKS94
            )
              .map((coord) => Math.round(coord))
              .join(', '),
          };
          this.showObjInfoMenu(this.dischargesLayer);
        });
        break;
      case LAYER_KEY_CENT:
        this.ntisMapService.getCentDetails(point.id).subscribe((result) => {
          this.centLayer.extras.infoData = { ...result };
          this.showObjInfoMenu(this.centLayer);
        });
        break;
      case LAYER_KEY_RESEARCH:
        this.ntisMapService.getResearchDetails(point.id).subscribe((result) => {
          this.researchLayer.extras.infoData = { ...result };
          this.showObjInfoMenu(this.researchLayer);
        });
        break;
      case LAYER_KEY_DISPOSAL:
        this.ntisMapService.getDisposalDetails(point.id).subscribe((result) => {
          this.disposalLayer.extras.infoData = { ...result };
          this.showObjInfoMenu(this.disposalLayer);
        });
        break;
    }
  }

  backToClickedPointsList(): void {
    if (this.clickedPoints?.length) {
      this.objInfoLayerIndex = undefined;
      this.clearDetails();
      this.showClickedPointsMenu();
    }
  }

  allLayersChanged(): void {
    this.layers?.forEach((layer) => layer.layers?.forEach((olLayer) => olLayer.changed()));
  }

  private getAggloLayerStyle(feature: FeatureLike, resolution: number): Style {
    const featureProperties = feature.getProperties();
    if (
      this.aggloLayer.extras?.selectedOptions?.includes(`${featureProperties.sav_kodas as number}`) &&
      featureProperties.a_tipas === 'G' &&
      !this.isFeatureFilteredOut(feature, this.aggloLayer.extras?.appliedFilters)
    ) {
      let fillAlpha = 0.7;
      if (resolution < 5) {
        fillAlpha = 0.07;
      } else if (resolution < 10) {
        fillAlpha = 0.1;
      } else if (resolution < 25) {
        fillAlpha = 0.2;
      } else if (resolution < 50) {
        fillAlpha = 0.3;
      } else if (resolution < 100) {
        fillAlpha = 0.5;
      } else if (resolution < 200) {
        fillAlpha = 0.6;
      }
      return new Style({
        stroke: new Stroke({
          color:
            this.aggloLayer.extras?.infoData?.id === featureProperties.a_ter_id
              ? 'hsla(270, 65%, 40%, 1)'
              : 'hsla(250, 60%, 50%, 1.0)',
          width: this.aggloLayer.extras?.infoData?.id === featureProperties.a_ter_id ? 2 : 1,
        }),
        fill: new Fill({
          color:
            this.aggloLayer.extras?.infoData?.id === featureProperties.a_ter_id
              ? `hsla(270, 65%, 60%, ${fillAlpha})`
              : `hsla(250, 60%, 55%, ${fillAlpha})`,
        }),
      });
    } else {
      return null;
    }
  }

  private getPrelAggloLayerStyle(feature: FeatureLike, resolution: number): Style {
    const featureProperties = feature.getProperties();
    if (
      this.prelAggloLayer.extras?.selectedOptions?.includes(`${featureProperties.sav_kodas as number}`) &&
      featureProperties.a_tipas === 'P' &&
      !this.isFeatureFilteredOut(feature, this.prelAggloLayer.extras?.appliedFilters)
    ) {
      let fillAlpha = 0.7;
      if (resolution < 5) {
        fillAlpha = 0.07;
      } else if (resolution < 10) {
        fillAlpha = 0.1;
      } else if (resolution < 25) {
        fillAlpha = 0.2;
      } else if (resolution < 50) {
        fillAlpha = 0.3;
      } else if (resolution < 100) {
        fillAlpha = 0.5;
      } else if (resolution < 200) {
        fillAlpha = 0.6;
      }
      return new Style({
        stroke: new Stroke({
          color:
            this.prelAggloLayer.extras.infoData?.id === featureProperties.a_ter_id
              ? 'hsla(300, 20%, 50%, 1)'
              : 'hsla(280, 10%, 60%, 1.0)',
          width: this.prelAggloLayer.extras.infoData?.id === featureProperties.a_ter_id ? 2 : 1,
        }),
        fill: new Fill({
          color:
            this.prelAggloLayer.extras.infoData?.id === featureProperties.a_ter_id
              ? `hsla(300, 20%, 70%, ${fillAlpha})`
              : `hsla(280, 10%, 65%, ${fillAlpha})`,
        }),
      });
    } else {
      return null;
    }
  }

  private getRadiusMultiplier(cnt: number, resolution: number, maxCnt = POINT_MAX_CNT): number {
    return !cnt || cnt === 1 ? 1 : (1 + (cnt * 2) / maxCnt) * Math.max(1, (2 - resolution / 150) / 1.6);
  }

  private getBuildingsLayerStyle(feature: FeatureLike, resolution: number, detailsMode = false): Style[] | Style {
    if (!detailsMode && this.isFeatureFilteredOut(feature, this.buildingsLayer.extras?.appliedFilters)) {
      return null;
    }
    const properties = feature.getProperties();
    const treatmentFilter = this.buildingsLayer.extras?.appliedFilters?.find(
      (filter) => filter.paramName === 'treatment'
    );
    if (treatmentFilter) {
      const hasCentr = properties['has_centr'] as string;
      const hasNonCentr = properties['has_non_centr'] as string;
      const centralizedFilter =
        treatmentFilter.paramValue.value === NtisTypeWastewaterTreatment.CENTRALIZED
          ? DB_BOOLEAN_TRUE
          : DB_BOOLEAN_FALSE;
      if (centralizedFilter === DB_BOOLEAN_TRUE ? hasCentr !== DB_BOOLEAN_TRUE : hasNonCentr !== DB_BOOLEAN_TRUE) {
        return null;
      }
    }
    const fill = new Fill({
      color: `hsla(${properties['mainPoint'] ? 22 : 54}, ${properties['mainPoint'] ? '88%' : '90%'}, ${
        properties['mainPoint'] ? '58%' : '80%'
      }, ${detailsMode ? 1 : properties['cnt'] === 0 ? 0.9 : 0.8})`,
    });
    const radius = Math.round(
      detailsMode
        ? (resolution <= 250 ? (resolution <= 100 ? 8 : 9) : 10) *
            this.getRadiusMultiplier(properties['cnt'] as number, resolution) *
            1.5
        : (resolution <= 250 ? (resolution <= 100 ? 8 : 9) : 10) *
            this.getRadiusMultiplier(properties['cnt'] as number, resolution)
    );
    return new Style({
      image: new RegularShape({
        fill: fill,
        stroke: new Stroke({
          color: properties['mainPoint']
            ? `hsla(38, 94%, 66%, ${properties['wtf_id'] ? 1 : 0.9})`
            : `hsla(40, 94%, 58%, ${properties['wtf_id'] ? 1 : 0.9})`,
          width: detailsMode ? (properties['mainPoint'] ? 5 : 2) : 1,
        }),
        declutterMode: 'none',
        points: 4,
        radius,
        rotation: 45 * (Math.PI / 180),
        angle: 0,
      }),
      zIndex: detailsMode ? (properties['mainPoint'] ? 100 : this.buildingsOlLayer.getZIndex() + 2) : undefined,
    });
  }

  private getDischargesLayerStyle(feature: FeatureLike, resolution: number, detailsMode = false): Style[] | Style {
    if (!detailsMode && this.isFeatureFilteredOut(feature, this.dischargesLayer.extras?.appliedFilters)) {
      return null;
    }
    const properties = feature.getProperties();
    const fill = new Fill({
      color: properties['mainPoint']
        ? `hsla(170, 97%, 40%, ${properties['wtf_id'] ? (detailsMode ? 1 : 0.9) : 0.7})`
        : `hsla(170, 92%, 30%, ${properties['wtf_id'] ? (detailsMode ? 1 : 0.9) : 0.7})`,
    });
    const radius = Math.round(
      detailsMode
        ? (resolution <= 250 ? (resolution <= 100 ? 8 : 9) : 10) *
            this.getRadiusMultiplier(properties['cnt'] as number, resolution) *
            1.5
        : (resolution <= 250 ? (resolution <= 100 ? 8 : 9) : 10) *
            this.getRadiusMultiplier(properties['cnt'] as number, resolution)
    );
    return new Style({
      image: new RegularShape({
        fill: fill,
        stroke: new Stroke({
          color: `hsla( ${properties['mainPoint'] ? 160 : 170} , 100%, 70%, ${properties['wtf_id'] ? 1 : 0.9})`,
          width: detailsMode ? (properties['mainPoint'] ? 5 : 2) : 1,
        }),
        declutterMode: 'none',
        points: 3,
        radius,
        rotation: 0,
        angle: 0,
      }),
      zIndex: detailsMode ? (properties['mainPoint'] ? 100 : this.dischargesOlLayer.getZIndex() + 2) : undefined,
    });
  }

  private getFacilitiesLayerStyle(feature: FeatureLike, resolution: number, detailsMode = false): Style[] | Style {
    if (!detailsMode && this.isFeatureFilteredOut(feature, this.facilitiesLayer.extras?.appliedFilters)) {
      return null;
    }
    const properties = feature.getProperties();
    const wtfType = properties['wtf_type'] as NtisWtfType;
    const types = [NtisWtfType.BIO, NtisWtfType.SEPTIC, NtisWtfType.RESERVOIR, NtisWtfType.PORTABLE_RESERVOIR];
    const typeIndex = types.indexOf(wtfType);
    const hue = typeIndex === -1 ? 14 : 14 + Math.round((typeIndex / (types.length - 1)) * 270);
    const wtfState = properties['wtf_state'] as NtisIntsStatus;
    const states = [NtisIntsStatus.CLOSED, NtisIntsStatus.REGISTERED, NtisIntsStatus.CONFIRMED];
    const stateIndex = states.indexOf(wtfState);
    const saturation = stateIndex === -1 ? 92 : 30 + Math.round((stateIndex / (states.length - 1)) * 60);
    const fill = new Fill({
      color: properties['mainPoint']
        ? `hsla(${hue + 50}, ${saturation}%, 30%, ${properties['wtf_id'] ? (detailsMode ? 1 : 0.9) : 0.7})`
        : `hsla(${hue}, ${saturation}%, 30%, ${properties['wtf_id'] ? (detailsMode ? 1 : 0.9) : 0.7})`,
    });
    const radius = Math.round(
      properties['mainPoint']
        ? (resolution <= 250 ? (resolution <= 100 ? 6 : 7) : 8) *
            this.getRadiusMultiplier(properties['cnt'] as number, resolution) *
            2
        : (resolution <= 250 ? (resolution <= 100 ? 6 : 7) : 8) *
            this.getRadiusMultiplier(properties['cnt'] as number, resolution)
    );
    return new Style({
      image: new Circle({
        radius,
        fill,
        declutterMode: 'none',
        stroke: new Stroke({
          color: `hsla(${hue}, ${Math.min(saturation, 100)}%, 80%, ${properties['wtf_id'] ? 1 : 0.9})`,
          width: detailsMode ? (properties['mainPoint'] ? 5 : 2) : 1,
        }),
      }),
      zIndex: detailsMode ? (properties['mainPoint'] ? 100 : this.facilitiesOlLayer.getZIndex() + 2) : undefined,
    });
  }

  private getDetailsLayerStyle(feature: FeatureLike, resolution: number): Style[] | Style {
    const geomType = feature.getGeometry().getType();
    if (geomType === 'LineString') {
      return [
        new Style({
          stroke: new Stroke({ width: 4, color: `hsla(0, 0%, 100%, 0.6)` }),
          zIndex: 1,
        }),
        new Style({
          stroke: new Stroke({ width: 2, color: `hsla(0, 0%, 20%, 1)` }),
          zIndex: 2,
        }),
      ];
    } else if (geomType === 'Point') {
      const properties = feature.getProperties() as DetailsPointProperties;
      switch (properties.layer) {
        case LAYER_KEY_FACILITIES:
          return this.getFacilitiesLayerStyle(feature, resolution, true);
        case LAYER_KEY_BUILDINGS:
          return this.getBuildingsLayerStyle(feature, resolution, true);
        case LAYER_KEY_DISCHARGE:
          return this.getDischargesLayerStyle(feature, resolution, true);
      }
    }
    return null;
  }

  private getWellsLayerStyle(feature: FeatureLike, resolution: number): Style[] | Style {
    const properties = feature.getProperties();
    const fill = new Fill({ color: `hsla(260, 80%, 40%, ${properties['we_id'] ? 0.9 : 0.7})` });
    return new Style({
      image: new RegularShape({
        fill: fill,
        stroke: new Stroke({
          color: `hsla(260, 40%, 60%, ${properties['we_id'] ? 1 : 0.9})`,
          width: 1,
        }),
        declutterMode: 'none',
        points: 4,
        radius: resolution <= 250 ? (resolution <= 100 ? 6 : 7) : 8,
        rotation: 0,
        angle: 0,
      }),
    });
  }

  private getCentLayerStyle(feature: FeatureLike): Style[] | Style {
    if (this.isFeatureFilteredOut(feature, this.centLayer.extras?.appliedFilters)) {
      return null;
    }
    const properties = feature.getProperties();
    const fill = new Fill({ color: `hsla(180, 80%, 40%, ${properties['wtf_id'] ? 0.9 : 0.7})` });
    return new Style({
      image: new RegularShape({
        fill: fill,
        stroke: new Stroke({
          color: `hsla(180, 40%, 60%, ${properties['wtf_id'] ? 1 : 0.9})`,
          width: 1,
        }),
        declutterMode: 'none',
        points: 4,
        radius: 8,
        rotation: 45 * (Math.PI / 180),
        angle: 0,
      }),
    });
  }

  private getResearchLayerStyle(feature: FeatureLike): Style {
    const researchColumns = this.researchLayer.extras?.selectedOptions;
    if (!researchColumns.length) {
      return null;
    }
    const properties = feature.getProperties();
    let hs = [0, 0]; // hue, saturation
    let zIndex = 0;

    const isPointSelected =
      this.isObjInfoMenuOpenForLayer(this.researchLayer) &&
      feature.getId() === this.researchLayer.extras.infoData['id'];

    const filterDate1 =
      this.researchLayer.extras?.appliedFilters?.[0]?.paramValue.values?.[0] &&
      new Date(this.researchLayer.extras.appliedFilters[0].paramValue.values[0]);
    filterDate1?.setHours(0);
    const filterDate2 =
      this.researchLayer.extras?.appliedFilters?.[0]?.paramValue.values?.[1] &&
      new Date(this.researchLayer.extras.appliedFilters[0].paramValue.values[1]);
    filterDate2?.setHours(23, 59, 59, 999);

    const complies = researchColumns.filter((col) => {
      const colDate = properties[`${col}_research_date`] && new Date(properties[`${col}_research_date`] as string);
      return (
        (colDate && filterDate1 && filterDate2 ? colDate >= filterDate1 && colDate <= filterDate2 : true) &&
        properties[`${col}_research_norm_compliance`] === 'RESEARCH_NORM_COMPLIES'
      );
    });
    const notComplies = researchColumns.filter((col) => {
      const colDate = properties[`${col}_research_date`] && new Date(properties[`${col}_research_date`] as string);
      return (
        (colDate && filterDate1 && filterDate2 ? colDate >= filterDate1 && colDate <= filterDate2 : true) &&
        properties[`${col}_research_norm_compliance`] === 'RESEARCH_NORM_NOT_COMPLIES'
      );
    });

    if (complies.length === researchColumns.length) {
      hs = [120, 100];
      zIndex = 2;
    } else if (notComplies.length) {
      hs = [0, 100];
      zIndex = 1;
    }

    const fill = new Fill({ color: `hsla(${hs[0]}, ${hs[1]}%, 40%, 0.9` });
    return new Style({
      image: new RegularShape({
        fill: fill,
        stroke: new Stroke({
          color: `hsl(${hs[0]}, ${hs[1]}%, 60%)`,
          width: isPointSelected ? 3 : 1,
        }),
        declutterMode: 'none',
        points: 4,
        radius: 9,
        radius2: 5,
        angle: 0,
      }),
      zIndex,
    });
  }

  private getDisposalLayerStyle(feature: FeatureLike): Style {
    const properties = feature.getProperties();
    let hs = [0, 0];
    let zIndex = 0;

    const isPointSelected =
      this.isObjInfoMenuOpenForLayer(this.disposalLayer) &&
      feature.getId() === this.disposalLayer.extras.infoData['id'];

    const filterDate1 =
      this.disposalLayer.extras?.appliedFilters?.[0]?.paramValue.values?.[0] &&
      new Date(this.disposalLayer.extras.appliedFilters[0].paramValue.values[0]);
    filterDate1?.setHours(0);
    const filterDate2 =
      this.disposalLayer.extras?.appliedFilters?.[0]?.paramValue.values?.[1] &&
      new Date(this.disposalLayer.extras.appliedFilters[0].paramValue.values[1]);
    filterDate2?.setHours(23, 59, 59, 999);

    const disposalDate =
      properties['ord_removed_sewage_date'] && new Date(properties['ord_removed_sewage_date'] as string);

    const finished =
      disposalDate &&
      filterDate1 &&
      filterDate2 &&
      disposalDate >= filterDate1 &&
      disposalDate <= filterDate2 &&
      properties['ord_state'] === OrderStatus.finished;

    if (finished) {
      hs = [120, 100];
      zIndex = 1;
    }

    const fill = new Fill({ color: `hsla(${hs[0]}, ${hs[1]}%, 40%, 0.9` });
    return new Style({
      image: new RegularShape({
        fill: fill,
        stroke: new Stroke({
          color: `hsl(${hs[0]}, ${hs[1]}%, 60%)`,
          width: isPointSelected ? 3 : 1,
        }),
        declutterMode: 'none',
        points: 3,
        radius: 8,
        angle: 0,
        rotation: 180 * (Math.PI / 180),
      }),
      zIndex,
    });
  }

  private isFeatureFilteredOut(feature: FeatureLike, filters: AdvancedSearchParameterStatement[]): boolean {
    if (filters?.length) {
      const properties = feature.getProperties();
      return filters.some((filter) => {
        if (!Object.hasOwn(properties, filter.paramName)) {
          return false;
        }
        let property: string | string[] =
          properties[filter.paramName] !== undefined
            ? `${properties[filter.paramName] as string}`.toLocaleLowerCase()
            : undefined;
        if (property && property.startsWith('[') && isValidJSON(property)) {
          const parsedJSON = JSON.parse(property) as unknown;
          if (Array.isArray(parsedJSON)) {
            property = parsedJSON.map((jsonEntry) =>
              typeof jsonEntry === 'number' ? `${jsonEntry}` : (jsonEntry as string)
            );
          }
        }
        const filterValue = filter.paramValue?.value?.toLocaleLowerCase() || undefined;
        const filterValues = filter.paramValue?.values?.map((value) => value.toLocaleLowerCase()) || undefined;
        switch (filter.paramValue.condition) {
          case ExtendedSearchCondition.Equals:
            if (Array.isArray(property)) {
              return !property.includes(filterValue);
            } else {
              if (filterValue && property !== filterValue) {
                return true;
              } else if (filterValues?.length && !filterValues.includes(property)) {
                return true;
              }
            }
            break;
          case ExtendedSearchCondition.NotEqual:
            if (Array.isArray(property)) {
              return property.includes(filterValue);
            } else if (property === filterValue) {
              return true;
            }
            break;
          case ExtendedSearchCondition.Contains:
            if (Array.isArray(property)) {
              return !property.some((value) => value?.includes(filterValue));
            } else if (filterValue && (!property || !property.includes(filterValue))) {
              return true;
            }
            break;
          case ExtendedSearchCondition.Empty:
            if (Array.isArray(property)) {
              return !property.some((value) => !value);
            } else if (property) {
              return true;
            }
            break;
          case ExtendedSearchCondition.NotEmpty:
            if (Array.isArray(property)) {
              return !property.some((value) => value);
            } else if (!property) {
              return true;
            }
            break;
        }
        return false;
      });
    }
    return false;
  }

  handleClickFilter(layerKey?: MapLayerKey): void {
    if (layerKey) {
      this.mapComponent.setSelectedMenuIndex(-1);
      this.customMenuMode = 'filter';
      this.filterLayerIndex = this.layers.findIndex((layer) => layer.key === layerKey);
    } else {
      this.mapComponent.setSelectedMenuIndex(
        this.mapComponent.menuItems.findIndex((item) => item.mode === MapMenuMode.Layers)
      );
      this.customMenuMode = undefined;
      this.filterLayerIndex = undefined;
    }
  }

  handleClickTable(layerKey?: MapLayerKey): void {
    if (layerKey) {
      if (this.showingTable !== layerKey) {
        this.showingTable = layerKey;
        if (!this.mapComponent.getLayerByKey<NtisMapLayerExtras>(layerKey)?.extras.tableData) {
          this.loadTableData(layerKey);
        }
      }
    } else {
      this.showingTable = undefined;
    }
  }

  handleClickExport(layer: MapLayer<NtisMapLayerExtras>, format: MapExportFormat): void {
    if (layer.extras?.tableColumns && layer.extras.tableData) {
      if (format === MapExportFormat.CSV) {
        this.translateService
          .get(`${this.translationsRef}.layerCols.${layer.key}`)
          .subscribe((translations: Record<string, string>) => {
            this.tableExportService.exportCSV(
              layer.extras.tableColumns.map((col) => ({ title: translations[col], dataKey: col })),
              layer.extras.tableData.items,
              layer.translateName === false ? layer.name : (this.translateService.instant(layer.name) as string)
            );
          });
      } else if (format === MapExportFormat.GeoJSON) {
        if ([LAYER_KEY_FACILITIES, LAYER_KEY_BUILDINGS, LAYER_KEY_DISCHARGE, LAYER_KEY_CENT].includes(layer.key)) {
          const geojsons: GeoJSON[] = layer.extras.tableData.items
            .filter((row) => (row.x && row.y) || (row.minX && row.minY))
            .map<GeoJSON>((row) => ({
              type: 'Feature',
              geometry: {
                crs: {
                  type: 'name',
                  properties: {
                    name: 'EPSG:3857',
                  },
                },
                type: 'Point',
                coordinates: [row.x || row.minX, row.y || row.minY],
              },
              properties: layer.extras.tableColumns.reduce<Record<string, unknown>>((accumulator, col) => {
                accumulator[col] = row[col];
                return accumulator;
              }, {}),
            }));
          FileSaver.saveAs(
            new Blob([JSON.stringify(geojsons)], { type: 'application/geo+json' }),
            `${
              layer.translateName === false ? layer.name : (this.translateService.instant(layer.name) as string)
            }.geojson`
          );
        } else if (layer.key === LAYER_KEY_AGGLO) {
          this.ntisMapService.getMapAggloTableGeoms(this.aggloLayer.extras?.appliedFilters).subscribe((result) => {
            const geojsons: GeoJSON[] = layer.extras.tableData.items
              .filter((row) => result[row.id as number])
              .map<GeoJSON>((row) => ({
                type: 'Feature',
                geometry: result[row.id as number],
                properties: layer.extras.tableColumns.reduce<Record<string, unknown>>((accumulator, col) => {
                  accumulator[col] = row[col];
                  return accumulator;
                }, {}),
              }));
            FileSaver.saveAs(
              new Blob([JSON.stringify(geojsons)], { type: 'application/geo+json' }),
              `${
                layer.translateName === false ? layer.name : (this.translateService.instant(layer.name) as string)
              }.geojson`
            );
          });
        }
      }
    }
  }

  loadTableData(layer: MapLayerKey): void {
    switch (layer) {
      case LAYER_KEY_AGGLO:
        this.ntisMapService.getMapAggloTable(this.aggloLayer.extras?.appliedFilters).subscribe((result) => {
          this.aggloLayer.extras.tableData = result as NtisMapTableResult<Partial<NtisAggloMapTableItem>>;
        });
        break;
      case LAYER_KEY_BUILDINGS:
        this.ntisMapService.getMapBuildingsTable(this.buildingsLayer.extras?.appliedFilters).subscribe((result) => {
          this.buildingsLayer.extras.tableData = result as NtisMapTableResult<Partial<NtisBuildingsMapTableItem>>;
        });
        break;
      case LAYER_KEY_FACILITIES:
        this.ntisMapService.getMapFacilitiesTable(this.facilitiesLayer.extras?.appliedFilters).subscribe((result) => {
          this.facilitiesLayer.extras.tableData = result as NtisMapTableResult<Partial<NtisMapFacilityDetails>>;
        });
        break;
      case LAYER_KEY_DISCHARGE:
        this.ntisMapService.getMapDischargesTable(this.dischargesLayer.extras?.appliedFilters).subscribe((result) => {
          this.dischargesLayer.extras.tableData = result as NtisMapTableResult<Partial<NtisMapFacilityDetails>>;
        });
        break;
      case LAYER_KEY_CENT:
        this.ntisMapService.getMapCentTable(this.centLayer.extras?.appliedFilters).subscribe((result) => {
          this.centLayer.extras.tableData = result as NtisMapTableResult<Partial<NtisMapCentDetails>>;
        });
        break;
      case LAYER_KEY_RESEARCH:
        this.ntisMapService.getMapResearchTable(this.researchLayer.extras?.appliedFilters).subscribe((result) => {
          this.researchLayer.extras.tableData = result as NtisMapTableResult<Partial<NtisMapResearchDetails>>;
        });
        break;
      case LAYER_KEY_DISPOSAL:
        this.ntisMapService.getMapDisposalTable(this.disposalLayer.extras?.appliedFilters).subscribe((result) => {
          this.disposalLayer.extras.tableData = result as NtisMapTableResult<Partial<NtisMapDisposalDetails>>;
        });
        break;
    }
  }

  handleSaveFilters(filters: AdvancedSearchParameterStatement[], filterLayer?: MapLayer<NtisMapLayerExtras>): void {
    if (!filterLayer) {
      filterLayer = this.layers[this.filterLayerIndex];
    }
    filterLayer.extras.appliedFilters = filters;
    filterLayer.layers.forEach((layer) => {
      layer.changed();
    });
    this.handleClickFilter();
    if (this.showingTable === filterLayer.key) {
      this.loadTableData(this.showingTable);
    } else {
      filterLayer.extras.tableData = undefined;
    }
    if (filters?.length) {
      this.clearFiltersEnabled = true;
    }
  }

  handleClearAllFilters(): void {
    this.layers.forEach((layer) => {
      if (layer.extras?.appliedFilters?.length) {
        let filters: AdvancedSearchParameterStatement[] = [];
        if (layer === this.facilitiesLayer) {
          filters = [
            {
              paramName: 'wtf_state',
              paramValue: {
                condition: '=',
                upperLower: 'caseInsensitiveLatin',
                value: undefined,
                values: [REGISTERED, CONFIRMED],
              },
            },
          ];
        }
        this.handleSaveFilters(filters, layer);
      }
    });
    this.clearFiltersEnabled = false;
  }
}
