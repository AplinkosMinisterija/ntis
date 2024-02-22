import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CacheManagerPageComponent } from './cache-manager-page.component';

describe('CacheManagerPageComponent', () => {
  let component: CacheManagerPageComponent;
  let fixture: ComponentFixture<CacheManagerPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CacheManagerPageComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CacheManagerPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
