import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResearchOutsideNtisCreateComponent } from './research-outside-ntis-create.component';

describe('ResearchOutsideNtisCreateComponent', () => {
  let component: ResearchOutsideNtisCreateComponent;
  let fixture: ComponentFixture<ResearchOutsideNtisCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResearchOutsideNtisCreateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResearchOutsideNtisCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
