import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubmittedAggloDataListComponent } from './submitted-agglo-data-list.component';

describe('SubmittedAggloDataListComponent', () => {
  let component: SubmittedAggloDataListComponent;
  let fixture: ComponentFixture<SubmittedAggloDataListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ SubmittedAggloDataListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubmittedAggloDataListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
