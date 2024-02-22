import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InstitutionsEditPageComponent } from './institutions-edit-page.component';

describe('InstitutionsEditPageComponent', () => {
  let component: InstitutionsEditPageComponent;
  let fixture: ComponentFixture<InstitutionsEditPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InstitutionsEditPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InstitutionsEditPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
