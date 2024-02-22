import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InstitutionsListPageComponent } from './institutions-list-page.component';

describe('InstitutionsListPageComponent', () => {
  let component: InstitutionsListPageComponent;
  let fixture: ComponentFixture<InstitutionsListPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InstitutionsListPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InstitutionsListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
