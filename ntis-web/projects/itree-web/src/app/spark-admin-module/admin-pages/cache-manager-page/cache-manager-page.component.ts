import { ActivatedRoute } from '@angular/router';
import { AdminService } from '../../admin-services/admin.service';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { CacheInfo } from '@itree-commons/src/lib/model/api/api';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { Component } from '@angular/core';
import { ConfirmationService } from 'primeng/api';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { SPR_CACHE_MANAGER } from '@itree-commons/src/constants/forms.constants';

interface CacheInfoView extends CacheInfo {
  hitsWithPercentage: string;
  missesWithPercentage: string;
}

@Component({
  selector: 'app-cache-manager-page',
  templateUrl: './cache-manager-page.component.html',
  styleUrls: ['./cache-manager-page.component.scss'],
})
export class CacheManagerPageComponent {
  readonly translationsRef = 'pages.sprCacheManager';
  cacheList: CacheInfoView[];
  clearAllowed = false;
  cols = ['name', 'puts', 'removals', 'expirations', 'gets', 'hitsWithPercentage', 'missesWithPercentage'];

  constructor(
    private adminService: AdminService,
    private confirmationService: ConfirmationService,
    protected commonFormServices: CommonFormServices,
    private authService: AuthService,
    activatedRoute: ActivatedRoute
  ) {
    this.cacheList = this.cacheInfoToCacheInfoView(activatedRoute.snapshot.data[RoutingConst.DATA] as CacheInfo[]);
    this.clearAllowed = this.authService.isFormActionEnabled(SPR_CACHE_MANAGER, 'CLEAR_CACHE');
  }

  clearCache(name: string): void {
    this.adminService.clearCache(name).subscribe((result) => {
      this.cacheList = this.cacheInfoToCacheInfoView(result);
      this.commonFormServices.translate
        .get(`${this.translationsRef}.clearSuccessMessage`, { name })
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showSuccess('', translation);
        });
    });
  }

  clearAllCache(): void {
    this.adminService.clearAllCaches().subscribe((result) => {
      this.cacheList = this.cacheInfoToCacheInfoView(result);
      this.commonFormServices.translate
        .get(`${this.translationsRef}.clearAllSuccessMessage`)
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showSuccess('', translation);
        });
    });
  }

  handleClickClearCache(name: string): void {
    this.commonFormServices.translate
      .get([`${this.translationsRef}.clearDialogHeader`, `${this.translationsRef}.clearDialogMessage`], { name })
      .subscribe((translations: Record<string, string>) => {
        this.confirmationService.confirm({
          header: translations[`${this.translationsRef}.clearDialogHeader`],
          message: translations[`${this.translationsRef}.clearDialogMessage`],
          accept: () => {
            this.clearCache(name);
          },
        });
      });
  }

  handleClickClearAllCache(): void {
    this.commonFormServices.translate.get(this.translationsRef).subscribe((translations: Record<string, string>) => {
      this.confirmationService.confirm({
        header: translations['clearAllDialogHeader'],
        message: translations['clearAllDialogMessage'],
        accept: () => {
          this.clearAllCache();
        },
      });
    });
  }

  cacheInfoToCacheInfoView(data: CacheInfo[]): CacheInfoView[] {
    return data.map((cache) => ({
      ...cache,
      hitsWithPercentage: `${cache.hits} (${cache.hitPercentage} %)`,
      missesWithPercentage: `${cache.misses} (${cache.missPercentage} %)`,
    }));
  }
}
