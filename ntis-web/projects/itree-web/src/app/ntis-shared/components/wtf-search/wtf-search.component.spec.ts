import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WtfSearchComponent } from './wtf-search.component';

describe('WtfSearchComponent', () => {
  let component: WtfSearchComponent;
  let fixture: ComponentFixture<WtfSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WtfSearchComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WtfSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
