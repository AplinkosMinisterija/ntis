import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceRequestReviewPageComponent } from './service-request-review-page.component';

describe('ServiceRequestReviewPageComponent', () => {
  let component: ServiceRequestReviewPageComponent;
  let fixture: ComponentFixture<ServiceRequestReviewPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServiceRequestReviewPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServiceRequestReviewPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
