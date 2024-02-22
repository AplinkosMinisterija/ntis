import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { EMPTY, Observable, catchError } from 'rxjs';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { UrlSegmentsConst } from '@itree-commons/src/constants/urlSegments.const';
import { NtisContractsService } from '../services/ntis-contracts.service';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { NtisContractEditModel, NtisContractUploadRequest } from '@itree-commons/src/lib/model/api/api';
import { SprErrorHandler } from '@itree-commons/src/lib/errors/error-handler';

@Injectable({
  providedIn: 'root',
})
export class ContractUploadResolver {
  constructor(
    private ntisContractsService: NtisContractsService,
    protected commonFormServices: CommonFormServices,
    private sprErrorHandler: SprErrorHandler
  ) {}
  resolve(route: ActivatedRouteSnapshot): Observable<NtisContractEditModel> {
    const backToList = `${RoutingConst.INTERNAL}/${NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION}/${NtisRoutingConst.CONTRACTS}/${NtisRoutingConst.CONTRACTS_LIST}`;
    if (route.paramMap.get('mode') === RoutingConst.EDIT) {
      const request = {} as NtisContractUploadRequest;
      request.contractId = route.paramMap.get(UrlSegmentsConst.ID) as unknown as number;
      request.spOrgId = route.queryParamMap.get('sp') as unknown as number;
      if (route.queryParamMap.get('wtfId')) {
        request.wtfId = route.queryParamMap.get('wtfId') as unknown as number;
      }
      request.filKey = route.queryParamMap.get('filKey');
      return this.ntisContractsService.loadTempContract(request).pipe(
        catchError((error) => {
          void this.commonFormServices.router.navigate([backToList]);
          this.sprErrorHandler.handleError(error as Error);
          return EMPTY;
        })
      );
    } else {
      if (route.queryParamMap.get('cot')) {
        return this.ntisContractsService.loadUploadedContract(route.queryParamMap.get('cot') as unknown as number).pipe(
          catchError((error) => {
            void this.commonFormServices.router.navigate([backToList]);
            this.sprErrorHandler.handleError(error as Error);
            return EMPTY;
          })
        );
      } else {
        return this.ntisContractsService.loadUploadedContractByToken(route.queryParamMap.get('token')).pipe(
          catchError((error) => {
            void this.commonFormServices.router.navigate([backToList]);
            this.sprErrorHandler.handleError(error as Error);
            return EMPTY;
          })
        );
      }
    }
  }
}
