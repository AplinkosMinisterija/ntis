import { Component, ElementRef, HostListener, Input, OnInit } from '@angular/core';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { WtfServedObject } from '../../../ntis-building-data-module/models/browse-page';
import { NtisRoutingConst } from '../../constants/ntis-routing.const';

@Component({
  selector: 'ntis-view-served-objects',
  templateUrl: './view-served-objects.component.html',
  styleUrls: ['./view-served-objects.component.scss'],
})
export class ViewServedObjectsComponent implements OnInit {
  readonly formTranslationsReference = 'ntisBuildingData.components.wastewaterFacilityObjectList';
  @Input() servedObjects: string;
  @Input() rowTitle: string;
  @Input() iconSize: string;
  @Input() restrictView: boolean = false;
  parsedServedObjects: WtfServedObject[] = [];
  showDialog: boolean = false;

  constructor(
    public faIconsService: FaIconsService,
    private elementRef: ElementRef<Element>,
    protected commonFormServices: CommonFormServices
  ) {}

  ngOnInit(): void {
    if (this.servedObjects) {
      this.parsedServedObjects = JSON.parse(this.servedObjects) as WtfServedObject[];
    }
  }

  openServedObject(row: WtfServedObject): void {
    if ((this.restrictView && !!row.fo_so_id) || !this.restrictView)
      if (row.so_id) {
        this.showDialog = false;
        void this.commonFormServices.router.navigate([
          RoutingConst.INTERNAL,
          NtisRoutingConst.BUILDING_DATA,
          NtisRoutingConst.BUILDING_DATA_WASTEWATER_FACILITY,
          NtisRoutingConst.NTR_BUILDING,
          RoutingConst.VIEW,
          row.so_id,
        ]);
      }
  }

  @HostListener('document:keydown.Escape', ['$event'])
  onKeydownHandler(): void {
    this.showDialog = false;
  }
}
