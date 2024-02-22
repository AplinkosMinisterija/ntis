import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LegalEntityDetailsComponent } from './legal-entity-details.component';

describe('LegalEntityDetailsComponent', () => {
  let component: LegalEntityDetailsComponent;
  let fixture: ComponentFixture<LegalEntityDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LegalEntityDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LegalEntityDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
