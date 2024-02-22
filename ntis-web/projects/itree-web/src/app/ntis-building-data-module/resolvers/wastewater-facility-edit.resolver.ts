import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router } from '@angular/router';
import { NtisWastewaterTreatmentFaci } from '@itree-commons/src/lib/model/api/api';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { Observable, of } from 'rxjs';
import { BuildingDataService } from '../services/building-data.service';

@Injectable({
  providedIn: 'root',
})
export class WastewaterFacilityEditResolver implements Resolve<NtisWastewaterTreatmentFaci> {
  constructor(
    private buildingDataService: BuildingDataService,
    private router: Router,
    protected commonFormServices: CommonFormServices
  ) {}

  resolve(route: ActivatedRouteSnapshot): Observable<NtisWastewaterTreatmentFaci> {
    if (route.paramMap.get('id') && route.paramMap.get('id') !== 'new') {
      return this.buildingDataService.getFacility(route.paramMap.get('id'));
    } else {
      return of(null);
    }
  }
}
