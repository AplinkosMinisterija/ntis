import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DictionariesEditPageComponent } from './dictionaries-edit-page.component';

describe('DictionariesEditPageComponent', () => {
  let component: DictionariesEditPageComponent;
  let fixture: ComponentFixture<DictionariesEditPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DictionariesEditPageComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DictionariesEditPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
