import {
  ChangeDetectorRef,
  Component,
  ElementRef,
  HostListener,
  Inject,
  OnDestroy,
  OnInit,
  ViewChild,
  forwardRef,
} from '@angular/core';
import { Router } from '@angular/router';
import { IconDefinition } from '@fortawesome/free-regular-svg-icons';
import { DB_BOOLEAN_FALSE, DB_BOOLEAN_TRUE } from '@itree-commons/src/constants/db.const';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { AuthEvent, AuthUtil } from '@itree/ngx-s2-commons';
import { fadeInFields, slideToLeft } from '../../animations/animations';
import { NotificationTypes } from '../../enums/notification-types.enums';
import { SprNotificationsDAO } from '../../model/api/api';
import { WebsocketService } from '../../services/websocket.service';
import { CommonService } from '../../services/common.service';
import { FaIconsService } from '../../services/fa-icons.service';
import { Subject, takeUntil } from 'rxjs';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { LayoutComponent } from '../layout/layout.component';

export interface TabElements {
  tabValue: NotificationTypes;
}

@Component({
  selector: 'spr-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.scss'],
  animations: [slideToLeft, fadeInFields],
})
export class NotificationsComponent implements OnInit, OnDestroy {
  readonly DB_BOOLEAN_FALSE = DB_BOOLEAN_FALSE;
  readonly DB_BOOLEAN_TRUE = DB_BOOLEAN_TRUE;
  readonly notificationTypes = NotificationTypes;
  readonly translationsReference = 'components.notification.';

  audioUrl: string = '../../../../assets/audio/notification.mp3';
  messageViewData: SprNotificationsDAO;
  messagesList: SprNotificationsDAO[] = [];
  notification: SprNotificationsDAO = null;
  showMessageView: boolean = false;
  showMessages: boolean = false;
  tabList: TabElements[] = [];
  tabValue: string = this.notificationTypes.all;
  tmpMessagesList: SprNotificationsDAO[] = [];
  destroy$: Subject<boolean> = new Subject();
  callAuthUtil: boolean = true;
  ntfId: number;
  @ViewChild('audioElement') audioElement: ElementRef<HTMLAudioElement>;

  constructor(
    public faIconsService: FaIconsService,
    public websocketService: WebsocketService,
    private commonService: CommonService,
    private elementRef: ElementRef<Element>,
    private router: Router,
    private cdr: ChangeDetectorRef,
    @Inject(forwardRef(() => LayoutComponent)) public layoutComponent: LayoutComponent
  ) {}

  ngOnDestroy(): void {
    this.destroy$.next(true);
    this.destroy$.unsubscribe();
  }

  ngOnInit(): void {
    this.tabList = [
      { tabValue: this.notificationTypes.all },
      { tabValue: this.notificationTypes.message },
      { tabValue: this.notificationTypes.alert },
      { tabValue: this.notificationTypes.task },
    ];

    if (AuthUtil.isLoggedIn()) {
      this.websocketService.stompConnect();
      this.websocketService.getMessages().subscribe((response: unknown) => {
        this.notification = JSON.parse(response as string) as SprNotificationsDAO;
        this.hideNotification();
        this.getMessages();
        void this.audioElement?.nativeElement.play();
      });
    } else {
      this.websocketService.stompDisconnect();
    }

    AuthEvent.userLoggedIn.pipe(takeUntil(this.destroy$)).subscribe((item) => {
      if (item.session) {
        if (item.session.orgId !== null || item.session.roleId !== null) {
          this.callAuthUtil = false;
          this.getMessages();
        }
      }
    });

    if (this.callAuthUtil) {
      if (AuthUtil.getSessionInfo().orgId !== null || AuthUtil.getSessionInfo().roleId !== null) {
        this.getMessages();
      }
    }
  }

  navigateToBrowse(): void {
    this.layoutComponent.selectedMenuItem = -1;
    this.showMessages = false;
    void this.router.navigate([RoutingConst.INTERNAL, NtisRoutingConst.NOTIFICATIONS_LIST]);
  }

  filterMessages(value: string): void {
    this.tmpMessagesList = this.messagesList;

    if (value === this.notificationTypes.all) {
      this.tmpMessagesList = this.messagesList;
    } else {
      this.tmpMessagesList = this.messagesList.filter((item) => item.ntf_type.toLowerCase() === value);
    }
    this.showMessageView = false;
    this.ntfId = null;
    this.tabValue = value;
  }

  getMessages(): void {
    this.commonService.getNotification().subscribe((response) => {
      this.messagesList = response.data;
      this.tmpMessagesList = response.data;
      this.cdr.detectChanges();
    });
  }

  countUnreadMessages(): number {
    return this.messagesList.filter((item) => !item.ntf_mark_as_read_date).length;
  }

  markAsRead(id: number): void {
    this.commonService.markAsRead(id).subscribe(() => {
      this.showMessageView = true;
      this.ntfId = id;
      this.findMessage(id);
      this.getMessages();
      this.cdr.detectChanges();
    });
  }

  getIcon(value: string): IconDefinition {
    switch (value) {
      case this.notificationTypes.alert:
        return this.faIconsService.far.faBell;
      case this.notificationTypes.message:
        return this.faIconsService.far.faMessage;
      case this.notificationTypes.task:
        return this.faIconsService.fas.faListCheck;
    }
    return null;
  }
  findMessage(id: number): void {
    this.messageViewData = this.messagesList.find((item) => item.ntf_id === id);
  }

  hideNotification(): void {
    setTimeout(() => {
      this.notification = null;
    }, 7000);
  }

  @HostListener('document:click', ['$event.target'])
  onPageClick(targetElement: Element): void {
    if (!this.elementRef.nativeElement.contains(targetElement) && targetElement.className !== 'button-back') {
      this.showMessages = false;
    }
  }

  navigateToViewMessage(): void {
    this.showMessages = false;
    this.showMessageView = false;
    void this.router.navigate(
      [RoutingConst.INTERNAL, NtisRoutingConst.NOTIFICATIONS_LIST, RoutingConst.VIEW, this.ntfId],
      { skipLocationChange: false }
    );
  }
}
