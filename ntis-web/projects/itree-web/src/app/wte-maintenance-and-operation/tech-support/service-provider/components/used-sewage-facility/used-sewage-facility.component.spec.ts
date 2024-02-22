import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsedSewageFacilityComponent } from './used-sewage-facility.component';

describe('UsedSewageFacilityComponent', () => {
  let component: UsedSewageFacilityComponent;
  let fixture: ComponentFixture<UsedSewageFacilityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UsedSewageFacilityComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UsedSewageFacilityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
