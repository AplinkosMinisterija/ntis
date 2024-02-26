import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdrAddressesListPageComponent } from './adr-addresses-list-page.component';

describe('AdrAddressesListPageComponent', () => {
  let component: AdrAddressesListPageComponent;
  let fixture: ComponentFixture<AdrAddressesListPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdrAddressesListPageComponent]
    });
    fixture = TestBed.createComponent(AdrAddressesListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
