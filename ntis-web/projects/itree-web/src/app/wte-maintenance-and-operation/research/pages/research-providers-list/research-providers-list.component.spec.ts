import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResearchProvidersListComponent } from './research-providers-list.component';

describe('ResearchProvidersListComponent', () => {
  let component: ResearchProvidersListComponent;
  let fixture: ComponentFixture<ResearchProvidersListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ResearchProvidersListComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ResearchProvidersListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
