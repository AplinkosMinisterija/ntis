import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { FaqThemesBrowseRow } from '../../../models/browse-page';

@Component({
  selector: 'ntis-faq-theme',
  templateUrl: './faq-theme.component.html',
  styleUrls: ['./faq-theme.component.scss'],
})
export class FaqThemeComponent {
  @Input() specificTheme: FaqThemesBrowseRow;

  constructor(public faIconsService: FaIconsService, public router: Router) {}

  navigateToQuestion(code: string): void {
    void this.router.navigate([this.router.url, code]);
  }
}
