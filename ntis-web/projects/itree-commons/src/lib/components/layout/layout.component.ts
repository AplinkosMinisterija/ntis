import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { MENU_DEFAULT_ICON_NAME } from '@itree-commons/src/constants/menu.const';
import { MenuItemType } from '../../enums/menu.enums';
import { FaIconsService } from '../../services/fa-icons.service';
import { Subject, filter } from 'rxjs';
import { MenuStructure } from '../../model/browse-pages';
import { AppDataService } from '../../services/app-data.service';

@Component({
  selector: 'spr-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss'],
})
export class LayoutComponent implements OnChanges {
  readonly MenuItemType = MenuItemType;
  readonly mobileBreakpointInPx: number = 640;
  readonly categoryItemMaxHeightInRem = 10;
  readonly MENU_DEFAULT_ICON_NAME = MENU_DEFAULT_ICON_NAME;
  readonly destroy$ = new Subject<void>();

  isMenuVisible: boolean = true;
  selectedMenuItem: number = -1;
  showTestHeader: boolean = false;

  @Input() menuItems: MenuStructure[] = [];
  @Input() logoRouterLink: string | string[] = [''];

  constructor(public faIconsService: FaIconsService, private router: Router, private appDataService: AppDataService) {
    this.showTestHeader = this.appDataService.getAppData()?.springProfilesActive === 'test';
    if (this.showTestHeader) {
      document.title = '(TEST) Nuotekų Tvarkymo Informacinė Sistema';
    }
    this.router.events.pipe(filter((event) => event instanceof NavigationEnd)).subscribe(() => {
      document.getElementById('content-layout').scrollTop = 0;

      let activeItem = this.menuItems.find(
        (menuItem) =>
          menuItem.type === MenuItemType.Category &&
          menuItem.rows.find(
            (row) =>
              row?.link &&
              this.router.isActive(row.link, {
                matrixParams: 'ignored',
                queryParams: 'ignored',
                paths: 'subset',
                fragment: 'ignored',
              })
          )
      );
      if (!activeItem) {
        activeItem = this.menuItems.find(
          (menuItem) =>
            menuItem.type === MenuItemType.Link &&
            menuItem.link &&
            this.router.isActive(menuItem.link, {
              matrixParams: 'ignored',
              queryParams: 'ignored',
              paths: 'subset',
              fragment: 'ignored',
            })
        );
      }
      if (activeItem) {
        this.selectedMenuItem = this.menuItems.findIndex((menuItem) => menuItem === activeItem);
      }
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.menuItems && this.menuItems.length > 0) {
      const tempArray: MenuStructure[] = [];
      this.menuItems.forEach((row) => {
        const additionalDetailsJson: MenuStructure = (JSON.parse(row?.name) as MenuStructure[])[0];
        const internalRowsArr: MenuStructure[] = [];
        if (row.rows) {
          row.rows.forEach((subRow) => {
            const internalRowsJson: MenuStructure = (JSON.parse(subRow.name) as MenuStructure[])[0];
            const internalRowMapped: MenuStructure = {
              iconName: subRow.iconName,
              level: subRow.level,
              link: subRow.link,
              name: internalRowsJson.title,
              row: subRow.row,
              rows: subRow.rows,
              type: subRow.type,
              toolTip: internalRowsJson.toolTip,
              title: internalRowsJson.title,
              code: internalRowsJson.code,
            };
            internalRowsArr.push(internalRowMapped);
          });
        }

        const menuItemMapped: MenuStructure = {
          iconName: row.iconName,
          level: row.level,
          link: row.link,
          name: additionalDetailsJson.title,
          row: row.row,
          rows: internalRowsArr,
          type: row.type,
          toolTip: additionalDetailsJson.toolTip,
          title: additionalDetailsJson.title,
          code: additionalDetailsJson.code,
        };
        row = menuItemMapped;
        tempArray.push(menuItemMapped);
      });
      this.menuItems = tempArray;
      const activeItem = this.menuItems.find(
        (menuItem) =>
          menuItem.type === MenuItemType.Category &&
          menuItem.rows.find(
            (row) =>
              row?.link &&
              this.router.isActive(row.link, {
                matrixParams: 'ignored',
                queryParams: 'ignored',
                paths: 'subset',
                fragment: 'ignored',
              })
          )
      );
      if (activeItem) {
        this.selectedMenuItem = this.menuItems.findIndex((menuItem) => menuItem === activeItem);
      }
    }
  }

  toggleNav(item: number = -1): void {
    if (item === -1) {
      this.isMenuVisible = !this.isMenuVisible;
    } else {
      this.selectedMenuItem = item === this.selectedMenuItem ? (this.isMenuVisible ? -1 : item) : item;
      if (!this.isMenuVisible && this.menuItems[item].type !== MenuItemType.Link) {
        this.isMenuVisible = true;
      } else if (this.menuItems[item].type === MenuItemType.Link) {
        this.hideNavIfMobile();
      }
    }
  }

  hideNavIfMobile(): void {
    if (window.innerWidth < this.mobileBreakpointInPx) {
      this.isMenuVisible = false;
    }
  }
}
