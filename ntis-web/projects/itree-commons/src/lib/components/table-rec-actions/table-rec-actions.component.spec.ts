import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TableRecActionsComponent } from './table-rec-actions.component';

describe('TableRecActionsComponent', () => {
  let component: TableRecActionsComponent;
  let fixture: ComponentFixture<TableRecActionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TableRecActionsComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TableRecActionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
