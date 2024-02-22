import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecordStatusComponent } from './record-status.component';

describe('RecordtStatusComponent', () => {
  let component: RecordStatusComponent;
  let fixture: ComponentFixture<RecordStatusComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RecordStatusComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(RecordStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
