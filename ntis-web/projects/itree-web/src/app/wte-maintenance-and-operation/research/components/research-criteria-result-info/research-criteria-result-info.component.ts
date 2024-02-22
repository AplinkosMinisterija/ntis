import { CommonModule } from '@angular/common';
import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ResearchCriteriaResultsModel } from '@itree-commons/src/lib/model/api/api';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisResearchType } from '@itree-web/src/app/ntis-shared/enums/classifiers.enums';

@Component({
  selector: 'app-research-criteria-result-info',
  templateUrl: './research-criteria-result-info.component.html',
  styleUrls: ['./research-criteria-result-info.component.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule, ItreeCommonsModule, ReactiveFormsModule],
})
export class ResearchCriteriaResultInfoComponent implements OnInit, OnChanges {
  readonly translationsReference = 'wteMaintenanceAndOperation.research.components.researchCriteriaResults';
  readonly doesNotComply = 'RESEARCH_NORM_NOT_COMPLIES';
  readonly researchTypes = NtisResearchType;
  @Input() editMode: boolean = true;
  @Input() data: ResearchCriteriaResultsModel;
  @Input() submit: boolean = false;

  unit: string;

  form = new FormGroup({
    result: new FormControl<number>(null, Validators.compose([Validators.max(1000)])),
  });

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.submit?.currentValue) {
      this.form.markAllAsTouched();
    }
  }

  ngOnInit(): void {
    this.form.controls.result.setValue(this.data.result);
    if (this.data.resId) {
      this.form.controls.result.addValidators([Validators.required]);
    }
    this.form.controls.result.valueChanges.subscribe((newValue) => {
      this.data.result = newValue;
    });
    this.checkForUnit(this.data?.code);
  }

  checkForUnit(researchType: string): void {
    switch (researchType) {
      case this.researchTypes.PHOSPHORUS:
        this.unit = 'mg P/l';
        break;
      case this.researchTypes.NITROGEN:
        this.unit = 'mg N/l';
        break;
      case this.researchTypes.DROWNING:
        this.unit = 'mg/l';
        break;
      case this.researchTypes.BIOCHEMICAL:
        this.unit = 'mg O2/l';
        break;
    }
  }
}
