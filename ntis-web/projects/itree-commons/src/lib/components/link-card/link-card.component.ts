import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { FaIconsService } from '../../services/fa-icons.service';

@Component({
  selector: 'spr-link-card',
  templateUrl: './link-card.component.html',
  styleUrls: ['./link-card.component.scss'],
})
export class LinkCardComponent {
  @Input() iconName: string;
  @Input() title: string;
  @Input() description: string;
  @Input() link: string;

  constructor(public faIconsService: FaIconsService, private router: Router) {}

  onClickButton(): void {
    if (this.link.startsWith('http')) {
      document.defaultView.open(this.link, '_blank');
    } else {
      void this.router.navigate([this.link]);
    }
  }
}
