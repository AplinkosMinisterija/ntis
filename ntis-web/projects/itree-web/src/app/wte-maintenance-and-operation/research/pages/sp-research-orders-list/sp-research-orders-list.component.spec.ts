import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpResearchOrdersListComponent } from './sp-research-orders-list.component';

describe('SpResearchOrdersListComponent', () => {
  let component: SpResearchOrdersListComponent;
  let fixture: ComponentFixture<SpResearchOrdersListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SpResearchOrdersListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SpResearchOrdersListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
