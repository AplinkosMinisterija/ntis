import BaseLayer from 'ol/layer/Base';

export interface MapBackgroundLayer<T extends BaseLayer> {
  key: string;
  layer: T;
  imagePath: string;
}
