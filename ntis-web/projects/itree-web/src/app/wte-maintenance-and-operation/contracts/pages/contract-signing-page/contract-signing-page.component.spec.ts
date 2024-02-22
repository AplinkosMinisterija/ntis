import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContractSigningPageComponent } from './contract-signing-page.component';

describe('ContractSigningPageComponent', () => {
  let component: ContractSigningPageComponent;
  let fixture: ComponentFixture<ContractSigningPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContractSigningPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContractSigningPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
