import { Location } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FaIconsService } from '../../services/fa-icons.service';

@Component({
  selector: 'spr-button-back',
  templateUrl: './button-back.component.html',
  styleUrls: ['./button-back.component.scss'],
})
export class ButtonBackComponent {
  @Input() disableLocationBack = false;
  @Output() navigateBack: EventEmitter<void> = new EventEmitter<void>();

  constructor(private location: Location, public faIconsService: FaIconsService) {}

  onNavigate(): void {
    if (!this.disableLocationBack) {
      this.location.back();
    }
    this.navigateBack.emit();
  }
}
