import { Component, EventEmitter, HostListener, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { CommonFormServices } from '@itree/ngx-s2-commons';

@Component({
  selector: 'spr-text-truncate',
  templateUrl: './text-truncate.component.html',
  styleUrls: ['./text-truncate.component.scss'],
})
export class TextTruncateComponent implements OnInit, OnChanges {
  @Input() text: string;
  @Input() textLength: number = 0;
  @Input() showCopyButton: boolean = true;
  @Input() showPrintButton: boolean = false;
  @Input() headerText: string = 'components.textTruncate.headerText';
  @Input() translateHeader = true;
  @Input() textAlignment: 'text-left' | 'text-center' | 'text-right' | 'text-justify' = 'text-justify';
  @Input() type: 'readMore' | 'linkText' = 'readMore';
  @Input() showHTML: boolean = false;
  @Input() showComponentDialog: boolean = true;
  @Output() clickedOnMore: EventEmitter<void> = new EventEmitter<void>();

  truncatedText: string;
  showTextConteiner: boolean = false;
  textCopied: boolean = false;

  constructor(protected commonFormServices: CommonFormServices) {}

  ngOnInit(): void {
    this.truncate();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.text) {
      this.truncate();
    }
  }

  truncate(): void {
    if (this.text?.length > this.textLength && this.textLength > 0) {
      this.truncatedText = `${this.text
        .replace(/<\/?p[^>]*>/g, ' ')
        .slice(0, -(this.text.length - this.textLength))}... `;
    } else {
      this.truncatedText = null;
    }
  }

  clipboard(element: HTMLDivElement): void {
    if (navigator.clipboard) {
      navigator.clipboard.writeText(element.innerText).then(
        () => {
          this.textCopied = true;
        },
        (error) => {
          console.error(error);
        }
      );
    }
  }

  print(element: HTMLDivElement): void {
    const isAndroid = window.navigator.userAgent.indexOf('Android') !== -1;
    const windows = window.open('', isAndroid ? '_self' : '_blank', 'width=900, height=600, left=200, top=100');
    windows.document.write(element.innerText);
    windows.print();
    if (isAndroid) {
      windows.addEventListener('afterprint', () => {
        location.reload();
      });
    } else {
      windows.close();
    }
  }

  showDialog(value: string): void {
    if (this.showComponentDialog) {
      if (value === this.type) {
        this.showTextConteiner = !this.showTextConteiner;
        this.textCopied = false;
      }
    } else {
      this.clickedOnMore.emit();
    }
  }

  @HostListener('document:keydown.Escape', ['$event'])
  onKeydownHandler(): void {
    this.showTextConteiner = false;
  }
}
