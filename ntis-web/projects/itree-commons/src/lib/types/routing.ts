import { Data, Route } from '@angular/router';
import { FaIconData } from './fa-icons';

export interface SprRouteData extends Data {
  breadcrumb?: string;
  breadcrumbUrl?: string | null;
  breadcrumbIcon?: FaIconData;
  translateBreadcrumb?: boolean;
  formCode?: string;
}

export interface SprRoute extends Route {
  data?: SprRouteData;
  children?: SprRoutes;
}

export type SprRoutes = SprRoute[];
