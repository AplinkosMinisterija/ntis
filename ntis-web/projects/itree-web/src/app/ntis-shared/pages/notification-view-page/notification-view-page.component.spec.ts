import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotificationViewPageComponent } from './notification-view-page.component';

describe('NotificationViewPageComponent', () => {
  let component: NotificationViewPageComponent;
  let fixture: ComponentFixture<NotificationViewPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NotificationViewPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NotificationViewPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
