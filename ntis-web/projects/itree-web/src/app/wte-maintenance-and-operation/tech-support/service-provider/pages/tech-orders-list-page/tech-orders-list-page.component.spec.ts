import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TechOrdersListPageComponent } from './tech-orders-list-page.component';

describe('TechOrdersListPageComponent', () => {
  let component: TechOrdersListPageComponent;
  let fixture: ComponentFixture<TechOrdersListPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TechOrdersListPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TechOrdersListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
