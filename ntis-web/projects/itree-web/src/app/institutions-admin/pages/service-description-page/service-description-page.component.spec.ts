import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceDescriptionPageComponent } from './service-description-page.component';

describe('ServiceDescriptionComponent', () => {
  let component: ServiceDescriptionPageComponent;
  let fixture: ComponentFixture<ServiceDescriptionPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ServiceDescriptionPageComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ServiceDescriptionPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
