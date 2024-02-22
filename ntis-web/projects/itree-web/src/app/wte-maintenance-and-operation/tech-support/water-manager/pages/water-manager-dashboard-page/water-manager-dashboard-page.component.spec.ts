import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WaterManagerDashboardPageComponent } from './water-manager-dashboard-page.component';

describe('WaterManagerDashboardPageComponent', () => {
  let component: WaterManagerDashboardPageComponent;
  let fixture: ComponentFixture<WaterManagerDashboardPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WaterManagerDashboardPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WaterManagerDashboardPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
