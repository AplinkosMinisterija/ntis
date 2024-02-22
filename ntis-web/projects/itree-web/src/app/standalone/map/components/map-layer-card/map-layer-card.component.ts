import { Component, EventEmitter, Inject, Input, Output, TemplateRef, forwardRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FormsModule } from '@angular/forms';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { MapComponent } from '../map/map.component';
import { SliderModule } from 'primeng/slider';
import { Subject } from 'rxjs';
import { TooltipModule } from 'primeng/tooltip';

@Component({
  selector: 'map-layer-card',
  standalone: true,
  imports: [CommonModule, TooltipModule, FontAwesomeModule, ItreeCommonsModule, SliderModule, FormsModule],
  templateUrl: './map-layer-card.component.html',
  styleUrls: ['./map-layer-card.component.scss'],
})
export class MapLayerCardComponent {
  readonly destroy$ = new Subject<void>();
  readonly translationsRef = 'map.components.layerCard';
  template: TemplateRef<unknown>;
  showFilter = false;
  showTable = false;

  constructor(
    public faIconsService: FaIconsService,
    @Inject(forwardRef(() => MapComponent)) public mapComponent: MapComponent
  ) {}

  @Input() index: number;
  @Output() toggleShow = new EventEmitter<void>();
  @Output() remove = new EventEmitter<void>();
  @Output() moveUp = new EventEmitter<void>();
  @Output() moveDown = new EventEmitter<void>();
  @Output() changeTransparency = new EventEmitter<void>();
}
