import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceProvidersListComponent } from './service-providers-list.component';

describe('ServiceProvidersListComponent', () => {
  let component: ServiceProvidersListComponent;
  let fixture: ComponentFixture<ServiceProvidersListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServiceProvidersListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServiceProvidersListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
