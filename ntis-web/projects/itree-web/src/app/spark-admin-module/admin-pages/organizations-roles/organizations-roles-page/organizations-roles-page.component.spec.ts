import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganizationsRolesPageComponent } from './organizations-roles-page.component';

describe('OrganizationsRolesPageComponent', () => {
  let component: OrganizationsRolesPageComponent;
  let fixture: ComponentFixture<OrganizationsRolesPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrganizationsRolesPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrganizationsRolesPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
