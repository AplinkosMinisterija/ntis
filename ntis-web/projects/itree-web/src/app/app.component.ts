import { AccessibilityCssClass } from '@itree-commons/src/lib/types/accessibility';
import { AccessibilityService } from '@itree-commons/src/lib/services/accessibility.service';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { AppMessages, BaseAppComponent, CommonFormServices, getLang } from '@itree/ngx-s2-commons';
import { BreadcrumbsService } from '@itree-commons/src/lib/services/breadcrumbs.service';
import { AfterViewChecked, Component, NgZone, OnInit, Renderer2 } from '@angular/core';
import { filter } from 'rxjs';
import { MessageService, PrimeNGConfig, Translation } from 'primeng/api';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { SprBackendWebSessionInfo } from '@itree-commons/src/lib/model/api/api';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { TranslateService } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent extends BaseAppComponent<SprBackendWebSessionInfo> implements OnInit, AfterViewChecked {
  styles: AccessibilityCssClass[];
  currentUrl: string = null;
  previousUrl: string = null;

  constructor(
    protected override router: Router,
    protected override appMessages: AppMessages,
    protected override translate: TranslateService,
    protected override route: ActivatedRoute,
    protected override ngZone: NgZone,
    private primeConfig: PrimeNGConfig,
    public accessibilityService: AccessibilityService,
    public breadcrumbsService: BreadcrumbsService,
    private messageService: MessageService,
    private renderer: Renderer2,
    private http: HttpClient,
    private commonFormServices: CommonFormServices
  ) {
    super(router, appMessages, translate, route, ngZone);

    this.appMessages.add$.pipe(takeUntilDestroyed()).subscribe((message) => {
      this.messageService.add(message);
    });

    this.appMessages.clear$.pipe(takeUntilDestroyed()).subscribe(() => {
      this.messageService.clear();
    });
  }

  ngAfterViewChecked(): void {
    this.fixPaginatorLabels();
  }

  override ngOnInit(): void {
    super.ngOnInit();
    this.setPrimeJson();
    this.router.events
      .pipe(filter((event): event is NavigationEnd => event instanceof NavigationEnd))
      .subscribe((event: NavigationEnd) => {
        this.previousUrl = this.currentUrl;
        this.currentUrl = event.url;
      });

    window.addEventListener('popstate', () => {
      if (this.currentUrl.includes('edit') && typeof this.previousUrl === 'string') {
        window.history.pushState({ prevUrl: this.previousUrl }, this.previousUrl);
      }
    });
  }

  protected getLoginUrl(): string {
    return RoutingConst.LOGIN;
  }

  setPrimeJson(): void {
    this.http.get(`assets/i18n/${getLang()}-primeng.json`, { responseType: 'text' as 'json' }).subscribe((data) => {
      const translation = JSON.parse(data as string) as Translation;
      this.primeConfig.setTranslation(translation);
    });
  }

  private fixPaginatorLabels(): void {
    document.querySelectorAll('.p-paginator-page').forEach((btn) => {
      const pageNumber = btn.textContent?.trim();
      if (pageNumber) {
        if (btn.classList.contains('p-highlight')) {
          btn.setAttribute('aria-current', 'page');
        } else {
          btn.removeAttribute('aria-current');
        }

        if (!btn.hasAttribute('aria-label')) {
          this.commonFormServices.translate.get('common.generalUse.page').subscribe((translate: string) => {
            btn.setAttribute('aria-label', `${translate} ${pageNumber}`);
          });
        }
      }
    });

    document.querySelectorAll('.p-paginator-prev').forEach((btn) => {
      if (!btn.hasAttribute('aria-label')) {
        this.commonFormServices.translate.get('common.generalUse.previousPage').subscribe((translate: string) => {
          btn.setAttribute('aria-label', translate);
        });
      }
    });
  }

  insertScriptIntoHeader(): void {
    // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment
    const script = this.renderer.createElement('script');
    // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access
    script.type = 'text/javascript';
    // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access
    script.src = 'path/to/your/script.js';

    // You can add other attributes to the script tag as needed, such as 'async', 'defer', etc.

    this.renderer.appendChild(document.head, script);
  }
}
