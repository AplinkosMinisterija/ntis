import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganizationUsersPageComponent } from './organization-users-page.component';

describe('OrganizationUsersPageComponent', () => {
  let component: OrganizationUsersPageComponent;
  let fixture: ComponentFixture<OrganizationUsersPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OrganizationUsersPageComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(OrganizationUsersPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
