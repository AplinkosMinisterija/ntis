import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DisposalOrdersListPageComponent } from './disposal-orders-list-page.component';

describe('DisposalOrdersListPageComponent', () => {
  let component: DisposalOrdersListPageComponent;
  let fixture: ComponentFixture<DisposalOrdersListPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DisposalOrdersListPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DisposalOrdersListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
