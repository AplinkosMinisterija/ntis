import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormEditPageComponent } from './form-edit-page.component';

describe('FormEditPageComponent', () => {
  let component: FormEditPageComponent;
  let fixture: ComponentFixture<FormEditPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormEditPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormEditPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
