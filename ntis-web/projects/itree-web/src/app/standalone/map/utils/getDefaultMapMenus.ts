import { MapMenuMode } from '../enums/map-menu-mode';
import { MapMenu } from '../types/map-menu';

export const getDefaultMapMenus = (): MapMenu[] => {
  return [getSearchMapMenu(), getLayersMapMenu()];
};

export const getSearchMapMenu = (): MapMenu => ({
  mode: MapMenuMode.Search,
  label: 'common.action.search2',
  icon: {
    iconName: 'faSearch',
    iconStyle: 'fas',
  },
});

export const getLayersMapMenu = (): MapMenu => ({
  mode: MapMenuMode.Layers,
  label: 'map.common.layers',
  icon: {
    iconName: 'faLayerGroup',
    iconStyle: 'fas',
  },
});
