import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MapMenuLayersComponent } from './map-menu-layers.component';

describe('MapMenuLayersComponent', () => {
  let component: MapMenuLayersComponent;
  let fixture: ComponentFixture<MapMenuLayersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ MapMenuLayersComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MapMenuLayersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
