import { Component, OnInit } from '@angular/core';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { AppDataService } from '../../services/app-data.service';
import { MenuStructure } from '../../model/browse-pages';

@Component({
  selector: 'spr-public',
  templateUrl: './public.component.html',
  styleUrls: ['./public.component.scss'],
})
export class PublicComponent implements OnInit {
  readonly RoutingConst = RoutingConst;
  currentYear: number = new Date().getFullYear();
  menuItems: MenuStructure[] = [];
  multilanguageExists: boolean = true;

  constructor(private authService: AuthService, private appDataService: AppDataService) {}

  ngOnInit(): void {
    this.authService.loadAndGetPublicMenu().subscribe((menu) => {
      this.menuItems = menu || [];
    });
    this.multilanguageExists = this.appDataService.multilanguageExists();

    this.authService.loadedMenu$.subscribe((loadedMenu) => {
      this.menuItems = loadedMenu;
    });
  }
}
