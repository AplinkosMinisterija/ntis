import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoleOrganizationsPageComponent } from './role-organizations-page.component';

describe('RoleOrganizationsPageComponent', () => {
  let component: RoleOrganizationsPageComponent;
  let fixture: ComponentFixture<RoleOrganizationsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RoleOrganizationsPageComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(RoleOrganizationsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
