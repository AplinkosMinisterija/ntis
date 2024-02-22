import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve } from '@angular/router';
import { NtisServedBuildingUpdateModel } from '@itree-commons/src/lib/model/api/api';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { Observable } from 'rxjs';
import { BuildingDataService } from '../services/building-data.service';

@Injectable({
  providedIn: 'root',
})
export class WastewaterFacilityBuildingEditResolver implements Resolve<NtisServedBuildingUpdateModel> {
  constructor(private buildingDataService: BuildingDataService, protected commonFormServices: CommonFormServices) {}
  resolve(route: ActivatedRouteSnapshot): Observable<NtisServedBuildingUpdateModel> {
    return this.buildingDataService.getBuildingAgreement(route.paramMap.get('id'));
  }
}
