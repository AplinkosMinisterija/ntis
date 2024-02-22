import { Component, Inject, TemplateRef, forwardRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MapComponent } from '../map/map.component';
import { MapLayerCardComponent } from '../map-layer-card/map-layer-card.component';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';

@Component({
  selector: 'map-menu-layers',
  standalone: true,
  imports: [CommonModule, MapLayerCardComponent, ItreeCommonsModule],
  templateUrl: './map-menu-layers.component.html',
  styleUrls: ['./map-menu-layers.component.scss'],
})
export class MapMenuLayersComponent {
  readonly translationsRef = 'map.components.menuLayers';
  filterLayerIndex: number;
  filterTemplate: TemplateRef<unknown>;

  constructor(@Inject(forwardRef(() => MapComponent)) public mapComponent: MapComponent) {}

  toggleShowLayer(index: number): void {
    const layer = this.mapComponent.layers[index];
    if (layer) {
      layer.show = !layer.show;
      layer.layers.forEach((olLayer) => {
        olLayer.setVisible(layer.show);
      });
      this.mapComponent.toggleShowLayer.emit(layer);
    }
  }

  moveUpLayer(index: number): void {
    if (index > 0) {
      this.mapComponent.layers.splice(index - 1, 0, this.mapComponent.layers.splice(index, 1)[0]);
      this.mapComponent.resetLayersZIndexes();
    }
  }

  moveDownLayer(index: number): void {
    if (index < this.mapComponent.layers.length - 1) {
      this.mapComponent.layers.splice(index + 1, 0, this.mapComponent.layers.splice(index, 1)[0]);
      this.mapComponent.resetLayersZIndexes();
    }
  }

  updateLayersTransparency(index: number): void {
    this.mapComponent.layers[index].layers.forEach((layer) => {
      layer.setOpacity(1 - this.mapComponent.layers[index].transparency / 100);
    });
  }

  removeLayer(index: number): void {
    this.mapComponent.layers[index].layers.forEach((layer) => {
      this.mapComponent.map.removeLayer(layer);
    });
    this.mapComponent.layers.splice(index, 1);
    this.mapComponent.resetLayersZIndexes();
  }
}
