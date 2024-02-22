import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubmitAggloDataComponent } from './submit-agglo-data.component';

describe('SubmitAggloDataComponent', () => {
  let component: SubmitAggloDataComponent;
  let fixture: ComponentFixture<SubmitAggloDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ SubmitAggloDataComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubmitAggloDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
