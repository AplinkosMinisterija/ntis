import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilitiesInstallerInformationComponent } from './facilities-installer-information.component';

describe('FacilitiesInstallerInformationComponent', () => {
  let component: FacilitiesInstallerInformationComponent;
  let fixture: ComponentFixture<FacilitiesInstallerInformationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FacilitiesInstallerInformationComponent]
    });
    fixture = TestBed.createComponent(FacilitiesInstallerInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
