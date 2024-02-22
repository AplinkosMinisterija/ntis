import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WastewaterFacilityBuildingEditComponent } from './wastewater-facility-building-edit.component';

describe('WastewaterFacilityBuildingEditComponent', () => {
  let component: WastewaterFacilityBuildingEditComponent;
  let fixture: ComponentFixture<WastewaterFacilityBuildingEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WastewaterFacilityBuildingEditComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WastewaterFacilityBuildingEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
