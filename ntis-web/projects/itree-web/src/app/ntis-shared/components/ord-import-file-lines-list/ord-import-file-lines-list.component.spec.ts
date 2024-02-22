import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrdImportFileLinesListComponent } from './ord-import-file-lines-list.component';

describe('OrdImportFileLinesListComponent', () => {
  let component: OrdImportFileLinesListComponent;
  let fixture: ComponentFixture<OrdImportFileLinesListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrdImportFileLinesListComponent]
    });
    fixture = TestBed.createComponent(OrdImportFileLinesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
