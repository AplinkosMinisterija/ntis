import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WaterManagerWtfInformationComponent } from './water-manager-wtf-information.component';

describe('WaterManagerWtfInformationComponent', () => {
  let component: WaterManagerWtfInformationComponent;
  let fixture: ComponentFixture<WaterManagerWtfInformationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WaterManagerWtfInformationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WaterManagerWtfInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
