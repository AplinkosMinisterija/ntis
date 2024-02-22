import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResearchNormsManagementComponent } from './research-norms-management.component';

describe('ResearchNormsManagementComponent', () => {
  let component: ResearchNormsManagementComponent;
  let fixture: ComponentFixture<ResearchNormsManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResearchNormsManagementComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResearchNormsManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
