import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnerResearchOrdersListComponent } from './owner-research-orders-list.component';

describe('OwnerResearchOrdersListComponent', () => {
  let component: OwnerResearchOrdersListComponent;
  let fixture: ComponentFixture<OwnerResearchOrdersListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OwnerResearchOrdersListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OwnerResearchOrdersListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
