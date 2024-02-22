import { FaIconData } from '@itree-commons/src/lib/types/fa-icons';
import { MapMenuMode } from '../enums/map-menu-mode';

export interface MapMenu {
  mode: MapMenuMode;
  label: string;
  icon: FaIconData;
  key?: string;
  translateLabel?: boolean;
  visible?: boolean;
}
