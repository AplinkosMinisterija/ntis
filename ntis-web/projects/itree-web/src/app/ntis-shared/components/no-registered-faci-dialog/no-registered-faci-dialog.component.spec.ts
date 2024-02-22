import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NoRegisteredFaciDialogComponent } from './no-registered-faci-dialog.component';

describe('NoRegisteredFaciDialogComponent', () => {
  let component: NoRegisteredFaciDialogComponent;
  let fixture: ComponentFixture<NoRegisteredFaciDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NoRegisteredFaciDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NoRegisteredFaciDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
