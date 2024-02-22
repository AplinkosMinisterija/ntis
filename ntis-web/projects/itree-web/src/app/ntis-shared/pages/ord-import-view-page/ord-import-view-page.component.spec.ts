import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrdImportViewPageComponent } from './ord-import-view-page.component';

describe('OrdImportViewPageComponent', () => {
  let component: OrdImportViewPageComponent;
  let fixture: ComponentFixture<OrdImportViewPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrdImportViewPageComponent]
    });
    fixture = TestBed.createComponent(OrdImportViewPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
