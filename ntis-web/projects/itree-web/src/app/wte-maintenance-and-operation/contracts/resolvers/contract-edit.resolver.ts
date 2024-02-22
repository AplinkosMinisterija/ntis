import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { EMPTY, Observable, catchError } from 'rxjs';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { UrlSegmentsConst } from '@itree-commons/src/constants/urlSegments.const';
import { NtisContractsService } from '../services/ntis-contracts.service';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { NtisContractEditModel } from '@itree-commons/src/lib/model/api/api';
import { SprErrorHandler } from '@itree-commons/src/lib/errors/error-handler';

@Injectable({
  providedIn: 'root',
})
export class ContractEditResolver {
  constructor(
    private ntisContractsService: NtisContractsService,
    protected commonFormServices: CommonFormServices,
    private sprErrorHandler: SprErrorHandler
  ) {}
  resolve(route: ActivatedRouteSnapshot): Observable<NtisContractEditModel> {
    return route.queryParams.token
      ? this.ntisContractsService.getContractByToken(route.queryParams.token as string).pipe(
          catchError((error) => {
            void this.commonFormServices.router.navigate([
              RoutingConst.INTERNAL,
              NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
              NtisRoutingConst.CONTRACTS,
              NtisRoutingConst.CONTRACTS_LIST,
            ]);
            this.sprErrorHandler.handleError(error as Error);
            return EMPTY;
          })
        )
      : this.ntisContractsService.loadContractInfo(route.paramMap.get(UrlSegmentsConst.ID) as unknown as number).pipe(
          catchError((error) => {
            void this.commonFormServices.router.navigate([
              RoutingConst.INTERNAL,
              NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
              NtisRoutingConst.CONTRACTS,
              NtisRoutingConst.CONTRACTS_LIST,
            ]);
            this.sprErrorHandler.handleError(error as Error);
            return EMPTY;
          })
        );
  }
}
