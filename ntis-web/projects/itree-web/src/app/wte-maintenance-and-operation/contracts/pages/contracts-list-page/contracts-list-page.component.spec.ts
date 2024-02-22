import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContractsListPageComponent } from './contracts-list-page.component';

describe('ContractsListPageComponent', () => {
  let component: ContractsListPageComponent;
  let fixture: ComponentFixture<ContractsListPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContractsListPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContractsListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
