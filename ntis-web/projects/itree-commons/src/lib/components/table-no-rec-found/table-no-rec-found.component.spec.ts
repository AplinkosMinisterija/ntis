import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableNoRecFoundComponent } from './table-no-rec-found.component';

describe('TableNoRecFoundComponent', () => {
  let component: TableNoRecFoundComponent;
  let fixture: ComponentFixture<TableNoRecFoundComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TableNoRecFoundComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TableNoRecFoundComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
