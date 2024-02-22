import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TemplateEditPageComponent } from './template-edit-page.component';

describe('TemplateEditPageComponent', () => {
  let component: TemplateEditPageComponent;
  let fixture: ComponentFixture<TemplateEditPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TemplateEditPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TemplateEditPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
