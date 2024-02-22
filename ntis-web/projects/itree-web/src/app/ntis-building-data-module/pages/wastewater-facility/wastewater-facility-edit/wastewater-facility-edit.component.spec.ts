import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WastewaterFacilityEditComponent } from './wastewater-facility-edit.component';

describe('WastewaterFacilityEditComponent', () => {
  let component: WastewaterFacilityEditComponent;
  let fixture: ComponentFixture<WastewaterFacilityEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [WastewaterFacilityEditComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(WastewaterFacilityEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
