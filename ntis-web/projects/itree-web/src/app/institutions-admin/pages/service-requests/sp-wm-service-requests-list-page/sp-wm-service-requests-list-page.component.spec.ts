import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpWmServiceRequestsListPageComponent } from './sp-wm-service-requests-list-page.component';

describe('SpWmServiceRequestsListPageComponent', () => {
  let component: SpWmServiceRequestsListPageComponent;
  let fixture: ComponentFixture<SpWmServiceRequestsListPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SpWmServiceRequestsListPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SpWmServiceRequestsListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
