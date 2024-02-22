import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonsBrowsePageComponent } from './persons-browse-page.component';

describe('PersonsBrowsePageComponent', () => {
  let component: PersonsBrowsePageComponent;
  let fixture: ComponentFixture<PersonsBrowsePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PersonsBrowsePageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonsBrowsePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
