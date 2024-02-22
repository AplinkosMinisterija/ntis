import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { CommonFormServices, getLang } from '@itree/ngx-s2-commons';
import { Component, ElementRef, HostListener } from '@angular/core';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { AppDataService } from '../../services/app-data.service';

interface Language {
  name: string;
  code: string;
}

@Component({
  selector: 'spr-language-switch',
  templateUrl: './language-switch.component.html',
  styleUrls: ['./language-switch.component.scss'],
})
export class LanguageSwitchComponent {
  isLanguageSwitchOpen: boolean = false;
  selectedIndex: number;
  languages: Language[] = [];

  constructor(
    public commonFormServices: CommonFormServices,
    private authService: AuthService,
    private elementRef: ElementRef<Element>,
    private appData: AppDataService,
    public faIconsService: FaIconsService
  ) {
    this.languages = this.appData.getLanguages().map((language, index) => {
      if (language.id === getLang()) {
        this.selectedIndex = index;
      }
      return { name: language.value, code: language.id };
    });
    if (this.languages.length === 1 && this.selectedIndex !== 0) {
      this.selectedIndex = 0;
      this.authService.changeLanguage(this.commonFormServices.translate, this.languages[this.selectedIndex].code);
    }
  }

  toggleLanguageSwitch(state: boolean): void {
    this.isLanguageSwitchOpen = state;
  }

  @HostListener('document:click', ['$event.target'])
  onPageClick(targetElement: HTMLElement): void {
    if (!this.elementRef.nativeElement.contains(targetElement)) {
      this.toggleLanguageSwitch(false);
    }
  }

  onLanguageChange(code: Language['code'], index: number): void {
    if (getLang() !== code) {
      this.selectedIndex = index;
      this.authService.changeLanguage(this.commonFormServices.translate, code);
    }
    this.toggleLanguageSwitch(false);
  }
}
