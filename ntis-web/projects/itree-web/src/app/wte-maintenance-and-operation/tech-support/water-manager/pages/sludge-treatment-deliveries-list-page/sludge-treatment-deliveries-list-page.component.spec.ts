import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SludgeTreatmentDeliveriesListPageComponent } from './sludge-treatment-deliveries-list-page.component';

describe('SludgeTreatmentDeliveriesListPageComponent', () => {
  let component: SludgeTreatmentDeliveriesListPageComponent;
  let fixture: ComponentFixture<SludgeTreatmentDeliveriesListPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SludgeTreatmentDeliveriesListPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SludgeTreatmentDeliveriesListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
