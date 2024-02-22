import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResearchProvidersListTableComponent } from './research-providers-data.component';

describe('ResearchProvidersListTableComponent', () => {
  let component: ResearchProvidersListTableComponent;
  let fixture: ComponentFixture<ResearchProvidersListTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ResearchProvidersListTableComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ResearchProvidersListTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
