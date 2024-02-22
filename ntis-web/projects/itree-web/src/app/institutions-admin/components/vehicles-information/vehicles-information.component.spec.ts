import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VehiclesInformationComponent } from './vehicles-information.component';

describe('VehiclesInformationComponent', () => {
  let component: VehiclesInformationComponent;
  let fixture: ComponentFixture<VehiclesInformationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VehiclesInformationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VehiclesInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
