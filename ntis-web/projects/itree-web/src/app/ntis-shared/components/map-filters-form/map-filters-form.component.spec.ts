import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MapFiltersFormComponent } from './map-filters-form.component';

describe('MapFiltersFormComponent', () => {
  let component: MapFiltersFormComponent;
  let fixture: ComponentFixture<MapFiltersFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MapFiltersFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MapFiltersFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
