import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyFavWaterManagersComponent } from './my-fav-water-managers.component';

describe('MyFavWaterManagersComponent', () => {
  let component: MyFavWaterManagersComponent;
  let fixture: ComponentFixture<MyFavWaterManagersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MyFavWaterManagersComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MyFavWaterManagersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
