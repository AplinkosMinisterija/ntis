import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot } from '@angular/router';
import { CommonResult } from '@itree-commons/src/lib/model/api';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { Observable } from 'rxjs';
import { NtisWastewaterFacilityView } from '../models/browse-page';
import { BuildingDataService } from '../services/building-data.service';

@Injectable({
  providedIn: 'root',
})
export class WastewaterFacilityViewResolver {
  constructor(private buildingDataService: BuildingDataService, protected commonFormServices: CommonFormServices) {}

  resolve(route: ActivatedRouteSnapshot): Observable<CommonResult<NtisWastewaterFacilityView>> {
    return this.buildingDataService.getTreatmentFacilityInfo(route.paramMap.get('id'));
  }
}
