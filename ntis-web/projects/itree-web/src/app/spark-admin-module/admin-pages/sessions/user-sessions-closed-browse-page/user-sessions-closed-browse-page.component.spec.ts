import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserSessionsClosedBrowsePageComponent } from './user-sessions-closed-browse-page.component';

describe('UserSessionClosedBrowsePageComponent', () => {
  let component: UserSessionsClosedBrowsePageComponent;
  let fixture: ComponentFixture<UserSessionsClosedBrowsePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UserSessionsClosedBrowsePageComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserSessionsClosedBrowsePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
