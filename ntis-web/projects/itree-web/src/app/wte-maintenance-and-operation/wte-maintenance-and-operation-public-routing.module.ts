import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NtisRoutingConst } from '../ntis-shared/constants/ntis-routing.const';

const routes: Routes = [
  {
    path: NtisRoutingConst.CONTRACTS,
    loadChildren: () =>
      import('./contracts/contracts-public-routing.module').then((m) => m.ContractsPublicRoutingModule),
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class WteMaintenanceAndOperationPublicRoutingModule {}
