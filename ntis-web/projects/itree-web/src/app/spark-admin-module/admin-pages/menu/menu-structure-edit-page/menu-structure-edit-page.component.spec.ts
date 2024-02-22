import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuStructureEditPageComponent } from './menu-structure-edit-page.component';

describe('MenuStructureEditPageComponent', () => {
  let component: MenuStructureEditPageComponent;
  let fixture: ComponentFixture<MenuStructureEditPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MenuStructureEditPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MenuStructureEditPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
