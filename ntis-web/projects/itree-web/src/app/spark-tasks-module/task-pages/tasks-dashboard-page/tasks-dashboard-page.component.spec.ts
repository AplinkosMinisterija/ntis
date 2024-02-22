import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TasksDashboardPageComponent } from './tasks-dashboard-page.component';

describe('TasksDashboardPageComponent', () => {
  let component: TasksDashboardPageComponent;
  let fixture: ComponentFixture<TasksDashboardPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TasksDashboardPageComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TasksDashboardPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
