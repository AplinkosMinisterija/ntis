import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WfManagersEditComponent } from './wf-managers-edit.component';

describe('WfManagersEditComponent', () => {
  let component: WfManagersEditComponent;
  let fixture: ComponentFixture<WfManagersEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WfManagersEditComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WfManagersEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
