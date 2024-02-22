import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserOrganizationsPageComponent } from './user-organizations-page.component';

describe('UserOrganizationsPageComponent', () => {
  let component: UserOrganizationsPageComponent;
  let fixture: ComponentFixture<UserOrganizationsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserOrganizationsPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserOrganizationsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
