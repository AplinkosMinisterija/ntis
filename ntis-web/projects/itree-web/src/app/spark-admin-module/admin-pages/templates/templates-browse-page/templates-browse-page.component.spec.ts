import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TemplatesBrowsePageComponent } from './templates-browse-page.component';

describe('TemplatesBrowsePageComponent', () => {
  let component: TemplatesBrowsePageComponent;
  let fixture: ComponentFixture<TemplatesBrowsePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TemplatesBrowsePageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TemplatesBrowsePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
