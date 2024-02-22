import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExistingFacilitySearchComponent } from './existing-facility-search.component';

describe('ExistingFacilitySearchComponent', () => {
  let component: ExistingFacilitySearchComponent;
  let fixture: ComponentFixture<ExistingFacilitySearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ExistingFacilitySearchComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ExistingFacilitySearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
