import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewsForAdminListComponent } from './reviews-for-admin-list.component';

describe('ReviewsForAdminListComponent', () => {
  let component: ReviewsForAdminListComponent;
  let fixture: ComponentFixture<ReviewsForAdminListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReviewsForAdminListComponent]
    });
    fixture = TestBed.createComponent(ReviewsForAdminListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
