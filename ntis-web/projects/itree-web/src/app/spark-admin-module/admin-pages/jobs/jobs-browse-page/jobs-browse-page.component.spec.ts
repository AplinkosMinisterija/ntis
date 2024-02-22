import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JobsBrowsePageComponent } from './jobs-browse-page.component';

describe('JobsBrowsePageComponent', () => {
  let component: JobsBrowsePageComponent;
  let fixture: ComponentFixture<JobsBrowsePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JobsBrowsePageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(JobsBrowsePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
