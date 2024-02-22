import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CwFileLinesListComponent } from './cw-file-lines-list.component';

describe('CwFileLinesListComponent', () => {
  let component: CwFileLinesListComponent;
  let fixture: ComponentFixture<CwFileLinesListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CwFileLinesListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CwFileLinesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
