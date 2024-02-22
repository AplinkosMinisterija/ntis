import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoleUsersPageComponent } from './role-users-page.component';

describe('RoleUsersPageComponent', () => {
  let component: RoleUsersPageComponent;
  let fixture: ComponentFixture<RoleUsersPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RoleUsersPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RoleUsersPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
