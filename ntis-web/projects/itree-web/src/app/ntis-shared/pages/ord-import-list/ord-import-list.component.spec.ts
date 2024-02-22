import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrdImportListComponent } from './ord-import-list.component';

describe('OrdImportListComponent', () => {
  let component: OrdImportListComponent;
  let fixture: ComponentFixture<OrdImportListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrdImportListComponent]
    });
    fixture = TestBed.createComponent(OrdImportListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
