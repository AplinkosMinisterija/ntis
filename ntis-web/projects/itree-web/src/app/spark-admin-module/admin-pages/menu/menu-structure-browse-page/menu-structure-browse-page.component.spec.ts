import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuStructureBrowsePageComponent } from './menu-structure-browse-page.component';

describe('MenuStructureBrowsePageComponent', () => {
  let component: MenuStructureBrowsePageComponent;
  let fixture: ComponentFixture<MenuStructureBrowsePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MenuStructureBrowsePageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MenuStructureBrowsePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
