import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewsBrowseComponent } from './news-browse.component';

describe('NewsBrowseComponent', () => {
  let component: NewsBrowseComponent;
  let fixture: ComponentFixture<NewsBrowseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewsBrowseComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewsBrowseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
