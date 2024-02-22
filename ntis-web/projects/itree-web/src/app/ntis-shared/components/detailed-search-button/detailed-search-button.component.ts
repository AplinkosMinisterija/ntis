import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'ntis-detailed-search-button',
  templateUrl: './detailed-search-button.component.html',
  styleUrls: ['./detailed-search-button.component.scss'],
})
export class DetailedSearchButtonComponent {
  @Input() text: string;
  @Output() showContentEmit = new EventEmitter<boolean>();
  showCont: boolean = false;

  showContent(): void {
    this.showContentEmit.emit((this.showCont = !this.showCont));
  }
}
