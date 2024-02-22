import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceRequestCreatePageComponent } from './service-request-create-page.component';

describe('ServiceRequestCreatePageComponent', () => {
  let component: ServiceRequestCreatePageComponent;
  let fixture: ComponentFixture<ServiceRequestCreatePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServiceRequestCreatePageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServiceRequestCreatePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
