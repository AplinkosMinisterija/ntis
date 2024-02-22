import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoleActionsPageComponent } from './role-actions-page.component';

describe('RoleActionsPageComponent', () => {
  let component: RoleActionsPageComponent;
  let fixture: ComponentFixture<RoleActionsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RoleActionsPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RoleActionsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
