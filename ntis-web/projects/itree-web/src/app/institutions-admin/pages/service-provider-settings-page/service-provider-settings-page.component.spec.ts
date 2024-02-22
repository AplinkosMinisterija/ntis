import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceProviderSettingsPageComponent } from './service-provider-settings-page.component';

describe('ServiceProviderSettingsPageComponent', () => {
  let component: ServiceProviderSettingsPageComponent;
  let fixture: ComponentFixture<ServiceProviderSettingsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServiceProviderSettingsPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServiceProviderSettingsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
