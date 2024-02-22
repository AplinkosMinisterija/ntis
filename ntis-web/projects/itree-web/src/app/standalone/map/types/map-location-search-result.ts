import { Coordinate } from 'ol/coordinate';
import { Extent } from 'ol/extent';

export interface MapLocationSearchResult {
  coordinates: Coordinate;
  extent: Extent;
  name: string;
  description: string;
  type: string;
}
