import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceProviderDashboardPageComponent } from './service-provider-dashboard-page.component';

describe('ServiceProviderDashboardPageComponent', () => {
  let component: ServiceProviderDashboardPageComponent;
  let fixture: ComponentFixture<ServiceProviderDashboardPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServiceProviderDashboardPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServiceProviderDashboardPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
