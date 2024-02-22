import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AggloInfoPageComponent } from './agglo-info-page.component';

describe('AggloInfoPageComponent', () => {
  let component: AggloInfoPageComponent;
  let fixture: ComponentFixture<AggloInfoPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ AggloInfoPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AggloInfoPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
