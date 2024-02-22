import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectOrAddWtfDialogComponent } from './select-or-add-wtf-dialog.component';

describe('SelectOrAddWtfDialogComponent', () => {
  let component: SelectOrAddWtfDialogComponent;
  let fixture: ComponentFixture<SelectOrAddWtfDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SelectOrAddWtfDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SelectOrAddWtfDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
