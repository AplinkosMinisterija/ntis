import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PriorityFacilitiesListComponent } from './priority-facilities-list.component';

describe('PriorityFacilitiesListComponent', () => {
  let component: PriorityFacilitiesListComponent;
  let fixture: ComponentFixture<PriorityFacilitiesListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PriorityFacilitiesListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PriorityFacilitiesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
