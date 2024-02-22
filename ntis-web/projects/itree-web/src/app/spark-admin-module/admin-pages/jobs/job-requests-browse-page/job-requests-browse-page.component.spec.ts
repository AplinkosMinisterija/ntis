import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JobRequestsBrowsePageComponent } from './job-requests-browse-page.component';

describe('JobRequestsBrowsePageComponent', () => {
  let component: JobRequestsBrowsePageComponent;
  let fixture: ComponentFixture<JobRequestsBrowsePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [JobRequestsBrowsePageComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(JobRequestsBrowsePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
