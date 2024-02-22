import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NtisRoutingConst } from '../ntis-shared/constants/ntis-routing.const';

const routes: Routes = [
  {
    path: NtisRoutingConst.CONTRACTS,
    loadChildren: () =>
      import('./contracts/contracts-internal-routing.module').then((m) => m.ContractsInternalRoutingModule),
  },
  {
    path: NtisRoutingConst.TECH_SUPPORT,
    loadChildren: () =>
      import('./tech-support/tech-support-internal-routing.module').then((m) => m.TechSupportInternalRoutingModule),
  },
  {
    path: NtisRoutingConst.SLUDGE_TREATMENT,
    loadChildren: () =>
      import('./tech-support/sludge-treatment-internal-routing.module').then(
        (m) => m.SludgeTreatmentInternalRoutingModule
      ),
  },
  {
    path: NtisRoutingConst.RESEARCH,
    loadChildren: () =>
      import('./research/research-internal-routing.module').then((m) => m.ResearchInternalRoutingModule),
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class WteMaintenanceAndOperationInternalRoutingModule {}
