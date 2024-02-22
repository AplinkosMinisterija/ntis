import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve } from '@angular/router';
import { CommonResult } from '@itree-commons/src/lib/model/api';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { Observable } from 'rxjs';
import { ServedObjectsView } from '../../ntis-shared/models/served-objects-view';
import { BuildingDataService } from '../services/building-data.service';

@Injectable({
  providedIn: 'root',
})
export class WastewaterFacilityBuildingViewResolver implements Resolve<CommonResult<ServedObjectsView>> {
  constructor(private buildingDataService: BuildingDataService, protected commonFormServices: CommonFormServices) {}
  resolve(route: ActivatedRouteSnapshot): Observable<CommonResult<ServedObjectsView>> {
    return this.buildingDataService.getBuildingData(route.paramMap.get('id'));
  }
}
