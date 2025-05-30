import { Injectable } from '@angular/core';
import {
  NtisSystemWorksDAO,
  SprBackendWebSessionInfo,
  SprNotificationsDAO,
} from '@itree-commons/src/lib/model/api/api';
import { CompatClient, IMessage, Stomp } from '@stomp/stompjs';
import { REST_API_BASE_URL } from '@itree-commons/src/constants/rest.constants';
import { Subject } from 'rxjs';
import SockJS from 'sockjs-client';
import { AuthUtil } from '@itree/ngx-s2-commons';

@Injectable({
  providedIn: 'root',
})
export class WebsocketService {
  private stompClient: CompatClient = null;
  messageItem: Subject<SprNotificationsDAO> = new Subject();
  messageItem$ = this.messageItem.asObservable();

  sysWorksItem: Subject<NtisSystemWorksDAO> = new Subject();
  sysWorksItem$ = this.sysWorksItem.asObservable();

  constructor() {
    this.stompClient = Stomp.over(() => {
      return new SockJS(document.location.origin + REST_API_BASE_URL + '/spr-stomp-endpoint');
    });

    this.stompClient.reconnectDelay = 3000;

    this.stompClient.debug = (): void => {
      console.log;
    };
  }

  stompConnect(): void {
    this.stompClient.connect({}, (): void => {
      this.getMessages();
      this.getSysWorksMessage();
    });
  }

  stompConnectSysWorksMessages(): void {
    this.stompClient.connect({}, (): void => {
      this.getSysWorksMessage();
    });
  }

  getMessages(): Subject<SprNotificationsDAO> {
    if (this.stompClient.connected) {
      this.stompClient.subscribe(
        `/message/listen-messages/${(AuthUtil.getLoginResult().session as SprBackendWebSessionInfo).sessionKey}`,
        (response: IMessage) => {
          this.messageItem.next(response.body as unknown as SprNotificationsDAO);
        }
      );
    }
    return this.messageItem;
  }

  getSysWorksMessage(): Subject<NtisSystemWorksDAO> {
    if (this.stompClient.connected) {
      this.stompClient.subscribe(
        `/message/listen-sys-works/${(AuthUtil.getLoginResult().session as SprBackendWebSessionInfo).sessionKey}`,
        (response: IMessage) => {
          this.sysWorksItem.next(response.body as unknown as NtisSystemWorksDAO);
        }
      );
    }
    return this.sysWorksItem;
  }

  stompDisconnect(): void {
    this.stompClient.disconnect();
  }
}
