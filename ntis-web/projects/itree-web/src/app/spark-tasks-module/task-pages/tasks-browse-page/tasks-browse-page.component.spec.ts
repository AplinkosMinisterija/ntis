import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TasksBrowsePageComponent } from './tasks-browse-page.component';

describe('TasksBrowsePageComponent', () => {
  let component: TasksBrowsePageComponent;
  let fixture: ComponentFixture<TasksBrowsePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TasksBrowsePageComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TasksBrowsePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
