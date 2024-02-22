import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResearchNormsHistoryComponent } from './research-norms-history.component';

describe('ResearchNormsHistoryComponent', () => {
  let component: ResearchNormsHistoryComponent;
  let fixture: ComponentFixture<ResearchNormsHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResearchNormsHistoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResearchNormsHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
