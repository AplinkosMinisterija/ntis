import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SewageDeliveryEditComponent } from './sewage-delivery-edit.component';

describe('SewageDeliveryEditComponent', () => {
  let component: SewageDeliveryEditComponent;
  let fixture: ComponentFixture<SewageDeliveryEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SewageDeliveryEditComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SewageDeliveryEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
