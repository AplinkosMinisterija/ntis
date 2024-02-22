import { Component, Input, OnInit } from '@angular/core';
import { FaIconsService } from '../../../services/fa-icons.service';

@Component({
  selector: 'spr-accessibility-button',
  templateUrl: './accessibility-button.component.html',
  styleUrls: ['./accessibility-button.component.scss'],
})
export class AccessibilityButtonComponent implements OnInit {
  @Input() level: number = 0;
  @Input() maxLevel: number | string = 3;
  @Input() iconName: string;
  @Input() text: string;
  levelsArr: unknown[] = [];

  constructor(public faIconsService: FaIconsService) {}

  ngOnInit(): void {
    this.levelsArr = Array(this.maxLevel);
  }
}
