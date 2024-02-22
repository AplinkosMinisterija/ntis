import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceRequestContentComponent } from './service-request-content.component';

describe('ServiceRequestContentComponent', () => {
  let component: ServiceRequestContentComponent;
  let fixture: ComponentFixture<ServiceRequestContentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServiceRequestContentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServiceRequestContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
