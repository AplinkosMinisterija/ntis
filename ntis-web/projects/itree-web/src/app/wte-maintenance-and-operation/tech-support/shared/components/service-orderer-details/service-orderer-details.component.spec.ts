import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceOrdererDetailsComponent } from './service-orderer-details.component';

describe('ServiceOrdererDetailsComponent', () => {
  let component: ServiceOrdererDetailsComponent;
  let fixture: ComponentFixture<ServiceOrdererDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServiceOrdererDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServiceOrdererDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
