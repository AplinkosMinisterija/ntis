import { ComponentFixture, TestBed } from '@angular/core/testing';
import { WastewaterFacilityViewComponent } from './wastewater-facility-view';

describe('SewageTreatmentFacilityReviewComponent', () => {
  let component: WastewaterFacilityViewComponent;
  let fixture: ComponentFixture<WastewaterFacilityViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [WastewaterFacilityViewComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(WastewaterFacilityViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
