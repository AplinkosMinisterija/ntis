import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContractRequestPageComponent } from './contract-request-page.component';

describe('ContractRequestPageComponent', () => {
  let component: ContractRequestPageComponent;
  let fixture: ComponentFixture<ContractRequestPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContractRequestPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContractRequestPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
