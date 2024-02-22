import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormsBrowsePageComponent } from './forms-browse-page.component';

describe('FormsBrowsePageComponent', () => {
  let component: FormsBrowsePageComponent;
  let fixture: ComponentFixture<FormsBrowsePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormsBrowsePageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormsBrowsePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
