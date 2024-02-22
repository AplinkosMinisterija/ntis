import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DictionariesBrowsePageComponent } from './dictionaries-browse-page.component';

describe('DictionariesBrowsePageComponent', () => {
  let component: DictionariesBrowsePageComponent;
  let fixture: ComponentFixture<DictionariesBrowsePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DictionariesBrowsePageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DictionariesBrowsePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
