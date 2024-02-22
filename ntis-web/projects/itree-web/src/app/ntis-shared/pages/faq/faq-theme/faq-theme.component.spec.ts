import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FaqThemeComponent } from './faq-theme.component';

describe('FaqThemeComponent', () => {
  let component: FaqThemeComponent;
  let fixture: ComponentFixture<FaqThemeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FaqThemeComponent]
    });
    fixture = TestBed.createComponent(FaqThemeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
