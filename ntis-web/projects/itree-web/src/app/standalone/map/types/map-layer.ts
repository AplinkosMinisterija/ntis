import Layer from 'ol/layer/Layer';

export interface MapLayer<T = unknown> {
  key: string;
  name: string;
  translateName?: boolean;
  show: boolean;
  transparency: number;
  removable: boolean;
  layers: Layer[];
  extras?: T;
}
