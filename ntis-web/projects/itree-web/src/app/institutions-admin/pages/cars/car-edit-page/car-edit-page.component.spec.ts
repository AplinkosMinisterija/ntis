import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarEditPageComponent } from './car-edit-page.component';

describe('CarEditPageComponent', () => {
  let component: CarEditPageComponent;
  let fixture: ComponentFixture<CarEditPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CarEditPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CarEditPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
