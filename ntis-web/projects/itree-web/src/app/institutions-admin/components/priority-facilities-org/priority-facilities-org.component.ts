import { CommonModule } from '@angular/common';
import { Component, ElementRef, HostListener, Input } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { WaterManagerBrowseRow } from '../../models/browse-pages';

@Component({
  selector: 'app-priority-facilities-org',
  templateUrl: './priority-facilities-org.component.html',
  styleUrls: ['./priority-facilities-org.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, NtisSharedModule, FontAwesomeModule],
})
export class PriorityFacilitiesOrgComponent {
  @Input() data: WaterManagerBrowseRow;
  displayModal: boolean = false;

  constructor(public faIconsService: FaIconsService, private elementRef: ElementRef<Element>) {}

  @HostListener('document:click', ['$event.target'])
  onPageClick(targetElement: HTMLElement): void {
    if (!this.elementRef.nativeElement.contains(targetElement)) {
      this.displayModal = false;
    }
  }

  @HostListener('document:keydown.Escape', ['$event'])
  onKeydownHandler(): void {
    this.displayModal = false;
  }
}
