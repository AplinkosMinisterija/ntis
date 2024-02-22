import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NtisClientInfoComponent } from './ntis-client-info.component';

describe('NtisClientInfoComponent', () => {
  let component: NtisClientInfoComponent;
  let fixture: ComponentFixture<NtisClientInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NtisClientInfoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NtisClientInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
