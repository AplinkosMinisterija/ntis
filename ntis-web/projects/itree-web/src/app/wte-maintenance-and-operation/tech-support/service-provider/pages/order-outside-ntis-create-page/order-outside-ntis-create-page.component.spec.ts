import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderOutsideNtisCreatePageComponent } from './order-outside-ntis-create-page.component';

describe('OrderOutsideNtisCreatePageComponent', () => {
  let component: OrderOutsideNtisCreatePageComponent;
  let fixture: ComponentFixture<OrderOutsideNtisCreatePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrderOutsideNtisCreatePageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrderOutsideNtisCreatePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
