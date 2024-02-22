import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NtisMapComponent } from './map.component';

describe('NtisMapComponent', () => {
  let component: NtisMapComponent;
  let fixture: ComponentFixture<NtisMapComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NtisMapComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(NtisMapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
