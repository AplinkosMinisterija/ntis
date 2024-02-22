import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClsfDropdownComponent } from './clsf-dropdown.component';

describe('ClsfDropdownComponent', () => {
  let component: ClsfDropdownComponent;
  let fixture: ComponentFixture<ClsfDropdownComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ClsfDropdownComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ClsfDropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
