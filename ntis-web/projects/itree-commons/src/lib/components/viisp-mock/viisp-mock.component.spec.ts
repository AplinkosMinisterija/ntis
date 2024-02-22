import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViispMockComponent } from './viisp-mock.component';

describe('ViispMockComponent', () => {
  let component: ViispMockComponent;
  let fixture: ComponentFixture<ViispMockComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViispMockComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViispMockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
