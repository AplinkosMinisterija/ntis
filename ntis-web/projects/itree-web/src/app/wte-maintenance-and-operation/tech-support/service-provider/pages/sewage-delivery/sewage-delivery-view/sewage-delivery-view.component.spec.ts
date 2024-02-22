import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SewageDeliveryViewComponent } from './sewage-delivery-view.component';

describe('SewageDeliveryViewComponent', () => {
  let component: SewageDeliveryViewComponent;
  let fixture: ComponentFixture<SewageDeliveryViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SewageDeliveryViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SewageDeliveryViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
