import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PriorityFacilitiesOrgComponent } from './priority-facilities-org.component';

describe('PriorityFacilitiesOrgComponent', () => {
  let component: PriorityFacilitiesOrgComponent;
  let fixture: ComponentFixture<PriorityFacilitiesOrgComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PriorityFacilitiesOrgComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PriorityFacilitiesOrgComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
