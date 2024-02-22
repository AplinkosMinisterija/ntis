import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, ResolveEnd, Router } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { BaseBrowseForm } from '@itree/ngx-s2-commons';
import { filter } from 'rxjs';
import { FaIconData } from '../types/fa-icons';
import { SprRouteData } from '../types/routing';

interface Breadcrumb {
  name: string;
  url: string;
  keepFilters?: boolean;
  icon?: FaIconData;
  translate: boolean;
}

@Injectable({
  providedIn: 'root',
})
export class BreadcrumbsService {
  breadcrumbs: Breadcrumb[] = [];

  constructor(private router: Router) {
    this.router.events.pipe(filter((event) => event instanceof ResolveEnd)).subscribe((event) => {
      if (event instanceof ResolveEnd) {
        this.breadcrumbs = [];
        this.createBreadcrumbs(event.state.root);
      }
    });
  }

  private createBreadcrumbs(routeSnapshot: ActivatedRouteSnapshot): void {
    if (routeSnapshot.routeConfig && typeof routeSnapshot.routeConfig.path === 'string') {
      const data = routeSnapshot.routeConfig.data as SprRouteData;
      let breadcrumbName = data?.breadcrumb;
      let translateBreadcrumb = data?.translateBreadcrumb !== false;
      if (!breadcrumbName) {
        const id = routeSnapshot.params.id as string;
        if (routeSnapshot.routeConfig.path === RoutingConst.EDIT_WITH_ID && id) {
          translateBreadcrumb = true;
          breadcrumbName =
            id === RoutingConst.NEW ? 'components.breadcrumbs.newRecord' : 'components.breadcrumbs.editRecord';
        }
      }
      if (breadcrumbName) {
        const url = data?.breadcrumbUrl || data?.breadcrumbUrl === null ? null : this.getUrl(routeSnapshot);
        const keepFilters = this.isBrowseForm(routeSnapshot);
        if (url !== null) {
          this.addBreadcrumb(breadcrumbName, url, keepFilters, data?.breadcrumbIcon, translateBreadcrumb);
        }
      }
    }
    if (routeSnapshot.firstChild) {
      this.createBreadcrumbs(routeSnapshot.firstChild);
    }
  }

  private isBrowseForm(routeSnapshot: ActivatedRouteSnapshot): boolean {
    if (routeSnapshot.routeConfig) {
      const component = routeSnapshot.routeConfig.component;
      if (component) {
        return component.prototype instanceof BaseBrowseForm;
      } else {
        const children = routeSnapshot.routeConfig.children;
        if (children) {
          return children.some(
            (child) => child.path === '' && !!child.component && child.component.prototype instanceof BaseBrowseForm
          );
        }
      }
    }
    return false;
  }

  private addBreadcrumb(
    name: string,
    url: string,
    keepFilters = false,
    icon: FaIconData = undefined,
    translate: boolean
  ): void {
    this.breadcrumbs.push({
      name,
      url,
      keepFilters,
      icon,
      translate,
    });
  }

  private getUrl(routeSnapshot: ActivatedRouteSnapshot): string {
    return (
      '/' +
      routeSnapshot.pathFromRoot
        .filter((route) => route.url?.length)
        .map((route) => route.url.map((segment) => segment.toString()).join('/'))
        .join('/')
    );
  }
}
