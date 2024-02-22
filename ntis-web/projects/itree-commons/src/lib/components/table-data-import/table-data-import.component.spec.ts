import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableDataImportComponent } from './table-data-import.component';

describe('TableDataImportComponent', () => {
  let component: TableDataImportComponent;
  let fixture: ComponentFixture<TableDataImportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TableDataImportComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TableDataImportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
