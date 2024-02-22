import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddressSearchFormComponent } from './address-search-form.component';

describe('AddressSearchFormComponent', () => {
  let component: AddressSearchFormComponent;
  let fixture: ComponentFixture<AddressSearchFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddressSearchFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddressSearchFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
