import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrgSelectPageComponent } from './org-select-page.component';

describe('OrgSelectPageComponent', () => {
  let component: OrgSelectPageComponent;
  let fixture: ComponentFixture<OrgSelectPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrgSelectPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrgSelectPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
