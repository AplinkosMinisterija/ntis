import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoleSelectPageComponent } from './role-select-page.component';

describe('RoleSelectPageComponent', () => {
  let component: RoleSelectPageComponent;
  let fixture: ComponentFixture<RoleSelectPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RoleSelectPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RoleSelectPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
