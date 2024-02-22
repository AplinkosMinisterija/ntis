import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SewageDeliveryInfoComponent } from './sewage-delivery-info.component';

describe('SewageDeliveryInfoComponent', () => {
  let component: SewageDeliveryInfoComponent;
  let fixture: ComponentFixture<SewageDeliveryInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SewageDeliveryInfoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SewageDeliveryInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
