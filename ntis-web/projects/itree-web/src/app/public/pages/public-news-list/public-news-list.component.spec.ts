import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PublicNewsListComponent } from './public-news-list.component';

describe('PublicNewsListComponent', () => {
  let component: PublicNewsListComponent;
  let fixture: ComponentFixture<PublicNewsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PublicNewsListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PublicNewsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
