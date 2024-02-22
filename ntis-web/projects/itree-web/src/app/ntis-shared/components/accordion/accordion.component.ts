import { Component, Input, OnInit } from '@angular/core';
import { animate, state, style, transition, trigger } from '@angular/animations';
enum AccordionStateEnum {
  expanded = 'expanded',
  collapsed = 'collapsed',
}
@Component({
  selector: 'ntis-accordion',
  templateUrl: './accordion.component.html',
  styleUrls: ['./accordion.component.scss'],
  animations: [
    trigger('slideContent', [
      state(AccordionStateEnum.collapsed, style({ height: '0px' })),
      state(AccordionStateEnum.expanded, style({ height: '*' })),
      transition(`${AccordionStateEnum.collapsed}=>${AccordionStateEnum.expanded}`, animate('200ms ease-in')),
      transition(`${AccordionStateEnum.expanded}=>${AccordionStateEnum.collapsed}`, animate('200ms ease-out')),
    ]),
  ],
})
export class AccordionComponent {
  @Input() label: string = '';
  @Input() asCard: boolean = false;
  @Input() uppercase: boolean = false;
  @Input() showHeader: boolean = true;

  accordionStateEnum = AccordionStateEnum;
  accordionState = this.accordionStateEnum.expanded;

  toggleAccordion(): void {
    if (!this.asCard) {
      this.accordionState =
        this.accordionState === this.accordionStateEnum.collapsed
          ? this.accordionStateEnum.expanded
          : this.accordionStateEnum.collapsed;
    }
  }
}
