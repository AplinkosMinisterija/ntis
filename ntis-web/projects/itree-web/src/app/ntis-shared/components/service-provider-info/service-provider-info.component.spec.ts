import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceProviderInfoComponent } from './service-provider-info.component';

describe('ServiceProviderInfoComponent', () => {
  let component: ServiceProviderInfoComponent;
  let fixture: ComponentFixture<ServiceProviderInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServiceProviderInfoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServiceProviderInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
