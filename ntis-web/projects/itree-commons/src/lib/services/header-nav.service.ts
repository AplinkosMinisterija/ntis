import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { HeaderNav } from '../components/header-nav/header-nav.component';

@Injectable({
  providedIn: 'root',
})
export class HeaderNavService {
  private headerNavSubject = new BehaviorSubject<HeaderNav | null>(null);
  private menuLinkSubject = new BehaviorSubject<HeaderNav | null>(null);
  headerNav$ = this.headerNavSubject.asObservable();
  menuLink$ = this.menuLinkSubject.asObservable();

  sendHeaderNav(headerNav: HeaderNav): void {
    this.headerNavSubject.next(headerNav);
  }

  sendMenuLink(menuLink: HeaderNav): void {
    this.menuLinkSubject.next(menuLink);
  }
}
