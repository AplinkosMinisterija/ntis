import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SewageDeliveriesListComponent } from './sewage-deliveries-list.component';

describe('SewageDeliveriesListComponent', () => {
  let component: SewageDeliveriesListComponent;
  let fixture: ComponentFixture<SewageDeliveriesListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SewageDeliveriesListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SewageDeliveriesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
