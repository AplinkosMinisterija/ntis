import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableRowActionsComponent } from './table-row-actions.component';

describe('TableRowActionsComponent', () => {
  let component: TableRowActionsComponent;
  let fixture: ComponentFixture<TableRowActionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TableRowActionsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TableRowActionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
