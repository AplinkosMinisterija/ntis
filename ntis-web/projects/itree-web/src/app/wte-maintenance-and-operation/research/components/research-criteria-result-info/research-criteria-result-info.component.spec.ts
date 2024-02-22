import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResearchCriteriaResultInfoComponent } from './research-criteria-result-info.component';

describe('ResearchCriteriaResultInfoComponent', () => {
  let component: ResearchCriteriaResultInfoComponent;
  let fixture: ComponentFixture<ResearchCriteriaResultInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResearchCriteriaResultInfoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResearchCriteriaResultInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
