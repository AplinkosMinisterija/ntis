import { ConfirmationService } from 'primeng/api';
import { ActivatedRoute, Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { AppMessages } from '../message/app.messages';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root',
})
export class CommonFormServices {
  calendarLt = {
    closeText: 'Done',
    prevText: 'Prev',
    nextText: 'Next',
    currentText: 'Today',
    today: 'Šiandien',
    clear: 'Išvalyti',
    monthNames: [
      'sausis',
      'vasaris',
      'kovas',
      'balandis',
      'gegužė',
      'birželis',
      'liepa',
      'rugpjūtis',
      'rugsėjis',
      'spalis',
      'lapkritis',
      'gruodis',
    ],
    monthNamesShort: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
    dayNames: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
    dayNamesShort: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
    dayNamesMin: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
    weekHeader: 'Wk',
    dateFormat: 'dd/mm/yy',
    firstDay: 1,
    isRTL: false,
    showMonthAfterYear: false,
    yearSuffix: '',
  };

  constructor(
    public route: ActivatedRoute,
    public router: Router,
    public appMessages: AppMessages,
    public translate: TranslateService,
    public confirmationService: ConfirmationService
  ) {}
}
