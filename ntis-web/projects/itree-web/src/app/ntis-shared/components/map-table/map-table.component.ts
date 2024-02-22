import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { TooltipModule } from 'primeng/tooltip';

export enum MapExportFormat {
  CSV,
  GeoJSON,
}

@Component({
  selector: 'ntis-map-table',
  templateUrl: './map-table.component.html',
  styleUrls: ['./map-table.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, FontAwesomeModule, TooltipModule, OverlayPanelModule],
})
export class MapTableComponent {
  readonly translationsRef = 'ntisShared.components.mapTable';
  readonly MapExportFormat = MapExportFormat;

  @Input() title: string;
  @Input() translateTitle = false;
  @Input() showingItemsCount = 0;
  @Input() totalItemsCount = 0;
  @Input() filtersApplied = true;
  @Input() exportAvailable = true;
  @Input() tableExpanded: boolean;

  @Output() clickClose = new EventEmitter<void>();
  @Output() clickExport = new EventEmitter<MapExportFormat>();
  @Output() tableExpandedChange = new EventEmitter<boolean>();

  exportOpen = false;

  constructor(public faIconsService: FaIconsService) {}

  toggleExpand(): void {
    this.tableExpanded = !this.tableExpanded;
    this.tableExpandedChange.emit(this.tableExpanded);
  }
}
