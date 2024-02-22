import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilterManagerComponent } from './filter-manager.component';

describe('FilterTemplateComponent', () => {
  let component: FilterManagerComponent;
  let fixture: ComponentFixture<FilterManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FilterManagerComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(FilterManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
