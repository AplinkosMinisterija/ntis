import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CodesTranslationsPageComponent } from './codes-translations-page.component';

describe('CodesTranslationsPageComponent', () => {
  let component: CodesTranslationsPageComponent;
  let fixture: ComponentFixture<CodesTranslationsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CodesTranslationsPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CodesTranslationsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
