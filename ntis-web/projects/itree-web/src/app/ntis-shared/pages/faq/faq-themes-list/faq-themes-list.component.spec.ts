import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FaqThemesListComponent } from './faq-themes-list.component';

describe('FaqThemesListComponent', () => {
  let component: FaqThemesListComponent;
  let fixture: ComponentFixture<FaqThemesListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FaqThemesListComponent]
    });
    fixture = TestBed.createComponent(FaqThemesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
