import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WaterManagerSettingsPageComponent } from './water-manager-settings-page.component';

describe('WaterManagerSettingsPageComponent', () => {
  let component: WaterManagerSettingsPageComponent;
  let fixture: ComponentFixture<WaterManagerSettingsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WaterManagerSettingsPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WaterManagerSettingsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
