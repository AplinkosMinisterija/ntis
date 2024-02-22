import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceSearchPageComponent } from './service-search-page.component';

describe('ServiceSearchPageComponent', () => {
  let component: ServiceSearchPageComponent;
  let fixture: ComponentFixture<ServiceSearchPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServiceSearchPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServiceSearchPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
