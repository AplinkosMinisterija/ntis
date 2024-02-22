import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RoleActionsRoleBrowsePageComponent } from './role-actions-role-page.component';



describe('RoleActionsRoleBrowsePageComponent', () => {
  let component: RoleActionsRoleBrowsePageComponent;
  let fixture: ComponentFixture<RoleActionsRoleBrowsePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RoleActionsRoleBrowsePageComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RoleActionsRoleBrowsePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
