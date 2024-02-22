import { Component } from '@angular/core';

@Component({
  selector: 'spr-spinner',
  templateUrl: './spinner.component.html',
  styleUrls: ['./spinner.component.scss'],
})
export class SpinnerComponent {
  divList = new Array(12) as Array<number>;
}
