import { Input } from '@angular/core';
import { Component } from '@angular/core';
import { LoaderService } from '@itree/ngx-s2-commons';
import { fadeOutLineLoader, loaderFadeAnimation } from '../../animations/animations';

@Component({
  selector: 'spr-loader',
  templateUrl: './loader.component.html',
  styleUrls: ['./loader.component.scss'],
  animations: [loaderFadeAnimation, fadeOutLineLoader],
})
export class LoaderComponent {
  divList = new Array(12) as Array<number>;
  @Input() type: 'line' | 'spinner' = 'spinner';

  constructor(public loaderService: LoaderService) {}
}
