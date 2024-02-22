import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WaterManagerFacilitiesListComponent } from './water-manager-facilities-list.component';

describe('WaterManagerFacilitiesListComponent', () => {
  let component: WaterManagerFacilitiesListComponent;
  let fixture: ComponentFixture<WaterManagerFacilitiesListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WaterManagerFacilitiesListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WaterManagerFacilitiesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
