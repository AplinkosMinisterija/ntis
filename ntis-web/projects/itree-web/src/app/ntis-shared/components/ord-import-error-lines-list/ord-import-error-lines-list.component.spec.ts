import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrdImportErrorLinesListComponent } from './ord-import-error-lines-list.component';

describe('OrdImportErrorLinesListComponent', () => {
  let component: OrdImportErrorLinesListComponent;
  let fixture: ComponentFixture<OrdImportErrorLinesListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrdImportErrorLinesListComponent]
    });
    fixture = TestBed.createComponent(OrdImportErrorLinesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
