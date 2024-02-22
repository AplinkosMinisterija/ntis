import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AggloInfoVersionPageComponent } from './agglo-info-version-page.component';

describe('AggloInfoVersionPageComponent', () => {
  let component: AggloInfoVersionPageComponent;
  let fixture: ComponentFixture<AggloInfoVersionPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ AggloInfoVersionPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AggloInfoVersionPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
