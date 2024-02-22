import { ComponentFixture, TestBed } from '@angular/core/testing';
import { BrowseSearchFormComponent } from './browse-search-form.component';

describe('BrowseSearchFormComponent', () => {
  let component: BrowseSearchFormComponent;
  let fixture: ComponentFixture<BrowseSearchFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BrowseSearchFormComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BrowseSearchFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
