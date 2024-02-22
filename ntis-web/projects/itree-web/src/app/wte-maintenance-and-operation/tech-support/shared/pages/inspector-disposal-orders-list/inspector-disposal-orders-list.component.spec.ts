import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InspectorDisposalOrdersListComponent } from './inspector-disposal-orders-list.component';

describe('InspectorDisposalOrdersListComponent', () => {
  let component: InspectorDisposalOrdersListComponent;
  let fixture: ComponentFixture<InspectorDisposalOrdersListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InspectorDisposalOrdersListComponent]
    });
    fixture = TestBed.createComponent(InspectorDisposalOrdersListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
