import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarsBrowsePageComponent } from './cars-browse-page.component';

describe('CarsBrowsePageComponent', () => {
  let component: CarsBrowsePageComponent;
  let fixture: ComponentFixture<CarsBrowsePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CarsBrowsePageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CarsBrowsePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
