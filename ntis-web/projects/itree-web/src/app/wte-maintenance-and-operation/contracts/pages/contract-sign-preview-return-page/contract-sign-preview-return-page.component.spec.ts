import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContractSignPreviewReturnPageComponent } from './contract-sign-preview-return-page.component';

describe('ContractSignPreviewReturnPageComponent', () => {
  let component: ContractSignPreviewReturnPageComponent;
  let fixture: ComponentFixture<ContractSignPreviewReturnPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContractSignPreviewReturnPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContractSignPreviewReturnPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
