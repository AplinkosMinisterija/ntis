import { Coordinate } from 'ol/coordinate';
import { ProjectionLikeCode } from '../enums/projection-like-code';

export type SearchByCoordinatesEvent = {
  coordinates: Coordinate;
  projectionLikeCode: ProjectionLikeCode;
};
