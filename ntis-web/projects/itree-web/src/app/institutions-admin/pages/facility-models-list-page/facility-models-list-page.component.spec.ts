import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilityModelsListPageComponent } from './facility-models-list-page.component';

describe('FacilityModelsListPageComponent', () => {
  let component: FacilityModelsListPageComponent;
  let fixture: ComponentFixture<FacilityModelsListPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FacilityModelsListPageComponent]
    });
    fixture = TestBed.createComponent(FacilityModelsListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
