import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ScrollService {
  innerScroll = new BehaviorSubject<number | null>(1);

  constructor() {
    document.getElementById('content-layout').addEventListener('scroll', () => {
      const { scrollTop, clientHeight, scrollHeight } = document.getElementById('content-layout');
      if (scrollTop + clientHeight >= scrollHeight - 200) {
        this.innerScroll.next(this.innerScroll.value + 1);
      }
    });
  }
}
