import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResearchOrderPageComponent } from './research-order-page.component';

describe('ResearchOrderPageComponent', () => {
  let component: ResearchOrderPageComponent;
  let fixture: ComponentFixture<ResearchOrderPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResearchOrderPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResearchOrderPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
