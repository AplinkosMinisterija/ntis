import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailedSearchButtonComponent } from './detailed-search-button.component';

describe('DetailedSearchButtonComponent', () => {
  let component: DetailedSearchButtonComponent;
  let fixture: ComponentFixture<DetailedSearchButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DetailedSearchButtonComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetailedSearchButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
