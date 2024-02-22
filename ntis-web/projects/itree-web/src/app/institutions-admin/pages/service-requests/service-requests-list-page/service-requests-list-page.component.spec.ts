import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceRequestsListPageComponent } from './service-requests-list-page.component';

describe('ServiceRequestsListPageComponent', () => {
  let component: ServiceRequestsListPageComponent;
  let fixture: ComponentFixture<ServiceRequestsListPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServiceRequestsListPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServiceRequestsListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
