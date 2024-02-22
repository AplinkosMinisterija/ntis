import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceDescriptionDetailsComponent } from './service-description-details.component';

describe('ServiceDescriptionDetailsComponent', () => {
  let component: ServiceDescriptionDetailsComponent;
  let fixture: ComponentFixture<ServiceDescriptionDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServiceDescriptionDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServiceDescriptionDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
