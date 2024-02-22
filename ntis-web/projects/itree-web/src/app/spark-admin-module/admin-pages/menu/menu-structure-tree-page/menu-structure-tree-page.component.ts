import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { CommonFormServices, getLang } from '@itree/ngx-s2-commons';
import { ConfirmationService, MessageService, TreeDragDropService, TreeNode } from 'primeng/api';
import { MenuStructureRequest } from '@itree-commons/src/lib/model/api/api';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { AdminService } from '../../../admin-services/admin.service';
import { DB_BOOLEAN_FALSE } from '@itree-commons/src/constants/db.const';
import { ClsfSprLanguage, ClsfSprSiteType } from '@itree-commons/src/lib/enums/classifiers.enums';
import { SprMenuStructureTreeItem } from '../../../models/edit-pages';

@Component({
  selector: 'app-menu-structure-tree-page',
  templateUrl: './menu-structure-tree-page.component.html',
  styleUrls: ['./menu-structure-tree-page.component.scss'],
  providers: [TreeDragDropService, MessageService],
})
export class MenuStructureTreePageComponent implements OnInit {
  menuPages: TreeNode[];
  unassignedPages: TreeNode[];
  searchForm = new FormGroup({
    mst_lang: new FormControl(null),
    mst_site: new FormControl(null),
    mst_is_public: new FormControl(null),
  });

  constructor(
    protected commonFormServices: CommonFormServices,
    private confirmationService: ConfirmationService,
    private adminService: AdminService,
    public faIconsService: FaIconsService
  ) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    const language = getLang();
    const site = (this.searchForm.controls.mst_site.value as string) || ClsfSprSiteType.Main;
    const isPublic = (this.searchForm.controls.mst_is_public.value as string) || DB_BOOLEAN_FALSE;

    this.adminService.getMenuTreeStructureList(language, site, isPublic).subscribe((result) => {
      this.menuPages = this.getMenuStructureTree(result.data);
    });

    this.adminService.getUnassignedMenuTreeStructureList(language, site, isPublic).subscribe((result) => {
      this.unassignedPages = this.getMenuStructureTree(result.data);
    });
  }

  private getMenuStructureTree(data: SprMenuStructureTreeItem[]): TreeNode[] {
    const getMenuTreeChildren = (parentId: number): TreeNode[] =>
      data
        .filter((menuItem) => menuItem.mst_mst_id === parentId)
        .map((menuItem) => ({
          label: menuItem.title,
          key: `${menuItem.mst_id}`,
          children: getMenuTreeChildren(menuItem.mst_id),
        }));
    return getMenuTreeChildren(null);
  }

  private getMenuStructureRequests(treeItems: TreeNode[]): MenuStructureRequest[] {
    const getMenuStructureRequestsOfParent = (children: TreeNode[], parentId: number): MenuStructureRequest[] => {
      return children.reduce((accumulator: MenuStructureRequest[], treeItem, index): MenuStructureRequest[] => {
        const menuStructureRequest = { mst_id: parseInt(treeItem.key), mst_mst_id: parentId, mst_order: index };
        if (treeItem.children?.length) {
          return [
            ...accumulator,
            menuStructureRequest,
            ...getMenuStructureRequestsOfParent(treeItem.children, parseInt(treeItem.key)),
          ];
        }
        return [...accumulator, menuStructureRequest];
      }, []);
    };
    return getMenuStructureRequestsOfParent(treeItems, null);
  }

  saveWithConfirmation(): void {
    this.commonFormServices.translate.get('common.message.saveConfirmationMsg').subscribe((translation: string) => {
      this.confirmationService.confirm({
        message: translation,
        accept: () => {
          this.save();
        },
      });
    });
  }

  save(): void {
    const menuRequests = this.getMenuStructureRequests(this.menuPages);
    const unassignedRequests: number[] = this.getMenuStructureRequests(this.unassignedPages).map((item) => item.mst_id);
    if (unassignedRequests.length > 0) {
      this.adminService.setUnassignedPages(unassignedRequests).subscribe();
    }
    this.adminService.setMenuTreeData(menuRequests).subscribe(() => {
      this.commonFormServices.translate.get('common.message.saveSuccess').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showSuccess('', translation);
      });
    });
  }
}
