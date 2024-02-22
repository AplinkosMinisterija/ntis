import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContractUploadPageComponent } from './contract-upload-page.component';

describe('ContractUploadPageComponent', () => {
  let component: ContractUploadPageComponent;
  let fixture: ComponentFixture<ContractUploadPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContractUploadPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContractUploadPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
