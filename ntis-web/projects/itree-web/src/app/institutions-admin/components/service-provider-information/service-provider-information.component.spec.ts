import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceProviderInformationComponent } from './service-provider-information.component';

describe('ServiceProviderInformationComponent', () => {
  let component: ServiceProviderInformationComponent;
  let fixture: ComponentFixture<ServiceProviderInformationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServiceProviderInformationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServiceProviderInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
