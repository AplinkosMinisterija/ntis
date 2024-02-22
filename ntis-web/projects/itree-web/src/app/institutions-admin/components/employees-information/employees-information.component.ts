import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';

@Component({
  selector: 'app-employees-information',
  templateUrl: './employees-information.component.html',
  styleUrls: ['./employees-information.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, RouterModule],
})
export class EmployeesInformationComponent {
  readonly translationsReference = 'institutionsAdmin.components.employeesInformation';
  readonly RoutingConst = RoutingConst;
  @Input() employeesCount = 0;
}
