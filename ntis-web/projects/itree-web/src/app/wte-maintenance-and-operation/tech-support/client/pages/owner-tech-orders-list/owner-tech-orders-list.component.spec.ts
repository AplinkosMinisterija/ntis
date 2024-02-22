import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnerTechOrdersListComponent } from './owner-tech-orders-list.component';

describe('OwnerTechOrdersListComponent', () => {
  let component: OwnerTechOrdersListComponent;
  let fixture: ComponentFixture<OwnerTechOrdersListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OwnerTechOrdersListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OwnerTechOrdersListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
