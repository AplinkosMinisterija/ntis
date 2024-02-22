import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserSessionOpenBrowsePageComponent } from './user-session-open-browse-page.component';

describe('UserSessionOpenBrowsePageComponent', () => {
  let component: UserSessionOpenBrowsePageComponent;
  let fixture: ComponentFixture<UserSessionOpenBrowsePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserSessionOpenBrowsePageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserSessionOpenBrowsePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
