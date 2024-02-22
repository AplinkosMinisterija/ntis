import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JobEditPageComponent } from './job-edit-page.component';

describe('JobEditPageComponent', () => {
  let component: JobEditPageComponent;
  let fixture: ComponentFixture<JobEditPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JobEditPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(JobEditPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
