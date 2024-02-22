import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeliveredSludgeInfoComponent } from './delivered-sludge-info.component';

describe('DeliveredSludgeInfoComponent', () => {
  let component: DeliveredSludgeInfoComponent;
  let fixture: ComponentFixture<DeliveredSludgeInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeliveredSludgeInfoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeliveredSludgeInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
