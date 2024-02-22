import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CentralizedWastewaterDataListComponent } from './centralized-wastewater-data-list.component';

describe('CentralizedWastewaterDataListComponent', () => {
  let component: CentralizedWastewaterDataListComponent;
  let fixture: ComponentFixture<CentralizedWastewaterDataListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CentralizedWastewaterDataListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CentralizedWastewaterDataListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
