import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SystemWorksEditComponent } from './system-works-edit.component';

describe('SystemWorksEditComponent', () => {
  let component: SystemWorksEditComponent;
  let fixture: ComponentFixture<SystemWorksEditComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SystemWorksEditComponent]
    });
    fixture = TestBed.createComponent(SystemWorksEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
