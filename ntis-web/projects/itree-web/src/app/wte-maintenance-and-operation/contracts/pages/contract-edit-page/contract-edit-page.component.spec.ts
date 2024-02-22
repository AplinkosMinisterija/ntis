import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContractEditPageComponent } from './contract-edit-page.component';

describe('ContractEditPageComponent', () => {
  let component: ContractEditPageComponent;
  let fixture: ComponentFixture<ContractEditPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContractEditPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContractEditPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
