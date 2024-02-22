import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonsEditPageComponent } from './persons-edit-page.component';

describe('PersonsEditPageComponent', () => {
  let component: PersonsEditPageComponent;
  let fixture: ComponentFixture<PersonsEditPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PersonsEditPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonsEditPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
