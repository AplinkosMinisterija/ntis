import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrdImportResearchLinesListComponent } from './ord-import-research-lines-list.component';

describe('OrdImportResearchLinesListComponent', () => {
  let component: OrdImportResearchLinesListComponent;
  let fixture: ComponentFixture<OrdImportResearchLinesListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrdImportResearchLinesListComponent]
    });
    fixture = TestBed.createComponent(OrdImportResearchLinesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
