import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SludgeTreatmentDeliveryPageComponent } from './sludge-treatment-delivery-page.component';

describe('SludgeTreatmentDeliveryPageComponent', () => {
  let component: SludgeTreatmentDeliveryPageComponent;
  let fixture: ComponentFixture<SludgeTreatmentDeliveryPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SludgeTreatmentDeliveryPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SludgeTreatmentDeliveryPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
