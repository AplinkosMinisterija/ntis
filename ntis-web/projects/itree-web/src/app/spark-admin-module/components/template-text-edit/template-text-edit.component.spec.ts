import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TemplateTextEditComponent } from './template-text-edit.component';

describe('TemplateTextEditComponent', () => {
  let component: TemplateTextEditComponent;
  let fixture: ComponentFixture<TemplateTextEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TemplateTextEditComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TemplateTextEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
