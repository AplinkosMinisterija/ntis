import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CwFileErrorsListComponent } from './cw-file-errors-list.component';

describe('CwFileErrorsListComponent', () => {
  let component: CwFileErrorsListComponent;
  let fixture: ComponentFixture<CwFileErrorsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CwFileErrorsListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CwFileErrorsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
