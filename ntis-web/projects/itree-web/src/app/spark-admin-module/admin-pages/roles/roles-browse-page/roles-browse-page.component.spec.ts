import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RolesBrowsePageComponent } from './roles-browse-page.component';

describe('RolesBrowsePageComponent', () => {
  let component: RolesBrowsePageComponent;
  let fixture: ComponentFixture<RolesBrowsePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RolesBrowsePageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RolesBrowsePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
