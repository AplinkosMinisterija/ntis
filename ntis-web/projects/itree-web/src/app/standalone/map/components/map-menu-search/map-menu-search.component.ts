import { CommonModule } from '@angular/common';
import { Component, Inject, OnDestroy, forwardRef } from '@angular/core';
import { Coordinate } from 'ol/coordinate';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { MapComponent } from '../map/map.component';
import { MapLocationSearchResult } from '../../types/map-location-search-result';
import { MapService } from '../../services/map.service';
import { ProjectionLikeCode } from '../../enums/projection-like-code';
import { SearchTermSuggestOption } from '@elastic/elasticsearch/lib/api/types';
import { TooltipModule } from 'primeng/tooltip';

@Component({
  selector: 'map-menu-search',
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, FormsModule, ReactiveFormsModule, TooltipModule, FontAwesomeModule],
  templateUrl: './map-menu-search.component.html',
  styleUrls: ['./map-menu-search.component.scss'],
})
export class MapMenuSearchComponent implements OnDestroy {
  readonly translationsRef = 'map.components.menuSearch';

  locationSearchControl = new FormControl<string>('');
  locationSearchSuggestions: SearchTermSuggestOption[];
  locationSearchResults: MapLocationSearchResult[];

  constructor(
    @Inject(forwardRef(() => MapComponent)) public mapComponent: MapComponent,
    public faIconsService: FaIconsService,
    private mapService: MapService
  ) {}

  ngOnDestroy(): void {
    this.clearSearchPins();
  }

  handleLocationSearch(inputValue?: string): void {
    if (typeof inputValue !== 'string') {
      inputValue = this.locationSearchControl.value;
    }
    if (inputValue) {
      const wgsRegex =
        /^\s*[-+]?([1-8]?\d(\.\d+)?|90(\.0+)?)\s*,\s*[-+]?(180(\.0+)?|((1[0-7]\d)|([1-9]?\d))(\.\d+)?)\s*$/;
      const lksRegex = /^\s*(\d{6})(\.\d+)?\s*,\s*(\d{7})(\.\d+)?\s*$/;
      const lksReverseRegex = /^\s*(\d{7})(\.\d+)?\s*,\s*(\d{6})(\.\d+)?\s*$/;
      if (wgsRegex.test(inputValue)) {
        const coordinatesString = inputValue.replaceAll(/\s/g, '');
        let coordinates: Coordinate = coordinatesString.split(',').map((value) => parseFloat(value));
        if (!(coordinates[0] >= 20 && coordinates[0] <= 30 && coordinates[1] >= 50 && coordinates[1] <= 60)) {
          coordinates = coordinates.reverse();
        }
        if (this.mapComponent.getSetting('showPinOnSearch')) {
          this.showPinInCoordinates(coordinates, ProjectionLikeCode.WGS84);
        }
        this.mapComponent.searchByCoordinates.emit({ coordinates, projectionLikeCode: ProjectionLikeCode.WGS84 });
        this.mapComponent.setCenterCoordinates(coordinates, 17, ProjectionLikeCode.WGS84);
      } else if (lksRegex.test(inputValue) || lksReverseRegex.test(inputValue)) {
        const coordinatesString = inputValue.replaceAll(/\s/g, '');
        let coordinates: Coordinate = coordinatesString.split(',').map((value) => parseFloat(value));
        if (lksReverseRegex.test(inputValue)) {
          coordinates = coordinates.reverse();
        }
        if (this.mapComponent.getSetting('showPinOnSearch')) {
          this.showPinInCoordinates(coordinates, ProjectionLikeCode.LKS94);
        }
        this.mapComponent.searchByCoordinates.emit({ coordinates, projectionLikeCode: ProjectionLikeCode.LKS94 });
        this.mapComponent.setCenterCoordinates(coordinates, 17, ProjectionLikeCode.LKS94);
      } else {
        this.mapService.getLocationResults(inputValue).subscribe({
          next: (result) => {
            this.locationSearchResults = result;
          },
          error: () => {
            this.mapComponent.showGeoportalWarning();
          },
        });
      }
    }
  }

  handleClickLocationResult(result: MapLocationSearchResult): void {
    this.mapComponent.clickSearchResult.emit(result);
    if (result.extent) {
      this.mapComponent.setBoundingExtent(result.extent, ProjectionLikeCode.WGS84);
    }
    if (result.coordinates) {
      if (this.mapComponent.getSetting('showPinOnSearch')) {
        this.showPinInCoordinates(result.coordinates, ProjectionLikeCode.WGS84);
      }
      if (!result.extent) {
        this.mapComponent.setCenterCoordinates(result.coordinates, 17, ProjectionLikeCode.WGS84);
      }
    }
  }

  clearSearchPins(): void {
    const source = this.mapComponent.pinLayer.getSource();
    this.mapComponent.pinLayer
      .getSource()
      .getFeatures()
      .forEach((feature) => {
        if (feature.getProperties()['search']) {
          source.removeFeature(feature);
        }
      });
  }

  showPinInCoordinates(
    coordinates: Coordinate,
    projection: ProjectionLikeCode = ProjectionLikeCode.WebMercator,
    clearSource = true
  ): void {
    if (clearSource) {
      this.clearSearchPins();
    }
    this.mapComponent.showPinInCoordinates(coordinates, projection, { search: true });
  }
}
