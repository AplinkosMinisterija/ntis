import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PropertiesEditPageComponent } from './properties-edit-page.component';

describe('PropertiesEditPageComponent', () => {
  let component: PropertiesEditPageComponent;
  let fixture: ComponentFixture<PropertiesEditPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PropertiesEditPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PropertiesEditPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
