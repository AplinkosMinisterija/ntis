import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NtisFormFieldComponent } from './ntis-form-field.component';

describe('FormFieldComponent', () => {
  let component: NtisFormFieldComponent;
  let fixture: ComponentFixture<NtisFormFieldComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NtisFormFieldComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NtisFormFieldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
