import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ConfirmEmailPageComponent } from './confirm-email-page.component';

describe('ConfirmEmailPageComponent', () => {
  let component: ConfirmEmailPageComponent;
  let fixture: ComponentFixture<ConfirmEmailPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ConfirmEmailPageComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfirmEmailPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
