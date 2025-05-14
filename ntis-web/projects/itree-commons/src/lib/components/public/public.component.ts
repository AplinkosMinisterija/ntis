import { Component, OnInit } from '@angular/core';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { AppDataService } from '../../services/app-data.service';
import { MenuStructure } from '../../model/browse-pages';
import { getLang } from '@itree/ngx-s2-commons';

@Component({
  selector: 'spr-public',
  templateUrl: './public.component.html',
  styleUrls: ['./public.component.scss'],
})
export class PublicComponent implements OnInit {
  readonly RoutingConst = RoutingConst;
  menuItems: MenuStructure[] = [];
  multilanguageExists: boolean = true;
  footerLine1: string = '';
  footerLine2: string = '';

  constructor(private authService: AuthService, private appDataService: AppDataService) {}

  ngOnInit(): void {
    this.authService.loadAndGetPublicMenu().subscribe((menu) => {
      this.menuItems = menu || [];
    });
    this.multilanguageExists = this.appDataService.multilanguageExists();

    this.authService.loadedMenu$.subscribe((loadedMenu) => {
      this.menuItems = loadedMenu;
    });

    if (getLang() === 'lt') {
      this.footerLine1 = this.appDataService.getPropertyValue('FOOTER_VAR_LT_1');
      this.footerLine2 = this.appDataService.getPropertyValue('FOOTER_VAR_LT_2');
    } else {
      this.footerLine1 = this.appDataService.getPropertyValue('FOOTER_VAR_EN_1');
      this.footerLine2 = this.appDataService.getPropertyValue('FOOTER_VAR_EN_2');
    }
  }
}
