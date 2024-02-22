import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceRequestPropsComponent } from './service-request-props.component';

describe('ServiceRequestPropsComponent', () => {
  let component: ServiceRequestPropsComponent;
  let fixture: ComponentFixture<ServiceRequestPropsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServiceRequestPropsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServiceRequestPropsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
