import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ResearchRequestedCriteriaModel } from '@itree-commons/src/lib/model/api/api';
import { CommonService } from '@itree-commons/src/lib/services/common.service';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';

@Component({
  selector: 'app-research-options-checkbox-list',
  templateUrl: './research-options-checkbox-list.component.html',
  styleUrls: ['./research-options-checkbox-list.component.scss'],
  standalone: true,
  imports: [CommonModule, NtisSharedModule],
})
export class ResearchOptionsCheckboxListComponent implements OnInit {
  @Input() selectedCriteria: ResearchRequestedCriteriaModel[];
  @Input() copiedCriteria: string[] = [];
  @Input() labelText: string;
  @Input() selectionDisabled: boolean = true;
  @Output() criteriaToOrder: EventEmitter<string[]> = new EventEmitter<string[]>();
  criteriaSelectionList: ResearchRequestedCriteriaModel[] = [];
  selectedResearchTypes: string[] = [];

  constructor(private clsfService: CommonService) {}

  ngOnInit(): void {
    this.clsfService.getClsf('NTIS_RESEARCH_TYPE').subscribe((options) => {
      this.criteriaSelectionList = options.map((item) => ({
        display: item.display,
        code: item.key,
        isSelected: undefined,
        belongs: undefined,
        rn_id: undefined,
      }));
      if (this.copiedCriteria?.length > 0) {
        this.criteriaSelectionList.forEach((criteria) => {
          if (this.copiedCriteria.find((critStr) => critStr === criteria.code)) {
            criteria.isSelected = true;
          } else {
            criteria.isSelected = false;
          }
        });
      }
    });
    if (this.copiedCriteria?.length > 0) {
      this.selectedResearchTypes = this.copiedCriteria;
    }
  }

  onCheckboxChange($event: Event, key: string): void {
    const eventTarget = $event.target as HTMLInputElement;
    if (eventTarget.checked) {
      this.selectedResearchTypes.push(key);
    } else {
      this.selectedResearchTypes = this.selectedResearchTypes.filter((research) => research !== key);
    }
    this.criteriaToOrder.emit(this.selectedResearchTypes);
  }
}
