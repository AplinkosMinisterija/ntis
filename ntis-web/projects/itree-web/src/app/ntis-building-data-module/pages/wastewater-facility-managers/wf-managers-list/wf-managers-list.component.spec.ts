import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WfManagersListComponent } from './wf-managers-list.component';

describe('WfManagersListComponent', () => {
  let component: WfManagersListComponent;
  let fixture: ComponentFixture<WfManagersListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WfManagersListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WfManagersListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
