import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CentralizedWastewaterDataViewPageComponent } from './centralized-wastewater-data-view-page.component';

describe('CentralizedWastewaterDataViewPageComponent', () => {
  let component: CentralizedWastewaterDataViewPageComponent;
  let fixture: ComponentFixture<CentralizedWastewaterDataViewPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CentralizedWastewaterDataViewPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CentralizedWastewaterDataViewPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
