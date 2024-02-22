import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'spr-divider',
  templateUrl: './divider.component.html',
  styleUrls: ['./divider.component.scss'],
})
export class DividerComponent implements OnInit {
  @Input() marginY: '2' | '4' | '6' | '8' = '2';
  @Input() hideBorder: boolean = false;

  marginClass: string = null;

  ngOnInit(): void {
    this.marginClass = `my-${this.marginY}`;
  }
}
