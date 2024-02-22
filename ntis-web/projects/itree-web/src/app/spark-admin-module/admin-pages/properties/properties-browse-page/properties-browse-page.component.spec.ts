import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PropertiesBrowsePageComponent } from './properties-browse-page.component';

describe('PropertiesbrowsePageComponent', () => {
  let component: PropertiesBrowsePageComponent;
  let fixture: ComponentFixture<PropertiesBrowsePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PropertiesBrowsePageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PropertiesBrowsePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
