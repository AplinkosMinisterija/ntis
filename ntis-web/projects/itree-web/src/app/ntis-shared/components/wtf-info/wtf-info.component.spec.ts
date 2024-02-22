import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WtfInfoComponent } from './wtf-info.component';

describe('WtfInfoComponent', () => {
  let component: WtfInfoComponent;
  let fixture: ComponentFixture<WtfInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WtfInfoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WtfInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
