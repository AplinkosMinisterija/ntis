import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckWtfSelectionComponent } from './check-wtf-selection.component';

describe('CheckWtfSelectionComponent', () => {
  let component: CheckWtfSelectionComponent;
  let fixture: ComponentFixture<CheckWtfSelectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ CheckWtfSelectionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CheckWtfSelectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
