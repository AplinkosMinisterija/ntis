import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MapMenuSearchComponent } from './map-menu-search.component';

describe('MapMenuSearchComponent', () => {
  let component: MapMenuSearchComponent;
  let fixture: ComponentFixture<MapMenuSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ MapMenuSearchComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MapMenuSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
