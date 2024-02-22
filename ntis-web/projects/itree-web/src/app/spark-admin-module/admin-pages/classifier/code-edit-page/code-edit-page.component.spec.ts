import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CodeEditPageComponent } from './code-edit-page.component';

describe('CodeEditPageComponent', () => {
  let component: CodeEditPageComponent;
  let fixture: ComponentFixture<CodeEditPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CodeEditPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CodeEditPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
