import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilityModelEditPageComponent } from './facility-model-edit-page.component';

describe('FacilityModelEditPageComponent', () => {
  let component: FacilityModelEditPageComponent;
  let fixture: ComponentFixture<FacilityModelEditPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FacilityModelEditPageComponent]
    });
    fixture = TestBed.createComponent(FacilityModelEditPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
