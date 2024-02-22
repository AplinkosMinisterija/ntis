import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoleEditPageComponent } from './role-edit-page.component';

describe('RoleEditPageComponent', () => {
  let component: RoleEditPageComponent;
  let fixture: ComponentFixture<RoleEditPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RoleEditPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RoleEditPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});