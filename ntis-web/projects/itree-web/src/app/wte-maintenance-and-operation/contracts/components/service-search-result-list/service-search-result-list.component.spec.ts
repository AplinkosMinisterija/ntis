import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceSearchResultListComponent } from './service-search-result-list.component';

describe('ServiceSearchResultListComponent', () => {
  let component: ServiceSearchResultListComponent;
  let fixture: ComponentFixture<ServiceSearchResultListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServiceSearchResultListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServiceSearchResultListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
