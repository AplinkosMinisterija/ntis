import { Coordinate } from 'ol/coordinate';

export type GeoJSON = {
  type: 'Feature';
  geometry: {
    type: 'Point' | 'LineString' | 'Polygon' | 'MultiPoint' | 'MultiLineString' | 'MultiPolygon';
    crs?: {
      type: string;
      properties: Record<string, unknown>;
    };
    coordinates: Coordinate;
  };
  properties?: Record<string, unknown>;
};
