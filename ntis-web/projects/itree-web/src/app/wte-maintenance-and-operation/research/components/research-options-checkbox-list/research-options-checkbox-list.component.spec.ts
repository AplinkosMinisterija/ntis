import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResearchOptionsCheckboxListComponent } from './research-options-checkbox-list.component';

describe('ResearchOptionsCheckboxListComponent', () => {
  let component: ResearchOptionsCheckboxListComponent;
  let fixture: ComponentFixture<ResearchOptionsCheckboxListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResearchOptionsCheckboxListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResearchOptionsCheckboxListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
