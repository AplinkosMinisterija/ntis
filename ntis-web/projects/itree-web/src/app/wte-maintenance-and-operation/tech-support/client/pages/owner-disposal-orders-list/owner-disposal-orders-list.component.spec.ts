import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnerDisposalOrdersListComponent } from './owner-disposal-orders-list.component';

describe('OwnerDisposalOrdersListComponent', () => {
  let component: OwnerDisposalOrdersListComponent;
  let fixture: ComponentFixture<OwnerDisposalOrdersListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OwnerDisposalOrdersListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OwnerDisposalOrdersListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
