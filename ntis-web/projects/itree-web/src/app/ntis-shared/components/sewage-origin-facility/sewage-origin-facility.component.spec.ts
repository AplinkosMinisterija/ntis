import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SewageOriginFacilityComponent } from './sewage-origin-facility.component';

describe('SewageOriginFacilityComponent', () => {
  let component: SewageOriginFacilityComponent;
  let fixture: ComponentFixture<SewageOriginFacilityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SewageOriginFacilityComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SewageOriginFacilityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
