import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrdImportRemovalLinesListComponent } from './ord-import-removal-lines-list.component';

describe('OrdImportRemovalLinesListComponent', () => {
  let component: OrdImportRemovalLinesListComponent;
  let fixture: ComponentFixture<OrdImportRemovalLinesListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrdImportRemovalLinesListComponent]
    });
    fixture = TestBed.createComponent(OrdImportRemovalLinesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
