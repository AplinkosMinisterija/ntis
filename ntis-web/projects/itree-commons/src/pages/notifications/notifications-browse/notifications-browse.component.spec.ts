import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotificationsBrowseComponent } from './notifications-browse.component';

describe('NotificationsBrowseComponent', () => {
  let component: NotificationsBrowseComponent;
  let fixture: ComponentFixture<NotificationsBrowseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NotificationsBrowseComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NotificationsBrowseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
