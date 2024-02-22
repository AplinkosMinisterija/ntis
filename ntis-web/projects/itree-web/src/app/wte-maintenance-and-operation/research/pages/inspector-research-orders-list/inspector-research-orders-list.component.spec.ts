import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InspectorResearchOrdersListComponent } from './inspector-research-orders-list.component';

describe('InspectorResearchOrdersListComponent', () => {
  let component: InspectorResearchOrdersListComponent;
  let fixture: ComponentFixture<InspectorResearchOrdersListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InspectorResearchOrdersListComponent]
    });
    fixture = TestBed.createComponent(InspectorResearchOrdersListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
