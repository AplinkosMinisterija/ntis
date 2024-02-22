import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CentralizedWastewaterDataReportComponent } from './centralized-wastewater-data-report.component';

describe('CentralizedWastewaterDataReportComponent', () => {
  let component: CentralizedWastewaterDataReportComponent;
  let fixture: ComponentFixture<CentralizedWastewaterDataReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CentralizedWastewaterDataReportComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CentralizedWastewaterDataReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
