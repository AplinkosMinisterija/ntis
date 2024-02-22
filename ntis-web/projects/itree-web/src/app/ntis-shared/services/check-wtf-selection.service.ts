import { Injectable } from '@angular/core';
import { NtisCheckWtfSelectionRequest } from '@itree-commons/src/lib/model/api/api';
import { Subject } from 'rxjs';

export type NtisCheckWtfSelectionParams = {
  request: NtisCheckWtfSelectionRequest;
  navigateUrl: string;
};

@Injectable({
  providedIn: 'root',
})
export class CheckWtfSelectionService {
  check$ = new Subject<NtisCheckWtfSelectionParams>();
  lastCheckParams: NtisCheckWtfSelectionParams;

  check(request: NtisCheckWtfSelectionRequest, navigateUrl: string): void {
    const params = { request, navigateUrl };
    this.check$.next(params);
    this.lastCheckParams = params;
  }
}
